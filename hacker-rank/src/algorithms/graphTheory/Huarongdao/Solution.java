package algorithms.graphTheory.Huarongdao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution {

	public static Node[][] board;
	public static int[] problem;
	public static Scanner in;
	
	
	public enum MOVE {
		CAN, CAN_NOT
	}
	
	
	/**
	 * Class Node
	 * @author cguzman
	 * */
	public class Node{
		public int dist = -1;
		public MOVE canMove;
		public boolean explored = false;
		public int x;
		public int y;
		
		// constructor
		public Node(){	}
		
		// constructor sending the label of the node
		public Node(MOVE i, int x, int y){
			canMove = i;
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals(Object obj) {
			Node exit = (Node) obj;
			if(x == exit.x && y == exit.y)
				return true;
			return false;
		}

		@Override
		public String toString() {
			return "[" + x +", "+ y + "] "+canMove.ordinal()+" ";
		}
	}

	public class State{

		public Node empty;
		public Node caoCao;
		public Node exit;
		
		public int dist;


		public State(Node empty2) {
			empty = empty2;
			dist = 0;
		}
		
		public State(Node empty2, Node caoCao) {
			empty = empty2;
			this.caoCao = caoCao;
			dist = 0;
		}

		public State(Node empty2, Node caoCao, Node exit) {
			empty = empty2;
			this.caoCao = caoCao;
			this.exit = exit;
			dist = 0;
		}

		public boolean isGoal(Node goal) {
			return caoCao.equals(goal);
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof State))
				return false;
			
			State t = (State)obj;
			return (t.caoCao.equals(caoCao) && t.empty.equals(empty));
		}

		public Node[] getNeighbors(Node[][] board){
			Node u = empty;
	    	
			Node[] childs;
	    	int pos = 0;
	    	
	    	int size = 4;
	    	if((u.x == 0 || u.x == board.length-1) && (u.y == 0 || u.y == board[0].length-1))
	    		size = 2;
	    	else{
		    	if((u.x == 0 || u.x == board.length-1) && (u.y != 0 && u.y != board[0].length-1))
		    		size = 3;
		    	else{
		    		if(u.y == 0 || u.y == board[0].length-1)
		    			size = 3;
		    	}
	    	}
	    	
	    	childs = new Node[size+1];
	    	
	    	// up 
	    	int i = u.x-1;
	    	if(i >= 0){
	    		childs[pos] = board[i][u.y];pos++;
	    	}
	    	// down
	    	i = u.x+1;
	    	if(i < board.length){
	    		childs[pos] = board[i][u.y];pos++;
	    	}
	    	//left
	    	i = u.y-1;
	    	if(i >= 0){
	    		childs[pos] = board[u.x][i];pos++;
	    	}
	    	// right
	    	i = u.y+1;
	    	if(i < board[0].length){
	    		childs[pos] = board[u.x][i];pos++;
	    	}
	    	
	    	return childs;
	    }

		@Override
		public String toString() {
			return "State [empty=" + empty + ", caoCao=" + caoCao + ", distance=" + dist + "]";
		}
	}
	
	public class StateDistComparator implements Comparator<State>{
		
		@Override
		public int compare(State x, State y){
			
			// the two elements are equals
			return x.dist - y.dist;

		}
	}

    
	static Solution sol = new Solution();
    
    public static void main(String[] args) {
    	
    	try {
        	File file = new File("./TEST/algorithms/graphTheory/Huarongdao/input-test2.txt");
        	loadFile(file);
        	// loadFile(null);
    		
        	readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    static void loadFile(File file) throws FileNotFoundException{
    	in = new Scanner(file);
    	// in = new Scanner(System.in);

    }
    
    static void readFile() {
    	
     	String[] temp;
 		
 		// creating the set of nodes of the undirected graph
 		// in.nextLine();
 		temp = in.nextLine().split(" ");
 		int N = Integer.parseInt(temp[0]);
 		int M = Integer.parseInt(temp[1]);
 		int K = Integer.parseInt(temp[2]);
 		int Q = Integer.parseInt(temp[3]);

 		// load the board information
    	loadBoard(N,M);
    	
    	// find a solution
 		solveBoard(K, Q);
	}

	public static Node[][] loadBoard(int N, int M) {
		board = new Node[N][M];
    	int i;
    	for(int j=0; j < N;++j){
    		i = 0;
    		while(i < M){
    			MOVE option = (in.nextInt()==1)?MOVE.CAN:MOVE.CAN_NOT;
    			board[j][i] = sol.new Node(option, j, i);
    			i++;
    		}
    	}

    	/*for(int j=0; j < N;++j){
    		System.out.println();
    		for(i=0; i < M;++i){
    			System.out.print(board[j][i].toString());
    		}
    	}*/
		
    	return board;
	}
	
    public static void solveBoard(int K, int Q){
    	int i = 0;
 		problem = new int[6];
 		while(i < Q){

 			problem[0] = in.nextInt()-1; // Empty X
 			problem[1] = in.nextInt()-1; // Empty Y
 			problem[2] = in.nextInt()-1; // Cao Cao block X
 			problem[3] = in.nextInt()-1; // Cao Cao block Y
 			problem[4] = in.nextInt()-1; // Exit X
 			problem[5] = in.nextInt()-1; // Exit Y
 			
 			++i;
 			// solving the problem

 			System.out.println(findBestMove(board, problem, K));
 		}
    }

	public static int findBestMove(Node[][] board, int[] problem, int k) {

    	List<State> visited = new ArrayList<State>();
    	Node 		goal 	= getExit(board, problem);
    	Node 		exit 	= getExit(board, problem);
    	Node[] 		neighbors;
    	State 		newState;
    	
    	Comparator<State> comparator = (new Solution()).new StateDistComparator();
    	Queue<State> Q = new PriorityQueue<State>(board.length*board[0].length, comparator);
    	
    	// we add the Start element
    	State initial = getInitialState(board, problem);
    	Q.add(initial);
    	while(!Q.isEmpty()){
    		
    		State u = Q.remove();
    		
    		// if it is the exit block
    		if(u.isGoal(goal)){
    			// System.out.println("Found the goal: "+u.toString()+" "+goal.toString());
    			return u.dist;
    		}
    		
    		// we put the node as explored
        	visited.add(u);
    		
    		// we get the neighbors of the node, adding always the exit node
    		neighbors = u.getNeighbors(board);
    		neighbors[neighbors.length-1] = exit;

    		// System.out.println("\n\n------ EVALUATING ------");
    		// System.out.print("\n"+u.toString()+" childs: ");
    		
    		// for each neighbors
    		for(Node v: neighbors){
    			
    			// System.out.print(v.toString()+" ");
    			
    			if(v.canMove == MOVE.CAN){
   					
    				// we create the new state assigning its distance
    				newState = createState(u, v);
    				newState.dist = u.dist + getDistance(u.empty, v, exit, k);
    				
    				// if the state is visited previously
    				int c = visited.indexOf(newState);
    				if(c != -1){
    					if(newState.dist < visited.get(c).dist)
    						visited.get(c).dist = newState.dist;
    				}else{
    					if(!Q.contains(newState))
    						Q.add(newState);
    				}
    			}
    		}
    	}
    	
    	
    	return -1;
	}
    
    static State getInitialState(Node[][] board, int[] problem) {
		return sol.new State(board[problem[0]][problem[1]], board[problem[2]][problem[3]], board[problem[4]][problem[5]]);
	}

	/**
	 * DEFINING COMPARATORS
	 */
	public static class PSComparators{
		
		public static Comparator<State> comparatorX = new Comparator<State>() {
			public int compare(State o1, State o2) {
			    return (o1.equals(o2))?1:0;
			}
		};
	}
    
    private static State createState(State u, Node v) {
		
    	State tmp = sol.new State(v, u.caoCao);
    	if(u.caoCao.equals(v)){
    		tmp = sol.new State(u.caoCao, u.empty);
    	}

    	return tmp;
    	
	}

	private static int getDistance(Node u, Node v, Node exit, int k) {
    	
		if(u == null && v == null && exit == null)
			throw new NullPointerException("Node u, v and exit are empty, k="+k);
		if(u == null && v == null)
			throw new NullPointerException("Node u and v are empty, exit="+exit.toString()+", k="+k);
		if(u == null)
			throw new NullPointerException("Node u is empty, v="+v.toString()+", exit="+exit.toString()+", k="+k);
		if(v == null)
			throw new NullPointerException("Node v is empty, u="+u.toString()+", exit="+exit.toString()+", k="+k);
		if(exit == null)
			throw new NullPointerException("Node exit is empty, u="+u.toString()+", v="+v.toString()+", k="+k);
		
    	if(v.equals(exit))
    		return k;
    		
		return 1;
	}

	

	private static Node getEmpty(Node[][] board, int[] problem) {
		return board[problem[0]][problem[1]];
	}
	
	private static Node getCaoCao(Node[][] board, int[] problem) {
		return board[problem[2]][problem[3]];
	}
	
	private static Node getExit(Node[][] board, int[] problem){
		return board[problem[4]][problem[5]];
	}

}
