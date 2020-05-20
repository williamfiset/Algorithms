class Solution {
  static int n;
  static Long[][] dp;
  static long MOD = 1_000_000_007;

  public int numTilings(int N) {
    n = N;
    dp = new Long[n + 1][1 << 2];
    long ans = f(0, true, true);
    return (int) ans;
  }

  // t1 - whether tile 1 is available
  // t2 - whether tile 2 is available
  static long f(int i, boolean t1, boolean t2) {
    // Finished fully tiling the board.
    if (i == n) {
      return 1;
    }
    int state = makeState(t1, t2);
    if (dp[i][state] != null) {
      return dp[i][state];
    }

    // Zones that define which regions are free. For the surrounding 4 tiles:
    // t1 t3
    // t2 t4
    boolean t3 = i + 1 < n;
    boolean t4 = i + 1 < n;

    long count = 0;

    // Placing:
    // XX
    // X
    if (t1 && t2 && t3) count += f(i + 1, false, true);

    // Placing:
    // X
    // XX
    if (t1 && t2 && t4) count += f(i + 1, true, false);

    // Placing:
    // XX
    // #X
    if (t1 && !t2 && t3 && t4) count += f(i + 1, false, false);

    // Placing:
    // #X
    // XX
    if (!t1 && t2 && t3 && t4) count += f(i + 1, false, false);

    // Placing
    // X
    // X
    if (t1 && t2) count += f(i + 1, true, true);

    // Placing two horizontals. We don't place 2 verticals because
    // that's accounted for with the single vertical tile:
    // XX
    // XX
    if (t1 && t2 && t3 && t4) count += f(i + 1, false, false);

    // Placing:
    // XX
    // #
    if (t1 && !t2 && t3) count += f(i + 1, false, true);

    // Placing:
    // #
    // XX
    if (!t1 && t2 && t4) count += f(i + 1, true, false);

    // Current column is already fully tiled, so move to next column
    // #
    // #
    if (!t1 && !t2) count += f(i + 1, true, true);

    return dp[i][state] = count % MOD;
  }

  static int makeState(boolean row1, boolean row2) {
    int state = 0;
    if (row1) state |= 0b01;
    if (row2) state |= 0b10;
    return state;
  }

  public static void main(String[] args) {
    Solution s = new Solution();
    for (int n = 1; n <= 10; n++) {
      System.out.printf("n = %d, ans = %d\n", n, s.numTilings(n));
    }
  }
}
