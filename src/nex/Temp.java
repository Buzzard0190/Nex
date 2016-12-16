package nex;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Temp extends Entity{

	private Vector velocity;
	
	public Temp(final int x, final int y){
		super(x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage(Nex.BLOCK));
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
}
