package tp;
import java.util.Scanner;


/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

	

	public class CONFIG {
	
		public static final int SCORE_MIN = 3;
	
	}
	

	public class ManagerGrid {

		public Grid current;
		
		public ManagerGrid(Grid c) {
			current=c;
		}
		
		
		public int dropBasic(Block b){
			
			int[] score = new int[6];
			int max= 0;
			int pos_max_score = -1;
			for(int i =0; i < current.columns.length; i++){
				Grid temp = new Grid(current);
				
				temp.append(b, i);
				temp.link_horizontal();
				
				score[i] = temp.score(true);
				if(score[i] >= max){
					max = score[i];
					pos_max_score = i;
				}	
			}
			
			if(max == 0){
				max = 1000000;
				for(int i=0; i < current.columns.length;++i){
					if(current.columns[i] == null){
						pos_max_score = i;
						break;
					}
					if(current.columns[i].total < max){
						max = current.columns[i].total;
						pos_max_score = i;
					}
				}
			}		
			
			return pos_max_score;
			
		}
		

	    
	}

	
	

	public class Block {
		
		
		public char		key;
		public Block 	next_up;
		public Block 	next_down;
		public Block 	next_left;
		public Block 	next_right;
		public int 		total;
		
		public Block(char key){
			this.key = key;
			next_up = null;
			next_down = null;
			next_left = null;
			next_right = null;
			total = 1;
		}
		
		public Block(Block block) {
			key = block.key;
			next_up = null;
			next_down = null;
			next_left = null;
			next_right = null;
			total = block.total;
			
			Block n = block.next_up;
			Block cur = this;
			while(n!=null){			
				cur.next_up = new Block(n.key);
				cur.next_up.next_down = cur;
				cur = cur.next_up;
				n = n.next_up;
			}
			
		}

		public void appendTail(char key){
			Block end = new Block(key);
			Block n = this;
			
			while(n.next_up != null){
				n = n.next_up;
			}
			n.next_up = end;
			end.next_down = n;
			total++;
		}

		public void append(Block block) {
			this.next_up = block;
			block.next_down = this;
			total++;
		}
		
		// count the score of the grid
		public int score(){
			if(next_up == null){
				return score_down();
			}else{
				if(next_down == null)
					return score_up();
			}
			return 0;
		}
		
		public int score_down(){
			Block top = this;
			Block n = this;
			Block cur = this;
			int count = 0;
			int score = 0;
			
			int max_skull = 0;
			while(cur.next_down != null){
				if(cur.key == n.key || cur.key == '0'){
					count++;
					if(cur.key == '0')
						max_skull++;
				}else{
					if(count >= (CONFIG.SCORE_MIN + max_skull)){
						score+=count;
						top = cur = remove_down(cur, n, top);
						change_values(top);
					}
					n = cur;
					count = 1;
					max_skull = 0;
				}
				cur = cur.next_down;
			}
			
			if(cur.key == n.key || cur.key == '0'){
				count++;
				if(cur.key == '0')
					max_skull++;
			}
					
			if(count >= (CONFIG.SCORE_MIN + max_skull)){
				score+=count;
				if(cur.key == n.key){
					top = null;
				}else{
					top = remove_down(cur, n, top);
				}
				change_values(cur, top, count);
			}
			
			return score;
		}

		// remove line
		private Block remove_down(Block cur, Block n, Block top) {
			cur.next_up = n.next_up; 
			if(cur.next_up != null){
				cur.next_up.next_down = cur;
				cur = top;
			}else{
				top = cur;
			}
			return top;
		}

		public int score_up(){
			Block top = this;
			Block n = this;
			Block cur = this;
			int count = 0;
			int score = 0;
			
			int max_skull = 0;
			while(cur.next_up != null){
				if(cur.key == n.key || cur.key == '0'){
					count++;
					if(cur.key == '0')
						max_skull++;
				}else{
					if(count >= (CONFIG.SCORE_MIN + max_skull)){
						score+=count;
						top = cur = remove_up(cur,n,top);
						change_values(top);
					}
					n = cur;
					count = 1;
					max_skull = 0;
				}
				cur = cur.next_up;
			}
			
			if(cur.key == n.key || cur.key == '0'){
				count++;
				if(cur.key == '0')
					max_skull++;
			}
			
			if(count >= (CONFIG.SCORE_MIN + max_skull)){
				score+=count;
				if(cur.key == n.key){
					top = null;
				}else{
					top = remove_up(cur,n,top);
				}
				change_values(cur, top, count);
			}
			
			return score;
		}
		
		

		// remove up line 
		private Block remove_up(Block cur, Block n, Block top) {
			cur.next_down = n.next_down; 
			if(cur.next_down != null){
				cur.next_down.next_up = cur;
				cur = top;
			}else{
				top = cur;
			}
			return top;
		}
		

		public void change_values(Block c, Block top, int count){
			
			Block n = c;
			
			while(count > 0){
				n = n.next_down;
				count--;
			}
			
			if(n == null){
				this.key='.';
				this.next_down = null;
				this.next_up = null;
				this.next_left = null;
				this.next_right = null;
			}else{
				n.next_up = top;
				if(top != null)
					top.next_down = c;
			}
		}
		
		public void change_values(Block c){
			
			if(c == null){
				this.key='.';
				this.next_down = null;
				this.next_up = null;
				this.next_left = null;
				this.next_right = null;
			}else{
				this.key=c.key;
				this.next_down = c.next_down;
				this.next_up = c.next_up;
				this.next_left = c.next_left;
				this.next_right = c.next_right;
			}
		}
		
		public void link_horizontal(Block block) {
			
			int min = block.total;
			if(total < block.total)
				min = total;

			Block l = this;
			Block r = block;
			l.next_right = r;
			r.next_left = l;
			
			while(min > 1){
				
				r = r.next_up;
				l = l.next_up;
				
				l.next_right = r;
				r.next_left = l;
				
				--min;
			}
			
		}
		

		@Override
		public String toString() {
			return key+" "+((next_up!=null)?next_up.toString():"");
		}

		/*@Override
		protected Object clone() throws CloneNotSupportedException {
			try{
				Block block = (Block) super.clone();
				block.next_down = (Block) next_down.clone();
				block.next_left = (Block) next_left.clone();
				block.next_right = (Block) next_right.clone();
				block.next_up = (Block) next_up.clone();
				return block;
			}catch(CloneNotSupportedException e){
				throw new AssertionError();
			}
		}*/
	}


	/*
	* Grid.java
	* Models a grid
	*/

	public class Grid {
	    Block[] columns = new Block[6];
	    int[]   columns_score = new int[6];
	    private int total_score;
	    
	    private Grid[] childs = new Grid[6];
	    
	    
	    public Grid(){
	    	for(int i = 0; i < columns.length;++i){
	    		columns[i] = null;
	    		columns_score[i] = 0;
	    	}
	    }

		public Grid(Grid game) {

			for(int i=0; i< game.columns.length;++i){
				columns_score[i] = game.columns_score[i];
				if(game.columns[i]!=null){
					columns[i] = new Block(game.columns[i]);
				}
			}

		}
		
		public int score(boolean calculate){

			if(!calculate)
				return total_score;
			
			total_score = 0;

			for(int i =0; i < columns.length; i++){
				if(columns[i] != null){
					total_score += columns_score[i] = columns[i].score() + score_right(i);
				}
			}
			
			return total_score;
			
		}
		

		public int score_right(int i){
			Block n = columns[i];
			Block cur = columns[i];
			int count = 0;
			int score = 0;
			
			int max_skull = 0;
			while(cur.next_right != null){
				if(cur.key == n.key || cur.key == '0'){
					count++;
					if(cur.key == '0')
						max_skull++;
				}else{
					if(count >= (CONFIG.SCORE_MIN + max_skull)){
						score+=count;
						n = n.next_up;
						i = remove_right(i,n.next_down);
						while(columns[i]==null && i < columns.length){
							++i;
						}
						if(i == columns.length)
							break;
						cur = columns[i];
					}
					n = cur;
					count = 1;
					max_skull = 0;
				}
				i++;
				cur = cur.next_right;
			}
			
			if(cur.key == n.key || cur.key == '0'){
				count++;
				if(cur.key == '0')
					max_skull++;
			}
			
			if(count >= (CONFIG.SCORE_MIN + max_skull)){
				score+=count;
				remove_right(i,n.next_down);
			}
			
			return score;
		}
		

		// remove right
		private int remove_right(int i, Block n) {
			
			Block ini = columns[i];
			
			while(ini != n && --i != -1){
				ini = columns[i];
				if(ini.next_up != null){
					ini.next_up.next_down = null;
					columns[i] = ini.next_up;
				}else{
					ini.next_right.next_left = null;
					ini.next_left.next_right = null;
					columns[i] = null;
				}
			}
			return i;
		}

		public void link_horizontal() {
			for(int i =0; i < columns.length-1; i++){
				if(columns[i] != null && columns[i+1] != null){
					columns[i].link_horizontal(columns[i+1]);
				}
			}
		}

	    
	    @Override
	    public String toString(){
	    	
	    	String temp = "";
	    	for(int i = 0; i < columns.length;++i){
	    		if(columns[i]==null){
	    			temp += ".\n";
	    		}else
	    			temp += columns[i].toString()+"\n";
	    		
	    	}
	    	
	        return temp;
	    }

		public void append(Block b, int i) {
			Block n = b;

			if(columns[i] == null){
				columns[i] = new Block(n);
			}else{
				while(n!=null){
					columns[i].appendTail(n.key);
					n = n.next_up;
				}
			}
		}
	}
	
	
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
    	
        Player player = new Player();

    	Grid game = player.new Grid();
        
    	Block[] blocks = new Block[8];
    	
    	ManagerGrid m;
        
        // game loop
        while (true) {
        	blocks[0] = player.new Block('5');
        	blocks[0].appendTail('5');
        	/*blocks = new Block[8];
            for (int i = 0; i < 8; i++) {
            	blocks[i] = player.new Block(Integer.toString(in.nextInt()).charAt(0)); // color of the first block
            	blocks[i].appendTail(Integer.toString(in.nextInt()).charAt(0));
            }*/
            
            String[] row = new String[12];
            row[0] = "......";
            row[1] = "......";
            row[2] = "......";
            row[3] = "......";
            row[4] = "......";
            row[5] = "......";
            row[6] = "......";
            row[7] = "......";
            row[8] = "......";
            row[9] = "3.....";
            row[10] = "3.....";
            row[11] = "000000";
            
            game = player.new Grid();
            
            for (int i = 0; i < 12; i++) {
                // String row = in.next(); // One line of the map ('.' = empty, '0' = skull block, '1' to '5' = colored block)

                System.err.println("row: "+row[i]);
                for(int j=0;j<row[i].length();++j){
                	char c = row[i].charAt(j);
                	if(c != '.'){
                		
                		Block t1 = player.new Block(c);
                		
                		if(game.columns[j] != null)
                			t1.append(game.columns[j]);
                		
            			game.columns[j] = t1;
                	}
                }  
            }
            
            /*
            for (int i = 0; i < 12; i++) {
                // String row = in.next();
                in.next();
            }*/

            System.err.println("Debug messages...\n"+game.toString()+"\n"); // +blocks[0].toString());
            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            m = player.new ManagerGrid(game);
            
            System.out.println(m.dropBasic(blocks[0])); // "x": the column in which to drop your blocks
        }
    }

	
}