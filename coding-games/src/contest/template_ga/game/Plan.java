package contest.template_ga.game;

import java.util.ArrayList;
import java.util.Collections;

import contest.template_ga.game.Game.PointId;
import contest.template_ga.Individual;

public class Plan implements Individual {

	
    /** scores **/
    private int ENEMY = 10;
    private int DATA = 100;
    
    
	public enum TARGET{
		ENEMY, DATA, NONE
	}
	
	public enum COMMAND{
		SHOOT, MOVE, ESCAPE
	}
	
	
	public static class Command{
		// debug
		public static boolean DEBUG = true;
		
		private PointId idTarget;
		private TARGET target;
		private COMMAND action;
		
		private int coordX;
		private int coordY;
		private String message;
		
		public Command(COMMAND act, PointId id, int x, int y, String msg){
			action = act;
			idTarget = id;
			target = TARGET.ENEMY;
			coordX = x;
			coordY = y;
			message = msg;
		}
		
		public Command(COMMAND act, PointId id, TARGET t, int x, int y, String msg){
			action = act;
			idTarget = id;
			target = t;
			coordX = x;
			coordY = y;
			message = msg;
		}

		public PointId getPointTarget() {
			return idTarget;
		}

		public TARGET getTarget() {
			return target;
		}

		public COMMAND getAction() {
			return action;
		}

		public int getCoordX() {
			return coordX;
		}

		public int getCoordY() {
			return coordY;
		}

		public String getMessage() {
			return message;
		}
		
		public String getCommandText(){
			if(action == COMMAND.MOVE)
				return action.toString()+" "+coordX+" "+coordY+""+message;
			else {
				if(action == COMMAND.ESCAPE)
					return COMMAND.MOVE.toString()+" "+coordX+" "+coordY+""+message;
				else
					return action.toString()+" "+idTarget.getId()+""+message;
					
			}
		}

		@Override
		public String toString() {
			if(!DEBUG)
				return getCommandText();
			return getCommandText()+" "+this.getPointTarget();
		}

		public void setPoint(PointId pointTarget) {
			coordX = pointTarget.getX();
			coordY = pointTarget.getY();
			
			message = " "+action.toString()+" "+coordX+" "+coordY;
		}	
	}

	
	/** Holds the current initial game */
    private Game game;
    private ArrayList<Command> actions = new ArrayList<Command>();
    
    // Cache
    private double 	fitness = 0;
    private int 	distance = 0;
    
 
    // Constructs a Plan
    public Plan(){  }
    
    // Constructs a blank Plan
    public Plan(Game game){
    	this.game = game;

    	int size = GameActions.numberOfCommands();
        for (int i = 0; i < size; i++) {
        	actions.add(null);
        }
    }
    
    public Plan(Game game, ArrayList<Command> actions){
        this.game = game;
        this.actions = actions;
    }

    // Creates a random individual
    public void generateIndividual() {
    	
        // Loop through all our commands and add them to the plan
    	int size = GameActions.numberOfCommands();
        for (int actionIndex = 0; actionIndex < size; actionIndex++) {
        	setCommand(actionIndex, GameActions.getCommand(actionIndex));
        }
        
        // Randomly reorder the action
        Collections.shuffle(actions);
    }

    // Gets a command from the plan
    public Command getCommand(int planPosition) {
        return (Command)actions.get(planPosition);
    }

    // Sets a command in a certain position within a game
    public void setCommand(int index, Command action) {
    	actions.set(index, action);
        
        // If the plans been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }
    
    // Gets the plans fitness
    public double getFitness() {
        if (fitness == 0) {
            double score = (double)getScore();
            fitness = 1/score;
        	if(GameActions.DEBUG){
            	System.out.println("\n\n************ START FITNESS ************\n\n");
            	System.out.println("\n************");
            	System.out.println("RESULT: ["+score+"]["+fitness+"] "+actions.toString());
        		System.out.println("************\nRESET INITIAL VALUES OF THE GAME\n\n");
        	}
        	game.defaultValues();
        }
        return fitness;
    }
    
    // Gets the total distance of the tour
    public int getScore(){
    	int S = 0; // total of shoot
    	int L = game.lifeEnemies();
    	
        if (distance == 0) {
        	Command action;
            int planScore = 0;
            boolean end = false;
            
            // Loop through our commands
            for (int actionIndex=0; actionIndex < planCommandSize() && !end; actionIndex++) {
            	
                // Get the action to execute
                action = getCommand(actionIndex);
                
				if(action.action != COMMAND.ESCAPE && action.getPointTarget().isEliminated())
					continue;
                
                if(action.action == COMMAND.SHOOT)
                	++S;
            	
                if(GameActions.DEBUG)
                	System.out.println("\n\n******************\nAction: "+action.toString());
                
                // Get the score of executing the action
                int deadEnemies = game.execute(action);
                
                if(GameActions.DEBUG)
                	System.out.println("******************\n\nGame: "+GameActions.getGame().toString());
            	
                if(deadEnemies == -1){
                	return 10;
                }else{
                	planScore+=deadEnemies;
                }
                
                // if Game ends, get extra points
                if(gameEnds()){
                	end = true;
                }
                
            }
        	
            distance = (DATA*game.getDataPointsSize())+planScore*ENEMY;
        	
            if(end){

            	// get extra points 
            	distance+=game.getDataPointsSize()*Math.max(0, (L - (30*S)))*3;
            	// This bonus is calculated with the following formula: DP * max(0, (L - 3*S)) * 3
            	// Where:
            	// DP is the number of data points left.
            	// L is the total amount of life points enemies have among themselves at the start of the game.
            	// S is the total number of shots fired during play.
            }
            
        }
        return distance;
    }
    
    // if the game ends, get extra points
    private boolean gameEnds(){
    	return game.isDataPointEmpty() || game.isEnemyEmpty();
    }

    // Get number of commands on our plan
    public double planCommandSize() {
        return actions.size();
    }
    
    // Check if the plan contains an action
    public boolean containsCommand(Command action){
        return actions.contains(action);
    }
    
    @Override
    public String toString() {
        String geneString = "["+distance+"]["+fitness+"]";
        for (int i = 0; i < planCommandSize(); i++) {
            geneString += getCommand(i)+",";
        }
        return geneString;
    }

	public Game getGame() {
		return game;
	}

	public ArrayList<Command> getActions() {
		return actions;
	}

	public Command getActionToExecute() {
		int i = 0;
		while(actions.get(i).action != COMMAND.ESCAPE && actions.get(i).getPointTarget().isEliminated() && i < actions.size())
			++i;
		if(i < actions.size())
			return actions.get(i);
		return null;
	}
}
