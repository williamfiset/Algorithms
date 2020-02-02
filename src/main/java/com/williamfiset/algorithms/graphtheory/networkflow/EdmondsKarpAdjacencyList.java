/**
 * An implementation of the Edmonds-Karp algorithm which is essentially Ford-Fulkerson with a BFS as
 * a method of finding augmenting paths. This Edmonds-Karp algorithm will allow you to find the max
 * flow through a directed graph and the min cut as a byproduct.
 *
 * <p>Time Complexity: O(VE^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.*;

public class EdmondsKarpAdjacencyList extends NetworkFlowSolverBase {

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)} method to
   * add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public EdmondsKarpAdjacencyList(int n, int s, int t) {
    super(n, s, t);
  }

  // Run Edmonds-Karp and compute the max flow from the source to the sink node.
  @Override
  public void solve() {
    long flow;
    do {
      markAllNodesAsUnvisited();
      flow = bfs();
      maxFlow += flow;
    } while (flow != 0);

    for (int i = 0; i < n; i++) if (visited(i)) minCut[i] = true;
  }

  private long bfs() {
    Edge[] prev = new Edge[n];

    // The queue can be optimized to use a faster queue
    Queue<Integer> q = new ArrayDeque<>(n);
    visit(s);
    q.offer(s);

    // Perform BFS from source to sink
    while (!q.isEmpty()) {
      int node = q.poll();
      if (node == t) break;

      for (Edge edge : graph[node]) {
        long cap = edge.remainingCapacity();
        if (cap > 0 && !visited(edge.to)) {
          visit(edge.to);
          prev[edge.to] = edge;
          q.offer(edge.to);
        }
      }
    }

    // Sink not reachable!
    if (prev[t] == null) return 0;

    long bottleNeck = Long.MAX_VALUE;

    // Find augmented path and bottle neck
    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from])
      bottleNeck = min(bottleNeck, edge.remainingCapacity());

    // Retrace augmented path and update flow values.
    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) edge.augment(bottleNeck);

    // Return bottleneck flow
    return bottleNeck;
  }

  /* Example */

  public static void main(String[] args) {
    testSmallFlowGraph();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n - 1;
    int t = n - 2;

    EdmondsKarpAdjacencyList solver;
    solver = new EdmondsKarpAdjacencyList(n, s, t);

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
