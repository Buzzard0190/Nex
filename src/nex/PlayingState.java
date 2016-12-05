package nex;

import java.util.Iterator;

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
	
	private float xVelocity = 0;
	private float yVelocity = 0;
	public int count = 0;
	private static TiledMap map;
	static Tile[][] tileSet;
	int stoneLayer, collisionLayer;
	static int player1x = 867, player1y = 967, player1Speed = 5;
	boolean playerCollision = false;
	String debugString;
	boolean hmove = false, vmove = false;
	int hspeed = 0, vspeed = 0;
	float playerXPosition = 19;
	float playerYPosition = 19;
	public static int row = 0, col = 0;
	
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
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		
		container.setSoundOn(true);
		
		Nex nx = (Nex)game;
		
		// ResourceManager.getSound(Nex.MUSIC_RSC).loop();
		
		//----- Variable Value Initialization -----//
		for(int i = -10; i < 30; i++)
		{
			nx.temp.add(new Temp(100*i, nx.ScreenHeight/3));
		}
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
		
		g.drawString("hmove = " + hmove + ", vmove = " + vmove + "\nhspeed = " + hspeed + ", vspeed = " + vspeed + "\nplayer position = " + nx.player.getPlayerPosition(), 10, 50);
		
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
//		for (Temp t : nx.temp)
//		{
//			t.setX(t.getX()-hspeed);
//			t.setY(t.getY()-vspeed);
//		}
		
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
			// DEBUG
//			System.out.println("Shifting");
//			for(int i = 0; i < 40; i++){
//				for(int j = 0; j < 40; j++){
//					System.out.print(tileSet[i][j].getCollision());
//				}
//				System.out.println();
//			}
//			System.out.println("\n");
			
			row = (int)nx.player.getPlayerPosition().getX();
			col = (int)nx.player.getPlayerPosition().getY();
			
//			System.out.println(row + " " + col + " " + playerXPosition + " " + playerYPosition);
			tileSet[row][col].setCollision();
			tileSet[(int)playerXPosition][(int)playerYPosition].resetCollision();
			shift(nx, hspeed, vspeed);
		}
		
		//System.out.println("Player x: " + player1x + " ,y " + player1y);
		
		// System.out.println("player1x = " + player1x + ", player1y = " + player1y);
		
//		nx.player.setVelocity(new Vector(xVelocity, yVelocity));;
//		nx.player.update(delta);
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- World Panning ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
	
	}
	
	public static void initVars(){
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
		
//		System.out.println("Initial map: 0 = open, 1 = collision");
//		for(int i = 0; i < 40; i++){
//			for(int j = 0; j < 40; j++){
//				System.out.print(tileSet[i][j].getCollision());
//			}
//			System.out.println();
//		}
		
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
