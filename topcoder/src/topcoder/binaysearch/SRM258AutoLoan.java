package topcoder.binaysearch;
public class SRM258AutoLoan{

	public static double interestRate(double price, double monthlyPayment, int loanTerm){
		double l=0;
	    double h=100;
	    double mid=0;
	    double sum;
	    while(h-l > 1e-12){
	        mid = l + (h-l)/2;
	        double mi = mid*((double)1/12);
	        double term = loanTerm;
	        sum = price;
	        while(term > 0){
	        	sum+=(sum*(mi/100));
	            sum-=monthlyPayment;
	            term--;
	        }
	        if(sum > 1e-12)
	            h = mid;
	        else
	            l = mid;
	    }
	    return h;
	}
	
	public static void main(String[] args){
		System.out.println(interestRate(2000, 510, 4));
	}
	
}