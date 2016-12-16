package nex;

import org.newdawn.slick.Animation;
import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

/*
 * if(status == IDLE){
			switch(direction){
			case UP:
				
				break;
				
			case DOWN:
				
				break;
				
			case LEFT:

				break;
				
			case RIGHT:

				break;
			}
		}
 */

public class Player extends Entity{
	
	public static final int UP 		= 0;
	public static final int DOWN 	= 1;
	public static final int LEFT 	= 2;
	public static final int RIGHT 	= 3;
	
	public static final int MOVING 		= 4;
	public static final int ATK1 		= 5;
	public static final int ATK2 		= 6;
	public static final int IDLE 		= 7;
	
	private Vector velocity;
	private Vector position;
	
	private int direction;
	private int status;
	
	public Animation clericRunLeft, clericRunRight, clericRunUp, clericRunDown;
	public Animation clericAtk1Left, clericAtk1Right, clericAtk1Up, clericAtk1Down;
	
	public Player(final int x, final int y){
		super(x, y);
		
		direction = UP;
		status = IDLE;
		
		addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
		
		velocity = new Vector(0, 0);
		
		clericRunRight 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_RUN, 65, 65), 0, 0, 3, 0, true, 100, true);
		clericRunDown 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_RUN, 65, 65), 0, 1, 3, 1, true, 100, true);
		clericRunLeft 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_RUN, 65, 65), 0, 2, 3, 2, true, 100, true);
		clericRunUp 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_RUN, 65, 65), 0, 3, 3, 3, true, 100, true);
		
	}
	
	
	public void update(final int delta) { 
		
		if(status == ATK1){
			
			removeAtk1();
			
		}
	}
	
	
	public void removeAtk2(){
		if(status == ATK2){
			switch(direction){
				case UP:
					removeImage(ResourceManager.getImage(Nex.CLERIC_BLOCK_UP));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
					status = IDLE;
					break;
					
				case DOWN:
					removeImage(ResourceManager.getImage(Nex.CLERIC_BLOCK_DOWN));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_DOWN));
					status = IDLE;
					break;
					
				case LEFT:
					removeImage(ResourceManager.getImage(Nex.CLERIC_BLOCK_LEFT));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_LEFT));
					status = IDLE;
					break;
					
				case RIGHT:
					removeImage(ResourceManager.getImage(Nex.CLERIC_BLOCK_RIGHT));
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_RIGHT));
					status = IDLE;
					break;
			}
		}
	}
	
	public void removeAtk1(){
		
		switch(direction){
			case UP:
				if(clericAtk1Up.isStopped()){
					removeAnimation(clericAtk1Up);
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
					status = IDLE;
				}
				break;
				
			case DOWN:
				if(clericAtk1Down.isStopped()){
					removeAnimation(clericAtk1Down);
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_DOWN));
					status = IDLE;
				}
				break;
				
			case LEFT:
				if(clericAtk1Left.isStopped()){
					removeAnimation(clericAtk1Left);
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_LEFT));
					status = IDLE;
				}

				break;
				
			case RIGHT:
				if(clericAtk1Right.isStopped()){
					removeAnimation(clericAtk1Right);
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_RIGHT));
					status = IDLE;
				}

				break;
		}
	}

	/*
	 * @param whichAtk
	 * 		Which attack to execute. 1 or 2 which corresponds to left mouse click and right mouse click.
	 */
	public void attack(int whichAtk){
		
		if(status == IDLE){
			
			removeIdleImage();
			
			// This needs to come after removeIdleImage()
			status = ATK1;
			
			switch(direction){
				case UP:
					if(whichAtk == 1){
						clericAtk1Up = new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_ATK1, 65, 65), 0, 3, 3, 3, true, 100, true);
						addAnimation(clericAtk1Up);
						clericAtk1Up.setLooping(false);
					}
					else{
						addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_BLOCK_UP));
						status = ATK2;
					}
					break;
					
				case DOWN:
					if(whichAtk == 1){
						clericAtk1Down 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_ATK1, 65, 65), 0, 1, 3, 1, true, 100, true);
						addAnimation(clericAtk1Down);
						clericAtk1Down.setLooping(false);
					}
					else{
						addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_BLOCK_DOWN));
						status = ATK2;
					}
					break;
					
				case LEFT:
					if(whichAtk == 1){
						clericAtk1Left 	= new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_ATK1, 65, 65), 0, 2, 3, 2, true, 100, true);
						addAnimation(clericAtk1Left);
						clericAtk1Left.setLooping(false);
					}
					else{
						addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_BLOCK_LEFT));
						status = ATK2;
					}
					break;
					
				case RIGHT:
					if(whichAtk == 1){
						clericAtk1Right = new Animation(ResourceManager.getSpriteSheet(Nex.CLERIC_ATK1, 65, 65), 0, 0, 3, 0, true, 100, true);
						addAnimation(clericAtk1Right);
						clericAtk1Right.setLooping(false);
					}
					else{
						addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_BLOCK_RIGHT));
						status = ATK2;
					}
					break;
			
			}
			
		}
	}
	
	/*
	 * Changes Cleric's running animation.
	 */
	public void runDir(final int dir){
		
		if(status == IDLE){
			
			removeIdleImage();
			
			switch(dir){
				case UP:
					addAnimation(clericRunUp);
					direction = UP;
					break;
				case DOWN:
					addAnimation(clericRunDown);
					direction = DOWN;
					break;
				case LEFT:
					addAnimation(clericRunLeft);
					direction = LEFT;
					break;
				case RIGHT:
					addAnimation(clericRunRight);
					direction = RIGHT;
					break;
			}
		}
		else if(status == MOVING){
			switch(dir){
			
				case UP:
					if(direction != UP){
						addAnimation(clericRunUp);
						removeAnim();
						direction = UP;
					}
					break;
					
				case DOWN:
					if(direction != DOWN){
						addAnimation(clericRunDown);
						removeAnim();
						direction = DOWN;
					}
					break;
					
				case LEFT:
					if(direction != LEFT){
						addAnimation(clericRunLeft);
						removeAnim();
						direction = LEFT;
					}
					break;
					
				case RIGHT:
					if(direction != RIGHT){
						addAnimation(clericRunRight);
						removeAnim();
						direction = RIGHT;
					}
					break;
			}
		}
		
	}
	
	/* 
	 * Replaces cleric's running animations with idle pose.
	 */
	public void stopRunning(){
		
		switch(direction){
		
			case UP:
				removeAnim();
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
				break;
				
			case DOWN:
				removeAnim();
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_DOWN));
				break;
				
			case LEFT:
				removeAnim();
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_LEFT));
				break;
				
			case RIGHT:
				removeAnim();
				addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_RIGHT));
				break;
			}
	}
	
	/*
	 * Removes cleric's running animation.
	 */
	private void removeAnim(){
		
		if(status == MOVING){
			
			switch(direction){
				case UP:
					removeAnimation(clericRunUp);
					break;
				case DOWN:
					removeAnimation(clericRunDown);
					break;
				case LEFT:
					removeAnimation(clericRunLeft);
					break;
				case RIGHT:
					removeAnimation(clericRunRight);
					break;
			}
		}
	}
	
	/*
	 * Rotates the player to face the mouse when idle.
	 */
	public void changeIdleDir(final int dir){
		
		if(status == IDLE){
			
			removeIdleImage();
			
			switch(dir){
			
				case DOWN:
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_DOWN));
					direction = DOWN;
					break;
				case UP:
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
					direction = UP;
					break;
				case LEFT:
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_LEFT));
					direction = LEFT;
					break;
				case RIGHT:
					addImageWithBoundingBox(ResourceManager.getImage(Nex.CLERIC_IDLE_RIGHT));
					direction = RIGHT;
					break;
			}
		}
	}
	

	/*
	 * Used to remove the image when rotating an idle player.
	 */
	public void removeIdleImage(){
		
		if(status == IDLE){
			
			switch(direction){
			
				case DOWN:
					removeImage(ResourceManager.getImage(Nex.CLERIC_IDLE_DOWN));
					break;
				case UP:
					removeImage(ResourceManager.getImage(Nex.CLERIC_IDLE_UP));
					break;
				case LEFT:
					removeImage(ResourceManager.getImage(Nex.CLERIC_IDLE_LEFT));
					break;
				case RIGHT:
					removeImage(ResourceManager.getImage(Nex.CLERIC_IDLE_RIGHT));
					break;
			}
		}
	}
	
	public int getDir() 	{ return direction; }
	public int getStatus() 	{ return status; }
	
	public void setStatus(int status) { this.status = status; }
	
	public void setVelocity(final Vector v) { this.velocity = v; }
	public Vector getVelocity() 			{ return this.velocity; }
	
	public void setPlayerPosition(Vector position)	{ this.position = position; }
	public Vector getPlayerPosition()				{ return this.position; }
}
