package nex;

import java.util.ArrayList;
import java.util.Iterator;

import jig.Vector;

public class ServerData {

	
	static int p1X;
	static int p1Y;
	int p2X, p2Y;
	static int p1Health, p2Health;
	static int p1Gold, p2Gold;
	int numberOfPlayers;
	volatile boolean playerOne, playerTwo;
    //volatile HashSet<DataOutputStream> playerWriters;
    
	/*
	 * Graph setup
	 */
	static ArrayList<Node> dijkstraGraph = new ArrayList<Node>();
	int wallType;
	static Graph graph;
    
    //Enemy Setup
	static ArrayList<ServerEnemyData> monsters = new ArrayList<ServerEnemyData>();
	int enemyX = (15*65)-867+33;
	int enemyY = (19*65)-967+33;
	
	/*
	 * Map setup
	 */
	public int count = 0;
	static Tile[][] tileSet;
	static int[][] tileSet2;
	public static int row = 0, col = 0;

	
	//INIT
	
	public ServerData(){
		
		ServerEnemyData enemy = new ServerEnemyData((15*65)-867+33, (19*65)-967+33);
		enemy.setTilePosition(new Vector(15,19));
		monsters.add(enemy);

		p1X = 0; p1Y = 0;
		p2X = 0; p2Y = 0;
		numberOfPlayers = 0;
		playerOne = false;
		playerTwo = false;
		//playerWriters = new HashSet<DataOutputStream>();
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
		dijkstraGraph = Dijkstra.runDijkstra(graph, (int) Math.floor(p1X/65), (int) Math.floor(p1Y/65));
	}
	
	
	

}
