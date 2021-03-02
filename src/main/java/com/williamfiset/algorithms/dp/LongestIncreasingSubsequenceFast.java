package com.williamfiset.algorithms.dp;

public class LongestIncreasingSubsequenceFast {
  public static void main(String[] args) {

    System.out.println(lis(new int[] {1, 3, 2, 4, 3})); // 3
    System.out.println(lis(new int[] {2, 7, 4, 3, 8})); // 3
    System.out.println(lis(new int[] {5, 4, 3, 2, 1})); // 1
    System.out.println(lis(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9})); // 9
    System.out.println(lis(new int[] {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15})); // 6
  }

  public static int binarySearch(int[] ar, int[] c, int i, int sz) {
    int mid, lo = 1, hi = sz;
    while (lo <= hi) {
      mid = (int) ((lo + hi) / 2);
      if (c[mid] < ar[i]) {
        lo = mid + 1;
      }	else {
        hi = mid - 1;
      }
    }
    //System.out.println("bs " + lo);
    return lo;
  }

  // Finds the length of the longest increasing subsequence length, O(n^2)
  public static int lis(int[] ar) {

    if (ar == null || ar.length == 0) return 0;
    int n = ar.length, len = 0, sz = 1;

    // When starting, each individual element has a LIS
    // of exactly one, so each index is initialized to 1
    int[] dp = new int[n];
    int[] c = new int[n + 1];
    c[1] = ar[0];
    dp[0] = 1;
    java.util.Arrays.fill(dp, 1);
    java.util.Arrays.fill(c, ar[0]);

    // Processing the array left to right update the value of dp[j] if two
    // conditions hold 1) The value at i is less than that of the one at j
    // and 2) updating the value of dp[j] to dp[i]+1 is better
    for (int i = 1; i < n; i++) {
      if (ar[i] < c[1]) {
        c[1] = ar[i]; /*you have to update the minimum value right now*/
        dp[i] = 1;
      } else if (ar[i] > c[sz]) {
        c[sz + 1] = ar[i];
        dp[i] = sz + 1;
        sz++;
      } else {
        int k = binarySearch(ar, c, i, sz); /*you want to find k so that c[k-1]<arr[i]<c[k]*/
        c[k] = ar[i];
        dp[i] = k;
      }
      if (dp[i] > len) len = dp[i];
    }

    return len;
  }
}
