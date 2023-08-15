/** WIP */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Boruvkas {

  // Inputs
  private final int n, m; // Num nodes, num edges
  private final CostComparingEdge[] graph; // Edge list

  // Internal
  private boolean solved;
  private boolean mstExists;

  // Outputs
  private long minCostSum;
  private List<CostComparingEdge> mst;

  public Boruvkas(int n, int m, CostComparingEdge[] graph) {
    if (graph == null) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
    this.m = m;
  }

  // Returns the edges used in finding the minimum spanning tree, or returns
  // null if no MST exists.
  public List<CostComparingEdge> getMst() {
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
        CostComparingEdge e = graph[i];
        if (e.getFrom() == e.getTo()) continue;
        int uc = uf.id[e.getFrom()], vc = uf.id[e.getTo()];
        if (uc == vc) continue;
        // if (cheapest[vc] == -1 || e.compareTo(graph[cheapest[vc]]) < 0) { stop = false;
        // cheapest[vc] = i; }
        // if (cheapest[uc] == -1 || e.compareTo(graph[cheapest[uc]]) < 0) { stop = false;
        // cheapest[uc] = i; }
        if (cheapest[vc] == -1 || e.getCost() < graph[cheapest[vc]].getCost()) {
          stop = false;
          cheapest[vc] = i;
        }
        if (cheapest[uc] == -1 || e.getCost() < graph[cheapest[uc]].getCost()) {
          stop = false;
          cheapest[uc] = i;
        }
      }

      if (stop) break;

      for (int i = 0; i < n; i++) {
        if (cheapest[i] == -1) continue;
        CostComparingEdge e = graph[cheapest[i]];
        // cheapest[i] = -1;
        if (uf.connected(e.getFrom(), e.getTo())) continue;

        mst.add(e);
        minCostSum += e.getCost();
        uf.union(e.getFrom(), e.getTo());

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
    for (CostComparingEdge e : mst) {
      int u = e.getFrom(), v = e.getTo();
      if (uf.connected(u, v)) {
        System.err.println("Not a forest");
        return false;
      }
      uf.union(u, v);
    }

    // check that it is a spanning forest
    for (CostComparingEdge e : mst) {
      int u = e.getFrom(), v = e.getTo();
      if (!uf.connected(u, v)) {
        System.err.println("Not a spanning forest");
        return false;
      }
    }

    // check that it is a minimal spanning forest (cut optimality conditions)
    for (CostComparingEdge e : mst) {

      // all edges in MST except e
      uf = new UnionFind(n);
      for (CostComparingEdge f : mst) {
        int x = f.getFrom(), y = f.getTo();
        if (f != e) uf.union(x, y);
      }

      // check that e is min weight edge in crossing cut
      for (CostComparingEdge f : graph) {
        int x = f.getFrom(), y = f.getTo();
        if (!uf.connected(x, y)) {
          if (f.getCost() < e.getCost()) {
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
    CostComparingEdge[] g = new CostComparingEdge[m];

    // Edges are treated as undirected
    g[i++] = new CostComparingEdge(0, 1, 5);
    g[i++] = new CostComparingEdge(0, 3, 4);
    g[i++] = new CostComparingEdge(0, 4, 1);
    g[i++] = new CostComparingEdge(1, 2, 4);
    g[i++] = new CostComparingEdge(1, 3, 2);
    g[i++] = new CostComparingEdge(2, 7, 4);
    g[i++] = new CostComparingEdge(2, 8, 1);
    g[i++] = new CostComparingEdge(2, 9, 2);
    g[i++] = new CostComparingEdge(3, 6, 11);
    g[i++] = new CostComparingEdge(3, 7, 2);
    g[i++] = new CostComparingEdge(4, 3, 2);
    g[i++] = new CostComparingEdge(4, 5, 1);
    g[i++] = new CostComparingEdge(5, 3, 5);
    g[i++] = new CostComparingEdge(5, 6, 7);
    g[i++] = new CostComparingEdge(6, 7, 1);
    g[i++] = new CostComparingEdge(6, 8, 4);
    g[i++] = new CostComparingEdge(7, 8, 6);
    g[i++] = new CostComparingEdge(9, 8, 0);

    Boruvkas solver = new Boruvkas(n, m, g);

    Long ans = solver.getMstCost();
    if (ans != null) {
      System.out.println("MST cost: " + ans);
      for (CostComparingEdge e : solver.getMst()) {
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
