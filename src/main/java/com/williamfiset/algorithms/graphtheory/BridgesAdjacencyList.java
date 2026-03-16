/**
 * Bridge Edges (Cut Edges) — Adjacency List
 *
 * <p>A bridge is an edge whose removal disconnects the graph (or increases
 * the number of connected components). This implementation uses Tarjan's
 * DFS-based algorithm with low-link values.
 *
 * <p>An edge (u, v) is a bridge if no vertex in the subtree rooted at v
 * (in the DFS tree) has a back edge to u or any ancestor of u:
 *
 * <pre>   ids[u] &lt; low[v]</pre>
 *
 * <p>Works on disconnected graphs by running DFS from every unvisited node.
 *
 * <p>See also: {@link ArticulationPointsAdjacencyList} for finding cut vertices.
 *
 * <p>Tested against HackerEarth online judge at:
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial
 *
 * <p>Time:  O(V + E)
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class BridgesAdjacencyList {

  private final int n;
  private final List<List<Integer>> graph;
  private boolean solved;
  private int id;
  private int[] low, ids;
  private boolean[] visited;
  private List<Integer> bridges;

  public BridgesAdjacencyList(List<List<Integer>> graph, int n) {
    if (graph == null || n <= 0 || graph.size() != n) {
      throw new IllegalArgumentException();
    }
    this.graph = graph;
    this.n = n;
  }

  /**
   * Returns a flat list of bridge endpoints. Indices (2*i, 2*i+1) form
   * a pair — for example, bridges.get(0) and bridges.get(1) are the two
   * endpoints of the first bridge edge.
   */
  public List<Integer> findBridges() {
    if (solved) {
      return bridges;
    }

    id = 0;
    low = new int[n];
    ids = new int[n];
    visited = new boolean[n];
    bridges = new ArrayList<>();

    // Run DFS from each unvisited node to handle disconnected components.
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfs(i, -1);
      }
    }

    solved = true;
    return bridges;
  }

  private void dfs(int at, int parent) {
    visited[at] = true;
    low[at] = ids[at] = ++id;

    for (int to : graph.get(at)) {
      if (to == parent) {
        continue;
      }
      if (!visited[to]) {
        dfs(to, at);
        low[at] = min(low[at], low[to]);
        // If no vertex in the subtree rooted at 'to' can reach 'at' or above,
        // then removing edge (at, to) would disconnect the graph.
        if (ids[at] < low[to]) {
          bridges.add(at);
          bridges.add(to);
        }
      } else {
        // Back edge: update low-link to the earliest reachable ancestor.
        low[at] = min(low[at], ids[to]);
      }
    }
  }

  /* Graph helpers */

  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // ==================== Main ====================

  //
  //   0 --- 1
  //   |   /
  //   2 -------- 5 --- 6
  //   |          |     |
  //   3 --- 4    8 --- 7
  //
  // Bridges: (2,3), (3,4), (2,5)
  //
  public static void main(String[] args) {
    int n = 9;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 6, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 8, 5);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(graph, n);
    List<Integer> bridges = solver.findBridges();

    for (int i = 0; i < bridges.size(); i += 2) {
      System.out.printf("Bridge between nodes: %d and %d\n", bridges.get(i), bridges.get(i + 1));
    }
  }
}
