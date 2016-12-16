package nex;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class IceAtk extends Entity{
	
	private Animation iceAtk;
	
	public IceAtk(final int x, final int y){
		
		super(x, y);
		
		iceAtk = new Animation(ResourceManager.getSpriteSheet(Nex.ICE_ATK, 65, 65), 0, 0, 4, 0, true, 100, true);
		addAnimation(iceAtk);
		iceAtk.setLooping(false);
	}

	
	public boolean isActive() { return !iceAtk.isStopped(); }
	
	
}
