/**
 * A solution to the edit distance problem
 *
 * <p>Tested against: https://leetcode.com/problems/edit-distance
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class EditDistanceRecursive {

  final char[] a, b;
  final int insertionCost, deletionCost, substitutionCost;

  public EditDistanceRecursive(
      String a, String b, int insertionCost, int deletionCost, int substitutionCost) {
    if (a == null || b == null) {
      throw new IllegalArgumentException("Input string must not be null");
    }
    this.a = a.toCharArray();
    this.b = b.toCharArray();
    this.insertionCost = insertionCost;
    this.deletionCost = deletionCost;
    this.substitutionCost = substitutionCost;
  }

  private static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values) {
      if (v < m) {
        m = v;
      }
    }
    return m;
  }

  // Returns the Levenshtein distance to transform string `a` into string `b`.
  public int editDistance() {
    Integer[][] dp = new Integer[a.length + 1][b.length + 1];
    return f(dp, 0, 0);
  }

  private int f(Integer[][] dp, int i, int j) {
    if (i == a.length && j == b.length) {
      return 0;
    }
    if (i == a.length) {
      return (b.length - j) * insertionCost;
    }
    if (j == b.length) {
      return (a.length - i) * deletionCost;
    }
    if (dp[i][j] != null) {
      return dp[i][j];
    }
    int substituteOrSkip = f(dp, i + 1, j + 1) + (a[i] == b[j] ? 0 : substitutionCost);
    int delete = f(dp, i + 1, j) + deletionCost;
    int insert = f(dp, i, j + 1) + insertionCost;
    return dp[i][j] = min(substituteOrSkip, delete, insert);
  }

  public static void main(String[] args) {
    String a = "923456789";
    String b = "12345";
    EditDistanceRecursive solver = new EditDistanceRecursive(a, b, 100, 4, 2);
    System.out.println(solver.editDistance());

    a = "12345";
    b = "923456789";
    solver = new EditDistanceRecursive(a, b, 100, 4, 2);
    System.out.println(solver.editDistance());

    a = "aaa";
    b = "aaabbb";
    solver = new EditDistanceRecursive(a, b, 10, 2, 3);
    System.out.println(solver.editDistance());

    a = "1023";
    b = "10101010";
    solver = new EditDistanceRecursive(a, b, 5, 7, 2);
    System.out.println(solver.editDistance());

    a = "923456789";
    b = "12345";
    EditDistanceRecursive solver2 = new EditDistanceRecursive(a, b, 100, 4, 2);
    System.out.println(solver2.editDistance());

    a = "aaaaa";
    b = "aabaa";
    solver = new EditDistanceRecursive(a, b, 2, 3, 10);
    System.out.println(solver.editDistance());
  }
}
