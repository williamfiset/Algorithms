/**
 * An implementation of Tarjan's Strongly Connected Components algorithm using an adjacency list.
 *
 * <p>Time complexity: O(V+E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

import static java.lang.Math.min;

public class TarjanSccSolverAdjacencyList {

  private static final int UNVISITED = -1;
  private int n;
  private List<List<Integer>> graph;
  private boolean solved;
  private int sccCount, id;
  private boolean[] onStack;
  private int[] ids, low;
  private Deque<Integer> stack;

  public TarjanSccSolverAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
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

  public static void main(String[] arg) {
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
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    // Prints:
    // Number of Strongly Connected Components: 3
    // Nodes: [0, 1, 2] form a Strongly Connected Component.
    // Nodes: [3, 7] form a Strongly Connected Component.
    // Nodes: [4, 5, 6] form a Strongly Connected Component.
    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount());
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
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
    return low;
  }

  public void solve() {
    if (solved) return;

    ids = new int[n];
    low = new int[n];
    onStack = new boolean[n];
    stack = new ArrayDeque<>();
    Arrays.fill(ids, UNVISITED);

    for (int i = 0; i < n; i++) if (ids[i] == UNVISITED) dfs(i);

    solved = true;
  }

  /* Example usage: */

  private void dfs(int at) {

    stack.push(at);
    onStack[at] = true;
    ids[at] = low[at] = id++;

    for (int to : graph.get(at)) {
      if (ids[to] == UNVISITED) dfs(to);
      if (onStack[to]) low[at] = min(low[at], low[to]);
    }

    // On recursive callback, if we're at the root node (start of SCC)
    // empty the seen stack until back to root.
    if (ids[at] == low[at]) {
      for (int node = stack.pop(); ; node = stack.pop()) {
        onStack[node] = false;
        low[node] = ids[at];
        if (node == at) break;
      }
      sccCount++;
    }
  }
}
