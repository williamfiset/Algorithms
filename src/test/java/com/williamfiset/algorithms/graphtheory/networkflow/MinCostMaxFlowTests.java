package com.williamfiset.algorithms.graphtheory.networkflow;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.jupiter.api.*;

public class MinCostMaxFlowTests {

  List<NetworkFlowSolverBase> solvers;

  @BeforeEach
  public void setUp() {
    solvers = new ArrayList<>();
  }

  void createAllSolvers(int n, int s, int t) {
    // TODO(issue/67): Fix Bellman Ford mincost maxflow
    // solvers.add(new MinCostMaxFlowWithBellmanFord(n, s, t));
    solvers.add(new MinCostMaxFlowJohnsons(n, s, t));
  }

  void addEdge(int f, int t, int cap, int cost) {
    for (NetworkFlowSolverBase solver : solvers) {
      solver.addEdge(f, t, cap, cost);
    }
  }

  void assertFlowAndCost(long flow, long cost) {
    for (NetworkFlowSolverBase solver : solvers) {
      assertThat(solver.getMaxFlow()).isEqualTo(flow);
      assertThat(solver.getMinCost()).isEqualTo(cost);
    }
  }

  @Test
  public void testNegativeCycle1() {
    int n = 5, s = n - 1, t = n - 2;
    createAllSolvers(n, s, t);

    addEdge(s, 0, 100, 0);
    addEdge(1, t, 100, 0);

    // Triangle cycle
    addEdge(0, 1, 10, -1);
    addEdge(1, 2, 10, -1);
    addEdge(2, 0, 10, -1);

    assertFlowAndCost(10, -10);
  }
}
