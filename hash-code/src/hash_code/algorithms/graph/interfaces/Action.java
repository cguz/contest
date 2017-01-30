package hash_code.algorithms.graph.interfaces;

/**
 * 
 * @author cguzman@cguz.org
 *
 */
public interface Action {
	
	/**
	 * 
	 * Execute the action in a given node
	 * 
	 * @param current Node
	 * @return retrieve the new node
	 */
	Node execute(Node current);
	
}
