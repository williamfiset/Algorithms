/**
 * Implementation of the Minimum Weight Perfect Matching (MWPM) problem. In this problem you are
 * given a distance matrix which gives the distance from each node to every other node, and you want
 * to pair up all the nodes to one another minimizing the overall cost.
 *
 * <p>Tested against: UVA 10911 - Forming Quiz Teams
 *
 * <p>To Run: ./gradlew run -Palgorithm=dp.WeightedMaximumCardinalityMatchingRecursive
 *
 * <p>Time Complexity: O(n * 2^n)
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.dp;

import java.awt.geom.*;
import java.util.*;

public class WeightedMaximumCardinalityMatchingRecursive implements MwpmInterface {

  // Inputs
  private int n;
  private Double[][] cost;

  // Internal
  private final int END_STATE;
  private int artificialNodeId = -1;
  private boolean isOdd;
  private boolean solved;

  // Outputs
  private double minWeightCost;
  private int[] matching;

  // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i]) and have a cost of `null`
  // between nodes i and j if no edge exists between those two nodes.
  public WeightedMaximumCardinalityMatchingRecursive(Double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n <= 1) throw new IllegalArgumentException("Invalid matrix size: " + n);
    setCostMatrix(cost);
    END_STATE = (1 << n) - 1;
  }

  // Sets the cost matrix. If the number of nodes in the graph is odd, add an artificial
  // node that connects to every other node with a cost of infinity. This will make it easy
  // to find a perfect matching and remove in the artificial node in the end.
  private void setCostMatrix(Double[][] inputMatrix) {
    isOdd = (n % 2 == 0) ? false : true;
    Double[][] newCostMatrix = null;

    if (isOdd) {
      newCostMatrix = new Double[n + 1][n + 1];
    } else {
      newCostMatrix = new Double[n][n];
    }

    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        newCostMatrix[i][j] = inputMatrix[i][j];
      }
    }

    if (isOdd) {
      for (int i = 0; i < n; i++) {
        newCostMatrix[n][i] = null;
        newCostMatrix[i][n] = null;
      }
      newCostMatrix[n][n] = 0.0;
      artificialNodeId = n;
      n++;
    }

    this.cost = newCostMatrix;
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
   * WeightedMaximumCardinalityMatchingRecursive mwpm = ...
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

  private void solve() {
    if (solved) return;
    Rv[] dp = new Rv[1 << n];
    int[] history = new int[1 << n];

    Rv rv = f(END_STATE, dp, history);
    minWeightCost = rv.cost;

    reconstructMatching(history);
    solved = true;
  }

  // Rv = Return Value
  private static class Rv {
    int invisibleEdgesSelected = 0;
    double cost = Double.MAX_VALUE;

    public Rv() {}

    public Rv(double cost) {
      this.cost = cost;
    }

    public Rv(Rv rv) {
      this.cost = rv.cost;
      this.invisibleEdgesSelected = rv.invisibleEdgesSelected;
    }

    @Override
    public String toString() {
      return cost + " " + invisibleEdgesSelected;
    }
  }

  private Rv f(int state, Rv[] dp, int[] history) {
    if (dp[state] != null) {
      return dp[state];
    }
    if (state == 0) {
      return new Rv(0);
    }
    int p1, p2;
    // Seek to find active bit position (p1)
    for (p1 = 0; p1 < n; p1++) {
      if ((state & (1 << p1)) > 0) {
        break;
      }
    }

    int bestState = -1;
    Rv rv = new Rv();
    rv.invisibleEdgesSelected = 99999;

    for (p2 = p1 + 1; p2 < n; p2++) {
      // Position `p2` is on. Try matching the pair (p1, p2) together.
      if ((state & (1 << p2)) > 0) {
        int reducedState = state ^ (1 << p1) ^ (1 << p2);
        Rv matchCost = new Rv(f(reducedState, dp, history));
        if (cost[p1][p2] == null) {
          matchCost.invisibleEdgesSelected++;
        } else {
          matchCost.cost += cost[p1][p2];
        }
        if (shouldUpdateMinVal(rv, matchCost)) {
          rv = new Rv(matchCost);
          bestState = reducedState;
        }
      }
    }
    history[state] = bestState;
    return dp[state] = new Rv(rv);
  }

  private static boolean shouldUpdateMinVal(Rv rv1, Rv rv2) {
    if (rv2.invisibleEdgesSelected < rv1.invisibleEdgesSelected) {
      return true;
    }
    if (rv1.invisibleEdgesSelected == rv2.invisibleEdgesSelected && rv2.cost < rv1.cost) {
      return true;
    }
    return false;
  }

  private double ff(int state, Double[] dp, int[] history) {
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
        double matchCost = ff(reducedState, dp, history) + cost[p1][p2];
        System.out.printf("Match cost: %f\n", matchCost);
        if (matchCost < minimum) {
          minimum = matchCost;
          bestState = reducedState;
        }
        System.out.printf("%f | %f\n", cost[p1][p2], minimum);
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

    int matchingSize = 0;

    // Reconstruct the matching of pairs of nodes working backwards through computed states.
    for (int i = 0, state = END_STATE; state != 0; state = history[state]) {
      // Isolate the pair used by xoring the state with the state used to generate it.
      int pairUsed = state ^ history[state];

      int leftNode = getBitPosition(Integer.lowestOneBit(pairUsed));
      int rightNode = getBitPosition(Integer.highestOneBit(pairUsed));

      leftNodes[i++] = leftNode;
      map[leftNode] = rightNode;

      if (cost[leftNode][rightNode] != null) matchingSize++;
    }

    // Sort the left nodes in ascending order.
    java.util.Arrays.sort(leftNodes);

    matchingSize = matchingSize * 2;
    matching = new int[matchingSize];

    for (int i = 0, j = 0; i < n / 2; i++) {
      int leftNode = leftNodes[i];
      int rightNode = map[leftNodes[i]];
      // Ignore the artificial node when there is an odd number of nodes.
      if (isOdd && (leftNode == artificialNodeId || rightNode == artificialNodeId)) {
        continue;
      }
      // Only match edges which actually exist
      if (cost[leftNode][rightNode] != null) {
        matching[2 * j] = leftNode;
        matching[2 * j + 1] = rightNode;
        j++;
      }
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
    Double[][] costMatrix = {
      {0.0, 9.0, 9.0, 9.0, 9.0, 1.0},
      {9.0, 0.0, 1.0, 9.0, 9.0, 9.0},
      {9.0, 1.0, 0.0, 9.0, 9.0, 9.0},
      {9.0, 9.0, 9.0, 0.0, 1.0, 9.0},
      {9.0, 9.0, 9.0, 1.0, 0.0, 9.0},
      {1.0, 9.0, 9.0, 9.0, 9.0, 0.0},
    };

    WeightedMaximumCardinalityMatchingRecursive mwpm =
        new WeightedMaximumCardinalityMatchingRecursive(costMatrix);

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
