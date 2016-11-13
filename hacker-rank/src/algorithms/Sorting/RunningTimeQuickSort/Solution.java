package algorithms.Sorting.RunningTimeQuickSort;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        File fileInput = new File("./TEST/algorithms/Sorting/RunningTimeAlgorithms/input/input02.txt");
		File fileOutput = new File("./TEST/algorithms/Sorting/RunningTimeAlgorithms/output/output02.txt");
		Scanner in = new Scanner(fileInput);
		//Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int[] a = new int[N];
        for(int i=0; i < N; i++){
            a[i] = in.nextInt();
        }
        
        int[] c = new int[N];
        c = Arrays.copyOf(a, a.length);
        int c1 = numberShiftInsertionSort(a);
        
        int c2 = shiftQuickSort(c);
        
        p((c1-c2)+"");
    }
    
    private static int shiftQuickSort(int[] a){
        return quickSort(a, 0, a.length-1);
    }
    
    private static int quickSort(int[] ar, int l, int h){
        int shift = 0;
        if(l < h){
            int[] pivotIndex = partition(ar, l, h);
            shift+=pivotIndex[1];
            shift+=quickSort(ar, l, pivotIndex[0]-1);
            shift+=quickSort(ar, pivotIndex[0]+1, h);
        }
        return shift;
    }
    
    private static int[] partition(int[] ar, int l, int h){
        int pivot = ar[h];
        int i = l-1;
        int tmp;
        int count = 0;
        for(int j=l; j <= h;++j){
            if(ar[j] < pivot){
                i++;
                tmp = ar[j];
                ar[j] = ar[i];
                ar[i] = tmp;
                count++;
            }
        }
        i++;
        tmp = ar[i];
        ar[i] = ar[h];
        ar[h] = tmp;
        count++;
        int[] n = new int[2];
        n[0] = i;
        n[1] = count;
        return n;
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
    
    private static int numberShiftInsertionSort(int[] a){
        int numberShift = 0;
        
        int temp;
        int j;
        for(int i=1; i < a.length;++i){
            temp = a[i];
            j = i-1;
            while(j>=0 && a[j]>temp){
                a[j+1] = a[j];
                j--;
                numberShift++;
            }
            a[j+1] = temp;
        }
        return numberShift;
    }
}