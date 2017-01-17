package hash_code.algorithms.graph;

import java.util.LinkedList;
import java.util.Queue;

import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.SearchAlgorithm;

public class BFSSearchAlgorithm implements SearchAlgorithm {

	private Node maximumNode;
	private Node minimumNode;
	
	public Node findPath(Node root, Node target){
		
		Node current;
		
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(root);
		root.setVisit(true);
		
		while(!Q.isEmpty()){
			current = Q.poll();
			
			if(current.isGoal(target))
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

	@Override
	public Node generateTree(Node root) {

		Node current;
		
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(root);

		maximumNode = root;
		maximumNode = root;
			
		while(!Q.isEmpty()){
			current = Q.poll();
			
			checkScore(root);
			
			for(Node successor : current.getSuccessors()){
				Q.add(successor);
			}
		}
		
		return maximumNode;
	}

	private void checkScore(Node root) {

		if(checkMaximumScore(root))
			maximumNode = root;

		if(checkMinimumScore(root))
			minimumNode = root;
		
	}

	private boolean checkMaximumScore(Node root) {
		if(maximumNode.getScore() < root.getScore()){
			return true;
		}
		return false;
	}

	private boolean checkMinimumScore(Node root) {
		if(minimumNode.getScore() > root.getScore()){
			return true;
		}
		return false;
	}
	
}