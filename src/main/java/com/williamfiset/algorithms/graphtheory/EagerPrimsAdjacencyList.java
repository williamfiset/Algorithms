/**
 * Eager implementation of Prim's minimum spanning tree algorithm using an indexed priority queue
 * (IPQ).
 *
 * <p>"Eager" because when a better edge to a frontier node is found, the IPQ entry is updated
 * in-place (via {@code decrease}), so stale edges never accumulate — unlike the lazy variant which
 * leaves them in the queue.
 *
 * <p>Time: O(E log(V))
 *
 * <p>Space: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class EagerPrimsAdjacencyList {

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
  private MinIndexedDHeap<Edge> ipq;

  private long minCostSum;
  private Edge[] mstEdges;

  /**
   * Creates an Eager Prim's MST solver for the given graph.
   *
   * @param graph adjacency list where each node maps to a list of weighted edges.
   * @throws IllegalArgumentException if the graph is null or empty.
   */
  public EagerPrimsAdjacencyList(List<List<Edge>> graph) {
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

  private void relaxEdgesAtNode(int node) {
    visited[node] = true;
    for (Edge edge : graph.get(node)) {
      if (visited[edge.to])
        continue;

      if (ipq.contains(edge.to))
        ipq.decrease(edge.to, edge);
      else
        ipq.insert(edge.to, edge);
    }
  }

  private void solve() {
    if (solved)
      return;
    solved = true;

    int m = n - 1;
    int edgeCount = 0;
    visited = new boolean[n];
    mstEdges = new Edge[m];

    // The degree of the d-ary heap can greatly impact performance, especially on dense graphs.
    // The base-2 logarithm of n is a good heuristic.
    int degree = Math.max(2, (int) Math.ceil(Math.log(n) / Math.log(2)));
    ipq = new MinIndexedDHeap<>(degree, n);

    relaxEdgesAtNode(0);

    while (!ipq.isEmpty() && edgeCount != m) {
      int destNode = ipq.peekMinKeyIndex();
      Edge edge = ipq.pollMinValue();

      mstEdges[edgeCount++] = edge;
      minCostSum += edge.cost;
      relaxEdgesAtNode(destNode);
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
    exampleGraphWithNegativeEdges();
    System.out.println();
    exampleSquareGraph();
    System.out.println();
    exampleDisjointFromStart();
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

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    printMst(solver);
  }

  // Example 2: Graph with 7 nodes and a negative edge weight. MST cost = 9.
  private static void exampleGraphWithNegativeEdges() {
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

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    printMst(solver);
  }

  // Example 3: Square-shaped graph with 9 nodes. MST cost = 39.
  private static void exampleSquareGraph() {
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

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    printMst(solver);
  }

  // Example 4: Node 0 is disconnected from the rest — no MST exists.
  private static void exampleDisjointFromStart() {
    int n = 4;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 1, 2, 1);
    addUndirectedEdge(g, 2, 3, 1);
    addUndirectedEdge(g, 3, 1, 1);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    printMst(solver);
  }

  // Example 5: Two disconnected components — no MST exists.
  private static void exampleDisconnectedGraph() {
    int n = 6;
    List<List<Edge>> g = createEmptyGraph(n);

    addUndirectedEdge(g, 0, 1, 1);
    addUndirectedEdge(g, 1, 2, 1);
    addUndirectedEdge(g, 2, 0, 1);

    addUndirectedEdge(g, 3, 4, 1);
    addUndirectedEdge(g, 4, 5, 1);
    addUndirectedEdge(g, 5, 3, 1);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    printMst(solver);
  }

  private static void printMst(EagerPrimsAdjacencyList solver) {
    Long cost = solver.getMstCost();
    if (cost == null) {
      System.out.println("No MST exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (Edge e : solver.getMst())
        System.out.printf("  %d -> %d (cost %d)\n", e.from, e.to, e.cost);
    }
  }

  /* Minimal indexed d-ary min-heap — only the operations needed by Prim's are kept. */

  private static class MinIndexedDHeap<T extends Comparable<T>> {

    // Current number of elements in the heap.
    private int sz;

    // Maximum number of elements in the heap.
    private final int N;

    // The degree of every node in the heap.
    private final int D;

    // Lookup arrays to track the child/parent indexes of each node.
    private final int[] child, parent;

    // The Position Map (pm) maps Key Indexes (ki) to where the position of that
    // key is represented in the priority queue in the domain [0, sz).
    public final int[] pm;

    // The Inverse Map (im) stores the indexes of the keys in the range
    // [0, sz) which make up the priority queue. It should be noted that
    // 'im' and 'pm' are inverses of each other, so: pm[im[i]] = im[pm[i]] = i
    public final int[] im;

    // The values associated with the keys. It is very important to note
    // that this array is indexed by the key indexes (aka 'ki').
    public final Object[] values;

    public MinIndexedDHeap(int degree, int maxSize) {
      D = Math.max(2, degree);
      N = Math.max(D + 1, maxSize);

      im = new int[N];
      pm = new int[N];
      child = new int[N];
      parent = new int[N];
      values = new Object[N];

      for (int i = 0; i < N; i++) {
        parent[i] = (i - 1) / D;
        child[i] = i * D + 1;
        pm[i] = im[i] = -1;
      }
    }

    public boolean isEmpty() {
      return sz == 0;
    }

    public boolean contains(int ki) {
      return pm[ki] != -1;
    }

    public int peekMinKeyIndex() {
      return im[0];
    }

    @SuppressWarnings("unchecked")
    public T pollMinValue() {
      T minVal = (T) values[im[0]];
      delete(im[0]);
      return minVal;
    }

    public void insert(int ki, T value) {
      pm[ki] = sz;
      im[sz] = ki;
      values[ki] = value;
      swim(sz++);
    }

    @SuppressWarnings("unchecked")
    public void decrease(int ki, T value) {
      if (((Comparable<? super T>) value).compareTo((T) values[ki]) < 0) {
        values[ki] = value;
        swim(pm[ki]);
      }
    }

    @SuppressWarnings("unchecked")
    public T delete(int ki) {
      int i = pm[ki];
      swap(i, --sz);
      sink(i);
      swim(i);
      T value = (T) values[ki];
      values[ki] = null;
      pm[ki] = -1;
      im[sz] = -1;
      return value;
    }

    private void sink(int i) {
      for (int j = minChild(i); j != -1; ) {
        swap(i, j);
        i = j;
        j = minChild(i);
      }
    }

    private void swim(int i) {
      while (less(i, parent[i])) {
        swap(i, parent[i]);
        i = parent[i];
      }
    }

    private int minChild(int i) {
      int index = -1;
      int from = child[i];
      int to = Math.min(sz, from + D);
      for (int j = from; j < to; j++) {
        if (less(j, i)) {
          index = j;
          i = j;
        }
      }
      return index;
    }

    private void swap(int i, int j) {
      pm[im[j]] = i;
      pm[im[i]] = j;
      int tmp = im[i];
      im[i] = im[j];
      im[j] = tmp;
    }

    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
      return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
    }
  }
}
