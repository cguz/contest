package algorithms.dynamicProgramming.fibonacciModified;
import java.util.Scanner;

public class Solution {
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
    	int a =  in.nextInt();
        
        while(a < 0){
	        in.nextInt(); 
	        String[] N =  in.nextLine().split(" ");
	        System.out.println(binarySearchSol(N));
        }
    }

	private static int binarySearchSol(String[] n) {
		
		int l = 0;
		int h = n.length;
		
		int sol = 0;
		while(l < h){
			
			boolean found = false;
			
			for(int i = l+1; i < h; ++i){
				int sumL = sum(n, l, i);
				int sumR = sum(n, i, h);
				
				if(sumL == sumR){
					sol++;
					if(Math.abs(l-i) > Math.abs(i-h)){
						h = i;
					}else{
						l = i;
					}
					found = true;
					break;
				}
			}
			if(!found)
				l = h;
		}
		
		return sol;
	}

	private static int sum(String[] n, int from, int to) {
		
		int sum = 0;
		
		for(int i = from; i < to; i++){
			sum += Integer.parseInt(n[i]);
		}
		
		return sum;
	}
}
