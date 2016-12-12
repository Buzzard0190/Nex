package nex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import jig.Entity;
import jig.ResourceManager;
import jig.Shape;
import jig.Vector;

public class PlayingState extends BasicGameState {
	
	//---------------------------------------------------//
	//-------------------- Variables --------------------//
	//---------------------------------------------------//
	
	/*
	 * Graph setup
	 */
//	static ArrayList<Node> dijkstraGraph = new ArrayList<Node>();
	int wallType;
//	static Graph graph;

	/*
	 * Map setup
	 */
	public int count = 0;
	private static TiledMap map;
	static Tile[][] tileSet;
	int stoneLayer, collisionLayer;
	public static int row = 0, col = 0;
	
	/*
	 * Player setup
	 */
	static int player1x = 867, player1y = 967, player1Speed = 5;
	boolean playerCollision = false;
	String debugString;
	boolean hmove = false, vmove = false;
	int hspeed = 0, vspeed = 0;
	float playerXPosition = 19;
	float playerYPosition = 19;
	static int otherPlayerX = 0;
	static int otherPlayerY = 0;
	static int playerNumber = 0, numberOfPlayers = 0;
	static int offsetX = 0, offsetY = 0;
	static int playerTileX;
	static int playerTileY;
	
	/*
	 * Enemy setup
	 */
	static public ArrayList<EnemyCharacters> monsters = new ArrayList<EnemyCharacters>();
	private int monsterX;
	private int monsterY;
	private int gRefreshRate = 400;
	private int graphRefresh = gRefreshRate;
	
	/*
	 * Debug booleans
	 */
	private boolean monsterDebug = true;
	private boolean debugDijkstra = true;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Nex nx = (Nex)game;
		
			//--------------------------
			//	Load map built in tiled
			//--------------------------
			
			tileSet = new Tile[40][40];

			try{
				map = new TiledMap("nex/resource/sprites/tiled/Stone_Background.tmx");
			} catch (SlickException e){
				System.out.println("Slick Exception Error: Level 1 map failed to load.");
			}
			
			stoneLayer = map.getLayerIndex("Stone_Background");
//			stoneLayer = map.getLayerIndex("Red_Line");
			collisionLayer = map.getLayerIndex("Collision");
			
			//System.out.println(stoneLayer);
			//System.out.println(collisionLayer);			
			
			initVars();
		
			// Player starting position
			nx.player.setPlayerPosition(new Vector(19,19));
			tileSet[19][19].setCollision();
			
			monsterX = (int)nx.player.getPlayerPosition().getX();
			monsterY = (int)nx.player.getPlayerPosition().getY();
			
			/*
			 * Add and set up enemies
			 */
//			for(int i = 0; i < 3; i++)
//			{
				
				// DEBUG
				//generateMonsterLoc();
					monsterX = 15;
					monsterY = 19;
			
				//-----
			
				EnemyCharacters enemy = new EnemyCharacters(1, (monsterY*65)-player1x+33, (monsterX*65)-player1y+33);
				monsters.add(enemy);
				enemy.setTilePosition(new Vector(monsterX, monsterY));
				enemy.setMapPosition(new Vector((monsterY*65)-player1x+32, (monsterX*65)-player1y+32));
//				enemy.setID(i);
				enemy.setID(0);
				tileSet[monsterX][monsterY].setCollision();
//			}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		container.setSoundOn(true);
		
		Nex nx = (Nex)game;
		
		// ResourceManager.getSound(Nex.MUSIC_RSC).loop();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,Graphics g) throws SlickException {
		
		Nex nx = (Nex)game;
		
		//----- Render -----//
		
		map.render(-player1x,-player1y,stoneLayer);
		map.render(-player1x,-player1y,collisionLayer);
//		map.render(-hspeed,-vspeed,stoneLayer);
		
//		System.out.println(map.getTileId(20, 20, map.getLayerIndex("Stone_Background")));
//		System.out.println(map.);
				
//		for(int i = 0; i < 40; i++)
//		{
//			for(int j = 0; j < 40; j++)
//			{
//				System.out.print(tileSet[i][j]. + " ");
//			}
//			System.out.println();
//		}
		
