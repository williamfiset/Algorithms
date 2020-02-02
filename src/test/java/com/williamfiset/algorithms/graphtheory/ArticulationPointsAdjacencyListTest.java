package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.*;

public class ArticulationPointsAdjacencyListTest {

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // Every edge should be a bridge if the input a tree
  @Test
  public void testTreeCase() {

    int n = 12;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 0, 2);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 5, 11);
    addEdge(graph, 5, 4);
    addEdge(graph, 4, 10);
    addEdge(graph, 4, 3);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 7, 9);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();

    boolean[] expected = new boolean[n];
    expected[0] = expected[2] = expected[5] = true;
    expected[4] = expected[3] = expected[7] = true;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testClosedCycle() {
    int n = 3;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 0);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testStarGraphWithRootAsMiddle() {
    int n = 6;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 0, 4);
    addEdge(graph, 0, 5);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];
    expected[0] = true;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testStarGraphWithRootNotInMiddle() {
    int n = 6;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 3);
    addEdge(graph, 1, 4);
    addEdge(graph, 1, 5);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];
    expected[1] = true;

    assertThat(actual).isEqualTo(expected);
  }

  // BiConnectedGraphs should not have any articulation points
  @Test
  public void testBiConnectedGraph() {
    int n = 5;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 1, 2);
    addEdge(graph, 0, 2);
    addEdge(graph, 0, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 4, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testButterflyGraph() {
    int n = 5;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 1, 0);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 4, 3);
    addEdge(graph, 4, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];
    expected[2] = true;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testButterflyGraphWithThreeWings() {
    int n = 7;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 2);
    addEdge(graph, 2, 4);
    addEdge(graph, 4, 3);
    addEdge(graph, 3, 2);
    addEdge(graph, 2, 5);
    addEdge(graph, 6, 5);
    addEdge(graph, 6, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 1, 0);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];
    expected[2] = true;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testDisconnectedGraph() {
    int n = 11;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 2, 1);

    addEdge(graph, 3, 4);

    addEdge(graph, 5, 7);
    addEdge(graph, 6, 7);
    addEdge(graph, 8, 7);
    addEdge(graph, 8, 9);
    addEdge(graph, 8, 10);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();
    boolean[] expected = new boolean[n];
    expected[1] = true;
    expected[7] = true;
    expected[8] = true;

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testSingleNodeGraph() {
    int n = 1;
    List<List<Integer>> graph = createGraph(n);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();

    boolean[] expected = new boolean[n];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testTwoNodeGraph() {
    int n = 2;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();

    boolean[] expected = new boolean[n];
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testMultiCycleGraph() {
    int n = 7;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 2, 1);
    addEdge(graph, 2, 0);

    addEdge(graph, 2, 3);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 3);

    addEdge(graph, 3, 4);
    addEdge(graph, 4, 6);
    addEdge(graph, 6, 3);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();

    boolean[] expected = new boolean[n];
    expected[2] = true;
    expected[3] = true;
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void testThreeNodeLineGraph() {
    int n = 3;
    List<List<Integer>> graph = createGraph(n);
    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph, n);
    boolean[] actual = solver.findArticulationPoints();

    boolean[] expected = new boolean[n];
    expected[1] = true; // middle node.
    assertThat(actual).isEqualTo(expected);
  }
}
