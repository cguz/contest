package topcoder.string;
import java.util.HashMap;
import java.util.Map;

public class SRM197QuickSums {

    public SRM197QuickSums() {}


    public static void main(String[] args){
    	System.out.println(minSums("99999", 100));
    }
    
    // dynamic programming
    public static int minSums(String num, int sum) {
    	
        int MAX = 100;
        Map<String, int[]> table = new HashMap<String, int[]>();
        
        // to control the total number of elements to select
        for (int l = 1; l <= num.length(); l++) {
        	
        	// for each element in the string considering the total of elements to select
            for (int i = 0; i + l <= num.length(); i++) {
            	
            	// we select the string
            	String c = num.substring(i, i + l);
                
            	// we get the number in a long variable
                long ci = Long.parseLong(c);
                
                
                int[] col = new int[sum+1];
                for (int cs = 0; cs <= sum; cs++) {
                	
                    col[cs] = MAX;
                    if (cs == ci) col[cs] = 0;
                    else {
                        int min = MAX;
                        for (int k = 1; k < c.length(); k++) {
                            long rest = cs - Long.parseLong(c.substring(0, k));
                            if (0 <= rest && rest <= cs) {
                                int csteps = table.get(c.substring(k))[(int)rest];
                                if (min > csteps) min = csteps;
                            }
                        }
                        col[cs] = 1 + min;
                    }
                }
                table.put(c, col);
            }
        }
        
        int re = table.get(num)[sum];
        return re >= MAX ? -1 : re;
    }

}