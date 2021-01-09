
import java.util.*;

public class BoardTilingsSolver {

  private int[] tiles;
  
  private int maxTileLength = 0;
  private List<Integer> tileSet;
  private int[] tileFrequency;

  public BoardTilingsSolver(int[] tiles) {
    this.tiles = tiles;
  }

  private void init() {
    int maxTileLength = 0;
    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i] > maxTileLength) {
        maxTileLength = tiles[i];
      }
    }
    tileSet = new ArrayList<>();
    tileFrequency = new int[maxTileLength+1];
    for (int tile : tiles) {
      if (tileFrequency[tile] == 0) {
        tileSet.add(tile);
      }
      tileFrequency[tile]++;
    }
  }

  public long iterativeSolution(int n) {
    init();
    long[] dp = new long[n+1];
    dp[0] = 1;
    for (int i = 1; i <= n; i++) {
      for (int tile : tileSet) {
        if (i - tile < 0) continue;
        dp[i] += dp[i - tile] * tileFrequency[tile];
      }
    }
    return dp[n];
  }

  // Works well when there is a low number of (large) tiles and the recursion
  // depth isn't too deep, otherwise you may encounter a stack overflow.
  public long recursiveSolution(int n) {
    init();
    // Use a Map instead of a List in the hope that the recursion is sparse
    // and that we can save memory by avoiding a large allocation.
    Map<Integer, Long> dp = new HashMap<>();
    dp.put(0, 1L);
    return f(n, dp);
  }

  private long f(int n, Map<Integer, Long> dp) {
    Long count = dp.get(n);
    if (count != null) {
      return count;
    }
    count = 0L;
    for (int tile : tileSet) {
      if (n - tile < 0) continue;
      count += f(n - tile, dp) * tileFrequency[tile];
    }
    // Cache (memorize) the solution
    dp.put(n, count);
    return count;
  }

  public static void main(String[] args) {
    int n = 25;
    int[] tiles = {1, 1, 2, 4};
    BoardTilingsSolver solver = new BoardTilingsSolver(tiles);
    System.out.printf("f(%d) = %d\n", n, solver.iterativeSolution(n));
    System.out.printf("f(%d) = %d\n", n, solver.iterativeSolution(n));
    System.out.println();
    for (int i = 0; i < 10; i++) {
      System.out.printf("f(%d) = %d (iterative soln)\n", i, solver.iterativeSolution(i));
      System.out.printf("f(%d) = %d (recursive soln)\n", i, solver.recursiveSolution(i));
    }

    // long startTime = System.currentTimeMillis();
    // int n = 60000000;
    // int[] tiles = {100000, 10000000, 20000000, 30000000};
    // System.out.println(f(n, tiles));
    // long endTime = System.currentTimeMillis();
    // System.out.println("f(n) total execution time: " + (endTime - startTime));

    // startTime = System.currentTimeMillis();
    // System.out.println(f2(n, tiles));
    // endTime = System.currentTimeMillis();
    // System.out.println("f2(n) total execution time: " + (endTime - startTime));
  }
}












