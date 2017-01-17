package interview.openbet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution1 {

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
        
    	Solution1 sol = new Solution1();
    	
    	File file = new File("./TEST/interview/Openbet/input-test1-1.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);
    	int input = in.nextInt();
     	

        String number = String.valueOf(input);
        int length = number.length();
        int sum = 0;
        
        for(int i=0; i < length;++i){
            int value = (number.charAt(i)- '0');
            sum+= Math.pow((double)value, length);
        }
        
        if(sum == input){
            System.out.println(1);
        }else {
            System.out.println(0);
        }
        
    }
    
}
