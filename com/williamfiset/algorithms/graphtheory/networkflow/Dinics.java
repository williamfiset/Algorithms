/**
 * Implementation of Dinic's network flow algorithm. The algorithm works 
 * by first constructing a level graph using a BFS and then finding 
 * augmenting paths on the level graph using multiple DFS's.
 *
 * Time Complexity: O(EV²)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.*;
import java.util.*;

public class Dinics {

  static final long INF = 987654321L;

  // Inputs: n = number of nodes, s = source, t = sink
  private final int n, s, t;
  
  // Internal
  private boolean solved;
  private int[] level;

  // Outputs
  private long maxFlow;
  private boolean[] minCut;
  private List<List<Edge>> graph;

  static class Edge {
    int to;
    Edge residual;
    long flow, capacity;
    public Edge(int to, long capacity) {
      this.to = to; 
      this.capacity = capacity;
    }
  }

  /**
   * Creates an instance of a flow network solver. Use the {@link #addEdge(int, int, int)}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= source < n
   * @param t - The index of the sink node, 0 <= sink < n
   */
  public Dinics(int n, int s, int t) {
    this.n = n; this.s = s; this.t = t;
    level = new int[n];
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

  // Do BFS from source to sink and compute the depth/level of each node
  // which is the minimum number of edges from that node to the source.
  private boolean bfs() {
    ArrayDeque<Integer> q = new ArrayDeque<>(n);
    Arrays.fill(level, -1);
    level[s] = 0;
    q.offer(s);
    while(!q.isEmpty()) {
      int node = q.poll();
      for (Edge edge : graph.get(node)) {
        if (edge.flow < edge.capacity && level[edge.to] == -1) {
          level[edge.to] = level[node] + 1;
          q.offer(edge.to);
        }
      }
    }
    return level[t] != -1;
  }

  private long dfs(int at, long flow) {
    // At sink node, return augmented path flow.
    if (at == t) return flow;

    for (Edge edge : graph.get(at)) {
      if (edge.flow < edge.capacity && level[edge.to] == level[at] + 1) {

        long bottleNeck = dfs(edge.to, min(flow, edge.capacity - edge.flow));

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

  public void solve() {
    if (solved) return;
    
    while(bfs())

      for (long flow = dfs(s, INF); flow != 0; flow = dfs(s, INF))
        maxFlow += flow;

    minCut = new boolean[n];
    for (int i = 0; i < n; i++)
      if (level[i] != -1)
        minCut[i] = true;

    solved = true;
  }
}
