
package contest.FantasticBits;

import java.util.ArrayList;
import java.util.Scanner;

import contest.FantasticBits.GAManager.T_SELECTION;
import contest.FantasticBits.game.Game;
import contest.FantasticBits.game.Game.BinarySearchTree;
import contest.FantasticBits.game.Game.BinarySearchTreeEnemy;
import contest.FantasticBits.game.Game.Data;
import contest.FantasticBits.game.Game.Enemy;
import contest.FantasticBits.game.Game.Hero;
import contest.FantasticBits.game.GameActions;
import contest.FantasticBits.game.Plan;
import contest.FantasticBits.game.Plan.Command;

public class GA_MAIN {

    public static void main(String[] args) {

    	// GA_MAIN.test();
    	
    	GA_MAIN.testPlayer();
    	
    }

    
	private static void testPlayer() {
		
		
		/** initial configuration **/
		Command.DEBUG=false;
		GameActions.DEBUG = false;
        GameActions.SIMULATE = false;
    	GameActions.totalActionsPlan = -1; // add -1 to get the total of commands
    	GameActions.population = 25; // default 50
    	GameActions.generations = 50; // default = 100
    	
    	GameActions.bstRootX = 0;
    	GameActions.bstRootY = 0;
    	
    	GameActions.enemyLife = 10;
    	GameActions.testDamageEnemy = 2;
    	
    	GAManager.mutationRate = 0.015;
        GAManager.tournamentSize = 5;
        GAManager.elitism = true;
        GAManager.selection = T_SELECTION.RWS;
        GAManager.RWS_POINTERS = 0.015;
        
        
        /** Genetic algorithm variable **/
    	Population pop = null;
    	
    	
    	/** Game variables **/
    	Game g = null;

    	// holds the hero
    	Hero myHero= null;

    	// holds the dataPoints
    	GameActions.lData = null;
    	BinarySearchTree dataPoints;
        
        // holds the enemies
    	GameActions.lEnemies = null;
        BinarySearchTreeEnemy enemies;
        
        
        /** Simulation variables **/
        boolean firstLoop = true;

    	Scanner in = new Scanner(System.in);
    	
    	
        /** game loop **/
        while (true) {
            if(myHero == null){
                int x = GameActions.getX();
                int y = GameActions.getY();
            	myHero = new Hero(1000, x, y);
            }else{
            	// myHero.changeCurrentPosition(new Point(x, y));
            }
            
            // saving the data
            dataPoints = new BinarySearchTree();
            // int dataCount = in.nextInt();
            int dataCount = 3; // GameActions.getDataCount();
            if(GameActions.lData == null){
            	GameActions.lData = new ArrayList<Data>(dataCount);
    		}
            for (int i = 0; i < dataCount; i++) {

                int dataId = i;
                Data d;
                if(firstLoop){
                    int dataX = GameActions.getX();
                    int dataY = GameActions.getY();
                    d = new Data(dataId, dataX, dataY);
        			GameActions.lData.add(dataId, d);
        		}else{
        			// GameActions.lData.get(dataId).changeInitialPosition(d);
    				d = GameActions.lData.get(dataId);
        		}
        		dataPoints.insert(d);
            }
            
            // saving the enemies
    		enemies = new BinarySearchTreeEnemy();
    		// int enemyCount = in.nextInt();
   	     	int enemyCount = 7; // GameActions.getEnemyCount();
            if(GameActions.lEnemies == null){
            	GameActions.lEnemies = new ArrayList<Enemy>(enemyCount);
    		}
            for (int i = 0; i < enemyCount; i++) {
                int enemyId = i;
                
        		Enemy e;
        		if(firstLoop){
                    int enemyX = GameActions.getX();
                    int enemyY = GameActions.getY();
                    int enemyLife = GameActions.getLife();
                    e = new Enemy(enemyId, 500, enemyX, enemyY, enemyLife);
        			GameActions.lEnemies.add(enemyId, e);
        		}else{
        			/*if(enemyLife <= 0){
        				GameActions.lEnemies.get(enemyId).changeToEliminate();
        			}else{
        				GameActions.lEnemies.get(enemyId).changeInitialPosition(e);
        			}
    				GameActions.lEnemies.get(enemyId).changeInitialLife(e.initialLife);*/
        			e = GameActions.lEnemies.get(enemyId);
        		}
        		e.changeInitialPosition(e);
        		enemies.insert(e);
    			if(!e.isEliminated())
    				enemies.increaseLife(e.initialLife);
            }
            
        	// Create our game
            g = new Game(myHero, enemies, dataPoints);
        	
            // Create and add the commands
        	GameActions.addGame(g);

            if(firstLoop)
            	System.out.println("\n*************\n\nGame: "+g.toString());
        	
            // Initialize population
        	// if(pop == null){
    	        pop = new Population(GameActions.population, true);
    	        //System.out.println("\nInitial distance: " + pop.getFittest().getScore());
    	        //System.out.println("Solution:");
    	        //System.out.println(pop.getFittest().planCommandSize());
    	    // }
            
            // Evolve population for 100 generations
            pop = GAManager.evolvePopulation(pop);

            if(GameActions.DEBUG){
            	System.out.println("************");
            	System.out.println("SORT POPULATION: ["+0+"]["+pop.getPlan(0).getFitness()+"] - ["+pop.populationSize()+"]"+pop.getPlan(pop.populationSize()-1).getFitness());
            	System.out.println("************");
            }
            
            for (int i = 0; i < GameActions.generations; i++) {
                pop = GAManager.evolvePopulation(pop);
            }
            
            // System.out.println("\n*************\n\nGame: "+g.toString());

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
        	Individual plan = pop.getFittest();
            Command act = ((Plan)plan).getActionToExecute();
            act = g.executePredictMove(act);
            
            System.out.println("\n*************\nPlan: "+plan);
            
            System.out.println("*************\nCommand to execute: "+act);
            
            GameActions.SIMULATE = true;
            g.simulateExecution(act);
            GameActions.SIMULATE = false;
            
            System.out.println("*************\n\nGame: "+g.toString());
            
            if(firstLoop)
            	firstLoop = false;
            
            if(g.isEnemyEmpty())
            {
            	System.out.println("\n\nGame finished, I win !");
            	break;
            }
            if(g.isDataPointEmpty())
            {
            	System.out.println("\n\nGame finished, I loose !");
            	break;
            }
        }		
	}


