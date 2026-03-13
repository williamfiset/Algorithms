/**
 * Bipartite Graph Check — Adjacency List (DFS coloring)
 *
 * Determines if an undirected graph is bipartite (2-colorable) by attempting
 * to color it with two colors via DFS. A graph is bipartite if and only if
 * it contains no odd-length cycles.
 *
 * The algorithm starts a DFS from node 0, alternating colors RED and BLACK.
 * If a neighbor already has the same color as the current node, the graph
 * is not bipartite.
 *
 * Note: this implementation only checks the connected component containing
 * node 0. Disconnected graphs with multiple components are reported as
 * not bipartite.
 *
 * Time:  O(V + E)
 * Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

import java.util.ArrayList;
import java.util.List;

public class BipartiteGraphCheckAdjacencyList {

  // Color constants. 0 means unvisited; RED and BLACK are the two colors.
  // XOR with 0b01 toggles between them: RED ^ 1 = BLACK, BLACK ^ 1 = RED.
  public static final int RED = 0b10, BLACK = 0b11;

  private final int n;
  private final List<List<Integer>> graph;
  private int[] colors;
  private boolean solved;
  private boolean isBipartite;

  public BipartiteGraphCheckAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    this.n = graph.size();
    this.graph = graph;
  }

  /** Returns true if the graph is bipartite (2-colorable). */
  public boolean isBipartite() {
    if (!solved) solve();
    return isBipartite;
  }

  /**
   * Returns the two-coloring array if the graph is bipartite, null otherwise.
   * Each entry is either RED or BLACK.
   */
  public int[] getTwoColoring() {
    return isBipartite() ? colors : null;
  }

  private void solve() {
    if (n <= 1) return;
    colors = new int[n];
    int nodesVisited = colorGraph(0, RED);
    isBipartite = (nodesVisited == n);
    solved = true;
  }

  /**
   * DFS that colors nodes alternately. Returns the number of nodes visited,
   * or -1 if a coloring contradiction is found.
   */
  private int colorGraph(int i, int color) {
    colors[i] = color;
    int nextColor = color ^ 1;

    int visitCount = 1;
    for (int to : graph.get(i)) {
      // Same color as current node → odd cycle → not bipartite.
      if (colors[to] == color) return -1;
      if (colors[to] == nextColor) continue;

      int count = colorGraph(to, nextColor);
      if (count == -1) return -1;
      visitCount += count;
    }

    return visitCount;
  }

  /* Graph helpers */

  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // ==================== Main ====================

  public static void main(String[] args) {
    testSelfLoop();
    testTwoNodes();
    testTriangle();
    testDisjoint();
    testSquare();
    testSquareWithDiagonal();
  }

  //
  //  +-+
  //  |0| (self-loop)
  //  +-+
  //
  // Not bipartite: node 0 is adjacent to itself.
  //
  private static void testSelfLoop() {
    List<List<Integer>> g = createGraph(1);
    addUndirectedEdge(g, 0, 0);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // false
  }

  //
  //  R --- B
  //  0     1
  //
  // Bipartite: {0=RED, 1=BLACK}
  //
  private static void testTwoNodes() {
    List<List<Integer>> g = createGraph(2);
    addUndirectedEdge(g, 0, 1);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // true
  }

  //
  //  R --- B
  //  0     1
  //   \   /
  //     2
  //     ?  ← must be both R and B
  //
  // Not bipartite: odd cycle (0-1-2).
  //
  private static void testTriangle() {
    List<List<Integer>> g = createGraph(3);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 2, 0);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // false
  }

  //
  //  R --- B        ? --- ?
  //  0     1        2     3
  //
  // Not bipartite: nodes 2,3 unreachable from node 0.
  //
  private static void testDisjoint() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 2, 3);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // false
  }

  //
  //  R --- B
  //  0     1
  //  |     |
  //  3     2
  //  B --- R
  //
  // Bipartite: {0=RED, 1=BLACK, 2=RED, 3=BLACK}
  //
  private static void testSquare() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 2, 3);
    addUndirectedEdge(g, 3, 0);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // true
  }

  //
  //  R --- B
  //  0     1
  //  | \   |
  //  |   \ |
  //  3     2
  //  B     ?  ← must be both R and B
  //
  // Not bipartite: diagonal 0-2 creates odd cycle (0-1-2).
  //
  private static void testSquareWithDiagonal() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 2, 3);
    addUndirectedEdge(g, 3, 0);
    addUndirectedEdge(g, 0, 2);
    System.out.println(new BipartiteGraphCheckAdjacencyList(g).isBipartite()); // false
  }
}
