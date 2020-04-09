package com.williamfiset.algorithms.sorting;

public class RadixSort {

  public static int getMax(int[] array) {
    int max = array[0];
    for (int i = 0; i < array.length; i++) {
      if (array[i] > max) {
        max = array[i];
      }
    }
    return max;
  }

  public static void radixSort(int[] ar) {
    int bucket[][] = new int[10][10];
    int bucket_count[] = new int[10];
    int i, j, k, remainder, numDig = 0, divisor = 1, largest, passNo;
    largest = getMax(ar);
    while (largest > 0) {
      numDig++;
      largest /= 10;
    }
    for (passNo = 0; passNo < numDig; passNo++) // Initialize the buckets
    {
      for (i = 0; i < 10; i++) bucket_count[i] = 0;
      for (i = 0; i < 10; i++) {
        // sort the numbers according to the digit at passNo place
        remainder = (ar[i] / divisor) % 10;
        bucket[remainder][bucket_count[remainder]] = ar[i];
        bucket_count[remainder] += 1;
      }
      // collect the numbers after passNo pass
      i = 0;
      for (k = 0; k < 10; k++) {
        for (j = 0; j < bucket_count[k]; j++) {
          ar[i] = bucket[k][j];
          i++;
        }
      }
      divisor *= 10;
    }
  }
}
