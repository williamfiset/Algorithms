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
import java.util.Optional;
import java.util.OptionalLong;

public class Boruvkas {

  static class Edge {
    int u, v, cost;

    public Edge(int u, int v, int cost) {
      this.u = u;
      this.v = v;
      this.cost = cost;
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
    this.mst = new ArrayList<>();
  }

  /**
   * Returns the edges in the MST, or empty if the graph is disconnected.
   */
  public Optional<List<Edge>> getMst() {
    solve();
    return mstExists ? Optional.of(mst) : Optional.empty();
  }

  /**
   * Returns the total cost of the MST, or empty if the graph is disconnected.
   */
  public OptionalLong getMstCost() {
    solve();
    return mstExists ? OptionalLong.of(minCostSum) : OptionalLong.empty();
  }

  private void solve() {
    if (solved) {
      return;
    }

    UnionFind uf = new UnionFind(n);

    while (uf.components > 1) {
      Edge[] cheapest = new Edge[n];

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
      int prevComponents = uf.components;
      for (Edge e : cheapest) {
        if (e != null && uf.find(e.u) != uf.find(e.v)) {
          uf.union(e.u, e.v);
          mst.add(e);
          minCostSum += e.cost;
        }
      }

      if (uf.components == prevComponents) {
        break;
      }
    }

    mstExists = (mst.size() == n - 1);
    solved = true;
  }

  // ==================== Main ====================

  //
  //                  1                 7                 2
  //      0 ---------------  1 ---------------  2 ---------------  3
  //      |                  |                  |                  |
  //      |                  |                  |                  |
  //    4 |                3 |                5 |                6 |
  //      |                  |                  |                  |
  //      |                  |                  |                  |
  //      4 ---------------  5 ---------------  6 ---------------  7
  //                  8                 2                 9
  //
  //  MST cost: 23
  //
  public static void main(String[] args) {
    Edge[] g = {
      new Edge(0, 1, 1),
      new Edge(1, 2, 7),
      new Edge(2, 3, 2),
      new Edge(0, 4, 4),
      new Edge(1, 5, 3),
      new Edge(2, 6, 5),
      new Edge(3, 7, 6),
      new Edge(4, 5, 8),
      new Edge(5, 6, 2),
      new Edge(6, 7, 9),
    };

    Boruvkas solver = new Boruvkas(8, g);

    OptionalLong cost = solver.getMstCost();
    if (cost.isPresent()) {
      System.out.println("MST cost: " + cost.getAsLong()); // 23
      for (Edge e : solver.getMst().get()) {
        System.out.printf("Edge %d-%d, cost: %d%n", e.u, e.v, e.cost);
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
