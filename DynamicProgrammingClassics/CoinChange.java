/**
 * The coin change problem is an unbounded knapsack problem variant. 
 * The problem asks you to find the minimum number of coins required
 * for a certain amount of change given the coin denominations. You
 * may use each coin denomination as many times as you please.
 *
 * Tested against:
 * https://leetcode.com/problems/coin-change/
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 *
 */

public class CoinChange {
  
  private final static int INF = 987654321;
  
  public static int coinChange(int[] coins, int amount) {
    
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (amount == 0) return 0;
    
    final int N = coins.length;
    
    // Initialize table and set first row to be infinity
    int[][] DP = new int[N+1][amount+1];
    java.util.Arrays.fill(DP[0], INF);
    DP[1][0] = 0;

    // Iterate through all the coins
    for(int i = 1; i <= N; i++) {
      
      int coinValue = coins[i-1];
      for(int j = 1; j <= amount; j++) {
        
        // Consider not selecting this coin
        DP[i][j] = DP[i-1][j];

        // Trying selecting this coin if it's better
        if ( j-coinValue >= 0 && DP[i][j-coinValue] + 1 < DP[i][j])
          DP[i][j] = DP[i][j-coinValue] + 1;
          
      }

    }
    
    // The amount we wanted to make cannot be made :/
    if (DP[N][amount] == INF) return -1;
    
    // Return the minimum number of coins needed
    return DP[N][amount];
      
  }

  public static int coinChangeSpaceEfficient(int[] coins, int amount) {
    
    if (coins == null) throw new IllegalArgumentException("Coins array is null");
    if (amount == 0) return 0;
    
    final int N = coins.length;

    // Initialize table and set everything to infinity except first cell
    int[] DP = new int[amount+1];
    java.util.Arrays.fill(DP, INF);
    DP[0] = 0;
    
    for (int i = 1; i <= amount; i++)
      for (int coinValue : coins)
        if (i - coinValue >= 0 && DP[i-coinValue] + 1 < DP[i])
          DP[i] = DP[i-coinValue] + 1;
    
    // The amount we wanted to make cannot be made :/
    if (DP[amount] == INF) return -1;
    
    // Return the minimum number of coins needed
    return DP[amount];

  }

  public static void main(String[] args) {
    int[] coins = {3,6,5};
    System.out.println(coinChangeSpaceEfficient(coins, 11));
  }
  
}















