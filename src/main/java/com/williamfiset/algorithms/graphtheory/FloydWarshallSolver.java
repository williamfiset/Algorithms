/**
 * Implementation of the Floyd-Warshall algorithm to find all pairs of shortest paths between nodes
 * in a graph. Also demonstrates how to detect negative cycles and reconstruct the shortest path.
 *
 * <p>Time: O(V^3)
 *
 * <p>Space: O(V^2)
 *
 * @author Micah Stairs, William Fiset
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FloydWarshallSolver {

  private final int n;
  private boolean solved;
  private double[][] dp;
  private Integer[][] next;

  private static final int REACHES_NEGATIVE_CYCLE = -1;

  /**
   * Creates a Floyd-Warshall solver from an adjacency matrix with edge weights between nodes, where
   * POSITIVE_INFINITY indicates that two nodes are not connected.
   *
   * <p>NOTE: Usually the diagonal of the adjacency matrix is all zeros (i.e. matrix[i][i] = 0 for
   * all i) since there is typically no cost to go from a node to itself, but this may depend on the
   * graph and the problem being solved.
   *
   * @param matrix an n x n adjacency matrix of edge weights.
   * @throws IllegalArgumentException if the matrix is null or empty.
   */
  public FloydWarshallSolver(double[][] matrix) {
    if (matrix == null || matrix.length == 0)
      throw new IllegalArgumentException("Matrix cannot be null or empty.");
    n = matrix.length;
    dp = new double[n][n];
    next = new Integer[n][n];

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] != POSITIVE_INFINITY)
          next[i][j] = j;
        dp[i][j] = matrix[i][j];
      }
    }
  }

  /**
   * Runs Floyd-Warshall to compute the shortest distance between every pair of nodes.
   *
   * @return the solved All Pairs Shortest Path (APSP) matrix.
   */
  public double[][] getApspMatrix() {
    solve();
    return dp;
  }

  /** Executes the Floyd-Warshall algorithm. */
  public void solve() {
    if (solved)
      return;

    // Compute all pairs shortest paths.
    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dp[i][k] + dp[k][j] < dp[i][j]) {
            dp[i][j] = dp[i][k] + dp[k][j];
            next[i][j] = next[i][k];
          }

    // Identify negative cycles by propagating NEGATIVE_INFINITY
    // to every edge that is part of or reaches into a negative cycle.
    for (int k = 0; k < n; k++)
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
          if (dp[i][k] != POSITIVE_INFINITY && dp[k][j] != POSITIVE_INFINITY && dp[k][k] < 0) {
            dp[i][j] = NEGATIVE_INFINITY;
            next[i][j] = REACHES_NEGATIVE_CYCLE;
          }

    solved = true;
  }

  /**
   * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
   *
   * @return an array of node indexes of the shortest path from 'start' to 'end'. If 'start' and
   *     'end' are not connected return an empty list. If the shortest path from 'start' to 'end'
   *     reaches a negative cycle return null.
   */
  public List<Integer> reconstructShortestPath(int start, int end) {
    solve();
    List<Integer> path = new ArrayList<>();
    if (dp[start][end] == POSITIVE_INFINITY)
      return path;
    int at = start;
    for (; at != end; at = next[at][end]) {
      if (at == REACHES_NEGATIVE_CYCLE)
        return null;
      path.add(at);
    }
    if (next[at][end] == REACHES_NEGATIVE_CYCLE)
      return null;
    path.add(end);
    return path;
  }

  /** Creates an n x n adjacency matrix initialized with POSITIVE_INFINITY and zero diagonal. */
  public static double[][] createGraph(int n) {
    double[][] matrix = new double[n][n];
    for (int i = 0; i < n; i++) {
      Arrays.fill(matrix[i], POSITIVE_INFINITY);
      matrix[i][i] = 0;
    }
    return matrix;
  }

  public static void main(String[] args) {
    exampleWithNegativeCycle();
    System.out.println();
    exampleSimpleGraph();
  }

  // Example 1: 4-node graph with a negative cycle between nodes 2 and 3.
  private static void exampleWithNegativeCycle() {
    int n = 4;
    double[][] m = createGraph(n);
    m[0][1] = 4;
    m[1][2] = 1;
    m[2][3] = 2;
    m[3][2] = -5; // Creates negative cycle: 2 -> 3 -> 2 (net cost -3).

    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    double[][] dist = solver.getApspMatrix();

    System.out.println("=== Example 1: Negative cycle ===");
    System.out.printf("dist(0, 1) = %.0f\n", dist[0][1]); // 4
    System.out.printf("dist(0, 2) = %.0f\n", dist[0][2]); // -Infinity (reaches negative cycle)
    System.out.printf("path(0, 2) = %s\n", formatPath(solver.reconstructShortestPath(0, 2), 0, 2));
    System.out.printf("path(0, 1) = %s\n", formatPath(solver.reconstructShortestPath(0, 1), 0, 1));
  }

  // Example 2: 4-node directed graph with no negative cycles.
  private static void exampleSimpleGraph() {
    int n = 4;
    double[][] m = createGraph(n);
    m[0][1] = 1;
    m[1][2] = 3;
    m[1][3] = 10;
    m[2][3] = 2;

    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    double[][] dist = solver.getApspMatrix();

    System.out.println("=== Example 2: Simple directed graph ===");
    // Shortest distance from 0 to 3 is 6 (0 -> 1 -> 2 -> 3), not 11 (0 -> 1 -> 3).
    System.out.printf("dist(0, 3) = %.0f\n", dist[0][3]);
    System.out.printf("path(0, 3) = %s\n", formatPath(solver.reconstructShortestPath(0, 3), 0, 3));
    System.out.printf("path(3, 0) = %s\n", formatPath(solver.reconstructShortestPath(3, 0), 3, 0));
  }

  private static String formatPath(List<Integer> path, int start, int end) {
    if (path == null)
      return "NEGATIVE CYCLE";
    if (path.isEmpty())
      return String.format("NO PATH (%d doesn't reach %d)", start, end);
    return path.stream().map(Object::toString).collect(Collectors.joining(" -> "));
  }
}
