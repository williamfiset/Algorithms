package com.williamfiset.algorithms.dp;

/**
 * Unbounded Knapsack Problem — Bottom-Up Dynamic Programming
 *
 * Given n items, each with a weight and a value, determine the maximum total
 * value that can be placed in a knapsack of a given capacity. Unlike the 0/1
 * knapsack, each item may be selected unlimited times.
 *
 * Two implementations are provided:
 *
 *   1. unboundedKnapsack()               — 2D DP table, O(n*W) time and space
 *   2. unboundedKnapsackSpaceEfficient() — 1D DP array, O(n*W) time, O(W) space
 *
 * The key difference from 0/1 knapsack is in the recurrence: when including
 * item i, we look at dp[i][sz - w] (same row) instead of dp[i-1][sz - w]
 * (previous row), allowing the item to be selected again.
 *
 * See also: Knapsack_01 for the variant where each item can be used at most once.
 *
 * Tested against: https://www.hackerrank.com/challenges/unbounded-knapsack
 *
 * Time:  O(n*W) where n = number of items, W = capacity
 * Space: O(n*W) or O(W) for the space-efficient version
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class KnapsackUnbounded {

  // ==================== Implementation 1: 2D DP table ====================

  /**
   * Computes the maximum value achievable with unlimited item reuse.
   *
   * dp[i][sz] = max value using items 1..i with capacity sz.
   * When including item i, we reference dp[i][sz-w] (not dp[i-1][sz-w])
   * because the item can be selected again.
   *
   * @param maxWeight the maximum weight the knapsack can hold
   * @param W         array of item weights
   * @param V         array of item values
   * @return the maximum total value
   *
   * Time:  O(n*W)
   * Space: O(n*W)
   */
  public static int unboundedKnapsack(int maxWeight, int[] W, int[] V) {
    if (W == null || V == null || W.length != V.length || maxWeight < 0)
      throw new IllegalArgumentException("Invalid input");

    final int n = W.length;
    int[][] dp = new int[n + 1][maxWeight + 1];

    for (int i = 1; i <= n; i++) {
      int w = W[i - 1], v = V[i - 1];
      for (int sz = 1; sz <= maxWeight; sz++) {
        // Include item i (reuse allowed — look at same row dp[i])
        if (sz >= w)
          dp[i][sz] = dp[i][sz - w] + v;

        // Skip item i if that's more profitable
        if (dp[i - 1][sz] > dp[i][sz])
          dp[i][sz] = dp[i - 1][sz];
      }
    }

    return dp[n][maxWeight];
  }

  // ==================== Implementation 2: Space-efficient 1D DP ====================

  /**
   * Space-efficient version using a single 1D array.
   *
   * dp[sz] = max value achievable with capacity sz using any items.
   * For each capacity, try every item and keep the best.
   *
   * @param maxWeight the maximum weight the knapsack can hold
   * @param W         array of item weights
   * @param V         array of item values
   * @return the maximum total value
   *
   * Time:  O(n*W)
   * Space: O(W)
   */
  public static int unboundedKnapsackSpaceEfficient(int maxWeight, int[] W, int[] V) {
    if (W == null || V == null || W.length != V.length)
      throw new IllegalArgumentException("Invalid input");

    final int n = W.length;
    int[] dp = new int[maxWeight + 1];

    for (int sz = 1; sz <= maxWeight; sz++) {
      for (int i = 0; i < n; i++) {
        // Include item i if it fits and improves the value
        if (sz >= W[i] && dp[sz - W[i]] + V[i] > dp[sz])
          dp[sz] = dp[sz - W[i]] + V[i];
      }
    }

    return dp[maxWeight];
  }

  public static void main(String[] args) {
    int[] W = {3, 6, 2};
    int[] V = {5, 20, 3};

    // Capacity 10: best is (w=6,v=20) + 2x(w=2,v=3) = weight 10, value 26
    System.out.println("2D DP:           " + unboundedKnapsack(10, W, V)); // 26

    // Space-efficient: same result
    System.out.println("Space-efficient: " + unboundedKnapsackSpaceEfficient(10, W, V)); // 26

    // Capacity 12: two items of weight 6 and value 20 = 40
    System.out.println("2D DP (cap=12):  " + unboundedKnapsack(12, W, V)); // 40
    System.out.println("Space (cap=12):  " + unboundedKnapsackSpaceEfficient(12, W, V)); // 40
  }
}
