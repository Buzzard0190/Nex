package nex;

import org.newdawn.slick.Animation;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Gate extends Entity{
	
	public static final int VERT = 0;
	public static final int HORI = 1;
	
	private int orientation;
	private boolean active = true;
	private Vector tile;
	
	private Animation recede;
	
	public Gate(final int x, final int y, final int orientation, Vector tile){
		super(x, y);
		
		this.orientation = orientation;
		this.tile = tile;
		
		if(orientation == VERT)
			addImageWithBoundingBox(ResourceManager.getImage(Nex.GATE_VERT));
		else
			addImageWithBoundingBox(ResourceManager.getImage(Nex.GATE_HORI));
		
	}
	
	public void gateAnim(){
		
		if(orientation == VERT){
			removeImage(ResourceManager.getImage(Nex.GATE_VERT));
			recede = new Animation(ResourceManager.getSpriteSheet(Nex.GATE_VERT_ANIM, 65, 65), 0, 0, 14, 0, true, 100, true);
		}
		
		else{
			removeImage(ResourceManager.getImage(Nex.GATE_HORI));
			recede = new Animation(ResourceManager.getSpriteSheet(Nex.GATE_HORI_ANIM, 65, 65), 0, 0, 14, 0, true, 100, true);
		}
		
		addAnimation(recede);
		recede.setLooping(false);
		
		active = false;
		
		// animate adjacent gates
		// remove collision
			
		System.out.println("animate gate");
	}
	
	public Vector getTileLocation() { return tile; }
	public boolean isActive() { return active; }
	public void setActive( boolean active ) { this.active = active; }
	
}
