package challenge6.problem2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Word {
	
	public String text;

	public int frecuency = 0;
	public List<Integer> pos=null;
	
	public boolean sort = false;
	
	public void add(int i) {
		if(pos == null)
			pos = new ArrayList<Integer>(100);
		frecuency++;
		pos.add(i);
	}

	public int[] getPosition(int A, int b) {
		if(!sort){
			Collections.sort(pos);
			sort = true;
		}
		
		int[] limits = {A,b};
		for(int j=0; j<pos.size(); ++j){
			if(pos.get(j) < A){
				limits[0] = pos.get(j);
			}else break;
		}

		for(int j=pos.size()-1; j>=0; --j){
			if(pos.get(j) <= b){
				limits[1] = pos.get(j);
				break;
			}
		}
		
		return limits;
	}
	

	@Override
	public String toString() {
		return "[" + text + ", frecuency=" + frecuency + ", pos=" + pos + ", sort=" + sort + "]";
	}

}
