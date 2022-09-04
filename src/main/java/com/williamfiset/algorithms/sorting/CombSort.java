package com.williamfiset.algorithms.sorting;

public class CombSort implements InplaceSort {

	 @Override
	 public void sort(int[] values) {
		 CombSort.combsort(values);
	 }

	 // best shrink factor found = 1.3
	 private static final double shrink_factor = 1.3;
	
	 // to calculate gap value between elements
	 private static int count_gap(int gap) {
	
	     // change double value to int
	     gap = (int)(gap/shrink_factor);
	
	     if(gap < 1){
	         return 1;
	     }
	
	     return gap;
	 }
	
	 // Swap arr[i] and arr[i+gap]
	 private static void swap(int[] array, int i, int j) {
	     int temp = array[i];
	     array[i] = array[j];
	     array[j] = temp;
	 }
	
	 // perform comb sort to array
	 static void combsort(int [] array){
	     if (array == null || array.length == 0) {
	    	 throw new IllegalArgumentException("Empty array cannot be sorted !!!");
	     }
	
	     // initialize gap size equal to size of array
	     int n = array.length;
	     int gap = n;
	     
	     // initialize swapped as true to make sure loop can runs
	     boolean swapped = true;
	
	     // Keep running until gap !=1 and no iteraction caused swapped
	     do {
	         gap = count_gap(gap);
	
	         // initialize swapped as false to check whether swapped happened
	         swapped = false;
	
	         // iterate and compare all elements
	         for (int i=0; i < n-gap; i++) { 
	
	             if (array [i] > array [i+gap]){
	                 
	                 // swap elements and arranged in sorted order
	                 swap(array, i, i + gap);
	                 swapped = true;
	             }
	         }
	     } while (gap != 1 || swapped == true);
	 }

	 public static void main(String[] args) {
	     int[] array = {49, 11, 24, 44, 29, 27, 2 ,22};
	     CombSort sorter = new CombSort();
	     sorter.sort(array);
	     System.out.println(java.util.Arrays.toString(array));
	 }
}