package topcoder.sort;

/**
 * @author zenshi
 * insertion sort algorithm
 * https://community.topcoder.com/stat?c=problem_statement&pm=11278
 */
public class UnsortedSequence {

    public static void main(String[] args){
    	int[] s = getUnsorted(new int[]{2,8,5,1,10,5,9,9,3,10,5,6,6,2,8,2,10});
    	for(int i : s)
    		System.out.print(i+" ");
    }
    
    /**
     * first we sort the elements with the inserstion sort algorithm
     * time complexity
     * best: O(n)+1 = O(n)
     * worst/average case: O(n^2)+(n-1) = O(n^2)
     * @param s
     * @return
     */
    public static int[] getUnsorted(int[] s){
    	
    	if(s.length == 1)
    		return new int[0];
    	

		/** 
		 * we sort the elements with the insertion sort algorithm
		 * we take the current element and insert it at the appropiated position of the list,
		 * adjusting the list
		 * note that we can sort the elements with the function: Arrays.sort(s)
		 * **/
    	for(int i = 1; i < s.length; i++){
    		
    		int cur = s[i];
    		int j = i-1;
    		
    		while(j >= 0 && s[j] > cur){
    			s[j+1] = s[j];
    			j-=1;
    		}
    		s[j+1]=cur;
    		
    	}
    	
    	/** if they are the same **/
    	if(s[0] == s[s.length-1])
    		return new int[0];
    	
    	
    	int size = s.length-1;
    	int last = s[size];

    	while(true){
    		if(last != s[size]){
    			int tmp = s[size];
    			s[size] = s[size+1];
    			s[size+1] = tmp;
    			break;
    		}
    		size--;
    	}
    	
    	return s;
    	
    }

}