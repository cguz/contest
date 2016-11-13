package algorithms.Sorting.QuickSort2Sorting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    
      
    public static void main(String[] args) throws FileNotFoundException {
    	File fileInput = new File("./TEST/algorithms/Sorting/QuickSort2Sorting/input/input00.txt");
		File fileOutput = new File("./TEST/algorithms/Sorting/QuickSort2Sorting/output/output00.txt");
		Scanner in = new Scanner(fileInput);
		// Scanner in = new Scanner(System.in);

		
    	int N = in.nextInt();
       
    	Integer[] ar = new Integer[N];
    	for(int i=0;i<N;i++){
            ar[i]=in.nextInt(); 
    	}
       
    	quickSort(ar);
                    
    }
    
    private static List<Integer> quickSort(Integer a[]){
    	int i = 1;
    	int pivot = a[0];
    	Integer[] ar;
    	
    	List<Integer> sorted = new ArrayList<Integer>();
    	List<Integer> left = new ArrayList<Integer>();
    	while(i < a.length){
    		while(i < a.length && a[i] > pivot) i++;
    		
    		if(i < a.length){
    			left.add(a[i]);
    			i++;
    		}
    	}
    	
		List<Integer> right = new ArrayList<Integer>();
		i = 1;
    	while(i < a.length){
    		while(i < a.length && a[i] < pivot) i++;
    		
    		if(i < a.length){
    			right.add(a[i]);
    			i++;
    		}
    	}
		
    	// recursion
    	
    	if(left.size() > 0) {
    		ar = new Integer[left.size()];
    		ar = left.toArray(ar);
    		left = quickSort(ar);
        	
    	}

		if(right.size() > 0) {
    		ar = new Integer[right.size()];
    		ar = right.toArray(ar);

    		right = quickSort(ar);
    	}

		sorted.addAll(left);
		sorted.add(pivot);
		sorted.addAll(right);
	
		if(sorted.size()>1){
			printArray(sorted);
			System.out.println("");
		}

	    return sorted;
    }
    
    private static void printArray(List<Integer> left) {
      for(int n=0; n < left.size(); n++){
         System.out.print(left.get(n)+" ");
      }
   }
    
    
    /**
     * private static void quickSort(int a[], int l, int r){
    	int i = l;
    	int j = r;
    	int tmp;
    	int pivot = a[i];
    	
    	boolean swap = false;
    	while(i <= j){
    		while(a[i] < pivot) i++;
    		while(a[j] > pivot) j--;
    		
    		if(i <= j){
    			tmp  = a[i];
    			a[i] = a[j];
    			a[j] = tmp;
    			i++;
    			j--;
    			swap = true;
    		}
    	}
    	
    	// recursion
    	if(l < j) {
    		quickSort(a, l, j);
    		printArray(Arrays.copyOfRange(a, l, i));
    	}
    	if(r > i) {
    		quickSort(a, i, r);
    		printArray(Arrays.copyOfRange(a, l, r));
    	}

    	// printArray(Arrays.copyOfRange(a, i, r));
    }
     */
}
