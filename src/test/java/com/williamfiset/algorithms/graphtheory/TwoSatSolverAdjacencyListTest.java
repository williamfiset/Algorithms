package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.jupiter.api.*;

public class TwoSatSolverAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(2 * n);
    for (int i = 0; i < 2 * n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  @Test
  public void testSimpleSatisfiable() {
    int n = 1;
    List<List<Integer>> g = createGraph(n);

    // Full clause: (p or ~p)
    TwoSatSolverAdjacencyList.addOrClause(g, 0, 0 ^ 1);

    TwoSatSolverAdjacencyList solver = new TwoSatSolverAdjacencyList(g);
    assertThat(solver.isSatisfiable()).isTrue();
  }

  @Test
  public void testSimpleImpossible() {
    int n = 1;
    List<List<Integer>> g = createGraph(n);

    // Full clause: (p or p) and (~p or ~p)
    TwoSatSolverAdjacencyList.addOrClause(g, 0, 0); // (p or p)
    TwoSatSolverAdjacencyList.addOrClause(g, 0 ^ 1, 0 ^ 1); // (~p or ~p)

    TwoSatSolverAdjacencyList solver = new TwoSatSolverAdjacencyList(g);
    assertThat(solver.isSatisfiable()).isFalse();
  }

  @Test
  public void testImpossibleFourNodeCycle() {
    int n = 2;
    List<List<Integer>> g = createGraph(n);

    // Full clause: (p0 or p1) and (p0 or ~p1) and (~p0 or p1) and (~p0 or ~p1)
    TwoSatSolverAdjacencyList.addOrClause(g, 2 * 0, 2 * 1); // (p0 or p1)
    TwoSatSolverAdjacencyList.addOrClause(g, 2 * 0, 2 * 1 ^ 1); // (p0 or ~p1)
    TwoSatSolverAdjacencyList.addOrClause(g, 2 * 0 ^ 1, 2 * 1); // (~p0 or p1)
    TwoSatSolverAdjacencyList.addOrClause(g, 2 * 0 ^ 1, 2 * 1 ^ 1); // (~p0 or ~p1)
    // System.out.println(g);

    TwoSatSolverAdjacencyList solver = new TwoSatSolverAdjacencyList(g);
    assertThat(solver.isSatisfiable()).isFalse();
  }
}
