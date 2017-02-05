package hash_code.algorithms.graph;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.SearchAlgorithm;

/**
 * 
 * Breadth first search algorithm
 * 
 * @author cguzman@cguz.org
 *
 */
public abstract class AbstractBreadthSearchAlgorithm implements SearchAlgorithm {
	

	private Set<Node> visitedNodes = new HashSet<Node>();
	
	
	@Override
	public Node find(Node root, Node target){
		
		Node current;
		boolean stop = false;
		
		Queue<Node> Q = new LinkedList<Node>();
		Q.add(root);
		
		/** function to define values **/
		defineInitialValues(root, target);
		
		while(!Q.isEmpty()){
			current = Q.poll();
			visitedNodes.add(current);
			
			/** function to define the stop condition **/
			stop = stopCondition(current, target);
			
			if(stop) return current; 
			
			for(Node successor : current.getSuccessors()){
				if(!visitedNodes.contains(successor)){
					Q.add(successor);
				}
			}
		}
		
		/** function to define the final value to return **/
		return getFinalValue(target);
	}

	/**
	 * function to define own values
	 * @param root node
	 * @param target node
	 */
	protected abstract void defineInitialValues(Node root, Node target);

	/**
	 * function to define the stop condition
	 * @param current node
	 * @param target node
	 * @return true if the goal is found, otherwise false
	 */
	protected abstract boolean stopCondition(Node current, Node target);

	/**
	 * function to define the final value to return
	 * @param target node
	 * @return retrieve the final value
	 */
	protected abstract Node getFinalValue(Node target);
	
}
