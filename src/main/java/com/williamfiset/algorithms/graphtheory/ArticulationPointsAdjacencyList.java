/**
 * Articulation Points (Cut Vertices) — Adjacency List
 *
 * An articulation point is a vertex whose removal disconnects the graph
 * (or increases the number of connected components). This implementation
 * uses Tarjan's DFS-based algorithm with low-link values.
 *
 * For each DFS tree rooted at node r, a non-root node u is an articulation
 * point if it has a child v such that no vertex in the subtree rooted at v
 * has a back edge to an ancestor of u:
 *
 *   ids[u] <= low[v]
 *
 * The root node r is an articulation point if it has more than one child
 * in the DFS tree.
 *
 * Works on disconnected graphs by running DFS from every unvisited node.
 *
 * See also: {@link BridgesAdjacencyList} for finding bridge edges.
 *
 * Tested against HackerEarth online judge at:
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial
 *
 * Time:  O(V + E)
 * Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class ArticulationPointsAdjacencyList {

  private final int n;
  private final List<List<Integer>> graph;
  private boolean solved;
  private int id, rootNodeOutgoingEdgeCount;
  private int[] low, ids;
  private boolean[] visited, isArticulationPoint;

  public ArticulationPointsAdjacencyList(List<List<Integer>> graph, int n) {
    if (graph == null || n <= 0 || graph.size() != n) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
  }

  /**
   * Returns a boolean array where index i is true if node i is an articulation point.
   * Works even if the graph is not fully connected.
   */
  public boolean[] findArticulationPoints() {
    if (solved) return isArticulationPoint;

    id = 0;
    low = new int[n];
    ids = new int[n];
    visited = new boolean[n];
    isArticulationPoint = new boolean[n];

    // Run DFS from each unvisited node to handle disconnected components.
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        rootNodeOutgoingEdgeCount = 0;
        dfs(i, i, -1);
        // Root is an articulation point only if it has 2+ children in the DFS tree.
        isArticulationPoint[i] = (rootNodeOutgoingEdgeCount > 1);
      }
    }

    solved = true;
    return isArticulationPoint;
  }

  private void dfs(int root, int at, int parent) {
    if (parent == root) rootNodeOutgoingEdgeCount++;

    visited[at] = true;
    low[at] = ids[at] = id++;

    for (int to : graph.get(at)) {
      if (to == parent) continue;
      if (!visited[to]) {
        dfs(root, to, at);
        low[at] = min(low[at], low[to]);
        // If no vertex in the subtree rooted at 'to' can reach above 'at',
        // then removing 'at' would disconnect 'to's subtree.
        if (ids[at] <= low[to]) {
          isArticulationPoint[at] = true;
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
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    testExample1();
    testExample2();
  }

  //
  //        0 --- 1
  //        |   /
  //        2 -------- 3 --- 4
  //        |
  //        5 --- 6
  //        |     |
  //        8 --- 7
  //
  // Articulation points: 2, 3, 5
  //
  private static void testExample1() {
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

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    // Node 2 is an articulation
    // Node 3 is an articulation
    // Node 5 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }

  //
  //  0 --- 1 --- 2
  //
  // Articulation point: 1
  //
  private static void testExample2() {
    int n = 3;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    // Node 1 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }
}
