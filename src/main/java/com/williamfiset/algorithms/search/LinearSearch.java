package com.williamfiset.algorithms.search;

public class LinearSearch {
	
	   /*Linear search is rarely used practically because other search algorithms such as the binary search algorithm 
	      and hash tables allow significantly faster searching */
	   //Linear search algorithm is very simple and it has time complexity O(n).
	   /*  Algorithm : linearSearch(searchElement)
			Start:
			Step 1: found = false
			Step 2: iterate all “element” of data structure and perform step 3
			Step 3: if(searchElement == element)
			then perform step 4 & step 5.
			Step 4 : found = true
			Step 5: break
			Step 6: return found
			end:                                 */
	   
	    private int[] arr;
	    public LinearSearch(int[] arr){
	        this.arr = arr;
	    }
	    
       //Stop looping when the searching element is found
	    public boolean linearSearch(int searchElement){
	        boolean found = false;
	        for(int i=0;i<arr.length;i++){
	            if(searchElement == arr[i]){
	                found = true;
	                break;
	            }
	        }
	        return found;
	    }
        
	    public static void main(String[] args) {
	    	
	        int[] array = {100,34,2,78,1,53,17,3};
	        LinearSearch obj = new LinearSearch(array);
	        boolean result = obj.linearSearch(3);
	        
	        if(result){
	            System.out.println("Element is found in array.");
	        }
	        else{
	            System.out.println("Element is not found in array.");
	        }
	    }
}

