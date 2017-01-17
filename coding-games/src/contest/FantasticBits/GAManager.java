/*
* GAManager.java
* Manages algorithms for evolving population
*/

package contest.FantasticBits;

import contest.FantasticBits.game.GameActions;
import contest.FantasticBits.game.Plan;
import contest.FantasticBits.game.Plan.Command;

public class GAManager {

    /**
     * @author cguzman
     * TYPES OF SELECTION METHODS
     * TOURNAMENT: 
     * RWS: 
     * MIX_RWS_TOURNAMENT: SELECT ONE INDIVIDUAL USING TOURNAMENT SELECTION
     * AND A SECOND INDIVIDUAL USING RWS SELECTION
     */
    public enum T_SELECTION {
    	TOURNAMENT, RWS, MIX_RWS_TOURNAMENT;
	}
    
    /**
     * @author cguzman
     * TYPES OF CROSSOVER OPERATORS
     * ONE_POINT: SELECT ONE POINT AND SAVE THE LEFT PART WITH THE INDIVIDUALS OF PARENT1 AND
     * 	THE RIGHT PART WITH THE INDIVIDUALS OF PARENT2
     * TWO_POINTS: TWO POINTS ARE SELECTED ON THE PARENTS ORGANISM. EVERYTHING BETWEEN 
     *  THE TWO POINTS IS SAVED FROM PARENT1 TO THE CHILD. AND EVERYTHING OUTSIDE THE 
     *  TWO POINTS IS SAVED FROM PARENT2 TO THE CHILD.
     * RANDOM_ONE:
     */
    public enum T_CROSS_OP {
    	ONE_POINT, TWO_POINTS, RANDOM_ONE
    }
    

	/* parameters */
    public static T_SELECTION 	selection = T_SELECTION.TOURNAMENT;
	public static double 		RWS_POINTERS = 0.05;
	
	public static T_CROSS_OP 	crossoverOperators = T_CROSS_OP.TWO_POINTS;
	
    public static int 			tournamentSize = 5;
    public static double 		mutationRate = 0.015;
    public static boolean   	elitism = true;
    

    // Evolves a population over one generation
    public static Population evolvePopulation(Population pop) {
    	
        Individual parent1;
        Individual parent2;
        
        // we create a new empty population
        Population newPopulation = new Population(pop.populationSize(), false);

        // Keep our best individual if elitism is enabled
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.savePlan(0, pop.getFittest());
            newPopulation.savePlan(1, pop.getPlan(1));
            elitismOffset = 2;
        }

        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {

            // selection population
            switch(selection){
	            default: case TOURNAMENT:
	            	parent1 = tournamentSelection(pop);
	                parent2 = tournamentSelection(pop);
	                break;
	            case RWS:
	            	parent1 = rwsSelection(pop);
	                parent2 = rwsSelection(pop);
	                break;
	            case MIX_RWS_TOURNAMENT:
	            	parent1 = tournamentSelection(pop);
	                parent2 = rwsSelection(pop);
	                break;
            }
            
        	// System.out.println("[evolvePopulation] plan parent1 size: "+parent1.planCommandSize());
        	// System.out.println("[evolvePopulation] plan parent2 size: "+parent2.planCommandSize());
        	
            // Crossover parents
            Individual child = crossover(parent1, parent2);
            
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
    public static Individual crossover(Individual parent1, Individual parent2) {
    	
        // Create new child plan
        Individual child = new Plan(((Plan)parent1).getGame());

        // crossover operators
        switch(crossoverOperators){
            default: case TWO_POINTS:
            	child = twoPointsOperator(parent1, parent2);
                break;
        }
        
        return child;
    }
    
    private static Individual twoPointsOperator(Individual parent1, Individual parent2){
    	
    	 // Create new child plan
        Individual child = new Plan(((Plan)parent1).getGame());
    	
    	// Get start and end sub plan positions for parent1's plan
        int startPos = GameActions.rnd.nextInt((int) parent1.planCommandSize());
        int endPos = GameActions.rnd.nextInt((int) parent1.planCommandSize());

        // Loop and add the sub plan from parent1 to our child
        for (int i = 0; i < child.planCommandSize(); i++) {
        	
            // If our start position is less than the end position
            if (startPos < endPos && i > startPos && i < endPos) {
            	((Plan)child).setCommand(i, ((Plan)parent1).getCommand(i));
            } else { // If our start position is larger
            	if (startPos > endPos) {
                    if (!(i < startPos && i > endPos) && i < parent1.planCommandSize()) {
                    	((Plan)child).setCommand(i, ((Plan)parent1).getCommand(i));
                    }
            	}
        	}
        }

        // Loop through parent2's plan
        for (int i = 0; i < parent2.planCommandSize(); i++) {
        	
            // If child doesn't have the city add it
            if (!((Plan)child).containsCommand(((Plan)parent2).getCommand(i))) {
            	
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.planCommandSize(); ii++) {
                	
                    // Spare position found, add city
                    if (((Plan)child).getCommand(ii) == null) {
                    	((Plan)child).setCommand(ii, ((Plan)parent2).getCommand(i));
                        break;
                    }
                }
            }
        }
        
        // fixing the null commands
        fixingNullCommands(((Plan)child));
        
        return child;
    }

