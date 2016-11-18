package nex;

import jig.Entity;
import jig.ResourceManager;

public class Player extends Entity{
	
	public Player(final int x, final int y){
		super(x, y);
		
		addImageWithBoundingBox(ResourceManager.getImage(Nex.PLAYER));

		
		
	}
}
