package nex;

public class Edge {
	int myX = 0;	//where edge points to
	int myY = 0;
	int pX = 0;		//parents
	int pY = 0;		
	int weight = 1;
	
	public Edge(int x, int y, int dx, int dy){
		this.pX = x;
		this.pY = y;
		this.myX = dx+x;
		this.myY = dy+y;
	}
}
