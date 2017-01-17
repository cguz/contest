package hash_code.algorithms.graph;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.State;

public class AbstractState implements State {
	
	// attributes for any problem
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
