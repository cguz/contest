package topcoder.binaysearch;
class SRM230SortEstimate{
	
    public static double howMany(int c, int time){
        double l = 0;
        double h = 0x1.fffffffffffffP+1023;
        double mid = 0;
        double log = 0;
        
        for(int i=0; i < 10000; ++i){
        	//  while(h/l > time){
        	mid = l + (h-l)/2;

        	log = c*mid*Math.log(mid) / Math.log(2);
        	// System.out.println(log+" h:"+h+" l:"+l+" time:"+time);
        	
        	if(log >= time){
        		h = mid;
        	}else{
        		l = mid;
        	}
        }
        
        return l;
        
    }
    
    public static void main(String[] args){
    	System.out.println(howMany(37, 12392342));
    }
    
}