    private static Individual twoPointsOperator2(Individual parent1, Individual parent2){
    	
   	 // Create new child plan
       Individual child = new Plan(((Plan)parent1).getGame());
   	
   	// Get start and end sub plan positions for parent1's plan
       int startPos = GameActions.rnd.nextInt((int) parent1.planCommandSize());
       int endPos = startPos+GameActions.rnd.nextInt((int) parent1.planCommandSize()-startPos);

       // Loop and add the sub plan from parent1 to our child
       for (int i = 0; i < child.planCommandSize(); i++) {
       	
           // If our start position is less than the end position
           if (startPos < endPos && i > startPos && i < endPos) {
           	((Plan)child).setCommand(i, ((Plan)parent1).getCommand(i));
           } else { // If our start position is larger
           	if (startPos > endPos) {
                   if (!(i < startPos && i > endPos) && i < parent1.planCommandSize()) {
                   	((Plan)child).setCommand(i, ((Plan)parent1).getCommand(i));
                   }
           	}
       	}
       }

       // Loop through parent2's plan
       for (int i = 0; i < parent2.planCommandSize(); i++) {
       	
           // If child doesn't have the city add it
           if (!((Plan)child).containsCommand(((Plan)parent2).getCommand(i))) {
           	
               // Loop to find a spare position in the child's tour
               for (int ii = 0; ii < child.planCommandSize(); ii++) {
               	
                   // Spare position found, add city
                   if (((Plan)child).getCommand(ii) == null) {
                   	((Plan)child).setCommand(ii, ((Plan)parent2).getCommand(i));
                       break;
                   }
               }
           }
       }
       
       // fixing the null commands
       fixingNullCommands(((Plan)child));
       
       return child;
   }

    public static void fixingNullCommands(Plan child) {
    	int size = (int) child.planCommandSize();
        for(int i =0; i < size; i++){
        	if(child.getCommand(i)==null){
        		child.getActions().remove(i);
        		--size;
        		--i;
        	}
        }
	}

	// Mutate an individual using swap mutation
    private static void mutate(Individual individual) {
    	
        // Loop through plan
        for(int planPos1=0; planPos1 < individual.planCommandSize(); planPos1++){
        
        	// Apply mutation rate
            if(Math.random() < mutationRate){
            
            	// Get a second random position in the plan
                int planPos2 = (int) (individual.planCommandSize() * Math.random());

                // Get the actions at target position in plan
                Command action1 = ((Plan)individual).getCommand(planPos1);
                Command action2 = ((Plan)individual).getCommand(planPos2);

                // Swap them around
                ((Plan)individual).setCommand(planPos2, action1);
                ((Plan)individual).setCommand(planPos1, action2);
            }
        }
    }

    // Selects candidate plan for crossover
    private static Individual tournamentSelection(Population pop) {
    	
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
    private static Individual rwsSelection(Population pop) {
    	
        // Create a tournament population
        Population tournament = new Population(tournamentSize, false);
        
        // We add a candidate plan and add to the tournament
        int i = 0;
        double sum = 0;
        Individual plan;
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