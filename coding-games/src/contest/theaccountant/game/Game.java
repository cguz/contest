package contest.theaccountant.game;

import java.util.ArrayList;

import contest.theaccountant.GameActions;
import contest.theaccountant.Plan.COMMAND;
import contest.theaccountant.Plan.Command;
import contest.theaccountant.Plan.TARGET;


public class Game {
	
	/**
	 * @author Cguzman
	 * LinkedList to save the enemies in the same position
	 */
	public static class NodeData{

		public PointId character;
		public NodeData next=null;
		
		public NodeData head= null;
		
		public NodeData(PointId c){
			character = c;
			head = this;
		}

		public void insert(NodeData data) {
			NodeData n = head;
			
			while(n.next != null){
				if(n.next.character.getId() < data.character.getId())
					n=n.next;
				else break;
			}
			
			if(n.next == null)
				n.next = data;
			else{
				NodeData temp = n.next;
				n.next = data;
				data.next = temp;
			}
		}

		/**
		 * We remove the first DataPoint changing is value to eliminate
		 */
		public void eliminateFirst() {
			
			PointId n = pollFirst();
			
			if(n != null)
				n.changeToEliminate();
						
		}

		/**
		 * @return the first node that is not eliminated
		 */
		public PointId pollFirst() {
			
			NodeData p = head;
			
			// if there is a node, we go through the linkedlist
			if(p != null){
			
				// we find a node that is not eliminated
				while(p.next!=null && p.character.isEliminated()){
					p = p.next;
				}			
				
				// if the last node is eliminated
				if(!p.character.isEliminated())
					return p.character;
				
			}
			
			return null;
			
		}
		
		public NodeData remove(NodeData data) {
			
		    NodeData p = head;
		 
		    if(p != null){
			    while(p.next != null){
			        if(p.next.character == data.character){
			            p.next = p.next.next; 
			            return p.next;
			        }else{
			            p = p.next;
			        }
			    }
			    
			    if(p.character == data.character){
			    	head = null;
			    	return p;
			    }
		    }
		 
		    return null;
			
		}
		
		@Override
		public String toString() {
			if(character instanceof Data)
				return ((Data)character).toString();
			else
				return ((Enemy)character).toString();
		}

	}
	
	/**
	 * @author Cguzman
	 * Node for the binary search tree
	 */
	public static class Node{
		public int distance;
		public NodeData data;
		
		public Node parent=null;
		public Node left=null;
		public Node right=null;
		
		public Node(int d, PointId ch){
			distance = d;
			data = new NodeData(ch);
		}

		@Override
		public String toString() {
			return "[" + distance + "," + data + "]";
		}
		
	}
	
	/**
	 * @author Cguzman
	 * Binary Search Tree to save the enemies and the data with respect 
	 * to the initial position (0,0)
	 */
	public static class BinarySearchTree {
		protected Node root = new Node(0, new PointId(0, 0, 0));
		protected int size = 0;
		public int size_in_game=0;

		
		protected ArrayList<PointId> dataAsArray;
		
		
		public void insert(PointId c){
			
			Node node = new Node(root.data.character.getDistance(c), c);
			
			if(root==null)
				root = new Node(0, new PointId(GameActions.bstRootId, GameActions.bstRootX, GameActions.bstRootY));
			
			insert(root, node);

			size++;
			if(!c.isEliminated())
				size_in_game++;
			
		}

		private void insert(Node c, Node n){
			if(c.distance > n.distance){// higher
				if(c.left == null){
					c.left = n;
					n.parent = c;
					return;
				}else insert(c.left, n);
			}else{
				if(c.distance < n.distance){ // low
					if(c.right == null){
						c.right = n;
						n.parent=c;
						return;
					}else insert(c.right, n);
				}else{ // equals
					c.data.insert(n.data);
				}
			}
		}
		
		public PointId search(PointId c){
			
			Node node = new Node(root.data.character.getDistance(c), c);
			
			return search(root, node);
			
		}
		
		/*private PointId search(Node c, Node n){

			PointId f;
			
			if(c.distance > n.distance){ // high
				if(c.left != null){
					f = search(c.left, n);
					if(f == null && c.right != null)
						f = search(c.right, n);
					if(f!=null)
						return f;
				}
			}else{
				if(c.distance < n.distance){ // low
					if(c.right != null){
						f = search(c.right, n);
						if(f == null && c.left != null)
							f = search(c.left,n);
						if(f!=null)
							return f;
					}
				} // equals
			}
			
			return c.data.pollFirst();
			
		}*/
		
