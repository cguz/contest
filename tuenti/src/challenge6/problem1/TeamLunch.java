package challenge6.problem1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import tools.Files;

public class TeamLunch {
	
	private String dir_inputs = "/home/zenshi/git/contests/tuenti/INPUTS/Challenge6/";
	private String filename = "submitInputTeamLunch";

	private int 	max = 32768;
	private int 	pos = 2;
	private int[] 	table = new int[max];
	
	public static void main(String[] args){
		TeamLunch challenge = new TeamLunch();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+filename));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				int N = Integer.parseInt(bf.readLine());
				
				int total_tables;
				
				if(N <= table[pos]){
					total_tables = minimumTablesBinarySearch(N);
				}else
					total_tables = minimumTables(N);
				
				System.out.println("Case #"+i+": "+total_tables);
				
				output = output + "Case #"+i+": "+total_tables+"\n";
				Files file = new Files(dir_inputs+filename+".out", false);
				file.write(output);
				file.close();
			}
			
		} catch (IOException e) {}		
	}
	
	private int minimumTablesBinarySearch(int target) {
		
		if(target == 0) return 0;
		if(target <= 4) return 1;
		
		int l = 0;
		int h = pos;
		
		while(l <= h){
			int mid = l + (h-l)/2;
			if(target > (table[mid] - 2) && target <= table[mid]){
				return mid;
			}else{
				if(table[mid] > target)
					h = mid -1;
				else
					l = mid + 1;
			}
		}
		return -1;
	}

	public int minimumTables(int N){
		int i;
		if(N==0) return 0;
		if(N <= 4) return 1;
		table[0] = 0;
		table[1] = 4;
		
		for(i=pos;i<N;++i){
			table[i] = (table[i-1]-1) + 3;
			pos = i;
			if(N<=table[i])
				return i;
		}
		return -1;
	}
	
}