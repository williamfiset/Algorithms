/**
 * An implementation of the Edmonds-Karp algorithm which is essentially
 * Ford-Fulkerson with a BFS as a method of finding augmenting paths. 
 * This Edmonds-Karp algorithm will allow you to find the max flow through
 * a directed graph and the min cut as a byproduct. 
 *
 * Time Complexity: O(VE^2)
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class EdmondsKarpAdjacencyList {

  private static class Edge {
    Edge residual;
    int to, from, capacity;
    public Edge(int to, int from, int capacity) {
      this.to = to; 
      this.from = from;
      this.capacity = capacity;
    }
  }

  // Construct an empty graph with n nodes (this 
  // should include the source and sink nodes)
  public static List<Edge>[] createGraph(int n) {
    List<Edge>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    return graph;
  }

  // Adds a directed edge to the flow graph. This method also
  // adds the residual edge for when Ford-Fulkerson is run.
  static void addEdge(List<Edge>[] graph, int from, int to, int capacity) {
    Edge e1 = new Edge(to, from, capacity);
    Edge e2 = new Edge(from, to, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  // Run Edmonds-Karp and return the max flow from the source to the 
  // sink node. You can modify this algorithm to return the min cut.
  public static int edmondsKarp(List<Edge>[] graph, int source, int sink) {

    int n = graph.length;

    boolean [] minCut = new boolean[n];
    boolean [] visited = new boolean[n];

    for (int maxFlow = 0;;) {

      // Try to find an augmenting path from source to sink
      Arrays.fill(visited, false);
      int flow = bfs(graph, visited, source, sink);

      maxFlow += flow;
      if (flow == 0) {

        return maxFlow;

        // Uncomment to return the min-cut in which the nodes on the "LEFT SIDE"
        // of the cut are marked as true and those on the "RIGHT SIDE" of the cut
        // are marked as false. This finds all the nodes which participated in the
        // last (failed) attempt to find an augmenting path from the source to 
        // the sink in the BFS phase.
        // for(int i = 0; i < n; i++)
        //   if (visited[i])
        //     minCut[i] = true;
        // return minCut;

      }

    }
  }

  private static int bfs(List<Edge>[] graph, boolean[] visited, int source, int sink) {

    int n = graph.length;
    Edge [] prev = new Edge[n];

    // The queue can be optimized to use a faster queue
    Queue <Integer> q = new ArrayDeque<>(n);
    visited[source] = true;
    q.offer(source);

    // Perform BFS from source to sink
    while(!q.isEmpty()) {

      int node = q.poll();
      if (node == sink) break;

      for (Edge edge : graph[node]) {
        if (!visited[edge.to] && edge.capacity > 0) {
          visited[edge.to] = true;
          prev[edge.to] = edge;
          q.offer(edge.to);
        }
      }

    }

    // Sink not reachable!
    if (prev[sink] == null) return 0;

    int bottleNeck = Integer.MAX_VALUE;

    // Find augmented path and bottle neck
    for(Edge edge = prev[sink]; edge != null; edge = prev[edge.from])
      bottleNeck = Math.min(bottleNeck, edge.capacity);

    // Retrace augmented path and update edges
    for(Edge edge = prev[sink]; edge != null; edge = prev[edge.from]) {
      Edge res = edge.residual;
      edge.capacity -= bottleNeck;
      res.capacity  += bottleNeck;
    }

    // Return bottleneck flow
    return bottleNeck;

  }

}















