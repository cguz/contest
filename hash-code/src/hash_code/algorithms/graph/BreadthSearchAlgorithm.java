package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Node;

public class BreadthSearchAlgorithm extends AbstractBreadthSearchAlgorithm {

	private Node maximumNode;
	private Node minimumNode;


	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractBFSSearchAlgorithm#commonDefaultValues(hash_code.algorithms.graph.interfaces.Node, hash_code.algorithms.graph.interfaces.Node)
	 */
	protected void defineInitialValues(Node root, Node target) {

		if(target == null){
			maximumNode = root;
			minimumNode = root;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractBFSSearchAlgorithm#stopCondition(hash_code.algorithms.graph.interfaces.Node, hash_code.algorithms.graph.interfaces.Node)
	 */
	protected boolean stopCondition(Node current, Node target) {

		System.out.println("******************");
		System.out.println("** CURRENT NODE **");
		System.out.println("******************");
		System.out.println(current.toString());
		
		// if the target is null, we save the node if it is necessary and return false
		if(target == null){
			saveNodes(current);
			return false;
		}
		
		return current.isGoal(target);
		
	}
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractBFSSearchAlgorithm#getFinalValue(hash_code.algorithms.graph.interfaces.Node)
	 */
	protected Node getFinalValue(Node target) {
		if(target == null)
			return maximumNode;
		return null;
	}
	
	
	
	protected void saveNodes(Node current) {

		if(checkMaximumScore(current))
			maximumNode = current;

		if(checkMinimumScore(current))
			minimumNode = current;
		
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