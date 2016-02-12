package hash.code.drones;

import java.util.ArrayList;
import java.util.List;

public class State {

	List<int[]> warehouses;
	List<int[]> orders; // goals
	
	int orders_total;
	List<int[]> drones;

	int[] t_drone;

	List<ACTIONS> type_action = new ArrayList<ACTIONS>();
	List<int[]> action = new ArrayList<int[]>();
	State parent;
	
	int time = 0; 
	
	public State(List<int[]> warehouses, List<int[]> orders, int orders_total, List<int[]> drones) {
		
		this.warehouses = warehouses; 
		this.orders = orders;
		this.orders_total = orders_total;
		this.drones = drones;
		

		t_drone = new int[drones.size()];
		
	}
	
	public State execute(){
		
		int max = 0;
		
		for(int i=0; i < type_action.size(); ++i){

			switch(type_action.get(i)){
				case DELIVER:
					//in:	dron numOrder type numberProduct 
					//out:	dron D numOrder type numberProduct
					
					//calculate the time
					if(this.drones.get(action.get(i)[0])[0] != this.orders.get(action.get(i)[1])[0] || this.drones.get(action.get(i)[0])[1] != this.orders.get(action.get(i)[1])[1]) {
						t_drone[action.get(i)[0]] += distance(this.drones.get(action.get(i)[0])[0], this.drones.get(action.get(i)[0])[1], this.orders.get(action.get(i)[1])[0], this.orders.get(action.get(i)[1])[1]);
					}
					t_drone[action.get(i)[0]] += 1;
					
					if(t_drone[action.get(i)[0]] > max)
						max = t_drone[action.get(i)[0]];
					
					this.drones.get(action.get(i)[0])[action.get(i)[2]+3]-=action.get(i)[3];
					this.orders.get(action.get(i)[1])[action.get(i)[2]+2]+=action.get(i)[3];
	
					orders_total-=action.get(i)[3];
					
					//increase the weight
					this.drones.get(action.get(i)[0])[3]-= action.get(i)[3] * action.get(i)[4];
					
					
					break;			
				case LOAD:
					//in:	dron warehouse type numberProduct
					//out:	dron L warehouse type numberProduct
					
					//calculate the time
					if(this.drones.get(action.get(i)[0])[0] != this.warehouses.get(action.get(i)[1])[0] || this.drones.get(action.get(i)[0])[1] != this.warehouses.get(action.get(i)[1])[1]) {
						t_drone[action.get(i)[0]] += distance(this.drones.get(action.get(i)[0])[0], this.drones.get(action.get(i)[0])[1], this.warehouses.get(action.get(i)[1])[0], this.warehouses.get(action.get(i)[1])[1]);
					}
					t_drone[action.get(i)[0]] += 1;
					
					if(t_drone[action.get(i)[0]] > max)
						max = t_drone[action.get(i)[0]];
					
					//pick up products  // <x, y, w, 0,..,p>
					this.drones.get(action.get(i)[0])[action.get(i)[2]+3]+=action.get(i)[3];
					this.warehouses.get(action.get(i)[1])[action.get(i)[2]+2]-=action.get(i)[3];
	
					
					//increase the weight
					this.drones.get(action.get(i)[0])[3]+= action.get(i)[3] * action.get(i)[4];
					
					break;
				case UNLOAD:
					//in:	dron warehouse type numberProduct 
					//out:	dron U warehouse type numberProduct 
					
					//calculate the time
					if(this.drones.get(action.get(i)[0])[0] != this.warehouses.get(action.get(i)[1])[0] || this.drones.get(action.get(i)[0])[1] != this.warehouses.get(action.get(i)[1])[1]) {
						t_drone[action.get(i)[0]] += distance(this.drones.get(action.get(i)[0])[0], this.drones.get(action.get(i)[0])[1], this.warehouses.get(action.get(i)[1])[0], this.warehouses.get(action.get(i)[1])[1]);
					}
					t_drone[action.get(i)[0]] += 1;
					
					if(t_drone[action.get(i)[0]] > max)
						max = t_drone[action.get(i)[0]];
					
					//pick up products  // <x, y, w, 0,..,p>
					this.drones.get(action.get(i)[0])[action.get(i)[2]+3]-=action.get(i)[3];
					this.warehouses.get(action.get(i)[1])[action.get(i)[2]+2]+=action.get(i)[3];
	
					//increase the weight
					this.drones.get(action.get(i)[0])[3]-= action.get(i)[3] * action.get(i)[4];
					
					
					break;
				
				case WAIT: 
					//in:	dron time
					//out:	dron W time 
					
					//calculate the time
					t_drone[action.get(i)[0]] += action.get(i)[1];
					
					if(t_drone[action.get(i)[0]] > max)
						max = t_drone[action.get(i)[0]];
					
					break;
			}
		
		}
		
		return this;
	}

	private int distance(int ax, int ay, int bx, int by) {
		//euclidean distance
		return (int) Math.round(Math.sqrt(Math.pow((ax - bx), 2) + Math.pow((ay - by), 2)));
	}
	
}
