/*
Performs density analysis to figure out whether an adjacency list or an
adjacency matrix is better for prims MST algorithm.

Results seem to indicate that the adjacency matrix is better starting at
around ~33% edge percentage density:

Percentage full: ~0%, Edges included: 0
List:   1168811 nanos
Matrix: 300204 nanos

Percentage full: ~5%, Edges included: 1249282
List:   78032794 nanos
Matrix: 160413885 nanos

Percentage full: ~10%, Edges included: 2499128
List:   53444885 nanos
Matrix: 136684636 nanos

Percentage full: ~15%, Edges included: 3747556
List:   84818677 nanos
Matrix: 154946744 nanos

Percentage full: ~20%, Edges included: 4996636
List:   105822314 nanos
Matrix: 167086118 nanos

Percentage full: ~25%, Edges included: 6246068
List:   117237558 nanos
Matrix: 190984980 nanos

Percentage full: ~30%, Edges included: 7497476
List:   249309754 nanos
Matrix: 233969389 nanos

Percentage full: ~35%, Edges included: 8748710
List:   265593928 nanos
Matrix: 235897178 nanos

Percentage full: ~40%, Edges included: 10000808
List:   317905981 nanos
Matrix: 255262713 nanos

Percentage full: ~45%, Edges included: 11245712
List:   428115402 nanos
Matrix: 244939994 nanos

Percentage full: ~50%, Edges included: 12495078
List:   485647021 nanos
Matrix: 241433180 nanos

Percentage full: ~55%, Edges included: 13744132
List:   523930222 nanos
Matrix: 240345667 nanos

Percentage full: ~60%, Edges included: 14991078
List:   565671594 nanos
Matrix: 250618728 nanos

Percentage full: ~65%, Edges included: 16249278
List:   635804318 nanos
Matrix: 247628418 nanos

Percentage full: ~70%, Edges included: 17492252
List:   448590410 nanos
Matrix: 218092040 nanos

Percentage full: ~75%, Edges included: 18748276
List:   365672497 nanos
Matrix: 209152347 nanos

Percentage full: ~80%, Edges included: 19997560
List:   389878221 nanos
Matrix: 197766511 nanos

Percentage full: ~85%, Edges included: 21243518
List:   360389630 nanos
Matrix: 181542371 nanos

Percentage full: ~90%, Edges included: 22496480
List:   486827671 nanos
Matrix: 182686235 nanos

Percentage full: ~95%, Edges included: 23747794
List:   423884430 nanos
Matrix: 159974003 nanos

Percentage full: ~100%, Edges included: 24995000
List:   436565071 nanos
Matrix: 154691124 nanos

*/

package com.williamfiset.algorithms.graphtheory.analysis;

import static java.lang.Math.*;

