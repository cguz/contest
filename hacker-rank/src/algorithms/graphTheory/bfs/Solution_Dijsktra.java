package algorithms.graphTheory.bfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution_Dijsktra {

	/**
	 * Class Node
	 * @author cguzman
	 * */
	public class Node{
		public int dist = -1;
		public int index;
		public List<Edges> childs = new ArrayList<Edges>();
		
		// constructor
		public Node(){	}
		
		// constructor sending the label of the node
		public Node(int i){
			index = i;
		}
		
		public void addChild(Node n, int r){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				childs.add(new Edges(n, r));
				n.add(this, r);
				
			}
			
		}
		
		public void add(Node n, int r){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				
				childs.add(new Edges(n, r));
				
			}
			
		}

		@Override
		public String toString() {
			String c = "";
			for(Edges d: childs)
				c = c + " " + d.child.index;
			return "[" + index + "](" + dist +") -> "+c;
		}
		
	}
	

	public class Edges {
		public Node child;
		public int length;
		
		public Edges(Node c, int i){
			child = c;
			length = i;
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
        
    	Solution_Dijsktra sol = new Solution_Dijsktra();
    	
    	File file = new File("./TEST/algorithms/graphTheory/dijsktra/input-test.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);
    	int T = in.nextInt();
     	
     	String[] temp;
     	for(int j = 0; j < T; j++){
     		in.nextLine();
     		
     		// creating the set of nodes of the undirected graph
     		temp = in.nextLine().split(" ");
     		int size = Integer.parseInt(temp[0]);

        	List<Node> N = new ArrayList<Node>(size);
        	for(int i=0; i < size;++i)
        		N.add(sol.new Node(i));
     		
     		int vertex = Integer.parseInt(temp[1]);
     		while(vertex > 0){
     			temp = in.nextLine().split(" ");
     			
     			int x = Integer.parseInt(temp[0])-1;
     			int y = Integer.parseInt(temp[1])-1;
     			int r = Integer.parseInt(temp[2]);

     			// System.out.println(N.get(x)+" "+N.get(y));
     			
            	N.get(x).addChild(N.get(y), r);
     			
            	--vertex;
     		}

        	Node S = N.get(in.nextInt()-1);
        	S.dist=0;

            String output = shortestReach(N, S);
            System.out.println(output);
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
    
    public static String shortestReach(List<Node> N, Node S){
    	
    	Comparator<Node> comparator = (new Solution_Dijsktra()).new NodeComparator();
    	
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add all elements
    	Q.add(S);
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		
    		for(Edges v: u.childs){
    			int alt = u.dist + v.length;
    			
    			if(v.child.dist == -1 || alt < v.child.dist){
	    			v.child.dist = alt;
	    			// Q.remove(v);
	    			Q.add(v.child);
    			}
    		}
    	}
    	
    	String output = "";
    	for(Node u: N){
    		if(u.dist == 0)
    			continue;
    		output = output + " "+u.dist;
    		output = output.trim();
    	}
    	
    	return output;
    }
    
    public static String shortestReachDijsktra(List<Node> N, Node S){
    	
    	Comparator<Node> comparator = (new Solution_Dijsktra()).new NodeComparator();
    	
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add all elements
    	Q.addAll(N);
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		
    		for(Edges v: u.childs){
    			int alt = u.dist + v.length;
    			
    			if(v.child.dist == -1 || alt < v.child.dist){
	    			v.child.dist = alt;
	    			Q.remove(v.child);
	    			Q.add(v.child);
    			}
    		}
    	}
    	
    	String output = "";
    	for(Node u: N){
    		if(u.dist == 0)
    			continue;
    		output = output + " "+u.dist;
    		output = output.trim();
    	}
    	
    	return output;
    }
}
