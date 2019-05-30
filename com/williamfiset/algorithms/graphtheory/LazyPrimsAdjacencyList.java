/**
 * An implementation of the lazy version of Prim's algorithm which relies on using a traditional
 * priority queue to query the next best edge.
 *
 * <p>Time Complexity: O(ElogE)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class LazyPrimsAdjacencyList {

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

  // Inputs
  private final int n;
  private final List<List<Edge>> graph;

  // Internal
  private boolean solved;
  private boolean mstExists;
  private boolean[] visited;
  private PriorityQueue<Edge> pq;

  // Outputs
  private long minCostSum;
  private Edge[] mstEdges;

  public LazyPrimsAdjacencyList(List<List<Edge>> graph) {
    if (graph == null || graph.isEmpty()) throw new IllegalArgumentException();
    this.n = graph.size();
    this.graph = graph;
  }

  // Returns the edges used in finding the minimum spanning tree,
  // or returns null if no MST exists.
  public Edge[] getMst() {
    solve();
    return mstExists ? mstEdges : null;
  }

  public Long getMstCost() {
    solve();
    return mstExists ? minCostSum : null;
  }

  private void addEdges(int nodeIndex) {
    visited[nodeIndex] = true;

    // edges will never be null if the createEmptyGraph method was used to build the graph.
    List<Edge> edges = graph.get(nodeIndex);
    for (Edge e : edges)
      if (!visited[e.to]) {
        // System.out.printf("(%d, %d, %d)\n", e.from, e.to, e.cost);
        pq.offer(e);
      }
  }

  // Computes the minimum spanning tree and minimum spanning tree cost.
  private void solve() {
    if (solved) return;
    solved = true;

    int m = n - 1, edgeCount = 0;
    pq = new PriorityQueue<>();
    visited = new boolean[n];
    mstEdges = new Edge[m];

    // Add initial set of edges to the priority queue starting at node 0.
    addEdges(0);

    // Loop while the MST is not complete.
    while (!pq.isEmpty() && edgeCount != m) {
      Edge edge = pq.poll();
      int nodeIndex = edge.to;

      // Skip any edge pointing to an already visited node.
      if (visited[nodeIndex]) continue;

      mstEdges[edgeCount++] = edge;
      minCostSum += edge.cost;

      addEdges(nodeIndex);
    }

    // Check if MST spans entire graph.
    mstExists = (edgeCount == m);
  }

  /* Graph construction helpers. */

  static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) g.add(new ArrayList<>());
    return g;
  }

  static void addDirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    g.get(from).add(new Edge(from, to, cost));
  }

  static void addUndirectedEdge(List<List<Edge>> g, int from, int to, int cost) {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }

  /* Example usage. */

  public static void main(String[] args) {
    // example1();
    // firstGraphFromSlides();
    // squareGraphFromSlides();
    lazyPrimsDemoFromSlides();
  }

  private static void example1() {
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

    LazyPrimsAdjacencyList solver = new LazyPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst()) {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }

    // Output:
    // MST cost: 14
    // from: 0, to: 4, cost: 1
    // from: 4, to: 5, cost: 1
    // from: 4, to: 3, cost: 2
    // from: 3, to: 1, cost: 2
    // from: 3, to: 7, cost: 2
    // from: 7, to: 6, cost: 1
    // from: 6, to: 8, cost: 4
    // from: 8, to: 9, cost: 0
    // from: 8, to: 2, cost: 1
  }

  private static void firstGraphFromSlides() {
    int n = 7;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 9);
    addUndirectedEdge(g, 0, 2, 0);
    addUndirectedEdge(g, 0, 3, 5);
    addUndirectedEdge(g, 0, 5, 7);
    addUndirectedEdge(g, 1, 3, -2);
    addUndirectedEdge(g, 1, 4, 3);
    addUndirectedEdge(g, 1, 6, 4);
    addUndirectedEdge(g, 2, 5, 6);
    addUndirectedEdge(g, 3, 5, 2);
    addUndirectedEdge(g, 3, 6, 3);
    addUndirectedEdge(g, 4, 6, 6);
    addUndirectedEdge(g, 5, 6, 1);

    LazyPrimsAdjacencyList solver = new LazyPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst()) {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }

  private static void squareGraphFromSlides() {
    int n = 9;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 6);
    addUndirectedEdge(g, 0, 3, 3);
    addUndirectedEdge(g, 1, 2, 4);
    addUndirectedEdge(g, 1, 4, 2);
    addUndirectedEdge(g, 2, 5, 12);
    addUndirectedEdge(g, 3, 4, 1);
    addUndirectedEdge(g, 3, 6, 8);
    addUndirectedEdge(g, 4, 5, 7);
    addUndirectedEdge(g, 4, 7, 9);
    addUndirectedEdge(g, 5, 8, 10);
    addUndirectedEdge(g, 6, 7, 11);
    addUndirectedEdge(g, 7, 8, 5);

    LazyPrimsAdjacencyList solver = new LazyPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst()) {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }

  private static void lazyPrimsDemoFromSlides() {
    int n = 8;
    List<List<Edge>> g = createEmptyGraph(n);

    addDirectedEdge(g, 0, 1, 10);
    addDirectedEdge(g, 0, 2, 1);
    addDirectedEdge(g, 0, 3, 4);

    addDirectedEdge(g, 2, 1, 3);
    addDirectedEdge(g, 2, 5, 8);
    addDirectedEdge(g, 2, 3, 2);
    addDirectedEdge(g, 2, 0, 1);

    addDirectedEdge(g, 3, 2, 2);
    addDirectedEdge(g, 3, 5, 2);
    addDirectedEdge(g, 3, 6, 7);
    addDirectedEdge(g, 3, 0, 4);

    addDirectedEdge(g, 5, 2, 8);
    addDirectedEdge(g, 5, 4, 1);
    addDirectedEdge(g, 5, 7, 9);
    addDirectedEdge(g, 5, 6, 6);
    addDirectedEdge(g, 5, 3, 2);

    addDirectedEdge(g, 4, 1, 0);
    addDirectedEdge(g, 4, 5, 1);
    addDirectedEdge(g, 4, 7, 8);

    addDirectedEdge(g, 1, 0, 10);
    addDirectedEdge(g, 1, 2, 3);
    addDirectedEdge(g, 1, 4, 0);

    addDirectedEdge(g, 6, 3, 7);
    addDirectedEdge(g, 6, 5, 6);
    addDirectedEdge(g, 6, 7, 12);

    addDirectedEdge(g, 7, 4, 8);
    addDirectedEdge(g, 7, 5, 9);
    addDirectedEdge(g, 7, 6, 12);

    LazyPrimsAdjacencyList solver = new LazyPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst()) {
        System.out.println(String.format("from: %d, to: %d, cost: %d", e.from, e.to, e.cost));
      }
    }
  }
}
