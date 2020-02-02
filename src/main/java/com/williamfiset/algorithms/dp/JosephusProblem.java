/**
 * An implementation of the Josephus problem Time complexity: O(n)
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.dp;

public class JosephusProblem {

  // Suppose there are n people in a circle and person
  // 0 kill the k'th person, then the k'th person kills
  // the 2k'th person and so on until only one person remains.
  // The question is who lives?
  // Let n be the number of people and k the hop size
  public static int josephus(int n, int k) {
    int[] dp = new int[n];
    for (int i = 1; i < n; i++) dp[i] = (dp[i - 1] + k) % (i + 1);
    return dp[n - 1];
  }

  public static void main(String[] args) {

    int n = 41, k = 2;
    System.out.println(josephus(n, k));

    n = 25;
    k = 18;
    System.out.println(josephus(n, k));

    n = 5;
    k = 2;
    System.out.println(josephus(n, k));
  }
}
