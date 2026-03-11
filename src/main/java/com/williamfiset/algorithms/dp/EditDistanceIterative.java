package com.williamfiset.algorithms.dp;

/**
 * Edit Distance (Levenshtein Distance) — Iterative Bottom-Up DP
 *
 * Computes the minimum cost to transform string `a` into string `b` using
 * three operations, each with a configurable cost:
 *
 *   - Insert a character into `a`   (cost: insertionCost)
 *   - Delete a character from `a`   (cost: deletionCost)
 *   - Substitute a character in `a` (cost: substitutionCost, 0 if chars match)
 *
 * The DP table dp[i][j] represents the cost of converting the first i
 * characters of `a` into the first j characters of `b`. Each cell is
 * computed from three neighbors: diagonal (substitute/match), above (delete),
 * and left (insert).
 *
 * See also: EditDistanceRecursive for a top-down memoized approach.
 *
 * Tested against: https://leetcode.com/problems/edit-distance
 *
 * Time:  O(n*m) where n = a.length(), m = b.length()
 * Space: O(n*m)
 *
 * @author Micah Stairs
 */
public class EditDistanceIterative {

  /**
   * Computes the minimum cost to convert string `a` into string `b`.
   *
   * @param a                the source string
   * @param b                the target string
   * @param insertionCost    cost of inserting one character
   * @param deletionCost     cost of deleting one character
   * @param substitutionCost cost of substituting one character (0 cost if chars already match)
   * @return the minimum edit distance
   *
   * Time:  O(n*m)
   * Space: O(n*m)
   */
  public static int editDistance(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {
    if (a == null || b == null) throw new IllegalArgumentException("Input strings must not be null");

    final int n = a.length(), m = b.length();
    int[][] dp = new int[n + 1][m + 1];

    // Base cases: transforming a prefix of `a` into empty string (deletions only)
    for (int i = 1; i <= n; i++)
      dp[i][0] = i * deletionCost;

    // Base cases: transforming empty string into a prefix of `b` (insertions only)
    for (int j = 1; j <= m; j++)
      dp[0][j] = j * insertionCost;

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        // If characters match, no substitution cost; otherwise pay substitutionCost
        int substitute = dp[i - 1][j - 1]
            + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : substitutionCost);
        int delete = dp[i - 1][j] + deletionCost;
        int insert = dp[i][j - 1] + insertionCost;
        dp[i][j] = Math.min(substitute, Math.min(delete, insert));
      }
    }

    return dp[n][m];
  }

  public static void main(String[] args) {
    // Identical strings — cost is 0
    System.out.println(editDistance("abcdefg", "abcdefg", 10, 10, 10)); // 0

    // 3 insertions at cost 10 each = 30
    System.out.println(editDistance("aaa", "aaabbb", 10, 2, 3)); // 30

    // 2 substitutions (cost 2) + 4 insertions (cost 5) = 24
    System.out.println(editDistance("1023", "10101010", 5, 7, 2)); // 24

    // 1 substitution (cost 1) + 4 deletions (cost 4) = 17
    System.out.println(editDistance("923456789", "12345", 2, 4, 1)); // 17

    // Insert 'b' then delete 'a' is cheaper than substituting 'a'->'b'
    System.out.println(editDistance("aaaaa", "aabaa", 2, 3, 10)); // 5
  }
}
