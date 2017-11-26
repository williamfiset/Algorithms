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
import java.util.Arrays;

public class TravelingSalesmanProblemDynamicProgramming {
   
  public static class TspSoln {
    double cost;
    List<Integer> path;
    public TspSoln(double cost, List<Integer> path) {
      this.cost = cost;
      this.path = new LinkedList<>(path);
    }
    public TspSoln(TspSoln soln) {
      this.cost = soln.cost;
      this.path = new LinkedList<Integer>(soln.path);
      this.path = new LinkedList<>();
      for(Integer x : soln.path) 
        this.path.add(x);
    }
    public String toString() {
      return cost + " " + path;
    }
  }

  // Input should be an NxN (N > 2) symmetric adjacency matrix.
  public static TspSoln tsp(double[][] dist) {
    int n = dist.length;
    int m = dist[0].length;

    if (n != m) throw new IllegalArgumentException("Matrix must be square (N x N)");
    if (n <= 2) throw new IllegalStateException("TSP on 0, 1 or 2 nodes doesn't make sense.");
    
    // TODO(williamfiset): Optimize to avoid storing all paths.
    TspSoln[][] memo = new TspSoln[n][1 << n];

    LinkedList<Integer> path = new LinkedList<>();
    TspSoln soln = tsp(0, 1, path, dist, memo);
    soln.path.add(0);

    // Double max = Double.POSITIVE_INFINITY;
    Set<List<Integer>> set = new HashSet<>();
    TspSoln best = null;
    for (TspSoln[] s : memo)
      for(TspSoln x : s)
        if (x != null) {
          if (set.contains(x.path))
            System.out.println(x.path);
          set.add(x.path);
        }
    
    // best.path.add(0);
    // return best;
    return soln;
  }
  
  static LinkedList<Integer> deepestDeepCopy(List<Integer> list) {
    int n = list.size();
    Integer[] ar = new Integer[n];
    for(int i = 0; i < n; i++)
      ar[i] = list.get(i);
    List<Integer> objList = Arrays.asList(ar);
    return new LinkedList<Integer>(objList);
  }

  /**
   * Private recursive method for solving the traveling salesman problem.
   * @method tsp
   * @param  int        i             The current node we're at.
   * @param  int        state         A bit mask representing the current state. 
   *                                  If the bit at index j is set then node j has
   *                                  already been visited.
   * @param  double[][] dist          The distance matrix.
   * @param  Double[][] memo          The DP memo table that stores cached results.
   *
   * @return The minimum tour value.
   */
  private static TspSoln tsp(int i, int state, LinkedList<Integer> path, double[][] dist, TspSoln[][] memo) {
    
    final int n = dist.length;
    
    // The finished state is when all bits are set to one (meaning all
    // the nodes have been visited).
    int finishedState = (1 << n) - 1;
    
    // Done this tour. Return cost of going back to start node.
    if (state == finishedState) {
      path.addLast(i);
      TspSoln soln = new TspSoln(dist[i][0], deepestDeepCopy(path));
      path.pollLast();
      return soln;
    }
    
    // Return cached answer if already precomputed.
    if (memo[i][state] != null) return new TspSoln(memo[i][state]);

    TspSoln answer = null;
    double minCost = Double.POSITIVE_INFINITY;

    for (int next = 0; next < n; next++) {
      
      // Skip if the next node has already been visited.
      if ((state & (1 << next)) != 0) continue;
      
      path.addLast(i);
      int nextState = state | (1 << next);
      TspSoln result = tsp(next, nextState, deepestDeepCopy(path), dist, memo);
      double resultCost = result.cost + dist[i][next];
      path.pollLast();

      if (resultCost < minCost) {
        minCost = resultCost;
        answer = new TspSoln(resultCost, deepestDeepCopy(result.path));
      }
    }
    
    return memo[i][state] = new TspSoln(answer);
  }
  
  public static double tourCost(List<Integer> tour, double[][] dist) {
    double total = 0;
    for (int i = 1; i < tour.size(); i++)
      total += dist[tour.get(i-1)][tour.get(i)];
    return total;
  }

  public static void main(String[] args) {
    test1();    
  }

  public static void test1() {

    int n = 6;
    double[][] dist = new double[n][n];
    for(double[] row : dist) java.util.Arrays.fill(row, 10000);
    addEdge(0, 3, 2, dist);
    addEdge(3, 2, 4, dist);
    addEdge(2, 4, 6, dist);
    addEdge(4, 1, 8, dist);
    addEdge(1, 5, 10, dist);
    addEdge(5, 0, 12, dist);
    
    for (double[] row : dist) System.out.println(Arrays.toString(row));
    System.out.println();

    TspSoln soln = tsp(dist);
    System.out.println(soln.path);
    System.out.println("Tour cost: " + tourCost(soln.path, dist));
    System.out.println("Soln Ans:  " + soln.cost);
  }

  public static void test2() {
    int n = 10;
    double[][] dist = new double[n][n];
    for(double[] row : dist) java.util.Arrays.fill(row, 10000);
    addEdge(0, 2, 1, dist);
    addEdge(2, 4, 2, dist);
    addEdge(4, 7, 3, dist);
    addEdge(7, 5, 4, dist);
    addEdge(5, 8, 5, dist);
    addEdge(8, 6, 6, dist);
    addEdge(6, 9, 7, dist);
    addEdge(9, 1, 8, dist);
    addEdge(1, 3, 9, dist);
    addEdge(3, 0, 10, dist);

    for (double[] row : dist) System.out.println(Arrays.toString(row));
    System.out.println();

    TspSoln soln = tsp(dist);
    System.out.println(soln.path);
    System.out.println("Tour cost: " + tourCost(soln.path, dist));
    System.out.println("Soln Ans:  " + soln.cost);    
  }

  public static void addEdge(int from, int to, double cost, double[][] m) {
    m[from][to] = m[to][from] = cost;
  }

}