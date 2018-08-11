package code.jam.r2016.oneC;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import tools.Files;

public class FashionPolice {

	public class Data {

		public int J;
		public int P;
		public int S;
		public int K;
		public int used;
		
		public Data(int j, int p, int s, int k) {
			J = j;
			P = p;
			S = s;
			K = k;
		}
		
		public Data(int j, int p, int s) {
			J = j;
			P = p;
			S = s;
		}

		@Override
		public String toString() {
			return J+" "+P+" "+S;
		}

		
		
	}
	

	public class Combination {

		public int J;
		public int P;
		public int used;
		public int combination;
		
		public Combination(int j, int p, int cJs) {
			J = j;
			P = p;
			used = 1;
			combination = cJs;
		}

		@Override
		public String toString() {
			return J+" "+P+" combination: "+combination+" used: "+used;
		}

		@Override
		public boolean equals(Object obj) {
			if(((Combination)obj).J == this.J && ((Combination)obj).P == this.P && ((Combination)obj).combination == this.combination)
				return true;
			return false;
		}


	}


	private String dir_inputs = "/home/zenshi/git/contests/code-jam/INPUTS/2016/1C/";
	private String file_problem = "C-small";
	private String file_out = file_problem+".out";
	
	
	public static void main(String[] args){
		FashionPolice challenge = new FashionPolice();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+file_problem+".in"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			
			int T = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= T; ++i) {

				String[] temp = bf.readLine().split(" ");
				Data max = new Data(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
				
				ArrayList<Combination> l = new ArrayList<Combination>();

				ArrayList<Data> result = new ArrayList<Data>();
				for(int j = 1; j <= max.J && j <= max.P && j <= max.S; j++){
					for(int p = 1; p <= max.P && p <= max.S; p++){
						for(int s = 1; s <= max.S; s++){
							
							// find the combinations
							Combination c1 = new Combination(j,p, 0);
							int index1 = l.indexOf(c1);
							if(index1 != -1){
								c1 = l.get(index1);
								if(c1.used > max.K)
									continue;
							}

							Combination c2 = new Combination(j,s, 1);
							int index2 = l.indexOf(c2);
							if(index2 != -1){
								c2 = l.get(index2);
								if(c2.used > max.K)
									continue;
							}
							
							Combination c3 = new Combination(p,s,2);
							int index3 = l.indexOf(c3);
							if(index3 != -1){
								c3 = l.get(index3);
								if(c3.used > max.K)
									continue;
							}

							c1.used++;
							c2.used++;
							c3.used++;
							if(index1==-1)
								l.add(c1);
							if(index2==-1)
								l.add(c2);
							if(index3==-1)
								l.add(c3);
							result.add(new Data(j,s,p));
							
						}	
					}	
				}
				
				String t_result = result.size()+"\n";
				for(Data r: result)
					t_result+=r.toString()+"\n";
				
				System.out.print("Case #"+i+": "+t_result);
				
				output = output + "Case #"+i+": "+t_result;
				Files file = new Files(dir_inputs+file_out, false);
				file.write(output);
				file.close();
			}

			
			bf.close();
		} catch (IOException e) {}		
	}

}