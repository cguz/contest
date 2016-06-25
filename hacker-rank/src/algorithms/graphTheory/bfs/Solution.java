package algorithms.graphTheory.bfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution {

	/**
	 * Class Node
	 * @author cguzman
	 * */
	public class Node{
		public int dist = -1;
		public int index;
		public List<Edges> roads = new ArrayList<Edges>();
		public boolean explored = false;
		
		// constructor
		public Node(){	}
		
		// constructor sending the label of the node
		public Node(int i){
			index = i;
		}
		
		public void addRoad(Node n, int r){
			
			int index = roads.indexOf(n);
			
			if(index == -1){
				roads.add(new Edges(n, r));
				n.add(this, r);
				
			}
			
		}
		
		public void add(Node n, int r){
			
			int index = roads.indexOf(n);
			
			if(index == -1){
				
				roads.add(new Edges(n, r));
				
			}
			
		}
		
		public void blockRoad(Node n){
			int index = roads.indexOf(new Edges(n, 0));
			
			if(index != -1){
				Edges r = roads.get(index);
				r.enable = false;
			}
		}
		
		public void unblockRoad(Node n){
			int index = roads.indexOf(new Edges(n, 0));
			
			if(index != -1){
				Edges r = roads.get(index);
				r.enable = true;
			}
		}

		@Override
		public String toString() {
			String c = "";
			for(Edges d: roads)
				c = c + " " + d.road_child.index;
			return "[" + index + "](" + dist +") -> "+c;
		}
	}
	

	public class Edges {
		public Node road_child;
		public int length;
		public boolean enable;
		
		public Edges(Node c, int i){
			road_child = c;
			length = i;
			enable = true;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Edges)
				return this.road_child.index == ((Edges)obj).road_child.index;
			return false;
		}
	}

	
	public class NodeComparator implements Comparator<Node>{
		
		@Override
		public int compare(Node x, Node y){
			
			// the two elements are equals
			if(x.dist == -1 && y.dist == -1)
				return 0;
			
			// the Node y is higher
			if(x.dist == -1)
				return 1;
			
			// the Node x is higher
			if(y.dist == -1)
				return -1;
			
			// the Node y is higher
			if(x.dist < y.dist)
				return -1;
			
			// the Node x is higher
			if(x.dist > y.dist)
				return 1;
			
			// the two Nodes are equals
			return 0;
		}
	}
    
    public static void main(String[] args) throws FileNotFoundException {
        
    	Solution sol = new Solution();
    	
    	File file = new File("./TEST/algorithms/graphTheory/goingToOffice/input09.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);

 		// in.nextLine();
     	String[] temp;
 		
 		// creating the set of nodes of the undirected graph
 		temp = in.nextLine().split(" ");
 		int size = Integer.parseInt(temp[0]);

    	List<Node> N = new ArrayList<Node>(size);
    	for(int i=0; i < size;++i)
    		N.add(sol.new Node(i));
 		
 		int vertex = Integer.parseInt(temp[1]);
 		while(vertex > 0){
 			temp = in.nextLine().split(" ");
 			
 			int x = Integer.parseInt(temp[0]);
 			int y = Integer.parseInt(temp[1]);
 			int r = Integer.parseInt(temp[2]);

 			// System.out.println(N.get(x)+" "+N.get(y));
 			
        	N.get(x).addRoad(N.get(y), r);
 			
        	--vertex;
 		}

 		// getting start S and destination D
 		temp = in.nextLine().split(" ");
 		Node S = N.get(Integer.parseInt(temp[0]));
    	S.dist=0;
    	Node D = N.get(Integer.parseInt(temp[1]));
    	
    	
    	// getting the blocked roads by days
    	vertex = in.nextInt();
    	in.nextLine();
 		while(vertex > 0){
 			temp = in.nextLine().split(" ");
 			
 			int x = Integer.parseInt(temp[0]);
 			int y = Integer.parseInt(temp[1]);

 			// System.out.println(N.get(x)+" "+N.get(y));
 			
        	N.get(x).blockRoad(N.get(y));
 			
            String output = shortestReach(N, S, D);
            System.out.println(output);
            
        	// initializing values
        	N.get(x).unblockRoad(N.get(y));
        	for(Node v: N){
        		v.dist = -1;
        		v.explored = false;
        	}
        	N.get(S.index).dist = 0;
        	
        	--vertex;
 		}

     	
    	
    	/*int size = 3;
    	List<Node> N = new ArrayList<Node>(size);
    	for(int i=0; i < size;++i)
    		N.add(sol.new Node(i));

    	N.get(1).addChild(N.get(2));
    	
    	Node S = N.get(1);
    	S.dist=0;
    	
        String output = shortestReach(N, S);
        System.out.println(output);*/
    }
    
    public static String shortestReach(List<Node> N, Node S, Node D){
    	
    	Comparator<Node> comparator = (new Solution()).new NodeComparator();
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add the Start element
    	Q.add(S);
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		
    		if(u.equals(D))
    			break;
    		
    		u.explored = true;
    		for(Edges v: u.roads){
    			if(v.enable == true){
    				if(v.road_child.explored == false){
		    			int alt = u.dist + v.length;
		    			
		    			if(v.road_child.dist == -1 || alt < v.road_child.dist){
			    			v.road_child.dist = alt;
			    			// Q.remove(v);
			    			Q.add(v.road_child);
		    			}
    				}
    			}
    		}
    	}
    	
    	String output = "Infinity";
    	int dist = N.get(D.index).dist;
    	if (dist != -1)
    		output = ""+dist;
    	
    	return output;
    }
    
}
