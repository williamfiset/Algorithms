/**
 * Implementation of Dinic's network flow algorithm. The algorithm works 
 * by first constructing a level graph using a BFS and then finding 
 * augmenting paths on the level graph using multiple DFSs.
 *
 * Time Complexity: O(EV²)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;
import java.util.*;

public class Dinics extends NetworkFlowSolverBase {

  private int[] level;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public Dinics(int n, int s, int t) {
    super(n, s, t);
    level = new int[n];
  }

  @Override
  public void solve() {
    // next[i] indicates the next unused edge index in the adjacency list for node i. This is part 
    // of the Shimon Even and Alon Itai optimization of pruning deads ends as part of the DFS phase.
    int[] next = new int[n];

    while (bfs()) {
      Arrays.fill(next, 0);
      // Find max flow by adding all augmenting path flows.
      for (long f = dfs(s, next, INF); f != 0; f = dfs(s, next, INF)) {
        maxFlow += f;
      }
    }

    for (int i = 0; i < n; i++)
      if (level[i] != -1)
        minCut[i] = true;
  }

  // Do a BFS from source to sink and compute the depth/level of each node
  // which is the minimum number of edges from that node to the source.
  private boolean bfs() {
    Arrays.fill(level, -1);
    level[s] = 0;
    Deque<Integer> q = new ArrayDeque<>(n);
    q.offer(s);
    while(!q.isEmpty()) {
      int node = q.poll();
      for (Edge edge : graph[node]) {
        long cap = edge.remainingCapacity();
        if (cap > 0 && level[edge.to] == -1) {
          level[edge.to] = level[node] + 1;
          q.offer(edge.to);
        }
      }
    }
    return level[t] != -1;
  }

  private long dfs(int at, int[] next, long flow) {
    if (at == t) return flow;
    final int sz = graph[at].size();
    
    for (;next[at] < sz; next[at]++) {
      Edge edge = graph[at].get(next[at]);
      long cap = edge.remainingCapacity();
      if (cap > 0 && level[edge.to] == level[at] + 1) {

        long bottleNeck = dfs(edge.to, next, min(flow, cap));
        if (bottleNeck > 0) {
          edge.augment(bottleNeck);
          return bottleNeck;
        }

      }
    }
    return 0;
  }

    /* Examples */

  public static void main(String[] args) {
    testSmallFlowGraph();
    // testGraphFromSlides();
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  private static void testSmallFlowGraph() {
    int n = 6;
    int s = n-1;
    int t = n-2;

    Dinics solver;
    solver = new Dinics(n, s, t);

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

  private static void testGraphFromSlides() {
    int n = 12;
    int s = n-1;
    int t = n-2;

    Dinics solver;
    solver = new Dinics(n, s, t);

    // Source edges
    solver.addEdge(s, 0, 5);
    solver.addEdge(s, 1, 20);
    solver.addEdge(s, 2, 10);

    // Middle edges
    solver.addEdge(0, 1, 3);
    solver.addEdge(0, 5, 4);

    solver.addEdge(1, 4, 14);
    solver.addEdge(1, 5, 14);

    solver.addEdge(2, 1, 5);
    solver.addEdge(2, 3, 4);

    solver.addEdge(3, 4, 3);
    solver.addEdge(3, 9, 11);

    solver.addEdge(4, 6, 4);
    solver.addEdge(4, 8, 22);

    solver.addEdge(5, 6, 8);
    solver.addEdge(5, 7, 3);

    solver.addEdge(6, 7, 12);

    solver.addEdge(7, 8, 9);
    solver.addEdge(7, t, 7);

    solver.addEdge(8, 9, 11);
    solver.addEdge(8, t, 15);

    solver.addEdge(9, t, 60);

    System.out.println(solver.getMaxFlow()); // 29
  }  

}
