package nex;

import java.util.ArrayList;

public class Node {
	int x = 0;
	int y = 0;
	int dist = Integer.MAX_VALUE;
	int px = -1;
	int py = -1;
	ArrayList<Edge> edges;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		edges = new ArrayList<Edge>();
		getEdges();
	}
	
	public void getEdges(){
		
		//[0, -1] 	N
		if(this.x >= 0 && this.x < 29 && this.y-1 >= 0 && this.y-1 < 25){
			//System.out.println("Running N");
			edges.add(new Edge(this.x, this.y, 0, (-1)));
		}
			
		//[1, -1]	NE
		if(this.x+1 >= 0 && this.x+1 < 29 && this.y-1 >= 0 && this.y-1 < 25){
			//System.out.println("Running NE");
			edges.add(new Edge(this.x, this.y, 1, (-1)));
		}
		
		//[1, 0] 	E
		if(this.x+1 >= 0 && this.x+1 < 29 && this.y >= 0 && this.y < 25){
			//System.out.println("Running E");
			edges.add(new Edge(this.x, this.y, 1, 0));
		}
		
		//[1, 1] 	SE
		if(this.x+1 >= 0 && this.x+1 < 29 && this.y+1 >= 0 && this.y+1 < 25){
			//System.out.println("Running SE");
			edges.add(new Edge(this.x, this.y, 1, 1));
		}
		
		//[0, 1] 	S
		if(this.x >= 0 && this.x < 29 && this.y+1 >= 0 && this.y+1 < 25){
			//System.out.println("Running S");
			edges.add(new Edge(this.x, this.y, 0, 1));
		}
		
		//[-1, 1]	SW
		if(this.x-1 >= 0 && this.x-1 < 29 && this.y+1 >= 0 && this.y+1 < 25){
			//System.out.println("Running SW");
			edges.add(new Edge(this.x, this.y, (-1), 1));
		}
		
		//[-1, 0] 	W
		if(this.x-1 >= 0 && this.x-1 < 29 && this.y >= 0 && this.y < 25){
			//System.out.println("Running W");
			edges.add(new Edge(this.x, this.y, (-1), 0));
		}
		
		//[-1, -1] 	NW
		if(this.x-1 >= 0 && this.x-1 < 29 && this.y-1 >= 0 && this.y-1 < 25){
			//System.out.println("Running NW");
			edges.add(new Edge(this.x, this.y, (-1), (-1)));
		}
		
	}

}
