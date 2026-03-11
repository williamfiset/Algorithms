package com.williamfiset.algorithms.dp;

/**
 * Edit Distance (Levenshtein Distance) — Top-Down Recursive with Memoization
 *
 * Computes the minimum cost to transform string `a` into string `b` using
 * three operations, each with a configurable cost:
 *
 *   - Insert a character into `a`   (cost: insertionCost)
 *   - Delete a character from `a`   (cost: deletionCost)
 *   - Substitute a character in `a` (cost: substitutionCost, 0 if chars match)
 *
 * The recursive function f(i, j) returns the cost of converting a[i..] into
 * b[j..]. At each step it considers three choices — substitute/match, delete,
 * insert — and memoizes results in a 2D table.
 *
 * Compared to EditDistanceIterative, the recursive approach only visits
 * reachable states, which can be faster when many states are unreachable.
 *
 * Tested against: https://leetcode.com/problems/edit-distance
 *
 * Time:  O(n*m) where n = a.length(), m = b.length()
 * Space: O(n*m)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class EditDistanceRecursive {

  private final char[] a, b;
  private final int insertionCost, deletionCost, substitutionCost;

  /**
   * Creates an edit distance solver for the given strings and operation costs.
   *
   * @param a                the source string
   * @param b                the target string
   * @param insertionCost    cost of inserting one character
   * @param deletionCost     cost of deleting one character
   * @param substitutionCost cost of substituting one character (0 cost if chars already match)
   */
  public EditDistanceRecursive(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Input strings must not be null");
    }
    this.a = a.toCharArray();
    this.b = b.toCharArray();
    this.insertionCost = insertionCost;
    this.deletionCost = deletionCost;
    this.substitutionCost = substitutionCost;
  }

  /**
   * Computes and returns the minimum edit distance from `a` to `b`.
   *
   * Time:  O(n*m)
   * Space: O(n*m)
   */
  public int editDistance() {
    Integer[][] dp = new Integer[a.length + 1][b.length + 1];
    return f(dp, 0, 0);
  }

  /**
   * Recursive helper: returns the min cost to convert a[i..] into b[j..].
   */
  private int f(Integer[][] dp, int i, int j) {
    // Both strings fully consumed — nothing left to do
    if (i == a.length && j == b.length) return 0;

    // Remaining characters in `b` must be inserted
    if (i == a.length) return (b.length - j) * insertionCost;

    // Remaining characters in `a` must be deleted
    if (j == b.length) return (a.length - i) * deletionCost;

    if (dp[i][j] != null) return dp[i][j];

    // Match (free) or substitute, then advance both pointers
    int substitute = f(dp, i + 1, j + 1) + (a[i] == b[j] ? 0 : substitutionCost);

    // Delete a[i], advance i only
    int delete = f(dp, i + 1, j) + deletionCost;

    // Insert b[j] into a, advance j only
    int insert = f(dp, i, j + 1) + insertionCost;

    return dp[i][j] = Math.min(substitute, Math.min(delete, insert));
  }

  public static void main(String[] args) {
    // 1 substitution (cost 2) + 4 deletions (cost 4) = 18
    System.out.println(
        new EditDistanceRecursive("923456789", "12345", 100, 4, 2).editDistance()); // 18

    // Reverse direction: 1 substitution (cost 2) + 4 insertions (cost 100) = 402
    System.out.println(
        new EditDistanceRecursive("12345", "923456789", 100, 4, 2).editDistance()); // 402

    // 3 insertions at cost 10 each = 30
    System.out.println(
        new EditDistanceRecursive("aaa", "aaabbb", 10, 2, 3).editDistance()); // 30

    // 2 substitutions (cost 2) + 4 insertions (cost 5) = 24
    System.out.println(
        new EditDistanceRecursive("1023", "10101010", 5, 7, 2).editDistance()); // 24

    // Insert 'b' then delete 'a' is cheaper than substituting 'a'->'b'
    System.out.println(
        new EditDistanceRecursive("aaaaa", "aabaa", 2, 3, 10).editDistance()); // 5
  }
}
