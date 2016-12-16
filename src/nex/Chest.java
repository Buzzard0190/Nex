package nex;

import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Chest extends Entity{
	
	public static final int UP 		= 0;
	public static final int DOWN 	= 1;
	public static final int LEFT 	= 2;
	public static final int RIGHT	= 3;
	
	public static final int CLOSED 	= 0;
	public static final int OPEN 	= 1;
	public static final int EMPTY 	= 2;
	
	private int orientation;
	private int status;
	private int gold;
	
	public Chest(final int x, final int y, final int orientation){
		super(x, y);
		
		this.orientation = orientation;
		
		gold = 1000;
		
		switch(orientation){
			case UP:
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_CLOSED_U));
				break;
				
			case DOWN:
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_CLOSED_D));
				break;
				
			case LEFT:
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_CLOSED_L));
				break;
				
			case RIGHT:
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_CLOSED_R));
				break;
		}
		
	}
	
	public int interact(){
		
		if(status == CLOSED){
			ResourceManager.getSound(Nex.OPEN_CHEST).play();
			switch(orientation){
				case UP:
					removeImage(ResourceManager.getImage(Nex.CHEST_CLOSED_U));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_OPEN_U));
					break;
					
				case DOWN:
					removeImage(ResourceManager.getImage(Nex.CHEST_CLOSED_D));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_OPEN_D));
					break;
					
				case LEFT:
					removeImage(ResourceManager.getImage(Nex.CHEST_CLOSED_L));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_OPEN_L));
					break;
					
				case RIGHT:
					removeImage(ResourceManager.getImage(Nex.CHEST_CLOSED_R));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_OPEN_R));
					break;
			}
			
			status = OPEN;
		}
		else if(status == OPEN){
			
			switch(orientation){
				case UP:
					removeImage(ResourceManager.getImage(Nex.CHEST_OPEN_U));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_EMPTY_U));
					break;
					
				case DOWN:
					removeImage(ResourceManager.getImage(Nex.CHEST_OPEN_D));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_EMPTY_D));
					break;
					
				case LEFT:
					removeImage(ResourceManager.getImage(Nex.CHEST_OPEN_L));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_EMPTY_L));
					break;
					
				case RIGHT:
					removeImage(ResourceManager.getImage(Nex.CHEST_OPEN_R));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CHEST_EMPTY_R));
					break;
			}
			
			status = EMPTY; 
			return gold;
		}
		
		return 0;
		
	}
	
	public int getStatus() { return status; }

	

}
