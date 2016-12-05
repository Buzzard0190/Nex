package nex;



import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class ConnectState extends BasicGameState{

	boolean connected;
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		// TODO Auto-generated method stub
		connected = false;
	}


	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("Press SPACE to connect.", 100, 100);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		Input input = container.getInput();
		
		Nex nx = (Nex)game;
		
		if (input.isKeyDown(Input.KEY_SPACE) && !connected){
			try{
				ClientHandler terminal = new ClientHandler();
				terminal.start();
				
			}
			catch(Exception e){
				System.out.println("Error in starting client = " + e);
				System.exit(-1);
			}
			
			connected = true;
			System.out.println("Success");
			nx.enterState(Nex.PLAYINGSTATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

}
