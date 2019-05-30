/**
 * This file contains a dynamic programming solutions to the classic unbounded knapsack problem
 * where are you are trying to maximize the total profit of items selected without exceeding the
 * capacity of your knapsack.
 *
 * <p>Version 1: Time Complexity: O(nW) Version 1 Space Complexity: O(nW)
 *
 * <p>Version 2: Time Complexity: O(nW) Space Complexity: O(W)
 *
 * <p>Tested code against: https://www.hackerrank.com/challenges/unbounded-knapsack
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class KnapsackUnbounded {

  /**
   * @param maxWeight - The maximum weight of the knapsack
   * @param W - The weights of the items
   * @param V - The values of the items
   * @return The maximum achievable profit of selecting a subset of the elements such that the
   *     capacity of the knapsack is not exceeded
   */
  public static int unboundedKnapsack(int maxWeight, int[] W, int[] V) {

    if (W == null || V == null || W.length != V.length || maxWeight < 0)
      throw new IllegalArgumentException("Invalid input");

    final int N = W.length;

    // Initialize a table where individual rows represent items
    // and columns represent the weight of the knapsack
    int[][] DP = new int[N + 1][maxWeight + 1];

    // Loop through items
    for (int i = 1; i <= N; i++) {

      // Get the value and weight of the item
      int w = W[i - 1], v = V[i - 1];

      // Consider all possible knapsack sizes
      for (int sz = 1; sz <= maxWeight; sz++) {

        // Try including the current element
        if (sz >= w) DP[i][sz] = DP[i][sz - w] + v;

        // Check if not selecting this item at all is more profitable
        if (DP[i - 1][sz] > DP[i][sz]) DP[i][sz] = DP[i - 1][sz];
      }
    }

    // Return the best value achievable
    return DP[N][maxWeight];
  }

  public static int unboundedKnapsackSpaceEfficient(int maxWeight, int[] W, int[] V) {

    if (W == null || V == null || W.length != V.length)
      throw new IllegalArgumentException("Invalid input");

    final int N = W.length;

    // Initialize a table where we will only keep track of
    // the best possible value for each knapsack weight
    int[] DP = new int[maxWeight + 1];

    // Consider all possible knapsack sizes
    for (int sz = 1; sz <= maxWeight; sz++) {

      // Loop through items
      for (int i = 0; i < N; i++) {

        // First check that we can include this item (we can't include it if
        // it's too heavy for our knapsack). Assumming it fits inside the
        // knapsack check if including this element would be profitable.
        if (sz - W[i] >= 0 && DP[sz - W[i]] + V[i] > DP[sz]) DP[sz] = DP[sz - W[i]] + V[i];
      }
    }

    // Return the best value achievable
    return DP[maxWeight];
  }

  public static void main(String[] args) {

    int[] W = {3, 6, 2};
    int[] V = {5, 20, 3};
    int knapsackValue = unboundedKnapsackSpaceEfficient(10, W, V);
    System.out.println("Maximum knapsack value: " + knapsackValue);
  }
}
