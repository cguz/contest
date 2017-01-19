package hash_code.algorithms.graph.interfaces;

/**
 * 
 * Node that contains the state
 * 
 * @author cguzman@cguz.org
 *
 */
public interface Node {

	/**
	 * Retrieve if this is the target node
	 * @param target Node to validate
	 * @return true if this is a target goal, otherwise false
	 */
	boolean isGoal(Node target);

	/**
	 * Return all the successors node
	 * @return List of Node that can be reached from this node
	 */
	Node[] getSuccessors();

	/**
	 * get the score of the node
	 * @return integer value 
	 */
	int getScore();
	
	
	/**
	 * get the value or state of the node
	 * @return T state
	 */
	public ValueNode getValue();
	
}
