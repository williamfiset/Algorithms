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

import static java.lang.Math.*;
import java.util.*;

public class DinicsCapacityScaling extends NetworkFlowSolverBase {

  private int delta;
  private int[] level;

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= source < n
   * @param t - The index of the sink node, 0 <= sink < n
   */
  public DinicsCapacityScaling(int n, int s, int t) {
    super(n, s, t);
    level = new int[n];
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The capacity of the edge.
   */
  @Override
  public void addEdge(int from, int to, int capacity) {
    super.addEdge(from, to, capacity);
    delta = Math.max(delta, capacity);
  }

  @Override
  public void solve() {
    // p[i] indicates the next unused edge index in the adjacency list for node i
    int[] p = new int[n];

    final int C = Integer.highestOneBit(delta);

    while(bfs()) {

      // Start delta at the smallest power of 2 <= the largest capacity.
      delta = C;

      // Repeatedly find an augmenting paths from source to sink using only edges
      // with capacity >= delta. Half delta every time we become unable to find an
      // augmenting path from source to sink until the max flow is found.
      for(long f = 0; delta > 0; delta /= 2) {
        Arrays.fill(p, 0);
        do {
          // visitedToken++;
          f = dfs(s, p, INF);
          maxFlow += f;
        } while (f != 0);
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
        if (edge.flow < edge.capacity && level[edge.to] == -1) {
          level[edge.to] = level[node] + 1;
          q.offer(edge.to);
        }
      }
    }
    return level[t] != -1;
  }

  private long dfs(int at, int[] p, long flow) {
    if (at == t) return flow;
    final int sz = graph[at].size();
    
    for (;p[at] < sz; p[at]++) {
      Edge edge = graph[at].get(p[at]);
      if (edge.flow < edge.capacity && level[edge.to] == level[at] + 1) {

        long bottleNeck = dfs(edge.to, p, min(flow, edge.capacity - edge.flow));
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

    Dinics solver;
    solver = new Dinics(n+2, s, t);

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
