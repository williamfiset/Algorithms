/**
 * Finds all articulation points on an undirected graph.
 *
 * <p>Tested against HackerEarth online judge at:
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class ArticulationPointsAdjacencyList {

  private int n, id, rootNodeOutcomingEdgeCount;
  private boolean solved;
  private int[] low, ids;
  private boolean[] visited, isArticulationPoint;
  private List<List<Integer>> graph;

  public ArticulationPointsAdjacencyList(List<List<Integer>> graph, int n) {
    if (graph == null || n <= 0 || graph.size() != n) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
  }

  // Returns the indexes for all articulation points in the graph even if the
  // graph is not fully connected.
  public boolean[] findArticulationPoints() {
    if (solved) return isArticulationPoint;

    id = 0;
    low = new int[n]; // Low link values
    ids = new int[n]; // Nodes ids
    visited = new boolean[n];
    isArticulationPoint = new boolean[n];

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        rootNodeOutcomingEdgeCount = 0;
        dfs(i, i, -1);
        isArticulationPoint[i] = (rootNodeOutcomingEdgeCount > 1);
      }
    }

    solved = true;
    return isArticulationPoint;
  }

  private void dfs(int root, int at, int parent) {

    if (parent == root) rootNodeOutcomingEdgeCount++;

    visited[at] = true;
    low[at] = ids[at] = id++;

    List<Integer> edges = graph.get(at);
    for (Integer to : edges) {
      if (to == parent) continue;
      if (!visited[to]) {
        dfs(root, to, at);
        low[at] = min(low[at], low[to]);
        if (ids[at] <= low[to]) {
          isArticulationPoint[at] = true;
        }
      } else {
        low[at] = min(low[at], ids[to]);
      }
    }
  }

  /* Graph helpers */

  // Initialize a graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add an undirected edge to a graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  /* Example usage: */

  public static void main(String[] args) {
    testExample2();
  }

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

    // Prints:
    // Node 2 is an articulation
    // Node 3 is an articulation
    // Node 5 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }

  // Tests a graph with 3 nodes in a line: A - B - C
  // Only node 'B' should be an articulation point.
  private static void testExample2() {
    int n = 3;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    // Prints:
    // Node 1 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }
}
