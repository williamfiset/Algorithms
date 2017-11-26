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
  private final int FINISHED_STATE;

  private double[][] distance;
  private double minTourCost = Double.POSITIVE_INFINITY;

  private LinkedList<Integer> tour;

  public TspDynamicProgramming(double[][] distance) {
    
    this.distance = distance;
    N = distance.length;
    
    // Check the dimensions of the adjacency matrix
    if (N != distance[0].length) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (N <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");

    // The finished state is when all bits are set to one (meaning all
    // the nodes have been visited).
    FINISHED_STATE = (1 << N) - 1;

    // Run the algorithm and store the results
    tsp(distance, 0); // Start at node zero

  }

  // Returns the optimal tour for the traveling salesman problem.
  public List<Integer> getTour() {
    return tour;
  }

  // Returns the minimal tour cost.
  public double getTourCost() {
    return minTourCost;
  }

  // Working TSP problem cost implementation. This implementation does not 
  // track the optimal path however.
  private void tsp(double[][] dist, int start) {

    // Run the solver    
    Double[][] memo = new Double[N][1 << N];
    Integer[][] indexAdded = new Integer[N][1 << N];
    minTourCost = tsp(start, 1 << start, dist, memo, indexAdded, start);
    
    // Regenerate path
    int state = 1 << start;
    int index = start;
    tour = new LinkedList<Integer>();
    while (true) {
      tour.add(index);
      Integer nextIndex = indexAdded[index][state];
      if (nextIndex == null) break;
      int nextState = state | (1 << nextIndex);
      index = nextIndex;
      state = nextState;
    }
    tour.add(start);
    
  }
  private double tsp(int i, int state, double[][] dist, Double[][] memo, Integer[][] indexAdded, int start) {
    
    final int N = dist.length;
    
    // Done this tour. Return cost of going back to start node.
    if (state == FINISHED_STATE) return dist[i][start];
    
    // Return cached answer if already precomputed.
    if (memo[i][state] != null) return memo[i][state];
    
    double minCost = Double.POSITIVE_INFINITY;
    int index = -1;
    for (int next = 0; next < N; next++) {
      
      // Skip if the next node has already been visited.
      if ((state & (1 << next)) != 0) continue;
      
      int nextState = state | (1 << next);
      double newCost = dist[i][next] + tsp(next, nextState, dist, memo, indexAdded, start);
      if (newCost < minCost) {
        minCost = newCost;
        index = next;
      }
    }
    
    indexAdded[i][state] = index;
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

  }

}
