package nex;

import jig.ResourceManager;
import jig.Vector;

public class ServerEnemyData {

	boolean skeleton, goblin, inCombat;
	int futureX, futureY;
	int health, attackingX, attackingY, lastHit;
//	private int armorClass;
	
	private Vector velocity;
	private Vector tilePosition, mapPosition, futureTile;
	private int ID;
	private int enemySpeed = 5;
	
	public ServerEnemyData(int x, int y){

		setMapPosition(new Vector(x,y));
		
//		if(type == 1) {
//			addImage(ResourceManager.getImage(Nex.BLOCK));
//			goblin = true;
//			health = 10;
////			armorClass = 13;
//			velocity = new Vector(.0f, .0f);
//			inCombat = false;
//		} else if (type == 2) {
//			addImage(ResourceManager.getImage(Game.SKELLY_RSC).getScaledCopy((float) .12));
//			skeleton = true;
//			health = 15;
//			armorClass = 15;
//			velocity = new Vector(.0f, .0f);
//			inCombat = false;
//		}
	}
	
	public void setVelocity(float vx, float vy) {
		velocity = new Vector(vx, vy);
	}

	public Vector getVelocity() {
		// TODO Auto-generated method stub
		return velocity;
	}

	public void setTilePosition(Vector tilePosition)
	{
		this.tilePosition = tilePosition;
	}
	
	public Vector getTilePosition()
	{
		return this.tilePosition;
	}
	
	public void setFutureTile(int x, int y) {
		this.tilePosition = new Vector(x,y);
	}
	
	public Vector getFutureTile(){
		return this.tilePosition;
	}
	
	public void setMapPosition(Vector mapPosition)
	{
		this.mapPosition = mapPosition;
	}
	
	public Vector getMapPosition()
	{
		return this.mapPosition;
	}
	
	public void setID(int ID)
	{
		this.ID = ID;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public void setEnemySpeed(int enemySpeed)
	{
		this.enemySpeed = enemySpeed;
	}
	
	public int getEnemySpeed()
	{
		return this.enemySpeed;
	}
	
	
}
