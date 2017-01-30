package hash_code.algorithms.graph.nodes;

import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
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

	@Override
	public Action[] getPath() {
		return value.getPath();
	}

    @Override
    public boolean equals(Object object) {
    	if(this == object)
    		return true;
    	if(object instanceof Node)
    		return this.getValue().equals(((Node)object).getValue());
        return false;
    }


    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }

	@Override
	public String toString() {
		
		String temp="\n";
		if(actions != null && actions.length > 0){
			temp+=actions.toString();
		}
		
		return value.toString()+temp;
	}

}