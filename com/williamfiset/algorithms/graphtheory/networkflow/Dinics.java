/**
 * This file is a WIP. 
 *
 * Time Complexity:
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.*;
import java.util.*;

public class Dinics {

  static final long INF = 987654321L;

  // Inputs: n = num nodes, s = source, t = sink
  int n, s, t;
  long maxFlow;
  int[] level;
  List<List<Edge>> graph;

  static class Edge {
    public Edge residual;
    int to;
    long flow, capacity;
    public Edge(int to, long capacity) {
      this.to = to; 
      this.capacity = capacity;
    }
  }

  public Dinics(int n, int s, int t) {
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

  private long dfs(int at, long f) {
    // At sink node, return augmented path flow.
    if (at == t) return f;

    for (Edge edge : graph.get(at)) {
      if (edge.flow < edge.capacity && level[edge.to] == level[at] + 1) {

        // final flow (ff)
        long ff = dfs(edge.to, min(f, edge.capacity - edge.flow));

        if (ff > 0) {
          Edge res = edge.residual;
          edge.flow += ff;
          res.flow -= ff;
          return ff;
        }

      }
    }
    return 0;
  }

  public void solve() {
    level = new int[n];
    while(bfs()) {
      for (long flow = dfs(s, INF); flow != 0; flow = dfs(s, INF)) {
        maxFlow += flow;
      }
    }
  }
}