		nx.player.render(g);
		
		for (EnemyCharacters e : monsters) e.render(g);
		
		if(numberOfPlayers == 2){
			nx.otherPlayer.render(g);
		}
		
//		count = 0;
//		for (Temp t : nx.temp)
//		{
//			if(t.getX() < nx.ScreenWidth+t.getCoarseGrainedWidth()/2 && t.getY() < nx.ScreenHeight+t.getCoarseGrainedHeight()/2
//					&& t.getX() > 0-t.getCoarseGrainedWidth()/2 && t.getY() > 0-t.getCoarseGrainedHeight()/2)
//			{
//				t.render(g);
//				count++; // DEBUG
//			}
//			
//		}
		
		g.drawString("hmove = " + hmove + 
				", vmove = " + vmove + "\nhspeed = " + hspeed + 
				", vspeed = " + vspeed + "\nplayer position = " + nx.player.getPlayerPosition(), 10, 50);
		
		if(monsterDebug)
		{
			int drawY = 190;
			for (EnemyCharacters e : monsters)
			{
				g.drawString("Monster #" + e.getID() + " position = " + e.getTilePosition(), 10, drawY);
				drawY += 20;
			}
		}
		
//		if(debugDijkstra){
//			g.setColor(Color.white);
//			for(Iterator<Node> dijkstraNode = dijkstraGraph.iterator(); dijkstraNode.hasNext();){
//				Node printPath = dijkstraNode.next();
//				g.drawLine(printPath.x*65+33, printPath.y*65+33, printPath.px*65+33, printPath.py*65+33);
//			}
//		}
		g.drawString("hmove = " + hmove + ", vmove = " + vmove + "\nhspeed = " + hspeed + ", vspeed = " + vspeed + "\nplayer position = " + nx.player.getPlayerPosition() + "\nOther player position = " + nx.otherPlayer.getPlayerPosition() + "\nPlayerNumber = " + playerNumber + "\nNumber of Players = " + numberOfPlayers, 10, 50);
		
		//		System.out.println(count + " blocks rendered"); // DEBUG
		
