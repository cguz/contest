package hash_code.contest_2017.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hash_code.algorithms.graph.SearchAlgorithmFactory;
import hash_code.algorithms.graph.enumerates.ALGORITHM;
import hash_code.algorithms.graph.interfaces.Action;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.SearchAlgorithm;
import hash_code.algorithms.graph.nodes.NodeImp;
import hash_code.contest_2017.practice.common.ACTIONS_TOTAL;
import hash_code.contest_2017.practice.pizza.PizzaGridValue;
import hash_code.tools.Files;


/**
 * @Challenge Pizza
 */
public class Pizza {

	
	public static void main(String[] args) {
		
		Pizza challenge = new Pizza();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String outputFileName = "small";
		String testDir = "TEST/2017/practice/";
		try {

			BufferedReader bf = new BufferedReader(new FileReader(testDir+outputFileName+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			String[] temp = bf.readLine().split(" ");
			String line;
			
			int R = Integer.parseInt(temp[0]); // 1 ≤ R ≤ 1000
			int C = Integer.parseInt(temp[1]); // 1 ≤ C ≤ 1000
			int L = Integer.parseInt(temp[2]); // 1 ≤ L ≤ 1000
			int H = Integer.parseInt(temp[3]); // 1 ≤ H ≤ 1000
			
			int[][] grid = new int[R][C];
			
			for (int i = 0; i < R; ++i) {

				line = bf.readLine();
				
				for(int j = 0; j < line.length(); j++){
					grid[i][j] = line.charAt(j);
				}
				
			}
			
			ACTIONS_TOTAL.generateActions(grid, L, H);
			
			
			Node graph = new NodeImp(new PizzaGridValue(grid, L , H));
			
			SearchAlgorithm bfs = SearchAlgorithmFactory.getAlgorithm(ALGORITHM.BREADTH_SEARCH);
			Node bestNode = bfs.find(graph, null);
			
			Action[] action = bestNode.getPath();
			output+=action.length+"\n";
			for(Action act: action){
				output+=act.toString()+"\n";
			}
			
			System.out.println(output);
			System.out.println("Score:"+bestNode.getScore());
			
			Files file = new Files(testDir+outputFileName+".out", false);
			file.write(output);
			file.close();
			
			bf.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}