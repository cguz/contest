package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.ValueNode;

/**
 * 
 * asbtract class of the value
 * 
 * @author cguzman@cguz.org
 *
 */
public abstract class AbstractValueNode implements ValueNode {
	
	// common attributes for any given problem
	protected Action[] actions;
	
	protected int score;
	
	
	@Override
	public int getScore() {
		return score;
	}
	
	@Override
	public Action[] getActions() {
		return actions;
	}
	
}
