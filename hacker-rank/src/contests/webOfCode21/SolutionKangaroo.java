package contests.webOfCode21;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolutionKangaroo {
    
    public static void main(String[] args) throws FileNotFoundException {
       
        
    	//File file = new File("./TEST/algorithms/webOfCode21/Kangaroo/input-test.txt");
    	//Scanner in = new Scanner(file);
    	Scanner in = new Scanner(System.in);

    	int x1 = in.nextInt();
    	int v1 = in.nextInt();
    	int x2 = in.nextInt();
    	int v2 = in.nextInt();
        
        System.out.println(sameLocation(x1,x2,v1,v2));
    }
    
    public static String sameLocation(int x1, int x2, int v1, int v2){
    	
    	if(x1 == x2) return "YES";
    	
    	if(v1 == v2) return "NO";
    	
    	if((x1 < x2 && v2 > v1) || (x2 < x1 && v1 > v2)) return "NO";
    	
    	return sameLocation(x1+v1,x2+v2,v1,v2);
    }
}