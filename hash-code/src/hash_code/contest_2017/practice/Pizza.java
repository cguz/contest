package hash_code.contest_2017.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hash_code.algorithms.graph.BreadthFSSearchAlgorithm;
import hash_code.algorithms.graph.NodeImp;
import hash_code.algorithms.graph.interfaces.Node;
import hash_code.algorithms.graph.interfaces.SearchAlgorithm;
import hash_code.algorithms.graph.interfaces.ValueNode;
import hash_code.contest_2017.practice.pizza.PizzaGridImp;
import hash_code.contest_2017.practice.pizza.Slide;


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
		String[] sCadena = null;
		try {

			BufferedReader bf = new BufferedReader(new FileReader("./TEST/2017/practice/samll.in"));
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
			
			Node graph = new NodeImp(new PizzaGridImp(grid, L , H));
			
			SearchAlgorithm bfs = new BreadthFSSearchAlgorithm();
			Node bestNode = bfs.find(graph, null);
			
			System.out.println(bestNode);
			
			/*// sint T = Integer.parseInt(bf.readLine());
			Files file = new Files("output", false);
			file.write(output);
			file.close();*/

			
			bf.close();
		} catch (IOException e) {
		}
	}

	/*
	public static void main(String[] args){
		
		Grid pizza = new PizzaGridImp(grid);
		((PizzaGridImp)pizza).add(new Slide(0,0,R,C));
		
		State initial = new State(pizza);
		
		SearchAlgorithm bfs = new SearchAlgorithm();
		System.out.println(bfs.breadthFirstSearch(initial, initial));
		
	}*/
}