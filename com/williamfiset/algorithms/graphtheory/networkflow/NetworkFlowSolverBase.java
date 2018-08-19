/**
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public abstract class NetworkFlowSolverBase {

  // To avoid overflow, set infinity to a value less than Long.MAX_VALUE;
  protected static final long INF = Long.MAX_VALUE / 2;

  public static class Edge {
    public int from, to;
    public Edge residual;
    public long flow, capacity, cost;
    final public long originalCapacity;
    
    public Edge(int from, int to, long capacity) {
      this.from = from;
      this.to = to;
      this.originalCapacity = this.capacity = capacity;
    }

    // Cost is only used for min-cost max-flow algorithms.
    public Edge(int from, int to, long capacity, long cost) {
      this(from, to, capacity);
      this.cost = cost;
    }

    public String toString(int s, int t) {
      String u = (from == s) ? "s" : ((from == t) ? "t" : String.valueOf(from));
      String v = (to == s) ? "s" : ((to == t) ? "t" : String.valueOf(to));
      return String.format("Edge %s -> %s = %d/%d", u, v, flow, originalCapacity);
    }
  }

  // Inputs: n = number of nodes, s = source, t = sink
  protected final int n, s, t;

  // 'visited' and 'visitedToken' are variables used in graph sub-routines to 
  // track whether a node has been visited or not. In particular, node 'i' was 
  // recently visited if visited[i] == visitedToken is true. This is handy 
  // because to mark all nodes as unvisited simply increment the visitedToken.
  protected int visitedToken = 1;
  protected int[] visited;

  // Indicates whether the network flow algorithm has ran. We should not need to
  // run the solver multiple times, because it always yields the same result.
  protected boolean solved;

  protected long maxFlow;
  protected long minCost;

  protected boolean[] minCut;
  protected List<Edge>[] graph;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n
   */
  public NetworkFlowSolverBase(int n, int s, int t) {
    this.n = n; this.s = s; this.t = t; 
    initializeGraph();
    minCut = new boolean[n];
    visited = new int[n];
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void initializeGraph() {
    graph = new List[n];
    for (int i = 0; i < n; i++)
      graph[i] = new ArrayList<Edge>();
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The capacity of the edge.
   */
  public void addEdge(int from, int to, long capacity) {
    Edge e1 = new Edge(from, to, capacity);
    Edge e2 = new Edge(to, from, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  /** Cost variant of {@link #addEdge(int, int, int)} for min-cost max-flow */
  public void addEdge(int from, int to, long capacity, long cost) {
    Edge e1 = new Edge(from, to, capacity, cost);
    Edge e2 = new Edge(to, from, 0, -cost);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to
   * inspect the {@link Edge#flow} compared to the {@link Edge#capacity} in 
   * each edge. This is useful if you want to figure out which edges were 
   * used during the max flow.
   */
  public List<Edge>[] getGraph() {
    execute();
    return graph;
  }

  // Returns the maximum flow from the source to the sink.
  public long getMaxFlow() {
    execute();
    return maxFlow;
  }

  // Returns the min cost from the source to the sink.
  // NOTE: This method only applies to min-cost max-flow algorithms.
  public long getMinCost() {
    execute();
    return minCost;
  }

  // Returns the min-cut of this flow network in which the nodes on the "left side"
  // of the cut with the source are marked as true and those on the "right side" 
  // of the cut with the sink are marked as false.
  public boolean[] getMinCut() {
    execute();
    return minCut;
  }

  // Wrapper method that ensures we only call solve() once
  private void execute() {
    if (solved) return; 
    solve();
    solved = true;
  }

  // Method to implement which solves the network flow problem.
  public abstract void solve();

}












