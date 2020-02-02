/**
 * An implementation of Kruskal's MST algorithm using an edge list Time Complexity: O(ElogE)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

public class KruskalsEdgeList {

  // Union find data structure
  static class UnionFind {

    private int[] id, sz;

    public UnionFind(int n) {
      id = new int[n];
      sz = new int[n];
      for (int i = 0; i < n; i++) {
        id[i] = i;
        sz[i] = 1;
      }
    }

    public int find(int p) {
      int root = p;
      while (root != id[root]) root = id[root];
      while (p != root) { // Do path compression
        int next = id[p];
        id[p] = root;
        p = next;
      }
      return root;
    }

    public boolean connected(int p, int q) {
      return find(p) == find(q);
    }

    public int size(int p) {
      return sz[find(p)];
    }

    public void union(int p, int q) {
      int root1 = find(p);
      int root2 = find(q);
      if (root1 == root2) return;
      if (sz[root1] < sz[root2]) {
        sz[root2] += sz[root1];
        id[root1] = root2;
      } else {
        sz[root1] += sz[root2];
        id[root2] = root1;
      }
    }
  }

  static class Edge implements Comparable<Edge> {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public int compareTo(Edge other) {
      return cost - other.cost;
    }
  }

  // Given a graph represented as an edge list this method finds
  // the Minimum Spanning Tree (MST) cost if there exists
  // a MST, otherwise it returns null.
  static Long kruskals(Edge[] edges, int n) {

    if (edges == null) return null;

    long sum = 0L;
    java.util.Arrays.sort(edges);
    UnionFind uf = new UnionFind(n);

    for (Edge edge : edges) {

      // Skip this edge to avoid creating a cycle in MST
      if (uf.connected(edge.from, edge.to)) continue;

      // Include this edge
      uf.union(edge.from, edge.to);
      sum += edge.cost;

      // Optimization to stop early if we found
      // a MST that includes all the nodes
      if (uf.size(0) == n) break;
    }

    // Make sure we have a MST that includes all the nodes
    if (uf.size(0) != n) return null;

    return sum;
  }
}
