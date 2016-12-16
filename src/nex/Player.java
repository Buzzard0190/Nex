package nex;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity{
	
	private Vector velocity;
	private Vector position;
	static int health, shield;
	
	public Player(final int x, final int y){
		super(x, y);
		
		health = 120;
		shield = 0;
		
		addImageWithBoundingBox(ResourceManager.getImage(Nex.PLAYER));
		velocity = new Vector(0, 0);
	}
	
	public void setVelocity(final Vector v) {
		this.velocity = v;
	}
	
	public Vector getVelocity() {
		return this.velocity;
	}
	
	public void update(final int delta) {
		translate(velocity.scale(delta));
	}
	
	public void setPlayerPosition(Vector position)
	{
		this.position = position;
	}
	
	public Vector getPlayerPosition()
	{
		return this.position;
	}
}
