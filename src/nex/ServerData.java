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
		

		for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
			ServerEnemyData enemy = e.next();
			int myX = (int) enemy.getMapPosition().getX();		//gets pixel
			int myY = (int) enemy.getMapPosition().getY();		//gets pixel
			
//			System.out.println("enemy position x: " + myX + " y: " + myY);
				
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
	
	
	public static void buildTileSet(){
		
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){		
				tileSet[j][i] = new Tile();
				if(tileSet1.get(((40*i)+j)) == 1){
					tileSet[j][i].setCollision();
					tileSet[j][i].setWeight(100);
				}
			}
		}
		
		tileSet1.clear();
	}
	
	

}
