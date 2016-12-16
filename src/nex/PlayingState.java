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
	
	// Player direction
	public static final int UP 		= 0;
	public static final int DOWN 	= 1;
	public static final int LEFT 	= 2;
	public static final int RIGHT	= 3;
	
	// Player status
	public static final int MOVING 		= 4;
	public static final int ATK1 		= 5;
	public static final int ATK2 		= 6;
	public static final int IDLE 		= 7;
	public static final int BLOCKING 	= 8;	// This is to stop the blocking animation when the cleric is hit.	
	public static final int DEAD 		= 9;
	
	// Gate orientation
	public static final int VERT = 0;
	public static final int HORI = 1;
	
	// Chest Status
	public static final int CLOSED 	= 0;
	public static final int OPEN 	= 1;
	public static final int EMPTY 	= 2;
	
	private float xVelocity = 0; 
	private float yVelocity = 0;
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
	static Tile[][] tileSet, tileSetGates, tileSetChests;
	int stoneLayer, collisionLayer, gateLayer, chestLayer;
	

//	volatile static Tile[][] tileSet;
//	static Tile[][] tileSetGates;

	public static int row = 0, col = 0;
	
	/*
	 * Player setup
	 */
	static int player1x = 19*65+33, player1y = 19*65+33, player1Speed = 5;
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
	private boolean monsterDebug = false;
	private boolean debugDijkstra = false;
	
	
	// Used to rotate the player to the mouse.
	private int mouseX, mouseY;
	private int angle;
	private int playerDir;
	private boolean canInteract;
	
	private int playerStatus;
	
	// Pointers to hold objects that can be interacted with when the player is close enough.
	Gate gatePointer;
	Chest chestPointer;
	
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Nex nx = (Nex)game;
		
			//--------------------------
			//	Load map built in tiled
			//--------------------------
			
			tileSet = new Tile[40][40];
			tileSetGates = new Tile[40][40];
			tileSetChests = new Tile[40][40];

			try{
				map = new TiledMap("nex/resource/sprites/tiled/Stone_Background.tmx");
			} catch (SlickException e){
				System.out.println("Slick Exception Error: Level 1 map failed to load.");
			}
			
			stoneLayer = map.getLayerIndex("Stone_Background");
//			stoneLayer = map.getLayerIndex("Red_Line");
//			collisionLayer = map.getLayerIndex("Collision");
			gateLayer = map.getLayerIndex("Gate_Collision");
			chestLayer = map.getLayerIndex("Chest_Collision");
			
			//System.out.println(stoneLayer);
			System.out.println(collisionLayer);			
			
			initVars();
		
			// Player starting position
			nx.player.setPlayerPosition(new Vector(19,19));
			tileSet[19][19].setCollision();
			
//			monsterX = (int)nx.player.getPlayerPosition().getX();
//			monsterY = (int)nx.player.getPlayerPosition().getY();
			
			/*
			 * Add and set up enemies
			 */
//			for(int i = 0; i < 2; i++)
//			{
//				
//				// DEBUG
//				generateMonsterLoc();
				monsterX = 15;
				monsterY = 15;
			
				//-----
				int offset = 0;
				for(int i = 0; i < 2; i++){
					int monsterX = 15;
					int monsterY = 15;
					
					EnemyCharacters enemy = new EnemyCharacters(1,(monsterX*65)-1268+400+33, (monsterY*65)-1268+300+33);
					enemy.setTilePosition(new Vector(monsterX, monsterY));
					enemy.setWorldCoords(new Vector((monsterX*65)+33, (monsterY*65)+33));
					enemy.setID(1);
					monsters.add(enemy);
					tileSet[monsterX][monsterY].setCollision();
					offset+=5;
				}
			
				
