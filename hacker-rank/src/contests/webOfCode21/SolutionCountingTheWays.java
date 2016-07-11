package contests.webOfCode21;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SolutionCountingTheWays {
    
    public static void main(String[] args) throws FileNotFoundException {

    	File file = new File("./TEST/algorithms/webOfCode21/LazySort/input-test.txt");
    	Scanner in = new Scanner(file);
    	// Scanner in = new Scanner(System.in);

    	int N = in.nextInt();
    	
    	List<Integer> P = new ArrayList<Integer>();
    	
    	for(int i=0; i < N; ++i){
    		int T = in.nextInt();
    		P.add(T);
    	}
    	
        System.out.println(expectedTime(P));
    }
    
    public static String expectedTime(List<Integer> P){
    	
    	double num = 0, den = 1;
    	
    	num = factorial(P.size());
    	
    	int[] num_P = new int[100];
    	for(int i=0; i < P.size(); i++){
    		num_P[P.get(i)]++;
    	}
    	
    	for(int i=0; i < num_P.length;i++){
    		if(num_P[i] > 1 && num_P[i] != -1){
    			den*=factorial(num_P[i]);
    		}
    	}
    	
    	double sum = num / den;
		
    	return String.format("%.6f", sum);
    	
    }
    
    public static int factorial(int N){
    	int sum = 1;
    	for(int i =1; i <= N; i++){
    		sum*=i;
    	}
    	return sum;
    }
}