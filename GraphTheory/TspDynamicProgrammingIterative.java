/**
 * An implementation of the traveling salesman problem in Java using dynamic 
 * programming to improve the time complexity from O(n!) to O(n^2 * 2^n).
 *
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

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

    this.start = start;
    this.distance = distance;
  }

  // Returns the optimal tour for the traveling salesman problem.
  public List<Integer> getTour() {
    if (!ranSolver) solve();
    return tour;
  }

  // Returns the minimal tour cost.
  public double getTourCost() {
    if (!ranSolver) solve();
    return minTourCost;
  }

  // Solves the traveling salesman problem and caches solution.
  public void solve() {

    if (ranSolver) return;

    final int END_STATE = (1 << N) - 1;
    Double[][] memo = new Double[N][1 << N];
    Integer[][] path = new Integer[N][1 << N];

    // Add all outgoing edges from the starting node to memo table.
    for (int end = 0; end < N; end++) {
      if (end == start) continue;
      path[end][(1 << start) | (1 << end)] = start;
      memo[end][(1 << start) | (1 << end)] = distance[start][end];
    }

    for (int r = 3; r <= N; r++) {
      for (int subset : combinations(r, N)) {
        if (notIn(start, subset)) continue;
        for (int next = 0; next < N; next++) {
          if (next == start) continue;
          if (notIn(next, subset)) continue;
          int index = -1;
          int subsetWithoutNext = subset & ~(1 << next);
          double minDist = Double.POSITIVE_INFINITY;
          for (int end = 0; end < N; end++) {
            if (end == start || end == next) continue;
            if (notIn(end, subset)) continue;
            double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
            if (newDistance < minDist) {
              minDist = newDistance;
              index = end;
            }
          }
          path[next][subset] = index;
          memo[next][subset] = minDist;
        }
      }
    }

    // Connect tour back to starting node.
    int index = -1;
    for (int end = 0; end < N; end++) {
      if (end == start) continue;
      double tourCost = memo[end][END_STATE] + distance[end][start];
      if (tourCost < minTourCost) {
        minTourCost = tourCost;
        index = end;
      }
    }

    // Build tour path.
    int state = END_STATE;
    tour.add(start);
    while(true) {
      tour.add(index);
      Integer prevIndex = path[index][state];
      if (prevIndex == null) break;
      int nextState = state & ~(1 << index);
      state = nextState;
      index = prevIndex;
    }
    Collections.reverse(tour);
    ranSolver = true;
  }

  private static boolean notIn(int elem, int subset) {
    return ((1 << elem) & subset) == 0;
  }

  // This method finds all the combinations of {0,1...n-1} of size 'r' 
  // returned as a list of integer masks.
  public static List<Integer> combinations(int r, int n) {
    int[] set = new int[n];
    for(int i = 0; i < n; i++) set[i] = i;
    List<Integer> subsets = new ArrayList<>();
    boolean [] used = new boolean[set.length];
    combinations(set, r, 0, used, subsets);
    return subsets;
  }

  // To find all the combinations of size r we need to recurse until we have
  // selected r elements (aka r = 0), otherwise if r != 0 then we still need to select
  // an element which is found after the position of our last selected element
  private static void combinations(int[] set, int r, int at, boolean[] used, List<Integer> subsets) {
    final int N = set.length;

    // Return early if there are more elements left to select than what is available.
    int elementsLeftToPick = N - at;
    if (elementsLeftToPick < r) return;

    // We selected 'r' elements so we found a valid subset!
    if (r == 0) {
      int subset = 0;
      for (int i = 0; i < N; i++)
        if (used[i]) subset |= 1 << i;
      subsets.add(subset);
    } else {
      for (int i = at; i < N; i++) {

        // Try including this element
        used[i] = true;

        combinations(set, r - 1, i + 1, used, subsets);

        // Backtrack and try the instance where we did not include this element
        used[i] = false;
      }
    }
  }

  public static void main(String[] args) {
    // Create adjacency matrix
    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    distanceMatrix[5][0] = 10;
    distanceMatrix[1][5] = 12;
    distanceMatrix[4][1] = 2;
    distanceMatrix[2][4] = 4;
    distanceMatrix[3][2] = 6;
    distanceMatrix[0][3] = 8;

    int startNode = 0;
    TspDynamicProgrammingIterative solver = new TspDynamicProgrammingIterative(startNode, distanceMatrix);
    
    // Prints: [0, 3, 2, 4, 1, 5, 0]
    System.out.println("Tour: " + solver.getTour());

    // Print: 42.0
    System.out.println("Tour cost: " + solver.getTourCost());
  }
}












