package code.jam.r2016.oneB;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.Files;

public class CloseMatch {

	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/1B/";
	private String file_problem = "B-small-attempt1";
	private String file_out = file_problem+".out";
	
	private static ArrayList<DataMin> min_values = new ArrayList<DataMin>();
	private static int min=1000000000;
	
	public static void main(String[] args){
		CloseMatch challenge = new CloseMatch();
				
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+file_problem+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				min=1000000000;
				min_values.clear();
				
				String[] S = bf.readLine().split(" ");
				findMin(S[0], S[1], 0, 0);
				
				DataMin d = min_values.get(0);
				if(min_values.size()>1){
					for(int j=1; j < min_values.size(); j++){
						if(Integer.parseInt(min_values.get(j).C) < Integer.parseInt(d.C))
							d = min_values.get(j);
						else{
							if(Integer.parseInt(d.C) == Integer.parseInt(min_values.get(j).C)){
								if(Integer.parseInt(min_values.get(j).F) < Integer.parseInt(d.F)){
									d = min_values.get(j);
								}
							}
						}
					}
				}
			
				String t_result = "";			
				t_result = d.C + " "+ d.F;
				
				System.out.println("Case #"+i+": "+t_result);
				
				output = output + "Case #"+i+": "+t_result+"\n";
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}
			
			bf.close();
			
		} catch (IOException e) {}		
	}

	private void findMin(String C, String F, int from, int permuta) {
		
		for(int i = from; i < C.length(); ++i){
			if(C.charAt(i) == '?'){
				int t = 0;
				while(t < 10){
					findMin(new String(C).replaceFirst("\\?", String.valueOf(t)), F, i, t);
					t++;
				}
			}
			
			if(F.charAt(i) == '?'){
				int t = 0;
				while(t < 10){
					findMin(C,  new String(F).replaceFirst("\\?", String.valueOf(t)), i, t);
					t++;
				}
			}
		}
		
		if(!C.contains("?") && !F.contains("?")){
			int value = Math.abs(Integer.parseInt(C)-Integer.parseInt(F));
			if(value < min){
				min = value;
				min_values.clear();
				min_values.add(new DataMin(C, F));
			}else{
				if(min == value)
					min_values.add(new DataMin(C, F));				
			}
		}
	}
	
}