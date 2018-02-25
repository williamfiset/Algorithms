/**
 * This file contains an implementation of the Floyd-Warshall algorithm to find
 * all pairs of shortest paths between nodes in a graph. We also demonstrate how
 * to detect negative cycles.
 *
 * Time Complexity: O(V^3)
 *
 * @author Micah Stairs
 **/
import java.util.*;
public class FloydWarshall {

  /**
   * Given an adjacency matrix with edge weights between nodes, where
   * Double.POSITIVE_INFINITY is used to indicate that two nodes are not, 
   * connected, this method mutates the given matrix in order to give the
   * shortest distance between all pairs of nodes. Double.NEGATIVE_INFINITY
   * is used to indicate that the edge between node i and node j is part of a
   * negative cycle.
   *
   * NOTE: Usually the diagonal of the adjacency matrix is all  zeros (i.e.
   * distance[i][i] = 0 for all i) since there is typically no cost to go from
   * a node to itself, but this may depend on your graph and the problem you are
   * trying to solve.
   **/
  public static void floydWarshall(double[][] distance) {
    int v = distance.length;

    // Compute all pairs shortest paths
    for (int k = 0; k < v; k++)
      for (int i = 0; i < v; i++)
        for (int j = 0; j < v; j++)
          if (distance[i][k] + distance[k][j] < distance[i][j])
            distance[i][j] = distance[i][k] + distance[k][j];

    // Identify negative cycles (you can comment this block of code out if you
    // know that no negative cycles exist)
    for (int k = 0; k < v; k++)
      for (int i = 0; i < v; i++)
        for (int j = 0; j < v; j++)
          if (distance[i][k] + distance[k][j] < distance[i][j])
            distance[i][j] = Double.NEGATIVE_INFINITY;

  }

  public static double[][] floydWarshallExtraMemory(double[][] m) {
    int v = m.length;
    double[][][] dp = new double[v][v][v];

    // Compute all pairs shortest paths
    for (int k = 0; k < v; k++) {
      for (int i = 0; i < v; i++) {
        for (int j = 0; j < v; j++) {
          if (k == 0) dp[k][i][j] = m[i][j];
          else dp[k][i][j] = Math.min(dp[k-1][i][j], dp[k-1][i][k] + dp[k-1][k][j]);
        }
      }

    }

    for (int i = 0; i < v; i++) {
      System.out.println(Arrays.toString(dp[v-1][i]));
    }

    return null;
  }
static final double INF = Double.POSITIVE_INFINITY;
  public static void main(String[] args) {
    // double[][] matrix1 = new double[][] {
    //   {  0, INF, INF, INF, INF},
    //   {  1,   0,   7, INF, INF},
    //   {INF,   3,   0, INF, INF},
    //   { 13, INF,   4,   0, INF},
    //   {INF, INF,   3,   0,   0}
    // };
    // double[][] matrix2 = new double[][] {
    //   {  0,   3,   1,   8, INF},
    //   {  2,   0,   9,   4, INF},
    //   {INF, INF,   0, INF,  -2},
    //   {INF, INF,   1,   0, INF},
    //   {INF, INF, INF,   0,   0}
    // };
    double[][] matrix2FromSlides = new double[][] {
      {   0,   6, INF,  25,   3},
      {   1,   0,   6,   1,   3},
      { INF,   1,   0,   2,   3},
      {   4,   4,   4,   0, INF},
      {   4,   3,   5, INF,   0}
    };
    FloydWarshall.floydWarshall(matrix2FromSlides);
    for (double[] r : matrix2FromSlides) {
      System.out.println(Arrays.toString(r));
    }
    System.out.println();
    double[][] matrix2FromSlides2 = new double[][] {
      {   0,   6, INF,  25,   3},
      {   1,   0,   6,   1,   3},
      { INF,   1,   0,   2,   3},
      {   4,   4,   4,   0, INF},
      {   4,   3,   5, INF,   0}
    };
    FloydWarshall.floydWarshallExtraMemory(matrix2FromSlides2);
      
  }

}








