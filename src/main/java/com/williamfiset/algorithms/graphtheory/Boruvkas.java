/** WIP */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Boruvkas {

  static class Edge {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
      this.u = u;
      this.v = v;
      this.cost = cost;
    }

    public String toString() {
      return String.format("%d %d, cost: %d", u, v, cost);
    }

    // @Override
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
  private final int n, m; // Num nodes, num edges
  private final Edge[] graph; // Edge list

  // Internal
  private boolean solved;
  private boolean mstExists;

  // Outputs
  private long minCostSum;
  private List<Edge> mst;

  public Boruvkas(int n, int m, Edge[] graph) {
    if (graph == null) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
    this.m = m;
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

    int[] cheapest = new int[n];
    Arrays.fill(cheapest, -1);

    // Repeat at most log(n) times or until we have a complete spanning tree.
    // for(int t = 1; t < N && index < n - 1; t = t + t) {
    // for(long t = 1; t <= n && mst.size() != n-1; t = t << 1) {
    for (; mst.size() != n - 1; ) {

      // TODO: Remove
      Arrays.fill(cheapest, -1);
      boolean stop = true;

      for (int i = 0; i < graph.length; i++) {
        Edge e = graph[i];
        if (e.u == e.v) continue;
        int uc = uf.id[e.u], vc = uf.id[e.v];
        if (uc == vc) continue;
        // if (cheapest[vc] == -1 || e.compareTo(graph[cheapest[vc]]) < 0) { stop = false;
        // cheapest[vc] = i; }
        // if (cheapest[uc] == -1 || e.compareTo(graph[cheapest[uc]]) < 0) { stop = false;
        // cheapest[uc] = i; }
        if (cheapest[vc] == -1 || e.cost < graph[cheapest[vc]].cost) {
          stop = false;
          cheapest[vc] = i;
        }
        if (cheapest[uc] == -1 || e.cost < graph[cheapest[uc]].cost) {
          stop = false;
          cheapest[uc] = i;
        }
      }

      if (stop) break;

      for (int i = 0; i < n; i++) {
        if (cheapest[i] == -1) continue;
        Edge e = graph[cheapest[i]];
        // cheapest[i] = -1;
        if (uf.connected(e.u, e.v)) continue;

        mst.add(e);
        minCostSum += e.cost;
        uf.union(e.u, e.v);

        // TODO(williamfiset): Optimization is to remove e from graph.
      }
    }

    // if ( (index==n-1) != (uf.size(0) == n) ) throw new NullPointerException();

    mstExists = (mst.size() == n - 1); // (uf.size(0) == n);
    solved = true;

    // if (!check()) throw new IllegalStateException();
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

    Boruvkas solver = new Boruvkas(n, m, g);

    Long ans = solver.getMstCost();
    if (ans != null) {
      System.out.println("MST cost: " + ans);
      for (Edge e : solver.getMst()) {
        System.out.println(e);
      }
    } else {
      System.out.println("No MST exists");
    }

    // System.out.println(solver.solve(g, n));

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
