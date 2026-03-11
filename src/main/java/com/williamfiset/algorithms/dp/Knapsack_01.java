package com.williamfiset.algorithms.dp;

import java.util.LinkedList;
import java.util.List;

/**
 * 0/1 Knapsack Problem — Bottom-Up Dynamic Programming
 *
 * Given n items, each with a weight and a value, determine the maximum total
 * value that can be placed in a knapsack of a given capacity. Each item may
 * be selected at most once (hence "0/1").
 *
 * The DP table dp[i][sz] represents the maximum value achievable using the
 * first i items with a knapsack capacity of sz. For each item we either:
 *   - Skip it:    dp[i][sz] = dp[i-1][sz]
 *   - Include it: dp[i][sz] = dp[i-1][sz - w] + v  (if it fits)
 *
 * After filling the table, we backtrack to recover which items were selected:
 * if dp[i][sz] != dp[i-1][sz], then item i was included.
 *
 * See also: KnapsackUnbounded for the variant where items can be reused.
 *
 * Tested against: https://open.kattis.com/problems/knapsack
 *
 * Time:  O(n*W) where n = number of items, W = capacity
 * Space: O(n*W)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class Knapsack_01 {

  /**
   * Computes the maximum value achievable without exceeding the knapsack capacity.
   *
   * @param capacity the maximum weight the knapsack can hold
   * @param W        array of item weights
   * @param V        array of item values
   * @return the maximum total value
   *
   * Time:  O(n*W)
   * Space: O(n*W)
   */
  public static int knapsack(int capacity, int[] W, int[] V) {
    if (W == null || V == null || W.length != V.length || capacity < 0)
      throw new IllegalArgumentException("Invalid input");

    final int n = W.length;

    // dp[i][sz] = max value using first i items with capacity sz
    int[][] dp = new int[n + 1][capacity + 1];

    for (int i = 1; i <= n; i++) {
      int w = W[i - 1], v = V[i - 1];
      for (int sz = 1; sz <= capacity; sz++) {
        // Option 1: skip this item
        dp[i][sz] = dp[i - 1][sz];

        // Option 2: include this item if it fits and improves the value
        if (sz >= w && dp[i - 1][sz - w] + v > dp[i][sz])
          dp[i][sz] = dp[i - 1][sz - w] + v;
      }
    }

    return dp[n][capacity];
  }

  /**
   * Returns the indices of items selected in the optimal solution.
   *
   * After filling the DP table, we recover the selected items by walking
   * backwards from dp[n][capacity]. At each row i, we check:
   *
   *   - If dp[i][sz] != dp[i-1][sz], then item i-1 contributed to the
   *     optimal value at this capacity, so it was selected. We add it
   *     to the result and reduce the remaining capacity by its weight.
   *
   *   - If dp[i][sz] == dp[i-1][sz], then item i-1 was NOT selected
   *     (the optimal value came from the previous items alone), so we
   *     just move to row i-1.
   *
   * @param capacity the maximum weight the knapsack can hold
   * @param W        array of item weights
   * @param V        array of item values
   * @return list of selected item indices (0-based, in ascending order)
   *
   * Time:  O(n*W)
   * Space: O(n*W)
   */
  public static List<Integer> knapsackItems(int capacity, int[] W, int[] V) {
    if (W == null || V == null || W.length != V.length || capacity < 0)
      throw new IllegalArgumentException("Invalid input");

    final int n = W.length;
    int[][] dp = new int[n + 1][capacity + 1];

    for (int i = 1; i <= n; i++) {
      int w = W[i - 1], v = V[i - 1];
      for (int sz = 1; sz <= capacity; sz++) {
        dp[i][sz] = dp[i - 1][sz];
        if (sz >= w && dp[i - 1][sz - w] + v > dp[i][sz])
          dp[i][sz] = dp[i - 1][sz - w] + v;
      }
    }

    // Backtrack through the table to find which items were selected.
    // Starting at dp[n][capacity], walk backwards row by row:
    //   - dp[i][sz] != dp[i-1][sz] → item i-1 was included, reduce capacity
    //   - dp[i][sz] == dp[i-1][sz] → item i-1 was skipped, move on
    // We walk backwards (high to low index), so inserting at the front
    // of a LinkedList produces ascending order without a separate sort.
    LinkedList<Integer> items = new LinkedList<>();
    int sz = capacity;
    for (int i = n; i > 0; i--) {
      if (dp[i][sz] != dp[i - 1][sz]) {
        items.addFirst(i - 1);
        sz -= W[i - 1];
      }
    }

    return items;
  }

  public static void main(String[] args) {
    // Example 1: capacity=10, items: (w=3,v=1), (w=3,v=4), (w=5,v=8), (w=6,v=5)
    int[] W = {3, 3, 5, 6};
    int[] V = {1, 4, 8, 5};
    System.out.println("Max value: " + knapsack(10, W, V));     // 12
    System.out.println("Items:     " + knapsackItems(10, W, V)); // [1, 2]

    // Example 2: capacity=7, items: (w=3,v=2), (w=1,v=2), (w=3,v=4), (w=4,v=5), (w=2,v=3)
    W = new int[] {3, 1, 3, 4, 2};
    V = new int[] {2, 2, 4, 5, 3};
    System.out.println("Max value: " + knapsack(7, W, V));     // 10
    System.out.println("Items:     " + knapsackItems(7, W, V)); // [1, 3, 4]
  }
}
