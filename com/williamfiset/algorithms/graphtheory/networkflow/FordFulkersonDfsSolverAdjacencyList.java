/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS
 * as a method of finding augmenting paths. FF allows you to find
 * the max flow through a directed graph and the min cut as a byproduct.
 *
 * Time Complexity: O(fE), where f is the max flow and E is the number of edges
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import java.util.ArrayList;
import java.util.List;

public class FordFulkersonDfsSolverAdjacencyList {

  public static class Edge {
    public Edge residual;
    public int to, capacity;
    public final int originalCapacity;
    public Edge(int to, int capacity) {
      this.to = to; 
      this.capacity = capacity;
      this.originalCapacity = capacity;
    }
  }

  // Inputs
  private int n, source, sink;

  // Internal
  private int visitedToken = 1;
  private int[] visited;
  private boolean solved;

  // Outputs
  private int maxFlow;
  private boolean[] minCut;
  private List<List<Edge>> graph;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n      - The number of nodes in the graph including source and sink nodes.
   * @param source - The index of the source node, 0 <= source < n
   * @param sink   - The index of the sink node, 0 <= sink < n
   */
  public FordFulkersonDfsSolverAdjacencyList(int n, int source, int sink) {
    this.n = n;
    initializeGraph();
    this.source = source;
    this.sink = sink;
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The capacity of the edge.
   */
  public void addEdge(int from, int to, int capacity) {
    Edge e1 = new Edge(to, capacity);
    Edge e2 = new Edge(from, 0);
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
  public int getMaxFlow() {
    solve();
    return maxFlow;
  }

  // Returns the min-cut of this flow network in which the nodes on the "left side"
  // of the cut with the source are marked as true and those on the "right side" 
  // of the cut with the sink are marked as false.
  public boolean[] getMinCut() {
    solve();
    return minCut;
  }

  // Performs the Ford-Fulkerson method applying a depth first search as
  // a means of finding an augmenting path. The input consists of a directed graph
  // with specified capacities on the edges.
  public void solve() {
    if (solved) return;

    visited = new int[n];
    minCut = new boolean[n];

    // Find max flow.
    int flow;
    do {
      // Try to find an augmenting path from source to sink
      flow = dfs(source, Integer.MAX_VALUE);
      visitedToken++;
      maxFlow += flow;
    } while(flow != 0);

    // Find min cut.
    for(int i = 0; i < n; i++)
      if (visited[i] == visitedToken-1)
        minCut[i] = true;

    solved = true;
  }

  private int dfs(int node, int flow) {
    // At sink node, return augmented path flow.
    if (node == sink) return flow;

    List<Edge> edges = graph.get(node);
    visited[node] = visitedToken;

    for (Edge edge : edges) {
      if (edge.capacity > 0 && visited[edge.to] != visitedToken) {

        // Update flow to be bottleneck 
        if (edge.capacity < flow) flow = edge.capacity;
        int dfsFlow = dfs(edge.to, flow);

        // Update edge capacities
        if (dfsFlow > 0) {
          Edge res = edge.residual;
          edge.capacity -= dfsFlow;
          res.capacity  += dfsFlow;
          return dfsFlow;
        }

      }
    }
    return 0;
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void initializeGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

}

