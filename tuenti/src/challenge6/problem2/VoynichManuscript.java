package challenge6.problem2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import tools.Files;

public class VoynichManuscript {
	
	private String dir_inputs = "/home/zenshi/git/contests/tuenti/INPUTS/Challenge6/";
	private String corpusfile = "2-corpus.txt";
	private String filename = "2-submitInput";

	private int[] frec_corpus;
	private String[] corpus;
	private HashMap<String,Word> frecuency;

	private int[] max1=new int[2]; // [frecuency, pos_word]
	private int[] max2=new int[2];
	private int[] max3=new int[2];
	
	public static void main(String[] args){
		VoynichManuscript challenge = new VoynichManuscript();
		
		challenge.begin(args);
	}
	
	public void begin(String[] args){
		try {

			String output = "";
			
			BufferedReader bf = new BufferedReader(new FileReader(dir_inputs+corpusfile));
			//BufferedReader bf = new BufferedReader(new FileReader(new File (args[0])));
		    
			corpus = bf.readLine().split(" ");
			frec_corpus = new int[corpus.length];
			frecuency = new HashMap<String, Word>();
			
			for(int i=0; i < corpus.length; ++i){
				Word count=null;
				if(frecuency.containsKey(corpus[i])){
					count = frecuency.get(corpus[i]);
				}
				if(count == null)
					count = new Word();
				count.add(i);
				count.text = corpus[i];
				frecuency.put(corpus[i], count);
				frec_corpus[i] = count.frecuency;
			}
			
			// for(int i=0; i < corpus.length; ++i)
			// 	System.out.println(i+" "+corpus[i]+" "+frec_corpus[i]);
			// System.out.println("");	
			
			
			
			bf = new BufferedReader(new FileReader(dir_inputs+filename));
			
			int N = Integer.parseInt(bf.readLine());

			for (int i = 1; i <= N; ++i) {

				max3[0] = 0;
				max3[1] = 0;
				max2[0] = 0;
				max2[1] = 0;
				max1[0] = 0;
				max1[1] = 0;
				
				String[] temp = bf.readLine().split(" ");

				int A = Integer.parseInt(temp[0]);
				int B = Integer.parseInt(temp[1]);
				
				if(B > corpus.length)
					B = corpus.length;
				
				maxFrecuency(A-1, B-1);
				
				System.out.println("Case #"+i+": "+corpus[max1[1]]+" "+max1[0]
						+","+corpus[max2[1]]+" "+max2[0]+","+corpus[max3[1]]+" "+max3[0]);
				
				output = output + "Case #"+i+": "+corpus[max1[1]]+" "+max1[0]
				+","+corpus[max2[1]]+" "+max2[0]+","+corpus[max3[1]]+" "+max3[0]+"\n";
			}
			
			Files file = new Files(dir_inputs+filename+".out", false);
			file.write(output);
			file.close();
			
		} catch (IOException e) {}		
	}
	
	public void maxFrecuency(int A, int B){
		// System.out.println("\n\nlimits A:"+A+", B:"+B);
		
		Word w;
		for(int i = A; i <= B; ++i){
			w = frecuency.get(corpus[i]);
			
			//if(w.text.equals("bipepice"))
			//	System.out.println("\n"+w.toString());
			
			int[] f = w.getPosition(A, B);
			int frec;
			if(f[1] > B)
				frec = frec_corpus[f[0]];
			else{
				if(f[0] >= A)
					frec = frec_corpus[f[1]];
				else
					frec = frec_corpus[f[1]]-frec_corpus[f[0]];
			}

			// System.out.println("limit_fre: "+f[0]+" to "+f[1]+", frecuency: "+frec);
			
			if(frec > max1[0]){
				if(i != max1[1]){
					max3[0] = max2[0];
					max3[1] = max2[1];
					max2[0] = max1[0];
					max2[1] = max1[1];
					max1[0] = frec;
					max1[1] = i;
				}
			}else{
				if(frec < max1[0] && frec > max2[0]){
					if(i != max2[1] && i != max1[1]){
						max3[0] = max2[0];
						max3[1] = max2[1];
						max2[0] = frec;
						max2[1] = i;
					}
				}else{
					if(frec < max1[0] && frec < max2[0] && frec > max3[0]){
						if (i != max3[1] && i != max2[1] && i != max1[1]){
							max3[0] = frec;
							max3[1] = i;
						}
					}
				}
			}			
		}
	}
	
}