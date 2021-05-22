/**
 * This file contains an implementation of the Floyd-Warshall algorithm to find all pairs of
 * shortest paths between nodes in a graph. We also demonstrate how to detect negative cycles and
 * reconstruct the shortest path.
 *
 * <p>Time Complexity: O(V^3)
 *
 * @author Micah Stairs, William Fiset
 */
package com.williamfiset.algorithms.graphtheory;

// Import Java's special constants ∞ and -∞ which behave
// as you expect them to when you do arithmetic. For example,
// ∞ + ∞ = ∞, ∞ + x = ∞, -∞ + x = -∞ and ∞ + -∞ = Nan
import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

import java.util.ArrayList;
import java.util.List;

public class FloydWarshallSolver {

  private int n;
  private boolean solved;
  private double[][] dp;
  private Integer[][] next;

  private static final int REACHES_NEGATIVE_CYCLE = -1;

  /**
   * As input, this class takes an adjacency matrix with edge weights between nodes, where
   * POSITIVE_INFINITY is used to indicate that two nodes are not connected.
   *
   * <p>NOTE: Usually the diagonal of the adjacency matrix is all zeros (i.e. matrix[i][i] = 0 for
   * all i) since there is typically no cost to go from a node to itself, but this may depend on
   * your graph and the problem you are trying to solve.
   */
  public FloydWarshallSolver(double[][] matrix) {
    n = matrix.length;
    dp = new double[n][n];
    next = new Integer[n][n];

    // Copy input matrix and setup 'next' matrix for path reconstruction.
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] != POSITIVE_INFINITY) next[i][j] = j;
        dp[i][j] = matrix[i][j];
      }
    }
  }

  /**
   * Runs Floyd-Warshall to compute the shortest distance between every pair of nodes.
   *
   * @return The solved All Pairs Shortest Path (APSP) matrix.
   */
  public double[][] getApspMatrix() {
    solve();
    return dp;
  }

  // Executes the Floyd-Warshall algorithm.
  public void solve() {
    if (solved) return;

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

    // Identify negative cycles by propagating the value 'NEGATIVE_INFINITY'
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
   * @return An array of nodes indexes of the shortest path from 'start' to 'end'. If 'start' and
   *     'end' are not connected return an empty array. If the shortest path from 'start' to 'end'
   *     are reachable by a negative cycle return -1.
   */
  public List<Integer> reconstructShortestPath(int start, int end) {
    solve();
    List<Integer> path = new ArrayList<>();
    if (dp[start][end] == POSITIVE_INFINITY) return path;
    int at = start;
    for (; at != end; at = next[at][end]) {
      // Return null since there are an infinite number of shortest paths.
      if (at == REACHES_NEGATIVE_CYCLE) return null;
      path.add(at);
    }
    // Return null since there are an infinite number of shortest paths.
    if (next[at][end] == REACHES_NEGATIVE_CYCLE) return null;
    path.add(end);
    return path;
  }

  /* Example usage. */

  // Creates a graph with n nodes. The adjacency matrix is constructed
  // such that the value of going from a node to itself is 0.
  public static double[][] createGraph(int n) {
    double[][] matrix = new double[n][n];
    for (int i = 0; i < n; i++) {
      java.util.Arrays.fill(matrix[i], POSITIVE_INFINITY);
      matrix[i][i] = 0;
    }
    return matrix;
  }

  public static void main(String[] args) {
    // Construct graph.
    int n = 7;
    double[][] m = createGraph(n);

    // Add some edge values.
    m[0][1] = 2;
    m[0][2] = 5;
    m[0][6] = 10;
    m[1][2] = 2;
    m[1][4] = 11;
    m[2][6] = 2;
    m[6][5] = 11;
    m[4][5] = 1;
    m[5][4] = -2;

    FloydWarshallSolver solver = new FloydWarshallSolver(m);
    double[][] dist = solver.getApspMatrix();

    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        System.out.printf("This shortest path from node %d to node %d is %.3f\n", i, j, dist[i][j]);

    // Prints:
    // This shortest path from node 0 to node 0 is 0.000
    // This shortest path from node 0 to node 1 is 2.000
    // This shortest path from node 0 to node 2 is 4.000
    // This shortest path from node 0 to node 3 is Infinity
    // This shortest path from node 0 to node 4 is -Infinity
    // This shortest path from node 0 to node 5 is -Infinity
    // This shortest path from node 0 to node 6 is 6.000
    // This shortest path from node 1 to node 0 is Infinity
    // This shortest path from node 1 to node 1 is 0.000
    // This shortest path from node 1 to node 2 is 2.000
    // This shortest path from node 1 to node 3 is Infinity
    // ...

    System.out.println();

    // Reconstructs the shortest paths from all nodes to every other nodes.
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        List<Integer> path = solver.reconstructShortestPath(i, j);
        String str;
        if (path == null) {
          str = "HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)";
        } else if (path.size() == 0) {
          str = String.format("DOES NOT EXIST (node %d doesn't reach node %d)", i, j);
        } else {
          str =
              String.join(
                  " -> ",
                  path.stream()
                      .map(Object::toString)
                      .collect(java.util.stream.Collectors.toList()));
          str = "is: [" + str + "]";
        }

        System.out.printf("The shortest path from node %d to node %d %s\n", i, j, str);
      }
    }

    // Prints:
    // The shortest path from node 0 to node 0 is: [0]
    // The shortest path from node 0 to node 1 is: [0 -> 1]
    // The shortest path from node 0 to node 2 is: [0 -> 1 -> 2]
    // The shortest path from node 0 to node 3 DOES NOT EXIST (node 0 doesn't reach node 3)
    // The shortest path from node 0 to node 4 HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)
    // The shortest path from node 0 to node 5 HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)
    // The shortest path from node 0 to node 6 is: [0 -> 1 -> 2 -> 6]
    // The shortest path from node 1 to node 0 DOES NOT EXIST (node 1 doesn't reach node 0)
    // The shortest path from node 1 to node 1 is: [1]
    // The shortest path from node 1 to node 2 is: [1 -> 2]
    // The shortest path from node 1 to node 3 DOES NOT EXIST (node 1 doesn't reach node 3)
    // The shortest path from node 1 to node 4 HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)
    // The shortest path from node 1 to node 5 HAS AN ∞ NUMBER OF SOLUTIONS! (negative cycle case)
    // The shortest path from node 1 to node 6 is: [1 -> 2 -> 6]
    // The shortest path from node 2 to node 0 DOES NOT EXIST (node 2 doesn't reach node 0)
    // ...
  }
}
