package code.jam.r2016.oneB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

import tools.Files;

public class GettingDigits {

	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/1B/";
	private String file_problem = "A-large";
	private String file_out = file_problem+".out";
	
	private static Data[] digits = new Data[10];
	
	
	public static void main(String[] args){
		GettingDigits challenge = new GettingDigits();
		
		digits[0] = new Data(0, 'Z', "ZERO");
		digits[1] = new Data(2, 'W', "TWO");
		digits[2] = new Data(6, 'X', "SIX");
		digits[3] = new Data(4, 'U', "FOUR");
		digits[4] = new Data(8, 'G', "EIGHT");
		digits[5] = new Data(3, 'H', "THREE");
		digits[6] = new Data(7, 'S', "SEVEN");
		digits[7] = new Data(1, 'O', "ONE");
		digits[8] = new Data(9, 'N', "NINE");
		digits[9] = new Data(5, 'F', "FIVE");
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+file_problem+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				String S = bf.readLine();
				
				int[] letters = new int[256];
				char[] s_array = S.toCharArray();
				
				for(char c: s_array) letters[c]++;
				
				int[] phone = new int[10]; 
				
				for(int j = 0; j < digits.length; ++j){
					Data number = digits[j];
					
					while(letters[(int) number.key] > 0){
						phone[number.value]++;
						
						s_array = number.text.toCharArray();
						for(char c: s_array)
							letters[c]--;
					}
				}
				
				String t_result = "";
				for(int j = 0; j < 10; j++){
					for(int k=0; k < phone[j]; k++)
						t_result+=j;
				}
				
				System.out.println("Case #"+i+": "+t_result);
				
				output = output + "Case #"+i+": "+t_result+"\n";
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}
			
		} catch (IOException e) {}		
	}
	
}