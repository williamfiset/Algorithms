/**
 * This file contains a recursive implementation of the TSP problem using 
 * dynamic programming. The main idea is that since we need to do all n!
 * permutations of nodes to find the optimal solution that caching the results 
 * of sub paths can improve performance.
 *
 * For example, if one permutation is: '... D A B C' then later when we need to 
 * compute the value for the permutation '... E A B C' we should already have 
 * cached the answer for the sub path 'B C' starting at A.
 * 
 * Time Complexity: O(n^2 * 2^n)
 * Space Complexity: O(n * 2^n)
 *
 * @author Steven & Felix Halim, William Fiset, Micah Stairs
 */

import java.util.*;

public class TspDynamicProgramming {
  
  private final int N;
  private final int START_NODE;
  private final int FINISHED_STATE;

  private double[][] distance;
  private double minTourCost = Double.POSITIVE_INFINITY;

  private List<Integer> tour = new ArrayList<>();

  public TspDynamicProgramming(double[][] distance) {
    this(0, distance);
  }

  public TspDynamicProgramming(int startNode, double[][] distance) {
    
    this.distance = distance;
    N = distance.length;
    START_NODE = startNode;
    
    // Validate inputs.
    if (N <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
    if (N != distance[0].length) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (START_NODE < 0 || START_NODE >= N) throw new IllegalArgumentException("Starting node must be: 0 <= startNode < N");

    // The finished state is when the finished state mask has all bits are set to
    // one (meaning all the nodes have been visited).
    FINISHED_STATE = (1 << N) - 1;

    // Run the algorithm and store the results
    solve();
  }

  // Returns the optimal tour for the traveling salesman problem.
  public List<Integer> getTour() {
    return tour;
  }

  // Returns the minimal tour cost.
  public double getTourCost() {
    return minTourCost;
  }

  private void solve() {

    // Run the solver    
    int state = 1 << START_NODE;
    Double[][] memo = new Double[N][1 << N];
    Integer[][] prev = new Integer[N][1 << N];
    minTourCost = tsp(START_NODE, state, memo, prev);
    
    // Regenerate path
    int index = START_NODE;
    while (true) {
      tour.add(index);
      Integer nextIndex = prev[index][state];
      if (nextIndex == null) break;
      int nextState = state | (1 << nextIndex);
      state = nextState;
      index = nextIndex;
    }
    tour.add(START_NODE);
    
  }

  private double tsp(int i, int state, Double[][] memo, Integer[][] prev) {
    
    // Done this tour. Return cost of going back to start node.
    if (state == FINISHED_STATE) return distance[i][START_NODE];
    
    // Return cached answer if already computed.
    if (memo[i][state] != null) return memo[i][state];
    
    double minCost = Double.POSITIVE_INFINITY;
    int index = -1;
    for (int next = 0; next < N; next++) {
      
      // Skip if the next node has already been visited.
      if ((state & (1 << next)) != 0) continue;
      
      int nextState = state | (1 << next);
      double newCost = distance[i][next] + tsp(next, nextState, memo, prev);
      if (newCost < minCost) {
        minCost = newCost;
        index = next;
      }
    }
    
    prev[i][state] = index;
    return memo[i][state] = minCost;
  }

  // Example usage:
  public static void main(String[] args) {

    // Create adjacency matrix
    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    distanceMatrix[1][4] = distanceMatrix[4][1] = 2;
    distanceMatrix[4][2] = distanceMatrix[2][4] = 4;
    distanceMatrix[2][3] = distanceMatrix[3][2] = 6;
    distanceMatrix[3][0] = distanceMatrix[0][3] = 8;
    distanceMatrix[0][5] = distanceMatrix[5][0] = 10;
    distanceMatrix[5][1] = distanceMatrix[1][5] = 12;

    // Run the solver
    TspDynamicProgramming solver = new TspDynamicProgramming(distanceMatrix);

    // Prints: [0, 3, 2, 4, 1, 5, 0]
    System.out.println("Tour: " + solver.getTour());

    // Print: 42.0
    System.out.println("Tour cost: " + solver.getTourCost());

    test();

  }

  static void test() {
    double[][] dist = {
      {0, 4, 1, 9},
      {3, 0, 6, 11},
      {4, 1, 0, 2},
      {6, 5,-4, 0}
    };
    TspDynamicProgramming solver = new TspDynamicProgramming(dist);
    System.out.println("Tour: " + solver.getTour());
    System.out.println("Tour cost: " + solver.getTourCost());    
  }

}
