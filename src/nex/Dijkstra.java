package nex;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;



public class Dijkstra {
	
	public static ArrayList<Node> runDijkstra(Graph G, int x, int y){
		
		int graphSize = G.nodes.size();
		ArrayList<Node> updatedGraph = new ArrayList<Node>();
		ArrayList<Node> vInput = new ArrayList<Node>();
		
		PriorityQueue<Node> queue = new PriorityQueue<Node>(graphSize, new myComparator());
		
		for(Iterator<Node> i = G.nodes.iterator(); i.hasNext();){
			Node n = i.next();
			n.dist = Integer.MAX_VALUE;
			n.px = n.x;
			n.py = n.y;
			if(n.x == x && n.y == y){
				n.dist = 0;
			}
			queue.add(n);
		}
		
		while(!queue.isEmpty()){
			Node u = queue.poll();
			updatedGraph.add(u);
			int edgeNum = u.edges.size();
			int edgeCount = 0;
			
			for(Iterator<Node> q = queue.iterator(); q.hasNext();){
				Node v = q.next();
				
				if(edgeCount < edgeNum){
					for(Iterator<Edge> e = u.edges.iterator(); e.hasNext();){
						Edge iEdge = e.next();
						if(v.x == iEdge.myX && v.y == iEdge.myY){
							int adjustedWeight = getWeight(v.x, v.y, u.x, u.y, iEdge.weight);
							if(v.dist > (u.dist+adjustedWeight)){
								v.dist = u.dist+adjustedWeight;
								v.px = u.x;
								v.py = u.y;
								vInput.add(v);
								edgeCount++;
							}
						}
					}
				}
			}
			
			//iterate here
			for(Iterator<Node> add = vInput.iterator(); add.hasNext();){
				Node addNode = add.next();
				queue.remove(addNode);
				queue.offer(addNode);
				add.remove();
			}
		}
		return updatedGraph;
	}
	
	public static int getWeight(int x1, int y1, int x2, int y2, int weight){
		if(x1-x2 == 0 || y1-y2 == 0){
			return weight;
		} else {
			int retValue = (int) Math.ceil((Math.sqrt(weight*weight*2)));
			return retValue;
		}
	}
}




//used in priority queue
class myComparator implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		if(o1.dist > o2.dist){
			return 1;
		} else {
			return -1;
		}
	}
}
