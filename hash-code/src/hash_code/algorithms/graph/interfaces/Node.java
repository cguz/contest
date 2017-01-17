package hash_code.algorithms.graph.interfaces;

/**
 * 
 * Node that contains the state
 * 
 * @author cguzman@cguz.org
 *
 */
public interface Node<T> {

	/**
	 * Set the node as visit or unvisit
	 * @param option boolean value that indicates if the node is visited or no
	 */
	void setVisit(boolean option);
	
	/**
	 * Retrieve if the node is visited or no
	 * @return true if it is visited, otherwise false
	 */
	boolean isVisit();

	/**
	 * Retrieve if this is the target node
	 * @param current Node to validate
	 * @return true if this is a target goal, otherwise false
	 */
	boolean isGoal(Node<T> current);

	/**
	 * Return all the successors node
	 * @return List of Node that can be reached from this node
	 */
	Node<T>[] getSuccessors();

	/**
	 * get the score of the node
	 * @return integer value 
	 */
	int getScore();
	
	
	/**
	 * get the value or state of the node
	 * @return T state
	 */
	public T getValueNode();
	
}
