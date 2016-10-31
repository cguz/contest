package contests.WeekOfCode25.BetweenTwoSets;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    
	public static boolean verifyValue = true;
	
    public static void main(String[] args) throws FileNotFoundException {

    	File fileOutput = null;
    	File fileInput = new File("./TEST/contests/WeekOfCode25/between-two-sets-testcases/input/input00.txt");
    	fileOutput = new File("./TEST/contests/WeekOfCode25/between-two-sets-testcases/output/output00.txt");
    	Scanner in = new Scanner(fileInput);
    	//Scanner in = new Scanner(System.in);
    	
    	String line;
    	
    	List<Integer> A=null;
    	List<Integer> B=null;
    	
    	int N = in.nextInt();
    	int M = in.nextInt();
    	
    	in.nextLine();
    	line = in.nextLine();
    	A = toArrayListInt(line, N);
    	line = in.nextLine();
    	B = toArrayListInt(line, M);
    	
    	Collections.sort(A);
    	Collections.sort(B);
        
    	int total = totalIntegersBetween(A,B);
    	
    	if(verifyValue){
	    	in = new Scanner(fileOutput);
	    	line = in.nextLine();
	    	if(total == Integer.parseInt(line))
	    		System.out.println("[CORRECT] "+total);
	    	else  
	    		System.out.println("[WRONG] my value:"+total+", expected value: "+Integer.parseInt(line));
    	}else{
    		System.out.println(total);
    	}
    }
    
    public static List<Integer> toArrayListInt(String temp, int N){

    	ArrayList<Integer> A = new ArrayList<Integer>(N);
    	for (String s: temp.split(" "))
    	    A.add(Integer.parseInt(s));
    	
    	return A;
    	
    }
    
    public static int totalIntegersBetween(List<Integer> A, List<Integer> B){
    	
    	int count = 0;
    	int upperLimit = B.get(0);
    	int lowerLimit = A.get(A.size()-1);
    	boolean factor = false;
    	int index;
    	
    	for(int x = lowerLimit; x <= upperLimit; ++x){
    		factor = true;
    		// we verifiy the first condition of the problem statement
    		for(index=0; index < A.size(); ++index){
    			if(x % A.get(index)!=0){
    				factor = false;
    				break;
    			}
    		}
    		// if the second condition is not valid, we iterate to the next element
    		if(!factor)
    			continue;
    		// we verify the second condition of the problem statement
    		for(index = 0; index < B.size();++index){
    			if(B.get(index)%x != 0){
    				factor = false;
    				break;
    			}
    		}
    		if(factor)
    			count++;
    	}
    	
    	return count;
    	
    }
    	
}
