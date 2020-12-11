/**
 * Solution to the Narrow Art Gallery problem from the 2014 ICPC North America Qualifier
 *
 * <p>Problem: https://open.kattis.com/problems/narrowartgallery
 *
 * <p>Problem Author: Robert Hochberg
 *
 * <p>Solution by: William Fiset
 */
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
    for (int v : values) if (v < m) m = v;
    return m;
  }

  public static int f(int n, int k) {
    // Compute the optimal value of ending at the top LEFT and the top RIGHT of
    // the gallery and return the minimum.
    return min(f(n, k, LEFT), f(n, k, RIGHT));
  }

  // f(n,k,c) Computes the minimum value you can save by closing off `k` rooms
  // in a gallery with `n` levels starting on side `c`.
  //
  // n = The gallery row index
  // k = The number of rooms the curator needs to close
  // c = The column, either LEFT (= 0) or RIGHT (= 1)
  public static int f(int n, int k, int c) {
    // We finished closing all K rooms
    if (k == 0) {
      return 0;
    }
    if (n < 0) {
      return INF;
    }
    // Return the value of this subproblem, if it's already been computed.
    if (dp[n][k][c] != null) {
      return dp[n][k][c];
    }
    // Get the value of the current room at row `n` and column `c`.
    int roomValue = gallery[n][c];
    return dp[n][k][c] =
        min(
            // Close the current room, and take the best value from the partial
            // state directly below the current room.
            f(n - 1, k - 1, c) + roomValue,
            // Don't include the current room. Instead, take the last best value from
            // the previously calculated partial state which includes `k` rooms closed.
            f(n - 1, k, LEFT),
            f(n - 1, k, RIGHT));
  }

  static void mainProgram() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[N][K + 1][2];

      int sum = 0;
      for (int i = 0; i < N; i++) {
        // Input the gallery values in reverse to simulate walking from the
        // bottom to the top of the gallery. This makes debugging easier and
        // shouldn't affect the final result.
        int index = N - i - 1;
        gallery[index][LEFT] = sc.nextInt();
        gallery[index][RIGHT] = sc.nextInt();
        sum += gallery[index][LEFT] + gallery[index][RIGHT];
      }

      System.out.printf("%d\n", sum - f(N - 1, K));
    }
  }

  public static void main(String[] Fiset) {
    mainProgram();
    // test1();
  }

  static void test1() {
    int N = 6;
    int K = 4;
    dp = new Integer[N][K + 1][2];

    // 3, 1
    // 2, 1
    // 1, 2
    // 1, 3
    // 3, 3
    // 1, 0
    //
    // Gallery has been flipped like it would when inputted:
    gallery =
        new int[][] {
          {1, 0},
          {3, 3},
          {1, 3},
          {1, 2},
          {2, 1},
          {3, 1},
        };
    f(N - 1, K);

    ok(f(0, 4, LEFT), INF);
    ok(f(0, 4, RIGHT), INF);
    ok(f(1, 4, LEFT), INF);
    ok(f(1, 4, RIGHT), INF);
    ok(f(2, 4, LEFT), INF);
    ok(f(2, 4, RIGHT), INF);
    ok(f(3, 4, LEFT), 6);
    ok(f(3, 4, RIGHT), 8);
    ok(f(4, 4, LEFT), 4);
    ok(f(4, 4, RIGHT), 6);
    ok(f(5, 4, LEFT), 4);
    ok(f(5, 4, RIGHT), 3);

    ok(f(0, 3, LEFT), INF);
    ok(f(0, 3, RIGHT), INF);
    ok(f(1, 3, LEFT), INF);
    ok(f(1, 3, RIGHT), INF);
    ok(f(2, 3, LEFT), 5);
    ok(f(2, 3, RIGHT), 6);
    ok(f(3, 3, LEFT), 2);
    ok(f(3, 3, RIGHT), 5);
    ok(f(4, 3, LEFT), 2);
    ok(f(4, 3, RIGHT), 2);
    ok(f(5, 3, LEFT), 2);
    ok(f(5, 3, RIGHT), 2);

    ok(f(0, 2, LEFT), INF);
    ok(f(0, 2, RIGHT), INF);
    ok(f(1, 2, LEFT), 4);
    ok(f(1, 2, RIGHT), 3);
    ok(f(2, 2, LEFT), 1);
    ok(f(2, 2, RIGHT), 3);
    ok(f(3, 2, LEFT), 1);
    ok(f(3, 2, RIGHT), 1);
    ok(f(4, 2, LEFT), 1);
    ok(f(4, 2, RIGHT), 1);
    ok(f(5, 2, LEFT), 1);
    ok(f(5, 2, RIGHT), 1);

    ok(f(0, 1, LEFT), 1);
    ok(f(0, 1, RIGHT), 0);
    ok(f(1, 1, LEFT), 0);
    ok(f(1, 1, RIGHT), 0);
    ok(f(2, 1, LEFT), 0);
    ok(f(2, 1, RIGHT), 0);
    ok(f(3, 1, LEFT), 0);
    ok(f(3, 1, RIGHT), 0);
    ok(f(4, 1, LEFT), 0);
    ok(f(4, 1, RIGHT), 0);
    ok(f(5, 1, LEFT), 0);
    ok(f(5, 1, RIGHT), 0);
  }

  static void ok(int a, int b) {
    if (a != b) {
      System.out.println("Error: " + a + " != " + b);
    }
  }
}