		private PointId search(Node c, Node n){

			PointId f;
			
			if(c.distance > n.distance){ // high
				if(c.left != null){
					f = search(c.left, n);
					if(f == null && c.right != null)
						f = search(c.right, n);
					if(f!=null)
						return f;
				}
			}else{
				if(c.distance < n.distance){ // low
					if(c.right != null){
						f = search(c.right, n);
						if(f == null && c.left != null)
							f = search(c.left,n);
						if(f!=null)
							return f;
					}
				}
			}
			
			f = c.data.pollFirst();
			if(f!=null)
				return f;
			else{
				if(c.left != null)
					f = search(c.left, n);
				if(f!=null)
					return f;
				if(c.right != null)
					return search(c.right, n);
			}
			return null;
		}
		
		
		public void remove(PointId min) {
			Node node = new Node(root.data.character.getDistance(min), min);
			remove(root, node);
			
			size--;
			size_in_game--;
			dataAsArray = null;
			
		}
		
		public void remove(Node c, Node n){
			if(c == null)
				return;
			
			if(c.distance == n.distance){ // equals
				c.data.remove(n.data);
				if(c.data.pollFirst() == null){
					/* if the node has no child */
					if(c.left == null && c.right == null){

						if(c == root){
							root = new Node(0, new PointId(0, 0, 0));
							return;
						}

						/* we remove the link from the parent node */
						removeLinkParent(c, null);

						return;

					}

					/* if the node is with one child */
					if(c.left == null){
					
						if(c == root){
							root = root.right;
							return;
						}
					
						/* make the parent point to the right child */
						removeLinkParent(c, c.right);
						c.right.parent = c.parent;

						return;
					}

					if(c.right == null){
						if(c == root){
							root = root.left;
							return;	
						}

						/* make the parent point to the left child */
						removeLinkParent(c, c.left);
						c.left.parent = c.parent;
							
						return;
					}
					
					/* node with two children */
							
					/* findSuccessorNode */
					Node temp = findSuccessorNode(c);
					
					/* swap the data of the to-be-removed node with the follower*/
					swapNodeData(c, temp);

					/* call removenodeBST on the follower node */
					remove(c.right, temp);
				}
			}else{
				if(c.distance > n.distance){ // high
					remove(c.left, n);
				}else{
					remove(c.right, n);
				}
			}
		}
		
		/* help function which swaps data of two given nodes */
		void swapNodeData(Node node1, Node node2){

			int distance = node1.distance;
			NodeData data = node1.data;
			
			node1.distance = node2.distance;
			node1.data = node2.data;
			
			node2.distance = distance;
			node2.data = data;

		}
		
		/* help function which tries to find the successor to a given node or NULL if the node has no successor in the BST */
		public Node findSuccessorNode(Node node){
			
			Node current = null;

			/* If the node has a right child, then the follower of a node is the smallest */
			if(node.right != null){

			 	current = node.right;

				while(current.left != null)
					current = current.left;
			
				return current;

			}

			/* If the node has no children */
			if(node.left == null && node.right == null){
				current = node.parent;
			
				while(current.right == node){
					current = current.parent;
				}

				return current;
			}

			return current;
		}

		/* change the link of the parent node */
		private void removeLinkParent(Node c, Node toLink) {

			Node parent = c.parent;
			
			if(parent.left == c)
				parent.left = toLink;
			else
				parent.right = toLink;
		
		}
		
		public void eliminate(PointId min) {
			Node node = new Node(root.data.character.getDistance(min), min);
			eliminate(root, node);
			// size--;
			size_in_game--;
		}
		
		public void eliminate(Node c, Node n){
			if(c == null)
				return;
			
			if(c.distance == n.distance){ // equals
				c.data.eliminateFirst();
			}else{
				if(c.distance > n.distance){ // high
					eliminate(c.left, n);
				}else{
					eliminate(c.right, n);
				}
			}
		}
		
		@Override
		public String toString() {
			return size_in_game+"["+preoderPrint()+"]";
		}
		
		public String preoderPrint(){
			String temp = "";
			temp+=preorderPrint(root.left)+",";
			temp+=preorderPrint(root.right);
			return temp;
		}

