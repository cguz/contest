package artificialIntelligence.BotBuilding.BotSavesPrincess;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;


public class Solution {
	
    /** Initial and goal nodes.*/
    public static int init=0;
	public static int goal=0;
	
     /** A* closed and open lists.*/
    private static Map<Integer,Node> 	nodes=null;
    private static Map<Integer,Integer> came_from=null;
    private static HashSet<Integer>		closed=null;
    private static PriorityQueue<Node> 	open=null;

    public static class Node {
		private int index;
        private char name;
		private int i;
		private int j;
		private int m;


		private int g;
		private int f;

		
        /** Construct init or goal node. 
         * @param index 
         * @param i 
         * @param j 
         * @param c 
         * @param m */
        public Node(int index, char c, int i, int j, int m) {
           f = 0;
           this.index = index;
           this.name = c;
           this.i=i;
           this.j=j;
           this.m=m;
        }

        public List<Integer> neighbor() {
			
        	List<Integer> neighbor = new ArrayList<Integer>();

        	if (j<m-1) neighbor.add(index+1);
        	if (j>0) neighbor.add(index-1);
        	
        	if (i<m-1) neighbor.add(index+m);
        	if (i>0) neighbor.add(index-m);
        	
			return neighbor;
		}

		public void f(int goal) {
        	
        	f = g + heuristic_cost_estimate(goal);
			
		}

		private int heuristic_cost_estimate(int goal) {

			int x_goal = nodes.get(goal).i;
			int y_goal = nodes.get(goal).j;
			
			int dx = i - x_goal; if (dx < 0) dx = -dx;
			int dy = j - y_goal; if (dy < 0) dy = -dy;
			int max = dx > dy ? dx : dy;
			
			if(max==0)return max;
			return max + (dx + dy - max) / (2 * max);
		}

		public void set_g(int k) {
			this.g = k;	
		}

		public int f() {
			return f;
		}
		
		public int g() {
			return g;
		}

    }
    
	/* Head ends here */
	static void nextMove(int m, String[] grid) {
		
		Node current;
		List<String> p = new ArrayList<String>(1);
		int index = 0;
		char c;
		nodes 		= new HashMap<Integer, Node>(m*m);
		came_from 	= new HashMap<Integer, Integer>(m*m);
		for(int i= 0;i<m;++i){
			int len = grid[0].length();
			for(int j=0;j<len;++j){
				c = grid[i].charAt(j);
				if(c=='m') init  = index;
				if(c=='p') goal  = index;
				nodes.put(index, new Node(index,c,i,j,m));
				++index;
			}
		}

        closed = new HashSet<Integer>();
        open   = new PriorityQueue<Node>(256,
            new Comparator<Node>() {
                public int compare(Node a, Node b) { return a.f() - b.f(); }
            });
        open.add(nodes.get(init));

        nodes.get(init).set_g(0);
        nodes.get(init).f(goal);
        
        while(!open.isEmpty()){
        	current = open.poll();
        	
        	if(current.index == goal)
        		p = reconstruct_path(came_from, goal, m);
        	
        	closed.add(current.index);
        	
        	List<Integer> l_neighbor = current.neighbor();
        	for(Integer neighbor : l_neighbor){
        		int tentative_g = current.g() + 1;
        		
        		if(closed.contains(neighbor))
        			if(tentative_g >= nodes.get(neighbor).g())
        				continue;
        		
        		if(!open.contains(nodes.get(neighbor)) || tentative_g < nodes.get(neighbor).g()){
        			came_from.put(neighbor, current.index);
        			nodes.get(neighbor).set_g(tentative_g);
        			nodes.get(neighbor).f(goal);
        			if(!open.contains(nodes.get(neighbor))){
        				open.add(nodes.get(neighbor));
        			}
        		}
        	}
        }
        

		for(String z: p)
			System.out.println(z);
        
	}
	
	private static List<String> reconstruct_path(Map<Integer, Integer> cameFrom, int goal2, int m) {
		List<String> p = new ArrayList<String>(cameFrom.size());
		if (cameFrom.containsKey(goal2)){
			p = reconstruct_path(cameFrom, cameFrom.get(goal2), m);
			p.add(moving(goal2, cameFrom.get(goal2), m));
			return p;
		}
		return p;
	}
	
	private static String moving(int cameFrom, int to, int m) {
		
		if(cameFrom > to){
			if(Math.abs(cameFrom-to)==m){
				return "DOWN";
			}else
				return "RIGHT";
		}else{
			if(Math.abs(cameFrom-to)==m){
				return "UP";
			}else
				return "LEFT";
		}
	}

	/* Tail starts here */
	public static void main(String[] args) throws FileNotFoundException {
		File fileInput = new File("./TEST/artificialIntellegence/BotBuilding/BotSavesPrincess/input/input00.txt");
		File fileOutput = new File("./TEST/artificialIntellegence/BotBuilding/BotSavesPrincess/output/output00.txt");
		Scanner in = new Scanner(fileInput);
		// Scanner in = new Scanner(System.in);

		int m;
		m = in.nextInt();
		String grid[] = new String[m];
		for (int i = 0; i < m; i++) {
			grid[i] = in.next();
		}

		nextMove(m, grid);
	}
}