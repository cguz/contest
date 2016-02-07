package hash.code.test_problem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import hash.code.Destination;
import hash.code.Junction;


/**
 * @Challenge StreetViewRouting
 */
public class Painting {

	public ArrayList<Junction> junctions = new ArrayList<Junction>();

	
	public static void main(String[] args) {
		Painting challenge = new Painting();
		
		challenge.begin(args);
		
	}

	private void begin(String[] args) {
		String output = "";
		String[] sCadena = null;
		try {

			BufferedReader bf = new BufferedReader(new FileReader("/home/zenshi/git/hash-code/hash-code-ceje/input/example.txt"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			String[] temp = bf.readLine().split(" ");
			String[] temp2;
			
			int N = Integer.parseInt(temp[0]);
			int M = Integer.parseInt(temp[1]);
			
			for (int i = 1; i <= N; ++i) {
				
				bf.readLine();
				junctions.add(new Junction(-1, -1));
				
			}
			
			for (int i = 1; i <= M; ++i) {
				
				temp2 = bf.readLine().split(" ");
				
				junctions.get(Integer.parseInt(temp2[0])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[1]), 
						Integer.parseInt(temp2[3]), Integer.parseInt(temp2[4])));
				
				if(Integer.parseInt(temp2[2])==2){
					junctions.get(Integer.parseInt(temp2[1])).destiny.colaEspera.add(new Destination(Integer.parseInt(temp[0]), 
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

}