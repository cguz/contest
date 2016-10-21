/*
* GA.java
* Manages algorithms for evolving population
*/

package contest.theaccountant;

import contest.theaccountant.Plan.Command;

public class GAManager {

    public enum STYPE {
    	TOURNAMENT, RWS, MIX_RWS_TOURNAMENT;
	}

	/* parameters */
    public static double 	mutationRate = 0.015;
    public static int 		tournamentSize = 5;
    public static boolean   elitism = true;
	
    public static STYPE		SELECTION=STYPE.TOURNAMENT;
    
	public static double 	RWS_POINTERS = 0.05;
    

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
    	
        // Select population variables
        Plan parent1;
        Plan parent2;
        
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.savePlan(0, pop.getFittest());
            elitismOffset = 1;
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {

            switch(SELECTION){
	            default: case TOURNAMENT:
	            	parent1 = tournamentSelection(pop);
	                parent2 = tournamentSelection(pop);
	                break;
	            case RWS:
	            	parent1 = RWSelection(pop);
	                parent2 = RWSelection(pop);
	                break;
	            case MIX_RWS_TOURNAMENT:
	            	parent1 = tournamentSelection(pop);
	                parent2 = RWSelection(pop);
	                break;
	            	
            }
            
        	// System.out.println("[evolvePopulation] plan parent1 size: "+parent1.planCommandSize());
        	// System.out.println("[evolvePopulation] plan parent2 size: "+parent2.planCommandSize());
        	
            // Crossover parents
            Plan child = crossover(parent1, parent2);
            
            // Add child to new population
            newPopulation.savePlan(i, child);
            
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getPlan(i));
        }

        return newPopulation;
    }

    // Applies crossover to a set of parents and creates offspring
    public static Plan crossover(Plan parent1, Plan parent2) {
    	
        // Create new child plan
        Plan child = new Plan(parent1.getGame());

        // Get start and end sub tour positions for parent1's tour
        int startPos = (int) (Math.random() * parent1.planCommandSize());
        int endPos = (int) (Math.random() * parent1.planCommandSize());

        // Loop and add the sub plan from parent1 to our child
        for (int i = 0; i < child.planCommandSize(); i++) {
        	
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCommand(i, parent1.getCommand(i));
            } else { // If our start position is larger
            	if (startPos > endPos) {
	                if (!(i < startPos && i > endPos) && i < parent1.planCommandSize()) {
						child.setCommand(i, parent1.getCommand(i));
	                }
            	}
        	}
        }

        // Loop through parent2's plan
        for (int i = 0; i < parent2.planCommandSize(); i++) {
        	
            // If child doesn't have the city add it
            if (!child.containsCommand(parent2.getCommand(i))) {
            	
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.planCommandSize(); ii++) {
                	
                    // Spare position found, add city
                    if (child.getCommand(ii) == null) {
                        child.setCommand(ii, parent2.getCommand(i));
                        break;
                    }
                }
            }
        }
        // fixing the null commands
        fixingNullCommands(child);
        
        return child;
    }

    public static void fixingNullCommands(Plan child) {
    	int size = child.planCommandSize();
        for(int i =0; i < size; i++){
        	if(child.getCommand(i)==null){
        		child.getActions().remove(i);
        		--size;
        		--i;
        	}
        }
	}

	// Mutate a plan using swap mutation
    private static void mutate(Plan plan) {
    	
        // Loop through plan
        for(int planPos1=0; planPos1 < plan.planCommandSize(); planPos1++){
        
        	// Apply mutation rate
            if(Math.random() < mutationRate){
            
            	// Get a second random position in the plan
                int planPos2 = (int) (plan.planCommandSize() * Math.random());

                // Get the actions at target position in plan
                Command action1 = plan.getCommand(planPos1);
                Command action2 = plan.getCommand(planPos2);

                // Swap them around
                plan.setCommand(planPos2, action1);
                plan.setCommand(planPos1, action2);
            }
        }
    }

    // Selects candidate plan for crossover
    private static Plan tournamentSelection(Population pop) {
    	
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        
        // For each place in the tournament get a random candidate plan and add it
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.savePlan(i, pop.getPlan(randomId));
        }
        
        // return the best plan
        return tournament.getFittest();
    }
    
    // Selects candidate Stochastic universal sampling
    private static Plan RWSelection(Population pop) {
    	
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        
        // We add a candidate plan and add to the tournament
        int i = 0;
        double sum = 0;
        Plan plan;
        while(sum < RWS_POINTERS && i < tournamentSize){
        	plan = pop.getPlan(i);
        	sum+=plan.getFitness();
            tournament.savePlan(i, plan);
            i++;
        }

        // For each remaining in the tournament get a random candidate plan with a higher fitness and add it
        for (; i < tournamentSize; ++i) {
            int randomId = (int) (Math.random() * pop.populationSize());
            tournament.savePlan(i, pop.getPlan(randomId));
        }
        
        // return the best plan
        return tournament.getFittest();
    }
}