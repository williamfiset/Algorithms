/**
 * An implementation of the Ford-Fulkerson (FF) method with a DFS
 * as a method of finding augmenting paths. FF allows you to find
 * the max flow through a directed graph and the min cut as a byproduct.
 *
 * Time Complexity: O(fE), where f is the max flow and E is the number of edges
 * 
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class FordFulkersonDFSAdjacencyList {

  private static int visitedToken = 1;

  private static class Edge {
    Edge residual;
    int to, capacity;
    public Edge(int to, int capacity) {
      this.to = to; 
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
  public static void addEdge(List<Edge>[] graph, int from, int to, int capacity) {
    Edge e1 = new Edge(to, capacity);
    Edge e2 = new Edge(from, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph[from].add(e1);
    graph[to].add(e2);
  }

  // Performs the Ford-Fulkerson method applying a depth first search as
  // a means of finding an augmenting path. The input consists of a directed graph
  // with specified capacities on the edges.
  public static int fordFulkerson(List<Edge>[] graph, int source, int sink) {

    int n = graph.length;
    int [] visited = new int[n];
    boolean [] minCut = new boolean[n];

    for (int maxFlow = 0;;) {

      // Try to find an augmenting path from source to sink
      int flow = dfs(graph, visited, source, sink, Integer.MAX_VALUE);
      visitedToken++;

      maxFlow += flow;
      if (flow == 0) {

        return maxFlow;

        // Uncomment to return the min-cut in which the nodes on the "LEFT SIDE"
        // of the cut are marked as true and those on the "RIGHT SIDE" of the cut
        // are marked as false. This finds all the nodes which participated in the
        // last (failed) attempt to find an augmenting path from the source to 
        // the sink in the DFS phase.
        // for(int i = 0; i < n; i++)
        //   if (visited[i] == visitedToken-1)
        //     minCut[i] = true;
        // return minCut;

      }

    }
  }

  private static int dfs(List<Edge>[] graph, int[] visited, int node, int sink, int flow) {

    // Found sink node, return augmented path flow
    if (node == sink) return flow;

    List <Edge> edges = graph[node];
    visited[node] = visitedToken;

    for (Edge edge : edges) {
      if (visited[edge.to] != visitedToken && edge.capacity > 0) {

        // Update flow to be bottleneck 
        if (edge.capacity < flow) flow = edge.capacity;
        int dfsFlow = dfs(graph, visited, edge.to, sink, flow);

        // Update edge capacities
        if (dfsFlow > 0) {
          Edge res = edge.residual;
          edge.capacity -= dfsFlow;
          res.capacity  += dfsFlow;
          return dfsFlow;
        }

      }
    }

    return 0;

  }

}


