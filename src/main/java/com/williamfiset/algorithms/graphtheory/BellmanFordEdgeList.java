/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between a
 * starting node and all other nodes in the graph. The algorithm also detects negative cycles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

public class BellmanFordEdgeList {

  // A directed edge
  public static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      this.to = to;
      this.from = from;
      this.cost = cost;
    }
  }

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
  public static double[] bellmanFord(Edge[] edges, int V, int start) {

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
      for (Edge edge : edges) {
        if (dist[edge.from] + edge.cost < dist[edge.to]) {
          dist[edge.to] = dist[edge.from] + edge.cost;
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
      for (Edge edge : edges) {
        if (dist[edge.from] + edge.cost < dist[edge.to]) {
          dist[edge.to] = Double.NEGATIVE_INFINITY;
          relaxedAnEdge = true;
        }
      }
    }

    // Return the array containing the shortest distance to every node
    return dist;
  }

  public static void main(String[] args) {

    int E = 10, V = 9, start = 0;
    Edge[] edges = new Edge[E];
    edges[0] = new Edge(0, 1, 1);
    edges[1] = new Edge(1, 2, 1);
    edges[2] = new Edge(2, 4, 1);
    edges[3] = new Edge(4, 3, -3);
    edges[4] = new Edge(3, 2, 1);
    edges[5] = new Edge(1, 5, 4);
    edges[6] = new Edge(1, 6, 4);
    edges[7] = new Edge(5, 6, 5);
    edges[8] = new Edge(6, 7, 4);
    edges[9] = new Edge(5, 7, 3);

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
