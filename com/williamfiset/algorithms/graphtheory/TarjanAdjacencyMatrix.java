/**
 * An implementation of Tarjan's SCC algorithm for a directed graph. Time Complexity: O(V^2)
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

class TarjanAdjacencyMatrix {

  public static void main(String[] args) {

    // As an example we create a graph with four strongly connected components

    final int NUM_NODES = 10;

    boolean[][] adjMatrix = new boolean[NUM_NODES][NUM_NODES];

    // SCC 1 with nodes 0,1,2
    adjMatrix[0][1] = true;
    adjMatrix[1][2] = true;
    adjMatrix[2][0] = true;

    // SCC 2 with nodes 3,4,5,6
    adjMatrix[5][4] = true;
    adjMatrix[5][6] = true;
    adjMatrix[3][5] = true;
    adjMatrix[4][3] = true;
    adjMatrix[4][5] = true;
    adjMatrix[6][4] = true;

    // SCC 3 with nodes 7,8
    adjMatrix[7][8] = true;
    adjMatrix[8][7] = true;

    // SCC 4 is node 9 all alone by itself
    // Add a few more edges to make things interesting
    adjMatrix[1][5] = true;
    adjMatrix[1][7] = true;
    adjMatrix[2][7] = true;
    adjMatrix[6][8] = true;
    adjMatrix[9][8] = true;
    adjMatrix[9][4] = true;

    Tarjan sccs = new Tarjan(adjMatrix);

    System.out.println(
        "Strong connected component count: " + sccs.countStronglyConnectedComponents());
    System.out.println(
        "Strong connected components:\n" + Arrays.toString(sccs.getStronglyConnectedComponents()));

    // Output:
    // Strong connected component count: 4
    // Strong connected components:
    // [2, 2, 2, 1, 1, 1, 1, 0, 0, 3]

  }

  // Tarjan is used to find/count the Strongly Connected
  // Components (SCCs) in a directed graph in O(V+E).
  static class Tarjan {

    private int n, pre, count = 0;
    private int[] id, low;
    private boolean[] marked;
    private boolean[][] adj;
    private Stack<Integer> stack = new Stack<>();

    // Tarjan input requires an NxN adjacency matrix
    public Tarjan(boolean[][] adj) {
      n = adj.length;
      this.adj = adj;
      marked = new boolean[n];
      id = new int[n];
      low = new int[n];
      for (int u = 0; u < n; u++) if (!marked[u]) dfs(u);
    }

    private void dfs(int u) {
      marked[u] = true;
      low[u] = pre++;
      int min = low[u];
      stack.push(u);
      for (int v = 0; v < n; v++) {
        if (adj[u][v]) {
          if (!marked[v]) dfs(v);
          if (low[v] < min) min = low[v];
        }
      }
      if (min < low[u]) {
        low[u] = min;
        return;
      }
      int v;
      do {
        v = stack.pop();
        id[v] = count;
        low[v] = n;
      } while (v != u);
      count++;
    }

    // Returns the id array with the strongly connected components.
    // If id[i] == id[j] then nodes i and j are part of the same strongly connected component.
    public int[] getStronglyConnectedComponents() {
      return id.clone();
    }

    // Returns the number of strongly connected components in this graph
    public int countStronglyConnectedComponents() {
      return count;
    }
  }
}