		public String preorderPrint(Node r){

			String temp = "";
			
			if(r != null){

				if(r.data.character instanceof Enemy && !r.data.character.isEliminated())
					temp+=((Enemy)r.data.character).toString();
				
				if(r.data.character instanceof Data && !r.data.character.isEliminated())
					temp+=((Data)r.data.character).toString();
				
				if(r.left != null)
					temp+=","+preorderPrint(r.left);
			
				if(r.right != null)
					temp+=","+preorderPrint(r.right);
				
			}
			
			return temp;
			
		}
		

		/**
		 * @return DataPoints in preorder traversal
		 */		
		public void preorder(){
			preorder(root.left);
			preorder(root.right);
		}
		
		public void preorder(Node r){

			if(r != null){
				
				visit(r);

				preorder(r.left);
			
				preorder(r.right);
				
			}
			
		}
		
		public void visit(Node r){
			NodeData n = r.data;
			while(n.next != null){
				if(!n.character.isEliminated()){
					dataAsArray.add(n.character);
				}
				n = n.next;
			}
			if(!n.character.isEliminated())
				dataAsArray.add(n.character);
		}

		public boolean isEmpty() {
			return size_in_game == 0;
		}
		
		public int getDataPointsSize(){
			return size_in_game;
		}
		
	}
	
	
	/**
	 * @author Cguzman
	 * BinarySearchTree for the enemies
	 */
	public static class BinarySearchTreeEnemy extends BinarySearchTree {

		private int totalLifeEnemies = 0;
		
		public void visit(Node r){
			NodeData n = r.data;
			while(n.next != null){
				if(((Enemy)n.character).currentLife > 0)
					dataAsArray.add(n.character);
				n = n.next;
			}
			if(((Enemy)n.character).currentLife > 0)
				dataAsArray.add(n.character);
		}

		public int lifeEnemies() {
			return totalLifeEnemies;
		}
		
		public void increaseLife(int life) {
			totalLifeEnemies+=life;		
		}
	}
	
	

	public interface Character{
		
	}
	
	public static class CharacterHeroImp extends Point implements Character{

		private int steps;
		
		public CharacterHeroImp(int steps, int x, int y) {
			super(x, y);
			this.steps = steps;
		}
		
		public int getSteps(){
			return steps;
		}
		
		public double getValueSteps(Point target){
			return  getSteps() / Math.sqrt(getDistance(target));
		}
	}
	
	public static class CharacterEnemyImp extends PointId implements Character{

		private int steps;
		
		public CharacterEnemyImp(int id, int steps, int x, int y) {
			super(id, x, y);
			this.steps = steps;
		}
		
		public int getSteps(){
			return steps;
		}

		public double getValueSteps(Point target){
			return  getSteps() / Math.sqrt(getDistance(target));
		}

	}

	public static class Hero extends CharacterHeroImp {

		private int maxClosestEnemy = 2000;
		
		public Hero(int steps, int x, int y) {
			super(steps, x, y);
		}

		@Override
		public String toString() {
			return "H(" + getX() + ", " + getY() + ", "+getSteps()+", "+maxClosestEnemy+")";
		}

		/**
		 * @param enemy Point where the enemy is
		 * @return damage that receive the enemy in a BigDecimal format 
		 */
		public double shoot(PointId enemy) {
			
			int dist = getDistance(enemy);
			
			double damage = Math.sqrt(dist);
			damage = Math.pow(damage, 1.2);
			return Math.round(125000 / damage); // new BigDecimal(GameActions.getTestDamageEnemy())
			
		}

	}
	
	public static class Enemy extends CharacterEnemyImp {
		
		private int maxClosestData = 500;

		private int currentLife;
		public int initialLife;
		
		public Enemy(int id, int steps, int x, int y, int life){
			super(id, steps, x, y);
			this.initialLife = this.currentLife = life;
			if(initialLife <= 0)
				changeToEliminate();
		}
		
		@Override
		public String toString() {
			return "E("+isEliminated()+","+getId()+", " + getX() + ", " + getY() + ", "+getSteps()+", "+maxClosestData+", "+currentLife+", "+initialLife+")";
		}
		
		public int getLife() {
			return currentLife;
		}

		public void decreaseDamage(double damage) {
			currentLife-=damage;
		}
		
		public void setDefault(){
			currentLife = initialLife;
			currentLife = initialLife;
		}
		
		public void changeInitialLife(int life){
			initialLife = life;
		}
	}

