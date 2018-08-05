/**
 * Implementation of the Capacity Scaling algorithm using a DFS
 * as a method of finding augmenting paths.
 *
 * Time Complexity: O(E^2log(C))
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class CapacityScalingSolverAdjacencyList {

  public static class Edge {
    int to;
    Edge residual;
    long flow, capacity;
    public Edge(int to, long capacity) {
      this.to = to; 
      this.capacity = capacity;
    }
  }

  // Inputs
  private int n, source, sink;

  // Internal
  private int delta;
  private int visitedToken = 1;
  private int[] visited;
  private boolean solved;

  // Outputs
  private long maxFlow;
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
  public CapacityScalingSolverAdjacencyList(int n, int source, int sink) {
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
    delta = Math.max(delta, capacity);
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to
   * inspect each edge's {@link Edge#flow} and {@link Edge#capacity}. This is
   * useful if you want to figure out which edges were used during the max flow.
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

  // Performs the Ford-Fulkerson method applying a depth first search as
  // a means of finding an augmenting path.
  public void solve() {
    if (solved) return;

    visited = new int[n];
    minCut = new boolean[n];

    // Make sure delta is a power of two.
    delta = Integer.highestOneBit(delta);

    for (long flow; delta > 0; delta /= 2) {
      do {
        // Try to find an augmenting path from source to sink 
        // with capacity greater than or equal to delta.
        flow = dfs(source, Long.MAX_VALUE);
        maxFlow += flow;
        visitedToken++;
      } while(flow != 0);
    }

    // Find min cut.
    for(int i = 0; i < n; i++)
      if (visited[i] == visitedToken-1)
        minCut[i] = true;

    solved = true;
  }

  private long dfs(int node, long flow) {
    // At sink node, return augmented path flow.
    if (node == sink) return flow;

    List<Edge> edges = graph.get(node);
    visited[node] = visitedToken;

    for (Edge edge : edges) {
      final long cap = edge.capacity - edge.flow; // Remaining capacity
      if (cap >= delta && visited[edge.to] != visitedToken) {

        long bottleNeck = dfs(edge.to, min(flow, cap));

        // Augment flow with bottle neck value
        if (bottleNeck > 0) {
          Edge res = edge.residual;
          edge.flow += bottleNeck;
          res.flow -= bottleNeck;
          return bottleNeck;
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

    /* Example */

  public static void main(String[] args) {
    testSmallFlowGraph();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph() {
    int n = 4;
    int s = n;
    int t = n+1;

    CapacityScalingSolverAdjacencyList solver;
    solver = new CapacityScalingSolverAdjacencyList(n+2, s, t);

    // Source edges
    solver.addEdge(s, 0, 10);
    solver.addEdge(s, 1, 10);

    // Sink edges
    solver.addEdge(2, t, 10);
    solver.addEdge(3, t, 10);

    // Middle edges
    solver.addEdge(0, 1, 2);
    solver.addEdge(0, 2, 4);
    solver.addEdge(0, 3, 8);
    solver.addEdge(1, 3, 9);
    solver.addEdge(3, 2, 6);

    System.out.println(solver.getMaxFlow()); // 19
  }

}
















