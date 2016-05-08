package contest.smashthecode;

public class ManagerGrid {

	public Grid current;
	
	public ManagerGrid(Grid c) {
		current=c;
	}
	
	
	public int dropBasic(Block b){
		
		int[] score = new int[6];
		int max= 0;
		int pos_max_score = -1;
		
		int max_skull = 1000000;
		int pos_max_skull = -1;
		int pos_null = -1;
		
		for(int i =0; i < current.columns.length; i++){
			Grid temp = new Grid(current);
			
			temp.append(b, i);
			temp.link_horizontal();
			
			score[i] = temp.score(true);
			if(score[i] >= max){
				max = score[i];
				pos_max_score = i;
			}
			
			if(current.columns[i] != null){
				if(current.columns[i].key == '0'){
					if(current.columns[i].total < max_skull){
						max_skull = current.columns[i].total;
						pos_max_skull = i;
					}
				}
			}
			if(current.columns[i] == null){
				pos_null = i;
			}
		}
		
		if(max == 0){
			if(pos_max_skull != -1)
				pos_max_score = pos_max_skull;
			else
				if(pos_null != -1)
					pos_max_score = pos_null;
		}		
		
		return pos_max_score;
		
	}
	

	public static void main(String[] args){
    	
    	Grid game = new Grid();
    	
    	Block col = null;

    	Block col1 = null;

    	Block col2 = new Block('0');
    	col2.appendTail('1');
    	col2.appendTail('1');
    	
    	Block col3 = null;
    	
    	Block col4 = new Block('5');
    	col4.appendTail('5');
    	
    	Block col5 = new Block('4');
    	col5.appendTail('4');
    	col5.appendTail('0');
    	col5.appendTail('4');
    	col5.appendTail('4');
    	
    	game.columns[0] = col;
    	game.columns[1] = col1;
    	game.columns[2] = col2;
    	game.columns[3] = col3;
    	game.columns[4] = col4;
    	game.columns[5] = col5;
    	
    	game.link_horizontal();
    	
    	ManagerGrid m = new ManagerGrid(game);
    	
    	Block c1 = new Block('2');
    	c1.appendTail('2');
    	
    	
    	System.out.println(m.current);
    	int pos = m.dropBasic(c1);
    	System.out.println("pos: "+pos+"\n");
    	game.append(c1, pos);
    	game.score(true);
    	System.out.println(m.current);
    	
    	/*c1 = new Block('1');
    	c1.appendTail('1');
    	pos = m.dropBasic(c1);
    	System.out.println(pos);
    	game.append(c1, pos);
    	System.out.println(m.current);
    	
    	c1 = new Block('3');
    	c1.appendTail('3');
    	pos = m.dropBasic(c1);
    	System.out.println(pos);
    	game.append(c1, pos);
    	System.out.println(m.current);
    	

    	c1 = new Block('1');
    	c1.appendTail('1');
    	pos = m.dropBasic(c1);
    	System.out.println(pos);
    	game.append(c1, pos);
    	System.out.println(m.current);*/
    	
    }
    
}
