package topcoder.sort;

import java.util.Arrays;

/**
 * @author zenshi
 * Sort algorithm
 * https://community.topcoder.com/stat?c=problem_statement&pm=12437
 */
public class FoxAndMp3Easy {


    public static void main(String[] args){
    	String[] elements = playList(109);
    	for(String s: elements)
    		System.out.print(s+" ");
    }
    
    /**
     * implementing with insertion sort algorithm
     * @param n will be between 1 and 1,000, inclusive.
     * @return
     */
    public static String[] playList(int n) {
    	
    	String[] copy = new String[n];
    	for(int i = 1; i <=n; i++){
    		copy[i-1] = i+".mp3";
    	}
    	
    	Arrays.sort(copy);

    	/**
    	 * You are given the int n. 
    	 * If n is at most 50, return a String[] containing 
    	 * the entire sorted list of file names. If n is more than 50, 
    	 * return a String[] containing the first 50 elements of 
    	 * the sorted list of file names.
    	 * **/
    	String[] elements = new String[Math.min(n,50)];
    	
    	for(int i = 0; i < elements.length;++i){
    		elements[i] = copy[i];
    	}
    	
    	return elements;
    }

}

