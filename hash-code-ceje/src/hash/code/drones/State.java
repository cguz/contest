package hash.code.drones;

import java.util.List;

public class State {

	List<int[]> warehouses;
	List<int[]> orders; // goals
	
	int orders_total;
	List<int[]> drones;

	ACTIONS type_action;
	int[] action;
	State parent;
	
	
	public State(int d, List<int[]> warehouses, List<int[]> orders, int orders_total, List<int[]> drones) {
		
		this.warehouses = warehouses; 
		this.orders = orders;
		this.orders_total = orders_total;
		this.drones = drones;
		
		
	}
	
	public void execute(){
		switch(type_action){
			case DELIVER:
				
				break;			
			case LOAD:
				
				break;
			case UNLOAD:
				
				break;
			case WAIT: 
				
				break;
		}
		
	}
	
}
