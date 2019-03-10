import java.util.Scanner;

public class NarrowArtGalleryRecursive {

  static Scanner sc = new Scanner(System.in);
  static final int INF = 10000;

  static int sum;
  static int[][] gallery;
  static Integer[][][] dp;
  
  static final int LEFT = 0;
  static final int RIGHT = 1;
  static final int NEITHER = 2;

  static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values)
      if (v < m)
        m = v;
    return m;
  }

  static int f(int k, int n) {
    return sum - min(f(k, n, LEFT), f(k, n, RIGHT));
  }

  // k = num rooms to close, n = row index, s = the side, either LEFT or RIGHT.
  static int f(int k, int n, int s) {
    if (n < 0) return INF;
    if (k == 0) return 0;
    if (s == NEITHER) return min(f(k-1, n-2, LEFT), f(k-1, n-2, RIGHT));

    if (dp[k][n][s] != null)
      return dp[k][n][s];

    dp[k][n][LEFT] = min(
      f(k, n, NEITHER)  + g(n-2, LEFT),
      f(k-1, n-1, LEFT) + g(n-2, LEFT),
      f(k, n-1, LEFT)
    );

    dp[k][n][RIGHT] = min(
      f(k, n, NEITHER)   + g(n-2, RIGHT),
      f(k-1, n-1, RIGHT) + g(n-2, RIGHT),
      f(k, n-1, RIGHT)
    );

    return s == LEFT ? dp[k][n][LEFT] : dp[k][n][RIGHT];
  }

  static int g(int n, int s) {
    if (n < 0) return INF;
    return gallery[n][s];
  }

  public static void main(String[] Fiset) {
    while(true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[K+1][N+2][2];

      for(int i = 0; i < N; i++) {
        gallery[i][LEFT] = sc.nextInt();
        gallery[i][RIGHT] = sc.nextInt();
        sum += gallery[i][LEFT] + gallery[i][RIGHT];
      }

      System.out.printf("%d\n", f(K, N+1));
    }
  }
}
