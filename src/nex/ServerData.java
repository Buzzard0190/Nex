package nex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jig.Vector;

public class ServerData {

	
	static int p1X;
	static int p1Y;
	int p2X, p2Y;
	int numberOfPlayers;
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
		
		ServerEnemyData enemy = new ServerEnemyData((10*65)+33, (10*65)+33);
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
		int xValue = (int) Math.floor((p1X+368)/65); int yValue = (int) Math.floor((p1Y+268)/65);
		dijkstraGraph = Dijkstra.runDijkstra(graph, xValue, yValue);
		updateEnemies();
	}
	
	public static void updateEnemies(){
		
		
		for(Iterator<Node> n = dijkstraGraph.iterator(); n.hasNext();){
			Node cycleNode = n.next();
			for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
				ServerEnemyData enemy = e.next();
				int myX = (int) enemy.getMapPosition().getX();		//gets pixel
				int myY = (int) enemy.getMapPosition().getY();		//gets pixel
				int xCenter = (cycleNode.x*65)+33;
				int yCenter = (cycleNode.y*65)+33;
				
				if(xCenter == myX && yCenter == myY){
					//North
					if((cycleNode.px*65+33)-myX == 0 && (cycleNode.py*65+33)-myY > 0){
						System.out.println("Go North");
						enemy.directionMovement = 1;
					} else if ((cycleNode.px*65+33)-myX > 0 && (cycleNode.py*65+33)-myY == 0){ //East
						System.out.println("Go East");

						enemy.directionMovement = 2;
					} else if ((cycleNode.px*65+33)-myX == 0 && (cycleNode.py*65+33)-myY < 0){ //South
						System.out.println("Go South");

						enemy.directionMovement = 3;
					} else if ((cycleNode.px*65+33)-myX < 0 && (cycleNode.py*65+33)-myY == 0){ //West
						System.out.println("Go West");

						enemy.directionMovement = 4;
					}									
				}			
			}
		}	
		
		for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
			ServerEnemyData enemy = e.next();
			int myX = (int) enemy.getMapPosition().getX();		//gets pixel
			int myY = (int) enemy.getMapPosition().getY();		//gets pixel
			
			if(enemy.directionMovement == 1){
				enemy.setMapPosition(new Vector(myX,myY-1));
			} else if(enemy.directionMovement == 2){
				enemy.setMapPosition(new Vector(myX+1,myY));
			} else if(enemy.directionMovement == 3){
				enemy.setMapPosition(new Vector(myX,myY+1));
			} else if(enemy.directionMovement == 4){
				enemy.setMapPosition(new Vector(myX-1,myY));
			}
		}
		System.out.println("before");
		int print = 0;
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){		
				Node n = dijkstraGraph.get(print);
				
				if( (int) Math.floor((p1X/65)+303) == i &&  (int) Math.floor((p1Y/65)+203) == j){
					System.out.print("O");
				} else if(n.x == n.px && n.y > n.py){
					System.out.print("^");
				} else if (n.x == n.px && n.y < n.py){
					System.out.print("v");
				} else if (n.x > n.px && n.y == n.py){
					System.out.print("<");
				} else if (n.x < n.px && n.y == n.py){
					System.out.print(">");
				} else {
					System.out.print("E");
				}
				print++;
			}
			System.out.println("");
		}
		System.out.println("after");
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