//			}
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		container.setSoundOn(true);
		
		Nex nx = (Nex)game;
		
		// Add gates
		nx.GateArray.add(new Gate(400 + (65*20), 300, VERT, new Vector(39, 19)));
		nx.GateArray.add(new Gate(400 + (65*20), 300+65, VERT, new Vector(39, 20)));
		nx.GateArray.add(new Gate(400 + (65*20), 300+(65*2), VERT, new Vector(39, 21)));
		
		// Add chests
		nx.ChestArray.add(new Chest(400, 300 - 33, UP));
		nx.ChestArray.add(new Chest(400, 300 - (33 + 65 * 5), DOWN));
		nx.ChestArray.add(new Chest(400 - (65 * 2) - 33, 300 - (65 * 3), RIGHT));
		nx.ChestArray.add(new Chest(400 + (65 * 2) + 33, 300 - (65 * 3), LEFT));
		
		// ResourceManager.getSound(Nex.MUSIC_RSC).loop();
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,Graphics g) throws SlickException {
		
		Nex nx = (Nex)game;
		
		//----- Render -----//
		
		map.render(-player1x+400,-player1y+300,stoneLayer);
		map.render(-player1x+400,-player1y+300,collisionLayer);
		// map.render(-player1x,-player1y,gateLayer);
		
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
		
		
		
		// DEBUG
		nx.block.render(g);
		
		for (Gate gt : nx.GateArray){
			gt.render(g);
		}
		
		for (Chest c : nx.ChestArray){
			c.render(g);
		}
		
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
				g.drawString("Monster #" + e.getID() + " position = " + e.getWorldCoords() + ", tilePosition = " + e.getTilePosition(), 10, drawY);
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
		g.drawString("hmove = " + hmove + ", vmove = " + vmove + "\nhspeed = " + hspeed + ", vspeed = " + vspeed + "\nplayer position = " + 
				nx.player.getPlayerPosition() + "\nOther player position = " + nx.otherPlayer.getPlayerPosition() + "\nPlayerNumber = " + 
				playerNumber + "\nNumber of Players = " + numberOfPlayers, 10, 50);
		g.drawString("mouseX = " + mouseX + " mouseY = " + mouseY, 500, 50);
		g.drawString("angle = " + angle, 500, 65);
		g.drawString("Player status = " + nx.player.getStatus(), 500, 80);
		g.drawString("Player direction = " + nx.player.getDir(), 500, 95);
		g.drawString("Player Health = " + nx.player.getHealth(), 500, 110);
		g.drawString("Player Gold = " + nx.player.getGold(), 500, 125);
		g.drawString("Player Tile X = " + nx.player.getPlayerPosition().getX() + " Player tile Y = " + nx.player.getPlayerPosition().getY(), 400, 140);
		
		
		if(canInteract)
			g.drawString("E: Interact", nx.player.getX() - 50, nx.player.getY() - 50);
		
		
		
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
	
		for (EnemyCharacters e : monsters)
		{
			e.setX(e.getX()-hspeed);
			e.setY(e.getY()-vspeed);
		}
		
		for (Gate gt : nx.GateArray){
			gt.setX(gt.getX()-hspeed);
			gt.setY(gt.getY()-vspeed);
		}
		
		for (Chest c : nx.ChestArray){
			c.setX(c.getX()-hspeed);
			c.setY(c.getY()-vspeed);
		}
		// Debug
		nx.block.setX(nx.block.getX()-hspeed);
		nx.block.setY(nx.block.getY()-vspeed);
		
		offsetX += hspeed;
		offsetY += vspeed;

		player1x += hspeed;
		player1y += vspeed;
		
	}
	
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {

		Input input = container.getInput();		// For key presses

		Nex nx = (Nex)game;
		
		// ----- Rotating player based on mouse position. -----//
		mouseX = input.getMouseX();
		mouseY = input.getMouseY();
		
		angle = (int)(Math.atan2((mouseY - nx.player.getY()), (mouseX - nx.player.getX())) * 180/Math.PI);
		
		// Converts angle to be 0 to 360 instead of -180 to 0 to 180. 
		if(angle > 0)
			angle = 360 - angle;
		else
			angle = -angle;
		
		playerDir = nx.player.getDir();
		
		if(nx.player.getStatus() == IDLE){
			if (angle > 45 && angle <= 135 && playerDir != UP){
				nx.player.changeIdleDir(UP);
			}
			else if (angle > 135 && angle <= 225 && playerDir != LEFT){
				nx.player.changeIdleDir(LEFT);
			}
			else if (angle > 225 && angle <= 315 && playerDir != DOWN){
				nx.player.changeIdleDir(DOWN);
			}
			else if (angle >= 0 && angle <= 45 || angle > 315 && angle <= 360 && playerDir != RIGHT){
				nx.player.changeIdleDir(RIGHT);
			}
		}
		
			
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*------------------------------------------- Update Objects ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		nx.player.update(delta);
		
//		for(EnemyCharacters e : monsters){
//			e.update(delta);
//		}
		
	
		/*--------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- Collisions -----------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- Interaction ----------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		canInteract = false;
		gatePointer = null; chestPointer = null;
		
		for(Gate gt : nx.GateArray){
			if(Math.abs(gt.getX() - nx.player.getX()) <= 100 && (Math.abs(gt.getY() - nx.player.getY()) <= 100) && gt.isActive()){
				gatePointer = gt;
				canInteract = true;
			}
					
		}
		
		for(Chest c : nx.ChestArray){
			if(Math.abs(c.getX() - nx.player.getX()) <= 100 && (Math.abs(c.getY() - nx.player.getY()) <= 100) && c.getStatus() != EMPTY){
				chestPointer = c;
				canInteract = true;
			}
		}
		
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- Moving Player ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		// Used to reduce the number of calls to getStatus().
		playerStatus = nx.player.getStatus();
		
		
//		System.out.println("player1x = " + player1x + ", player1y = " + player1y);
		if(Math.abs(player1x) % 65 == 33)
		{
			hmove = false;
			hspeed = 0;
			
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
		}
		
		if(Math.abs(player1y) % 65 == 33)
		{
			vmove = false;
			vspeed = 0;
			
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
		}
		
		if(playerStatus != DEAD && (playerStatus == MOVING || playerStatus == IDLE)){
			// Running LEFT
			if (input.isKeyDown(Input.KEY_A) && (hmove == false || hspeed > 0) 
					&& vmove == false && !input.isKeyDown(Input.KEY_D)
					&& tileSet[row][col-1].getCollision() == 0
					&& tileSetGates[row][col-1].getCollision() == 0
					&& tileSetChests[row][col-1].getCollision() == 0) {	// Left Key
				hmove = true;
				hspeed = -player1Speed;
				playerXPosition = nx.player.getPlayerPosition().getX();
				playerYPosition = nx.player.getPlayerPosition().getY();
				nx.player.setPlayerPosition(new Vector(playerXPosition,playerYPosition-1));
				
				nx.player.runDir(LEFT);
			}
			// Running RIGHT
			else if (input.isKeyDown(Input.KEY_D) && (hmove == false || hspeed < 0) 
					&& vmove == false && !input.isKeyDown(Input.KEY_A)
					&& tileSet[row][col+1].getCollision() == 0
					&& tileSetGates[row][col+1].getCollision() == 0
					&& tileSetChests[row][col+1].getCollision() == 0) {
				hmove = true;
				hspeed = player1Speed;
				playerXPosition = nx.player.getPlayerPosition().getX();
				playerYPosition = nx.player.getPlayerPosition().getY();
				nx.player.setPlayerPosition(new Vector(playerXPosition,playerYPosition+1));
				
				nx.player.runDir(RIGHT);
			}
			// Running UP
			else if (input.isKeyDown(Input.KEY_W) && hmove == false 
					&& (vmove == false || vspeed > 0) && !input.isKeyDown(Input.KEY_S)
					&& tileSet[row-1][col].getCollision() == 0
					&& tileSetGates[row-1][col].getCollision() == 0
					&& tileSetChests[row-1][col].getCollision() == 0) {
				vmove = true;
				vspeed = -player1Speed;
				playerXPosition = nx.player.getPlayerPosition().getX();
				playerYPosition = nx.player.getPlayerPosition().getY();
				nx.player.setPlayerPosition(new Vector(playerXPosition-1,playerYPosition));
				
				nx.player.runDir(UP);
			}
			// Running DOWN
			else if (input.isKeyDown(Input.KEY_S) && hmove == false 
					&& (vmove == false || vspeed < 0) && !input.isKeyDown(Input.KEY_W)
					&& tileSet[row+1][col].getCollision() == 0
					&& tileSetGates[row+1][col].getCollision() == 0
					&& tileSetChests[row+1][col].getCollision() == 0) {
				vmove = true;
				vspeed = player1Speed;
				playerXPosition = nx.player.getPlayerPosition().getX();
				playerYPosition = nx.player.getPlayerPosition().getY();
				nx.player.setPlayerPosition(new Vector(playerXPosition+1,playerYPosition));
				
				nx.player.runDir(DOWN);
			}
		}

		if(input.isKeyPressed(Input.KEY_E) && canInteract){
			
			// Interact with gates.
			if(gatePointer != null){
				
				// Activates all nearby gates to animate simultaneously.
				for(Gate gt : nx.GateArray){
					if(gt != gatePointer && (Math.abs(gt.getX() - gatePointer.getX()) <= 150) 
							&& (Math.abs(gt.getY() - gatePointer.getY()) <= 150) && gt.isActive()){
						gt.gateAnim();
						// Remove gate collisions of nearby gates when interacted with.
						tileSetGates[(int) gt.getTileLocation().getY()][(int) gt.getTileLocation().getX()].resetCollision();
					}
				}
				gatePointer.gateAnim();
				// Remove gate collisions of nearby gates when interacted with.
				tileSetGates[(int) gatePointer.getTileLocation().getY()][(int) gatePointer.getTileLocation().getX()].resetCollision();
			}
			
			// Interact with chests.
			if(chestPointer != null){
				nx.player.addGold(chestPointer.interact());
			}
		}
		
		
		// Left Mouse = Atk1
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			
			nx.player.attack(1);
			
			// XXX x = vertical, y = horizontal
			
			// check direction
			if(nx.player.getStatus() == ATK1){
				switch(nx.player.getDir()){
					case UP:
						attemptAttack(new Vector(nx.player.getPlayerPosition().getX() - 1, nx.player.getPlayerPosition().getY()), nx.player.getAttackStrength());
						break;
						
					case DOWN:
						attemptAttack(new Vector(nx.player.getPlayerPosition().getX() + 1, nx.player.getPlayerPosition().getY()), nx.player.getAttackStrength());
						break;
						
					case LEFT:
						attemptAttack(new Vector(nx.player.getPlayerPosition().getX(), nx.player.getPlayerPosition().getY() - 1), nx.player.getAttackStrength());
						break;
						
					case RIGHT:
						attemptAttack(new Vector(nx.player.getPlayerPosition().getX(), nx.player.getPlayerPosition().getY() + 1), nx.player.getAttackStrength());
						break;
			
				}
			}
			
			
		}
		// Right Mouse = Atk2
		else if(input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)){
				nx.player.attack(2);
		}
		
		// Removes the shield holding sprite when the player lets go as mouse2 as the Cleric.
		if(!input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON) && nx.player.getStatus() == ATK2){
			nx.player.removeAtk2();
		}
		
		// DEBUG: Giving the player damage.
		if(input.isKeyPressed(Input.KEY_1)){
			nx.player.takeDamage(100);
		}


		if((hmove || vmove) && nx.player.getStatus() != DEAD){
			/* XXX THIS MAY NEED TO BE IN THE WASD KEY PRESSES XXX */
			if(nx.player.getStatus() != MOVING)
				nx.player.setStatus(MOVING);
			
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

		
		
		// Update player status to IDLE if they are not moving.
		if(!hmove && !vmove){
			
			if(nx.player.getStatus() == MOVING){
				nx.player.stopRunning();
			}
			
			
			if(nx.player.getStatus() != ATK1 && nx.player.getStatus() != ATK2 && nx.player.getStatus() != BLOCKING && nx.player.getStatus() != DEAD)
				nx.player.setStatus(IDLE);
			
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
//		for (EnemyCharacters enemy : monsters)
//		{
//			
////			enemy.setPosition(new Vector(enemy.getX(),enemy.getY()));
//			
//			int enemyX = (int) enemy.getWorldCoords().getX(); // gets pixel
//			int enemyY = (int) enemy.getWorldCoords().getY(); // gets pixel
//			
//			int p1x = player1x;
//			int p1y = player1y;
//			
//			int enemySpeed = 1;
//			
////			System.out.println("enemyX = " + enemyX + ", enemyY = " + enemyY + 
////					", p1x = " + p1x + ", p1y = " + p1y);
//			
//			if(!enemy.getMoving())
//			{		
//				int tileX = (int) enemy.getTilePosition().getX();
//				int tileY = (int) enemy.getTilePosition().getY();
//				
//				System.out.println("tileX = " + tileX + ", tileY" + tileY);
//				
//				if (enemyX > p1x){ // Left
//					System.out.println("Go Left");
//					enemy.setDirectionMovement(1);
//				}	
//				else if (enemyX < p1x){ // Right
//					System.out.println("Go Right");
//					enemy.setDirectionMovement(2);
//				} 
//				else if(enemyY > p1y){ // Up
//					System.out.println("Go Up");
//					enemy.setDirectionMovement(3);
//				} 
//				else if (enemyY < p1y){ // Down
//					System.out.println("Go Down");
//					enemy.setDirectionMovement(4);
//				}
//				
//				enemyX = (int) enemy.getPosition().getX();		//gets pixel
//				enemyY = (int) enemy.getPosition().getY();		//gets pixel
//				
//				if(enemy.getDirectionMovement() == 3 && tileSet[tileX-1][tileY].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX, enemyY-enemySpeed)); // Up
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//					enemy.setMoving(true);
//				} 
//				else if(enemy.getDirectionMovement() == 4 && tileSet[tileX+1][tileY].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX,enemyY+enemySpeed)); // Down
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//					enemy.setMoving(true);
//				} 
//				else if(enemy.getDirectionMovement() == 2 && tileSet[tileX][tileY+1].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX+enemySpeed,enemyY)); // Right
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//					enemy.setMoving(true);
//				} 
//				else if(enemy.getDirectionMovement() == 1 && tileSet[tileX][tileY-1].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX-enemySpeed,enemyY)); // Left
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//					enemy.setMoving(true);
//				}
//				else if(enemy.getDirectionMovement() == 0){
//					enemy.setPosition(new Vector(enemyX,enemyY)); // Don't move
//				}
//				
//			}
//			else if(enemy.getMoving() && enemy.getPixelCount() < 65)
//			{
//				enemyX = (int) enemy.getPosition().getX();		//gets pixel
//				enemyY = (int) enemy.getPosition().getY();		//gets pixel
//				
//				int tileX = (int) enemy.getTilePosition().getX();
//				int tileY = (int) enemy.getTilePosition().getY();
//				
//				if(enemy.getDirectionMovement() == 3 && tileSet[tileX-1][tileY].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX, enemyY-enemySpeed)); // Up
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//				} 
//				else if(enemy.getDirectionMovement() == 4 && tileSet[tileX+1][tileY].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX,enemyY+enemySpeed)); // Down
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//				} 
//				else if(enemy.getDirectionMovement() == 2 && tileSet[tileX][tileY+1].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX+enemySpeed,enemyY)); // Right
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//				} 
//				else if(enemy.getDirectionMovement() == 1 && tileSet[tileX][tileY-1].getCollision() == 0){
//					enemy.setPosition(new Vector(enemyX-enemySpeed,enemyY)); // Left
//					enemy.setPixelCount(enemy.getPixelCount()+enemySpeed);
//				}
//				else if(enemy.getDirectionMovement() == 0){
//					enemy.setPosition(new Vector(enemyX,enemyY)); // Don't move
//				}
//				
//			}
//			else if(enemy.getPixelCount() == 65) // Hit the center of a tile
//			{
//				enemy.setMoving(false);
//				enemy.setPixelCount(0);
//				
//				int tileX = (int) enemy.getTilePosition().getX();
//				int tileY = (int) enemy.getTilePosition().getY();
//				
//				if(enemy.getDirectionMovement() == 1)
//				{
//					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX()-65, enemy.getWorldCoords().getY()));
//					tileSet[tileX][tileY].resetCollision();
//					tileSet[tileX][tileY-1].setCollision();
//					enemy.setTilePosition(new Vector(tileX, tileY-1));
//				}
//				else if (enemy.getDirectionMovement() == 2)
//				{
//					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX()+65, enemy.getWorldCoords().getY()));
//					tileSet[tileX][tileY].resetCollision();
//					tileSet[tileX][tileY+1].setCollision();
//					enemy.setTilePosition(new Vector(tileX, tileY+1));
//				}
//				else if(enemy.getDirectionMovement() == 3)
//				{
//					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX(), enemy.getWorldCoords().getY()-65));
//					tileSet[tileX][tileY].resetCollision();
//					tileSet[tileX-1][tileY].setCollision();
//					enemy.setTilePosition(new Vector(tileX-1, tileY));
//				}
//				else if (enemy.getDirectionMovement() == 4)
//				{
//					enemy.setWorldCoords(new Vector(enemy.getWorldCoords().getX(), enemy.getWorldCoords().getY()+65));
//					tileSet[tileX][tileY].resetCollision();
//					tileSet[tileX+1][tileY].setCollision();
//					enemy.setTilePosition(new Vector(tileX+1, tileY));
//				}
//			}
//			else if(enemy.getPixelCount() > 65)
//			{
//				System.out.println("Uh-oh");
//			}
//		}
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
	
	public void attemptAttack(final Vector attackPosition, final int damage){
		
		System.out.println("XXX attackPosition = " + attackPosition);
		
		for(EnemyCharacters e : monsters){
			
			System.out.println("Enemy " + e + " is at pos: " + e.getTilePosition());
			
			System.out.println("Check = " + (e.getTilePosition().getX() == attackPosition.getX()));
			if((e.getTilePosition().getX() == attackPosition.getX()) && (e.getTilePosition().getY() == attackPosition.getY())){
				System.out.println("Hit em");
				e.doDamage(damage);
			}
		}
		
	}
		
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
		
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){
				if(map.getTileId(i, j, map.getLayerIndex("Gate_Collision")) > 0){
					tileSetGates[j][i] = new Tile();
					tileSetGates[j][i].setCollision();
					tileSetGates[j][i].setWeight(100);
				} else {
					tileSetGates[j][i] = new Tile();
					tileSetGates[j][i].setWeight(1);
				}
			}
		}

		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){
				if(map.getTileId(i, j, map.getLayerIndex("Chest_Collision")) > 0){
					tileSetChests[j][i] = new Tile();
					tileSetChests[j][i].setCollision();
					tileSetChests[j][i].setWeight(100);
				} else {
					tileSetChests[j][i] = new Tile();
					tileSetChests[j][i].setWeight(1);
				}
			}
		}
		
//		System.out.println("Initial map: 0 = open, 1 = collision");
//		for(int i = 0; i < 40; i++){
//			for(int j = 0; j < 40; j++){
//				System.out.print(tileSet[i][j].getCollision());
//			}
//			System.out.println();
//		}
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
