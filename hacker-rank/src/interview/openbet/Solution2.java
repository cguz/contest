package interview.openbet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;


public class Solution2 {

    
    public static void main(String[] args) throws FileNotFoundException {
        
    	Solution2 sol = new Solution2();
    	
    	//File file = new File("./TEST/interview/Openbet/input-test1-1.txt");
    	//Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);
    	int[] input = new int[]{1,1,1};
     	

    	int sum = input[0];
        
        if(input.length != 1) {
        
            for(int i =1; i < input.length; i++){
                sum+= input[i];
            }
            
        }
        
        System.out.println(sum);
        
    }
    
}