import com.williamfiset.algorithms.datastructures.priorityqueue.MinIndexedDHeap;
import com.williamfiset.algorithms.graphtheory.CostComparingEdge;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class PrimsGraphRepresentationAnaylsis {

  private static class PrimsAdjList {

    // Inputs
    private final int n;
    private final List<List<CostComparingEdge>> graph;

    // Internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<CostComparingEdge> ipq;

    // Outputs
    private long minCostSum;
    private CostComparingEdge[] mstEdges;

    public PrimsAdjList(List<List<CostComparingEdge>> graph) {
      if (graph == null || graph.isEmpty()) throw new IllegalArgumentException();
      this.n = graph.size();
      this.graph = graph;
    }

    // Returns the edges used in finding the minimum spanning tree,
    // or returns null if no MST exists.
    public CostComparingEdge[] getMst() {
      solve();
      return mstExists ? mstEdges : null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;

      // edges will never be null if the createEmptyGraph method was used to build the graph.
      List<CostComparingEdge> edges = graph.get(currentNodeIndex);

      for (CostComparingEdge edge : edges) {
        int destNodeIndex = edge.getTo();

        // Skip edges pointing to already visited nodes.
        if (visited[destNodeIndex]) continue;

        if (ipq.contains(destNodeIndex)) {
          // Try and improve the cheapest edge at destNodeIndex with the current edge in the IPQ.
          ipq.decrease(destNodeIndex, edge);
        } else {
          // Insert edge for the first time.
          ipq.insert(destNodeIndex, edge);
        }
      }
    }

    // Computes the minimum spanning tree and minimum spanning tree cost.
    private void solve() {
      if (solved) return;
      solved = true;

      int m = n - 1, edgeCount = 0;
      visited = new boolean[n];
      mstEdges = new CostComparingEdge[m];

      // The degree of the d-ary heap supporting the IPQ can greatly impact performance, especially
      // on dense graphs. The base 2 logarithm of n is a decent value based on my quick experiments
      // (even better than E/V in many cases).
      int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);

      // Add initial set of edges to the priority queue starting at node 0.
      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex(); // equivalently: edge.to
        CostComparingEdge edge = ipq.pollMinValue();

        mstEdges[edgeCount++] = edge;
        minCostSum += edge.getCost();

        relaxEdgesAtNode(destNodeIndex);
      }

      // Verify MST spans entire graph.
      mstExists = (edgeCount == m);
    }

    /* Graph construction helpers. */

    // Creates an empty adjacency list graph with n nodes.
    static List<List<CostComparingEdge>> createEmptyGraph(int n) {
      List<List<CostComparingEdge>> g = new ArrayList<>();
      for (int i = 0; i < n; i++) g.add(new ArrayList<>());
      return g;
    }

    static void addDirectedEdge(List<List<CostComparingEdge>> g, int from, int to, int cost) {
      g.get(from).add(new CostComparingEdge(from, to, cost));
    }

    static void addUndirectedEdge(List<List<CostComparingEdge>> g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }

  private static class PrimsAdjMatrix {

    // Inputs
    private final int n;
    private final Integer[][] graph;

    // Internal
    private boolean solved;
    private boolean mstExists;
    private boolean[] visited;
    private MinIndexedDHeap<Integer> ipq;

    // Outputs
    private long minCostSum;
    private CostComparingEdge[] mstEdges;

    public PrimsAdjMatrix(Integer[][] graph) {
      if (graph == null || graph.length == 0 || graph[0].length != graph.length)
        throw new IllegalArgumentException();
      this.n = graph.length;
      this.graph = graph;
    }

    // Returns the edges used in finding the minimum spanning tree,
    // or returns null if no MST exists.
    public CostComparingEdge[] getMst() {
      // Unimplemented.
      return null;
    }

    public Long getMstCost() {
      solve();
      return mstExists ? minCostSum : null;
    }

    private void relaxEdgesAtNode(int currentNodeIndex) {
      visited[currentNodeIndex] = true;

      for (int to = 0; to < n; to++) {
        Integer cost = graph[currentNodeIndex][to];
        // Edge doesn't exist.
        if (cost == null) continue;

        // Skip edges pointing to already visited nodes.
        if (visited[to]) continue;

        if (ipq.contains(to)) {
          // Try and improve the cheapest edge at to with the current edge in the IPQ.
          ipq.decrease(to, cost);
        } else {
          // Insert edge for the first time.
          ipq.insert(to, cost);
        }
      }
    }

    // Computes the minimum spanning tree and minimum spanning tree cost.
    private void solve() {
      if (solved) return;
      solved = true;

      int m = n - 1, edgeCount = 0;
      visited = new boolean[n];

      // The degree of the d-ary heap supporting the IPQ can greatly impact performance, especially
      // on dense graphs. The base 2 logarithm of n is a decent value based on my quick experiments
      // (even better than E/V in many cases).
      int degree = (int) Math.ceil(Math.log(n) / Math.log(2));
      ipq = new MinIndexedDHeap<>(max(2, degree), n);

      // Add initial set of edges to the priority queue starting at node 0.
      relaxEdgesAtNode(0);

      while (!ipq.isEmpty() && edgeCount != m) {
        int destNodeIndex = ipq.peekMinKeyIndex();
        int edgeCost = ipq.pollMinValue();

        minCostSum += edgeCost;
        edgeCount++;

        relaxEdgesAtNode(destNodeIndex);
      }

      // Verify MST spans entire graph.
      mstExists = (edgeCount == m);
    }

    /* Graph construction helpers. */

    // Creates an empty adjacency matrix graph with n nodes.
    static Integer[][] createEmptyGraph(int n) {
      return new Integer[n][n];
    }

    static void addDirectedEdge(Integer[][] g, int from, int to, int cost) {
      g[from][to] = cost;
    }

    static void addUndirectedEdge(Integer[][] g, int from, int to, int cost) {
      addDirectedEdge(g, from, to, cost);
      addDirectedEdge(g, to, from, cost);
    }
  }

  /* Example usage. */

  public static void main(String[] args) throws InterruptedException {
    densityTest();
  }

  static Random random = new Random();

  private static void densityTest() throws InterruptedException {
    String rows = "", header = "edge density percentage, adj list, adj matrix\n";
    for (int percentage = 5; percentage <= 100; percentage += 5) {

      // Calling GC seems to give more consistent results?
      System.gc();
      TimeUnit.SECONDS.sleep(2);

      int n = 5000;
      List<List<CostComparingEdge>> g1 = PrimsAdjList.createEmptyGraph(n);
      Integer[][] g2 = PrimsAdjMatrix.createEmptyGraph(n);

      int numEdgesIncluded = 0;
      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          int r = Math.abs(random.nextInt()) % 100;
          if (r >= percentage) continue;
          PrimsAdjList.addUndirectedEdge(g1, i, j, r);
          PrimsAdjMatrix.addUndirectedEdge(g2, i, j, r);
          numEdgesIncluded += 2;
        }
      }

      PrimsAdjList adjListSolver = new PrimsAdjList(g1);
      PrimsAdjMatrix matrixSolver = new PrimsAdjMatrix(g2);

      System.out.println(
          "\nPercentage full: ~" + percentage + "%, Edges included: " + numEdgesIncluded);

      Instant start = Instant.now();
      Long listCost = adjListSolver.getMstCost();
      Instant end = Instant.now();
      long listTimeMs = Duration.between(start, end).toMillis();
      System.out.println("List:   " + listTimeMs + " millis");

      start = Instant.now();
      Long matrixCost = matrixSolver.getMstCost();
      end = Instant.now();
      long matrixTimeMs = Duration.between(start, end).toMillis();
      System.out.println("Matrix: " + matrixTimeMs + " millis");

      if (listCost != null && listCost.longValue() != matrixCost.longValue()) {
        System.out.println("Oh dear. " + listCost + " != " + matrixCost);
      }

      rows += String.format("%d%%,%d,%d\n", percentage, listTimeMs, matrixTimeMs);
    }
    System.out.println("CSV printout:\n\n" + header + rows);
  }
}
