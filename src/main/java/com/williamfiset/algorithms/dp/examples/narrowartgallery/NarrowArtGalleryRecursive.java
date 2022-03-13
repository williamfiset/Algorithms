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

  public static int f(int k, int r) {
    // Compute the optimal value of ending at the top LEFT and the top RIGHT of
    // the gallery and return the minimum.
    return min(f(k, r, LEFT), f(k, r, RIGHT));
  }

  // f(k,r,c) Computes the minimum value you can save by closing off `k` rooms
  // in a gallery with `r` levels starting on side `c`.
  //
  // k = The number of rooms the curator needs to close
  // r = The gallery row index
  // c = The column, either LEFT (= 0) or RIGHT (= 1)
  public static int f(int k, int r, int c) {
    // We finished closing all K rooms
    if (k == 0) {
      return 0;
    }
    if (r < 0) {
      return INF;
    }
    // Return the value of this subproblem, if it's already been computed.
    if (dp[k][r][c] != null) {
      return dp[k][r][c];
    }
    // Get the value of the current room at row `r` and column `c`.
    int roomValue = gallery[r][c];
    return dp[k][r][c] =
        min(
            // Close the current room, and take the best value from the partial
            // state directly below the current room.
            f(k - 1, r - 1, c) + roomValue,
            // Don't include the current room. Instead, take the last best value from
            // the previously calculated partial state which includes `k` rooms closed.
            f(k, r - 1));
  }

  static void mainProgram() {
    Scanner sc = new Scanner(System.in);
    while (true) {
      int N = sc.nextInt();
      int K = sc.nextInt();

      if (N == 0 && K == 0) break;

      gallery = new int[N][2];
      dp = new Integer[K + 1][N][2];

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

      System.out.printf("%d\n", sum - f(K, N - 1));
    }
  }

  public static void main(String[] Fiset) {
    mainProgram();
    // test2();
  }

  static void test2() {
    int N = 5;
    int K = 4;
    dp = new Integer[K + 1][N][2];

    // 3, 2
    // 5, 9
    // 0, 1
    // 4, 3
    // 8, 10
    //
    // Gallery has been flipped like it would when inputted:
    gallery =
        new int[][] {
          {8, 10},
          {4, 3},
          {0, 1},
          {5, 9},
          {3, 2},
        };
    System.out.println(f(4, 3, LEFT));
    System.out.println(f(4, 3, RIGHT));
    System.out.println(f(3, 3, LEFT));
    System.out.println(f(3, 3, RIGHT));
  }

  static void test1() {
    int N = 6;
    int K = 4;
    dp = new Integer[K + 1][N][2];

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
