package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Traveling Salesman Problem — Iterative DP with Bitmask
 *
 * Given a complete weighted graph of n nodes, find the minimum-cost
 * Hamiltonian cycle (a tour that visits every node exactly once and
 * returns to the starting node).
 *
 * This iterative (bottom-up) approach builds solutions for increasing
 * subset sizes. For each subset S of visited nodes and each endpoint
 * node i in S, we compute the minimum cost to reach i having visited
 * exactly the nodes in S. The recurrence is:
 *
 *   memo[next][S | (1 << next)] = min over end in S of
 *       memo[end][S] + distance[end][next]
 *
 * After filling the table, we close the tour by connecting back to
 * the start node and backtrack through the table to reconstruct
 * the optimal path.
 *
 * See also: {@link TspDynamicProgrammingRecursive} for the top-down variant.
 *
 * Time:  O(n^2 * 2^n)
 * Space: O(n * 2^n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class TspDynamicProgrammingIterative {

  private final int N, start;
  private final double[][] distance;
  private List<Integer> tour = new ArrayList<>();
  private double minTourCost = Double.POSITIVE_INFINITY;
  private boolean ranSolver = false;

  public TspDynamicProgrammingIterative(double[][] distance) {
    this(0, distance);
  }

  public TspDynamicProgrammingIterative(int start, double[][] distance) {
    N = distance.length;

    if (N <= 2) throw new IllegalStateException("N <= 2 not yet supported.");
    if (N != distance[0].length) throw new IllegalStateException("Matrix must be square (n x n)");
    if (start < 0 || start >= N) throw new IllegalArgumentException("Invalid start node.");
    if (N > 32)
      throw new IllegalArgumentException(
          "Matrix too large! A matrix that size for the DP TSP problem with a time complexity of"
              + "O(n^2*2^n) requires way too much computation for any modern home computer to handle");

    this.start = start;
    this.distance = distance;
  }

  /**
   * Returns the optimal tour for the traveling salesman problem.
   *
   * @return ordered list of node indices forming the optimal tour
   *         (starts and ends with the start node)
   */
  public List<Integer> getTour() {
    if (!ranSolver) solve();
    return tour;
  }

  /**
   * Returns the minimal tour cost.
   *
   * @return the total cost of the optimal Hamiltonian cycle
   */
  public double getTourCost() {
    if (!ranSolver) solve();
    return minTourCost;
  }

  /**
   * Solves the TSP and caches the result. Subsequent calls are no-ops.
   *
   * Phase 1: Fill the DP table bottom-up for subsets of size 2..N.
   * Phase 2: Close the tour by connecting the last node back to start.
   * Phase 3: Backtrack through the table to reconstruct the tour.
   */
  public void solve() {
    if (ranSolver) return;

    final int END_STATE = (1 << N) - 1;
    Double[][] memo = new Double[N][1 << N];

    // Phase 1a: Seed the memo table with direct edges from the start node.
    // memo[end][{start, end}] = distance from start to end
    for (int end = 0; end < N; end++) {
      if (end == start) continue;
      memo[end][(1 << start) | (1 << end)] = distance[start][end];
    }

    // Phase 1b: Build solutions for subsets of increasing size (3..N).
    // For each subset, try extending the path to each node in the subset.
    for (int r = 3; r <= N; r++) {
      for (int subset : combinations(r, N)) {
        if (notIn(start, subset)) continue;
        for (int next = 0; next < N; next++) {
          if (next == start || notIn(next, subset)) continue;
          // Consider all possible previous endpoints
          int subsetWithoutNext = subset ^ (1 << next);
          double minDist = Double.POSITIVE_INFINITY;
          for (int end = 0; end < N; end++) {
            if (end == start || end == next || notIn(end, subset)) continue;
            double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
            if (newDistance < minDist)
              minDist = newDistance;
          }
          memo[next][subset] = minDist;
        }
      }
    }

    // Phase 2: Close the tour — find the cheapest way to return to start.
    for (int i = 0; i < N; i++) {
      if (i == start) continue;
      double tourCost = memo[i][END_STATE] + distance[i][start];
      if (tourCost < minTourCost)
        minTourCost = tourCost;
    }

    // Phase 3: Reconstruct the tour by backtracking through the memo table.
    int lastIndex = start;
    int state = END_STATE;
    tour.add(start);

    for (int i = 1; i < N; i++) {
      int bestIndex = -1;
      double bestDist = Double.POSITIVE_INFINITY;
      for (int j = 0; j < N; j++) {
        if (j == start || notIn(j, state)) continue;
        double newDist = memo[j][state] + distance[j][lastIndex];
        if (newDist < bestDist) {
          bestIndex = j;
          bestDist = newDist;
        }
      }

      tour.add(bestIndex);
      state = state ^ (1 << bestIndex);
      lastIndex = bestIndex;
    }

    tour.add(start);
    Collections.reverse(tour);

    ranSolver = true;
  }

  /** Returns true if the given element's bit is not set in the subset bitmask. */
  private static boolean notIn(int elem, int subset) {
    return ((1 << elem) & subset) == 0;
  }

  /**
   * Generates all bitmasks of n bits where exactly r bits are set.
   * Used to enumerate subsets of a given size.
   *
   * @param r - number of bits to set
   * @param n - total number of bits
   * @return list of integer bitmasks
   */
  public static List<Integer> combinations(int r, int n) {
    List<Integer> subsets = new ArrayList<>();
    combinations(0, 0, r, n, subsets);
    return subsets;
  }

  /**
   * Recursively builds combinations by deciding whether to include
   * each bit position. Backtracks when not enough positions remain.
   */
  private static void combinations(int set, int at, int r, int n, List<Integer> subsets) {
    // Not enough positions remaining to pick r more bits
    int elementsLeftToPick = n - at;
    if (elementsLeftToPick < r) return;

    if (r == 0) {
      subsets.add(set);
    } else {
      for (int i = at; i < n; i++) {
        // Try including this element
        set ^= (1 << i);
        combinations(set, i + 1, r - 1, n, subsets);
        // Backtrack and try the instance where we did not include this element
        set ^= (1 << i);
      }
    }
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    // Create a 6-node directed graph with a known optimal tour
    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for (double[] row : distanceMatrix)
      java.util.Arrays.fill(row, 10000);

    distanceMatrix[5][0] = 10;
    distanceMatrix[1][5] = 12;
    distanceMatrix[4][1] = 2;
    distanceMatrix[2][4] = 4;
    distanceMatrix[3][2] = 6;
    distanceMatrix[0][3] = 8;

    int startNode = 0;
    TspDynamicProgrammingIterative solver =
        new TspDynamicProgrammingIterative(startNode, distanceMatrix);

    // Tour: [0, 3, 2, 4, 1, 5, 0]
    System.out.println("Tour: " + solver.getTour());

    // Tour cost: 42.0
    System.out.println("Tour cost: " + solver.getTourCost());
  }
}
