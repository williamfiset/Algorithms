/**
 * Implementation of Kosaraju's SCC algorithm
 *
 * <p>Verified against:
 *
 * <ul>
 *   <li>https://open.kattis.com/problems/equivalences
 *   <li>https://open.kattis.com/problems/runningmom
 * </ul>
 *
 * <p>./gradlew run -Palgorithm=graphtheory.Kosaraju
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Kosaraju {

  private int n;
  private int sccCount;
  private boolean solved;
  private int[] sccs;
  private boolean[] visited;

  private Deque<Integer> stack;

  private List<List<Integer>> graph;
  private List<List<Integer>> transverseGraph;

  public Kosaraju(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
  }

  private void createTransverseGraph() {
    transverseGraph = createGraph(n);
    for (int u = 0; u < n; u++) {
      for (int v : graph.get(u)) {
        addEdge(transverseGraph, v, u);
      }
    }
  }

  // Returns the number of strongly connected components in the graph.
  public int sccCount() {
    if (!solved) solve();
    return sccCount;
  }

  // Get the connected components of this graph. If two indexes
  // have the same value then they're in the same SCC.
  public int[] getSccs() {
    if (!solved) solve();
    return sccs;
  }

  public void solve() {
    sccCount = 0;
    sccs = new int[n];
    visited = new boolean[n];
    stack = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
      dfs1(i);
    }
    createTransverseGraph();

    Arrays.fill(visited, false);

    while (!stack.isEmpty()) {
      int top = stack.pop();
      if (!visited[top]) {
        dfs2(top);
        sccCount++;
      }
    }
  }

  private void dfs1(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : graph.get(from)) {
      dfs1(to);
    }
    stack.push(from);
  }

  private void dfs2(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : transverseGraph.get(from)) {
      dfs2(to);
    }
    sccs[from] = sccCount;
  }

  // Initializes adjacency list with n nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Adds a directed edge from node 'from' to node 'to'
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

    Kosaraju solver = new Kosaraju(graph);
    int[] sccs = solver.getSccs();
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++) {
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    // Prints:
    // Number of Strongly Connected Components: 3
    // Nodes: [3, 7] form a Strongly Connected Component.
    // Nodes: [4, 5, 6] form a Strongly Connected Component.
    // Nodes: [0, 1, 2] form a Strongly Connected Component.
    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }
}
