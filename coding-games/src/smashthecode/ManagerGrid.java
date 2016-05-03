package smashthecode;

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
	

	public static void main(String[] args){
    	
    	Grid game = new Grid();
    	
    	Block col = new Block('2');
    	col.appendTail('2');
    	col.appendTail('0');
    	col.appendTail('1');
    	col.appendTail('1');

    	Block col1 = null;

    	Block col3 = new Block('2');
    	col3.appendTail('2');
    	col3.appendTail('0');
    	
    	Block col4 = new Block('0');
    	
    	Block col5 = new Block('5');
    	col5.appendTail('5');
    	col5.appendTail('0');
    	
    	Block col6 = new Block('0');
    	
    	game.columns[0] = col;
    	game.columns[1] = col1;
    	game.columns[2] = col3;
    	game.columns[3] = col4;
    	game.columns[4] = col5;
    	game.columns[5] = col6;
    	
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
