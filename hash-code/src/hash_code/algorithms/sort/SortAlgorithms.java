package hash_code.algorithms.sort;

/**
 * @author cguzman@cguz.org
 * 
 * This class contains different sort algorithms
 */
public class SortAlgorithms {
	
	/**
	 * Bubble sort : exchange sort algorithm
	 * 
	 * It is stable and adaptive (do not check if the array of elements is already sorted)
	 *
	 * How works ?
	 * 1. Starting from the beginning. Swap the two first elements if e1 > e2
	 * 2. If swap go to 1
	 * 
	 * Time complexity
	 * 	- best case: O(n)
	 *  - average / worst case: O(n^2)
	 *  
	 * Space complexity
	 * 	- worst case: O(1)
	 *
	 * @param elements
	 */
	public void bubbleSort(int[] elements){
		
		boolean swap = true;
		int avoidLastElement = 0, tmp = 0; 
		
		while(swap){
			swap = false;
			avoidLastElement++; // avoid to evaluate the last element
			for(int index = 0; index < elements.length-avoidLastElement; ++index){
				if(elements[index] > elements[index+1]){
					tmp = elements[index];
					elements[index] = elements[index+1];
					elements[index+1] = tmp;
					swap = true;
				}
			}
		}
	}
	
	/**
	 * Quicksort : "Exchange sort algorithm"
	 * 
	 * How work ?
	 * 1. Choose a value as the pivot
	 * 2. Partition the elements left < value < right
	 * 3. Sort both parts
	 * 
	 * Time complexity
	 * 	- best / average cases: O(n*log(n))
	 * 	- worst case: O(n^2)
	 * 
	 * Space complexity
	 * 	- worst: O(n)
	 * 
	 * @param elements
	 */
	public void quickSort(int[] elements){
		quickSort(elements, 0, elements.length-1);
	}
	
	private void quickSort(int[] elements, int left, int right){
		int i = left;
		int j = right;
		int tmp;
		
		int pivot = elements[(i+j)/2];
		while(i <= j){
			
			while(elements[i] < pivot) i++;
			
			while(elements[j] > pivot) j--;
			
			if(i <= j){
				tmp = elements[i];
				elements[i] = elements[j];
				elements[j] = tmp;
				i++;
				j--;
			}				
		}
		
		// recursion
		if(left < i) quickSort(elements, left, j);
		if(right > j) quickSort(elements, i, right);
	}
	
	/**
	 * Selection sort: "Selection sort algorithm"
	 * 
	 * How works ?
	 * Take the current element and exchange it with the smallest element on the right hand
	 * 
	 * Time complexity
	 * 	 - best / worst / average cases: O(n^2)
	 * 
	 * Space complexity
	 * 	 - worst: O(n)
	 * 
	 * @param elements
	 */
	public void selectionSort(int[] elements){
		int smallestRight, iMinimum, tmp;
		int size = elements.length;
		
		for(int index=0; index < size; ++index){
			iMinimum = index;
			for(smallestRight=index+1; smallestRight < size; ++smallestRight)
				if(elements[smallestRight] < elements[iMinimum]) iMinimum = smallestRight;
			
			if(iMinimum != index){
				tmp = elements[index];
				elements[index] = elements[iMinimum];
				elements[iMinimum] = tmp;
			}					
		}
	}
	
	/**
	 * Insertion sort: "Insertion sort algorithm"
	 * 
	 * How works?
	 * Take the current element and insert it at the appropriated position of the list,
	 * adjusting the list.
	 * 
	 * Time complexity.
	 * 	 - best case: O(n)
	 * 	 - average / worst case: O(n^2)
	 * 
	 * Space complexity
	 * 	 - worst: O(n)
	 * 
	 * Where to use ?
	 * 
	 * - List that are nearly sorted initially
	 * - Data set that are constantly being added to the list
	 * 
	 * @param elements
	 */
	public void insertionSort(int[] elements){
		
		for(int index=1; index < elements.length; index++){
			int tmp = elements[index];
			int indexLeft = index-1;
			
			while(indexLeft >= 0 && elements[indexLeft] > tmp){
				elements[indexLeft+1] = elements[indexLeft];
				indexLeft--;
			}
			
			elements[indexLeft+1] = tmp;
		}
		
	}
	
	public static void main(String args[]){
		int[] elements = {3,1,7,2,10};
		SortAlgorithms s = new SortAlgorithms();
		s.quickSort(elements);
		
		for(int i: elements)
			System.out.print(i+" ");
	}
	
}
