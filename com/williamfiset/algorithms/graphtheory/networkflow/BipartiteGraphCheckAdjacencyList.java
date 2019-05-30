/**
 * This file shows you how to determine if a graph is bipartite or not. This can be achieved in
 * linear time by coloring the visited nodes.
 *
 * <p>Time Complexity: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

import com.williamfiset.algorithms.utils.graphutils.Utils;
import java.util.List;

public class BipartiteGraphCheckAdjacencyList {

  private int n;
  private int[] colors;
  private boolean solved;
  private boolean isBipartite;
  private List<List<Integer>> graph;

  public static final int RED = 0b10, BLACK = (RED ^ 1);

  public BipartiteGraphCheckAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = graph.size();
    this.graph = graph;
  }

  // Checks whether the input graph is bipartite.
  public boolean isBipartite() {
    if (!solved) solve();
    return isBipartite;
  }

  // If the input graph is bipartite it has a two coloring which can be obtained
  // through this method. Each index in the returned array is either RED or BLACK
  // indicating which color node i was colored.
  public int[] getTwoColoring() {
    return isBipartite() ? colors : null;
  }

  private void solve() {
    if (n <= 1) return;

    colors = new int[n];
    int nodesVisited = colorGraph(0, RED);

    // The graph is not bipartite. Either not all the nodes were visited or the
    // colorGraph method returned -1 meaning the graph is not 2-colorable.
    isBipartite = (nodesVisited == n);
    solved = true;
  }

  // Do a depth first search coloring the nodes of the graph as we go.
  // This method returns the count of the number of nodes visited while
  // coloring the graph or -1 if this graph is not bipartite.
  private int colorGraph(int i, int color) {
    colors[i] = color;

    // Toggles the color between RED and BLACK by exploiting the binary representation
    // of the constants and flipping the least significant bit on and off.
    int nextColor = (color ^ 1);

    int visitCount = 1;
    List<Integer> edges = graph.get(i);

    for (int to : edges) {
      // Contradiction found. In a bipartite graph no two
      // nodes of the same color can be next to each other!
      if (colors[to] == color) return -1;
      if (colors[to] == nextColor) continue;

      // If a contradiction is found propagate return -1
      // otherwise keep track of the number of visited nodes.
      int count = colorGraph(to, nextColor);
      if (count == -1) return -1;
      visitCount += count;
    }

    return visitCount;
  }

  /* Example usage */

  public static void main(String[] args) {

    // Singleton (not bipartite)
    int n = 1;
    List<List<Integer>> graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 0);
    displayGraph(graph);

    // Prints:
    // Graph has 1 node(s) and the following edges:
    // 0 -> 0
    // 0 -> 0
    // This graph is bipartite: false

    // Two nodes one edge between them (bipartite)
    n = 2;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    displayGraph(graph);

    // Prints:
    // Graph has 2 node(s) and the following edges:
    // 0 -> 1
    // 1 -> 0
    // This graph is bipartite: true

    // Triangle graph (not bipartite)
    n = 3;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 0);
    displayGraph(graph);

    // Prints:
    // Graph has 3 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 2
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 0
    // This graph is bipartite: false

    // Disjoint graph is bipartite connected components (altogether not bipartite)
    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 2, 3);
    displayGraph(graph);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 1 -> 0
    // 2 -> 3
    // 3 -> 2
    // This graph is bipartite: false

    // Square graph (bipartite)
    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    displayGraph(graph);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 3
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 3
    // 3 -> 2
    // 3 -> 0
    // This graph is bipartite: true

    // Square graph with additional edge (not bipartite)
    n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    Utils.addUndirectedEdge(graph, 0, 2);
    displayGraph(graph);

    // Prints:
    // Graph has 4 node(s) and the following edges:
    // 0 -> 1
    // 0 -> 3
    // 0 -> 2
    // 1 -> 0
    // 1 -> 2
    // 2 -> 1
    // 2 -> 3
    // 2 -> 0
    // 3 -> 2
    // 3 -> 0
    // This graph is bipartite: false

  }

  private static void displayGraph(List<List<Integer>> graph) {
    final int n = graph.size();

    System.out.println("Graph has " + n + " node(s) and the following edges:");
    for (int f = 0; f < n; f++) for (int t : graph.get(f)) System.out.println(f + " -> " + t);

    BipartiteGraphCheckAdjacencyList solver;
    solver = new BipartiteGraphCheckAdjacencyList(graph);

    System.out.println("This graph is bipartite: " + (solver.isBipartite()));
    System.out.println();
  }
}
