package interview.openbet;

import java.io.FileNotFoundException;


public class Solution3 {

    
    public static void main(String[] args) throws FileNotFoundException {
        
    	//File file = new File("./TEST/interview/Openbet/input-test1-1.txt");
    	//Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);
    	String input = "Pretpromneb oe efl tvno";
    	String input2 = "efc efrac yb";
     	
    	int min = Math.min(input.length(), input2.length());
        
    	StringBuilder f = new StringBuilder();
        
    	for(int i=0; i <min;i++){
    		f.append(input.charAt(i));
    		f.append(input2.charAt(i));
    	}
    	
    	if(input.length()>min)
    		f.append(input.substring(min));
    	else
    		f.append(input2.substring(min));
    	
        System.out.println(f.toString());
        
    }
    
}

