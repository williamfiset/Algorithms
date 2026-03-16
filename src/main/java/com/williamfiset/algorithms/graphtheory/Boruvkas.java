/**
 * Boruvka's Minimum Spanning Tree Algorithm — Edge List
 *
 * <p>Finds the MST of a weighted undirected graph by repeatedly selecting the
 * cheapest outgoing edge from each connected component and merging components.
 *
 * <p>Algorithm:
 * <ol>
 *   <li>Start with each node as its own component (using Union-Find).</li>
 *   <li>For each component, find the minimum-weight edge crossing to another component.</li>
 *   <li>Add all such cheapest edges to the MST and merge the components.</li>
 *   <li>Repeat until only one component remains, or no more merges are possible.</li>
 * </ol>
 *
 * <p>If the graph is disconnected, no MST exists and the solver returns null.
 *
 * <p>Time:  O(E log V)
 * <p>Space: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

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
      int cmp = Integer.compare(cost, other.cost);
      if (cmp != 0) {
        return cmp;
      }
      cmp = Integer.compare(u, other.u);
      if (cmp != 0) {
        return cmp;
      }
      return Integer.compare(v, other.v);
    }
  }

  private final int n;
  private final Edge[] graph;
  private boolean solved;
  private boolean mstExists;
  private long minCostSum;
  private List<Edge> mst;

  public Boruvkas(int n, Edge[] graph) {
    if (graph == null) {
      throw new IllegalArgumentException();
    }
    this.graph = graph;
    this.n = n;
  }

  /**
   * Returns the edges in the MST, or null if the graph is disconnected.
   */
  public List<Edge> getMst() {
    solve();
    return mstExists ? mst : null;
  }

  /**
   * Returns the total cost of the MST, or null if the graph is disconnected.
   */
  public Long getMstCost() {
    solve();
    return mstExists ? minCostSum : null;
  }

  private void solve() {
    if (solved) {
      return;
    }

    mst = new ArrayList<>();
    UnionFind uf = new UnionFind(n);

    while (uf.components > 1) {
      Edge[] cheapest = new Edge[n];
      boolean merged = false;

      // For each edge, track the cheapest crossing edge for each component.
      for (Edge e : graph) {
        int root1 = uf.find(e.u);
        int root2 = uf.find(e.v);
        if (root1 == root2) {
          continue;
        }
        if (cheapest[root1] == null || e.cost < cheapest[root1].cost) {
          cheapest[root1] = e;
        }
        if (cheapest[root2] == null || e.cost < cheapest[root2].cost) {
          cheapest[root2] = e;
        }
      }

      // Merge components using their cheapest crossing edges.
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
          merged = true;
        }
      }

      if (!merged) {
        break;
      }
    }

    mstExists = (mst.size() == n - 1);
    solved = true;
  }

  // ==================== Main ====================

  //
  //       5       4
  //   0 ----- 1 ----- 2
  //   |  \  2 |       |\ 2
  //  1|   3   |2      | 9
  //   |  / 2  |  1    |/
  //   4 ----- 3 --- 7 --- 8
  //    \  1  / 5   /    0 |
  //     5   /   \ 1      \|
  //      \ / 7   6 -- 4   9
  //       5       8
  //
  // MST cost: 14
  //
  public static void main(String[] args) {
    Edge[] g = {
      new Edge(0, 1, 5),
      new Edge(0, 3, 4),
      new Edge(0, 4, 1),
      new Edge(1, 2, 4),
      new Edge(1, 3, 2),
      new Edge(2, 7, 4),
      new Edge(2, 8, 1),
      new Edge(2, 9, 2),
      new Edge(3, 6, 11),
      new Edge(3, 7, 2),
      new Edge(4, 3, 2),
      new Edge(4, 5, 1),
      new Edge(5, 3, 5),
      new Edge(5, 6, 7),
      new Edge(6, 7, 1),
      new Edge(6, 8, 4),
      new Edge(7, 8, 6),
      new Edge(9, 8, 0),
    };

    Boruvkas solver = new Boruvkas(10, g);

    Long cost = solver.getMstCost();
    if (cost != null) {
      System.out.println("MST cost: " + cost); // 14
      for (Edge e : solver.getMst()) {
        System.out.println(e);
      }
    } else {
      System.out.println("No MST exists");
    }
  }

  // Union-Find with path compression and union by size.
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
      if (id[p] != p) {
        id[p] = find(id[p]);
      }
      return id[p];
    }

    public void union(int p, int q) {
      int root1 = find(p), root2 = find(q);
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
