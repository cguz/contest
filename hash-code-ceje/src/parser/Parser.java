package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.util.List;

public class Parser {

	private String pFile;
	private StreamTokenizer st;
	private Reader reader;
	
	public Parser (String _pFile) throws FileNotFoundException {
		this.pFile = _pFile;
		this.reader =  new FileReader(_pFile);
		this.st = new StreamTokenizer(reader);
	}
	
	public List<String> getData (String _pFile) throws IOException {
		
		while (st.nextToken() != StreamTokenizer.TT_EOF) {
			System.out.println(st.sval);
		}
		
		reader.close();
		
		return null;
	}
	
}
