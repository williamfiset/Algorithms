/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds
 * the shortest path between a starting node and all other nodes in the graph. 
 * The algorithm also detects negative cycles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

public class BellmanFordAdjacencyList {

  // A directed edge with a cost
  public static class Edge {
    double cost;
    int from, to;
    public Edge(int from, int to, double cost) {
      this.to = to;
      this.from = from;
      this.cost = cost;
    }
  }

  // Create a graph with V vertices 
  public static List<Edge>[] createGraph(final int V) {
    List <Edge> [] graph = new List[V];
    for(int i = 0; i < V; i++) graph[i] = new ArrayList<>();
    return graph;
  }

  // Helper function to add an edge to the graph
  public static void addEdge(List<Edge>[] graph, int from, int to, double cost) {
    graph[from].add(new Edge(from, to, cost));
  }

  /**
   * An implementation of the Bellman-Ford algorithm. The algorithm finds
   * the shortest path between a starting node and all other nodes in the graph. 
   * The algorithm also detects negative cycles. If a node is part of a negative 
   * cycle then the minimum cost for that node is set to Double.NEGATIVE_INFINITY.
   *
   * @param graph - An adjacency list containing directed edges forming the graph
   * @param V     - The number of vertices in the graph.
   * @param start - The id of the starting node
   **/
  public static double[] bellmanFord(List<Edge>[] graph, int V, int start) {

    // Initialize the distance to all nodes to be infinity
    // except for the start node which is zero.
    double[] dist = new double[V];
    java.util.Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

    // For each vertex, apply relaxation for all the edges
    for (int i = 0; i < V-1; i++)
      for(List<Edge> edges : graph)
        for (Edge edge : edges)
          if (dist[edge.from] + edge.cost < dist[edge.to])
            dist[edge.to] = dist[edge.from] + edge.cost;

    // Run algorithm a second time to detect which nodes are part
    // of a negative cycle. A negative cycle has occurred if we 
    // can find a better path beyond the optimal solution.
    for (int i = 0; i < V-1; i++)
      for(List<Edge> edges : graph)
        for (Edge edge : edges)
          if (dist[edge.from] + edge.cost < dist[edge.to])
            dist[edge.to] = Double.NEGATIVE_INFINITY;

    // Return the array containing the shortest distance to every node
    return dist;

  }

  public static void main(String[] args) {
    
    int E = 10, V = 9, start = 0;
    List <Edge> [] graph = createGraph(V);
    addEdge(graph, 0, 1, 1);
    addEdge(graph, 1, 2, 1);
    addEdge(graph, 2, 4, 1);
    addEdge(graph, 4, 3, -3);
    addEdge(graph, 3, 2, 1);
    addEdge(graph, 1, 5, 4);
    addEdge(graph, 1, 6, 4);
    addEdge(graph, 5, 6, 5);
    addEdge(graph, 6, 7, 4);
    addEdge(graph, 5, 7, 3);
    double[] d = bellmanFord(graph, V, start);

    for (int i = 0; i < V; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i] );

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










