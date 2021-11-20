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

  // The post order forest traversal of the original graph resulting from the first DFS.
  private List<Integer> postOrderTraversal;

  private List<List<Integer>> graph;
  private List<List<Integer>> transposeGraph;

  public Kosaraju(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    this.graph = graph;
    n = graph.size();
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

  private void solve() {
    sccCount = 0;
    sccs = new int[n];
    visited = new boolean[n];
    postOrderTraversal = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      dfs1(i);
    }

    Arrays.fill(visited, false);
    createTransposeGraph();

    // Reverse the post order traversal to make iterating through it
    // in the next step more intuitive.
    Collections.reverse(postOrderTraversal);

    for (int node : postOrderTraversal) {
      if (!visited[node]) {
        dfs2(node);
        sccCount++;
      }
    }

    solved = true;
  }

  // Traverse the original graph and add nodes to the post order traversal on the callback.
  private void dfs1(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : graph.get(from)) {
      dfs1(to);
    }
    postOrderTraversal.add(from);
  }

  // Traverse the transverse graph and label all the encountered nodes as part of the sane SCC.
  private void dfs2(int from) {
    if (visited[from]) {
      return;
    }
    visited[from] = true;
    for (int to : transposeGraph.get(from)) {
      dfs2(to);
    }
    sccs[from] = sccCount;
  }

  private void createTransposeGraph() {
    transposeGraph = createGraph(n);
    for (int u = 0; u < n; u++) {
      for (int v : graph.get(u)) {
        addEdge(transposeGraph, v, u);
      }
    }
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
    example1();
    // example2();
    // example3();
    // example4();
    // exampleFromCp4();
  }

  private static void exampleFromCp4() {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 1);
    addEdge(graph, 3, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 7);
    addEdge(graph, 6, 4);
    addEdge(graph, 7, 6);

    runKosaraju(graph);
  }

  private static void example4() {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    // [0, 3, 2, 1, 7, 6, 5, 4]
    addEdge(graph, 0, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 0, 5);
    addEdge(graph, 1, 4);
    addEdge(graph, 1, 7);
    addEdge(graph, 2, 1);
    addEdge(graph, 3, 0);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 2);
    addEdge(graph, 5, 7);
    addEdge(graph, 6, 5);
    addEdge(graph, 7, 6);

    runKosaraju(graph);
  }

  private static void example3() {
    int n = 6;
    List<List<Integer>> graph = createGraph(n);

    // [4, 2, 5, 0, 3, 1]
    addEdge(graph, 0, 2);
    addEdge(graph, 0, 5);
    addEdge(graph, 1, 0);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 4);
    addEdge(graph, 3, 1);
    addEdge(graph, 3, 5);
    addEdge(graph, 4, 0);

    runKosaraju(graph);
  }

  private static void example2() {
    // [8, 9, 5, 4, 7, 3, 2, 6, 1, 0]
    // [5, 4, 8, 9, 3, 2, 7, 1, 6, 0]
    int n = 10;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 6);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 3, 7);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 9);
    addEdge(graph, 6, 1);
    addEdge(graph, 7, 2);
    addEdge(graph, 8, 4);
    addEdge(graph, 9, 8);

    runKosaraju(graph);
  }

  private static void example1() {
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

    // Prints:
    // Number of Strongly Connected Components: 3
    // Nodes: [3, 7] form a Strongly Connected Component.
    // Nodes: [4, 5, 6] form a Strongly Connected Component.
    // Nodes: [0, 1, 2] form a Strongly Connected Component.
    runKosaraju(graph);
  }

  private static void runKosaraju(List<List<Integer>> graph) {
    int n = graph.size();
    Kosaraju solver = new Kosaraju(graph);
    int[] sccs = solver.getSccs();
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++) {
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }
}
