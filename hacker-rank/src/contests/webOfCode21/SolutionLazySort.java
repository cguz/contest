package contests.webOfCode21;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SolutionLazySort {
    
    public static void main(String[] args) throws FileNotFoundException {

    	// File file = new File("./TEST/algorithms/webOfCode21/LuckBalance/input-test.txt");
    	// Scanner in = new Scanner(file);
    	Scanner in = new Scanner(System.in);

    	int N = in.nextInt();
    	int K = in.nextInt();
    	
    	int sumI = 0;
    	int sumU = 0;
    	List<Integer> I = new ArrayList<Integer>();
    	
    	for(int i=0; i < N; ++i){
    		int L = in.nextInt();
    		int T = in.nextInt();
    		
    		if(T == 0)
    			sumU+=L;
    		else{
    			sumI+=L;
    			I.add(L);
    		}
    	}
    	
    	Collections.sort(I);
        
        System.out.println(maxLuck(sumI,sumU,I,K));
    }
    
    public static int maxLuck(int sumI, int sumU, List<Integer> I, int K){
    	
    	if(I.size() == 0) return sumU;
    	
    	int d = I.size() - K;
    	if(d <= 0) return sumI+sumU;
    	
    	for(int j=0; j < d; ++j){
    		sumI=sumI-(2*I.get(j));
    	}
    	
    	return sumI+sumU;
    	
    }
}