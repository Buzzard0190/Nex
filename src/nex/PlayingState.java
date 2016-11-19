package nex;

import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
	// public static final int LEFT = -2;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		Nex nx = (Nex)game;
		
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
		
		nx.player.render(g);
		
		count = 0;
		for (Temp t : nx.temp)
		{
			if(t.getX() < nx.ScreenWidth+t.getCoarseGrainedWidth()/2 && t.getY() < nx.ScreenHeight+t.getCoarseGrainedHeight()/2
					&& t.getX() > 0-t.getCoarseGrainedWidth()/2 && t.getY() > 0-t.getCoarseGrainedHeight()/2)
			{
				t.render(g);
				count++; // DEBUG
			}
			
		}
//		System.out.println(count + " blocks rendered"); // DEBUG
	}
	
	public void shift(StateBasedGame game, String direction)
	{
		Nex nx = (Nex)game;
		
		for (Temp t : nx.temp)
		{
			if(direction == "left")
				t.setX(t.getX()+4);
			else if(direction == "right")
				t.setX(t.getX()-4);
			else if(direction == "up")
				t.setY(t.getY()+4);
			else if(direction == "down")
				t.setY(t.getY()-4);
		}
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
		/*-------------------------------------------- Moving Player ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		if (input.isKeyDown(Input.KEY_A)) {	// Left Key
			shift(nx, "left");
		}
		else if (input.isKeyDown(Input.KEY_D)){
			shift(nx, "right");
		}
		else if (input.isKeyDown(Input.KEY_W)){
			shift(nx, "up");
		}
		else if (input.isKeyDown(Input.KEY_S)){
			shift(nx, "down");
		}
		
//		nx.player.setVelocity(new Vector(xVelocity, yVelocity));;
//		nx.player.update(delta);
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- World Panning ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- Collisions -----------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
//		for (Temp t : nx.temp)
//		{
//			if (t.collides(nx.player) != null)
//			{
//				System.out.println("Ouch!");
//				t.setPosition(new Vector(t.getX(),t.getY()));
//			}
//		}
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
