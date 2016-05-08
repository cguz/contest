package contest.smashthecode;

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