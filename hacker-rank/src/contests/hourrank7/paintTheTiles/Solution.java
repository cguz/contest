package contests.hourrank7.paintTheTiles;
import java.util.Scanner;

public class Solution {
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        String N;
        int a =  in.nextInt();
        in.nextLine();
        N = in.nextLine();
        
        int count = 1;
        char cp = N.charAt(0);
        for(int i = 1; i < N.length(); ++i){
        	if(cp != N.charAt(i)){
        		cp = N.charAt(i);
        		count++;
        	}
        }
        
        System.out.println(count);
    }
}