package topcoder.string;

public class SRM445TheEncryptionDivTwo {

    public SRM445TheEncryptionDivTwo() {}


    public static void main(String[] args){
    	System.out.println(encrypt("encryption"));
    }
    
    // dynamic programming
    public static String encrypt(String m) {
    	
    	String message = "";
    	
    	char[] assign = new char['z'-'a'];
    	char current = 'a';
    	
    	for(int i=0; i < m.length(); i++){
    		int t = ((int)m.charAt(i))-97;
    		
    		if(assign[t]==0){
    			assign[t] = current;
    			current++;
    		}
    		message+=assign[t];
    	}
    	
    	return message;
       
    }

}