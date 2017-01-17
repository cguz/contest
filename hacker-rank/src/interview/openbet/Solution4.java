

package interview.openbet;

import java.io.FileNotFoundException;


public class Solution4 {

    
    public static void main(String[] args) throws FileNotFoundException {
        
    	
    	//File file = new File("./TEST/interview/Openbet/input-test1-1.txt");
    	//Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);
    	String input = "1 2 3 4 5 6 7 8 9";
    	String[] temp = input.split(" ");
        int length = temp.length;
        
        int i;
        for(i=2; i<length;++i){
            if(length % i == 0)
                break;
        }
        
        if(i == length)
        	System.out.println("-");
        else{
	        for(int j=0; j <length; j++){
	            System.out.println(getElementAt(temp, j, i));
	        }
        }
        
    }
    
    static String getElementAt(String[] arr, int index, int col)
    {
    	if(index < col){
    		return arr[index];
    	}else{
    		
    		index = index + col;
    	    index = index % arr.length; // In case the previous step made index >= n
    	    
            return arr[index];
    		
    	}
    }
}
