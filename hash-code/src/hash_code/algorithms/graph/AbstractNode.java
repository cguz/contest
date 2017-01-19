package hash_code.algorithms.graph;

import java.util.ArrayList;
import java.util.List;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.ValueNode;

/**
 * 
 * Abstract Node of the graph
 * 
 * @author cguzman@cguz.org
 *
 */
public abstract class AbstractNode implements Node {

	/** value or state of the Node **/
	protected ValueNode value;

	/** list of actions that can be executed from the given nodeValue **/
	protected Action[] actions;
	


	/**
	 * Constructor
	 * @param value
	 */
	public AbstractNode(ValueNode value){
		this.value = value;
	}
	
	@Override
	public boolean isGoal(Node current) {
		return this.equals(current);
	}
	
	@Override
	public Node[] getSuccessors() {
		
		// we get the actions that can be executed in this state
		if(actions == null)
			actions = getActions();
		
		List<Node> successors = new ArrayList<Node>();
		
		Node newNode;
		for(Action act: actions){
			newNode = act.execute(this);
			if(newNode != null){
				successors.add(newNode);
			}
		}
		
		Node[] successorsArr = new Node[successors.size()];
		successorsArr = successors.toArray(successorsArr);
		
		return successorsArr;
		
	}
	
	@Override
	public ValueNode getValue() {
		return value;
	}
	
	@Override
	public abstract int getScore();

	protected abstract Action[] getActions();
	
}