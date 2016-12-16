package nex;

import jig.ResourceManager;
import jig.Vector;

public class ServerEnemyData {

	boolean skeleton, goblin, inCombat;
	int futureX, futureY;
	static int health, attackingX, attackingY, lastHit;
//	private int armorClass;
	
	private Vector velocity;
	private Vector tilePosition, mapPosition, futureTile;
	private int ID;
	private int enemySpeed = 5;
	private int directionMovement = 0;
	private boolean hmove, vmove;
	private int hspeed, vspeed;
	private boolean moving;
	private int pixelCount = 0;
	private Vector worldCoords;
	
	public ServerEnemyData(int x, int y){
		setMapPosition(new Vector(x,y));
		health = 10;
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
	
	public void setWorldCoords(Vector worldCoords)
	{
		this.worldCoords = worldCoords;
	}
	
	public Vector getWorldCoords()
	{
		return this.worldCoords;
	}
	
	public void setPixelCount(int pixelCount)
	{
		this.pixelCount = pixelCount;
	}
	
	public int getPixelCount()
	{
		return this.pixelCount;
	}
	
	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}
	
	public boolean getMoving()
	{
		return this.moving;
	}
	
	public void sethmove(boolean hmove)
	{
		this.hmove = hmove;
	}
	
	public boolean gethmove()
	{
		return this.hmove;
	}
	
	public void setvspeed(int vspeed)
	{
		this.vspeed = vspeed;
	}
	
	public int getvpeed()
	{
		return this.vspeed;
	}
	
	public void sethspeed(int hspeed)
	{
		this.hspeed = hspeed;
	}
	
	public int gethspeed()
	{
		return this.hspeed;
	}
	
	public void setvmove(boolean vmove)
	{
		this.vmove = vmove;
	}
	
	public boolean getvmove()
	{
		return this.vmove;
	}
	
	public void setDirectionMovement(int directionMovement)
	{
		this.directionMovement = directionMovement;
	}
	
	public int getDirectionMovement()
	{
		return this.directionMovement;
	}
}
