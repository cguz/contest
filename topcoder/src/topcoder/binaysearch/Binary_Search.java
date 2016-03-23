package topcoder.binaysearch;


class Binary_Search{
	
	public static void main(String[] args){
		
		int[] A = {3,8,9,20,40};
		
		System.out.println(binary_search(A, 40));
		
	}
	
	public static int binary_search(int[] A, int target){
		int l = 0;
		int h = A.length;
		
		while(l < h){
			int mid = l + (h-l)/2;
			
			if (A[mid] == target)
				return mid;
			else{
				if(A[mid] < target){
					l = mid + 1;
				}else{
					h = mid - 1;
				}
			}
		}
		
		return -1;
	}
	
}