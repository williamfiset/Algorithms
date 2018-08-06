/**
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkFlowBase {

  static final long INF = 987654321L;

  public static class Edge {
    Edge residual;
    int to;
    long flow, capacity;
    public Edge(int to, long capacity) {
      this.to = to; 
      this.capacity = capacity;
    }
  }

  // Inputs: n = number of nodes, s = source, t = sink
  protected final int n, s, t;

  // Internal
  protected int visitedToken = 1;
  protected int[] visited;

  // Outputs
  protected long maxFlow;
  protected boolean[] minCut;
  protected List<List<Edge>> graph;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n
   */
  public NetworkFlowBase(int n, int s, int t) {
    this.n = n; this.s = s; this.t = t; 
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
   * inspect the {@link Edge#flow} compared to the {@link Edge.capacity} in 
   * each edge. This is useful if you want to figure out which edges were 
   * used during the max flow.
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

  // Returns the min-cut of this flow network in which the nodes on the "left side"
  // of the cut with the source are marked as true and those on the "right side" 
  // of the cut with the sink are marked as false.
  public boolean[] getMinCut() {
    solve();
    return minCut;
  }

  // Method to implement.
  public abstract void solve();

}












