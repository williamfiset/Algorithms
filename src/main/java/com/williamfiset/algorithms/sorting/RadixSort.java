package com.williamfiset.algorithms.sorting;

public class RadixSort {
	static int getMax(int[] array) {
		int max = array[0];
	    for (int i = 0; i < array.length; i++) {
	      if (array[i] > max) {
	        max = array[i];
	      }
	    }
	    return max;
    }
	
	static int calculateNumberOfDigits(int number) {
        return (int) Math.log10(number) + 1;
    }

    public static void radixSort(int numbers[]) {
        int maximum = getMax(numbers);
        int numberOfDigits = calculateNumberOfDigits(maximum);
        int placeValue = 1;
        while (numberOfDigits-- > 0) {
            countSort(numbers, placeValue);
            placeValue *= 10;
        }
    }

    private static void countSort(int[] numbers, int placeValue) {
        int range = 10; 
        
        int[] frequency = new int[range];
        int[] sortedValues = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            int digit = (numbers[i] / placeValue) % range;
            frequency[digit]++;
        }

        for (int i = 1; i < range; i++) {
            frequency[i] += frequency[i - 1];
        }

        for (int i = numbers.length - 1; i >= 0; i--) {
            int digit = (numbers[i] / placeValue) % range;
            sortedValues[frequency[digit] - 1] = numbers[i];
            frequency[digit]--;
        }

        System.arraycopy(sortedValues, 0, numbers, 0, numbers.length);
        
    }
    
    

}