		/*
		 * DEBUG LEVEL COLLISIONS
		 */
		
//		for(int i = 0; i < 40; i++){
//			for(int j = 0; j < 40; j++){
//				//sets a grid
//				if(tileSet[j][i].getCollision() == 1){
//					debugString = "1";
//				} else {
//					debugString = "0";
//				}
////				if(false){
////					g.setColor(Color.orange);
////					g.drawString(debugString, j*32, i*32);
////				}
////				if(true){
////					g.setColor(Color.black);
////					g.drawRect(j*65, i*65, 65, 65);
////				}
//				if(true){
//					g.setColor(Color.blue);
//					g.drawString(String.valueOf(tileSet[j][i].getWeight()), j*65, i*65+65);
//				}
//			}
//		}
	}
	
	public void shift(Nex nx, int hspeed, int vspeed)
	{
	
//		for (EnemyCharacters e : monsters)
//		{
//			e.setX(e.getX()-hspeed);
//			e.setY(e.getY()-vspeed);
//		}
		
		offsetX += hspeed;
		offsetY += vspeed;
		player1x += hspeed;
		player1y += vspeed;
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		Input input = container.getInput();		// For key presses

		Nex nx = (Nex)game;
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*------------------------------------ Update Objects ------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		// dg.player.update(delta);
	
		/*--------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- Collisions -----------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		
		
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- Moving Player ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		if(Math.abs(player1x) % 65 == 22 || Math.abs(player1x) % 65 == 43)
		{
			hmove = false;
			hspeed = 0;
			
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
		}
		
		if(Math.abs(player1y) % 65 == 57 || Math.abs(player1y) % 65 == 8)
		{
			vmove = false;
			vspeed = 0;
			
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
		}
		
		
		if (input.isKeyDown(Input.KEY_A) && (hmove == false || hspeed > 0) 
				&& vmove == false && !input.isKeyDown(Input.KEY_D)
				&& tileSet[row][col-1].getCollision() == 0) {	// Left Key
			hmove = true;
			hspeed = -player1Speed;
			playerXPosition = nx.player.getPlayerPosition().getX();
			playerYPosition = nx.player.getPlayerPosition().getY();
			nx.player.setPlayerPosition(new Vector(playerXPosition,playerYPosition-1));
		}
		else if (input.isKeyDown(Input.KEY_D) && (hmove == false || hspeed < 0) 
				&& vmove == false && !input.isKeyDown(Input.KEY_A)
				&& tileSet[row][col+1].getCollision() == 0) {
			hmove = true;
			hspeed = player1Speed;
			playerXPosition = nx.player.getPlayerPosition().getX();
			playerYPosition = nx.player.getPlayerPosition().getY();
			nx.player.setPlayerPosition(new Vector(playerXPosition,playerYPosition+1));
		}
		else if (input.isKeyDown(Input.KEY_W) && hmove == false 
				&& (vmove == false || vspeed > 0) && !input.isKeyDown(Input.KEY_S)
				&& tileSet[row-1][col].getCollision() == 0) {
			vmove = true;
			vspeed = -player1Speed;
			playerXPosition = nx.player.getPlayerPosition().getX();
			playerYPosition = nx.player.getPlayerPosition().getY();
			nx.player.setPlayerPosition(new Vector(playerXPosition-1,playerYPosition));
		}
		else if (input.isKeyDown(Input.KEY_S) && hmove == false 
				&& (vmove == false || vspeed < 0) && !input.isKeyDown(Input.KEY_W)
				&& tileSet[row+1][col].getCollision() == 0) {
			vmove = true;
			vspeed = player1Speed;
			playerXPosition = nx.player.getPlayerPosition().getX();
			playerYPosition = nx.player.getPlayerPosition().getY();
			nx.player.setPlayerPosition(new Vector(playerXPosition+1,playerYPosition));
		}

		
		if(hmove || vmove)
		{
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
			
//			System.out.println(row + " " + col + " " + playerXPosition + " " + playerYPosition);
			tileSet[row][col].setCollision();
			tileSet[(int)playerXPosition][(int)playerYPosition].resetCollision();
			shift(nx, hspeed, vspeed);
			
			// Perhaps? Yes!
//			if(graphRefresh <= 0){
//				buildGraph();
//				graphRefresh = gRefreshRate;
//			} else {
//				graphRefresh -= delta;
//			}
		}

		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- World Panning ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*----------------------------------------------- Cheats -------------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		if(input.isKeyPressed(Input.KEY_1))
		{
			if(monsterDebug)
				monsterDebug = false;
			else
				monsterDebug = true;
		}
		
//		if(input.isKeyPressed(Input.KEY_2))
//		{
//			if(debugDijkstra)
//				debugDijkstra = false;
//			else
//				debugDijkstra = true;
//		}
		
//		//------------------------------------------------------------------------
//		//Monsters follow path
//		//------------------------------------------------------------------------
//
//		for(Iterator<Node> n = dijkstraGraph.iterator(); n.hasNext();){
//			Node cycleNode = n.next();
//			
//			for(Iterator<EnemyCharacters> e = monsters.iterator(); e.hasNext();){
//			
//				EnemyCharacters enemy = e.next();			
//
//				int myX = (int) enemy.getX();
//				int myY = (int) enemy.getX();
//				int futureX = (int) enemy.getFutureTile().getX()*65+33;
//				int futureY = (int) enemy.getFutureTile().getY()*65+33;
//				
//
//				System.out.println(myX);
//				float vx, vy;
//				
//				if(enemy.health <= 0){
//					e.remove();
//				}
//				 
//				//------------------------------------------------------------------------
//				//Check to find a vector for character to move. Uses dijkstra graph
//				//------------------------------------------------------------------------
//				if(cycleNode.x == myX && cycleNode.y == myY && enemy.inCombat == false){
//					
//					if((cycleNode.px - enemy.getTilePosition().getX()) == 0){
//						vx = .0f;
//					} else if ((cycleNode.px - enemy.getTilePosition().getX()) > 0){
//						vx = .2f;
//					} else {
//						vx = -.2f;
//					}
//					
//					
//					if((cycleNode.py - enemy.getTilePosition().getY()) == 0){
//						vy = .0f;
//					} else if ((cycleNode.py - enemy.getTilePosition().getY()) > 0){
//						vy = .2f;
//					} else {
//						vy = -.2f;
//					}
//					
//					if(vx != 0){
//						vy = 0;
//					} else if(vy != 0){
//						vx = 0;
//					}
//					
//					enemy.setVelocity(vx, vy);
//					enemy.update(delta);
//				} else if (enemy.inCombat == true){
//					
//					//-------------------------------------------------------------
//					//If the enemy is in combat it will set the velocity to 0
//					//-------------------------------------------------------------
//					enemy.setVelocity(.0f, .0f);
//					enemy.update(delta);
//				}
//			}
//		}
				
		/*--------------------------------------------------------------------------------------------------------*/
		/*---------------------------------------- Update other Player -------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
	
		float otherX = otherPlayerX - player1x + nx.ScreenWidth/2;
		float otherY = otherPlayerY - player1y + nx.ScreenHeight/2;
		
		nx.otherPlayer.setPlayerPosition(new Vector(otherX, otherY));
		nx.otherPlayer.setPosition(new Vector(otherX, otherY));
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- Enemy Movement --------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		for (EnemyCharacters e : monsters)
		{
			int playerX = (int)nx.player.getPlayerPosition().getX();
			int playerY = (int)nx.player.getPlayerPosition().getY();
			int enemyX = (int)e.getTilePosition().getX();
			int enemyY = (int)e.getTilePosition().getY();
			
//			System.out.println("playerX = " + playerX + ", playerY = " + playerY);
//			
//			if(playerY >= enemyY) // Player is to the left of the enemy
//			{
//				e.setX(e.getX());
//				e.setY(e.getY()+3);
//			}
			
//			if()
		}
		
	
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
		
		
//		dijkstraGraph = Dijkstra.runDijkstra(graph, row, col);

//		System.out.println(dijkstraGraph.size());
//		for (Node g : dijkstraGraph)
//		{
//			System.out.println("x = " + g.x + ", y = " + g.y + ", dist = " + g.dist);
//		}
//	}
		
	//Finds a place to put a monster on the game board
	public void generateMonsterLoc(){
		
		while(tileSet[monsterX][monsterY].getCollision() != 0)
		{
			Random r = new Random();
			int xory = r.nextInt(2);
			
			if(xory == 0){
				//on x axis
				r = new Random();
				int topOrBottom = r.nextInt(2);
				int tileLoc = r.nextInt(37);
				
				if(tileLoc == 0)
					tileLoc += 1;
				
				monsterY = tileLoc;
				if(topOrBottom == 0){
					//top of grid
					monsterX = 1;
				} else {
					//bottom of grid
					monsterX = 33;
				}
				
			} else {
				//on y axis
				r = new Random();
				int leftOrRight = r.nextInt(2);
				int tileLoc = r.nextInt(37);
				
				if(tileLoc == 0)
					tileLoc += 1;
				
				monsterX = tileLoc;
				if(leftOrRight == 0){
					//left of grid
					monsterY = 1;
				} else {
					//right of grid
					monsterY = 35;
				}
			}
		}
	}
	
	public static void initVars(){
		
		monsters = new ArrayList<EnemyCharacters>();
		
		//------------------------------------------------------------------------------
		//cycle through collisions layer and mark any tiles with a collision as such
		//------------------------------------------------------------------------------
				
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){
				if(map.getTileId(i, j, map.getLayerIndex("Collision")) > 0){
					tileSet[j][i] = new Tile();
					tileSet[j][i].setCollision();
					tileSet[j][i].setWeight(100);
				} else {
					tileSet[j][i] = new Tile();
					tileSet[j][i].setWeight(1);
				}
			}
		}
		
		playerTileX = 19;
		playerTileY = 19;
		
//		buildGraph();
	}
	
	public static void updateP1(int x, int y){
//		row = x;
//		col = y;
	}
	
	private void gameOver(Nex nx){
		
		nx.enterState(Nex.GAMEOVERSTATE);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
