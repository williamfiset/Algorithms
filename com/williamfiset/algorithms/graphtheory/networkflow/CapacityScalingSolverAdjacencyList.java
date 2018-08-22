/**
 * Implementation of the Capacity Scaling algorithm using a DFS
 * as a method of finding augmenting paths.
 *
 * Time Complexity: O(E^2log(U)), where E = num edges, U = max capacity
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class CapacityScalingSolverAdjacencyList extends NetworkFlowSolverBase {

  private long delta;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n
   */
  public CapacityScalingSolverAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The capacity of the edge.
   */
  @Override
  public void addEdge(int from, int to, long capacity) {
    super.addEdge(from, to, capacity);
    delta = Math.max(delta, capacity);
  }

  // Performs the Ford-Fulkerson method applying a depth first search as
  // a means of finding an augmenting path.
  @Override
  public void solve() {

    // Start delta at the smallest power of 2 <= the largest capacity.
    delta = Long.highestOneBit(delta);

    // Repeatedly find an augmenting paths from source to sink using only edges
    // with capacity >= delta. Half delta every time we become unable to find an
    // augmenting path from source to sink until the max flow is found.
    for(long f = 0; delta > 0; delta /= 2) {
      do {
        visitedToken++;
        f = dfs(s, INF);
        maxFlow += f;
      } while (f != 0);
    }

    // Find min cut.
    minCut = new boolean[n];
    for(int i = 0; i < n; i++)
      if (visited[i] == visitedToken)
        minCut[i] = true;
  }

  private long dfs(int node, long flow) {
    // At sink node, return augmented path flow.
    if (node == t) return flow;

    List<Edge> edges = graph[node];
    visited[node] = visitedToken;

    for (Edge edge : edges) {
      if (edge.capacity >= delta && visited[edge.to] != visitedToken) {

        long bottleNeck = dfs(edge.to, min(flow, edge.capacity));

        // Augment flow with bottle neck value
        if (bottleNeck > 0) {
          Edge res = edge.residual;
          edge.flow += bottleNeck;
          edge.capacity -= bottleNeck;
          res.capacity += bottleNeck;
          return bottleNeck;
        }

      }
    }
    return 0;
  }

    /* Example */

  public static void main(String[] args) {
    testSmallFlowGraph();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n-1;
    int t = n-2;

    CapacityScalingSolverAdjacencyList solver;
    solver = new CapacityScalingSolverAdjacencyList(n, s, t);

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
















