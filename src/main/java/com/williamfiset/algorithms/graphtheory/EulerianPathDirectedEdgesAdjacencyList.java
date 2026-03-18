/**
 * Implementation of finding an Eulerian Path on a directed graph. This implementation verifies that
 * the input graph is fully connected (all edges are reachable) and supports self loops and repeated
 * edges between nodes.
 *
 * <p>An Eulerian Path is a path in a graph that visits every edge exactly once. An Eulerian Circuit
 * is an Eulerian Path which starts and ends on the same vertex.
 *
 * <p>Test against:
 * <ul>
 *   <li>https://open.kattis.com/problems/eulerianpath
 *   <li>http://codeforces.com/contest/508/problem/D
 * </ul>
 *
 * <p>Run with:
 * bazel run //src/main/java/com/williamfiset/algorithms/graphtheory:EulerianPathDirectedEdgesAdjacencyList
 *
 * <p>Time Complexity: O(V + E)
 *
 * @see <a href="https://en.wikipedia.org/wiki/Eulerian_path">Eulerian Path (Wikipedia)</a>
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EulerianPathDirectedEdgesAdjacencyList {

  private final int n; // Number of nodes in the graph
  private int edgeCount; // Number of edges in the graph
  private int[] in, out; // Arrays to track in-degree and out-degree of each node
  private LinkedList<Integer> path; // Stores the final Eulerian path
  private List<List<Integer>> graph; // Adjacency list representation of the graph

  /**
   * Initializes the solver with an adjacency list representation of a directed graph.
   *
   * @param graph Adjacency list where graph.get(i) contains the neighbors of node i
   */
  public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) {
      throw new IllegalArgumentException("Graph cannot be null");
    }
    this.n = graph.size();
    this.graph = graph;
    this.path = new LinkedList<>();
  }

  /**
   * Finds an Eulerian path in the graph if one exists.
   *
   * <p>The algorithm first verifies the necessary conditions for an Eulerian path based on vertex
   * degrees and then uses Hierholzer's algorithm to construct the path via DFS.
   *
   * @return An array of node IDs representing the Eulerian path, or null if no path exists or the
   *     graph is disconnected.
   *
   * <p>Time: O(V + E)
   * <p>Space: O(V + E)
   */
  public int[] getEulerianPath() {
    setUp();

    if (!graphHasEulerianPath()) {
      return null;
    }

    // Start DFS from a valid starting node
    dfs(findStartNode());

    // Check if all edges were traversed. If the graph is disconnected
    // (excluding isolated nodes with no edges), path.size() will be less than edgeCount + 1.
    if (path.size() != edgeCount + 1) {
      return null;
    }

    // Convert the path from LinkedList to a primitive array for the caller's convenience.
    int[] soln = new int[edgeCount + 1];
    for (int i = 0; !path.isEmpty(); i++) {
      soln[i] = path.removeFirst();
    }

    return soln;
  }

  // Pre-computes in-degrees, out-degrees and the total edge count.
  private void setUp() {
    in = new int[n];
    out = new int[n];
    edgeCount = 0;

    for (int from = 0; from < n; from++) {
      for (int to : graph.get(from)) {
        in[to]++;
        out[from]++;
        edgeCount++;
      }
    }
  }

  // A directed graph has an Eulerian path if and only if:
  // 1. At most one vertex has outDegree - inDegree = 1 (start node)
  // 2. At most one vertex has inDegree - outDegree = 1 (end node)
  // 3. All other vertices have inDegree == outDegree
  private boolean graphHasEulerianPath() {
    if (edgeCount == 0) {
      return false;
    }
    int startNodes = 0, endNodes = 0;
    for (int i = 0; i < n; i++) {
      int diff = out[i] - in[i];
      if (Math.abs(diff) > 1) {
        return false;
      } else if (diff == 1) {
        startNodes++;
      } else if (diff == -1) {
        endNodes++;
      }
    }
    return (endNodes == 0 && startNodes == 0) || (endNodes == 1 && startNodes == 1);
  }

  // Identifies a node to begin the Eulerian path traversal.
  private int findStartNode() {
    int start = 0;
    for (int i = 0; i < n; i++) {
      // If a node has one more outgoing edge than incoming, it MUST be the start.
      if (out[i] - in[i] == 1) {
        return i;
      }
      // Otherwise, start at the first node encountered with at least one outgoing edge.
      if (out[i] > 0) {
        start = i;
      }
    }
    return start;
  }

  /**
   * Recursive DFS implementation of Hierholzer's algorithm.
   *
   * <p>We traverse edges until we reach a node with no remaining outgoing edges, then backtrack.
   * During backtracking, we add the current node to the front of the path. This naturally merges
   * all sub-cycles into the main path.
   *
   * @param at The current node in the DFS traversal
   */
  private void dfs(int at) {
    while (out[at] != 0) {
      // Pick the next available edge. We decrement out[at] to "remove" the edge
      // and use it as an index to select the next neighbor. This is O(1) per edge.
      int next = graph.get(at).get(--out[at]);
      dfs(next);
    }
    // As we backtrack from the recursion, add nodes to the start of the path.
    path.addFirst(at);
  }

  /* Graph creation helper methods */

  /**
   * Initializes an empty adjacency list with n nodes.
   *
   * @param n The number of nodes in the graph
   * @return An empty adjacency list
   */
  public static List<List<Integer>> initializeEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  /**
   * Adds a directed edge from one node to another.
   *
   * @param g Adjacency list to add the edge to
   * @param from The source node index
   * @param to The destination node index
   */
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

    EulerianPathDirectedEdgesAdjacencyList solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // Expected path: [1, 3, 5, 6, 3, 2, 4, 3, 1, 2, 2, 4, 6]
    int[] path = solver.getEulerianPath();
    System.out.println("Path from slides: " + Arrays.toString(path));
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

    EulerianPathDirectedEdgesAdjacencyList solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // Expected path: [0, 1, 4, 1, 2, 1, 3]
    int[] path = solver.getEulerianPath();
    System.out.println("Small example path: " + Arrays.toString(path));
  }
}
