package algorithms.warmup;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Solution_Circular_Array_Rotation {

	public static Scanner in;
    
	static Solution_Circular_Array_Rotation sol = new Solution_Circular_Array_Rotation();
    
    public static void main(String[] args) {
    	
    	try {
        	File file = new File("./TEST/algorithms/warmup/input-test-circular-array.txt");
        	loadFile(file);
        	// loadFile(null);
    		
        	readFile();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
    static void loadFile(File file) throws FileNotFoundException{

    	in = new Scanner(file);
    
    }
    
    static void readFile() {
    	
     	String[] temp;
 		int i;
 		
 		// creating the set of nodes of the undirected graph
 		temp = in.nextLine().split(" ");
 		int N = Integer.parseInt(temp[0]);
 		int K = Integer.parseInt(temp[1]);
 		int Q = Integer.parseInt(temp[2]);

 		temp = in.nextLine().split(" ");
 		int[] array = new int[N];
 		int[] arrayTemp = new int[N];
 		for(i=0; i< N;i++){
 			array[i] = arrayTemp[i] = Integer.parseInt(temp[i]);
 		}

 		// we move the array
 		K = K % N;
 		
 		int t;
 		
 		for(i=0;i<N;i++){
 	        t = (i+N-K)%N;
 	        array[i]=arrayTemp[t];   
 	    }
 		
 		// we print the position of the final array
 	    for(i=0;i<Q;i++){
 	        t = in.nextInt();
 	        System.out.println(array[t]);
 	    }
 		
	}

}
