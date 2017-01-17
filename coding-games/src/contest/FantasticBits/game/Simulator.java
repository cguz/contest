
package contest.FantasticBits.game;

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

/**
 * @author cguzman@cguz.org
 * 
 * Class that simulate the fantastic bits problem
 */
public class Simulator {

	private static Game gameTable; 
	
	
    public static void main(String[] args) {

    	// Simulator.test();
    	Scanner in = new Scanner(System.in);
        int myTeamId = in.nextInt(); // if 0 you need to score on the right of the map, if 1 you need to score on the left

    	
    	Simulator simulator = new Simulator();
    	
    	
        // game loop
        while (true) {
            int entities = in.nextInt(); // number of entities still in game
            for (int i = 0; i < entities; i++) {
                int entityId = in.nextInt(); // entity identifier
                String entityType = in.next(); // "WIZARD", "OPPONENT_WIZARD" or "SNAFFLE" (or "BLUDGER" after first league)
                int x = in.nextInt(); // position
                int y = in.nextInt(); // position
                int vx = in.nextInt(); // velocity
                int vy = in.nextInt(); // velocity
                int state = in.nextInt(); // 1 if the wizard is holding a Snaffle, 0 otherwise
            }
            for (int i = 0; i < 2; i++) {

                // Write an action using System.out.println()
                // To debug: System.err.println("Debug messages...");


                // Edit this line to indicate the action for each wizard (0 ≤ thrust ≤ 150, 0 ≤ power ≤ 500)
                // i.e.: "MOVE x y thrust" or "THROW x y power"
                System.out.println("MOVE 8000 3750 100");
            }
        }
        
    }

    
}