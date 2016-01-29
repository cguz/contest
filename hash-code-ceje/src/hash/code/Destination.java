package hash.code;

public class Destination implements Comparable<Destination> {

	int origin = -1;
	int destination = -1;
	double time = 0;
	double length = 0;
	int numberTimes = 0;
	
	
	@Override
	public int compareTo(Destination o) {
		return 0;
	}
	
}
