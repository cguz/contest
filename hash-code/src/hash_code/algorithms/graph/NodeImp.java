package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.ValueNode;

/**
 * 
 * Node of the graph
 * 
 * @author cguzman@cguz.org
 *
 */
public class NodeImp extends AbstractNode {
	
	public NodeImp(ValueNode state){
		super(state);
	}

	protected Action[] getActions() {
		return value.getActions();
	}

	@Override
	public int getScore() {
		return value.getScore();
	}

}