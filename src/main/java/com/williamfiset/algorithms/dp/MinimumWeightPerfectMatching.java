package com.williamfiset.algorithms.dp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Minimum Weight Perfect Matching (MWPM)
 *
 * Given n nodes and a symmetric distance matrix, pairs up all nodes to minimize
 * total matching cost. Uses bitmask DP where each state represents a subset of
 * matched nodes. Two solvers are provided:
 *
 *   - Top-down (recursive with memoization): naturally skips unreachable states
 *   - Bottom-up (iterative): builds solutions from pairs upward
 *
 * Requires n to be even (otherwise no perfect matching exists) and n <= 32
 * (bitmask representation limit).
 *
 * Tested against: UVA 10911 - Forming Quiz Teams
 *
 * Time:  O(n^2*2^n)
 * Space: O(2^n)
 *
 * @author William Fiset
 */
public class MinimumWeightPerfectMatching {

  // Inputs
  private final int n;
  private final double[][] cost;

  // Internal
  private final int END_STATE;
  private boolean solved;

  // Outputs
  private double minWeightCost;
  private int[] matching;

  /**
   * Creates a MWPM solver for the given cost matrix.
   *
   * @param cost symmetric n x n distance matrix (cost[i][j] = cost[j][i])
   *
   * @throws IllegalArgumentException if matrix is null, empty, odd-sized, or too large
   */
  public MinimumWeightPerfectMatching(double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n == 0) throw new IllegalArgumentException("Matrix size is zero");
    if (n % 2 != 0)
      throw new IllegalArgumentException("Matrix has an odd size, no perfect matching exists.");
    if (n > 32)
      throw new IllegalArgumentException(
          "Matrix too large! A matrix that size for the MWPM problem with a time complexity of"
              + "O(n^2*2^n) requires way too much computation and memory for a modern home computer.");
    END_STATE = (1 << n) - 1;
    this.cost = cost;
  }

  /**
   * Returns the minimum total cost of a perfect matching.
   * Lazily solves using the recursive solver if neither solver has run yet.
   */
  public double getMinWeightCost() {
    if (!solved) solveRecursive();
    return minWeightCost;
  }

  /**
   * Get the minimum weight cost matching. The matching is returned as an array where the nodes at
   * index 2*i and 2*i+1 form a matched pair. For example, nodes at indexes (0, 1) are a pair, (2,
   * 3) are another pair, etc...
   *
   * <p>How to iterate over the pairs:
   *
   * <pre>{@code
   * MinimumWeightPerfectMatching mwpm = ...
   * int[] matching = mwpm.getMinWeightCostMatching();
   * for (int i = 0; i < matching.length / 2; i++) {
   *   int node1 = matching[2*i];
   *   int node2 = matching[2*i+1];
   *   // Do something with the matched pair (node1, node2)
   * }
   * }</pre>
   */
  public int[] getMinWeightCostMatching() {
    if (!solved) solveRecursive();
    return matching;
  }

  // ==================== Solver 1: Top-down (recursive with memoization) ====================

  /**
   * Solves using top-down recursion with memoization. Starting from the full set
   * of nodes, it finds the lowest-numbered unmatched node and tries pairing it
   * with every other unmatched node, recursing on the reduced state.
   *
   * This approach naturally skips unreachable states (states that can't be formed
   * by removing pairs from the full set), so it often visits fewer states than
   * the iterative solver.
   */
  public void solveRecursive() {
    if (solved) return;
    Double[] dp = new Double[1 << n];
    int[] history = new int[1 << n];
    minWeightCost = f(END_STATE, dp, history);
    reconstructMatching(history);
    solved = true;
  }

  private double f(int state, Double[] dp, int[] history) {
    if (dp[state] != null) return dp[state];
    if (state == 0) return 0;

    // Find the lowest set bit position (p1) — always pair this node first
    int p1 = Integer.numberOfTrailingZeros(state);

    int bestState = -1;
    double minimum = Double.MAX_VALUE;

    // Try pairing p1 with every other set bit
    for (int p2 = p1 + 1; p2 < n; p2++) {
      if ((state & (1 << p2)) > 0) {
        int reducedState = state ^ (1 << p1) ^ (1 << p2);
        double matchCost = f(reducedState, dp, history) + cost[p1][p2];
        if (matchCost < minimum) {
          minimum = matchCost;
          bestState = reducedState;
        }
      }
    }
    history[state] = bestState;
    return dp[state] = minimum;
  }

  // ==================== Solver 2: Bottom-up (iterative) ====================

  /**
   * Solves using bottom-up iterative DP. Pre-computes all n*(n-1)/2 pair states,
   * then iterates over all bitmask states in ascending order, extending each
   * valid matching by adding a non-overlapping pair.
   *
   * This approach visits all 2^n states systematically. It avoids recursion
   * overhead and stack depth limits, making it better suited for larger n.
   */
  public void solveIterative() {
    if (solved) return;

    // The DP state is encoded as a bitmask where the i'th bit is flipped on if the i'th node is
    // included in the state. Encoding the state this way allows us to compactly represent selecting
    // a subset of the nodes present in the matching. Furthermore, it allows using the '&' binary
    // operator to compare states to see if they overlap and the '|' operator to combine states.
    //
    // dp[i] contains the optimal cost of the MWPM for the nodes captured in the binary
    // representation of `i`. The dp table is always half empty because all states with an odd
    // number of nodes do not have a MWPM.
    Double[] dp = new Double[1 << n];

    // Memo table to save the history of the chosen states. This table is used to reconstruct the
    // chosen pairs of nodes after the algorithm has executed.
    int[] history = new int[1 << n];

    // All the states consisting of pairs of nodes are the building blocks of this algorithm.
    // In every iteration, we try to add a pair of nodes to previous state to construct a larger
    // matching.
    final int numPairs = (n * (n - 1)) / 2;
    int[] pairStates = new int[numPairs];
    double[] pairCost = new double[numPairs];

    int k = 0;
    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int state = (1 << i) | (1 << j);
        dp[state] = cost[i][j];
        pairStates[k] = state;
        pairCost[k] = cost[i][j];
        k++;
      }
    }

    for (int state = 0b11; state < (1 << n); state++) { // O(2^n)
      // Skip states with an odd number of bits (nodes). It's easier (and faster) to
      // check dp[state] instead of calling `Integer.bitCount` for the bit count.
      if (dp[state] == null) continue;

      for (int i = 0; i < numPairs; i++) { // O(n^2)
        int pair = pairStates[i];
        // Ignore states which overlap
        if ((state & pair) != 0) continue;

        int newState = state | pair;
        double newCost = dp[state] + pairCost[i];
        if (dp[newState] == null || newCost < dp[newState]) {
          dp[newState] = newCost;
          // Save the fact that we went from 'state' -> 'newState'. From this we will be able to
          // reconstruct which pairs of nodes were taken by looking at 'state' xor 'newState' which
          // should give us the binary representation (state) of the pair used.
          history[newState] = state;
        }
      }
    }

    reconstructMatching(history);

    minWeightCost = dp[END_STATE];
    solved = true;
  }

  /**
   * Populates the {@code matching} array with a sorted deterministic matching.
   * For example, if the perfect matching consists of the pairs (3, 4), (1, 5), (0, 2),
   * the output is sorted as: (0, 2), (1, 5), (3, 4).
   * For any pair (a, b), it is guaranteed that a < b.
   */
  private void reconstructMatching(int[] history) {
    int[] map = new int[n];
    int[] leftNodes = new int[n / 2];

    // Walk backwards through computed states to recover matched pairs
    for (int i = 0, state = END_STATE; state != 0; state = history[state]) {
      int pairUsed = state ^ history[state];

      int leftNode = Integer.numberOfTrailingZeros(Integer.lowestOneBit(pairUsed));
      int rightNode = Integer.numberOfTrailingZeros(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;
    }

    Arrays.sort(leftNodes);

    matching = new int[n];
    for (int i = 0; i < n / 2; i++) {
      matching[2 * i] = leftNodes[i];
      matching[2 * i + 1] = map[leftNodes[i]];
    }
  }

  public static void main(String[] args) {
    test1();
    test2();
  }

  // Example 1: Uses the RECURSIVE solver.
  // Generates 2D points that form vertical pairs, shuffles them, and verifies
  // the MWPM correctly matches each pair (cost = 1 per pair, total = n/2).
  private static void test1() {
    System.out.println("=== Recursive solver ===");
    int n = 6;
    List<Point2D> pts = new ArrayList<>();

    // Generate points on a 2D plane which will produce a unique answer
    for (int i = 0; i < n / 2; i++) {
      pts.add(new Point2D.Double(2 * i, 0));
      pts.add(new Point2D.Double(2 * i, 1));
    }
    Collections.shuffle(pts);

    double[][] cost = new double[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        cost[i][j] = pts.get(i).distance(pts.get(j));

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    mwpm.solveRecursive();

    double minCost = mwpm.getMinWeightCost();
    if (minCost != n / 2) {
      System.out.printf("MWPM cost is wrong! Got: %.5f But wanted: %d\n", minCost, n / 2);
    } else {
      System.out.printf("MWPM is: %.5f\n", minCost);
    }

    int[] matching = mwpm.getMinWeightCostMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int ii = matching[2 * i];
      int jj = matching[2 * i + 1];
      System.out.printf(
          "(%d, %d) <-> (%d, %d)\n",
          (int) pts.get(ii).getX(),
          (int) pts.get(ii).getY(),
          (int) pts.get(jj).getX(),
          (int) pts.get(jj).getY());
    }
  }

  // Example 2: Uses the ITERATIVE solver.
  // Simple 4-node symmetric matrix where the optimal matching costs 2.0.
  private static void test2() {
    System.out.println("=== Iterative solver ===");
    double[][] costMatrix = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    mwpm.solveIterative();

    double cost = mwpm.getMinWeightCost();
    if (cost != 2.0) {
      System.out.println("error cost not 2");
    }
    System.out.println(cost); // 2.0
  }
}
