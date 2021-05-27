/**
 * Implementation of the Minimum Weight Perfect Matching (MWPM) problem. In this problem you are
 * given a distance matrix which gives the distance from each node to every other node, and you want
 * to pair up all the nodes to one another minimizing the overall cost.
 *
 * <p>Tested against: UVA 10911 - Forming Quiz Teams
 *
 * <p>To Run: ./gradlew run -Palgorithm=dp.MinimumWeightPerfectMatchingRecursive
 *
 * <p>Time Complexity: O(n * 2^n)
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.dp;

import java.awt.geom.*;
import java.util.*;

public class MinimumWeightPerfectMatchingRecursive implements MwpmInterface {

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
  public MinimumWeightPerfectMatchingRecursive(double[][] cost) {
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
    solve();
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
   * MinimumWeightPerfectMatchingRecursive mwpm = ...
   * int[] matching = mwpm.getMatching();
   * for (int i = 0; i < matching.length / 2; i++) {
   *   int node1 = matching[2*i];
   *   int node2 = matching[2*i+1];
   *   // Do something with the matched pair (node1, node2)
   * }
   * }</pre>
   */
  public int[] getMatching() {
    solve();
    return matching;
  }

  // Recursive impl
  // TODO(william): move to another file?
  private void solve() {
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
    test();
  }

  private static void test() {
    // mwpm is expected to be between nodes: 0 & 5, 1 & 2, 3 & 4
    double[][] costMatrix = {
      {0, 9, 9, 9, 9, 1},
      {9, 0, 1, 9, 9, 9},
      {9, 1, 0, 9, 9, 9},
      {9, 9, 9, 0, 1, 9},
      {9, 9, 9, 1, 0, 9},
      {1, 9, 9, 9, 9, 0},
    };

    MinimumWeightPerfectMatchingRecursive mwpm =
        new MinimumWeightPerfectMatchingRecursive(costMatrix);

    // Print minimum weight perfect matching cost
    double cost = mwpm.getMinWeightCost();
    if (cost != 3.0) {
      System.out.println("error cost not 3");
    } else {
      System.out.printf("Found MWPM of: %.3f\n", cost);
    }

    // Print matching
    int[] matching = mwpm.getMatching();
    for (int i = 0; i < matching.length / 2; i++) {
      int node1 = matching[2 * i];
      int node2 = matching[2 * i + 1];
      System.out.printf("Matched node %d with node %d\n", node1, node2);
    }

    // Prints:
    // Found MWPM of: 3.000
    // Matched node 0 with node 5
    // Matched node 1 with node 2
    // Matched node 3 with node 4
  }
}
