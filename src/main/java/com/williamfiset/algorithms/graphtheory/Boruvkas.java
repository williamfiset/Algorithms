package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Boruvkas {

  static class Edge implements Comparable<Edge> {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
      this.u = u;
      this.v = v;
      this.cost = cost;
    }

    @Override
    public String toString() {
      return String.format("%d %d, cost: %d", u, v, cost);
    }

    @Override
    public int compareTo(Edge other) {
      int cmp = cost - other.cost;
      // Break ties by picking lexicographically smallest edge pair.
      if (cmp == 0) {
        cmp = u - other.u;
        if (cmp == 0) return v - other.v;
        return cmp;
      }
      return cmp;
    }
  }

  // Inputs
  private final int n; // Number of nodes
  private final Edge[] graph; // Edge list

  // Internal
  private boolean solved;
  private boolean mstExists;

  // Outputs
  private long minCostSum;
  private List<Edge> mst;

  public Boruvkas(int n, Edge[] graph) {
    if (graph == null) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
  }

  // Returns the edges used in finding the minimum spanning tree, or returns
  // null if no MST exists.
  public List<Edge> getMst() {
    solve();
    return mstExists ? mst : null;
  }

  public Long getMstCost() {
    solve();
    return mstExists ? minCostSum : null;
  }

  // Given a graph represented as an edge list this method finds
  // the Minimum Spanning Tree (MST) cost if there exists
  // a MST, otherwise it returns null.
  private void solve() {
    if (solved) return;

    mst = new ArrayList<>();
    UnionFind uf = new UnionFind(n);

    while (uf.components > 1) {
      Edge[] cheapest = new Edge[n];

      // Find the cheapest edge for each component
      for (Edge e : graph) {
        int root1 = uf.find(e.u);
        int root2 = uf.find(e.v);
        if (root1 == root2) continue;

        if (cheapest[root1] == null || e.cost < cheapest[root1].cost) {
          cheapest[root1] = e;
        }
        if (cheapest[root2] == null || e.cost < cheapest[root2].cost) {
          cheapest[root2] = e;
        }
      }

      // Add the cheapest edges to the MST
      for (int i = 0; i < n; i++) {
        Edge e = cheapest[i];
        if (e == null) {
          continue;
        }
        int root1 = uf.find(e.u);
        int root2 = uf.find(e.v);
        if (root1 != root2) {
          uf.union(root1, root2);
          mst.add(e);
          minCostSum += e.cost;
        }
      }
    }

    mstExists = (mst.size() == n - 1);
    solved = true;
  }

  private boolean check() {

    if (!mstExists) return true;

    // check that it is acyclic
    UnionFind uf = new UnionFind(n);
    for (Edge e : mst) {
      int u = e.u, v = e.v;
      if (uf.connected(u, v)) {
        System.err.println("Not a forest");
        return false;
      }
      uf.union(u, v);
    }

    // check that it is a spanning forest
    for (Edge e : mst) {
      int u = e.u, v = e.v;
      if (!uf.connected(u, v)) {
        System.err.println("Not a spanning forest");
        return false;
      }
    }

    // check that it is a minimal spanning forest (cut optimality conditions)
    for (Edge e : mst) {

      // all edges in MST except e
      uf = new UnionFind(n);
      for (Edge f : mst) {
        int x = f.u, y = f.v;
        if (f != e) uf.union(x, y);
      }

      // check that e is min weight edge in crossing cut
      for (Edge f : graph) {
        int x = f.u, y = f.v;
        if (!uf.connected(x, y)) {
          if (f.cost < e.cost) {
            System.err.println("Edge " + f + " violates cut optimality conditions");
            return false;
          }
        }
      }
    }
    return true;
  }

  public static void main(String[] args) {

    int n = 10, m = 18, i = 0;
    Edge[] g = new Edge[m];

    // Edges are treated as undirected
    g[i++] = new Edge(0, 1, 5);
    g[i++] = new Edge(0, 3, 4);
    g[i++] = new Edge(0, 4, 1);
    g[i++] = new Edge(1, 2, 4);
    g[i++] = new Edge(1, 3, 2);
    g[i++] = new Edge(2, 7, 4);
    g[i++] = new Edge(2, 8, 1);
    g[i++] = new Edge(2, 9, 2);
    g[i++] = new Edge(3, 6, 11);
    g[i++] = new Edge(3, 7, 2);
    g[i++] = new Edge(4, 3, 2);
    g[i++] = new Edge(4, 5, 1);
    g[i++] = new Edge(5, 3, 5);
    g[i++] = new Edge(5, 6, 7);
    g[i++] = new Edge(6, 7, 1);
    g[i++] = new Edge(6, 8, 4);
    g[i++] = new Edge(7, 8, 6);
    g[i++] = new Edge(9, 8, 0);

    Boruvkas solver = new Boruvkas(n, g);

    Long ans = solver.getMstCost();
    if (ans != null) {
      System.out.println("MST cost: " + ans);
      for (Edge e : solver.getMst()) {
        System.out.println(e);
      }
    } else {
      System.out.println("No MST exists");
    }
  }

  // Union find data structure
  private static class UnionFind {
    int components;
    int[] id, sz;

    public UnionFind(int n) {
      components = n;
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
      int root1 = find(p), root2 = find(q);
      if (root1 == root2) return;
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
