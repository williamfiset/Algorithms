/**
 * Connected Components — Adjacency List (Union-Find)
 *
 * <p>Finds all connected components of an undirected graph using a Union-Find
 * data structure. Each edge merges the two endpoint components; the final number
 * of disjoint sets is the component count.
 *
 * <p>For directed graphs, use Tarjan's or Kosaraju's algorithm to find
 * <em>strongly</em> connected components instead.
 *
 * <p>Time:  O(V + E * α(V)) ≈ O(V + E)
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

public class ConnectedComponentsUnionFind {

  private final int n;
  private final List<List<Integer>> graph;
  private boolean solved;
  private UnionFind uf;

  public ConnectedComponentsUnionFind(List<List<Integer>> graph) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }
    this.n = graph.size();
    this.graph = graph;
  }

  /**
   * Returns the number of connected components.
   */
  public int countComponents() {
    solve();
    return uf.components;
  }

  /**
   * Returns the component id (root node) of the given node.
   */
  public int componentId(int node) {
    solve();
    return uf.find(node);
  }

  private void solve() {
    if (solved) {
      return;
    }

    uf = new UnionFind(n);
    for (int u = 0; u < n; u++) {
      for (int v : graph.get(u)) {
        uf.union(u, v);
      }
    }

    solved = true;
  }

  // ==================== Main ====================

  //
  //    0 --- 1       3 --- 6
  //    |   /         |     |
  //    |  /          |     |
  //    2             4     9
  //
  //    5       7 --- 8       10
  //
  //  Components: {0,1,2}, {3,4,6,9}, {5}, {7,8}, {10}
  //  Count: 5
  //
  public static void main(String[] args) {
    int n = 11;
    List<List<Integer>> graph = createGraph(n);

    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 0, 2);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 3, 6);
    addUndirectedEdge(graph, 6, 9);
    addUndirectedEdge(graph, 7, 8);

    ConnectedComponentsUnionFind solver =
        new ConnectedComponentsUnionFind(graph);

    System.out.printf("Number of components: %d%n", solver.countComponents());

    for (int i = 0; i < n; i++) {
      System.out.printf("Node %d -> component %d%n", i, solver.componentId(i));
    }
  }

  private static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  private static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // Union-Find with path compression and union by size.
  private static class UnionFind {
    int components;
    private final int[] id;
    private final int[] sz;

    UnionFind(int n) {
      components = n;
      id = new int[n];
      sz = new int[n];
      for (int i = 0; i < n; i++) {
        id[i] = i;
        sz[i] = 1;
      }
    }

    int find(int p) {
      if (id[p] != p) {
        id[p] = find(id[p]);
      }
      return id[p];
    }

    void union(int p, int q) {
      int root1 = find(p);
      int root2 = find(q);
      if (root1 == root2) {
        return;
      }
      if (sz[root1] < sz[root2]) {
        sz[root2] += sz[root1];
        id[root1] = root2;
      } else {
        sz[root1] += sz[root2];
        id[root2] = root1;
      }
      components--;
    }
  }
}
