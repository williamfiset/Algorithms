/**
 * WIP
 **/
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Boruvkas {

  static class Edge implements Comparable<Edge> {
    int from, to, cost;
    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
    @Override public int compareTo(Edge other) {
      int cmp = cost - other.cost;
      // Break ties by picking lexicographically smallest edge pair.
      if (cmp == 0) {
        cmp = from - other.from;
        if (cmp == 0) return to - other.to;
        return cmp;
      }
      return cmp;
    }
  }

  // Given a graph represented as an edge list this method finds
  // the Minimum Spanning Tree (MST) cost if there exists 
  // a MST, otherwise it returns null.
  static Long solve(List<List<Edge>> g, int n) {

    if (g == null) return null;

    long sum = 0L;
    UnionFind uf = new UnionFind(n);

    // Might cause inf loop if disjoint components
    while(uf.size(0) != n) {

      // Gotta track cheapest edge in a "component"

      // boolean foundEdge = false;
      // for (int i = 0; i < n; i++) {
      //   Edge me = null;
      //   for (Edge e : g.get(i)) {
      //     if (uf.connected(e.from, e.to)) continue; // avoid cycles
      //     if (me == null) me = e;
      //     else if (e.compareTo(me) < 0) me = e;
      //   }
      //   if (me != null) {
      //     uf.union(me.from, me.to);
      //     sum += me.cost;
      //     foundEdge = true;
      //   }
      // }
      // if (!foundEdge) break;

    }

    // Make sure we have a MST that includes all the nodes
    if (uf.size(0) != n) return null;

    return sum;

  }

  static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for(int i = 0; i < n; i++) g.add(new ArrayList<>());
    return g;
  }

  static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    g.get(from).add(new Edge(from, to, cost));
  }

  static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }

  public static void main(String[] args) {
    
    int n = 10;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 5);
    addUndirectedEdge(g, 1, 2, 4);
    addUndirectedEdge(g, 2, 9, 2);
    addUndirectedEdge(g, 0, 4, 1);
    addUndirectedEdge(g, 0, 3, 4);
    addUndirectedEdge(g, 1, 3, 2);
    addUndirectedEdge(g, 2, 7, 4);
    addUndirectedEdge(g, 2, 8, 1);
    addUndirectedEdge(g, 9, 8, 0);
    addUndirectedEdge(g, 4, 5, 1);
    addUndirectedEdge(g, 5, 6, 7);
    addUndirectedEdge(g, 6, 8, 4);
    addUndirectedEdge(g, 4, 3, 2);
    addUndirectedEdge(g, 5, 3, 5);
    addUndirectedEdge(g, 3, 6, 11);
    addUndirectedEdge(g, 6, 7, 1);
    addUndirectedEdge(g, 3, 7, 2);
    addUndirectedEdge(g, 7, 8, 6);

    Boruvkas solver = new Boruvkas();
    System.out.println(solver.solve(g, n));

  }

  // Union find data structure 
  private static class UnionFind {
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
    }
  }

}



