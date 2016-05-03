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
					total_score += columns_score[i] = columns[i].score();
				}
			}
			
			return total_score;
			
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
			
			while(cur.next_down != null){
				if(cur.key == n.key){
					count++;
				}else{
					if(count > CONFIG.SCORE_MIN){
						score+=count;
						top = cur = remove_down(cur, n, top);
						change_values(top);
					}
					n = cur;
					count = 1;
				}
				cur = cur.next_down;
			}
			
			if(cur.key == n.key)
				count++;
			
			if(count > CONFIG.SCORE_MIN){
				score+=count;
				if(cur.key == n.key){
					top = null;
				}else{
					top = remove_down(cur, n, top);
				}
				change_values(top);
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
			
			while(cur.next_up != null){
				if(cur.key == n.key){
					count++;
				}else{
					if(count > CONFIG.SCORE_MIN){
						score+=count;
						top = cur = remove_up(cur,n,top);
						change_values(top);
					}
					n = cur;
					count = 1;
				}
				cur = cur.next_up;
			}
			
			if(cur.key == n.key)
				count++;
			
			if(count > CONFIG.SCORE_MIN){
				score+=count;
				if(cur.key == n.key){
					top = null;
				}else{
					top = remove_up(cur,n,top);
				}
				change_values(top);
			}
			
			return score;
		}

		// remove line
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

		@Override
		public String toString() {
			return key+" "+((next_up!=null)?next_up.toString():"");
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
        	blocks = new Block[8];
            for (int i = 0; i < 8; i++) {
            	blocks[i] = player.new Block(Integer.toString(in.nextInt()).charAt(0)); // color of the first block
            	blocks[i].appendTail(Integer.toString(in.nextInt()).charAt(0));
            }
            
            for (int i = 0; i < 12; i++) {
                String row = in.next(); // One line of the map ('.' = empty, '0' = skull block, '1' to '5' = colored block)

                System.err.println("row: "+row);
                for(int j=0;j<row.length();++j){
                	char c = row.charAt(j);
                	if(c != '.'){
                		if(game.columns[j] == null)
                			game.columns[j] = player.new Block(c);
                		else
                			game.columns[j].appendTail(c);
                	}
                }   
            }
            for (int i = 0; i < 12; i++) {
                String row = in.next();
            }

            System.err.println("Debug messages...\n"+game.toString()+"\n"+blocks[0].toString());
            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            m = player.new ManagerGrid(game);
            
            System.out.println(m.dropBasic(blocks[0])); // "x": the column in which to drop your blocks
        }
    }

	
}