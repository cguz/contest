package contests.WeekOfCode25.BabyStepGiantStep;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Solution {
    
    public static void main(String[] args) throws FileNotFoundException {

    	File fileInput = new File("./TEST/contests/WeekOfCode25/BabyStepGiantStep/input/input00.txt");
    	Scanner in = new Scanner(fileInput);
    	//Scanner in = new Scanner(System.in);
    	
    	String[] line;
    	
    	int q = in.nextInt();
    	int a, b, d;

		in.nextLine();
    	while(q > 0){
    		a = in.nextInt();
    		b = in.nextInt();
    		d = in.nextInt();
    		
        	System.out.println(getMinimumSteps(a,b,d));
        	
    		--q;
    	}
    	
    }
    
    
    public static int getMinimumSteps(int a, int b, int d){
    	
    	if(a == d || b == d)
    		return 1;
    	
    	if(d == 0)
    		return 0;
    	
    	int steps = greedyMaxMinAlgorithm(a,b,d);
    	
    	if(steps != 0)
    		return steps;
    	
    	return 2;	
    }


	private static int greedyMaxMinAlgorithm(int a, int b, int d) {

		int coin = Math.max(a, b);
		int value = d;
		int count = 0;
    	if(coin+coin > value && coin+value > coin)
    		return count+2;
    	
		while(value >= coin){
			count++;
			value-=coin;
	    	if(coin+coin > value && coin+value > coin)
	    		return count+2;
		}
		coin = Math.min(a, b);
    	if(coin+coin > value && coin+value > coin)
    		return count+2;
		while(value >= coin){
			count++;
			value-=coin;
	    	if(coin+coin > value && coin+value > coin)
	    		return count+2;
		}
		
    	if(value == 0)
    		return count;
    	
		return 0;
	}

	private static int greedyMinMaxAlgorithm(int a, int b, int d) {

		int coin = Math.min(a, b);
		int value = d;
		int count = 0;
		while(value >= coin){
			count++;
			value-=coin;
		}
		coin = Math.max(a, b);
		while(value >= coin){
			count++;
			value-=coin;
		}
		
    	if(value == 0)
    		return count;
    	
		return 0;
	}

	private static int getMCD(int min) {
		int index = 1;
		if(min == 0)
			return index;
		
		// we verifiy the first condition of the problem statement
		for(index=2; index < min; ++index){
			if(min % index == 0){
				break;
			}
		}
		if(index != min)
			return index;
		return 1;
	}
}