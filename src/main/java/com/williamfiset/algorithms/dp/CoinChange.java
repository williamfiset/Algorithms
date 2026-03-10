package com.williamfiset.algorithms.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Coin Change Problem (Unbounded Knapsack Variant)
 *
 * Given a set of coin denominations and a target amount, find the minimum
 * number of coins needed to make that amount. Each coin denomination may
 * be used unlimited times.
 *
 * Three implementations are provided:
 *
 *   1. coinChange()               — 2D DP table, O(m*n) time/space, recovers selected coins
 *   2. coinChangeSpaceEfficient() — 1D DP array, O(m*n) time, O(n) space, recovers selected coins
 *   3. coinChangeRecursive()      — top-down with memoization, skips unreachable states
 *
 * Where m = number of coin denominations, n = target amount.
 *
 * Tested against: https://leetcode.com/problems/coin-change
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class CoinChange {

  /**
   * Holds the result of a coin change computation: the minimum number of coins
   * (if a solution exists) and the actual coins selected.
   */
  public static class Solution {
    /** The minimum number of coins to make the target amount, or empty if impossible. */
    Optional<Integer> minCoins = Optional.empty();

    /** The coins selected as part of the optimal solution. */
    List<Integer> selectedCoins = new ArrayList<>();
  }

  // ==================== Implementation 1: 2D DP table ====================

  /**
   * Solves coin change using a 2D DP table.
   *
   * dp[i][j] = minimum coins needed to make amount j using the first i coin types.
   * After computing the table, backtracks to recover which coins were selected.
   *
   * @param coins array of coin denominations (all positive)
   * @param n     the target amount
   * @return a Solution containing the min coin count and selected coins
   *
   * Time:  O(m*n) where m = coins.length
   * Space: O(m*n)
   */
  public static Solution coinChange(int[] coins, final int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (coins.length == 0) throw new IllegalArgumentException("No coin values :/");
    for (int coin : coins) {
      if (coin <= 0) {
        throw new IllegalArgumentException("Coin with value `" + coin + "` is not allowed.");
      }
    }

    final int m = coins.length;

    // dp[i][j] = min coins using first i denominations to make amount j.
    // Row 0 is a sentinel: no coins available, so everything is impossible (null).
    // Column 0 is always 0: it takes 0 coins to make amount 0.
    Integer[][] dp = new Integer[m + 1][n + 1];
    for (int i = 0; i <= m; i++)
      dp[i][0] = 0;

    for (int i = 1; i <= m; i++) {
      int coinValue = coins[i - 1];
      for (int j = 1; j <= n; j++) {

        // Option 1: don't use coin i — carry forward from previous row
        dp[i][j] = dp[i - 1][j];

        // Option 2: use coin i if it fits and yields fewer coins
        if (j - coinValue >= 0 && dp[i][j - coinValue] != null) {
          int withCoin = dp[i][j - coinValue] + 1;
          if (dp[i][j] == null || withCoin < dp[i][j]) {
            dp[i][j] = withCoin;
          }
        }
      }
    }

    Solution solution = new Solution();

    if (dp[m][n] == null) return solution;
    solution.minCoins = Optional.of(dp[m][n]);

    // Backtrack to recover selected coins
    for (int change = n, coinIndex = m; coinIndex > 0; ) {
      int coinValue = coins[coinIndex - 1];
      boolean canSelect = change - coinValue >= 0 && dp[coinIndex][change - coinValue] != null;
      if (canSelect && dp[coinIndex][change - coinValue] < dp[coinIndex][change]) {
        solution.selectedCoins.add(coinValue);
        change -= coinValue;
      } else {
        coinIndex--;
      }
    }

    return solution;
  }

  // ==================== Implementation 2: Space-efficient 1D DP ====================

  /**
   * Solves coin change using a space-efficient 1D DP array.
   *
   * dp[j] = minimum coins needed to make amount j using any denomination.
   * After computing, backtracks greedily to recover selected coins.
   *
   * Compare with coinChange(): same time complexity but uses O(n) space
   * instead of O(m*n) by collapsing the coin dimension.
   *
   * @param coins array of coin denominations (all positive)
   * @param n     the target amount
   * @return a Solution containing the min coin count and selected coins
   *
   * Time:  O(m*n)
   * Space: O(n)
   */
  public static Solution coinChangeSpaceEfficient(int[] coins, int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");

    // dp[j] = min coins to make amount j, null means impossible
    Integer[] dp = new Integer[n + 1];
    dp[0] = 0;

    for (int i = 1; i <= n; i++) {
      for (int coin : coins) {
        if (i - coin >= 0 && dp[i - coin] != null) {
          int withCoin = dp[i - coin] + 1;
          if (dp[i] == null || withCoin < dp[i]) {
            dp[i] = withCoin;
          }
        }
      }
    }

    Solution solution = new Solution();
    if (dp[n] == null) return solution;
    solution.minCoins = Optional.of(dp[n]);

    // Backtrack greedily: at each amount, pick the coin that leads to the fewest coins
    for (int i = n; i > 0; ) {
      int bestCoin = -1;
      int bestCount = dp[i];
      for (int coin : coins) {
        if (i - coin >= 0 && dp[i - coin] != null && dp[i - coin] < bestCount) {
          bestCount = dp[i - coin];
          bestCoin = coin;
        }
      }
      solution.selectedCoins.add(bestCoin);
      i -= bestCoin;
    }

    return solution;
  }

  // ==================== Implementation 3: Top-down recursive with memoization ====================

  /**
   * Solves coin change using top-down recursion with memoization.
   *
   * Unlike the two bottom-up implementations above, the recursive approach
   * only visits states reachable from the target amount. This can be faster
   * when coin denominations are large (many states are skipped).
   *
   * Note: returns -1 instead of Optional.empty() for impossible cases,
   * and does not recover the selected coins.
   *
   * @param coins array of coin denominations (all positive)
   * @param n     the target amount
   * @return the minimum number of coins, or -1 if impossible
   *
   * Time:  O(m*n)
   * Space: O(n)
   */
  public static int coinChangeRecursive(int[] coins, int n) {
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (n < 0) return -1;

    int[] dp = new int[n + 1];
    return coinChangeRecursive(n, coins, dp);
  }

  private static int coinChangeRecursive(int n, int[] coins, int[] dp) {
    if (n < 0) return -1;
    if (n == 0) return 0;
    if (dp[n] != 0) return dp[n];

    int minCoins = Integer.MAX_VALUE;
    for (int coin : coins) {
      int value = coinChangeRecursive(n - coin, coins, dp);
      if (value != -1 && value < minCoins)
        minCoins = value + 1;
    }

    // Cache -1 if no combination of coins can make this amount
    return dp[n] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
  }

  public static void main(String[] args) {
    example1();
    example2();
    example3();
    example4();
  }

  private static void example1() {
    int[] coins = {2, 6, 1};
    System.out.println("--- coins={2,6,1}, amount=17 ---");
    System.out.println("2D DP:      " + coinChange(coins, 17).minCoins);        // Optional[4]
    System.out.println("  selected: " + coinChange(coins, 17).selectedCoins);   // [6, 6, 2, 2, 1]
    System.out.println("1D DP:      " + coinChangeSpaceEfficient(coins, 17).minCoins); // Optional[4]
    System.out.println("Recursive:  " + coinChangeRecursive(coins, 17));        // 4
  }

  private static void example2() {
    int[] coins = {2, 3, 5};
    System.out.println("--- coins={2,3,5}, amount=12 ---");
    System.out.println("2D DP:      " + coinChange(coins, 12).minCoins);        // Optional[3]
    System.out.println("  selected: " + coinChange(coins, 12).selectedCoins);   // [5, 5, 2]
    System.out.println("1D DP:      " + coinChangeSpaceEfficient(coins, 12).minCoins); // Optional[3]
    System.out.println("Recursive:  " + coinChangeRecursive(coins, 12));        // 3
  }

  private static void example3() {
    int[] coins = {3, 4, 7};
    System.out.println("--- coins={3,4,7}, amount=17 ---");
    System.out.println("2D DP:      " + coinChange(coins, 17).minCoins);        // Optional[3]
    System.out.println("  selected: " + coinChange(coins, 17).selectedCoins);   // [7, 7, 3]
    System.out.println("1D DP:      " + coinChangeSpaceEfficient(coins, 17).minCoins); // Optional[3]
    System.out.println("Recursive:  " + coinChangeRecursive(coins, 17));        // 3
  }

  private static void example4() {
    int[] coins = {2, 4, 1};
    System.out.println("--- coins={2,4,1}, amount=11 ---");
    System.out.println("2D DP:      " + coinChange(coins, 11).minCoins);        // Optional[4]
    System.out.println("  selected: " + coinChange(coins, 11).selectedCoins);   // [4, 4, 2, 1]
    System.out.println("1D DP:      " + coinChangeSpaceEfficient(coins, 11).minCoins); // Optional[4]
    System.out.println("Recursive:  " + coinChangeRecursive(coins, 11));        // 4
  }
}
