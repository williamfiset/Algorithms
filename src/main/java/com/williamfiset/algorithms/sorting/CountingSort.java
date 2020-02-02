/**
 * An implementation of counting sort!
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

public class CountingSort {

  // Sorts values in the range of [minVal, maxVal] in O(n+maxVal-maxVal)
  public static void countingSort(int[] ar, int minVal, int maxVal) {
    int sz = maxVal - minVal + 1;
    int[] B = new int[sz];
    for (int i = 0; i < ar.length; i++) B[ar[i] - minVal]++;
    for (int i = 0, k = 0; i < sz; i++) while (B[i]-- > 0) ar[k++] = i + minVal;
  }

  public static void main(String[] args) {

    // The maximum and minimum values on the numbers we are sorting.
    // You need to know ahead of time the upper and lower bounds on
    // the numbers you are sorting for counting sort to work.
    final int MIN_VAL = -10;
    final int MAX_VAL = +10;

    int[] nums = {+4, -10, +0, +6, +1, -5, -5, +1, +1, -2, 0, +6, +8, -7, +10};
    countingSort(nums, MIN_VAL, MAX_VAL);

    // prints [-10, -7, -5, -5, -2, 0, 0, 1, 1, 1, 4, 6, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(nums));
  }
}
