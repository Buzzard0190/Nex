package nex;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Player extends Entity{
	
	private Vector velocity;
	private Vector position;
	
	public Player(final int x, final int y){
		super(x, y);
		
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
	
	public void setTilePosition(Vector position)
	{
		this.position = position;
	}
	
	public Vector getTilePosition()
	{
		return this.position;
	}
}
