package com.williamfiset.algorithms.dp;

/**
 * Longest Common Subsequence (LCS)
 *
 * Given two strings A and B, find the longest subsequence present in both.
 * A subsequence is a sequence that appears in the same relative order but
 * not necessarily contiguously (unlike a substring).
 *
 * Two implementations are provided:
 *   1. Iterative (bottom-up DP) — builds an (n+1) x (m+1) table, then
 *      backtracks to recover one LCS. See {@link #lcsIterative(char[], char[])}.
 *   2. Recursive (top-down DP with memoization) — explores subproblems on
 *      demand and caches results. See {@link #lcsRecursive(char[], char[])}.
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
   * Defaults to the iterative implementation.
   *
   * @param A - first string
   * @param B - second string
   * @return one LCS string, or null if either input is null
   */
  public static String lcs(String A, String B) {
    if (A == null || B == null) return null;
    return lcsIterative(A.toCharArray(), B.toCharArray());
  }

  /**
   * Finds one Longest Common Subsequence between A and B.
   * Defaults to the iterative implementation.
   *
   * @param A - first character array
   * @param B - second character array
   * @return one LCS string, or null if either input is null
   */
  public static String lcs(char[] A, char[] B) {
    return lcsIterative(A, B);
  }

  // ==================== Implementation 1: Iterative (bottom-up) ====================

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
  public static String lcsIterative(char[] A, char[] B) {
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
    int lcsLen = dp[n][m];
    char[] lcs = new char[lcsLen];
    int index = 0;
    int i = n, j = m;

    while (i >= 1 && j >= 1) {
      int v = dp[i][j];

      // Walk up/left while the value doesn't change — these cells
      // did not contribute a character to the LCS
      while (i > 1 && dp[i - 1][j] == v)
        i--;
      while (j > 1 && dp[i][j - 1] == v)
        j--;

      if (v > 0)
        lcs[lcsLen - index++ - 1] = A[i - 1];

      i--;
      j--;
    }

    return new String(lcs, 0, lcsLen);
  }

  // ==================== Implementation 2: Recursive (top-down with memoization) ====================

  /**
   * Finds one Longest Common Subsequence between A and B using top-down DP
   * with memoization.
   *
   * Recursively computes the LCS length, caching results in a memo table,
   * then backtracks through the memo to reconstruct the subsequence.
   *
   * @param A - first character array
   * @param B - second character array
   * @return one LCS string, or null if either input is null
   *
   * Time:  O(n*m)
   * Space: O(n*m)
   */
  public static String lcsRecursive(char[] A, char[] B) {
    if (A == null || B == null) return null;

    final int n = A.length;
    final int m = B.length;

    if (n == 0 || m == 0) return "";

    // Use Integer[][] so we can distinguish "not computed" (null) from 0
    Integer[][] memo = new Integer[n][m];
    int lcsLen = lcsHelper(A, B, n - 1, m - 1, memo);

    // Backtrack through the memo table to reconstruct the LCS
    char[] lcs = new char[lcsLen];
    int index = lcsLen - 1;
    int i = n - 1, j = m - 1;

    while (i >= 0 && j >= 0) {
      if (A[i] == B[j]) {
        // This character is part of the LCS
        lcs[index--] = A[i];
        i--;
        j--;
      } else if (i > 0 && memo[i - 1][j] != null && (j == 0 || memo[i - 1][j] >= getMemo(memo, i, j - 1))) {
        // Moving up gives a longer or equal LCS
        i--;
      } else {
        j--;
      }
    }

    return new String(lcs, 0, lcsLen);
  }

  /**
   * Recursively computes the LCS length of A[0..i] and B[0..j].
   */
  private static int lcsHelper(char[] A, char[] B, int i, int j, Integer[][] memo) {
    if (i < 0 || j < 0) return 0;
    if (memo[i][j] != null) return memo[i][j];

    if (A[i] == B[j])
      memo[i][j] = 1 + lcsHelper(A, B, i - 1, j - 1, memo);
    else
      memo[i][j] = Math.max(lcsHelper(A, B, i - 1, j, memo), lcsHelper(A, B, i, j - 1, memo));

    return memo[i][j];
  }

  /** Safe memo lookup that returns 0 for out-of-bounds indices. */
  private static int getMemo(Integer[][] memo, int i, int j) {
    if (i < 0 || j < 0) return 0;
    return memo[i][j] != null ? memo[i][j] : 0;
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    String s1 = "AXBCY";
    String s2 = "ZAYWBC";

    // LCS: ABC
    System.out.println("LCS: " + lcs(s1, s2));

    s1 = "398397970";
    s2 = "3399917206";

    // LCS: 339970
    System.out.println("LCS Iterative: " + lcsIterative(s1.toCharArray(), s2.toCharArray()));
    System.out.println("LCS Recursive: " + lcsRecursive(s1.toCharArray(), s2.toCharArray()));
  }
}
