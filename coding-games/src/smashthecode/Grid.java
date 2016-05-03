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
				total_score += columns_score[i] = columns[i].score();
			}
		}
		
		return total_score;
		
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