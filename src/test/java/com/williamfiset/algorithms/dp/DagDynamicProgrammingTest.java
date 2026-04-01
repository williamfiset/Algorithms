package com.williamfiset.algorithms.dp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;

public class DagDynamicProgrammingTest {

  private Map<Integer, List<DagDynamicProgramming.Edge>> createGraph(int n) {
    Map<Integer, List<DagDynamicProgramming.Edge>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) {
      graph.put(i, new ArrayList<>());
    }
    return graph;
  }

  @Test
  public void testSimpleDAGWays() {

    int n = 5;
    Map<Integer, List<DagDynamicProgramming.Edge>> graph = createGraph(n);

    graph.get(0).add(new DagDynamicProgramming.Edge(0, 1, 1));
    graph.get(0).add(new DagDynamicProgramming.Edge(0, 2, 1));
    graph.get(1).add(new DagDynamicProgramming.Edge(1, 3, 1));
    graph.get(2).add(new DagDynamicProgramming.Edge(2, 3, 1));
    graph.get(3).add(new DagDynamicProgramming.Edge(3, 4, 1));

    long[] dp = DagDynamicProgramming.countWaysDAG(graph, 0, n);

    assertNotNull(dp);
    assertEquals(1, dp[0]);
    assertEquals(1, dp[1]);
    assertEquals(1, dp[2]);
    assertEquals(2, dp[3]); // two paths: 0->1->3 and 0->2->3
    assertEquals(2, dp[4]);
  }

  @Test
  public void testDisconnectedGraph() {

    int n = 4;
    Map<Integer, List<DagDynamicProgramming.Edge>> graph = createGraph(n);

    graph.get(0).add(new DagDynamicProgramming.Edge(0, 1, 1));

    long[] dp = DagDynamicProgramming.countWaysDAG(graph, 0, n);

    assertNotNull(dp);
    assertEquals(1, dp[0]);
    assertEquals(1, dp[1]);
    assertEquals(0, dp[2]); // unreachable
    assertEquals(0, dp[3]); // unreachable
  }

  @Test
  public void testCycleDetection() {

    int n = 3;
    Map<Integer, List<DagDynamicProgramming.Edge>> graph = createGraph(n);

    graph.get(0).add(new DagDynamicProgramming.Edge(0, 1, 1));
    graph.get(1).add(new DagDynamicProgramming.Edge(1, 2, 1));
    graph.get(2).add(new DagDynamicProgramming.Edge(2, 0, 1)); // cycle

    long[] dp = DagDynamicProgramming.countWaysDAG(graph, 0, n);

    assertNull(dp); // cycle detected
  }
}