import java.util.Scanner;

public class NarrowArtGalleryRecursive {

  // A large enough infinity value for this problem
  static final int INF = 1000000;

  static int[][] gallery;
  static Integer[][][] dp;
  
  static final int LEFT = 0;
  static final int RIGHT = 1;

  static int min(int... values) {
    int m = Integer.MAX_VALUE;
    for (int v : values)
      if (v < m)
        m = v;
    return m;
  }

  static int f(int n, int k) {
    // Compute the optimal value of ending at the top LEFT and the top RIGHT of
    // the gallery and return the minimum.
    return min(f(n, k, LEFT), f(n, k, RIGHT));
  }

  // f(n,k,s) Computes the minimum value you can save by closing off `k` rooms
  // in a gallery with `n` levels starting on the side `s`.
  //
  // n = The gallery row index
  // k = The number of rooms the curator needs to close
  // s = The side, either LEFT (= 0) or RIGHT (= 1)
  static int f(int n, int k, int s) {
    // NOTE: Mention the order of the base cased matter
    if (k == 0) {
      return 0;
    }
    if (n < 0) {
      return INF;
    }
    if (dp[n][k][s] != null) {
      return dp[n][k][s];
    }
    return dp[n][k][s] = min(
      // Take the best solution from 2 rows back, and block off the left room,
      // and then do the same, but for the right room.
      f(n-2, k-1, s) + gallery[n][s],
      f(n-2, k-1, s ^ 1) + gallery[n][s],
      // Take the partial state which may include the room directly below,
      // and include the room in the current row.
      f(n-1, k-1, s) + gallery[n][s],
      // Don't include the current room. Instead, take the last best value from
      // the previously calculated partial state which includes k rooms closed.
      f(n-1, k, s)
    );
  }

  public static void main(String[] Fiset) {
    Scanner sc = new Scanner(System.in);
    while(true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[N][K+1][2];

      int sum = 0;
      for (int i = 0; i < N; i++) {
        // Input the gallery values in reverse to simulate walking from the
        // bottom to the top of the gallery. This makes debugging easier and
        // shouldn't affect the final result.
        int index = N-i-1;
        gallery[index][LEFT] = sc.nextInt();
        gallery[index][RIGHT] = sc.nextInt();
        sum += gallery[index][LEFT] + gallery[index][RIGHT];
      }

      System.out.printf("%d\n", sum - f(N-1, K));
    }
  }
}

