package hash_code.contest_2016.round_qualification.drones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import hash_code.tools.Files;


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
	
	int[] product_types_weigh;
	
	private int heuristic;

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
			product_types_weigh = new int[Integer.parseInt(bf.readLine())];
			
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
			int orders_total = 0;
			for(i=0; i < total_orders; ++i){
				temp = bf.readLine().split(" "); // <x, y, 0,..,p>
				
				bf.readLine();
				
				temp2 = bf.readLine().split(" ");
				
				int[] temp_content = new int[2+product_types_weigh.length];
				temp_content[0]=Integer.parseInt(temp[0]);
				temp_content[1]=Integer.parseInt(temp[1]);

				for(String t: temp2){
					temp_content[Integer.parseInt(t)]+= 1;
					orders_total++;
				}
				
				orders.add(temp_content);
				
			}

			List<int[]> drones = new ArrayList<int[]>(D);  // <x, y, w, 0,..,p>
			for(i = 0; i < D; ++i){
				int[] t = new int[2+1+product_types_weigh.length];
				t[0] = warehouses.get(0)[0];
				t[1] = warehouses.get(0)[1];
				drones.add(t);
			}
			
			
			// FIND A PATH
			find_path(new State(warehouses, orders, orders_total, drones));

			System.out.println(output+"\nscore:");

			
			bf.close();
		} catch (IOException e) {
		}
	}
	

	private void find_path(State state) {
		
		while(state.time < deadline){
			
			System.out.println(state.orders_total);
			
			if(state.orders_total > heuristic){
				heuristic = state.orders_total;
				// save(state.path());
			}
			
			// int[] i = get_actions(state);
			get_actions(state);
			state = state.execute();
			// state.type_action=type_action;
			
		}
		
	}

	//in:	dron warehouse type numberProduct

	private void get_actions(State state) {
		
		get_actions_load(state);

		List<int[]> actions_deliver = get_actions_deliver(state);
		
	}


	private List<int[]> get_actions_deliver(State state) {
		int p = 0;  
		
		List<int[]> action = new ArrayList<int[]>();
		
		for(int j = 0; j <state.orders.size(); ++j){// <x, y, 0,..,p>
			
			int[] o = state.orders.get(j);
			
			while(p < product_types_weigh.length){
				
				if(o[p+2] > 0){
					for(int i = 0; i <state.drones.size(); ++i){ // <x, y, w, 0,..,p> 
						
						int[] d = state.drones.get(i);
						
						if(d[p+3] > 0){

							int diff = d[p+3] - o[p+2];
							
							if(diff <= 0){
								state.action.add(new int[]{i, j, p, d[p+3]});
							} else {
								state.action.add(new int[]{i, j, p, o[p+2]});
							}
							state.type_action.add(ACTIONS.DELIVER);
						}						
					}
				}
				
				++p;
			}
		}
		
		return action;
	}
	

	private void get_actions_load(State state) {
		int p = 0;  
		
		for(int j = 0; j <state.warehouses.size(); ++j){// <x, y, 0,..,p>
			
			int[] o = state.warehouses.get(j);
			
			while(p < product_types_weigh.length){
				
				if(o[p+2] > 0){
					int products = o[p+2];
					
					for(int i = 0; i <state.drones.size() && products >0; ++i){ // <x, y, w, 0,..,p> 
						
						int[] d = state.drones.get(i);

						int diff = (d[3] + (products*product_types_weigh[p])) - maximum_load;

						//in:	dron warehouse type numberProduct
						if(diff <= 0){
							state.action.add(new int[]{i, j, p, o[p+2], product_types_weigh[p]});
							products-= o[p+2];

							state.type_action.add(ACTIONS.LOAD);
						}
					}
				}
				
				++p;
			}
		}
		
	}

	private int distance(int ax, int ay, int bx, int by) {
		//euclidean distance
		return (int) Math.round(Math.sqrt(Math.pow((ax - bx), 2) + Math.pow((ay - by), 2)));
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