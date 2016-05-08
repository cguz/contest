package code.jam.r2016.oneC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import tools.Files;

public class SenateEvacuation {

	public class Data {

		public int senator_i;
		public int senator_j;
		
		public Data(int max_i, int max_l_i) {
			senator_i = max_i;
			senator_j = max_l_i;
		}
		
		public Data(int max_i) {
			senator_i = max_i;
			senator_j = -1;
		}

	}

	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/1C/";
	private String file_problem = "A-small";
	private String file_out = file_problem+".out";
	
	
	public static void main(String[] args){
		SenateEvacuation challenge = new SenateEvacuation();
		
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
				int[] N = new int[SIZE];

				String[] temp = bf.readLine().split(" ");
				
				BigInteger sum = new BigInteger("0");
				int max = 0;
				int max_i = 0;
				int max_l = 0;
				int max_l_i = 0;
				
				for(j=0; j < temp.length; ++j){
					int value = Integer.parseInt(temp[j]);
					N[j] = value;
					
					sum = sum.add(new BigInteger(Integer.toString(value)));
					
					if(value > max){
						max_l = max;
						max_l_i = max_i;
						max = value;
						max_i = j;
					}else{
						if(value > max_l){
							max_l = value;
							max_l_i = j;							
						}
					}
				}
				
				Data[] evacuate = new Data[10000];
				int pos = 0;
				
				
				do {
						
					if(max == max_l){
						max = --N[max_i];
						max_l = --N[max_l_i];
						sum = sum.subtract(new BigInteger("2"));
						evacuate[pos] = new Data(max_i, max_l_i);
					}else{
						max = --N[max_i];
						sum = sum.subtract(new BigInteger("1"));
						if(sum.intValue() > 0){
							max = --N[max_i];
							sum = sum.subtract(new BigInteger("1"));	
							evacuate[pos] = new Data(max_i, max_i);						
						}else
							evacuate[pos] = new Data(max_i);
					}
					++pos;
					
					// find the max and sum
					sum = new BigInteger("0");
					max = 0;
					max_i = 0;
					max_l = 0;
					max_l_i = 0;
					for(j=0; j < N.length; ++j){
						int value = N[j];
						
						sum = sum.add(new BigInteger(Integer.toString(value)));
						
						if(value > max){
							max_l = max;
							max_l_i = max_i;
							max = value;
							max_i = j;
						}else{
							if(value > max_l){
								max_l = value;
								max_l_i = j;							
							}
						}
					}
					
					if(sum.intValue() > 0 && !rules(max, sum)){
						System.out.println("NOT Correct- Sum: "+sum.intValue()+" max:"+max);		
					}
						
				}while(sum.intValue() > 0);
				
				
				
				String t_result = ""; 
				for(Data d: evacuate){
					if(d == null)
						break;
					char a;
					a = (char)(d.senator_i+65);
					if(d.senator_j != -1){
						char b;
						b = (char)(d.senator_j+65);
						t_result += a+""+b+" ";
					}else{
						t_result += a+" ";						
					}
				}
				
				System.out.println("Case #"+i+": "+t_result);
				
				output = output + "Case #"+i+": "+t_result+"\n";
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}
			
		} catch (IOException e) {}		
	}

	private boolean rules(int max, BigInteger sum) {
		
		return (max/sum.intValue() > 0.5);
	}
	
}