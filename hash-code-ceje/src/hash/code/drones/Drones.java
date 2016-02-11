package hash.code.drones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import hash.code.tools.Files;


/**
 * @Challenge StreetViewRouting
 */
public class Drones {

	// public ArrayList<State> junctions = new ArrayList<Junction>();

	
	public static void main(String[] args) {
		Drones challenge = new Drones();
		
		challenge.begin(args);
		
	}

	private int rows;
	private int columns;
	private int D;
	private int deadline;
	private int maximum_load;

	private void begin(String[] args) {
		String output = "";
		try {

			BufferedReader bf = new BufferedReader(new FileReader("/home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje/input/example.txt"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			rows = Integer.parseInt(temp[0]);
			columns = Integer.parseInt(temp[1]);
			D = Integer.parseInt(temp[2]);
			deadline = Integer.parseInt(temp[3]);
			maximum_load = Integer.parseInt(temp[4]);
			
			// different product types
			int[] product_types_weigh = new int[Integer.parseInt(bf.readLine())];
			
			// product types
			temp = bf.readLine().split(" ");
			int i=0;
			for(String t: temp)
				product_types_weigh[i++]=Integer.parseInt(t);
			
			int total_warehouse = Integer.parseInt(bf.readLine());
			List<int[]> warehouses = new ArrayList<int[]>(total_warehouse);
			for(i=0; i < total_warehouse; ++i){
				temp = bf.readLine().split(" "); // <x, y, 0,..,p>
				
				int[] temp_content = new int[2+product_types_weigh.length];
				temp_content[0]=Integer.parseInt(temp[0]);
				temp_content[1]=Integer.parseInt(temp[1]);
				
				temp = bf.readLine().split(" ");
				int j = 2;
				for(String t: temp)
					temp_content[j++] = Integer.parseInt(t);
				
				warehouses.add(temp_content);
				
			}
			
			int total_orders = Integer.parseInt(bf.readLine());
			List<int[]> orders = new ArrayList<int[]>(total_orders);
			for(i=0; i < total_orders; ++i){
				temp = bf.readLine().split(" "); // <x, y, 0,..,p>
				
				bf.readLine();
				
				temp2 = bf.readLine().split(" ");
				
				int[] temp_content = new int[2+product_types_weigh.length];
				temp_content[0]=Integer.parseInt(temp[0]);
				temp_content[1]=Integer.parseInt(temp[1]);
				// inicializar a zero

				for(String t: temp2)
					temp_content[Integer.parseInt(t)]+= 1;
				
				orders.add(temp_content);
				
			}

			// FIND A PATH
			List<int[]> path = find_path(new State(warehouses, orders));
			
			// save(path);

			System.out.println(output+"\nscore:");
		
		} catch (IOException e) {
		}
	}
	

	private List<int[]> find_path(State state) {
		
		
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
			/*if(i[0]==ACTIONS.ERASE_CELL.ordinal()){
				output = output + ACTIONS.ERASE_CELL.toString()+" "+i[1]+" "+i[2]+"\n";
			}
			if(i[0]==ACTIONS.PAINT_LINE.ordinal()){
				output = output + ACTIONS.PAINT_LINE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+" "+i[4]+"\n";
			}
			if(i[0]==ACTIONS.PAINT_SQUARE.ordinal()){
				output = output + ACTIONS.PAINT_SQUARE.toString()+" "+i[1]+" "+i[2]+" "+i[3]+"\n";
			}*/
		}  
		
		Files file = new Files("output", false);
		file.write(output);
		file.close();
		
		// }
		return path;
	}

}