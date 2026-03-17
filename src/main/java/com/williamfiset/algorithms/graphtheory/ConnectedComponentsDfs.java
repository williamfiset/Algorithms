/**
 * Connected Components — Adjacency List (DFS)
 *
 * <p>Finds all connected components of an undirected graph using depth-first
 * search. Each unvisited node starts a new DFS that labels every reachable node
 * with the same component id.
 *
 * <p>For directed graphs, use Tarjan's or Kosaraju's algorithm to find
 * <em>strongly</em> connected components instead.
 *
 * <p>Time:  O(V + E)
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConnectedComponentsDfs {

  private final int n;
  private final List<List<Integer>> graph;
  private boolean solved;
  private int componentCount;
  private int[] componentIds;

  public ConnectedComponentsDfs(List<List<Integer>> graph) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }
    this.n = graph.size();
    this.graph = graph;
  }

  /**
   * Returns the number of connected components.
   */
  public int countComponents() {
    solve();
    return componentCount;
  }

  /**
   * Returns the component id of the given node. Component ids are 0-indexed.
   */
  public int componentId(int node) {
    solve();
    return componentIds[node];
  }

  /**
   * Returns the component id array where {@code componentIds[i]} is the
   * component id of node i. Component ids are 0-indexed.
   */
  public int[] getComponentIds() {
    solve();
    return componentIds;
  }

  private void solve() {
    if (solved) {
      return;
    }

    componentIds = new int[n];
    Arrays.fill(componentIds, -1);

    for (int i = 0; i < n; i++) {
      if (componentIds[i] == -1) {
        dfs(i, componentCount++);
      }
    }

    solved = true;
  }

  private void dfs(int at, int id) {
    componentIds[at] = id;
    for (int to : graph.get(at)) {
      if (componentIds[to] == -1) {
        dfs(to, id);
      }
    }
  }

  // ==================== Main ====================

  //
  //    0 --- 1       3 --- 6
  //    |   / |       |     |
  //    |  /  |       |     |
  //    7     2 - 5   4     9       10
  //
  //          8
  //
  //  Components: {0,1,2,5,7}, {3,4,6,9}, {8}, {10}
  //  Count: 4
  //
  public static void main(String[] args) {
    int n = 11;
    List<List<Integer>> graph = createGraph(n);

    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 7);
    addUndirectedEdge(graph, 7, 0);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 3, 6);
    addUndirectedEdge(graph, 6, 9);

    ConnectedComponentsDfs solver = new ConnectedComponentsDfs(graph);

    System.out.printf("Number of components: %d%n", solver.countComponents());

    for (int i = 0; i < n; i++) {
      System.out.printf("Node %d -> component %d%n", i, solver.componentId(i));
    }
  }

  private static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  private static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
}
