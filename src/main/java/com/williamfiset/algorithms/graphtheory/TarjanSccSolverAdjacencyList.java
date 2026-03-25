/**
 * Implementation of Tarjan's Strongly Connected Components algorithm using an adjacency list.
 *
 * <p>Verified against:
 *
 * <ul>
 *   <li>https://open.kattis.com/problems/equivalences
 *   <li>https://open.kattis.com/problems/runningmom
 *   <li>https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/tutorial
 * </ul>
 *
 * <p>Time: O(V + E)
 *
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Math.min;

import java.util.*;

public class TarjanSccSolverAdjacencyList {

  private final int n;
  private final List<List<Integer>> graph;

  private boolean solved;
  private int sccCount, id;
  private boolean[] onStack;
  private int[] ids, low, sccs;
  private Deque<Integer> stack;

  private static final int UNVISITED = -1;

  /**
   * Creates a Tarjan SCC solver for the given directed graph.
   *
   * @param graph adjacency list representation of a directed graph.
   * @throws IllegalArgumentException if the graph is null.
   */
  public TarjanSccSolverAdjacencyList(List<List<Integer>> graph) {
    if (graph == null)
      throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
  }

  /** Returns the number of strongly connected components in the graph. */
  public int sccCount() {
    if (!solved)
      solve();
    return sccCount;
  }

  /**
   * Returns the SCC id for each node. If two nodes have the same value, they belong to the same
   * SCC.
   */
  public int[] getSccs() {
    if (!solved)
      solve();
    return sccs;
  }

  /** Runs Tarjan's algorithm. */
  public void solve() {
    if (solved)
      return;

    ids = new int[n];
    low = new int[n];
    sccs = new int[n];
    onStack = new boolean[n];
    stack = new ArrayDeque<>();
    Arrays.fill(ids, UNVISITED);

    for (int i = 0; i < n; i++)
      if (ids[i] == UNVISITED)
        dfs(i);

    solved = true;
  }

  private void dfs(int at) {
    ids[at] = low[at] = id++;
    stack.push(at);
    onStack[at] = true;

    for (int to : graph.get(at)) {
      if (ids[to] == UNVISITED)
        dfs(to);
      if (onStack[to])
        low[at] = min(low[at], low[to]);
    }

    // If we're at the root of an SCC, pop all nodes in this component off the stack.
    if (ids[at] == low[at]) {
      for (int node = stack.pop(); ; node = stack.pop()) {
        onStack[node] = false;
        sccs[node] = sccCount;
        if (node == at)
          break;
      }
      sccCount++;
    }
  }

  /** Creates an adjacency list with n nodes. */
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++)
      graph.add(new ArrayList<>());
    return graph;
  }

  /** Adds a directed edge from node 'from' to node 'to'. */
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void main(String[] args) {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 6, 0);
    addEdge(graph, 6, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 6, 4);
    addEdge(graph, 2, 0);
    addEdge(graph, 0, 1);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 5);
    addEdge(graph, 1, 2);
    addEdge(graph, 7, 3);
    addEdge(graph, 5, 0);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(graph);

    int[] sccs = solver.getSccs();
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++) {
      multimap.computeIfAbsent(sccs[i], k -> new ArrayList<>()).add(i);
    }

    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }
}
