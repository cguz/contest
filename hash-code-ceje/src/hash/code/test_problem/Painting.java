package hash.code.test_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
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
			
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			
			goal_state=new int[N*M];
			
			for (int i = 0; i < N; ++i) {
				String t = bf.readLine();
				temp2 = t.replaceAll("\\.", "0 ").replaceAll("#", "1 ").split(" ");
				
				for(int j = 0; j < M; ++j){
					goal_state[(i*M)+j] = Integer.parseInt(temp2[j]);
					System.out.println((i*M)+j);
					if(goal_state[(i*M)+j]==1)
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
				System.out.print(root_state.goal_state[(i*root_state.M)+j]+" ");
			}
			System.out.println("\n");
		}
		
		List<int[]> actions;
		
		State state, state_generated;
		
		Queue<State> Q = new LinkedList<State>();
		Q.add(root_state);
		
		while(!Q.isEmpty()){
			state = Q.poll();

			actions = get_actions(state);
			
			
			// int[] i = actions.get(0);
			for(int[] i: actions){
			
				state_generated = regress(i, state);
				
				if(state_generated.goal_painted==0)
					return path(state_generated);
				
				Q.add(state_generated);
			}
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

		List<int[]> act = new ArrayList<int[]>(3);
		
		List<int[]> actions = new ArrayList<int[]>(state.N*state.M);
		List<int[]> actions_erase = new ArrayList<int[]>(state.goal_painted);
		List<int[]> actions_paint_line = new ArrayList<int[]>(state.N*state.M);
		
		boolean stop = false;
		for (int i = 0; i < state.N && !stop; ++i) {
			
			for(int j = 0; j < state.M && !stop; ++j){
				if(state.goal_state[(i*state.M)+j] == 1){
					
					// saving square
					actions.addAll(0, get_actions_square(state, i, j));
					/*if(actions.size()>0 && actions.get(0)[3]>0){
						stop = true;
						break;
					}*/
					
					// saving line
					actions_paint_line.addAll(0, get_actions_line(state, i, j));
					/*if(actions_paint_line.size()>0){
						stop = true;
						break;
					}*/
					
				}else{
					actions_erase.addAll(0, get_actions_erase(state, i, j));
				}
			}
		}

		for(int i = 0; i < actions.size(); ++i){
			if(actions.get(i)[3]>0){
				act.addAll(actions.subList(i, i+1));
			}
		}
		
		int line=-1;
		int mayor = 0;
		for(int i = 0; i < actions_paint_line.size(); ++i){
			if(actions_paint_line.get(i)[5]>=mayor){
				mayor = actions_paint_line.get(i)[5];
				line=i;
			}
		}
		
		if(line != -1)
			act.add(actions_paint_line.get(line));
		
		if(!actions_erase.isEmpty())
			act.add(0,actions_erase.get(0));
		
		return act;
	}

	private List<int[]> get_actions_line(State state, int i, int j) {

		List<int[]> actions_paint_line = new ArrayList<int[]>(0);
		
		int index = i;
		for (int k = i; k < state.N; ++k) {
			if(state.goal_state[(k*state.M)+j] != 1){
				index = k-1;
				break;
			}
		}
		
		// if(index != i){
			actions_paint_line.add(new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,index,j, index-i});
		//}
		
		index = j;
		for (int k = j; k < state.M; ++k) {
			if(state.goal_state[(i*state.M)+k] != 1){
				index = k-1;
				break;
			}
		}
		
		//if(index != j){
			actions_paint_line.add(new int[]{ACTIONS.PAINT_LINE.ordinal(),i,j,i,index, index-j});
		//}
		
		return actions_paint_line;
	}
	
	private List<int[]> get_actions_erase(State state, int i, int j) {

		List<int[]> actions = new ArrayList<int[]>(0);
		
		int index = 1;
		boolean stop=false;
		while (!stop) {
			if((j+index) < state.M && ((j-index) >= 0) && (i+index) < state.N && ((i-index) >= 0)){
				if(state.goal_state[(i*state.M)+(j+index)] == 1 && state.goal_state[(i*state.M)+(j-index)] == 1
				&& state.goal_state[((i+index)*state.M)+j] == 1 && state.goal_state[((i-index)*state.M)+j] == 1
				&& state.goal_state[((i+index)*state.M)+(j+index)] == 1 && state.goal_state[((i-index)*state.M)+(j-index)] == 1
				&& state.goal_state[((i+index)*state.M)+(j-index)] == 1 && state.goal_state[((i-index)*state.M)+(j+index)] == 1){
					++index;
				}else{
					if(state.goal_state[(i*state.M)+(j+index)] == 1 && state.goal_state[(i*state.M)+(j-index)] == 1
						&& state.goal_state[((i+index)*state.M)+(j+index)] == 1 && state.goal_state[((i-index)*state.M)+(j-index)] == 1
						&& state.goal_state[((i+index)*state.M)+(j-index)] == 1 && state.goal_state[((i-index)*state.M)+(j+index)] == 1){
							++index;
						}else { if(state.goal_state[(i*state.M)+(j+index)] == 1 && state.goal_state[(i*state.M)+(j-index)] == 1
							&& state.goal_state[((i+index)*state.M)+j] == 1 && state.goal_state[((i-index)*state.M)+j] == 1
							&& state.goal_state[((i+index)*state.M)+(j-index)] == 1 && state.goal_state[((i-index)*state.M)+(j+index)] == 1){
								++index;
							}
						}
				}
				stop = true;
				break;
			}else{ 
				stop = true;
				break;
			}
		}

		--index;
		if(index==1)
			actions.add(new int[]{ACTIONS.ERASE_CELL.ordinal(),i,j});
		return actions;
	}
	
	private List<int[]> get_actions_square(State state, int i, int j) {

		List<int[]> actions = new ArrayList<int[]>(0);
		
		int index = 0;
		boolean stop=false;
		while (!stop) {
			if((j+index) < state.M && ((j-index) >= 0) && (i+index) < state.N && ((i-index) >= 0)){
				if(state.goal_state[(i*state.M)+(j+index)] == 1 && state.goal_state[(i*state.M)+(j-index)] == 1
				&& state.goal_state[((i+index)*state.M)+j] == 1 && state.goal_state[((i-index)*state.M)+j] == 1
				&& state.goal_state[((i+index)*state.M)+(j+index)] == 1 && state.goal_state[((i-index)*state.M)+(j-index)] == 1
				&& state.goal_state[((i+index)*state.M)+(j-index)] == 1 && state.goal_state[((i-index)*state.M)+(j+index)] == 1){
					++index;
				}else{
					stop = true;
					break;
				}
			}else{ 
				stop = true;
				break;
			}
		}

		--index;
		actions.add(0,new int[]{ACTIONS.PAINT_SQUARE.ordinal(),i,j,index});
		return actions;
	}

}