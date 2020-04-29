import java.util.*;

public class TilingDominoes {
  static Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    while (true) {
      int n = sc.nextInt();
      if (n == -1) break;
      System.out.println(solution1(n));
      // System.out.println(solution2(n));
    }
  }

  // Tile states 0...7 represent:
  //
  //  0: 0    1: 1    2: 0    3: 1
  //     0       0       1       1
  //     0       0       0       0
  //
  //  4: 0    5: 1    6: 0    7: 1
  //     0       0       1       1
  //     1       1       1       1
  private static int solution1(int n) {
    int[][] dp = new int[n + 1][1 << 3];
    dp[0][7] = 1;
    for (int i = 1; i < n + 1; i++) {
      // The number of empty states for this column is the number
      // of full states in the previous column.
      dp[i][0] += dp[i - 1][7];

      dp[i][1] += dp[i - 1][6];

      // State 5 depends on state 2 and vice versa, but neither of them
      // contribute to the number of tilings.
      // dp[i][2] += dp[i-1][5];

      dp[i][3] += dp[i - 1][7];
      dp[i][3] += dp[i - 1][4]; // Don't double because placing a block sideways double includes?

      dp[i][4] += dp[i - 1][3];

      // State 5 depends on state 2 and vice versa, but neither of them
      // contribute to the number of tilings.
      // dp[i][5] += dp[i-1][2];

      dp[i][6] += dp[i - 1][7];
      dp[i][6] += dp[i - 1][1]; // Don't double because placing a block sideways double includes?

      dp[i][7] += dp[i - 1][3];
      dp[i][7] += dp[i - 1][6];
      dp[i][7] += dp[i - 1][0]; // Three sideways blocks
    }
    // printMatrix(dp);
    return dp[dp.length - 1][7];
  }

  private static void printMatrix(int[][] dp) {
    for (int i = 0; i < dp.length; i++) {
      for (int j = 0; j < 1 << 3; j++) {
        System.out.printf("% 5d,", dp[i][j]);
      }
      System.out.println();
    }
  }

  private static int solution2(int n) {
    int[] dp = new int[n + 4];
    dp[0] = 1;
    dp[2] = 3;
    for (int i = 4; i < n + 4; i += 2) {
      dp[i] = 4 * dp[i - 2] - dp[i - 4];
    }
    return dp[n];
  }
}
