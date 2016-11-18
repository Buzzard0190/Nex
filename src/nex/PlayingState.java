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
		
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,Graphics g) throws SlickException {
		
		Nex nx = (Nex)game;
		
		//----- Render -----//
		
		nx.player.render(g);
			
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

		}
		else if (input.isKeyDown(Input.KEY_D)){
			
		}
			
		/*--------------------------------------------------------------------------------------------------------*/
		/*-------------------------------------------- World Panning ---------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
		/*--------------------------------------------------------------------------------------------------------*/
		/*--------------------------------------------- Collisions -----------------------------------------------*/
		/*--------------------------------------------------------------------------------------------------------*/
		
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
