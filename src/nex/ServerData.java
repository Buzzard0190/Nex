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
		
<<<<<<< HEAD
		ServerEnemyData enemy = new ServerEnemyData((19*65)+22, (19*65)+57);
		enemy.setTilePosition(new Vector(15,19));
=======
		int monsterX = 15;
		int monsterY = 15;
		
		ServerEnemyData enemy = new ServerEnemyData((monsterX*65)-1268+400+33, (monsterY*65)-1268+300+33);
		enemy.setTilePosition(new Vector(monsterX, monsterY));
		enemy.setWorldCoords(new Vector((monsterX*65)+33, (monsterY*65)+33));
>>>>>>> branch 'issue5' of https://github.com/Buzzard0190/Nex.git
		monsters.add(enemy);

		p1X = 0; p1Y = 0;
		p2X = 0; p2Y = 0;
		numberOfPlayers = 0;
		playerOne = false;
		playerTwo = false;
		tileSet = new Tile[40][40];

	}
<<<<<<< HEAD

	
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
=======
>>>>>>> branch 'issue5' of https://github.com/Buzzard0190/Nex.git
	
	public static void updateEnemies(){
		
<<<<<<< HEAD

		for(Iterator<ServerEnemyData> e = monsters.iterator(); e.hasNext();){
			ServerEnemyData enemy = e.next();
			int myX = (int) enemy.getMapPosition().getX();		//gets pixel
			int myY = (int) enemy.getMapPosition().getY();		//gets pixel
=======
		for (ServerEnemyData enemy : monsters)
		{
			
//			enemy.setPosition(new Vector(enemy.getX(),enemy.getY()));
			
			int enemyX = (int) enemy.getWorldCoords().getX(); // gets pixel
			int enemyY = (int) enemy.getWorldCoords().getY(); // gets pixel
			
			int p1x = p1X;
			int p1y = p1Y;
			
			int enemySpeed = 1;
			
			System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
					", p1x = " + p1x + ", p1y = " + p1y);
			
			if(!enemy.getMoving())
			{		
				int tileX = (int) enemy.getTilePosition().getX();
				int tileY = (int) enemy.getTilePosition().getY();
>>>>>>> branch 'issue5' of https://github.com/Buzzard0190/Nex.git
			
<<<<<<< HEAD
//			System.out.println("enemy position x: " + myX + " y: " + myY);
=======
>>>>>>> branch 'issue5' of https://github.com/Buzzard0190/Nex.git
				
				if (enemyX > p1x){ // Left
					System.out.println("Go Left");
					enemy.setDirectionMovement(1);
				}	
				else if (enemyX < p1x){ // Right
					System.out.println("Go Right");
					enemy.setDirectionMovement(2);
				} 
				else if(enemyY > p1y){ // Up
					System.out.println("Go Up");
					enemy.setDirectionMovement(3);
				} 
				else if (enemyY < p1y){ // Down
					System.out.println("Go Down");
					enemy.setDirectionMovement(4);
				}
				
				enemyX = (int) enemy.getMapPosition().getX();		//gets pixel
				enemyY = (int) enemy.getMapPosition().getY();		//gets pixel
				
				if(enemy.getDirectionMovement() == 3 && tileSet[tileX-1][tileY].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX, enemyY-enemySpeed)); // Up
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
					enemy.setMoving(true);
				} 
				else if(enemy.getDirectionMovement() == 4 && tileSet[tileX+1][tileY].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX,enemyY+enemySpeed)); // Down
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
					enemy.setMoving(true);
				} 
				else if(enemy.getDirectionMovement() == 2 && tileSet[tileX][tileY+1].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX+enemySpeed,enemyY)); // Right
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
					enemy.setMoving(true);
				} 
				else if(enemy.getDirectionMovement() == 1 && tileSet[tileX][tileY-1].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX-enemySpeed,enemyY)); // Left
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
					enemy.setMoving(true);
				}
				else if(enemy.getDirectionMovement() == 0){
					enemy.setMapPosition(new Vector(enemyX,enemyY)); // Don't move
				}
				
			}
			else if(enemy.getMoving() && enemy.getPixelCount() < 65)
			{
				enemyX = (int) enemy.getMapPosition().getX();		//gets pixel
				enemyY = (int) enemy.getMapPosition().getY();		//gets pixel
				
				int tileX = (int) enemy.getTilePosition().getX();
				int tileY = (int) enemy.getTilePosition().getY();
				
				if(enemy.getDirectionMovement() == 3 && tileSet[tileX-1][tileY].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX, enemyY-enemySpeed)); // Up
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
				} 
				else if(enemy.getDirectionMovement() == 4 && tileSet[tileX+1][tileY].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX,enemyY+enemySpeed)); // Down
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
				} 
				else if(enemy.getDirectionMovement() == 2 && tileSet[tileX][tileY+1].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX+enemySpeed,enemyY)); // Right
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
				} 
				else if(enemy.getDirectionMovement() == 1 && tileSet[tileX][tileY-1].getCollision() == 0){
					enemy.setMapPosition(new Vector(enemyX-enemySpeed,enemyY)); // Left
					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
				}
				else if(enemy.getDirectionMovement() == 0){
					enemy.setMapPosition(new Vector(enemyX,enemyY)); // Don't move
				}
				
			}
			else if(enemy.getPixelCount() == 65) // Hit the center of a tile
			{
				enemy.setMoving(false);
				enemy.setPixelCount(0);
				
				int tileX = (int) enemy.getTilePosition().getX();
				int tileY = (int) enemy.getTilePosition().getY();
				
				if(enemy.getDirectionMovement() == 1)
				{
					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX()-65, enemy.getWorldCoords().getY()));
					tileSet[tileX][tileY].resetCollision();
					tileSet[tileX][tileY-1].setCollision();
					enemy.setTilePosition(new Vector(tileX, tileY-1));
				}
				else if (enemy.getDirectionMovement() == 2)
				{
					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX()+65, enemy.getWorldCoords().getY()));
					tileSet[tileX][tileY].resetCollision();
					tileSet[tileX][tileY+1].setCollision();
					enemy.setTilePosition(new Vector(tileX, tileY+1));
				}
				else if(enemy.getDirectionMovement() == 3)
				{
					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX(), enemy.getWorldCoords().getY()-65));
					tileSet[tileX][tileY].resetCollision();
					tileSet[tileX-1][tileY].setCollision();
					enemy.setTilePosition(new Vector(tileX-1, tileY));
				}
				else if (enemy.getDirectionMovement() == 4)
				{
					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX(), enemy.getWorldCoords().getY()+65));
					tileSet[tileX][tileY].resetCollision();
					tileSet[tileX+1][tileY].setCollision();
					enemy.setTilePosition(new Vector(tileX+1, tileY));
				}
			}
			else if(enemy.getPixelCount() > 65)
			{
				System.out.println("Uh-oh");
			}
		}	

	}
	
	public static void buildTileSet(){
	
<<<<<<< HEAD
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
=======
	for(int i = 0; i < 40; i++){
		for(int j = 0; j < 40; j++){		
			tileSet[j][i] = new Tile();
			if(tileSet1.get(((40*i)+j)) == 1){
				tileSet[j][i].setCollision();
				tileSet[j][i].setWeight(100);
			}
		}
>>>>>>> branch 'issue5' of https://github.com/Buzzard0190/Nex.git
	}
	
	tileSet1.clear();
}

}
