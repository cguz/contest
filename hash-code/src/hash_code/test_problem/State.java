package hash_code.test_problem;

import java.util.ArrayList;
import java.util.List;

public class State implements Comparable<State> {

	int N;
	int M;
	int[] goal_state;
	int goal_painted;
	
	int[] action;
	State parent;

	List<int[]> path = new ArrayList<int[]>(0);
	
	public State(int[] goal_state, int goal_painted, int n, int m) {
		this.goal_state = goal_state;
		this.goal_painted = goal_painted;
		N = n;
		M = m;
	}

	public State(int[] i, State state) {
		this.goal_state = new int[state.goal_state.length];
		for(int j=0;j<state.goal_state.length;++j)
			this.goal_state[j] = state.goal_state[j];
			
		this.goal_painted = state.goal_painted;
		N = state.N;
		M = state.M;
		action = i;
		parent = state;
		
	}

	public void regress_action() {

		if(action[0]==ACTIONS.ERASE_CELL.ordinal()){
			goal_state[(action[1]*M)+action[2]]=1;
			++goal_painted;
		}
		
		if(action[0]==ACTIONS.PAINT_LINE.ordinal()){
			if(action[1] == action[3]){
				for(int i=action[2]; i<=action[4];++i){
					if(goal_state[(action[1]*M)+i]==1){
						goal_state[(action[1]*M)+i]=0;
						--goal_painted;
					}
				}
			}else{
				// System.out.println(ACTIONS.PAINT_LINE.toString()+" "+action[1]+" "+action[2]+" "+action[3]+" "+action[4]);
				if(action[2] == action[4])
					for(int i=action[1]; i<=action[3];++i){
						// System.out.println((i*M)+action[2]);
						if(goal_state[(i*M)+action[2]]==1){
							goal_state[(i*M)+action[2]]=0;
							--goal_painted;
						}
					}
			}
		}
		
		if(action[0]==ACTIONS.PAINT_SQUARE.ordinal()){

			int top_i = action[1]-action[3];
			int left_j = action[2]-action[3];
			
			int bottom_i = action[1]+action[3];
			int right_j = action[2]+action[3];
			
			for (int i = top_i; i <= bottom_i; ++i) {
				for(int j = left_j; j <= right_j; ++j){
					paint((i*M)+j);
				}
			}
		}
	}

	private void paint(int i) {
		if(goal_state[i]==1){
			goal_state[i]=0;
			--goal_painted;
		}
	}
	
	public List<int[]> path() {
		
		if(!path.isEmpty())
			return path;
		
		State state_generated = this;
		while(state_generated != null && state_generated.parent!=null){
			path.add(state_generated.action);
			state_generated=state_generated.parent;
		}
		
		return path;
	}

	@Override
	public int compareTo(State o) {
		return (this.goal_painted*this.path().size())-(o.goal_painted*o.path().size());
	}
}