	public static class Point{
		private int initialX;
		private int initialY;

		private int currentX;
		private int currentY;
		
		public Point(int x, int y){
			this.initialX = x;
			this.initialY = y;
			
			// we set the current position to the initial position
			setInitial();
		}
		
		public void setInitial(){
			currentX = initialX;
			currentY = initialY;
		}
		
		/**
		 * change the current position
		 * @param p that represents the current location of the character
		 */
		public void changeCurrentPosition(Point p) {
			currentX = p.getX();
			currentY = p.getY();
		}
		
		/**
		 * change the initial position
		 * @param p that represents the initial / current location of the character
		 */
		public void changeInitialPosition(Point p) {
			initialX = p.getX();
			initialY = p.getY();
			
			// we set the current position to the initial position
			setInitial();
		}
		
		// is the same as the enemy
		public Point getPointToMove(Point target) {

			int dx = target.getX()-getX();
			int dy = target.getY()-getY();

			double ratio = getValueSteps(target);
			
			double finalX = Math.round(getX()+ (ratio*dx));
			double finaly = Math.round(getY()+ (ratio*dy));

			// we calculate the point
			return new Point((int)finalX, (int)finaly);
		}
		
		public double getValueSteps(Point target){
			return 1;
		}

		public int getDistance(Point p) {
			return (int) Math.round(Math.pow(getX()-p.getX(), 2)+Math.pow(getY()-p.getY(), 2));
		}
		
		public int getX(){
			return currentX;
		}
		public int getY(){
			return currentY;
		}
	}
	
	public static class PointId extends Point{
		private int id;
		private boolean initialEliminated=false;
		private boolean currentEliminated=false;

		public PointId(int id, int x, int y) {
			super(x, y);
			this.id = id;
		}

		public int getId(){
			return id;
		}
		
		public void setInitialEliminated(){
			currentEliminated = initialEliminated;
		}
		
		public void changeToEliminate(){
			currentEliminated = true;
		}
		
		public void changeInitialToEliminate(){
			initialEliminated = true;
		}
		
		public boolean isEliminated(){
			return currentEliminated;
		}
	}
	
    public static class Data extends PointId {

		public Data(int id, int x, int y) {
			super(id, x, y);
		}

		@Override
		public String toString() {
			return "D("+isEliminated()+","+getId()+", " + getX() + ", " + getY()+")";
		}
	}
	
	public static void main(String[] args) {

		Game g = new Game();
		System.out.println(g.toString());
	}

    
    @Override
	public String toString() {
		return "[" + myHero + "\n\nEnemies=" + enemies + "\n\ndataPoints=" + dataPoints + "]";
	}


	/** Holds the Hero */
    private Hero myHero;

	/** Holds the dataPoints */
    private BinarySearchTree dataPoints = new BinarySearchTree();
    
    /** Holds the enemies */
    private BinarySearchTreeEnemy enemies = new BinarySearchTreeEnemy();
    
    /** data score **/
    private int score = 0;
    
    // ESCAPE 
	Point min = new Point(GameActions.wide, GameActions.high);
	Point max = new Point(0,0);
    
    
    /** Generates randomly a game */
    public Game(){ 
    	int i;
    	
    	// generate the hero
    	myHero = new Hero(1000, GameActions.getX(), GameActions.getY());
    	
    	// generate the enemies
    	int total = 1+ GameActions.getEnemyCount();
    	for(i=0; i < total; ++i){
    		Enemy e = new Enemy(i, 500, GameActions.getX(), GameActions.getY(), 
    				1+GameActions.getLife());
    		enemies.insert(e);
    		enemies.increaseLife(e.initialLife);
    	}
    	
    	// generate the data points
    	total = 1+ GameActions.getDataCount();
    	for(i=0; i < total; ++i){
    		dataPoints.insert(new Data(i, GameActions.getX(), GameActions.getY()));
    	}
    }
    
    public Game(Hero h, BinarySearchTreeEnemy e, BinarySearchTree d){
    	myHero = h;
    	enemies = e;
    	dataPoints = d;
    }

	public int getDataPointsSize(){
		return dataPoints.getDataPointsSize();
	}


	/**
	 * used in the GA
	 * @param action to execute
	 * @return the score
	 */
	public int execute(Command action) {
		
		score = 0;
		
		// moving the enemies to the closest data point
		moveEnemies();
		
		// we update the target of the action
		if(action.getTarget() == TARGET.ENEMY)
			action.setPoint(action.getPointTarget());
		
		return executeWolff(action);
		
	}
	
