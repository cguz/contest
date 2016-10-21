/*
* Population.java
* Manages a population of candidate plans
*/

package contest.theaccountant;


public class Population {

    // Holds population of plans
    Plan[] plans;

    // Construct a population
    public Population(int populationSize, boolean initialise) {
    	
        plans = new Plan[populationSize];
        
        // If we need to initialize a population of plans do so
        if (initialise) {
        	
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++) {
                Plan newPlan = new Plan(GameActions.getGame());
                newPlan.generateIndividual();
                savePlan(i, newPlan);
            }
        }
    }
    
    // Saves a plan
    public void savePlan(int index, Plan plan) {
        plans[index] = plan;
    }
    
    // Gets a plan from population
    public Plan getPlan(int index) {
        return plans[index];
    }

    // Gets the best plan in the population
    public Plan getFittest() {
        Plan fittest = getPlan(0);
        
        // Loop through individuals to:
        // 1) find the best
        // 2) sort the elements with the insertion sort algorithm
        // Time complexity: worst/average O(n^2), Best: O(n)
        for (int i = 1; i < populationSize(); i++) {
        	// 1) find the best 
        	if (fittest.getFitness() > getPlan(i).getFitness()) {
                fittest = getPlan(i);
            }
        	
        	// 2) sort the elements
        	Plan temp = getPlan(i);
        	int j = i-1;
            while(j>=0 && getPlan(j).getFitness() > temp.getFitness()){
            	savePlan(j+1, getPlan(j));
            	j--;
            }
        	savePlan(j+1, temp);
            
        }
        return fittest;
    }

    // Gets population size
    public int populationSize() {
        return plans.length;
    }
}