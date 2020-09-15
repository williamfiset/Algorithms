/**
 * Implementation of finding an Eulerian Path on a graph. This implementation verifies that the
 * input graph is fully connected and supports self loops and repeated edges between nodes.
 *
 * <p>Test against: https://open.kattis.com/problems/eulerianpath
 * http://codeforces.com/contest/508/problem/D
 *
 * <p>Run: ./gradlew run -Palgorithm=graphtheory.EulerianPathDirectedEdgesAdjacencyList
 *
 * <p>Time Complexity: O(E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EulerianPathDirectedEdgesAdjacencyList {

  private final int n;
  private int edgeCount;
  private int[] in, out;
  private LinkedList<Integer> path;
  private List<List<Integer>> graph;

  public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    n = graph.size();
    this.graph = graph;
    path = new LinkedList<>();
  }

  // Returns a list of edgeCount + 1 node ids that give the Eulerian path or
  // null if no path exists or the graph is disconnected.
  public int[] getEulerianPath() {
    setUp();

    if (!graphHasEulerianPath()) return null;
    dfs(findStartNode());

    // Make sure all edges of the graph were traversed. It could be the
    // case that the graph is disconnected in which case return null.
    if (path.size() != edgeCount + 1) return null;

    // Instead of returning the 'path' as a linked list return
    // the solution as a primitive array for convenience.
    int[] soln = new int[edgeCount + 1];
    for (int i = 0; !path.isEmpty(); i++) soln[i] = path.removeFirst();

    return soln;
  }

  private void setUp() {
    // Arrays that track the in degree and out degree of each node.
    in = new int[n];
    out = new int[n];

    edgeCount = 0;

    // Compute in and out node degrees.
    for (int from = 0; from < n; from++) {
      for (int to : graph.get(from)) {
        in[to]++;
        out[from]++;
        edgeCount++;
      }
    }
  }

  private boolean graphHasEulerianPath() {
    if (edgeCount == 0) return false;
    int startNodes = 0, endNodes = 0;
    for (int i = 0; i < n; i++) {
      if (out[i] - in[i] > 1 || in[i] - out[i] > 1) return false;
      else if (out[i] - in[i] == 1) startNodes++;
      else if (in[i] - out[i] == 1) endNodes++;
    }
    return (endNodes == 0 && startNodes == 0) || (endNodes == 1 && startNodes == 1);
  }

  private int findStartNode() {
    int start = 0;
    for (int i = 0; i < n; i++) {
      // Unique starting node.
      if (out[i] - in[i] == 1) return i;
      // Start at a node with an outgoing edge.
      if (out[i] > 0) start = i;
    }
    return start;
  }

  // Perform DFS to find Eulerian path.
  private void dfs(int at) {
    while (out[at] != 0) {
      int next = graph.get(at).get(--out[at]);
      dfs(next);
    }
    path.addFirst(at);
  }

  /* Graph creation helper methods */

  public static List<List<Integer>> initializeEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addDirectedEdge(List<List<Integer>> g, int from, int to) {
    g.get(from).add(to);
  }

  /* Examples */

  public static void main(String[] args) {
    exampleFromSlides();
    smallExample();
  }

  private static void exampleFromSlides() {
    int n = 7;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 2, 2);
    addDirectedEdge(graph, 2, 4);
    addDirectedEdge(graph, 2, 4);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 3, 2);
    addDirectedEdge(graph, 3, 5);
    addDirectedEdge(graph, 4, 3);
    addDirectedEdge(graph, 4, 6);
    addDirectedEdge(graph, 5, 6);
    addDirectedEdge(graph, 6, 3);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // Outputs path: [1, 3, 5, 6, 3, 2, 4, 3, 1, 2, 2, 4, 6]
    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  private static void smallExample() {
    int n = 5;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    addDirectedEdge(graph, 0, 1);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 1, 4);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 2, 1);
    addDirectedEdge(graph, 4, 1);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // Outputs path: [0, 1, 4, 1, 2, 1, 3]
    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }
}
