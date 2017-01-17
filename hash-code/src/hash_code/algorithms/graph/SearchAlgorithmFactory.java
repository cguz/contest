package hash_code.algorithms.graph;

import java.util.LinkedList;
import java.util.Queue;

import hash_code.algorithms.graph.interfaces.Node;

public class SearchAlgorithmFactory  {
	
	
	public Node breadthFirstSearch(Node root, Node target){
		
		Node current;
		
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(root);
		root.setVisit(true);
		
		while(!Q.isEmpty()){
			current = Q.poll();
			
			if(target.isGoal(current))
				return current; 
			
			for(Node successor : current.getSuccessors()){
				if(!successor.isVisit()){
					successor.setVisit(true);
					Q.add(successor);
				}
			}
		}
		
		return null;
	}
	
}