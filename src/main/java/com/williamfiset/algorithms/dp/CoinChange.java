/**
 * The coin change problem is an unbounded knapsack problem variant. The problem asks you to find
 * the minimum number of coins required for a certain amount of change given the coin denominations.
 * You may use each coin denomination as many times as you please.
 *
 * <p>Tested against: https://leetcode.com/problems/coin-change/
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.dp;

public class CoinChange {

  private static final int INF = 987654321;

  public static int coinChange(int[] coins, int amount) {

    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (coins.length == 0) throw new IllegalArgumentException("No coin values :/");

    final int N = coins.length;
    // Initialize table and set first row to be infinity
    int[][] DP = new int[N + 1][amount + 1];
    java.util.Arrays.fill(DP[0], INF);
    DP[1][0] = 0;

    // Iterate through all the coins
    for (int i = 1; i <= N; i++) {

      int coinValue = coins[i - 1];
      for (int j = 1; j <= amount; j++) {

        // Consider not selecting this coin
        DP[i][j] = DP[i - 1][j];

        // Try selecting this coin if it's better
        if (j - coinValue >= 0 && DP[i][j - coinValue] + 1 < DP[i][j])
          DP[i][j] = DP[i][j - coinValue] + 1;
      }
    }

    // The amount we wanted to make cannot be made :/
    if (DP[N][amount] == INF) return -1;

    // Return the minimum number of coins needed
    return DP[N][amount];
  }

  public static int coinChangeSpaceEfficient(int[] coins, int amount) {

    if (coins == null) throw new IllegalArgumentException("Coins array is null");

    // Initialize table and set everything to infinity except first cell
    int[] DP = new int[amount + 1];
    java.util.Arrays.fill(DP, INF);
    DP[0] = 0;

    for (int i = 1; i <= amount; i++)
      for (int coinValue : coins)
        if (i - coinValue >= 0 && DP[i - coinValue] + 1 < DP[i]) DP[i] = DP[i - coinValue] + 1;

    // The amount we wanted to make cannot be made :/
    if (DP[amount] == INF) return -1;

    // Return the minimum number of coins needed
    return DP[amount];
  }

  // The recursive approach has the advantage that it does not have to visit
  // all possible states like the tabular approach does. This can speedup
  // things especially if the coin denominations are large.
  public static int coinChangeRecursive(int[] coins, int amount) {

    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (amount < 0) return -1;

    int[] DP = new int[amount + 1];
    return coinChangeRecursive(amount, coins, DP);
  }

  // Private helper method to actually go the recursion
  private static int coinChangeRecursive(int amount, int[] coins, int[] DP) {

    // Base cases.
    if (amount < 0) return -1;
    if (amount == 0) return 0;
    if (DP[amount] != 0) return DP[amount];

    int minCoins = INF;
    for (int coinValue : coins) {

      int newAmount = amount - coinValue;
      int value = coinChangeRecursive(newAmount, coins, DP);
      if (value != -1 && value < minCoins) minCoins = value + 1;
    }

    // If we weren't able to find some coins to make our
    // amount then cache -1 as the answer.
    return DP[amount] = (minCoins == INF) ? -1 : minCoins;
  }

  public static void main(String[] args) {
    int[] coins = {2, 6, 1};
    System.out.println(coinChange(coins, 17));
    System.out.println(coinChangeSpaceEfficient(coins, 17));
    System.out.println(coinChangeRecursive(coins, 17));
  }
}
