package algorithms.Sorting.QuickSortInPlace;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
    
      
    public static void main(String[] args) throws FileNotFoundException {
    	File fileInput = new File("./TEST/algorithms/Sorting/QuickSortInPlace/input/input00.txt");
		File fileOutput = new File("./TEST/algorithms/Sorting/QuickSortInPlace/output/output00.txt");
		Scanner in = new Scanner(fileInput);
		// Scanner in = new Scanner(System.in);

		int n = in.nextInt();
        int[] ar = new int[n];
        for(int i=0; i < n; ++i)
            ar[i] = in.nextInt();
        
        quickSort(ar, 0, n-1);
    }
    
    private static void quickSort(int[] ar, int l, int h){
        if(l < h){
            int pivotIndex = partition(ar, l, h);
            pa(ar);
            quickSort(ar, l, pivotIndex-1);
            quickSort(ar, pivotIndex+1, h);
        }
    }
    
    private static int partition(int[] ar, int l, int h){
        int pivot = ar[h];
        int i = l-1;
        int tmp;
        for(int j=l; j <= h;++j){
            if(ar[j] < pivot){
                i++;
                tmp = ar[j];
                ar[j] = ar[i];
                ar[i] = tmp;
            }
        }
        i++;
        tmp = ar[i];
        ar[i] = ar[h];
        ar[h] = tmp;
        return i;
    }
    
    private static void pa(int[] ar){
        for(int i:ar)
            p(i+" ");
        pln("");
    }
    
    private static void p(String s){
        System.out.print(s);
    }
    
    private static void pln(String s){
        System.out.println(s);
    }
}
