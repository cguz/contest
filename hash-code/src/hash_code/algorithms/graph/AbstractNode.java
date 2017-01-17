package hash_code.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;

/**
 * 
 * State of the world
 * 
 * @author cguzman@cguz.org
 *
 */
public abstract class AbstractNode<T> implements Node<T> {

	/** value or state of the Node **/
	private T value;

	/** indicate if the node was visited or not **/
	private boolean visit;


	/**
	 * Constructor
	 * @param value
	 */
	public AbstractNode(T value){
		this.value = value;
		visit = false;
	}
	
	@Override
	public void setVisit(boolean option) {
		visit = option;
	}

	@Override
	public boolean isVisit() {
		return visit;
	}

	@Override
	public boolean isGoal(Node<T> current) {
		return this.equals(current);
	}

	@Override
	public Node<T>[] getSuccessors() {
		
		// we get the actions that can be executed in this state
		Action[] actions = getActions();
		
		List<Node<T>> successors = new ArrayList<Node<T>>();
		
		Node<T> newNode;
		for(Action act: actions){
			newNode = act.execute(this);
			if(newNode != null){
				successors.add(newNode);
			}
		}
		
		@SuppressWarnings("unchecked")
		Node<T>[] successorsArr = new Node[successors.size()];
		successorsArr = successors.toArray(successorsArr);
		
		return successorsArr;
		
	}
	
	@Override
	public T getValueNode() {
		return value;
	}

	protected abstract Action[] getActions();
	
}