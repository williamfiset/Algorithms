package com.williamfiset.algorithms.dp.examples.scenes;

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
    W = Integer.parseInt(ln[1]); // Width
    H = Integer.parseInt(ln[2]); // Height

    solution1();
  }

  // Recursive solution implementation
  static void solution1() {
    // Count the number of plain mountains. Be sure to account for having more
    // ribbon than can possible be placed.
    int ribbonSquares = Math.min(W * H, N);
    int plains = (ribbonSquares / W) + 1;

    dp = new Long[W + 1][N + 1];
    long ans = ((f(1, N) - plains) + MOD) % MOD;
    System.out.println(ans);
  }

  static long f(int w, int ribbon) {
    // We're trying to create a mountain scene for which we don't have enough ribbon material for.
    if (ribbon < 0) {
      return 0;
    }
    // When the width value exceeds the frame width, we know we’ve finished creating a unique
    // mountain scene!
    if (w > W) {
      return 1;
    }
    if (dp[w][ribbon] != null) {
      return dp[w][ribbon];
    }
    long scenes = 0L;
    // Try placing all possible ribbon lengths at this column/width
    for (int len = 0; len <= H; len++) {
      scenes = (scenes + f(w + 1, ribbon - len));
    }
    // Store and return the number of scenes for this sub-problem.
    // Applying the MOD after the loop is safe since H is at most 100 and
    // the MOD is 10^9+7 which cannot overflow a signed 64bit integer.
    return dp[w][ribbon] = scenes % MOD;
  }
}
