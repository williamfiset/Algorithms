/**
 * Solution to the Mountain Scenes problem (https://open.kattis.com/problems/scenes)
 *
 * <p>Solution by: William Fiset
 */
import java.io.*;
import java.util.*;

public class Scenes {

  static Long[][] dp;
  static int N, W, H;
  static long MOD = 1_000_000_007;

  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    String[] ln = br.readLine().split(" ");
    N = Integer.parseInt(ln[0]); // Total ribbon length
    W = Integer.parseInt(ln[1]);
    H = Integer.parseInt(ln[2]);

    // Count the number of plain mountains. Be sure to account for having more
    // ribbon than can possible be placed.
    int plains = (Math.min(W * H, N) / W) + 1;

    dp = new Long[W + 1][N + 1];
    long ans = ((f(1, N) - plains) + MOD) % MOD;
    System.out.println(ans);
  }

  static long f(int w, int ribbon) {
    // We're trying to place a strip of ribbon that we don't have.
    if (ribbon < 0) {
      return 0;
    }
    // Finished a mountain scene.
    if (w > W) {
      return 1;
    }
    if (dp[w][ribbon] != null) {
      return dp[w][ribbon];
    }
    long scenes = 0L;
    // Try all possible ribbon lengths for this column
    for (int len = 0; len <= H; len++) {
      scenes = (scenes + f(w + 1, ribbon - len)) % MOD;
    }
    return dp[w][ribbon] = scenes;
  }
}
