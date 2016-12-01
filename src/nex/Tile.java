package nex;

import jig.Vector;

public class Tile {
	private boolean collision, bolt = false;
	private int weight;	
	private Vector position;
	
	
	public Tile(){
		
		collision = false;
		weight = 0;
		
	}

	public void setCollision(){
		this.collision = true;
	}
	
	public void resetCollision(){
		this.collision = false;
	}
	
	public int getCollision(){
		if(this.collision == true){
			return 1;
		} else {
			return 0;
		}
	}
	
	public void setWeight(int w){
		weight = w;
	}
	
	public int getWeight(){
		return weight;
	}
}
