/**
 * Implementation of the Minimum Weight Perfect Matching (MWPM) problem. In this problem you are
 * given a distance matrix which gives the distance from each node to every other node, and you want
 * to pair up all the nodes to one another minimizing the overall cost.
 *
 * <p>Tested against: UVA 10911 - Forming Quiz Teams
 *
 * <p>To Run: ./gradlew run -Palgorithm=dp.MinimumWeightPerfectMatching
 *
 * <p>Time Complexity: O(n * 2^n)
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.dp;

import java.awt.geom.*;
import java.util.*;

public class MinimumWeightPerfectMatching {

  // Inputs
  private final int n;
  private double[][] cost;

  // Internal
  private final int END_STATE;
  private boolean solved;

  // Outputs
  private double minWeightCost;
  private int[] matching;

  // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i])
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

  public double getMinWeightCost() {
    solveRecursive();
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
    solveRecursive();
    return matching;
  }

  // Recursive impl
  public void solveRecursive() {
    if (solved) return;
    Double[] dp = new Double[1 << n];
    int[] history = new int[1 << n];
    minWeightCost = f(END_STATE, dp, history);
    reconstructMatching(history);
    solved = true;
  }

  private double f(int state, Double[] dp, int[] history) {
    if (dp[state] != null) {
      return dp[state];
    }
    if (state == 0) {
      return 0;
    }
    int p1, p2;
    // Seek to find active bit position (p1)
    for (p1 = 0; p1 < n; p1++) {
      if ((state & (1 << p1)) > 0) {
        break;
      }
    }
    int bestState = -1;
    double minimum = Double.MAX_VALUE;

    for (p2 = p1 + 1; p2 < n; p2++) {
      // Position `p2` is on. Try matching the pair (p1, p2) together.
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

  public void solve() {
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
      if (dp[state] == null) {
        continue;
      }
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

  // Populates the `matching` array with a sorted deterministic matching sorted by lowest node
  // index. For example, if the perfect matching consists of the pairs (3, 4), (1, 5), (0, 2).
  // The matching is sorted such that the pairs appear in the ordering: (0, 2), (1, 5), (3, 4).
  // Furthermore, it is guaranteed that for any pair (a, b) that a < b.
  private void reconstructMatching(int[] history) {
    // A map between pairs of nodes that were matched together.
    int[] map = new int[n];
    int[] leftNodes = new int[n / 2];

    // Reconstruct the matching of pairs of nodes working backwards through computed states.
    for (int i = 0, state = END_STATE; state != 0; state = history[state]) {
      // Isolate the pair used by xoring the state with the state used to generate it.
      int pairUsed = state ^ history[state];

      int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
      int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;
    }

    // Sort the left nodes in ascending order.
    java.util.Arrays.sort(leftNodes);

    matching = new int[n];
    for (int i = 0; i < n / 2; i++) {
      matching[2 * i] = leftNodes[i];
      int rightNode = map[leftNodes[i]];
      matching[2 * i + 1] = rightNode;
    }
  }

  // Gets the zero base index position of the 1 bit in `k`. `k` must be a power of 2, so there is
  // only ever 1 bit in the binary representation of k.
  private int getBitPosition(int k) {
    int count = -1;
    while (k > 0) {
      count++;
      k >>= 1;
    }
    return count;
  }

  /* Example */

  public static void main(String[] args) {
    // test1();
    // for (int i = 0; i < 50; i++) {
    //   if (include(i)) System.out.printf("%2d %7s\n", i, Integer.toBinaryString(i));
    // }
  }

  private static boolean include(int i) {
    boolean toInclude = Integer.bitCount(i) >= 2 && Integer.bitCount(i) % 2 == 0;
    return toInclude;
  }

  private static void test1() {
    // int n = 18;
    int n = 6;
    List<Point2D> pts = new ArrayList<>();

    // Generate points on a 2D plane which will produce a unique answer
    for (int i = 0; i < n / 2; i++) {
      pts.add(new Point2D.Double(2 * i, 0));
      pts.add(new Point2D.Double(2 * i, 1));
    }
    Collections.shuffle(pts);

    double[][] cost = new double[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        cost[i][j] = pts.get(i).distance(pts.get(j));
      }
    }

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
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

  private static void test2() {
    double[][] costMatrix = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    if (cost != 2.0) {
      System.out.println("error cost not 2");
    }
    System.out.println(cost);
    // System.out.println(mwpm.solve2());

  }
}
