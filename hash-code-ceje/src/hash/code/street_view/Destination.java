package hash.code.street_view;

public class Destination implements Comparable<Destination> {

	//int origin = -1;
	int destination = -1;
	int time = 0;
	int length = 0;
	int numberTimes = 0;
	
	public Destination(int string, int string2, int string3) {
		destination = string;
		time = string2;
		length = string3;
	}

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
