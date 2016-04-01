package code.jam.round1A2008;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import tools.Files;

public class MinimumScalarPoduct {
	
	private String dir_inputs = "/home/anubis/Trabajos/java/hash-code/git/contests/code-jam/INPUTS/Round1A2008/";
	
	public static enum SORT{
		LESS, HIGH
	};
	
	public static void main(String[] args){
		MinimumScalarPoduct challenge = new MinimumScalarPoduct();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+"A-large-practice.in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				int j; 
				
				int SIZE = Integer.parseInt(bf.readLine());				
				
				long[] data_x = insertion_sort(bf.readLine().split(" "), SIZE, SORT.LESS);
				long[] data_y = insertion_sort(bf.readLine().split(" "), SIZE, SORT.HIGH);
				
				BigInteger scalar = new BigInteger("0");
				for(j = 0; j < data_x.length;++j)
					scalar = scalar.add(new BigInteger(Long.toString(data_x[j]*data_y[j])));					
				
				System.out.println("Case #"+i+": "+scalar);
				
				output = output + "Case #"+i+": "+scalar+"\n";
				Files file = new Files(dir_inputs+"output", false);
				file.write(output);
				file.close();
			}
			
		} catch (IOException e) {}		
	}
	
	public long[] insertion_sort(String[] d, int size, SORT sort){
		
		int k;
		long tmp;
		
		long[] data = new long[size];
		data[0] = Integer.parseInt(d[0]);
		
		// we apply an insertion sort
		for(int j = 1; j < d.length; ++j){
			tmp = Long.parseLong(d[j]);
			k = j -1;
			if(sort == SORT.LESS){
				while(k >= 0 && data[k] > tmp){
					data[k+1] = data[k];
					--k;
				}
			}else{
				if(sort == SORT.HIGH){
					while(k >= 0 && data[k] <= tmp){
						data[k+1] = data[k];
						--k;
					}
				}
			}
			data[k+1] = tmp;
		}
		
		return data;
	}
}