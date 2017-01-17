package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;

/**
 * 
 * Node of the graph
 * 
 * @author cguzman@cguz.org
 *
 */
public class NodeImp<T> extends AbstractNode<T> {
	
	public NodeImp(T state){
		super(state);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		Node<T> tmp;
		if(obj instanceof Node){
			tmp = (Node<T>) obj;
			return getValueNode().equals(tmp.getValueNode());
		}
		
		return false;
	}

	@Override
	public int getScore() {
		// TODO
		return 0;
	}

	@Override
	protected Action[] getActions() {
		// TODO
		return null;
	}


}