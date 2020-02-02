package com.williamfiset.algorithms.graphtheory.networkflow;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.graphtheory.networkflow.NetworkFlowSolverBase.Edge;
import java.util.*;
import org.junit.*;

public class MaxFlowTests {

  List<NetworkFlowSolverBase> solvers;

  @Before
  public void setUp() {
    solvers = new ArrayList<>();
  }

  void createAllSolvers(int n, int s, int t) {
    solvers.add(new CapacityScalingSolverAdjacencyList(n, s, t));
    solvers.add(new Dinics(n, s, t));
    solvers.add(new EdmondsKarpAdjacencyList(n, s, t));
    solvers.add(new FordFulkersonDfsSolverAdjacencyList(n, s, t));
    solvers.add(new MinCostMaxFlowWithBellmanFord(n, s, t));
    solvers.add(new MinCostMaxFlowJohnsons(n, s, t));
  }

  void addEdge(int f, int t, int c) {
    for (NetworkFlowSolverBase solver : solvers) {
      solver.addEdge(f, t, c);
    }
  }

  void assertFlow(long flow) {
    for (NetworkFlowSolverBase solver : solvers) {
      assertThat(solver.getMaxFlow()).isEqualTo(flow);
    }
  }

  @Test
  public void lineGraphTest() {
    int n = 4, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    addEdge(s, 0, 5);
    addEdge(0, 1, 3);
    addEdge(1, t, 7);

    assertFlow(3);
  }

  @Test
  public void testDisconnectedGraph() {
    int n = 4, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    // There's no edge connecting 0 and 1
    addEdge(s, 0, 9);
    addEdge(1, t, 9);

    assertFlow(0);
  }

  // Testing graph from:
  // http://crypto.cs.mcgill.ca/~crepeau/COMP251/KeyNoteSlides/07demo-maxflowCS-C.pdf
  @Test
  public void testSmallFlowGraph() {
    int n = 6, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    // Source edges
    addEdge(s, 0, 10);
    addEdge(s, 1, 10);

    // Sink edges
    addEdge(2, t, 10);
    addEdge(3, t, 10);

    // Middle edges
    addEdge(0, 1, 2);
    addEdge(0, 2, 4);
    addEdge(0, 3, 8);
    addEdge(1, 3, 9);
    addEdge(3, 2, 6);

    assertFlow(19);
  }

  @Test
  public void classicNetwork() {
    int n = 4, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    final int k = 10000;
    addEdge(s, 0, k);
    addEdge(s, 1, k);
    addEdge(0, t, k);
    addEdge(1, t, k);
    addEdge(0, 1, 1);

    assertFlow(2 * k);
  }

  @Test
  public void evilNetwork1() {
    final int k = 250, t = 2 * k; // source  = 0
    createAllSolvers(2 * k + 1, 0, t);

    for (int i = 0; i < k - 1; i++) addEdge(i, i + 1, k);
    for (int i = 0; i < k; i++) {
      addEdge(k - 1, k + i, 1);
      addEdge(k + i, t, 1);
    }

    assertFlow(k);
  }

  @Test
  public void testMediumSizeFlowGraph() {
    int n = 12, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    // Source edges
    addEdge(s, 0, 5);
    addEdge(s, 1, 20);
    addEdge(s, 2, 10);
    addEdge(0, 1, 3);
    addEdge(0, 5, 4);
    addEdge(1, 4, 14);
    addEdge(1, 5, 14);
    addEdge(2, 1, 5);
    addEdge(2, 3, 4);
    addEdge(3, 4, 3);
    addEdge(3, 9, 11);
    addEdge(4, 6, 4);
    addEdge(4, 8, 22);
    addEdge(5, 6, 8);
    addEdge(5, 7, 3);
    addEdge(6, 7, 12);
    addEdge(7, 8, 9);
    addEdge(7, t, 7);
    addEdge(8, 9, 11);
    addEdge(8, t, 15);
    addEdge(9, t, 60);
    assertFlow(29);
  }

  @Test
  public void testFlowInEqualsFlowOut() {
    int n = 12, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    // Source edges
    addEdge(s, 0, 5);
    addEdge(s, 1, 20);
    addEdge(s, 2, 10);
    addEdge(0, 1, 3);
    addEdge(0, 5, 4);
    addEdge(1, 4, 14);
    addEdge(1, 5, 14);
    addEdge(2, 1, 5);
    addEdge(2, 3, 4);
    addEdge(3, 4, 3);
    addEdge(3, 9, 11);
    addEdge(4, 6, 4);
    addEdge(4, 8, 22);
    addEdge(5, 6, 8);
    addEdge(5, 7, 3);
    addEdge(6, 7, 12);
    addEdge(7, 8, 9);
    addEdge(7, t, 7);
    addEdge(8, 9, 11);
    addEdge(8, t, 15);
    addEdge(9, t, 60);

    for (NetworkFlowSolverBase solver : solvers) {
      List<Edge>[] g = solver.getGraph();
      int[] inFlows = new int[n];
      int[] outFlows = new int[n];
      for (int i = 0; i < n; i++) {
        List<Edge> edges = g[i];
        for (Edge e : edges) {
          inFlows[e.from] += e.flow;
          outFlows[e.to] += e.flow;
        }
      }

      for (int i = 0; i < n; i++) {
        if (i == s || i == t) continue;
        assertThat(inFlows[i]).isEqualTo(outFlows[i]);
      }
    }
  }
}
