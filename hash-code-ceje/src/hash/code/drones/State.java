package hash.code.drones;

import java.util.List;

public class State {

	private List<int[]> warehouses;
	private List<int[]> orders;

	public State(List<int[]> warehouses, List<int[]> orders) {
		
		this.warehouses = warehouses; 
		this.orders = orders;
		
	}
	
	
	
}
