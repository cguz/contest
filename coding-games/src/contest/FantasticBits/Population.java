/*
* Population.java
* Manages a population of candidate individuals
*/

package contest.FantasticBits;

import contest.FantasticBits.game.GameActions;
import contest.FantasticBits.game.Plan;

public class Population {

    // Holds population of individuals
    Individual[] individuals;

    // Construct a population
    public Population(int populationSize, boolean initialise) {
    	
        individuals = new Individual[populationSize];
        
        // If we need to initialize a population of plans do so
        if (initialise) {
        	
            // Loop and create individuals
            for (int i = 0; i < populationSize(); i++) {
                Individual newPlan = new Plan(GameActions.getGame());
                ((Plan)newPlan).generateIndividual();
                savePlan(i, newPlan);
            }
        }
    }
    
    // Saves a plan
    public void savePlan(int index, Individual plan) {
        individuals[index] = plan;
    }
    
    // Gets a plan from population
    public Individual getPlan(int index) {
        return individuals[index];
    }

    // Gets the best plan in the population
    public Individual getFittest() {
        Individual fittest = getPlan(0);
        
        // Loop through individuals to:
        for (int i = 1; i < populationSize(); i++) {
        	
        	//find the best 
        	if (fittest.getFitness() > getPlan(i).getFitness()) {
                fittest = getPlan(i);
            }
        	
        	// sort the elements with the insertion sort algorithm
            // Time complexity: worst/average O(n^2), Best: O(n)
        	Individual temp = getPlan(i);
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
        return individuals.length;
    }
}