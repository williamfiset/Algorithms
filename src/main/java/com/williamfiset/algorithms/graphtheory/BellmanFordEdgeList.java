/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between a
 * starting node and all other nodes in the graph. The algorithm also detects negative cycles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

public class BellmanFordEdgeList {

  /**
   * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between
   * a starting node and all other nodes in the graph. The algorithm also detects negative cycles.
   * If a node is part of a negative cycle then the minimum cost for that node is set to
   * Double.NEGATIVE_INFINITY.
   *
   * @param edges - An edge list containing directed edges forming the graph
   * @param V - The number of vertices in the graph.
   * @param start - The id of the starting node
   */
  public static double[] bellmanFord(WeightedEdge<Double>[] edges, int V, int start) {

    double[] dist = new double[V];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

    // Only in the worst case does it take V-1 iterations for the Bellman-Ford
    // algorithm to complete. Another stopping condition is when we're unable to
    // relax an edge, this means we have reached the optimal solution early.
    boolean relaxedAnEdge = true;

    // For each vertex, apply relaxation for all the edges
    for (int v = 0; v < V - 1 && relaxedAnEdge; v++) {
      relaxedAnEdge = false;
      for (WeightedEdge<Double> edge : edges) {
        if (dist[edge.getFrom()] + edge.getCost() < dist[edge.getTo()]) {
          dist[edge.getTo()] = dist[edge.getFrom()] + edge.getCost();
          relaxedAnEdge = true;
        }
      }
    }

    // Run algorithm a second time to detect which nodes are part
    // of a negative cycle. A negative cycle has occurred if we
    // can find a better path beyond the optimal solution.
    relaxedAnEdge = true;
    for (int v = 0; v < V - 1 && relaxedAnEdge; v++) {
      relaxedAnEdge = false;
      for (WeightedEdge<Double> edge : edges) {
        if (dist[edge.getFrom()] + edge.getCost() < dist[edge.getTo()]) {
          dist[edge.getTo()] = Double.NEGATIVE_INFINITY;
          relaxedAnEdge = true;
        }
      }
    }

    // Return the array containing the shortest distance to every node
    return dist;
  }

  @SuppressWarnings("unchecked")
  public static void main(String[] args) {

    int E = 10, V = 9, start = 0;
    WeightedEdge<Double>[] edges = new WeightedEdge[E];
    edges[0] = new WeightedEdge<>(0, 1, 1d);
    edges[1] = new WeightedEdge<>(1, 2, 1d);
    edges[2] = new WeightedEdge<>(2, 4, 1d);
    edges[3] = new WeightedEdge<>(4, 3, -3d);
    edges[4] = new WeightedEdge<>(3, 2, 1d);
    edges[5] = new WeightedEdge<>(1, 5, 4d);
    edges[6] = new WeightedEdge<>(1, 6, 4d);
    edges[7] = new WeightedEdge<>(5, 6, 5d);
    edges[8] = new WeightedEdge<>(6, 7, 4d);
    edges[9] = new WeightedEdge<>(5, 7, 3d);

    double[] d = bellmanFord(edges, V, start);

    for (int i = 0; i < V; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);

    // Output:
    // The cost to get from node 0 to 0 is 0.00
    // The cost to get from node 0 to 1 is 1.00
    // The cost to get from node 0 to 2 is -Infinity
    // The cost to get from node 0 to 3 is -Infinity
    // The cost to get from node 0 to 4 is -Infinity
    // The cost to get from node 0 to 5 is 5.00
    // The cost to get from node 0 to 6 is 5.00
    // The cost to get from node 0 to 7 is 8.00
    // The cost to get from node 0 to 8 is Infinity

  }
}
