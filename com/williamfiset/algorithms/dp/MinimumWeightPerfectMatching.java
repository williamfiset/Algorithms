/*
WIP
*/


import java.util.*;
import java.awt.geom.*;

public class MinimumWeightPerfectMatching {

  private final int n;
  private double[][] cost;
  private double minWeightCost;
  private boolean solved;

  // The cost matrix should be a symmetric (i.e cost[i][j] = cost[j][i])
  public MinimumWeightPerfectMatching(double[][] cost) {
    if (cost == null) throw new IllegalArgumentException("Input cannot be null");
    n = cost.length;
    if (n % 2 != 0)
      throw new IllegalArgumentException("Matrix has an odd size, no perfect matching exists.");
    // TODO(william): Check if n is too large
    this.cost = cost;
  }

  public String pbs(int b) {
    return String.format("%1$10s", Integer.toBinaryString(b));
  }

  public double getMinWeightCost() {
    if (!solved) solve();
    return minWeightCost;
  }

  public void solve2() {
    // State is: Number of pairs solved for (zero based) and the binary encoded state.
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

  public void solve() {
    final int H = n / 2;
    
    Map<Integer, Double> pairs = new HashMap<>();
    Map<Integer, Double> states = new HashMap<>();
    for (int i = 0; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        int state = (1 << j) | (1 << i);
        pairs.put(state, cost[i][j]);
        states.put(state, cost[i][j]);
      }
    }

    for (int h = 1; h < H; h++) {
      Map<Integer, Double> newStates = new HashMap<>();
      System.out.println(states.size());
      for (int state : states.keySet()) {
        double stateCost = states.get(state);
        for (int pairState : pairs.keySet()) {
          double pairCost = pairs.get(pairState);
          if ((state & pairState) != 0) continue;
          int newState = state | pairState;
          double newCost = stateCost + pairCost;
          Double value = newStates.get(newState);
          value = value == null ? newCost : Math.min(newCost, value);
          newStates.put(newState, value);
        }
      }
      states = newStates;
    }

    int END_STATE = (1 << n) - 1;
    if (!states.containsKey(END_STATE)) {
      System.out.println("We got a problem. End state does not exist!");
      System.out.println(states);
    } else {
      minWeightCost = states.get(END_STATE);
    }

    solved = true;
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
