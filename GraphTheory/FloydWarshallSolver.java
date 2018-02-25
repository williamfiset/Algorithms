/**
 * This file contains an implementation of the Floyd-Warshall algorithm to find
 * all pairs of shortest paths between nodes in a graph. We also demonstrate how
 * to detect negative cycles.
 *
 * Time Complexity: O(V^3)
 *
 * @author Micah Stairs, William Fiset
 **/

// Import Java's special constants ∞ and -∞ which behave 
// as you expect them to when you do arithmetic. For example,
// ∞ + ∞ = ∞, ∞ + x = ∞, -∞ + x = -∞ and ∞ + -∞ = Nan
import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Double.NEGATIVE_INFINITY;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshallSolver {

  private int n;
  private boolean solved;
  private double[][] dp;
  private Integer[][] next;

  /**
   * As input, this class takes an adjacency matrix with edge weights between nodes, 
   * where POSITIVE_INFINITY is used to indicate that two nodes are not connected.
   *
   * NOTE: Usually the diagonal of the adjacency matrix is all zeros 
   * (i.e. matrix[i][i] = 0 for all i) since there is typically no cost 
   * to go from a node to itself, but this may depend on your graph and 
   * the problem you are trying to solve.
   */
  public FloydWarshallSolver(double[][] matrix) {
    n = matrix.length;
    dp = new double[n][n];
    next = new Integer[n][n];

    // Copy input matrix and setup 'next' matrix for path reconstruction.
    for(int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] != POSITIVE_INFINITY)
          next[i][j] = j;
        dp[i][j] = matrix[i][j];
      }
    }
  }

  // Returns the solved All Pairs Shortest Path (APSP) matrix.
  public double[][] getApspMatrix() {
    if (!solved) solve();
    return dp;
  }

  // Returns the shortest path (of nodes) from start to end inclusive.
  // TODO(williamfiset): Needs testing!
  public List<Integer> getPath(int start, int end) {
    if (!solved) solve();
    List<Integer> path = new ArrayList<>();
    if (next[start][end] == null) return path;
    for(int at = start; at != end; at = next[at][end])
      path.add(at);
    path.add(end);
    return path;
  }

  // Executes the Floyd-Warshall algorithm internally.
  public void solve() {
    // Compute all pairs shortest paths.
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (dp[i][k] + dp[k][j] < dp[i][j]) {
            dp[i][j] = dp[i][k] + dp[k][j];
            next[i][j] = next[i][k];
          }
        }
      }
    }

    // Identify negative cycles (you can comment this block 
    // if you know that no negative cycles exist).
    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dp[i][k] + dp[k][j] < dp[i][j])
            dp[i][j] = NEGATIVE_INFINITY;

    solved = true;
  }

  // TODO(williamfiset): Remove this test method
  public static double[][] floydWarshallExtraMemory(double[][] m) {
    int v = m.length;
    double[][][] dp = new double[v][v][v];
    // Compute all pairs shortest paths
    for (int k = 0; k < v; k++) {
      for (int i = 0; i < v; i++) {
        for (int j = 0; j < v; j++) {
          if (k == 0) dp[k][i][j] = m[i][j];
          else dp[k][i][j] = Math.min(dp[k-1][i][j], dp[k-1][i][k] + dp[k-1][k][j]);
        }
      }
    }
    for (int i = 0; i < v; i++) System.out.println(java.util.Arrays.toString(dp[v-1][i]));
    return null;
  }

  public static void main(String[] args) {
    final double INF = POSITIVE_INFINITY;
    double[][] m = new double[][] {
      {   0,   6, INF,  25,   3},
      {   1,   0,   6,   1,   3},
      { INF,   1,   0,   2,   3},
      {   4,   4,   4,   0, INF},
      {   4,   2,   5, INF,   0}
    };
    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    List<Integer> path = solver.getPath(0, 3);
    System.out.println(path);
  }

}








