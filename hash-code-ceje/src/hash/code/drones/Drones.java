package hash.code.drones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import hash.code.test_problem.ACTIONS;
import hash.code.test_problem.State;
import hash.code.tools.Files;


/**
 * @Challenge StreetViewRouting
 */
public class Drones {

	public ArrayList<Junction> junctions = new ArrayList<Junction>();

	
	public static void main(String[] args) {
		Drones challenge = new Drones();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String[] sCadena = null;
		try {

			BufferedReader bf = new BufferedReader(new FileReader("/home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje/input/paris_54000.txt"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			int T = Integer.parseInt(temp[2]);
			int C = Integer.parseInt(temp[3]);
			int S = Integer.parseInt(temp[4]);
			
			for (int i = 1; i <= N; ++i) {
				
				bf.readLine();
				junctions.add(new Junction(-1, -1));
				
				// System.out.println(bf.readLine());
				
				/*temp2 = bf.readLine().split(" ");
				city.junctions.add(new Junction(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2])));*/
				
			}
			
			for (int i = 1; i <= M; ++i) {
				
				temp2 = bf.readLine().split(" ");
				
				junctions.get(Integer.parseInt(temp2[0])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[1]), 
						Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4])));
				
				if(Integer.parseInt(temp2[2])==2){
					junctions.get(Integer.parseInt(temp2[1])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[0]), 
							Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4])));
				}
				
				
			}

			
			// FIND A PATH
			List<int[]> path = find_path();
			
			save(path);

			System.out.println(output+"\nscore:");
		
		} catch (IOException e) {
		}
	}
	

	private List<int[]> find_path() {
		
		
		State state, state_generated;
		
		/*Queue<State> Q = new PriorityQueue<State>();
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
		
		return best_solution;*/
		return null;
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

}