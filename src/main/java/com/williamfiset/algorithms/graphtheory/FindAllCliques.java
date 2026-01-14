package com.williamfiset.algorithms.graphtheory;

/**
 * Finds all cliques (complete sub-graphs) in a given graph. The algorithm loops and finds all
 * cliques for each given clique size up until the size of the graph itself.
 *
 * <p>Complexity: O(n^2)
 */
public class FindAllCliques {
  static int size = 10000;
  static int[] vertices = new int[size];
  static int n;
  static int[][] graph = new int[size][size];
  static int[] degree = new int[size];

  /**
   * Function that checks if the given set of vertices in store array is a clique or not
   *
   * @param count number of vertices in the vertices array
   * @return true id the current subgraph is a clique false otherwise
   */
  static boolean isClique(int count) {
    for (int i = 1; i < count; i++) {
      for (int j = i + 1; j < count; j++) {
        // for missing edges
        if (graph[vertices[i]][vertices[j]] == 0) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Function that prints the clique
   *
   * @param n number of nodes in the clique
   */
  static void print(int n) {
    for (int i = 1; i < n; i++) {
      if (i < n - 1) {
        System.out.print(vertices[i] + ", ");
      } else {
        System.out.println(vertices[i]);
      }
    }
  }

  /**
   * Function that finds all the cliques of size s
   *
   * @param i initial index counter
   * @param currentSize the size of the current sub-graph
   * @param size the vertex count of the searched clique
   */
  static void findCliques(int i, int currentSize, int size) {
    // Find insertable vertices
    for (int j = i + 1; j <= n - (size - currentSize); j++) {
      if (degree[j] >= size - 1) {
        vertices[currentSize] = j;
        // Max subgraph size achieved
        if (isClique(currentSize + 1)) {
          // Max subgraph size not yet achieved
          if (currentSize < size) {
            findCliques(j, currentSize + 1, size);
          } else {
            print(currentSize + 1);
          }
        }
      }
    }
  }

  /**
   * Function that finds all cliques
   *
   * @param edges the list of edges formed with the nodes of the graph
   * @param nodesNr the number of nodes of the graph
   */
  public static void findAllCliques(int[][] edges, int nodesNr) {
    n = nodesNr;
    for (int[] edge : edges) {
      graph[edge[0]][edge[1]] = 1;
      graph[edge[1]][edge[0]] = 1;
      degree[edge[0]]++;
      degree[edge[1]]++;
    }
    for (int i = 1; i <= n; i++) {
      findCliques(0, 1, i);
    }
  }

  public static void main(String[] args) {
    int[][] edges = {
      {1, 2},
      {2, 3},
      {3, 1},
      {4, 3},
      {4, 5},
      {5, 3},
    };
    findAllCliques(edges, 5);
  }
}
