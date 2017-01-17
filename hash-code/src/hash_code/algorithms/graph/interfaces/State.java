package hash_code.algorithms.graph.interfaces;

/**
 * @author cguzman@cguz.org
 * Class to represent the state of the given problem
 */
public interface State {

	/**
	 * @return get the score of the given state
	 */
	int getScore();

	/**
	 * @return List of Action that can be executed from this state
	 */
	Action[] getActions();

}
