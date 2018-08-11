package code.jam.r2016.qualification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tools.Files;

public class RevengePancakes {

	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/";
	private String file_problem = "B-large";
	private String file_out = file_problem+".out";
	
	
	public static void main(String[] args){
		RevengePancakes challenge = new RevengePancakes();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+file_problem+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				count = 0;
				
				String S = bf.readLine().replaceAll("\\-", "0").replaceAll("\\+", "1");
				int[] int_s = new int[S.length()];
				for(int j=0;j<S.length();++j)
					int_s[j] = Integer.parseInt(String.valueOf(S.charAt(j)));
				
				convert(int_s);
				
				System.out.println("Case #"+i+": "+count);
				
				output = output + "Case #"+i+": "+count+"\n";
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}
			
		} catch (IOException e) {}		
	}

	private boolean all_possitive(int[] S){
		for(int j=0; j<S.length;j++)
			if(S[j]==0)
				return false;
		return true;
	}
	
	private int count = 0;
	private int[] numbers;
	private int number;
	private int[] helper;

	private void convert(int[] int_s) {
		
		// saving current state
		numbers = int_s;
		number = int_s.length;

		// while all elements are not positive
		while(!all_possitive(int_s)){

			helper = new int[number];
			
			// search for the left
			int index_to_change = get(int_s);
			
			// change the values and count
			change(0,index_to_change);
			++count;

			int_s = numbers;
			
		}
	}

	private int get(int[] int_s) {
		
		int first = int_s[0];
		int i = 0;
		for(i=0;i<int_s.length;++i){
			if(first!=int_s[i])
				break;
		}
		
		return i-1;
	}
	
	private int[] change(int l, int h){

		helper = new int[number];
		
		for(int i = l, j=h; i <= h;i++, j--)
			helper[j] = numbers[i];
		
		for(int i=l; i <=h; ++i){
			if(helper[i] == 1)
				helper[i] = 0;
			else
				helper[i] = 1;
		}
		
		for(int i = h+1; i < number;i++)
			helper[i] = numbers[i];
				
		
		int pos = 0, neg = 0;
		for(int i = 0; i < number; i++){
			if(helper[i]==1)
				pos++;
			else neg++;
			
			if(i >= l && i <=h)
				numbers[i] = helper[i];
		}
		
		return new int[]{pos, neg};
	}
	
}