package smashthecode;

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