	/**
	 * used to simulate the execution of the action
	 * @param action to simulate
	 * @return the score
	 */
	public int simulateExecution(Command action) {
		
		score = 0;
		
		// moving the enemies to the closest data point
		moveEnemies();
		
		return executeWolff(action);
		
	}
	
	private int executeWolff(Command action){
		//System.out.println("*************\n\nGame: "+GameActions.getGame().toString());
		//System.out.println("\n"+action.toString());
		
		// if a move command, move wolff
		if(action.getAction() == COMMAND.MOVE)
			moveWolff(action);
		
		// if a move command, move wolff
		if(action.getAction() == COMMAND.ESCAPE)
			escapeWolff(action);
		
		// Game over if an enemy is close enough to Wolff
		if(enemyIsCloseToHero())
			return -1;
		
		// if a shoot command, wolff shoot
		if(action.getAction() == COMMAND.SHOOT)
			shootWolff(action);
		
		//System.out.println("*************\n\nGame: "+GameActions.getGame().toString());
		
		// remove enemies with zero life
		// removeEnemies();
		
		// enemies that collect data points, change its position
		
		return score;
	}

	public Command executePredictMove(Command act) {

		if(act.getTarget() == TARGET.ENEMY){
			
			// moving the enemies to the closest data point
			Point p = moveEnemy((Enemy)act.getPointTarget());
			
			try {
				act.setPoint(new PointId(act.getPointTarget().getId(), p.getX(), p.getY()));
			} catch (Exception e) {
				e.printStackTrace();
				p = moveEnemy((Enemy)act.getPointTarget());
				if(act.getPointTarget() == null)
					System.out.println("s");
			}

		}
		
		return act;
	}
	
	private void moveEnemies(){
		
		int i;
		Enemy e;
		
		// for each enemy
		for(i=0; i< GameActions.getEnemies().size(); ++i){

			e = (Enemy)GameActions.getEnemies().get(i);
			
			// if the enemy is eliminated, we continue with the next enemy
			if(e == null || e.isEliminated())
				continue;
			
			Point p = moveEnemy(e);
			
			if(p == null)
				break;
			
			// change the coordinate of the enemy.
			changePosition(e, p);
			
			// ESCAPE: saving the minimum and maximum coordinate
			saveMinMaxCoord(e);
		}
		
	}

	private void saveMinMaxCoord(Enemy e) {
		int x = min.getX();
		int y = min.getY();
		// minimum
		if(e.getX() < x){
			x = e.getX();
		}
		if(e.getY() < y){
			y = e.getY();
		}
		min.changeInitialPosition(new Point(x, y));
		
		// maximum
		x = max.getX();
		y = max.getY();
		if(e.getX() > x){
			x = e.getX();
		}
		if(e.getY() > y){
			y = e.getY();
		}
		max.changeInitialPosition(new Point(x, y));
		
	}


	private Point moveEnemy(Enemy e){

		Data min = null;
    	
		Point p = null;
		
    	// System.out.println("test: "+i+" - "+min+" - "+e);
    	
		// find the closest data
		min = findClosestData(e);
		
		// if the closest data is null, we stop because there is not more dataPoint
		if(min == null)
			return p;
		
		// move the enemy to the closest data
		int dist = min.getDistance(e);
		
		// if enemy is close to data
		double distPow = e.getSteps()*e.getSteps();
		if(dist <= distPow){
			
			// we change the coordinate of the enemy to the data
			p = new Point(min.getX(), min.getY());
			
			// 6. and we remove the data
			removeData(min);
			
		}else{ // otherwise
			
			// we get the point to move
			p = e.getPointToMove(min);
		}
		
		return p;
		
	}
	
	private void changePosition(Enemy e, Point p) {
		
		removeEnemy(e);
		e.changeCurrentPosition(p);
		enemies.insert(e);
		
	}
	
	private void removeEnemy(Enemy e){
		enemies.remove(e);
	}


	private void removeData(Data min) {
		dataPoints.eliminate(min);

		if(GameActions.SIMULATE==true)
			min.changeInitialToEliminate();
	}


