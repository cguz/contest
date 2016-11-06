package algorithms.Sorting.RunningTimeAlgorithms;

import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

		//File fileInput = new File("./TEST/algorithms/Sorting/RunningTimeAlgorithms/input/input02.txt");
		//fileOutput = new File("./TEST/algorithms/Sorting/RunningTimeAlgorithms/output/output02.txt");
		//Scanner in = new Scanner(fileInput);
		Scanner in = new Scanner(System.in);

        int N = in.nextInt();
        int[] a = new int[N];
        for(int i=0; i < N; i++){
            a[i] = in.nextInt();
        }
        
        System.out.println(numberShiftInsertionSort(a));
        
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