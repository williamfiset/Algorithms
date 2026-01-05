/**
 * An implementation of the eager version of Prim's algorithm which relies on using an indexed
 * priority queue data structure to query the next best edge.
 *
 * <p>Download the code: $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Change directory to the root of the Algorithms directory: $ cd Algorithms
 *
 * <p>Compile: $ javac -d src/main/java
 * src/main/java/com/williamfiset/algorithms/graphtheory/examples/EagerPrimsExample.java
 *
 * <p>Run: $ java -cp src/main/java
 * com/williamfiset/algorithms/graphtheory/examples/EagerPrimsExample
 *
 * <p>Time Complexity: O(ElogV)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.examples;

import static java.lang.Math.*;

import com.williamfiset.algorithms.datastructures.priorityqueue.MinIndexedDHeap;
import com.williamfiset.algorithms.graphtheory.CostComparingEdge;
import java.util.*;

public class EagerPrimsExample {

  /* Example from slides. */

  public static void main(String[] args) {
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

    MinimumSpanningTreeSolver solver = new MinimumSpanningTreeSolver(g);

    if (!solver.mstExists()) {
      System.out.println("No MST does not exists");
    } else {
      System.out.println("MST cost: " + solver.getMstCost());
      System.out.println("MST edges:");
      for (CostComparingEdge e : solver.getMst()) {
        System.out.println(String.format("  (%d, %d, %d)", e.getFrom(), e.getTo(), e.getCost()));
      }
    }
    // MST cost: 9
    // MST edges:
    //   (0, 2, 0)
    //   (0, 3, 5)
    //   (3, 1, -2)
    //   (3, 5, 2)
    //   (5, 6, 1)
    //   (1, 4, 3)
  }

  /* Graph construction helpers. */

  // Creates an empty adjacency list graph with n nodes.
  private static List<List<CostComparingEdge>> createEmptyGraph(int n) {
    List<List<CostComparingEdge>> g = new ArrayList<>();
    for (int i = 0; i < n; i++) g.add(new ArrayList<>());
    return g;
  }

  private static void addDirectedEdge(List<List<CostComparingEdge>> g, int from, int to, int cost) {
    g.get(from).add(new CostComparingEdge(from, to, cost));
  }

  private static void addUndirectedEdge(
      List<List<CostComparingEdge>> g, int from, int to, int cost) {
    addDirectedEdge(g, from, to, cost);
    addDirectedEdge(g, to, from, cost);
  }

  // Solves the MST problem using Prim's algorithm.
  private static class MinimumSpanningTreeSolver {

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

    public MinimumSpanningTreeSolver(List<List<CostComparingEdge>> graph) {
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

    public boolean mstExists() {
      solve();
      return mstExists;
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

      // Add initial set of edges to the indexed priority queue starting with node 0.
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
  }
}