	private static void test() {
		Command.DEBUG=false;
    	GameActions.totalActionsPlan = -1; // add -1 to get the total of commands
    	GameActions.population = 5; // default 50
    	GameActions.generations = 10; // default = 100
    	
    	GameActions.bstRootX = 16000/2;
    	GameActions.bstRootY = 9000/2;
    	

    	Game g = null;
    	Population pop = null;
    	
    	
    	
    	// Create our game
    	g = new Game();
        // System.out.println("Game: " + g.toString());
    	
        // Create and add the commands
    	GameActions.addGame(g);
    	
    	long start = System.currentTimeMillis();
    	System.out.println("\nStart time: "+start);
    	
        // Initialize population
    	if(pop == null){
	        pop = new Population(GameActions.population, true);
	        // System.out.println("\nInitial distance: " + pop.getFittest().getScore());
	        //System.out.println("Solution:");
	        //System.out.println(pop.getFittest());
    	}

        // Evolve population for 100 generations
        pop = GAManager.evolvePopulation(pop);
        for (int i = 0; i < GameActions.generations; i++) {
            pop = GAManager.evolvePopulation(pop);
        }

        // Print final results
        //System.out.println("\nFinished");
        // System.out.println("Final distance: " + pop.getFittest().getScore());
        //System.out.println("Solution:");
        System.out.println(((Plan)pop.getFittest()).getActions().remove(0));

    	System.out.println("\nFinish time: "+(System.currentTimeMillis()-start));
	}
}