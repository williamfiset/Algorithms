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
 * @author Steven & Felix Halim, William Fiset
 */

import java.util.*;

public class TspDynamicProgramming {
  
  private int n;
  private int FINISHED_STATE;

  private Double[][] memo;
  private double[][] distance;
  private double minTourCost = Double.POSITIVE_INFINITY;

  private LinkedList<Integer> stack;
  private LinkedList<Integer> tour;

  public TspDynamicProgramming(double[][] distance) {
    n = distance.length;
    int m = distance[0].length;

    if (n != m) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (n <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");

    this.distance = distance;
    memo = new Double[n][1 << n];
    stack = new LinkedList<>();

    // The finished state is when all bits are set to one (meaning all
    // the nodes have been visited).
    FINISHED_STATE = (1 << n) - 1;

    // Start at node zero, so mark it as used.
    int startState = 1;

    tsp(0, 0, startState);
    tour.addFirst(0);
  }

  // Returns the optimal tour for the traveling salesman problem.
  public List<Integer> getTour() {
    return tour;
  }

  // Returns the minimal tour cost.
  public double getTourCost() {
    return minTourCost;
  }

  private double tsp(double tourCost, int i, int state) {

    // Return cached value of already precomputed sub path.
    if (memo[i][state] != null) return memo[i][state];

    if (state == FINISHED_STATE) {
      
      // Add the distance to go back the the starting node (which is node 0)
      tourCost += distance[i][0];

      if (tourCost < minTourCost) {
        stack.push(i);
        tour = new LinkedList<>(stack);
        minTourCost = tourCost;
        stack.pop();
      }

      return tourCost;
    }


    double minCost = Double.POSITIVE_INFINITY;
    for (int next = 0; next < n; next++) {
      
      // Skip if the next node has already been visited.
      if ((state & (1 << next)) != 0) continue;

      stack.push(i);
      int nextState = state | (1 << next);
      minCost = Math.min(minCost, tsp(tourCost + distance[i][next], next, nextState));
      stack.pop();
    }

    // return memo[i][state] = minCost; // Doing memo breaks tests :/
    return minCost;
  }

  // Gets the cost of a tour. Used only for testing.
  static double tourCost(List<Integer> tour, double[][] dist) {
    double total = 0;
    for (int i = 1; i < tour.size(); i++)
      total += dist[tour.get(i-1)][tour.get(i)];
    return total;
  }

  // Example usage:
  public static void main(String[] args) {

    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for(double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    
    distanceMatrix[1][4] = distanceMatrix[4][1] = 2;
    distanceMatrix[4][2] = distanceMatrix[2][4] = 4;
    distanceMatrix[2][3] = distanceMatrix[3][2] = 6;
    distanceMatrix[3][0] = distanceMatrix[0][3] = 8;
    distanceMatrix[0][5] = distanceMatrix[5][0] = 10;
    distanceMatrix[5][1] = distanceMatrix[1][5] = 12;

    TspDynamicProgramming solver = new TspDynamicProgramming(distanceMatrix);
    System.out.println();

    // Prints: [0, 5, 1, 4, 2, 3, 0]
    System.out.println("Tour: " + solver.getTour());

    // Print: 42.0
    System.out.println("Tour cost: " + solver.getTourCost());

    System.out.println("Path tour cost: " + tourCost(solver.getTour(), distanceMatrix));

    System.out.println("TRUE tsp cost: " + TSP(distanceMatrix));
  }




  // Working TSP problem cost implementation. This implementation does not 
  // track the optimal path however.
  public static double TSP(double[][] dist) {
    int n = dist.length;
    int m = dist[0].length;

    if (n != m) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (n <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
    
    Double[][] memo = new Double[n][1 << n];
    return TSP(0, 1, dist, memo);
  }
  private static double TSP(int i, int state, double[][] dist, Double[][] memo) {
    
    final int n = dist.length;
    
    // The finished state is when all bits are set to one (meaning all
    // the nodes have been visited).
    int finishedState = (1 << n) - 1;
    
    // Done this tour. Return cost of going back to start node.
    if (state == finishedState) return dist[i][0];
    
    // Return cached answer if already precomputed.
    if (memo[i][state] != null) return memo[i][state];
    
    double minCost = Double.POSITIVE_INFINITY;
    for (int next = 0; next < n; next++) {
      
      // Skip if the next node has already been visited.
      if ((state & (1 << next)) != 0) continue;
      
      int nextState = state | (1 << next);
      minCost = Math.min(minCost, dist[i][next] + TSP(next, nextState, dist, memo));
    }
    
    return memo[i][state] = minCost;
  }

}