package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class ConnectedComponentsUnionFindTest {

  private static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  private static void addEdge(List<List<Integer>> graph, int u, int v) {
    graph.get(u).add(v);
    graph.get(v).add(u);
  }

  @Test
  public void testNullGraphThrows() {
    assertThrows(IllegalArgumentException.class, () -> new ConnectedComponentsUnionFind(null));
  }

  @Test
  public void testEmptyGraph() {
    List<List<Integer>> graph = createGraph(0);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(0);
  }

  @Test
  public void testSingleNode() {
    List<List<Integer>> graph = createGraph(1);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(1);
    assertThat(solver.componentSize(0)).isEqualTo(1);
  }

  @Test
  public void testTwoNodesConnected() {
    List<List<Integer>> graph = createGraph(2);
    addEdge(graph, 0, 1);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(1);
    assertThat(solver.componentId(0)).isEqualTo(solver.componentId(1));
    assertThat(solver.componentSize(0)).isEqualTo(2);
    assertThat(solver.componentSize(1)).isEqualTo(2);
  }

  @Test
  public void testTwoNodesDisconnected() {
    List<List<Integer>> graph = createGraph(2);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(2);
    assertThat(solver.componentId(0)).isNotEqualTo(solver.componentId(1));
    assertThat(solver.componentSize(0)).isEqualTo(1);
    assertThat(solver.componentSize(1)).isEqualTo(1);
  }

  @Test
  public void testAllIsolatedNodes() {
    int n = 5;
    List<List<Integer>> graph = createGraph(n);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(5);
    for (int i = 0; i < n; i++) {
      assertThat(solver.componentSize(i)).isEqualTo(1);
    }
  }

  @Test
  public void testFullyConnectedGraph() {
    int n = 4;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 3);
    addEdge(graph, 2, 3);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(1);
    for (int i = 0; i < n; i++) {
      assertThat(solver.componentSize(i)).isEqualTo(4);
    }
  }

  @Test
  public void testExampleFromMainMethod() {
    // {0,1,2}, {3,4,6,9}, {5}, {7,8}, {10}
    int n = 11;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 3, 6);
    addEdge(graph, 6, 9);
    addEdge(graph, 7, 8);

    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(5);

    // Nodes in the same component share the same id.
    assertThat(solver.componentId(0)).isEqualTo(solver.componentId(1));
    assertThat(solver.componentId(0)).isEqualTo(solver.componentId(2));
    assertThat(solver.componentId(3)).isEqualTo(solver.componentId(4));
    assertThat(solver.componentId(3)).isEqualTo(solver.componentId(6));
    assertThat(solver.componentId(3)).isEqualTo(solver.componentId(9));
    assertThat(solver.componentId(7)).isEqualTo(solver.componentId(8));

    // Nodes in different components have different ids.
    Set<Integer> roots = new HashSet<>();
    roots.add(solver.componentId(0));
    roots.add(solver.componentId(3));
    roots.add(solver.componentId(5));
    roots.add(solver.componentId(7));
    roots.add(solver.componentId(10));
    assertThat(roots).hasSize(5);

    // Component sizes.
    assertThat(solver.componentSize(0)).isEqualTo(3);
    assertThat(solver.componentSize(3)).isEqualTo(4);
    assertThat(solver.componentSize(5)).isEqualTo(1);
    assertThat(solver.componentSize(7)).isEqualTo(2);
    assertThat(solver.componentSize(10)).isEqualTo(1);
  }

  @Test
  public void testLinearChain() {
    // 0 - 1 - 2 - 3 - 4
    int n = 5;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(1);
    for (int i = 0; i < n; i++) {
      assertThat(solver.componentSize(i)).isEqualTo(5);
    }
  }

  @Test
  public void testSelfLoop() {
    List<List<Integer>> graph = createGraph(2);
    addEdge(graph, 0, 0);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(2);
    assertThat(solver.componentSize(0)).isEqualTo(1);
    assertThat(solver.componentSize(1)).isEqualTo(1);
  }

  @Test
  public void testIdempotent() {
    List<List<Integer>> graph = createGraph(3);
    addEdge(graph, 0, 1);
    ConnectedComponentsUnionFind solver = new ConnectedComponentsUnionFind(graph);
    assertThat(solver.countComponents()).isEqualTo(2);
    assertThat(solver.countComponents()).isEqualTo(2);
    assertThat(solver.componentId(0)).isEqualTo(solver.componentId(0));
    assertThat(solver.componentSize(0)).isEqualTo(solver.componentSize(0));
  }
}
