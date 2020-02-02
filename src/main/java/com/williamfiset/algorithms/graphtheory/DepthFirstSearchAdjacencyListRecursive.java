/**
 * An implementation of a recursive approach to DFS Time Complexity: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class DepthFirstSearchAdjacencyListRecursive {

  static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  // Perform a depth first search on the graph counting
  // the number of nodes traversed starting at some position
  static long dfs(int at, boolean[] visited, Map<Integer, List<Edge>> graph) {

    // We have already visited this node
    if (visited[at]) return 0L;

    // Visit this node
    visited[at] = true;
    long count = 1;

    // Visit all edges adjacent to where we're at
    List<Edge> edges = graph.get(at);
    if (edges != null) {
      for (Edge edge : edges) {
        count += dfs(edge.to, visited, graph);
      }
    }

    return count;
  }

  // Example usage of DFS
  public static void main(String[] args) {

    // Create a fully connected graph
    //           (0)
    //           / \
    //        5 /   \ 4
    //         /     \
    // 10     <   -2  >
    //   +->(2)<------(1)      (4)
    //   +--- \       /
    //         \     /
    //        1 \   / 6
    //           > <
    //           (3)
    int numNodes = 5;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    addDirectedEdge(graph, 0, 1, 4);
    addDirectedEdge(graph, 0, 2, 5);
    addDirectedEdge(graph, 1, 2, -2);
    addDirectedEdge(graph, 1, 3, 6);
    addDirectedEdge(graph, 2, 3, 1);
    addDirectedEdge(graph, 2, 2, 10); // Self loop

    long nodeCount = dfs(0, new boolean[numNodes], graph);
    System.out.println("DFS node count starting at node 0: " + nodeCount);
    if (nodeCount != 4) System.err.println("Error with DFS");

    nodeCount = dfs(4, new boolean[numNodes], graph);
    System.out.println("DFS node count starting at node 4: " + nodeCount);
    if (nodeCount != 1) System.err.println("Error with DFS");
  }

  // Helper method to setup graph
  private static void addDirectedEdge(Map<Integer, List<Edge>> graph, int from, int to, int cost) {
    List<Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
  }
}