	private Data findClosestData(PointId p){
		Data d = null;
		
		if(dataPoints.isEmpty())
			return d;
		
		try {
			d = (Data) dataPoints.search(p);
		} catch (Exception e) {
			if(GameActions.DEBUG){
				System.out.println("\n[ERROR] "+d+" "+p+"\n\n");
			}
			e.printStackTrace();
			System.exit(-1);
		}
		return d;
	}
	
	private void moveWolff(Command action){
		Hero e = myHero;
		
		Point target = new Point(action.getCoordX(), action.getCoordY());
		
		// move the hero to the target coordinate
		int dist = e.getDistance(target);
		
		Point p;
		
		// if enemy is close to data
		double distPow = e.getSteps()*e.getSteps();
		if(dist <= distPow){
			
			// we change the coordinate of the hero to the data
			p = new Point(target.getX(), target.getY());
			
		}else{ // otherwise
			
			// we get the point to move
			p = e.getPointToMove(target);
		}
		
		// change the coordinate of the hero.
		e.changeCurrentPosition(p);
	}
	
	private void escapeWolff(Command action){
		Hero e = myHero;
		
		int x = e.getX();
		int y = e.getY();
		
		// if Hero is inside the axis x of the enemies
		if(min.getX() < x && x < max.getX()){
			if(x-e.getSteps() >= 0){
				x-= e.getSteps();
			}else{
				if(x+e.getSteps() <= GameActions.wide)
					x+=e.getSteps();
			}
		}else{ // otherwise
			if(x <= min.getX()){
				if(x-e.getSteps() >= 0)
					x-=e.getSteps();
			}else{
				if(x >= max.getX()){
					if(x+e.getSteps() <= GameActions.wide)
						x+=e.getSteps();
				}
			}
		}
		
		// same for the axis y
		if(min.getY() < y && y < max.getY()){
			if(y-e.getSteps() >= 0){
				y-= e.getSteps();
			}else{
				if(y+e.getSteps() <= GameActions.high)
					y+=e.getSteps();
			}
		}else{
			if(y <= min.getY()){
				if(y-e.getSteps() >= 0)
					y-=e.getSteps();
			}else{
				if(y >= max.getY()){
					if(y+e.getSteps() <= GameActions.high)
						y+=e.getSteps();
				}
			}
		}

		// we get the point to move
		Point p = e.getPointToMove(new Point(x,y));
		
		// change the coordinate of the hero.
		e.changeCurrentPosition(p);
		
		action.setPoint(new PointId(0, p.getX(), p.getY()));
	}

	private boolean enemyIsCloseToHero(){
		
		// for each enemy
		for(int i=0; i< GameActions.getEnemies().size(); ++i){
			Enemy e = GameActions.getEnemies().get(i);
			if(!e.isEliminated()){
				if(myHero.getDistance(e) < Math.pow(myHero.maxClosestEnemy,2))
					return true;
			}
		}
		
		return false;
	}
	
	public void shootWolff(Command action){
		Hero e = myHero;
		
		Enemy en = ((Enemy)action.getPointTarget());
		
		// move the hero to the target coordinate
		double damage = e.shoot(en);
		
		// decrease the damage
		en.decreaseDamage(damage);
		
		// remove enemies with zero life
		if(en.getLife() <= 0){
			enemies.eliminate(en);
			++score;
			if(GameActions.SIMULATE==true)
				en.changeInitialToEliminate();
		}
	}


	public void defaultValues() {
		// set default values for the hero
    	myHero.setInitial();
    	
    	// set default values for the enemies
    	enemies = new BinarySearchTreeEnemy();
    	for(int i=0; i < GameActions.lEnemies.size(); ++i){
    		Enemy e = GameActions.lEnemies.get(i);
    		e.setDefault();
    		e.setInitialEliminated();
    		e.setInitial();
    		enemies.insert(e);
			if(!e.isEliminated())
				enemies.increaseLife(e.initialLife);
    	}
    	
    	// set default values for the data points
    	dataPoints = new BinarySearchTree();
    	for(int i=0; i < GameActions.lData.size(); ++i){
    		Data d = GameActions.lData.get(i);
    		d.setInitial();
    		d.setInitialEliminated();
    		dataPoints.insert(d);
    	}
	}

	public int lifeEnemies() {
		return enemies.lifeEnemies();
	}

	public boolean isDataPointEmpty() {
		return dataPoints.isEmpty();
	}


	public boolean isEnemyEmpty() {
		return enemies.isEmpty();
	}
}
