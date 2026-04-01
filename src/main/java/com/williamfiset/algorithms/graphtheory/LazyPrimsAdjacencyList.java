/**
 * Lazy implementation of Prim's minimum spanning tree algorithm using a priority queue.
 *
 * <p>"Lazy" because stale edges (to already-visited nodes) remain in the priority queue and are
 * skipped when polled, rather than being eagerly removed or updated.
 *
 * <p>Time: O(E log(E))
 *
 * <p>Space: O(V + E)
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
      return Integer.compare(cost, other.cost);
    }
  }

  private final int n;
  private final List<List<Edge>> graph;

  private boolean solved;
  private boolean mstExists;
  private boolean[] visited;
  private PriorityQueue<Edge> pq;

  private long minCostSum;
  private Edge[] mstEdges;

  /**
   * Creates a Lazy Prim's MST solver for the given graph.
   *
   * @param graph adjacency list where each node maps to a list of weighted edges.
   * @throws IllegalArgumentException if the graph is null or empty.
   */
  public LazyPrimsAdjacencyList(List<List<Edge>> graph) {
    if (graph == null || graph.isEmpty())
      throw new IllegalArgumentException();
    this.n = graph.size();
    this.graph = graph;
  }

  /** Returns the MST edges, or null if no MST exists. */
  public Edge[] getMst() {
    solve();
    return mstExists ? mstEdges : null;
  }

  /** Returns the MST total cost, or null if no MST exists. */
  public Long getMstCost() {
    solve();
    return mstExists ? minCostSum : null;
  }

  private void addEdges(int node) {
    visited[node] = true;
    for (Edge e : graph.get(node))
      if (!visited[e.to])
        pq.offer(e);
  }

  private void solve() {
    if (solved)
      return;
    solved = true;

    int m = n - 1;
    int edgeCount = 0;
    pq = new PriorityQueue<>();
    visited = new boolean[n];
    mstEdges = new Edge[m];

    addEdges(0);

    while (!pq.isEmpty() && edgeCount != m) {
      Edge edge = pq.poll();
      if (visited[edge.to])
        continue;

      mstEdges[edgeCount++] = edge;
      minCostSum += edge.cost;
      addEdges(edge.to);
    }

    mstExists = (edgeCount == m);
  }

  /** Creates an adjacency list with n nodes. */
  static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++)
      g.add(new ArrayList<>());
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
    exampleConnectedGraph();
    System.out.println();
    exampleDisconnectedGraph();
  }

  // Example 1: Connected graph with 10 nodes. MST cost = 14.
  private static void exampleConnectedGraph() {
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
    printMst(solver);
  }

  // Example 2: Disconnected graph — no MST exists.
  private static void exampleDisconnectedGraph() {
    int n = 4;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 3);
    addUndirectedEdge(g, 2, 3, 5);
    // Nodes {0,1} and {2,3} are not connected.

    LazyPrimsAdjacencyList solver = new LazyPrimsAdjacencyList(g);
    printMst(solver);
  }

  private static void printMst(LazyPrimsAdjacencyList solver) {
    Long cost = solver.getMstCost();
    if (cost == null) {
      System.out.println("No MST exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
        System.out.printf("  %d -> %d (cost %d)\n", e.from, e.to, e.cost);
    }
  }
}
