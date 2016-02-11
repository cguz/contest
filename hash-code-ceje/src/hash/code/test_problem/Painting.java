package hash.code.test_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import hash.code.tools.Files;


/**
 * @Challenge Painting
 */
public class Painting {

	public int[] goal_state;
	public int   goal_painted=0;
	private int heuristic;
	
	
	public static void main(String[] args) {
		Painting challenge = new Painting();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String[] sCadena = null;
		try {
			// /home/zenshi/git/hash-code/hash-code-ceje/input
			// /home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje
			BufferedReader bf = new BufferedReader(new FileReader("/home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje/input/right_angle.in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			
			//******************************//
			//**********READ THE FILE*******//
			//******************************//
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			
			goal_state=new int[N*M];
			
			for (int i = 0; i < N; ++i) {
				String t = bf.readLine();
				temp2 = t.replaceAll("\\.", "0 ").replaceAll("#", "1 ").split(" ");
				
				for(int j = 0; j < M; ++j){
					goal_state[(i*M)+j] = Integer.parseInt(temp2[j]);
					if(goal_state[(i*M)+j]==1)
						goal_painted++;
				}
				
			}

			// FIND A PATH
			List<int[]> path = find_path(new State(goal_state, goal_painted, N, M));
			
			save(path);

			System.out.println(output+"\nscore:"+(goal_painted-path.size()));
		
		} catch (IOException e) {
		}
	}

	private List<int[]> find_path(State root_state) {
		
		List<int[]> actions;
		List<int[]> best_solution=null;
		List<int[]> sol;
		int total_solutions = 0, max_solutions = 1000;
		
		State state, state_generated;
		
		Queue<State> Q = new PriorityQueue<State>();
		Q.add(root_state);
		
		while(!Q.isEmpty()){
			state = Q.poll();
			
			heuristic = state.goal_painted;
			
			// int[] i = get_actions(state);
			actions = get_actions(state);
			for(int[] i: actions){
			
				state_generated = regress(i, state);
				
				if(state_generated.goal_painted==0){
					++total_solutions;
					if(best_solution==null){
						best_solution = save(state_generated.path());
					}else{
						sol = state_generated.path();
						if(sol.size()<best_solution.size())
							best_solution = save(sol);
					}
					
					if(total_solutions >= max_solutions){
						return best_solution;	
					}
				}
				
				if(state_generated.goal_painted < heuristic){
					heuristic = state_generated.goal_painted;
					if(heuristic!=0)
						Q.add(state_generated);
				}
			}
		}
		
		return best_solution;
	}

	private List<int[]> save(List<int[]> path) {
		// if (T >= 1 && T <= 20){
		System.out.println("new solution size: "+path.size());
		
		String output = "";
		output = output + (path.size())+"\n";
		for(int j = path.size()-1; j>=0; --j){
			int[] i = path.get(j);
			if(i[0]==ACTIONS.ERASE_CELL.ordinal()){
				output = output + ACTIONS.ERASE_CELL.toString()+" "+i[1]+" "+i[2]+"\n";
			}
			if(i[0]==ACTIONS.PAINT_LINE.ordinal()){
				output = output + ACTIONS.PAINT_LINE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+" "+i[4]+"\n";
			}
			if(i[0]==ACTIONS.PAINT_SQUARE.ordinal()){
				output = output + ACTIONS.PAINT_SQUARE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+"\n";
			}
		}  
		
		Files file = new Files("output", false);
		file.write(output);
		file.close();
		
		// }
		return path;
	}
	
	private State regress(int[] i, State state) {
		
		State new_state = new State(i, state);
		
		new_state.regress_action();
		
		return new_state;
	}

	private List<int[]> get_actions(State state) {
		
		int[] act_square=null;
		int[] act_lineal=null;
		int[] act_temp	=null;
		List<int[]> act_erase= new ArrayList<int[]>();
		
		List<int[]> act = new ArrayList<int[]>(3);
		
		for (int i = 0; i < state.N && (act_temp == null); ++i) {
			
			for(int j = 0; j < state.M && (act_temp == null); ++j){
				if(state.goal_state[(i*state.M)+j] == 1){
					
					// saving square
					act_temp = get_actions_square(state, i, j);
					if(act_square == null)
						act_square = act_temp;
					else{
						if(act_temp!= null && act_temp[3] > act_square[3])
							act_square = act_temp;
					}
					act_temp= null;
					
					// saving line
					act_temp = get_actions_line(state, i, j);
					if(act_lineal == null)
						act_lineal = act_temp;
					else{
						if(act_temp!= null && act_temp[5] > act_lineal[5])
							act_lineal = act_temp;
					}
				}else{
					act_temp = get_actions_erase_middle(state, i, j);
					if(act_temp != null)
						act_erase.add(act_temp);
				}
				
				act_temp= null;
			}
		}

		if(act_square != null)
			act.add(act_square);
		
		if(!act_erase.isEmpty())
			act.addAll(0,act_erase);
			
		if(act_lineal!=null)
			act.add(act_lineal);
		
		return act;
	}

	private int[] get_actions_line(State state, int i, int j) {

		int index = i;
		for (int k = i; k < state.N; ++k) {
			if(state.goal_state[(k*state.M)+j] == 1){
				index = k;
			}else{ break; }
		}
		
		int horizontal = index-i;
		
		index = j;
		for (int k = j; k < state.M; ++k) {
			if(state.goal_state[(i*state.M)+k] == 1){
				index = k;
			}else{ break; }
		}
		
		if(horizontal > (index-j))	
			return new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,horizontal+i,j, horizontal};
		
		return new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,i,index, index-j};

	}
	
	private int[] get_actions_erase_middle(State state, int R, int C) {

		
		int S = 1;
		int total = 0;
		int[] RC = new int[2];
		if((R+S) < state.N && ((R-S) >= 0) && (C+S) < state.M && ((C-S) >= 0)){

			int top_i = R-S;
			int left_j = C-S;
			
			int bottom_i = R+S;
			int right_j = C+S;
			
			for (int i = top_i; i <= bottom_i; ++i) {
				for(int j = left_j; j <= right_j; ++j){
					total = total + state.goal_state[(i*state.M)+j];
					if(state.goal_state[(i*state.M)+j]==0){
						RC[0]=i;
						RC[1]=j;
					}
				}
			}
		}

		if(total == 8)
			return new int[]{ACTIONS.ERASE_CELL.ordinal(),R,C};
		
		if(total == 7)
			return new int[]{ACTIONS.ERASE_CELL.ordinal(),RC[0],RC[1]};
		
		return null;
	}
	
	private int[] get_actions_square(State state, int R, int C) {

		int S = 0;
		boolean stop=false;
		while (!stop) {
			int total = 0;
			if((R+S) < state.N && ((R-S) >= 0) && (C+S) < state.M && ((C-S) >= 0)){

				int top_i = R-S;
				int left_j = C-S;
				
				int bottom_i = R+S;
				int right_j = C+S;
				
				for (int i = top_i; i <= bottom_i; ++i) {
					for(int j = left_j; j <= right_j; ++j){
						total = total + state.goal_state[(i*state.M)+j];
					}
				}
			}
			
			if(total == (((2*S)+1)*((2*S)+1))){
				++S;
			}else{
				stop = true;
				break;
			}
		}

		--S;
		if(S>0)
			return new int[]{ACTIONS.PAINT_SQUARE.ordinal(),R,C,S};
		return null;
	}

}