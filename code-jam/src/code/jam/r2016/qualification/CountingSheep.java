package code.jam.r2016.qualification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import tools.Files;

public class CountingSheep {

	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/";
	private String file_problem = "A-large";
	private String file_out = file_problem+".out";
	
	
	public static void main(String[] args){
		CountingSheep challenge = new CountingSheep();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+file_problem+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				int j; 
				
				int SIZE = Integer.parseInt(bf.readLine());				
				BigInteger SIZE_BIG = new BigInteger(Integer.toString(SIZE));
				
				boolean[] check = new boolean[10];
				int count = 0;
				int index = -1;
				BigInteger result;
				String tmp="";
				for(j = 1; j <= 100;++j){
					result = new BigInteger(Integer.toString(j));
					result = result.multiply(SIZE_BIG);
					
					tmp = result.toString();
					for(int k=0; k < tmp.length();++k){
						int t = Integer.parseInt(String.valueOf(tmp.charAt(k)));
						if(!check[t]){
							count++;
							check[t] = true;
						}
					}
					
					if(count == 10){
						index=j;
						break;
					}
				}
				
				String t_result = (index==-1)?"INSOMNIA":tmp;
				
				System.out.println("Case #"+i+": "+t_result);
				
				output = output + "Case #"+i+": "+t_result+"\n";
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}

			
			bf.close();
		} catch (IOException e) {}		
	}
	
}