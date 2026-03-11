package com.williamfiset.algorithms.dp;

/**
 * Longest Common Subsequence (LCS)
 *
 * Given two strings A and B, find the longest subsequence present in both.
 * A subsequence is a sequence that appears in the same relative order but
 * not necessarily contiguously (unlike a substring).
 *
 * Builds an (n+1) x (m+1) DP table where dp[i][j] = length of the LCS of
 * A[0..i-1] and B[0..j-1], then backtracks to recover one LCS string.
 *
 * Tested against: https://leetcode.com/problems/longest-common-subsequence
 *
 * Time:  O(n*m)
 * Space: O(n*m)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class LongestCommonSubsequence {

  /**
   * Finds one Longest Common Subsequence between A and B.
   *
   * @param A - first string
   * @param B - second string
   * @return one LCS string, or null if either input is null
   */
  public static String lcs(String A, String B) {
    if (A == null || B == null) return null;
    return lcs(A.toCharArray(), B.toCharArray());
  }

  /**
   * Finds one Longest Common Subsequence between A and B using bottom-up DP.
   *
   * Builds a table dp[i][j] = length of LCS of A[0..i-1] and B[0..j-1],
   * then backtracks through the table to reconstruct the actual subsequence.
   *
   * @param A - first character array
   * @param B - second character array
   * @return one LCS string, or null if either input is null
   *
   * Time:  O(n*m)
   * Space: O(n*m)
   */
  public static String lcs(char[] A, char[] B) {
    if (A == null || B == null) return null;

    final int n = A.length;
    final int m = B.length;

    if (n == 0 || m == 0) return "";

    int[][] dp = new int[n + 1][m + 1];

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        // If characters match, extend the LCS from the diagonal
        if (A[i - 1] == B[j - 1])
          dp[i][j] = dp[i - 1][j - 1] + 1;
        // Otherwise take the best LCS excluding one character from either string
        else
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
      }
    }

    // Backtrack to reconstruct the LCS string
    StringBuilder sb = new StringBuilder();
    int i = n, j = m;

    while (i > 0 && j > 0) {
      if (A[i - 1] == B[j - 1]) {
        sb.append(A[i - 1]);
        i--;
        j--;
      } else if (dp[i - 1][j] >= dp[i][j - 1]) {
        i--;
      } else {
        j--;
      }
    }

    return sb.reverse().toString();
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    // LCS: ABC
    System.out.println("LCS: " + lcs("AXBCY", "ZAYWBC"));

    // LCS: 339970
    System.out.println("LCS: " + lcs("398397970", "3399917206"));
  }
}
