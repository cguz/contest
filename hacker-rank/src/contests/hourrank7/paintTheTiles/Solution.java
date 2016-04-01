package contests.hourrank7.paintTheTiles;
import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

    static BigInteger fibonaci(BigInteger a, BigInteger b, int c){
        BigInteger T;
   
        for(int i=2;i<c;++i){
            T = b;
            b = b.pow(2).add(a);
            a = T;
        }
        
        return b;
    }
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner in = new Scanner(System.in);
        BigInteger a,b,N;
        int c;
        a =  in.nextBigInteger();
        b =  in.nextBigInteger();
        c = in.nextInt();
        N = fibonaci(a, b, c);
        System.out.println(N);
    }
}
