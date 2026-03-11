/**
 * Longest Palindrome Subsequence (LPS)
 *
 * Given a string S, find the length of the longest subsequence in S
 * that is also a palindrome.
 *
 * Time Complexity: O(n^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class LongestPalindromeSubsequence {

  /**
   * Returns the length of the longest palindrome subsequence.
   * Defaults to the recursive implementation with memoization.
   */
  public static int lps(String s) {
    return lpsRecursive(s);
  }

  /**
   * Recursive implementation with memoization to find the length of
   * the longest palindrome subsequence.
   *
   * Time Complexity: O(n^2)
   * Space Complexity: O(n^2)
   */
  public static int lpsRecursive(String s) {
    if (s == null || s.length() == 0) return 0;
    Integer[][] dp = new Integer[s.length()][s.length()];
    return lpsRecursive(s, dp, 0, s.length() - 1);
  }

  private static int lpsRecursive(String s, Integer[][] dp, int i, int j) {
    if (j < i) return 0;
    if (i == j) return 1;
    if (dp[i][j] != null) return dp[i][j];

    if (s.charAt(i) == s.charAt(j)) {
      return dp[i][j] = lpsRecursive(s, dp, i + 1, j - 1) + 2;
    }
    return dp[i][j] = Math.max(lpsRecursive(s, dp, i + 1, j), lpsRecursive(s, dp, i, j - 1));
  }

  /**
   * Iterative implementation (bottom-up) to find the length of
   * the longest palindrome subsequence.
   *
   * Time Complexity: O(n^2)
   * Space Complexity: O(n^2)
   */
  public static int lpsIterative(String s) {
    if (s == null || s.isEmpty()) return 0;
    int n = s.length();
    int[][] dp = new int[n][n];

    // Every single character is a palindrome of length 1
    for (int i = 0; i < n; i++) dp[i][i] = 1;

    for (int len = 2; len <= n; len++) {
      for (int i = 0; i <= n - len; i++) {
        int j = i + len - 1;
        if (s.charAt(i) == s.charAt(j)) {
          dp[i][j] = dp[i + 1][j - 1] + 2;
        } else {
          dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
        }
      }
    }
    return dp[0][n - 1];
  }

  public static void main(String[] args) {
    String s1 = "bbbab";
    System.out.println(lps(s1)); // 4
    System.out.println(lpsIterative(s1)); // 4

    String s2 = "bccd";
    System.out.println(lps(s2)); // 2
    System.out.println(lpsIterative(s2)); // 2
  }
}
