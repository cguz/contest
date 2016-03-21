package hash_code.contest_2016.final_round.street_view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hash_code.tools.Files;


/**
 * @Challenge StreetViewRouting
 */
public class StreetViewRouting {

	
	public static void main(String[] args) {
		StreetViewRouting challenge = new StreetViewRouting();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String[] sCadena = null;
		try {

			BufferedReader bf = new BufferedReader(new FileReader("/home/anubis/Trabajos/java/hash-code/git/hash-code/hash-code-ceje/input/paris_54000.txt"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			City city = new City();
			
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			int T = Integer.parseInt(temp[2]);
			int C = Integer.parseInt(temp[3]);
			int S = Integer.parseInt(temp[4]);
			
			for (int i = 1; i <= N; ++i) {
				
				bf.readLine();
				city.junctions.add(new Junction(-1, -1));
				
				// System.out.println(bf.readLine());
				
				/*temp2 = bf.readLine().split(" ");
				city.junctions.add(new Junction(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2])));*/
				
			}
			
			for (int i = 1; i <= M; ++i) {
				
				temp2 = bf.readLine().split(" ");
				
				city.junctions.get(Integer.parseInt(temp2[0])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[1]), 
						Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4])));
				
				if(Integer.parseInt(temp2[2])==2){
					city.junctions.get(Integer.parseInt(temp2[1])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[0]), 
							Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4])));
				}
				
				/*temp2 = bf.readLine().split(" ");
				city.junctions.add(new Junction(Integer.parseInt(temp2[1]), Integer.parseInt(temp2[2])));*/
				
			}

			
			/*// sint T = Integer.parseInt(bf.readLine());
			if (T >= 1 && T <= 20)
				for (int i = 1; i <= T; ++i) {
					sCadena = bf.readLine().split(" ");
					output = output + "Case #"+i+": "+fontSizer(sCadena)+"\n";
				}
			
			Files file = new Files("output", false);
			file.write(output);
			file.close();*/
		} catch (IOException e) {
		}
	}

	private int fontSizer(String[] sCadena) {
		
		return 0;
	}

}