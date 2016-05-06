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

	public void append(Block block) {
		this.next_up = block;
		block.next_down = this;
		total++;
	}
	
	/* 
	 * lo he sacado de aqui, lo utilizo en grid
	 * */
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
