
import java.util.Arrays; 

class kth
{ 

	public static int partition (int[] arr, int low, int high) 
	{ 
		int pivot = arr[high], pivotloc = low; 
		for (int i = low; i <= high; i++) 
		{ 
			if(arr[i] < pivot) 
			{ 
				int temp = arr[i]; 
				arr[i] = arr[pivotloc]; 
				arr[pivotloc] = temp; 
				pivotloc++; 
			} 


			} 

		
		
		int temp = arr[high]; 
		arr[high] = arr[pivotloc]; 
		arr[pivotloc] = temp; 

			return pivotloc; 
	} 
	
		public static int kthSmallest(int[] arr, int low, 
								int high, int k) 
	{ 
		int partition = partition(arr,low,high); 
		if(partition == k) 
			return arr[partition];	 
			
		else if(partition < k ) 
			return kthSmallest(arr, partition + 1, high, k ); 
			
		else
			return kthSmallest(arr, low, partition-1, k );		 
	} 
	
	 
	public static void main(String[] args) 
	{ 
		int[] array = new int[]{10, 4, 5, 8, 6, 11, 26}; 
		int[] arraycopy = new int[]{10, 4, 5, 8, 6, 11, 26}; 
				
		int kPosition = 3; 
		int length = array.length; 
		
		if(kPosition > length) 
		{ 
			System.out.println("Index out of bound"); 
		} 
		else
		{ 
			System.out.println("K-th smallest element in array : " + 
								kthSmallest(arraycopy, 0, length - 1, 
														kPosition - 1)); 
		} 
	} 
} 

