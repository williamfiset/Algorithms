/**
 * An implementation of a iterative DFS with an adjacency list Time Complexity: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class DepthFirstSearchAdjacencyListIterative {

  // Perform a depth first search on a graph with n nodes
  // from a starting point to count the number of nodes
  // in a given component.
  static int dfs(Map<Integer, List<WeightedEdge<Integer>>> graph, int start, int n) {

    int count = 0;
    boolean[] visited = new boolean[n];
    Stack<Integer> stack = new Stack<>();

    // Start by visiting the starting node
    stack.push(start);
    visited[start] = true;

    while (!stack.isEmpty()) {
      int node = stack.pop();
      count++;
      List<WeightedEdge<Integer>> edges = graph.get(node);

      if (edges != null) {
        for (WeightedEdge<Integer> edge : edges) {
          if (!visited[edge.getTo()]) {
            stack.push(edge.getTo());
            visited[edge.getTo()] = true;
          }
        }
      }
    }

    return count;
  }

  // Example usage of DFS
  public static void main(String[] args) {

    // Create a fully connected graph
    // (0)
    // / \
    // 5 / \ 4
    // / \
    // 10 < -2 >
    // +->(2)<------(1) (4)
    // +--- \ /
    // \ /
    // 1 \ / 6
    // > <
    // (3)
    int numNodes = 5;
    Map<Integer, List<WeightedEdge<Integer>>> graph = new HashMap<>();
    addDirectedEdge(graph, 0, 1, 4);
    addDirectedEdge(graph, 0, 2, 5);
    addDirectedEdge(graph, 1, 2, -2);
    addDirectedEdge(graph, 1, 3, 6);
    addDirectedEdge(graph, 2, 3, 1);
    addDirectedEdge(graph, 2, 2, 10); // Self loop

    long nodeCount = dfs(graph, 0, numNodes);
    System.out.println("DFS node count starting at node 0: " + nodeCount);
    if (nodeCount != 4) System.err.println("Error with DFS");

    nodeCount = dfs(graph, 4, numNodes);
    System.out.println("DFS node count starting at node 4: " + nodeCount);
    if (nodeCount != 1) System.err.println("Error with DFS");
  }

  // Helper method to setup graph
  private static void addDirectedEdge(
      Map<Integer, List<WeightedEdge<Integer>>> graph, int from, int to, int cost) {
    List<WeightedEdge<Integer>> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<WeightedEdge<Integer>>();
      graph.put(from, list);
    }
    list.add(new WeightedEdge<>(from, to, cost));
  }
}
