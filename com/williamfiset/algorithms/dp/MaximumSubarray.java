/**
 * This file shows you how to find the maximal subarray in an integer array Time complexity: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class MaximumSubarray {

  public static void main(String[] args) {
    System.out.println(maximumSubarrayValue(new int[] {-5}));
    System.out.println(maximumSubarrayValue(new int[] {-5, -4, -10, -3, -1, -12, -6}));
    System.out.println(maximumSubarrayValue(new int[] {1, 2, 1, -7, 2, -1, 40, -89}));
  }

  // Return the value of the maximum subarray in 'ar'
  public static long maximumSubarrayValue(int[] ar) {

    if (ar == null || ar.length == 0) return 0L;
    int n = ar.length, maxValue, sum;

    maxValue = sum = ar[0];

    for (int i = 1; i < n; i++) {

      // At each step consider continuing the current subarray
      // or starting a new one because adding the next element
      // doesn't acutally make the subarray sum any better.
      if (ar[i] > sum + ar[i]) sum = ar[i];
      else sum = sum + ar[i];

      if (sum > maxValue) maxValue = sum;
    }

    return maxValue;
  }
}
