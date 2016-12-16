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
	
	public static final String WIZARD_IDLE_UP = "nex/resource/sprites/player/Wizard_U.png";
	public static final String WIZARD_IDLE_DOWN = "nex/resource/sprites/player/Wizard_D.png";
	public static final String WIZARD_IDLE_RIGHT = "nex/resource/sprites/player/Wizard_R.png";
	public static final String WIZARD_IDLE_LEFT = "nex/resource/sprites/player/Wizard_L.png";
	
	public static final String CLERIC_BLOCK_UP = "nex/resource/sprites/player/Cleric_Block_Up.png";
	public static final String CLERIC_BLOCK_DOWN = "nex/resource/sprites/player/Cleric_Block_Down.png";
	public static final String CLERIC_BLOCK_RIGHT = "nex/resource/sprites/player/Cleric_Block_Right.png";
	public static final String CLERIC_BLOCK_LEFT = "nex/resource/sprites/player/Cleric_Block_Left.png";
	
	public static final String GATE_VERT = "nex/resource/sprites/environment/Gate_Vert.png";
	public static final String GATE_HORI = "nex/resource/sprites/environment/Gate_Hori.png";
	
	public static final String CHEST_CLOSED_U = "nex/resource/sprites/environment/Chest_Closed_U.png";
	public static final String CHEST_CLOSED_D = "nex/resource/sprites/environment/Chest_Closed_D.png";
	public static final String CHEST_CLOSED_R = "nex/resource/sprites/environment/Chest_Closed_R.png";
	public static final String CHEST_CLOSED_L = "nex/resource/sprites/environment/Chest_Closed_L.png";
	
	public static final String CHEST_OPEN_U = "nex/resource/sprites/environment/Chest_Open_U.png";
	public static final String CHEST_OPEN_D = "nex/resource/sprites/environment/Chest_Open_D.png";
	public static final String CHEST_OPEN_R = "nex/resource/sprites/environment/Chest_Open_R.png";
	public static final String CHEST_OPEN_L = "nex/resource/sprites/environment/Chest_Open_L.png";
	
	public static final String CHEST_EMPTY_U = "nex/resource/sprites/environment/Chest_Empty_U.png";
	public static final String CHEST_EMPTY_D = "nex/resource/sprites/environment/Chest_Empty_D.png";
	public static final String CHEST_EMPTY_R = "nex/resource/sprites/environment/Chest_Empty_R.png";
	public static final String CHEST_EMPTY_L = "nex/resource/sprites/environment/Chest_Empty_L.png";
	
	//----- Animations -----//
	public static final String CLERIC_RUN = "nex/resource/sprites/player/Cleric_Walk.png";
	public static final String CLERIC_ATK1 = "nex/resource/sprites/player/Cleric_Atk.png";
	public static final String CLERIC_BLOCK_ANIM = "nex/resource/sprites/player/Cleric_Block_Anim.png";
	public static final String CLERIC_DEATH = "nex/resource/sprites/player/Cleric_Death.png";
	
	public static final String WIZARD_RUN = "nex/resource/sprites/player/Wizard_Move.png";
	public static final String WIZARD_ATK1 = "nex/resource/sprites/player/Wizard_Ice_Atk.png";
	public static final String WIZARD_DEATH = "nex/resource/sprites/player/Wizard_Death.png";
	
	public static final String ICE_ATK = "nex/resource/sprites/player/Ice_Atk.png";
	
	
	public static final String GATE_VERT_ANIM = "nex/resource/sprites/environment/Gate_Anim.png";
	public static final String GATE_HORI_ANIM = "nex/resource/sprites/environment/Gate_Anim_2.png";
	
	
	public static final String BLOCK = "nex/resource/sprites/player/block.png";
	
	
	//----- Sounds -----//
	
	// public static final String Z_GRUNT_RSC = "platformer/resource/zombie_grunt.wav";

	public final int ScreenWidth; 
	public final int ScreenHeight;

	//----- Declare Objects -----//
	
	Player player, otherPlayer;
	Temp block;
	List<Temp> temp;
	List<Gate> GateArray;
	List<Chest> ChestArray;
	List<IceAtk> IceArray;

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
		ChestArray = new ArrayList<Chest>();
		IceArray = new ArrayList<IceAtk>();
		
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
		
		ResourceManager.loadImage(WIZARD_IDLE_UP);
		ResourceManager.loadImage(WIZARD_IDLE_DOWN);
		ResourceManager.loadImage(WIZARD_IDLE_LEFT);
		ResourceManager.loadImage(WIZARD_IDLE_RIGHT);
		
		ResourceManager.loadImage(CLERIC_BLOCK_UP);
		ResourceManager.loadImage(CLERIC_BLOCK_DOWN);
		ResourceManager.loadImage(CLERIC_BLOCK_LEFT);
		ResourceManager.loadImage(CLERIC_BLOCK_RIGHT);
		
		ResourceManager.loadImage(GATE_VERT);
		ResourceManager.loadImage(GATE_HORI);
		
		ResourceManager.loadImage(CHEST_CLOSED_U);
		ResourceManager.loadImage(CHEST_CLOSED_D);
		ResourceManager.loadImage(CHEST_CLOSED_R);
		ResourceManager.loadImage(CHEST_CLOSED_L);
		
		ResourceManager.loadImage(CHEST_OPEN_U);
		ResourceManager.loadImage(CHEST_OPEN_D);
		ResourceManager.loadImage(CHEST_OPEN_R);
		ResourceManager.loadImage(CHEST_OPEN_L);
		
		ResourceManager.loadImage(CHEST_EMPTY_U);
		ResourceManager.loadImage(CHEST_EMPTY_D);
		ResourceManager.loadImage(CHEST_EMPTY_L);
		ResourceManager.loadImage(CHEST_EMPTY_R);
		
		
		//----- Animations ----//
		ResourceManager.loadImage(CLERIC_RUN);
		ResourceManager.loadImage(CLERIC_ATK1);
		ResourceManager.loadImage(CLERIC_BLOCK_ANIM);
		ResourceManager.loadImage(CLERIC_DEATH);
		
		ResourceManager.loadImage(WIZARD_RUN);
		ResourceManager.loadImage(WIZARD_ATK1);
		ResourceManager.loadImage(WIZARD_DEATH);
		
		ResourceManager.loadImage(ICE_ATK);
		
		ResourceManager.loadImage(GATE_VERT_ANIM);
		ResourceManager.loadImage(GATE_HORI_ANIM);
		
		//----- Sounds -----//
		
		// ResourceManager.loadSound(Z_GRUNT_RSC);	
		
		//----- Create Instances -----//
		
		player = new Player(ScreenWidth/2, ScreenHeight/2);
		block = new Temp(400, 400);
		otherPlayer = new Player(ScreenWidth/2, ScreenHeight/2);

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
