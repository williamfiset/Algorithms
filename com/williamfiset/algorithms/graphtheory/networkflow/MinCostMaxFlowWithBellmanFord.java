/**
 * Min Cost Max Flow algorithm implemented with Bellman-Ford as a means of 
 * finding augmenting paths to support negative edge weights. 
 *
 * Time Complexity: O(E²V²)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import java.util.*;

public class MinCostMaxFlowWithBellmanFord {

  private static final long INF = 987654321;

  private static class Edge {
    Edge residual;
    int from, to;
    long flow, capacity;
    final long cost, originalCapacity;
    public Edge(int from, int to, long capacity, long cost) {
      this.to = to; 
      this.from = from;
      this.cost = cost;
      this.originalCapacity = this.capacity = capacity;
    }
  }

  // Inputs
  private int n, source, sink;

  // Internal
  private boolean solved;

  // Outputs
  private long maxFlow;
  private long minCost;
  private List<List<Edge>> graph;

  /**
   * Creates an instance of a flow network solver. Use the 
   * {@link #addEdge(int, int, int, int)} method to add edges to the graph.
   *
   * @param n      - The number of nodes in the graph including source and sink nodes.
   * @param source - The index of the source node, 0 <= source < n
   * @param sink   - The index of the source node, 0 <= sink < n
   */
  public MinCostMaxFlowWithBellmanFord(int n, int source, int sink) {
    this.n = n;
    this.source = source;
    this.sink = sink;
    initializeGraph();
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void initializeGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The number of units of capacity for this edge.
   * @param cost     - The cost of using up 1 unit of capacity along this edge.
   */
  public void addEdge(int from, int to, int capacity, int cost) {
    Edge e1 = new Edge(from, to, capacity, cost);
    Edge e2 = new Edge(to, from, 0, -cost);
    e1.residual = e2;
    e2.residual = e1;
    graph.get(from).add(e1);
    graph.get(to).add(e2);
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to
   * inspect each edge's remaining {@link Edge#capacity} compared to the
   * {@link Edge.originalCapacity} value. This is useful if you want to figure 
   * out which edges were used during the max flow.
   */
  public List<List<Edge>> getGraph() {
    solve();
    return graph;
  }

  // Returns the maximum flow from the source to the sink.
  public long getMaxFlow() {
    solve();
    return maxFlow;
  }

  // Returns the minimum cost that achieves the maximum flow.
  public long getMinCost() {
    solve();
    return minCost;
  }

  public void solve() {
    if (solved) return;
    maxFlow = minCost = 0;

    // Sum up the bottlenecks on each augmenting path to find the max flow.
    for(long flow = findBottleNeck(); flow != 0; flow = findBottleNeck())
      maxFlow += flow;

    // Compute the min cost.
    for (List<Edge> edges : graph)
      for (Edge edge : edges)
        minCost += edge.flow * edge.cost;

    solved = true;
  }

  /** 
   * Use the Bellman-Ford algorithm (which handles negative edge weights) to 
   * find an augmenting path through the flow network.
   */
  private List<Edge> getAugmentingPath() {
    long[] dist = new long[n];
    java.util.Arrays.fill(dist, INF);
    dist[source] = 0;

    Edge[] prev = new Edge[n];

    // For each vertex, relax all the edges in the graph, O(VE)
    for (int i = 0; i < n-1; i++) {
      for(int from = 0; from < n; from++) {
        for (Edge edge : graph.get(from)) {
          if (edge.capacity > 0 && dist[from] + edge.cost < dist[edge.to]) {
            dist[edge.to] = dist[from] + edge.cost;
            prev[edge.to] = edge;
          }
        }
      }
    }

    // Retrace augmenting path from sink back to the source.
    LinkedList<Edge> path = new LinkedList<>();
    for(Edge edge = prev[sink]; edge != null; edge = prev[edge.from])
      path.addFirst(edge);
    return path;
  }

  private long findBottleNeck() {
    List<Edge> path = getAugmentingPath();
    
    // Sink not reachable!
    if (path.isEmpty()) return 0;

    // Find bottle neck edge value along path.
    long bottleNeck = Long.MAX_VALUE;
    for(Edge edge : path) bottleNeck = Math.min(bottleNeck, edge.capacity);
    
    // Retrace path and update edge capacities.
    for(Edge edge : path) {
      Edge res = edge.residual;
      edge.flow += bottleNeck;
      edge.capacity -= bottleNeck;
      res.capacity  += bottleNeck;
    }

    return bottleNeck;
  }

    /* Example usage. */

  public static void main(String[] args) {
    testSmallNetwork();
  }

  private static void testSmallNetwork() {
    int n = 4;
    int source = n;
    int sink = n+1;
    MinCostMaxFlowWithBellmanFord solver;
    solver = new MinCostMaxFlowWithBellmanFord(n+2, source, sink);

    solver.addEdge(source, 1, 4, 10);
    solver.addEdge(source, 2, 2, 30);
    solver.addEdge(1, 2, 2, 10);
    solver.addEdge(1, sink, 0, 9999);
    solver.addEdge(2, sink, 4, 10);

    // Prints: Max flow: 4, Min cost: 140
    System.out.printf("Max flow: %d, Min cost: %d\n", solver.getMaxFlow(), solver.getMinCost());
  }

}


