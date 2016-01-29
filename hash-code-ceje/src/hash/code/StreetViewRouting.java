package hash.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import hash.code.tools.Files;


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

			BufferedReader bf = new BufferedReader(new FileReader("/home/zenshi/Trabajos/java/facebook/input"));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			int T = Integer.parseInt(bf.readLine());
			if (T >= 1 && T <= 20)
				for (int i = 1; i <= T; ++i) {
					sCadena = bf.readLine().split(" ");
					output = output + "Case #"+i+": "+fontSizer(sCadena)+"\n";
				}
			
			Files file = new Files("output", false);
			file.write(output);
			file.close();
		} catch (IOException e) {
		}
	}

	private int fontSizer(String[] sCadena) {
		
		return 0;
	}

}