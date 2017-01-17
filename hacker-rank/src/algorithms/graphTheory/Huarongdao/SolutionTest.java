package algorithms.graphTheory.Huarongdao;

import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import algorithms.graphTheory.Huarongdao.Solution.MOVE;
import algorithms.graphTheory.Huarongdao.Solution.Node;
import algorithms.graphTheory.Huarongdao.Solution.State;

public class SolutionTest {
	
	Solution sol = new Solution();
	Node[][] board;
	
	
	@Test
	public void findShouldEachElementBeWorking() throws FileNotFoundException {

		int N = 20, M = 25;
		
    	File file = new File("./TEST/algorithms/graphTheory/Huarongdao/input-test2.txt");
    	
		board = new Node[N][M];
		board[0][0] = sol.new Node(MOVE.CAN, 0, 0);
		board[0][1] = sol.new Node(MOVE.CAN, 0, 1);
		board[0][2] = sol.new Node(MOVE.CAN, 0, 2);
		board[0][3] = sol.new Node(MOVE.CAN, 0, 3);
		board[0][4] = sol.new Node(MOVE.CAN, 0, 4);
		board[1][0] = sol.new Node(MOVE.CAN, 1, 0);
		board[1][1] = sol.new Node(MOVE.CAN, 1, 1);
		board[1][2] = sol.new Node(MOVE.CAN, 1, 2);
		board[1][3] = sol.new Node(MOVE.CAN, 1, 3);
		board[1][4] = sol.new Node(MOVE.CAN, 1, 4);
		board[2][0] = sol.new Node(MOVE.CAN_NOT, 2, 0);
		board[2][1] = sol.new Node(MOVE.CAN, 2, 1);
		board[2][2] = sol.new Node(MOVE.CAN, 2, 2);
		board[2][3] = sol.new Node(MOVE.CAN, 2, 3);
		board[2][4] = sol.new Node(MOVE.CAN, 2, 4);
		board[3][0] = sol.new Node(MOVE.CAN, 3, 0);
		board[3][1] = sol.new Node(MOVE.CAN, 3, 1);
		board[3][2] = sol.new Node(MOVE.CAN, 3, 2);
		board[3][3] = sol.new Node(MOVE.CAN, 3, 3);
		board[3][4] = sol.new Node(MOVE.CAN, 3, 4);
		board[4][0] = sol.new Node(MOVE.CAN_NOT, 4, 0);
		board[4][1] = sol.new Node(MOVE.CAN, 4, 1);
		board[4][2] = sol.new Node(MOVE.CAN_NOT, 4, 2);
		board[4][3] = sol.new Node(MOVE.CAN, 4, 3);
		board[4][4] = sol.new Node(MOVE.CAN, 4, 4);
    	
		
		// load the file in the Scanner
		sol.loadFile(file);
		
		// sol.readFile();
		
		// move to the next line in the Scanner
		sol.in.nextLine();
    	
		// verifying the board
		Node[][] actual_board = sol.loadBoard(N,M);
		for(int i=0;i < board.length; i++){
			// Assert.assertTrue("board 5 x 5 must be board", Arrays.equals(board[i], actual_board[i]));
			assertArrayEquals("board 5 x 5 must be board", board[i], actual_board[i]);
			// System.out.println(Arrays.equals(board[i], actual_board[i]));
		}

    	for(int j=0; j < N;++j){
    		System.out.println();
    		for(int i=0; i < M;++i){
    			System.out.print(actual_board[j][i].toString());
    		}
    	}
		
	}
	
	@Test
	public void getNeighborsShouldReturnTheNeighbors() throws FileNotFoundException{

		int N = 5, M = 5;
    	File file = new File("./TEST/algorithms/graphTheory/Huarongdao/input-test.txt");
		
		// load the file in the Scanner
		sol.loadFile(file);
		
		// sol.readFile();
		
		// move to the next line in the Scanner
		sol.in.nextLine();
    	
		// verifying the board
		Node[][] actual_board = sol.loadBoard(N,M);
		
		
		sol.problem = new int[6];

		sol.problem[0] = sol.in.nextInt()-1; // Empty X
		sol.problem[1] = sol.in.nextInt()-1; // Empty Y
 		sol.problem[2] = sol.in.nextInt()-1; // Cao Cao block X
 		sol.problem[3] = sol.in.nextInt()-1; // Cao Cao block Y
 		sol.problem[4] = sol.in.nextInt()-1; // Exit X
 		sol.problem[5] = sol.in.nextInt()-1; // Exit Y
		
		
 		sol.problem[0] = 0; // Empty X
		sol.problem[1] = 1; // Empty Y
 		
		
		State initial = sol.getInitialState(actual_board, sol.problem);
		Node[] neighbors = new Node[4];
		neighbors[0] = sol.new Node(MOVE.CAN, 1, 1);
		neighbors[1] = sol.new Node(MOVE.CAN, 0, 0);
		neighbors[2] = sol.new Node(MOVE.CAN, 0, 2);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
		
		
		sol.problem[0] = 1; // Empty X
		sol.problem[1] = 0; // Empty Y
 		
		
		initial = sol.getInitialState(actual_board, sol.problem);
		neighbors = new Node[4];
		neighbors[0] = sol.new Node(MOVE.CAN, 0, 0);
		neighbors[1] = sol.new Node(MOVE.CAN_NOT, 2, 0);
		neighbors[2] = sol.new Node(MOVE.CAN, 1, 1);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
		
		
		sol.problem[0] = 4; // Empty X
		sol.problem[1] = 0; // Empty Y
 		
		
		initial = sol.getInitialState(actual_board, sol.problem);
		neighbors = new Node[3];
		neighbors[0] = sol.new Node(MOVE.CAN, 3, 0);
		neighbors[1] = sol.new Node(MOVE.CAN, 4, 1);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
		
		
		
		sol.problem[0] = 4; // Empty X
		sol.problem[1] = 4; // Empty Y
 		
		
		initial = sol.getInitialState(actual_board, sol.problem);
		neighbors = new Node[3];
		neighbors[0] = sol.new Node(MOVE.CAN, 3, 4);
		neighbors[1] = sol.new Node(MOVE.CAN, 4, 3);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
		
		

		sol.problem[0] = 0; // Empty X
		sol.problem[1] = 4; // Empty Y
 		
		
		initial = sol.getInitialState(actual_board, sol.problem);
		neighbors = new Node[3];
		neighbors[0] = sol.new Node(MOVE.CAN, 1, 4);
		neighbors[1] = sol.new Node(MOVE.CAN, 0, 3);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
		

		sol.problem[0] = 2; // Empty X
		sol.problem[1] = 2; // Empty Y
 		
		
		initial = sol.getInitialState(actual_board, sol.problem);
		neighbors = new Node[5];
		neighbors[0] = sol.new Node(MOVE.CAN, 1, 2);
		neighbors[1] = sol.new Node(MOVE.CAN, 3, 2);
		neighbors[2] = sol.new Node(MOVE.CAN, 2, 1);
		neighbors[3] = sol.new Node(MOVE.CAN, 2, 3);
		assertArrayEquals("neighbors of ", neighbors, initial.getNeighbors(actual_board));
		
	}

}


/*
 import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
                MyClassTest.class,
                MySecondClassTest.class })

public class AllTests {

}
 * */