package nex;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import jig.Entity;
import jig.ResourceManager;

public class Nex extends StateBasedGame{


	//----- States -----//
	public static final int PLAYINGSTATE = 0;
	// public static final int STARTUPSTATE = 1;
	public static final int GAMEOVERSTATE = 2;
	
	//----- Images -----//
	// public static final String STARTUP_BANNER_RSC = "platformer/resource/startup_banner.png";
	// public static final String GAMEOVER_BANNER_RSC = "platformer/resource/gameover_banner.png";
	
	public static final String PLAYER = "nex/resource/sprites/player/player.png";
	public static final String BLOCK = "nex/resource/sprites/player/block.png";
	
	//----- Sounds -----//
	
	// public static final String Z_GRUNT_RSC = "platformer/resource/zombie_grunt.wav";

	public final int ScreenWidth;
	public final int ScreenHeight;

	//----- Declare Objects -----//
	
	Player player;
	List<Temp> temp;

	// ---- Declare Arrays -----//
	
	// ArrayList<Block> blocks;

	/**
	 * Create the BounceGame frame, saving the width and height for later use.
	 * 
	 * @param title
	 *            the window's title
	 * @param width
	 *            the window's width
	 * @param height
	 *            the window's height
	 */
	public Nex(String title, int width, int height) {
		
		super(title);
		
		ScreenHeight = height;
		ScreenWidth = width;

		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
		
		//----- Create Arrays -----//
		
		temp = new ArrayList<Temp>(50);
		// blocks = new ArrayList<Block>(100);
		
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		//----- States -----//
		
		// addState(new StartUpState());
		addState(new PlayingState());
		// addState(new GameOverState());
		
		//------------------------------------------------//
		//-------------- Pre-load resources. -------------//
		//------------------------------------------------//
		
		//----- Images -----//
		
		// ResourceManager.loadImage(STARTUP_BANNER_RSC);
		// ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		
		ResourceManager.loadImage(PLAYER);
		ResourceManager.loadImage(BLOCK);
		
		//----- Sounds -----//
		
		// ResourceManager.loadSound(Z_GRUNT_RSC);	
		
		//----- Create Instances -----//
		
		player = new Player(ScreenWidth/2, ScreenHeight/2);
	}
	
	public static void main(String[] args) {
		AppGameContainer app;
		try {
			app = new AppGameContainer(new Nex("Nex!", 800, 600));
			app.setDisplayMode(800, 600, false);
			app.setVSync(true);
			
			System.out.println("app start");	//DEBUG
			
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

}
