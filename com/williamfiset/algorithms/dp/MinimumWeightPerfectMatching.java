/**
 * Implementation of the Minimum Weight Perfect Matching (MWPM) problem. In this problem you
 * are given a distance matrix which gives the distance from each node to every other node, and
 * you want to pair up all the nodes to one another minimizing the overall cost.
 *
 * Time Complexity: O(n^3 * 2^n) 
 *
 * @author William Fiset
 */

import java.util.*;
import java.awt.geom.*;

public class MinimumWeightPerfectMatching {

  private final int n;
  private double[][] cost;
  private double minWeightCost;
  private int[] matching;
  private boolean solved;

  // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i])
  public MinimumWeightPerfectMatching(double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n % 2 != 0)
      throw new IllegalArgumentException("Matrix has an odd size, no perfect matching exists.");
    if (n > 32) throw new IllegalArgumentException("Matrix too large! A matrix that size for the MWPM problem with a time complexity of" +
                                                   "O(n^3*2^n) requires way too much computation and memory for a modern home computer.");
    this.cost = cost;
  }

  public double getMinWeightCost() {
    if (!solved) solve();
    return minWeightCost;
  }

  /**
   * Get the minimum weight cost matching.
   * The matching is returned as an array where the nodes at index 2*i and 2*i+1 form
   * a matched pair. For example, nodes at indexes (0, 1) are a pair, (2, 3) are 
   * another pair, etc...
   * 
   * How to iterate over the pairs:
   * <pre>
   * {@code
   *     MinimumWeightPerfectMatching mwpm = ...
   *     int[] matching = mwpm.getMinWeightCostMatching();
   *     for (int i = 0; i &lt; n/2; i += 2) {
   *       int node1 = matching[2*i];
   *       int node2 = matching[2*i+1];
   *       // Do something with the matched pair (node1, node2)
   *     }
   * }</pre>
   */
  public int[] getMinWeightCostMatching() {
    if (!solved) solve();
    return matching;
  }

  public void solve() {
    double[][] dp = new double[n >> 1][1 << n];
    
    int numPairs = (n*(n+1))/2;
    int[] pairStates = new int[numPairs];
    double[] pairCost = new double[numPairs];

    for (int i = 0, k = 0; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        int state = (1 << i) | (1 << j);
        dp[0][state] = cost[i][j];
        pairStates[k] = state;
        pairCost[k++] = cost[i][j];
      }
    }

    for (int k = 1; k < (n >> 1); k++) {
      for (int state = 0; state < (1 << n); state++) {
        double prevStateCost = dp[k-1][state];
        // A cost of zero means the previous state does not exist.
        if (prevStateCost == 0) continue;
        for (int j = 0; j < numPairs; j++) {

          int pairState = pairStates[j];
          // Ignore states which overlap
          if ((state & pairState) != 0) continue;

          int newState = state | pairState;
          double newCost = prevStateCost + pairCost[j];
          if (dp[k][newState] == 0 || newCost < dp[k][newState])
            dp[k][newState] = newCost;
        }
      }
    }

    int END_STATE = (1 << n) - 1;
    minWeightCost = dp[(n >> 1) - 1][END_STATE];
  }

    /* Example */

  public static void main(String[] args) {
    int n = 20;
    List<Point2D> pts = new ArrayList<>();

    // Generate points on a 2D plane which will produce a unique answer
    for (int i = 0; i < n/2; i++) {
      pts.add(new Point2D.Double(2*i, 0));
      pts.add(new Point2D.Double(2*i, 1));
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
    if (minCost != n/2) {
      System.out.printf("MWPM cost is wrong! Got: %.5f But wanted: %d\n", minCost, n/2);
    } else {
      System.out.printf("MWPM is: %.5f\n", minCost);
    }
  }

}
