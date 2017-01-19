package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Node;

public class BreadthFSSearchAlgorithm extends AbstractBreadthFSSearchAlgorithm {

	private Node maximumNode;
	private Node minimumNode;


	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractBFSSearchAlgorithm#commonDefaultValues(hash_code.algorithms.graph.interfaces.Node, hash_code.algorithms.graph.interfaces.Node)
	 */
	protected void commonDefaultValues(Node root, Node target) {

		if(target == null){
			maximumNode = root;
			minimumNode = root;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.AbstractBFSSearchAlgorithm#stopCondition(hash_code.algorithms.graph.interfaces.Node, hash_code.algorithms.graph.interfaces.Node)
	 */
	protected boolean stopCondition(Node current, Node target) {
		if(target == null){
			checkScore(current);
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