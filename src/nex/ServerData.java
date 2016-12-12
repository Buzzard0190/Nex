package nex;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;

public class ServerData {

	
	static int p1X;
	static int p1Y;
	int p2X, p2Y;
	int numberOfPlayers;
	volatile boolean playerOne, playerTwo;
    volatile HashSet<DataOutputStream> playerWriters;
    
	/*
	 * Graph setup
	 */
	static ArrayList<Node> dijkstraGraph = new ArrayList<Node>();
	int wallType;
	static Graph graph;
    
    //Enemy Setup
//	static ArrayList<EnemyCharacters> monsters = new ArrayList<EnemyCharacters>();
	int enemyX = (15*65)-867+33;
	int enemyY = (19*65)-967+33;
	
	/*
	 * Map setup
	 */
	public int count = 0;
	private static TiledMap map;
	static Tile[][] tileSet;
	int stoneLayer, collisionLayer;
	public static int row = 0, col = 0;

	
	public ServerData(){
		
		////////change this!!!!!!!!!!
//		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
//		EnemyCharacters enemy = new EnemyCharacters(1, (15*65)-867+33, (19*65)-967+33);
//		monsters.add(enemy);
		
		//--------------------------
		//	Load map built in tiled
		//--------------------------
		
		tileSet = new Tile[40][40];

		try{
			map = new TiledMap("nex/resource/sprites/tiled/Stone_Background.tmx");
		} catch (SlickException e){
			System.out.println("Slick Exception Error: Level 1 map failed to load.");
		}
		
//		collisionLayer = map.getLayerIndex("Collision");

//		tileSet[19][19].setCollision();

		p1X = 0; p1Y = 0;
		p2X = 0; p2Y = 0;
		numberOfPlayers = 0;
		playerOne = false;
		playerTwo = false;
		playerWriters = new HashSet<DataOutputStream>();
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

//		System.out.println(dijkstraGraph.size());
//		for (Node g : dijkstraGraph)
//		{
//			System.out.println("x = " + g.x + ", y = " + g.y + ", dist = " + g.dist);
//		}
	}
		

}
