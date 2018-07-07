/**
 * WIP
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Boruvkas {

  static final int INF = 987654321;
  static final Edge INF_EDGE = new Edge(-1, INF, INF, INF);

  static class Edge implements Comparable<Edge> {
    final int i, u, v, cost; // index, from, to, cost
    public Edge(int i, int u, int v, int cost) {
      this.i = i;
      this.u = u;
      this.v = v;
      this.cost = cost;
    }
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
  private final int n, m; // Num nodes, num edges
  private final Edge[] graph; // Edge list

  // Internal
  private boolean solved;
  private boolean mstExists;

  // Outputs
  private long minCostSum;
  private Edge[] mstEdges;

  public Boruvkas(int n, Edge[] graph) {
    if (graph == null) throw new IllegalArgumentException();
    this.graph = graph;
    this.n = n;
    this.m = graph.length;
  }

  // Returns the edges used in finding the minimum spanning tree, or returns
  // null if no MST exists.
  public Edge[] getMst() {
    solve();
    return mstExists ? mstEdges : null;
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

    int index = 0;
    mstEdges = new Edge[n-1];

    boolean[] used = new boolean[m];
    UnionFind uf = new UnionFind(n);

    Edge[] cheapest = new Edge[n];
    Arrays.fill(cheapest, INF_EDGE);

    for(int components = n;;components = uf.components) {

      for (Edge e : graph) {
        int uc = uf.id[e.u], vc = uf.id[e.v];
        if (uc == vc) continue;
        if (e.compareTo(cheapest[vc]) < 0) cheapest[vc] = e;
        if (e.compareTo(cheapest[uc]) < 0) cheapest[uc] = e;
      }

      for (int i = 0; i < n; i++) {
        if (cheapest[i] == INF_EDGE) continue;
        Edge e = cheapest[i];
        if (!used[e.i]) {
          minCostSum += e.cost;
          mstEdges[index++] = e;

          uf.union(e.u, e.v);
          used[e.i] = true;
          // used[e.i^1] = true;

          // TODO(williamfiset): Optimization is to remove e from graph.
        }

        cheapest[i] = INF_EDGE;
      }

      // Was not able to reduce num components.
      if (uf.components == components) break;
    }

    mstExists = (uf.size(0) == n);
    solved = true;
  }

  static Edge[] createEmptyGraph(int m) {
    return new Edge[m];
  }

  static void addDirectedEdge(Edge[] g, int i, int from, int to, int cost) {
    g[i] = new Edge(i, from, to, cost);
  }

  // static void addUndirectedEdge(List<List<Edge>> g, int i, int from, int to, int cost) {
  //   addDirectedEdge(g, i, from, to, cost);
  //   addDirectedEdge(g, i, to, from, cost);
  // }

  public static void main(String[] args) {
    
    int n = 10, m = 18, i = 0;
    Edge[] g = createEmptyGraph(m);

    // Edges are treated as undirected
    addDirectedEdge(g, i++, 0, 1, 5);
    addDirectedEdge(g, i++, 0, 3, 4);
    addDirectedEdge(g, i++, 0, 4, 1);
    addDirectedEdge(g, i++, 1, 2, 4);
    addDirectedEdge(g, i++, 1, 3, 2);
    addDirectedEdge(g, i++, 2, 7, 4);
    addDirectedEdge(g, i++, 2, 8, 1);
    addDirectedEdge(g, i++, 2, 9, 2);
    addDirectedEdge(g, i++, 3, 6, 11);
    addDirectedEdge(g, i++, 3, 7, 2);
    addDirectedEdge(g, i++, 4, 3, 2);
    addDirectedEdge(g, i++, 4, 5, 1);
    addDirectedEdge(g, i++, 5, 3, 5);
    addDirectedEdge(g, i++, 5, 6, 7);
    addDirectedEdge(g, i++, 6, 7, 1);
    addDirectedEdge(g, i++, 6, 8, 4);
    addDirectedEdge(g, i++, 7, 8, 6);
    addDirectedEdge(g, i++, 9, 8, 0);

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
      while (root != id[root])
        root = id[root];
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



