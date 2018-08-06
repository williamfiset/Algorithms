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

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class FordFulkersonDfsSolverAdjacencyList extends NetworkFlowBase {

  private boolean solved;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n
   */
  public FordFulkersonDfsSolverAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }

  // Performs the Ford-Fulkerson method applying a depth first search as
  // a means of finding an augmenting path.
  @Override
  public void solve() {
    if (solved) return;

    visited = new int[n];
    minCut = new boolean[n];

    // Find max flow by adding all augmenting path flows.
    for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
      visitedToken++;
      maxFlow += f;
    }

    // Find min cut.
    for(int i = 0; i < n; i++)
      if (visited[i] == visitedToken)
        minCut[i] = true;

    solved = true;
  }

  private long dfs(int node, long flow) {
    // At sink node, return augmented path flow.
    if (node == t) return flow;

    List<Edge> edges = graph.get(node);
    visited[node] = visitedToken;

    for (Edge edge : edges) {
      final long cap = edge.capacity - edge.flow; // Remaining capacity
      if (cap > 0 && visited[edge.to] != visitedToken) {

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

