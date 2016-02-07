package hash.code;

public class Junction {

	// <latitud, longitud>
	int[] location = new int[2];
	
	// set of destiny in a junction
	public QueueDestiny destiny = new QueueDestiny();
	
	
	public Junction(int lat, int longitud) {
		location[0] = lat;
		location[1] = longitud;
	}
	
}