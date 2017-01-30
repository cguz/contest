package hash_code.algorithms.graph.nodes;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.ValueNode;

/**
 * 
 * abstract class of the value
 * 
 * @author cguzman@cguz.org
 *
 */
public abstract class AbstractValueNode implements ValueNode {
	
	// common attributes for any given problem
	protected Action[] actions;
	
	protected int score;
	
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.interfaces.ValueNode#getScore()
	 */
	@Override
	public abstract int getScore();
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.interfaces.ValueNode#getActions()
	 */
	@Override
	public abstract Action[] getActions();
	
	/* (non-Javadoc)
	 * @see hash_code.algorithms.graph.interfaces.ValueNode#getPath()
	 */
	@Override
	public abstract Action[] getPath();
	
	@Override
	public abstract boolean equals(Object object);
	
	@Override
	public abstract int hashCode();
	
}
