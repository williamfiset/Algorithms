/**
 * This file contains an implementation of the Floyd-Warshall algorithm to find
 * all pairs of shortest paths between nodes in a graph. We also demonstrate how
 * to detect negative cycles.
 *
 * Time Complexity: O(V^3)
 *
 * @author Micah Stairs
 **/

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

}