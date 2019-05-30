/**
 * Implementation of finding the longest paldindrome subsequence Time complexity: O(n^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class LongestPalindromeSubsequence {

  public static void main(String[] args) {
    System.out.println(lps("bbbab")); // Outputs 4 since "bbbb" is valid soln
    System.out.println(lps("bccd")); // Outputs 2 since "cc" is valid soln
  }

  // Returns the length of the longest paldindrome subsequence
  public static int lps(String s) {
    if (s == null || s.length() == 0) return 0;
    Integer[][] dp = new Integer[s.length()][s.length()];
    return lps(s, dp, 0, s.length() - 1);
  }

  // Private recursive method with memoization to count
  // the longest paldindrome subsequence.
  private static int lps(String s, Integer[][] dp, int i, int j) {

    // Base cases
    if (j < i) return 0;
    if (i == j) return 1;
    if (dp[i][j] != null) return dp[i][j];

    char c1 = s.charAt(i), c2 = s.charAt(j);

    // Both end characters match
    if (c1 == c2) return dp[i][j] = lps(s, dp, i + 1, j - 1) + 2;

    // Consider both possible substrings and take the maximum
    return dp[i][j] = Math.max(lps(s, dp, i + 1, j), lps(s, dp, i, j - 1));
  }
}
