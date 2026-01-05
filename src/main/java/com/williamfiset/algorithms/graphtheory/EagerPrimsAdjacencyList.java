/**
 * An implementation of the eager version of Prim's algorithm which relies on using an indexed
 * priority queue data structure to query the next best edge.
 *
 * <p>Time Complexity: O(ElogV)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static java.lang.Math.*;

import com.williamfiset.algorithms.datastructures.priorityqueue.MinIndexedDHeap;
import java.util.*;

public class EagerPrimsAdjacencyList {

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

  public EagerPrimsAdjacencyList(List<List<CostComparingEdge>> graph) {
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

  /* Example usage. */

  public static void main(String[] args) {
    // example1();
    // firstGraphFromSlides();
    // squareGraphFromSlides();
    // disjointOnFirstNode();
    // disjointGraph();
    eagerPrimsExampleFromSlides();
    // lazyVsEagerAnalysis();
  }

  private static void example1() {
    int n = 10;
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

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
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
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
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

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
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
  }

  private static void squareGraphFromSlides() {
    int n = 9;
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

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
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (WeightedEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
  }

  private static void disjointOnFirstNode() {
    int n = 4;
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

    // Node edges connected to zero
    addUndirectedEdge(g, 1, 2, 1);
    addUndirectedEdge(g, 2, 3, 1);
    addUndirectedEdge(g, 3, 1, 1);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
  }

  private static void disjointGraph() {
    int n = 6;
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

    // Component 1
    addUndirectedEdge(g, 0, 1, 1);
    addUndirectedEdge(g, 1, 2, 1);
    addUndirectedEdge(g, 2, 0, 1);

    // Component 2
    addUndirectedEdge(g, 3, 4, 1);
    addUndirectedEdge(g, 4, 5, 1);
    addUndirectedEdge(g, 5, 3, 1);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
  }

  private static void eagerPrimsExampleFromSlides() {
    int n = 7;
    List<List<CostComparingEdge>> g = createEmptyGraph(n);

    addDirectedEdge(g, 0, 2, 0);
    addDirectedEdge(g, 0, 5, 7);
    addDirectedEdge(g, 0, 3, 5);
    addDirectedEdge(g, 0, 1, 9);

    addDirectedEdge(g, 2, 0, 0);
    addDirectedEdge(g, 2, 5, 6);

    addDirectedEdge(g, 3, 0, 5);
    addDirectedEdge(g, 3, 1, -2);
    addDirectedEdge(g, 3, 6, 3);
    addDirectedEdge(g, 3, 5, 2);

    addDirectedEdge(g, 1, 0, 9);
    addDirectedEdge(g, 1, 3, -2);
    addDirectedEdge(g, 1, 6, 4);
    addDirectedEdge(g, 1, 4, 3);

    addDirectedEdge(g, 5, 2, 6);
    addDirectedEdge(g, 5, 0, 7);
    addDirectedEdge(g, 5, 3, 2);
    addDirectedEdge(g, 5, 6, 1);

    addDirectedEdge(g, 6, 5, 1);
    addDirectedEdge(g, 6, 3, 3);
    addDirectedEdge(g, 6, 1, 4);
    addDirectedEdge(g, 6, 4, 6);

    addDirectedEdge(g, 4, 1, 3);
    addDirectedEdge(g, 4, 6, 6);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    Long cost = solver.getMstCost();

    if (cost == null) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + cost);
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(
            String.format("from: %d, to: %d, cost: %d", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
  }

  static Random random = new Random();

  private static void lazyVsEagerAnalysis() {
    int n = 5000;
    List<List<CostComparingEdge>> g1 = EagerPrimsAdjacencyList.createEmptyGraph(n);
    List<List<CostComparingEdge>> g2 = LazyPrimsAdjacencyList.createEmptyGraph(n);

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int r = random.nextInt() % 10;
        EagerPrimsAdjacencyList.addUndirectedEdge(g1, i, j, r);
        LazyPrimsAdjacencyList.addUndirectedEdge(g2, i, j, r);
      }
    }

    EagerPrimsAdjacencyList eagerSolver = new EagerPrimsAdjacencyList(g1);
    LazyPrimsAdjacencyList lazySolver = new LazyPrimsAdjacencyList(g2);

    long startTime = System.nanoTime();
    Long eagerCost = eagerSolver.getMstCost();
    long endTime = System.nanoTime();
    System.out.println("Eager: " + (endTime - startTime));

    startTime = System.nanoTime();
    Long lazyCost = lazySolver.getMstCost();
    endTime = System.nanoTime();
    System.out.println("Lazy:  " + (endTime - startTime));

    if (eagerCost.longValue() != lazyCost.longValue()) {
      System.out.println("Oh dear. " + eagerCost + " != " + lazyCost);
    }
  }
}
