package nex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jig.Vector;

public class ServerData {

	
	static int p1X;
	static int p1Y;
	static int p2X, p2Y;
	static int numberOfPlayers;
	volatile boolean playerOne, playerTwo;
    //volatile HashSet<DataOutputStream> playerWriters;
    
	/*
	 * Graph setup
	 */
	static ArrayList<Node> dijkstraGraph = new ArrayList<Node>();
	static List<Integer> tileSet1 = new ArrayList<Integer>();
	int wallType;
	static Graph graph;
    
    //Enemy Setup
	static ArrayList<ServerEnemyData> monsters = new ArrayList<ServerEnemyData>();
	
	/*
	 * Map setup
	 */
	public int count = 0;
	static Tile[][] tileSet;
	public static int row = 0, col = 0;

	
	//INIT
	
	public ServerData(){
		
		ServerEnemyData enemy = new ServerEnemyData((15*65)-867+33, (19*65)-967+33);
		ServerEnemyData enemy = new ServerEnemyData((19*65)+22, (19*65)+57);
		enemy.setTilePosition(new Vector(15,19));
		monsters.add(enemy);

		p1X = 0; p1Y = 0;
		p2X = 0; p2Y = 0;
		numberOfPlayers = 0;
		playerOne = false;
		playerTwo = false;
		tileSet = new Tile[40][40];
	}

	
	//this builds a new graph based on changes made to the map and will rerun dijkstras to produce a usable graph
	public static void buildGraph(){

		graph = new Graph();

		for(Iterator<Node> i = graph.nodes.iterator(); i.hasNext();){
			Node n = i.next();
			for(Iterator<Edge> j = n.edges.iterator(); j.hasNext();){
				Edge e = j.next();
				e.weight = tileSet[e.myX][e.myY].getWeight();
			}
		}	

		int xValue = (int) Math.floor((p1X+368)/65); 
		int yValue = (int) Math.floor((p1Y+268)/65);
		
//		System.out.println("xValue = " + xValue + ", yValue = " + yValue); // DEBUG
		
		dijkstraGraph = Dijkstra.runDijkstra(graph, xValue, yValue);
		updateEnemies();
	}
//	public static void buildGraph(){
//
//		graph = new Graph();
//
//		for(Iterator<Node> i = graph.nodes.iterator(); i.hasNext();){
//			Node n = i.next();
//			for(Iterator<Edge> j = n.edges.iterator(); j.hasNext();){
//				Edge e = j.next();
//				e.weight = tileSet[e.myX][e.myY].getWeight();
//			}
//		}	
//		int xValue = (int) Math.floor((p1X+368)/65); int yValue = (int) Math.floor((p1Y+268)/65);
//		dijkstraGraph = Dijkstra.runDijkstra(graph, xValue, yValue);
//		updateEnemies();
//	}
	
	public static void updateEnemies(){
		
		
//		for(Iterator<Node> n = dijkstraGraph.iterator(); n.hasNext();){
//			Node cycleNode = n.next();
//			for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
//				ServerEnemyData enemy = e.next();
//				int enemyX = (int) enemy.getMapPosition().getX()+867; // gets pixel
//				int enemyY = (int) enemy.getMapPosition().getY()+967; // gets pixel
//				int xCenter = (cycleNode.x*65)+33;
//				int yCenter = (cycleNode.y*65)+33;
//				
//				System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
//						", xCenter = " + xCenter + ", yCenter = " + yCenter);
//				
//				// Checks to make sure the enemy is centered in the tile
//				if(xCenter == enemyX && yCenter == enemyY){
//					if((cycleNode.px*65+33)-enemyX == 0 && (cycleNode.py*65+33)-enemyY > 0){ // North
//						System.out.println("Go North");
//						enemy.directionMovement = 1;
//					} 
//					else if ((cycleNode.px*65+33)-enemyX == 0 && (cycleNode.py*65+33)-enemyY < 0){ // South
//						System.out.println("Go South");
//
//						enemy.directionMovement = 3;
//					} 
//					else if ((cycleNode.px*65+33)-enemyX > 0 && (cycleNode.py*65+33)-enemyY == 0){ // East
//						System.out.println("Go East");
//
//						enemy.directionMovement = 2;
//					} 
//					else if ((cycleNode.px*65+33)-enemyX < 0 && (cycleNode.py*65+33)-enemyY == 0){ // West
//						System.out.println("Go West");
//
//						enemy.directionMovement = 4;
//					}									
//				}			
//			}
//		}
		
		for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
			ServerEnemyData enemy = e.next();
			
			int enemyX = (int) enemy.getMapPosition().getX()+466; // gets pixel
			int enemyY = (int) enemy.getMapPosition().getY()+666; // gets pixel
			
			int p1x = p1X;
			int p1y = p1Y;
			
//			System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
//					", p1x = " + p1x + ", p1y = " + p1y);
			
			// Move left or right
			if(Math.abs(enemyX) % 65 == 22 || Math.abs(enemyX) % 65 == 43)
			{
//				System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
//						", p1x = " + p1x + ", p1y = " + p1y);
				if (enemyX < p1x){ // East
					System.out.println("Go East");
	
					enemy.directionMovement = 2;
				} 
				else if (enemyX > p1x){ // West
					System.out.println("Go West");
	
					enemy.directionMovement = 4;
				}		
			}
			
			// Move up or down
			else if(Math.abs(enemyY) % 65 == 57 || Math.abs(enemyY) % 65 == 8)
			{
//				System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
//						", p1x = " + p1x + ", p1y = " + p1y);
				if(enemyY > p1y){ // North
					System.out.println("Go North");
					
					enemy.directionMovement = 1;
				} 
				else if (enemyY < p1y){ // South
					System.out.println("Go South");
	
					enemy.directionMovement = 3;
				} 
			}

		}
		

