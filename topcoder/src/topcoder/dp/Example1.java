package topcoder.dp;


/**
 * Given a list of N coins, their values (V1, V2, … , VN), and the total sum S. 
 * Find the minimum number of coins the sum of which is S (we can use as many 
 * coins of one type as we want), or report that it’s not possible to select 
 * coins in such a way that they sum up to S. 
 * @author Cesar Augusto Guzman Alvarez
 */
public class Example1 {

    public Example1() {}


    public static void main(String[] args){
    	System.out.println(minSums(new int[]{1,3,5}, 1));
    }
    
    // dynamic programming
    public static int minSums(int[] coins, int sum) {
    	// in order to include the number zero.
    	sum++;
    	
    	// Set Min[i] equal to Infinity for all of i
    	// This variable save the count of total coins
    	int[] Min = new int[sum];
    	for(int i=0; i < sum; ++i)
    		Min[i] = 10000000;
    	
    	// first optimal solution, sum = 0 with 0 coins
    	Min[0] = 0;
    	
    	// For i = 1 to S
    	for(int i=1; i < sum; ++i){
    		// For j = 0 to N - 1
    		for(int j=0; j < coins.length; j++){
    			// IF the value of the coin is less or equal than the sum i 
    			// AND the total coins of the next sum is less than the evaluated i total coins
    			if(coins[j] <= i && Min[i-coins[j]]+1 < Min[i]){
    				// We save the total coins in the evaluated i
    				Min[i] = Min[i-coins[j]]+1;
    			}
    		}
    	}

    	return Min[sum-1];
       
    }

}