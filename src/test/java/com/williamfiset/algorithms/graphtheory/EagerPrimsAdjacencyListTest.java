package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.graphtheory.EagerPrimsAdjacencyList.Edge;
import java.util.*;
import org.junit.jupiter.api.*;

public class EagerPrimsAdjacencyListTest {

  private static List<List<Edge>> createGraph(int n) {
    return EagerPrimsAdjacencyList.createEmptyGraph(n);
  }

  private static void addEdge(List<List<Edge>> g, int from, int to, int cost) {
    EagerPrimsAdjacencyList.addUndirectedEdge(g, from, to, cost);
  }

  @Test
  public void testNullGraphThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new EagerPrimsAdjacencyList(null));
  }

  @Test
  public void testEmptyGraphThrowsException() {
    assertThrows(IllegalArgumentException.class, () -> new EagerPrimsAdjacencyList(new ArrayList<>()));
  }

  @Test
  public void testSingleNode() {
    List<List<Edge>> g = createGraph(1);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(0L);
    assertThat(solver.getMst()).isEmpty();
  }

  @Test
  public void testTwoNodesConnected() {
    List<List<Edge>> g = createGraph(2);
    addEdge(g, 0, 1, 5);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(5L);
    assertThat(solver.getMst()).hasLength(1);
  }

  @Test
  public void testTwoNodesDisconnected() {
    List<List<Edge>> g = createGraph(2);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isNull();
    assertThat(solver.getMst()).isNull();
  }

  @Test
  public void testSimpleTriangle() {
    List<List<Edge>> g = createGraph(3);
    addEdge(g, 0, 1, 1);
    addEdge(g, 1, 2, 2);
    addEdge(g, 0, 2, 3);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(3L);
    assertThat(solver.getMst()).hasLength(2);
  }

  @Test
  public void testDisconnectedGraph() {
    List<List<Edge>> g = createGraph(4);
    addEdge(g, 0, 1, 1);
    addEdge(g, 2, 3, 2);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isNull();
    assertThat(solver.getMst()).isNull();
  }

  @Test
  public void testConnectedGraphMstCost14() {
    List<List<Edge>> g = createGraph(10);
    addEdge(g, 0, 1, 5);
    addEdge(g, 1, 2, 4);
    addEdge(g, 2, 9, 2);
    addEdge(g, 0, 4, 1);
    addEdge(g, 0, 3, 4);
    addEdge(g, 1, 3, 2);
    addEdge(g, 2, 7, 4);
    addEdge(g, 2, 8, 1);
    addEdge(g, 9, 8, 0);
    addEdge(g, 4, 5, 1);
    addEdge(g, 5, 6, 7);
    addEdge(g, 6, 8, 4);
    addEdge(g, 4, 3, 2);
    addEdge(g, 5, 3, 5);
    addEdge(g, 3, 6, 11);
    addEdge(g, 6, 7, 1);
    addEdge(g, 3, 7, 2);
    addEdge(g, 7, 8, 6);

    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(14L);
    assertThat(solver.getMst()).hasLength(9);
  }

  @Test
  public void testGraphWithNegativeWeightEdges() {
    List<List<Edge>> g = createGraph(7);
    addEdge(g, 0, 1, 9);
    addEdge(g, 0, 2, 0);
    addEdge(g, 0, 3, 5);
    addEdge(g, 0, 5, 7);
    addEdge(g, 1, 3, -2);
    addEdge(g, 1, 4, 3);
    addEdge(g, 1, 6, 4);
    addEdge(g, 2, 5, 6);
    addEdge(g, 3, 5, 2);
    addEdge(g, 3, 6, 3);
    addEdge(g, 4, 6, 6);
    addEdge(g, 5, 6, 1);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(9L);
    assertThat(solver.getMst()).hasLength(6);
  }

  @Test
  public void testGraphWithZeroWeightEdges() {
    List<List<Edge>> g = createGraph(4);
    addEdge(g, 0, 1, 0);
    addEdge(g, 1, 2, 0);
    addEdge(g, 2, 3, 0);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(0L);
    assertThat(solver.getMst()).hasLength(3);
  }

  @Test
  public void testCompleteGraphK4() {
    List<List<Edge>> g = createGraph(4);
    addEdge(g, 0, 1, 1);
    addEdge(g, 0, 2, 4);
    addEdge(g, 0, 3, 3);
    addEdge(g, 1, 2, 2);
    addEdge(g, 1, 3, 5);
    addEdge(g, 2, 3, 6);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(6L);
    assertThat(solver.getMst()).hasLength(3);
  }

  @Test
  public void testClassicGraph9Nodes() {
    List<List<Edge>> g = createGraph(9);
    addEdge(g, 0, 1, 4);
    addEdge(g, 0, 7, 8);
    addEdge(g, 1, 2, 8);
    addEdge(g, 1, 7, 11);
    addEdge(g, 2, 3, 7);
    addEdge(g, 2, 5, 4);
    addEdge(g, 2, 8, 2);
    addEdge(g, 3, 4, 9);
    addEdge(g, 3, 5, 14);
    addEdge(g, 4, 5, 10);
    addEdge(g, 5, 6, 2);
    addEdge(g, 6, 7, 1);
    addEdge(g, 6, 8, 6);
    addEdge(g, 7, 8, 7);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isEqualTo(37L);
    assertThat(solver.getMst()).hasLength(8);
  }

  @Test
  public void testMstIsIdempotent() {
    List<List<Edge>> g = createGraph(3);
    addEdge(g, 0, 1, 1);
    addEdge(g, 1, 2, 2);
    addEdge(g, 0, 2, 3);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);

    long cost1 = solver.getMstCost();
    long cost2 = solver.getMstCost();
    Edge[] mst1 = solver.getMst();
    Edge[] mst2 = solver.getMst();

    assertThat(cost1).isEqualTo(cost2);
    assertThat(mst1).isEqualTo(mst2);
  }

  @Test
  public void testStartNodeDisconnected() {
    List<List<Edge>> g = createGraph(4);
    addEdge(g, 1, 2, 1);
    addEdge(g, 2, 3, 1);
    addEdge(g, 3, 1, 1);
    EagerPrimsAdjacencyList solver = new EagerPrimsAdjacencyList(g);
    assertThat(solver.getMstCost()).isNull();
    assertThat(solver.getMst()).isNull();
  }

  @Test
  public void testLazyAndEagerAgree() {
    List<List<EagerPrimsAdjacencyList.Edge>> eg = EagerPrimsAdjacencyList.createEmptyGraph(10);
    List<List<LazyPrimsAdjacencyList.Edge>> lg = LazyPrimsAdjacencyList.createEmptyGraph(10);

    int[][] edges = {
      {0, 1, 5}, {1, 2, 4}, {2, 9, 2}, {0, 4, 1}, {0, 3, 4},
      {1, 3, 2}, {2, 7, 4}, {2, 8, 1}, {9, 8, 0}, {4, 5, 1},
      {5, 6, 7}, {6, 8, 4}, {4, 3, 2}, {5, 3, 5}, {3, 6, 11},
      {6, 7, 1}, {3, 7, 2}, {7, 8, 6}
    };
    for (int[] e : edges) {
      EagerPrimsAdjacencyList.addUndirectedEdge(eg, e[0], e[1], e[2]);
      LazyPrimsAdjacencyList.addUndirectedEdge(lg, e[0], e[1], e[2]);
    }

    EagerPrimsAdjacencyList eagerSolver = new EagerPrimsAdjacencyList(eg);
    LazyPrimsAdjacencyList lazySolver = new LazyPrimsAdjacencyList(lg);

    assertThat(eagerSolver.getMstCost()).isEqualTo(lazySolver.getMstCost());
  }
}
