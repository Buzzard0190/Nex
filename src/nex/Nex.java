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
	public static final int CONNECTSTATE = 3;
	
	//----- Images -----//
	// public static final String STARTUP_BANNER_RSC = "platformer/resource/startup_banner.png";
	// public static final String GAMEOVER_BANNER_RSC = "platformer/resource/gameover_banner.png";
	
	public static final String CLERIC_IDLE_UP = "nex/resource/sprites/player/Cleric_Idle_Up.png";
	public static final String CLERIC_IDLE_DOWN = "nex/resource/sprites/player/Cleric_Idle_Down.png";
	public static final String CLERIC_IDLE_RIGHT = "nex/resource/sprites/player/Cleric_Idle_Right.png";
	public static final String CLERIC_IDLE_LEFT = "nex/resource/sprites/player/Cleric_Idle_Left.png";
	
	public static final String CLERIC_BLOCK_UP = "nex/resource/sprites/player/Cleric_Block_Up.png";
	public static final String CLERIC_BLOCK_DOWN = "nex/resource/sprites/player/Cleric_Block_Down.png";
	public static final String CLERIC_BLOCK_RIGHT = "nex/resource/sprites/player/Cleric_Block_Right.png";
	public static final String CLERIC_BLOCK_LEFT = "nex/resource/sprites/player/Cleric_Block_Left.png";
	
	
	public static final String GATE_VERT = "nex/resource/sprites/environment/Gate_Vert.png";
	public static final String GATE_HORI = "nex/resource/sprites/environment/Gate_Hori.png";
	
	//----- Animations -----//
	public static final String CLERIC_RUN = "nex/resource/sprites/player/Cleric_Walk.png";
	public static final String CLERIC_ATK1 = "nex/resource/sprites/player/Cleric_Atk.png";
	public static final String CLERIC_BLOCK_ANIM = "nex/resource/sprites/player/Cleric_Block_Anim.png";
	public static final String CLERIC_DEATH = "nex/resource/sprites/player/Cleric_Death.png";
	
	public static final String GATE_VERT_ANIM = "nex/resource/sprites/environment/Gate_Anim.png";
	public static final String GATE_HORI_ANIM = "nex/resource/sprites/environment/Gate_Anim_2.png";
	
	
	public static final String BLOCK = "nex/resource/sprites/player/block.png";
	
	
	//----- Sounds -----//
	
	// public static final String Z_GRUNT_RSC = "platformer/resource/zombie_grunt.wav";

	public final int ScreenWidth; 
	public final int ScreenHeight;

	//----- Declare Objects -----//
	
	Player player;
	Temp block;
	List<Temp> temp;
	List<Gate> GateArray;

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
		
		GateArray = new ArrayList<Gate>();
		
		temp = new ArrayList<Temp>(50);
		// blocks = new ArrayList<Block>(100);
		
	}


	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		
		//----- States -----//
		
		// addState(new StartUpState());
		addState(new ConnectState());
		addState(new PlayingState());
		
		// addState(new GameOverState());
		
		//------------------------------------------------//
		//-------------- Pre-load resources. -------------//
		//------------------------------------------------//
		
		//----- Images -----//
		
		// ResourceManager.loadImage(STARTUP_BANNER_RSC);
		// ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
		
		ResourceManager.loadImage(BLOCK);
		
		ResourceManager.loadImage(CLERIC_IDLE_UP);
		ResourceManager.loadImage(CLERIC_IDLE_DOWN);
		ResourceManager.loadImage(CLERIC_IDLE_LEFT);
		ResourceManager.loadImage(CLERIC_IDLE_RIGHT);
		
		ResourceManager.loadImage(CLERIC_BLOCK_UP);
		ResourceManager.loadImage(CLERIC_BLOCK_DOWN);
		ResourceManager.loadImage(CLERIC_BLOCK_LEFT);
		ResourceManager.loadImage(CLERIC_BLOCK_RIGHT);
		
		ResourceManager.loadImage(GATE_VERT);
		ResourceManager.loadImage(GATE_HORI);
		
		
		
		//----- Animations ----//
		ResourceManager.loadImage(CLERIC_RUN);
		ResourceManager.loadImage(CLERIC_ATK1);
		ResourceManager.loadImage(CLERIC_BLOCK_ANIM);
		ResourceManager.loadImage(CLERIC_DEATH);
		
		ResourceManager.loadImage(GATE_VERT_ANIM);
		ResourceManager.loadImage(GATE_HORI_ANIM);
		
		
		
		//----- Sounds -----//
		
		// ResourceManager.loadSound(Z_GRUNT_RSC);	
		
		//----- Create Instances -----//
		
		player = new Player(ScreenWidth/2, ScreenHeight/2);
		block = new Temp(400, 400);
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
