package hash_code.algorithms.graph;

import hash_code.algorithms.graph.enumerates.ALGORITHM;
import hash_code.algorithms.graph.interfaces.SearchAlgorithm;

public class SearchAlgorithmFactory {

	public static SearchAlgorithm getAlgorithm(ALGORITHM algorithm) {
		switch(algorithm){
			case BREADTH_SEARCH:
				return new BreadthSearchAlgorithm();
		}
		return null;
	}

}
