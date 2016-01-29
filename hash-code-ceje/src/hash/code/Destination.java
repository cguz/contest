package hash.code;

public class Destination implements Comparable<Destination> {

	int origin = -1;
	int destination = -1;
	double time = 0;
	double length = 0;
	int numberTimes = 0;
	
	//this < o -> 	-1
	//o > this ->	1
	//this = 0
	//	this < o	->  1
	//	this > o	-> -1
	//	this = o	0
	@Override
	public int compareTo(Destination o) {
		if (this.length < o.length) {
			return -1;
		} else if (this.length > o.length) { 
			return 1;
		} else if (this.length == o.length) {
			if(this.numberTimes < o.numberTimes) {
				return 1;
			} else if(this.numberTimes > o.numberTimes) {
				return -1;
			} else {
				return 0;
			}
		}
		
		return 0;
	}

}
