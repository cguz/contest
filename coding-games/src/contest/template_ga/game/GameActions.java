/*
* TourManager.java
* Holds the cities of a tour
*/

package contest.template_ga.game;

import java.util.ArrayList;
import java.util.Random;

import contest.template_ga.game.Game;
import contest.template_ga.game.Game.Data;
import contest.template_ga.game.Game.Enemy;
import contest.template_ga.game.Game.PointId;
import contest.template_ga.game.Plan.COMMAND;
import contest.template_ga.game.Plan.Command;
import contest.template_ga.game.Plan.TARGET;

public class GameActions {

    public static Random rnd = new Random(1234567);
    
	// configuration
	public static int totalActionsPlan = 10;
	public static int population;
	public static int generations;

	public static int bstRootId=-1;
	public static int bstRootX=0;
	public static int bstRootY=0;

	// game 
	private static Game game;

	public static boolean DEBUG;

	public static boolean SIMULATE=false;
	
    
    /** max constraints of the game */
	public static int wide = 16001;
	public static int high = 9001;
    
    public static int dataCount = 99;
    public static int enemyCount = 99;
    public static int enemyLife = 149;
    
    public static double testDamageEnemy = 2;
    


    // Holds our DataPoints
	public static ArrayList<Data> lData = null;
    
    // Holds our Enemies
    public static ArrayList<Enemy> lEnemies = null;
	
    // Holds our Commands
    private static ArrayList<Command> commands = new ArrayList<Command>();
    
    
    
    public static int getX(){
    	return rnd.nextInt(wide);
    }
    public static int getY(){
    	return rnd.nextInt(high);
    }
    public static int getLife(){
    	return 1+rnd.nextInt(enemyLife);
    }
    public static int getEnemyCount(){
    	return rnd.nextInt(enemyCount);
    }
    public static int getDataCount(){
    	return rnd.nextInt(dataCount);
    }
	public static double getTestDamageEnemy() {
		return testDamageEnemy;
	}
	
	public static ArrayList<Enemy> getEnemies(){
		return lEnemies;
	}
	public static ArrayList<Data> getData(){
		return lData;
	}

    
    // Adds a command
    public static void addCommand(Command city) {
        commands.add(city);
    }
    
    // Get a command
    public static Command getCommand(int index){
        return (Command)commands.get(index);
    }
    
    // Get the number of commands
    public static int numberOfCommands(){
    	if(GameActions.totalActionsPlan != -1){
    		return (commands.size() < GameActions.totalActionsPlan)?commands.size():GameActions.totalActionsPlan;
    	}
        return commands.size();
    }

    // Add a Game
	public static void addGame(Game g) {
		game = g;
		commands.clear();
		generateCommands();
	}

	// Get the game
	public static Game getGame() {
		return game;
	}
	
	// Generate commands for a given game
    public static void generateCommands(){
   
    	// Plan p = new Plan();
    	
    	// we get the commands from the different data points
    	for(PointId t: getData()){
    		Data d = (Data)t;
    		
    		if(d == null || d.isEliminated())
    			continue;
    		
    		// Command action = new Command(COMMAND.MOVE, d, TARGET.DATA, d.getX(), d.getY(), "");
    		// addCommand(action);
    		
    	}
    	
    	// we get the commands from the different enemies
    	for(PointId t : getEnemies()){
    		Enemy e = (Enemy)t;

    		if(e == null || e.isEliminated())
    			continue;
    		
    		Command action;
    		
    		// for each enemy we can generate a move command
    		//action = new Command(COMMAND.MOVE, e, e.getX(), e.getY(), "");
    		//addCommand(action);

    		// for each enemy we can generate a shoot command
    		action = new Command(COMMAND.SHOOT, e, e.getX(), e.getY(), "");
            addCommand(action);
            addCommand(action);
            addCommand(action);

            // for each data we can generate a move command
    		action = new Command(COMMAND.ESCAPE, null, TARGET.NONE, 0, 0, "ESCAPE");
    		addCommand(action);
        
    	}
        
    }
}