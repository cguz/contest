package algorithms.graphTheory.bfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Solution_ShortestPath1 {

	/**
	 * Class Node
	 * @author cguzman
	 * */
	public class Node{
		public int dist = -1;
		public int index;
		public List<Node> childs = new ArrayList<Node>();
		
		// constructor
		public Node(){	}
		
		// constructor sending the label of the node
		public Node(int i){
			index = i;
		}
		
		public void addChild(Node n){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				
				childs.add(n);
				n.add(this);
				
			}
			
		}
		
		public void add(Node n){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				
				childs.add(n);
				
			}
			
		}

		@Override
		public String toString() {
			String c = "";
			for(Node d: childs)
				c = c + " " + d.index;
			return "[" + index + "](" + dist +") -> "+c;
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
        
    	Solution_ShortestPath1 sol = new Solution_ShortestPath1();
    	
    	//File file = new File("./TEST/algorithms/graphTheory/bfs/input04.txt");
    	//Scanner in = new Scanner(file);
    	Scanner in = new Scanner(System.in);
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

     			// System.out.println(N.get(x)+" "+N.get(y));
     			
            	N.get(x).addChild(N.get(y));
     			
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
    	
    	Comparator<Node> comparator = (new Solution_ShortestPath1()).new NodeComparator();
    	
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add all elements
    	Q.add(S);
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		
    		for(Node v: u.childs){
    			int alt = u.dist + 6;
    			
    			if(v.dist == -1 || alt < v.dist){
	    			v.dist = alt;
	    			// Q.remove(v);
	    			Q.add(v);
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
    	
    	Comparator<Node> comparator = (new Solution_ShortestPath1()).new NodeComparator();
    	
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add all elements
    	Q.addAll(N);
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		
    		for(Node v: u.childs){
    			int alt = u.dist + 6;
    			
    			if(v.dist == -1 || alt < v.dist){
	    			v.dist = alt;
	    			Q.remove(v);
	    			Q.add(v);
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