		for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
			ServerEnemyData enemy = e.next();
			int enemyX = (int) enemy.getMapPosition().getX()+466;		//gets pixel
			int enemyY = (int) enemy.getMapPosition().getY()+666;		//gets pixel
			
			if(enemy.directionMovement == 1){
				enemy.setMapPosition(new Vector(enemyX,enemyY-1)); // North
			} 
			else if(enemy.directionMovement == 3){
				enemy.setMapPosition(new Vector(enemyX,enemyY+1)); // South
			} 
			else if(enemy.directionMovement == 2){
				enemy.setMapPosition(new Vector(enemyX+1,enemyY)); // East
			} 
			else if(enemy.directionMovement == 4){
				enemy.setMapPosition(new Vector(enemyX-1,enemyY)); // West
			}
		}
//		System.out.println("before");
//		int print = 0;
//		for(int i = 0; i < 40; i++){
//			for(int j = 0; j < 40; j++){		
//				Node n = dijkstraGraph.get(print);
//				
//				if( (int) Math.floor((p1X/65)+303) == i &&  (int) Math.floor((p1Y/65)+203) == j){
//					System.out.print("O");
//				} else if(n.x == n.px && n.y > n.py){
//					System.out.print("^");
//				} else if (n.x == n.px && n.y < n.py){
//					System.out.print("v");
//				} else if (n.x > n.px && n.y == n.py){
//					System.out.print("<");
//				} else if (n.x < n.px && n.y == n.py){
//					System.out.print(">");
//				} else {
//					System.out.print("E");
//				}
//				print++;
//			}
//			System.out.println("");
//		}
//		System.out.println("after");
			System.out.println("enemy position x: " + myX + " y: " + myY);
				
			int xDist = Math.abs(myX - p1X);
			int yDist = Math.abs(myY - p1Y);

			int p1Dist = (int) Math.sqrt(xDist*xDist + yDist*yDist);
			
			xDist = Math.abs(myX - p2X);
			yDist = Math.abs(myY - p2Y);
			
			int p2Dist = (int) Math.sqrt(xDist*xDist + yDist*yDist);
			
			if(p1Dist > p2Dist && numberOfPlayers > 1){
				//attack player 2
				
				if((myX - p2X) == 0 && (myY - p2Y) < 0){
					enemy.setMapPosition(new Vector(myX,myY-1));
				} else if((myX - p2X) == 0 && (myY - p2Y) >= 0){	//south
					enemy.setMapPosition(new Vector(myX,myY+1));
				} else if((myX - p2X) >= 0 && (myY - p2Y) == 0){ //east
					enemy.setMapPosition(new Vector(myX+1,myY));
				} else {
					enemy.setMapPosition(new Vector(myX-1,myY));
				}
					
			} else {
				//attack player 1
				
				//North
//				if((myX - p1X) == 0 && (myY - p1Y) < 0){
//					enemy.setMapPosition(new Vector(myX,myY-1));
//				} else if((myX - p1X) == 0 && (myY - p1Y) >= 0){	//south
//					enemy.setMapPosition(new Vector(myX,myY+1));
//				} else if((myX - p1X) >= 0 && (myY - p1Y) == 0){ //east
//					enemy.setMapPosition(new Vector(myX+1,myY));
//				} else if((myX - p1X) < 0 && (myY - p1Y) == 0){
//					enemy.setMapPosition(new Vector(myX-1,myY));
//				}
			}
			
					
		}			

		

			
//			if(enemy.directionMovement == 1){
//				enemy.setMapPosition(new Vector(myX,myY-1));
//			} else if(enemy.directionMovement == 2){
//				enemy.setMapPosition(new Vector(myX+1,myY));
//			} else if(enemy.directionMovement == 3){
//				enemy.setMapPosition(new Vector(myX,myY+1));
//			} else if(enemy.directionMovement == 4){
//				enemy.setMapPosition(new Vector(myX-1,myY));
//			}
		
	}
	
	
//	public static void buildTileSet(){
//		
//		for(int i = 0; i < 40; i++){
//			for(int j = 0; j < 40; j++){		
//				tileSet[j][i] = new Tile();
//				if(tileSet1.get(((40*i)+j)) == 1){
//					tileSet[j][i].setCollision();
//					tileSet[j][i].setWeight(100);
//				}
//			}
//		}
//		
//		tileSet1.clear();
//	}
	
	

}
