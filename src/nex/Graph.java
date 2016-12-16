package nex;

import java.util.ArrayList;

public class Graph {
	ArrayList<Node> nodes;
	
	public Graph(){
		
		nodes = new ArrayList<Node>();
		
		for(int i = 0; i < 40; i++){
			for(int j = 0; j < 40; j++){
				nodes.add(new Node(j, i));
			}
		}	
	}
}
