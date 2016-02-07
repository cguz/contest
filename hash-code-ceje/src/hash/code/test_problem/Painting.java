package hash.code.test_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;


/**
 * @Challenge StreetViewRouting
 */
public class Painting {

	public int[] goal_state;
	public int   goal_painted=0;
	
	
	public static void main(String[] args) {
		Painting challenge = new Painting();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String[] sCadena = null;
		try {

			BufferedReader bf = new BufferedReader(new FileReader("/home/zenshi/git/hash-code/hash-code-ceje/input/example.txt"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			
			goal_state=new int[N*M];
			
			for (int i = 0; i < N; ++i) {
				String t = bf.readLine();
				temp2 = t.replaceAll("\\.", "0 ").replaceAll("#", "1 ").split(" ");
				
				for(int j = 0; j < M; ++j){
					goal_state[(i*N)+j] = Integer.parseInt(temp2[j]);
					if(goal_state[(i*N)+j]==1)
						goal_painted++;
				}
				
			}

			List<int[]> path = find_path(new State(goal_state, goal_painted, N, M));
			
			for(int[] i: path){
				if(i[0]==ACTIONS.ERASE_CELL.ordinal()){
					System.out.println(ACTIONS.ERASE_CELL.toString()+" "+i[1]+" "+i[2]);
				}
				if(i[0]==ACTIONS.PAINT_LINE.ordinal()){
					System.out.println(ACTIONS.PAINT_LINE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+" "+i[4]);
				}
				if(i[0]==ACTIONS.PAINT_SQUARE.ordinal()){
					System.out.println(ACTIONS.PAINT_SQUARE.toString()+" "+i[1]+" "+i[2]+" "+i[3]);
				}
			}
			
			/*// sint T = Integer.parseInt(bf.readLine());
			if (T >= 1 && T <= 20)
				for (int i = 1; i <= T; ++i) {
					sCadena = bf.readLine().split(" ");
					output = output + "Case #"+i+": "+fontSizer(sCadena)+"\n";
				}
			
			Files file = new Files("output", false);
			file.write(output);
			file.close();*/
		} catch (IOException e) {
		}
	}

	private List<int[]> find_path(State root_state) {
		
		for (int i = 0; i < root_state.N; ++i) {
			
			for(int j = 0; j < root_state.M; ++j){
				System.out.print(root_state.goal_state[(i*root_state.N)+j]+" ");
			}
			System.out.println("\n");
		}
		
		List<int[]> actions;
		
		State state, state_generated;
		
		Queue<State> Q = new PriorityQueue<State>();
		Q.add(root_state);
		
		while(!Q.isEmpty()){
			state = Q.poll();
			
			actions = get_actions(state);
			int[] i = actions.get(0);
			//for(int[] i: actions){
			
			state_generated = regress(i, state);
			
			if(state_generated.goal_painted==0)
				return path(state_generated);
			
			Q.add(state_generated);
			//}
		}
		
		return null;
	}

	private List<int[]> path(State state_generated) {
		
		List<int[]> path = new ArrayList<int[]>();
		
		while(state_generated.parent!=null){
			path.add(state_generated.action);
			state_generated=state_generated.parent;
		}
		
		return path;
	}

	private State regress(int[] i, State state) {
		
		State new_state = new State(i, state);
		
		new_state.regress_action();
		
		return new_state;
	}

	private List<int[]> get_actions(State state) {

		List<int[]> actions = new ArrayList<int[]>(state.N*state.M);
		List<int[]> actions_erase = new ArrayList<int[]>(state.goal_painted);
		List<int[]> actions_paint_line = new ArrayList<int[]>(state.N*state.M);
		
		boolean stop = false;
		for (int i = 0; i < state.N && !stop; ++i) {
			
			for(int j = 0; j < state.M && !stop; ++j){
				if(state.goal_state[(i*state.N)+j] == 1){
					
					// saving erase cell
					actions_erase.add(new int[]{ACTIONS.ERASE_CELL.ordinal(),i,j});
					
					// saving line
					actions_paint_line = get_actions_line(state, i, j);
					if(actions_paint_line.size()>0){
						stop = true;
						break;
					}
					
					// saving square
					actions = get_actions_square(state, i, j);
					if(actions.size()>0){
						stop = true;
						break;
					}
					
				}
			}
		}

		if(!actions.isEmpty())
			return  actions;
		if(!actions_paint_line.isEmpty())
			return  actions_paint_line;
		return actions_erase;
	}

	private List<int[]> get_actions_line(State state, int i, int j) {

		List<int[]> actions_paint_line = new ArrayList<int[]>(0);
		
		int index = i;
		for (int k = i; k < state.N; ++k) {
			if(state.goal_state[(k*state.N)+j] != 1){
				index = k;
				break;
			}
		}
		
		if(index != i){
			actions_paint_line.add(new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,index,j});
		}
		
		index = j;
		for (int k = j; k < state.M; ++k) {
			if(state.goal_state[(i*state.N)+k] != 1){
				index = k;
				break;
			}
		}
		
		if(index != i){
			actions_paint_line.add(new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,i,index});
		}
		
		return actions_paint_line;
	}
	
	private List<int[]> get_actions_square(State state, int i, int j) {

		List<int[]> actions = new ArrayList<int[]>(0);
		
		int index = 0;
		boolean stop=false;
		while (!stop) {
			if((j+index) < state.M && ((j-index) >= 0) && (i+index) < state.N && ((i-index) >= 0)){
				if(state.goal_state[(i*state.N)+(j+index)] != 1 && state.goal_state[(i*state.N)+(j-index)] != 1
				&& state.goal_state[((i+index)*state.N)+j] != 1 && state.goal_state[((i-index)*state.N)+j] != 1
				&& state.goal_state[((i+index)*state.N)+(j+index)] != 1 && state.goal_state[((i-index)*state.N)+(j-index)] != 1
				&& state.goal_state[((i+index)*state.N)+(j-index)] != 1 && state.goal_state[((i-index)*state.N)+(j+index)] != 1){
					stop = true;
					break;
				}
			}else{ 
				stop = true;
				break;
			}
			
			++index;
		}
		
		actions.add(new int[]{ACTIONS.PAINT_SQUARE.ordinal(),i,j,index});
		return actions;
	}

}