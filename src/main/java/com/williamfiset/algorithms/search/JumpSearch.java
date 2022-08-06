package com.williamfiset.algorithms.search;

public class JumpSearch {
	
	// Jump searching jump in the interval square root of length of array ahead until reach an element greater than current element or end of the array
	// If encounter an element greater than the searching element, jumping will stop
	// Linear search apply between the previous step and the current step
	
	public int jumpSearch(int[] integers, int elementToSearch) {

	    int arrayLength = integers.length;
	    int jumpStep = (int) Math.sqrt(integers.length);
	    int previousStep = 0;
	    
	    // Throw exception if integers array is empty
	    if(integers.length == 0) {
	    	String msg = "The list of elements must not be empty.";
            throw new IllegalArgumentException(msg);
	    }

	    // Looping when integers[x] < searching element
	    while (integers[Math.min(jumpStep, arrayLength) - 1] < elementToSearch) {
	        previousStep = jumpStep;
	        jumpStep += (int)(Math.sqrt(arrayLength));
	        
	        //Return -1 if the searching element is not found and the searching element is larger than last element
	        if (previousStep >= arrayLength) {
	            return -1;
	        }
	    }
	    
	    // Linear search is used to find the element from the previous step
	    while (integers[previousStep] < elementToSearch) {
	        previousStep++;
	        
	        if (previousStep == Math.min(jumpStep, arrayLength)) {
	            return -1;
	        }
	    }

	    // The correct position of searching will be return 
	    if (integers[previousStep] == elementToSearch)
	        return previousStep;
	    
	    // Else -1 will be return
	    return -1;
	}

}
