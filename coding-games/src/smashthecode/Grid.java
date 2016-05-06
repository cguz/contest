package smashthecode;

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
				total_score += columns_score[i] = score(i) + score_right(i);
			}
		}
		
		return total_score;
		
	}
	
	
	// count the score of the grid
	public int score(int i){
		if(columns[i].next_up == null){
			return score_down(i);
		}else{
			if(columns[i].next_down == null)
				return score_up(i);
		}
		return 0;
	}
	

	public int score_down(int i){
		Block top = columns[i];
		Block n = columns[i];
		Block cur = columns[i];
		int count = 0;
		int score = 0;
		
		while(cur.next_down != null){
			if(cur.key == n.key){
				count++;
			}else{
				if(count >= CONFIG.SCORE_MIN){
					score+=count;
					top = cur = remove_down(cur, n, top);
					change_values(i, top);
				}
				n = cur;
				count = 1;
			}
			cur = cur.next_down;
		}
		
		if(cur.key == n.key){
			count++;
		}
				
		if(count >= CONFIG.SCORE_MIN){
			score+=count;
			if(cur.key == n.key){
				top = null;
			}else{
				top = remove_down(cur, n, top);
			}
			change_values(cur, top, count, i);
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

	public int score_up(int i){
		Block top = columns[i];
		Block n = columns[i];
		Block cur = columns[i];
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
					change_values(i,top);
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
			change_values(cur, top, count,i);
		}
		
		return score;
	}

	
	public void change_values(int i, Block c){
		
		if(c == null){
			columns[i].key='.';
			columns[i].next_down = null;
			columns[i].next_up = null;
			columns[i].next_left = null;
			columns[i].next_right = null;
		}else{
			columns[i].key=c.key;
			columns[i].next_down = c.next_down;
			columns[i].next_up = c.next_up;
			columns[i].next_left = c.next_left;
			columns[i].next_right = c.next_right;
		}
	}
	
	
	public void change_values(Block c, Block top, int count, int i){
			
		Block n = c;
		
		while(count > 0){
			n = n.next_down;
			count--;
		}
		
		if(n == null){
			columns[i].key='.';
			columns[i].next_down = null;
			columns[i].next_up = null;
			columns[i].next_left = null;
			columns[i].next_right = null;
		}else{
			n.next_up = top;
			if(top != null)
				top.next_down = c;
		}
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
	

	public int score_right(int i){
		Block n = columns[i];
		Block cur = columns[i];
		int count = 0;
		int score = 0;
		
		while(cur.next_right != null){
			if(cur.key == n.key){
				count++;
			}else{
				if(count >= CONFIG.SCORE_MIN){
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
			}
			i++;
			cur = cur.next_right;
		}
		
		if(cur.key == n.key){
			count++;
		}
		
		if(count >= CONFIG.SCORE_MIN){
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

	public static void main(String[] args){
    	
    	Grid game = new Grid();
    	
    	Block col = new Block('1');
    	col.appendTail('1');
    	col.appendTail('1');
    	col.appendTail('1');
    	col.appendTail('1');
    	
    	Block col1 = new Block('2');
    	col1.appendTail('1');
    	col1.appendTail('1');
    	col1.appendTail('1');
    	col1.appendTail('1');
    	col1.appendTail('2');
    	col1.appendTail('2');
    	col1.appendTail('2');
    	
    	game.columns[0] = col;
    	game.columns[1] = col1;
    	
    	Grid game1 = new Grid(game);
    	
    	for(Block b: game.columns)
    		if(b != null)
    			System.out.println(b.toString());
    		else 
    			System.out.println(".");
    	
		System.out.println("----");
    	System.out.println(game.columns[0].score());
		
    	for(Block b: game.columns)
    		if(b != null)
    			System.out.println(b.toString());
    		else 
    			System.out.println(".");
    	

		System.out.println("----");
    	System.out.println(game.columns[1].score());
    	for(Block b: game.columns)
    		if(b != null)
    			System.out.println(b.toString());
    		else 
    			System.out.println(".");
    	

		if(game1!=null){
			System.out.println("----");
	    	for(Block b: game1.columns)
	    		if(b != null)
	    			System.out.println(b.toString());
	    		else 
	    			System.out.println(".");
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