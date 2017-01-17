package hash_code.algorithms.graph.interfaces;

public interface SearchAlgorithm {
	
	public Node findPath(Node root, Node target);

	public Node generateTree(Node initial);
	
}