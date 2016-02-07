package hash.code.test_problem;

public class State implements Comparable<State> {

	int N;
	int M;
	int[] goal_state;
	int goal_painted;
	
	int[] action;
	State parent;
	
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
			goal_state[(action[1]*N)+action[2]]=0;
			--goal_painted;
		}
		
		if(action[0]==ACTIONS.PAINT_LINE.ordinal()){
			if(action[1] == action[3])
				for(int i=action[2]; i<action[4];++i){
					goal_state[(action[1]*N)+i]=0;
					--goal_painted;
				}
			if(action[2] == action[4])
				for(int i=action[1]; i<action[3];++i){
					goal_state[(i*N)+action[2]]=0;
					--goal_painted;
				}
		}
		
		if(action[0]==ACTIONS.PAINT_SQUARE.ordinal()){
			for (int i=0; i < action[2];++i){
				goal_state[((action[0]+i)*N)+(action[1]+i)]=0;--goal_painted;

				goal_state[(action[0]*N)+(action[1]+i)] = 0;--goal_painted;
				goal_state[(action[0]*N)+(action[1]-i)] = 0;--goal_painted;
				goal_state[((action[0]+i)*N)+action[1]] = 0;--goal_painted;
				goal_state[((action[0]-i)*N)+action[1]] = 0;--goal_painted;
				goal_state[((action[0]+i)*N)+(action[1]+i)]= 0;--goal_painted;
				goal_state[((action[0]+i)*N)+(action[1]-i)]= 0;--goal_painted;
				goal_state[((action[0]-i)*N)+(action[1]+i)]= 0;--goal_painted;
				goal_state[((action[0]-i)*N)+(action[1]-i)]= 0;--goal_painted;
			}
		}
	}

	@Override
	public int compareTo(State o) {
		return o.goal_painted - this.goal_painted;
	}
}
