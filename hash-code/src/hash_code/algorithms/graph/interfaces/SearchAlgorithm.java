package hash_code.algorithms.graph.interfaces;

/**
 * 
 * Interface of the search algorithm
 * 
 * @author cguzman@cguz.org
 *
 */
public interface SearchAlgorithm {
	
	/**
	 * find a target node from a given root node
	 * @param root node
	 * @param target node
	 * @return the target node. Otherwise, a null value
	 */
	public Node find(Node root, Node target);
	
}