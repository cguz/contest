package hash.code.test_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * @Challenge Painting
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

			BufferedReader bf = new BufferedReader(new FileReader("/home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje/input/example.txt"));
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
			
			
			// SHOW THE PATH
			for(int[] i: path){
				if(i[0]==ACTIONS.ERASE_CELL.ordinal()){
					System.out.println(ACTIONS.ERASE_CELL.toString()+" "+i[1]+" "+i[2]);
				}
				if(i[0]==ACTIONS.PAINT_LINE.ordinal()){
					System.out.println(ACTIONS.PAINT_LINE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+" "+i[4]+" "+i[5]);
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
		
		/*for (int i = 0; i < root_state.N; ++i) {
			
			for(int j = 0; j < root_state.M; ++j){
				System.out.print(root_state.goal_state[(i*root_state.M)+j]+" ");
			}
			System.out.println("\n");
		}*/
		
		// List<int[]> actions;
		
		State state, state_generated;
		
		Queue<State> Q = new LinkedList<State>();
		Q.add(root_state);
		
		while(!Q.isEmpty()){
			state = Q.poll();

			int[] i = get_actions(state);
			//actions = get_actions(state);
			//for(int[] i: actions){
			
				state_generated = regress(i, state);

				/*System.out.println("\n");
				System.out.println("\n");
				for (int k = 0; k < state_generated.N; ++k) {
				
					for(int j = 0; j < state_generated.M; ++j){
						System.out.print(state_generated.goal_state[(k*state_generated.M)+j]+" ");
					}
					System.out.println("\n");
				}*/
				
				if(state_generated.goal_painted==0)
					return path(state_generated);
				
				Q.add(state_generated);
			// }
				
				if((Q.size() % 400)==0)
					System.out.println(Q.size());
		}
		
		return new ArrayList<int[]>(0);
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

	private int[] get_actions(State state) {
		
		int[] act_square=null;
		int[] act_lineal=null;
		int[] act_erase=null;
		
		for (int i = 0; i < state.N && (act_erase == null); ++i) {
			
			for(int j = 0; j < state.M && (act_erase == null); ++j){
				if(state.goal_state[(i*state.M)+j] == 1){
					
					// saving square
					act_erase = get_actions_square(state, i, j);
					if(act_square == null)
						act_square = act_erase;
					else{
						if(act_erase!= null && act_erase[3] > act_square[3])
							act_square = act_erase;
					}
					act_erase= null;
					
					// saving line
					act_erase = get_actions_line(state, i, j);
					if(act_lineal == null)
						act_lineal = act_erase;
					else{
						if(act_erase!= null && act_erase[5] > act_lineal[5])
							act_lineal = act_erase;
					}
					act_erase= null;
					
				}else{
					act_erase = get_actions_erase(state, i, j);;
				}
			}
		}

		if(act_square != null)
			return act_square;
		
		if(act_erase != null)
			return act_erase;
			
		if(act_lineal!=null)
			return act_lineal;
		
		return null;
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
			return new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,horizontal-i,j, horizontal};
		
		return new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,i,index, index-j};

	}
	
	private int[] get_actions_erase(State state, int R, int C) {

		
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