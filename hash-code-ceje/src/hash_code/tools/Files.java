/** *  
 *  @author    		Cesar Augusto Guzman Alvarez
 */
package hash_code.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Files {
	
	private File fichero = null;
	private FileWriter fWriter;
	private BufferedWriter bWriter;
	

	public Files(String file, boolean modificar) {
		create(file, modificar);
	}

	public void create(String path, boolean modificar) {
		fichero = new File(path);
		
		try {
			if (!fichero.exists())
				fichero.createNewFile();

			fileTowrite(fichero, modificar);

		} catch (IOException ioe) {
			System.out.println("Elte");
			ioe.printStackTrace();
		}
	}
		
	private void fileTowrite(File fichero, boolean modificar) {
		
		try{
			fWriter = new FileWriter(fichero, modificar);
			bWriter = new BufferedWriter(fWriter);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void write(String string) {
		try{
			bWriter.write(string);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public void close() {
		try{
			bWriter.close();
			fWriter.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}