/**
 * Ford-Fulkerson Max Flow — DFS Augmenting Paths (Adjacency List)
 *
 * <p>The Ford-Fulkerson method finds the maximum flow in a flow network by
 * repeatedly finding augmenting paths from source to sink and pushing flow
 * along them. This implementation uses DFS to find each augmenting path.
 *
 * <p>Algorithm:
 * <ol>
 *   <li>Run DFS from source to find a path to the sink with available capacity.</li>
 *   <li>Compute the bottleneck (minimum residual capacity along the path).</li>
 *   <li>Augment flow along the path by the bottleneck value.</li>
 *   <li>Repeat until no augmenting path exists.</li>
 * </ol>
 *
 * <p>The min-cut is obtained as a byproduct: after the algorithm terminates,
 * all nodes still reachable from the source in the residual graph belong to
 * the source side of the minimum cut.
 *
 * <p>Time Complexity: O(fE), where f is the max flow and E is the number of edges.
 * The DFS-based approach can be slow on graphs with large integer capacities
 * because each augmenting path may only push one unit of flow.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.List;

public class FordFulkersonDfsSolverAdjacencyList extends NetworkFlowSolverBase {

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge} method to add edges to
   * the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public FordFulkersonDfsSolverAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }

  @Override
  public void solve() {
    // Repeatedly find augmenting paths via DFS and accumulate flow.
    for (long f = dfs(s, INF); f != 0; f = dfs(s, INF)) {
      markAllNodesAsUnvisited();
      maxFlow += f;
    }

    // Nodes still reachable from source in the residual graph form the min-cut.
    for (int i = 0; i < n; i++) {
      if (visited(i)) {
        minCut[i] = true;
      }
    }
  }

  private long dfs(int node, long flow) {
    if (node == t) {
      return flow;
    }

    visit(node);

    for (Edge edge : graph[node]) {
      long rcap = edge.remainingCapacity();
      if (rcap > 0 && !visited(edge.to)) {
        long bottleNeck = dfs(edge.to, min(flow, rcap));
        if (bottleNeck > 0) {
          edge.augment(bottleNeck);
          return bottleNeck;
        }
      }
    }
    return 0;
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    testSmallFlowGraph();
    testExampleFromSlides();
    testLargerFlowGraph();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    FordFulkersonDfsSolverAdjacencyList solver =
        new FordFulkersonDfsSolverAdjacencyList(n, s, t);

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

  private static void testExampleFromSlides() {
    int n = 6;
    int s = n - 2;
    int t = n - 1;

    FordFulkersonDfsSolverAdjacencyList solver =
        new FordFulkersonDfsSolverAdjacencyList(n, s, t);

    solver.addEdge(s, 1, 10);
    solver.addEdge(1, 3, 15);
    solver.addEdge(3, 0, 6);
    solver.addEdge(0, 2, 25);
    solver.addEdge(2, t, 10);

    solver.addEdge(s, 0, 10);
    solver.addEdge(3, t, 10);

    System.out.println(solver.getMaxFlow()); // 20
  }

  private static void testLargerFlowGraph() {
    int n = 12;
    int s = n - 2;
    int t = n - 1;

    FordFulkersonDfsSolverAdjacencyList solver =
        new FordFulkersonDfsSolverAdjacencyList(n, s, t);

    solver.addEdge(s, 1, 2);
    solver.addEdge(s, 2, 1);
    solver.addEdge(s, 0, 7);

    solver.addEdge(0, 3, 2);
    solver.addEdge(0, 4, 4);

    solver.addEdge(1, 4, 5);
    solver.addEdge(1, 5, 6);

    solver.addEdge(2, 3, 4);
    solver.addEdge(2, 7, 8);

    solver.addEdge(3, 6, 7);
    solver.addEdge(3, 7, 1);

    solver.addEdge(4, 5, 8);
    solver.addEdge(4, 8, 3);

    solver.addEdge(5, 8, 3);

    solver.addEdge(6, t, 1);
    solver.addEdge(7, t, 3);
    solver.addEdge(8, t, 4);

    System.out.println(solver.getMaxFlow());
  }
}
