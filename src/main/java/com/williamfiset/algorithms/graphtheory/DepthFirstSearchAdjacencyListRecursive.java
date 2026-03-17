/**
 * Depth-First Search — Adjacency List (Recursive)
 *
 * <p>Performs a recursive DFS traversal on a directed graph represented as an
 * adjacency list, counting the number of reachable nodes from a given source.
 *
 * <p>Time:  O(V + E)
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

public class DepthFirstSearchAdjacencyListRecursive {

  /**
   * Returns the number of nodes reachable from {@code start} (including itself).
   */
  static int dfs(int start, List<List<Integer>> graph) {
    return dfs(start, new boolean[graph.size()], graph);
  }

  private static int dfs(int at, boolean[] visited, List<List<Integer>> graph) {
    if (visited[at]) {
      return 0;
    }
    visited[at] = true;
    int count = 1;
    for (int to : graph.get(at)) {
      count += dfs(to, visited, graph);
    }
    return count;
  }

  // ==================== Main ====================

  //
  //    0 ---> 1 ---> 3
  //    |      |
  //    v      v
  //    2      4 ---> 5       6
  //
  //  DFS from 0: 6 nodes
  //  DFS from 6: 1 node
  //
  public static void main(String[] args) {
    int n = 7;
    List<List<Integer>> graph = createGraph(n);

    addDirectedEdge(graph, 0, 1);
    addDirectedEdge(graph, 0, 2);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 1, 4);
    addDirectedEdge(graph, 4, 5);

    System.out.println("DFS from 0: " + dfs(0, graph) + " nodes");
    System.out.println("DFS from 6: " + dfs(6, graph) + " nodes");
  }

  private static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  private static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }
}
