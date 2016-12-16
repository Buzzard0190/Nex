package nex;

import java.util.Iterator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import jig.ResourceManager;
import jig.Vector;

public class GameOverState extends BasicGameState
{
	private int timer;
	private int lastKnownBounces; // the user's score, to be displayed, but not updated.
	private int lastKnownScore;
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		timer = 4000;
	}

	public void setUserScore(int score) {
		lastKnownScore = score;
	}
	
	@Override
	public void render(GameContainer container, StateBasedGame game,
			Graphics g) throws SlickException {

		Nex nx = (Nex)game;
		
//		g.drawString("Score: " + lastKnownScore, 10, 30);
//		g.drawString("Highscore: " + ((StartUpState)game.getState(BounceGame.STARTUPSTATE)).getUserHighScore(), 620, 10);
//		g.drawString("Lives Remaining: 0", 10, 50);
//		
//		for (Bang b : bg.explosions)
//			b.render(g);
		
		g.drawImage(ResourceManager.getImage(Nex.GAMEOVER), 185,
				210);

	}

	@Override
	public void update(GameContainer container, StateBasedGame game,
			int delta) throws SlickException {
		
		Nex bbg = (Nex)game;
		
		timer -= delta;
		if (timer <= 0)
		{
//			// Remove remaining bricks
//			for (Iterator<Blocks> b = bbg.block.iterator(); b.hasNext();)
//			{
//				if(b.next() != null)
//					b.remove();
//			}
//			
//			// Remove enemies that have died
//			for (Iterator<Enemy> removeEnemy = bbg.enemy.iterator(); removeEnemy.hasNext();)
//			{
//				Enemy e = removeEnemy.next();
//				removeEnemy.remove();
//			}
//			
//			// Remove enemies that have died
//			for (Iterator<Bubble> removeBubbles = bbg.bubble.iterator(); removeBubbles.hasNext();)
//			{
//				Bubble b = removeBubbles.next();
//				removeBubbles.remove();
//			}
//			
////			bbg.bub.AnimateRight();
//			bbg.bub.setPosition(new Vector(bbg.ScreenWidth/2, 2*bbg.ScreenHeight/3+60));
			//game.enterState(Nex.PLAYINGSTATE, new FadeOutTransition(), new FadeInTransition() );
		}
			
//		// check if there are any finished explosions, if so remove them
//		for (Iterator<Bang> i = ((BounceGame)game).explosions.iterator(); i.hasNext();) {
//			if (!i.next().isActive()) {
//				i.remove();
//			}
//		}

	}

	@Override
	public int getID() {
		return Nex.GAMEOVERSTATE;
	}
}
