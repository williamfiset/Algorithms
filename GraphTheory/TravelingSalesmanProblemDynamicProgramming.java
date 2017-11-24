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

public class TravelingSalesmanProblemDynamicProgramming {
  
  public static double tsp(double[][] dist) {
    int n = dist.length;
    int m = dist[0].length;

    if (n != m) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (n <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
    
    Double[][] memo = new Double[n][1 << n];
    return tsp(0, 1, dist, memo);
  }
  
  /**
   * Private recursive method for solving the traveling salesman problem.
   * @method tsp
   * @param  int        i             The current node we're at.
   * @param  int        state         A bit mask representing the current state. 
   *                                  If the bit at index j is set then node j has
   *                                  already been visited.
   * @param  double[][] dist          The distance matrix
   * @param  Double[][] memo          The DP memo table that stores cached results.
   *
   * @return The minimum tour value.
   */
  private static double tsp(int i, int state, double[][] dist, Double[][] memo) {
    
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
      minCost = Math.min(minCost, dist[i][next] + tsp(next, nextState, dist, memo));
    }
    
    return memo[i][state] = minCost;
  }
  
  public static void main(String[] args) {
    int n = 5;
    double[][] dist = new double[n][n];
    for(double[] row : dist) java.util.Arrays.fill(row, 100);
    
    dist[1][3] = 1;
    dist[3][0] = 2;
    dist[0][2] = 3;
    dist[2][4] = 4;
    dist[4][1] = 5;
    
    // Prints '15.0'
    System.out.println(TravelingSalesmanProblemDynamicProgramming.tsp(dist));
    
  }
  
}