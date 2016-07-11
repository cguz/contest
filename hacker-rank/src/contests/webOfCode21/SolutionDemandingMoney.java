package contests.webOfCode21;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class SolutionDemandingMoney {
    
	static SolutionDemandingMoney sol = new SolutionDemandingMoney();
	
	private static int max_money = 0;
	private static List<Result> paths = new ArrayList<Result>();
	
	
	/**
	 * Class Node
	 * @author cguzman
	 * */
	public class Node{
		public int money = 0;
		public int index;
		public List<Edges> childs = new ArrayList<Edges>();

		public boolean blocked=false;
		public boolean visited=false;
		
		// constructor
		public Node(){	}
		
		// constructor sending the label of the node
		public Node(int i){
			index = i;
		}
		
		public void addChild(Node n){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				childs.add(new Edges(n)); // ***
				n.add(this);
				
			}
			
		}
		
		public void add(Node n){
			
			int index = childs.indexOf(n);
			
			if(index == -1){
				
				childs.add(new Edges(n));
				
			}
			
		}
		
		public void blockChilds(boolean o){
			for(Edges d: childs)
				d.child.blocked = o;
		}

		@Override
		public String toString() {
			String c = "";
			for(Edges d: childs)
				c = c + " "+ (d.child.visited?"v":"")+(d.child.blocked?"b":"") + " "+d.child.index;
			return "[" + index + " - "+(visited?"v":"")+"] -> "+c;
			//return index+"";
		}

	}
	

	public class Edges {
		public Node child;
		
		public Edges(Node c){
			child = c;
		}

		@Override
		public boolean equals(Object obj) {
			
			if(!(obj instanceof Edges))
				return false;
			
			if(((Edges)obj).child.index != child.index)
				return false;
			
			return true;
		}
		
	}

	public class Result{
		public int money;
		public List<Node> path;
		
		public Result(){}
		
		public Result(int m, List<Node> p){
			money = m;
			path = p;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(!(obj instanceof Result))
				return false;
			
			Result t = (Result) obj;
			if(t.path.size() != path.size())
				return false;

			for(Node n: t.path){
				int index = path.indexOf(n);
				if(index == -1)
					return false;
			}
			
			return true;
		}
	}
	
	public class NodeChildComparator implements Comparator<Node>{
		
		@Override
		public int compare(Node x, Node y){
			
			// the Node y is higher
			if(x.childs.size() < y.childs.size())
				return -1;
			
			// the Node x is higher
			if(x.childs.size() > y.childs.size())
				return 1;
			
			// the two Nodes are equals
			return 0;
		}
	}
    
	
    public static void main(String[] args) throws FileNotFoundException {
    	
    	File file = new File("./TEST/algorithms/webOfCode21/DemandingMoney/input-test2.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);

    	int N = in.nextInt();
    	int M = in.nextInt();
    	
    	
    	// creating list of houses
    	List<Node> houses = new ArrayList<Node>(N);
    	for(int i=0; i < N;++i)
    		houses.add(sol.new Node(i));
 		
    	// creating list of money
    	int[] P = new int[N];
    	for(int i=0; i < N; ++i){
    		int T = in.nextInt();
    		P[i] = T;
    	}
    	
    	
    	for(int i=0; i < M; ++i){
    		int L1 = in.nextInt()-1;
    		int L2 = in.nextInt()-1;
    		
        	houses.get(L1).addChild(houses.get(L2));
    		
    	}
    	
    	System.out.println(maxMoney(houses, P));
    	
    }
    
    public static String maxMoney(List<Node> N, int[] money){
    	
    	for(int i=0; i < N.size(); i++){
    		Node u = N.get(i);
    		
    		// money, path
    		searchMoney(N, u, money);
    		
			initialize(N);
    		
    	}
    	
    	//if(max_money == 0)
    	//	return max_money+" "+(paths.size()+1);
		return max_money+" "+paths.size();
    }


	public static void initialize(List<Node> N){
    	for(Node n: N){
    		n.visited = false;
    		n.blocked = false;
    	}
    }
    
    
    public static void searchMoney(List<Node> N, Node Start, int[] money){
    	
    	Comparator<Node> comparator = (new SolutionDemandingMoney()).new NodeChildComparator();
    	Queue<Node> Q = new PriorityQueue<Node>(N.size(), comparator);
    	
    	// we add all elements
    	Start.money = money[Start.index];
    	save_path(Start, new ArrayList<Node>());
    	
    	Q.add(Start);
    	
    	List<Node> path = new ArrayList<Node>();
    	
    	while(!Q.isEmpty()){
    		Node u = Q.remove();
    		u.visited=true;
    		u.blockChilds(true);
    		path.add(u);
    		    		
    		List<Node> next = selectNode(N);
    		for(Node n: next){
	    		if(n.visited == false && n.blocked == false){
	        		n.money = u.money + money[n.index];
	            	save_path(n, path);
	        		
	    			if(!Q.contains(n))
	    				Q.add(n);
	    		}
    		}
    	}
    	
    }

    private static void save_path(Node n, List<Node> p) {

    	if(n.money > max_money){
			max_money = n.money;
			
			paths.clear();
			add_path(p, n);
		}else{
			if(n.money == max_money)
				add_path(p, n);
		}
    	
	}

	private static void add_path(List<Node> path, Node n) {
    	List<Node> s = new ArrayList<Node>(path);
		s.add(n);
		Result r = sol.new Result(max_money, s);
		int index = paths.indexOf(r);
		if(index == -1){
			paths.add(r);
			System.out.println("->"+path.toString()+n.toString());
		}
	}

	// we can here save all and return a list
	private static List<Node> selectNode(List<Node> n) {
		
		int j = 0;

		List<Node> next = new ArrayList<Node>();
		for(j=0;j < n.size(); j++){
			
			if(n.get(j).blocked || n.get(j).visited)
				continue;
			
			next.add(n.get(j));
			
		}
		
		return next;
	}
    
}
