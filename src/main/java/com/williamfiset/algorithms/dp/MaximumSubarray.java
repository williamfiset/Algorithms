package com.williamfiset.algorithms.dp;

/**
 * Maximum Subarray Problem (Kadane's Algorithm)
 *
 * Given an integer array, find the contiguous subarray with the largest sum.
 * Kadane's algorithm solves this in a single pass by maintaining the best
 * sum ending at the current position:
 *
 *   sum[i] = max(arr[i], sum[i-1] + arr[i])
 *
 * At each index, we either extend the current subarray or start a new one
 * from the current element — whichever gives the larger sum. The global
 * maximum across all positions is the answer.
 *
 * Tested against: https://leetcode.com/problems/maximum-subarray
 *
 * Time:  O(n)
 * Space: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class MaximumSubarray {

  /**
   * Returns the sum of the maximum contiguous subarray.
   *
   * @param arr the input array (must be non-null and non-empty)
   * @return the maximum subarray sum
   *
   * Time:  O(n)
   * Space: O(1)
   */
  public static long maximumSubarrayValue(int[] arr) {
    if (arr == null || arr.length == 0)
      throw new IllegalArgumentException("Array must not be null or empty");

    long maxValue = arr[0];
    long sum = arr[0];

    for (int i = 1; i < arr.length; i++) {
      // Either start a new subarray at arr[i], or extend the current one
      sum = Math.max(arr[i], sum + arr[i]);

      if (sum > maxValue)
        maxValue = sum;
    }

    return maxValue;
  }

  public static void main(String[] args) {
    // Single negative element
    System.out.println(maximumSubarrayValue(new int[] {-5})); // -5

    // All negative: best subarray is the largest single element (-1)
    System.out.println(
        maximumSubarrayValue(new int[] {-5, -4, -10, -3, -1, -12, -6})); // -1

    // Mixed: subarray [2, -1, 40] has sum 41
    System.out.println(
        maximumSubarrayValue(new int[] {1, 2, 1, -7, 2, -1, 40, -89})); // 41
  }
}
