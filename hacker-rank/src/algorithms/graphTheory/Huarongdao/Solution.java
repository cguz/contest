package algorithms.graphTheory.Huarongdao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution {

	
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

		public boolean isEqual(Node exit) {
			if(x == exit.x && y == exit.y)
				return true;
			return false;
		}
		
		@Override
		public String toString() {
			return "[" + canMove.ordinal() + "] ";
			/// return "[" + canMove.ordinal() + "](" + dist +") ";
		}
	}

	public class State{
		public Node empty;
		public Node caoCao;
		public Node exit;
		
		public int dist;
		
		public State(Node empty2, Node caoCao, Node exit) {
			empty = empty2;
			this.caoCao = caoCao;
			this.exit = exit;
			dist = 0;
		}

		public boolean isGoal(Node goal) {
			return empty.isEqual(goal);
		}

		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof State))
				return false;
			
			State t = (State)obj;
			return (t.caoCao.isEqual(empty) && t.empty.isEqual(exit) && t.exit.isEqual(exit));
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
    
    public static void main(String[] args) throws FileNotFoundException {
    	
    	File file = new File("./TEST/algorithms/graphTheory/Huarongdao/input-test.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);

 		// in.nextLine();
     	String[] temp;
 		
 		// creating the set of nodes of the undirected graph
 		temp = in.nextLine().split(" ");
 		int N = Integer.parseInt(temp[0]);
 		int M = Integer.parseInt(temp[1]);
 		int K = Integer.parseInt(temp[2]);
 		int Q = Integer.parseInt(temp[3]);

    	Node[][] board = new Node[N][M];
    	int i;
    	for(int j=0; j < N;++j){
    		i = 0;
    		while(i < M){
    			MOVE option = (in.nextInt()==1)?MOVE.CAN:MOVE.CAN_NOT;
    			board[j][i] = sol.new Node(option, j, i);
    			i++;
    		}
    	}
    	
    	for(int j=0; j < N;++j){
    		System.out.println();
    		for(i=0; i < M;++i){
    			System.out.print(board[j][i].toString());
    		}
    	}
    	
 		i = 0;
 		int[] problem = new int[6];
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

    	
    	/*int size = 3;
    	List<Node> N = new ArrayList<Node>(size);
    	for(int i=0; i < size;++i)
    		N.add(sol.new Node(i));

    	N.get(1).addChild(N.get(2));
    	
    	Node S = N.get(1);
    	S.dist=0;
    	
        String output = shortestReach(N, S);
        System.out.println(output);*/
    }
    
    private static int findBestMove(Node[][] board, int[] problem, int k) {
    	
    	List<State> reached = new ArrayList<State>();
    	
    	Comparator<State> comparator = (new Solution()).new StateDistComparator();
    	Queue<State> Q = new PriorityQueue<State>(board.length*board[0].length, comparator);
    	
    	// we add the Start element
    	State initial = getInitialState(board, problem);
    	Q.add(initial);
    	reached.add(initial);
    	
    	Node exit = getExit(board, problem);
    	Node[] neighbors;
    	while(!Q.isEmpty()){
    		State u = Q.remove();
    		
    		// if it is the exit block
    		if(u.isGoal(exit)){
    			System.out.println("Found the state with the exit block, apply cheating");
    		}
    		
    		// we put the node as explored
    		u.empty.explored = true;
    		
    		// we get the neighbors of the node, adding always the exit node
    		neighbors = getNeighbors(board, u.empty);
    		neighbors[neighbors.length-1] = exit;
    		
    		// for each neighbors
    		for(Node v: neighbors){
				int alt_dist = u.dist + getDistance(u.empty, v, exit, k);
    			if(v.explored == false){
   					
    				State t = createState(u, v);
    				int c = reached.indexOf(t);
    				if(c != -1){
    					if(t.dist < reached.get(c).dist)
    						reached.get(c).dist = t.dist;
    				}else{
        				t.dist = alt_dist;
    					Q.add(t);
    				}
    			}
    		}
    	}
    	
    	String output = "Infinity";
    	/*int dist = N.get(D.canMove).dist;
    	if (dist != -1)
    		output = ""+dist;*/
    	
    	return -1;
	}
    
    private static State getInitialState(Node[][] board, int[] problem) {
		return sol.new State(board[problem[0]][problem[1]],
    			board[problem[2]][problem[3]],
    			board[problem[4]][problem[5]]);
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
		
    	State tmp = sol.new State(v, u.caoCao, u.exit);
    	if(u.exit.isEqual(v)){
    		tmp = sol.new State(u.exit, u.caoCao, u.empty);
    	}else{
        	if(u.caoCao.isEqual(v)){
        		tmp = sol.new State(u.caoCao, u.empty, u.exit);
        	}
    	}
    	
		return tmp;
	}

	private static int getDistance(Node u, Node v, Node exit, int k) {
    	
    	if(v.isEqual(exit))
    		return k;
    		
		return 1;
	}

	private static Node[] getNeighbors(Node[][] board, Node u){
    	Node[] childs;
    	int pos = 0;
    	
    	int size = 4;
    	if((u.x == 0 || u.x == 4) && (u.y == 0 || u.y == 4))
    		size = 2;
    	else{
	    	if((u.x == 0 || u.x == 4) && (u.y != 0 && u.y != 4))
	    		size = 3;
	    	else{
	    		if(u.y == 0 || u.y == 4)
	    			size = 3;
	    	}
    	}
    	
    	childs = new Node[size+1];
    	
    	// up 
    	int i = u.x-1;
    	if(i >= 0){
    		if(board[i][u.y].canMove == MOVE.CAN)
    			childs[pos] = board[i][u.y];pos++;
    	}
    	// down
    	i = u.x+1;
    	if(i < board.length){
    		if(board[i][u.y].canMove == MOVE.CAN)
    			childs[pos] = board[i][u.y];pos++;
    	}
    	//left
    	i = u.y-1;
    	if(i >= 0){
    		if(board[u.x][i].canMove == MOVE.CAN)
    			childs[pos] = board[u.x][i];pos++;
    	}
    	// right
    	i = u.y+1;
    	if(i < board[0].length){
    		if(board[u.x][i].canMove == MOVE.CAN)
    			childs[pos] = board[u.x][i];pos++;
    	}
    	
    	return childs;
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
