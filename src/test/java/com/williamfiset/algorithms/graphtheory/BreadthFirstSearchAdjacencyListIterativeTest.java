package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative.Edge;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative.addUnweightedUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyListIterative.createEmptyGraph;
import static java.lang.Math.max;
import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;

public class BreadthFirstSearchAdjacencyListIterativeTest {

  BreadthFirstSearchAdjacencyListIterative solver;

  @BeforeEach
  public void setup() {
    solver = null;
  }

  @Test
  public void testNullGraphInput() {
    assertThrows(
        IllegalArgumentException.class, () -> new BreadthFirstSearchAdjacencyListIterative(null));
  }

  @Test
  public void testSingletonGraph() {
    int n = 1;
    List<List<Edge>> graph = createEmptyGraph(n);

    solver = new BreadthFirstSearchAdjacencyListIterative(graph);
    List<Integer> path = solver.reconstructPath(0, 0);
    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    assertThat(path).isEqualTo(expected);
  }

  @Test
  public void testTwoNodeGraph() {
    int n = 2;
    List<List<Edge>> graph = createEmptyGraph(n);
    addUnweightedUndirectedEdge(graph, 0, 1);
    solver = new BreadthFirstSearchAdjacencyListIterative(graph);

    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    expected.add(1);

    List<Integer> path = solver.reconstructPath(0, 1);
    assertThat(path).isEqualTo(expected);
  }

  @Test
  public void testThreeNodeGraph() {
    int n = 3;
    List<List<Edge>> graph = BreadthFirstSearchAdjacencyListIterative.createEmptyGraph(n);
    addUnweightedUndirectedEdge(graph, 0, 1);
    addUnweightedUndirectedEdge(graph, 2, 1);
    solver = new BreadthFirstSearchAdjacencyListIterative(graph);

    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    expected.add(1);
    expected.add(2);

    List<Integer> path = solver.reconstructPath(0, 2);
    assertThat(path).isEqualTo(expected);
  }

  @Test
  public void testShortestPathAgainstBellmanFord() {
    int loops = 150;
    for (int i = 0, n = 1; i < loops; i++, n++) {
      List<List<Edge>> graph = createEmptyGraph(n);
      double[][] graph2 = generateRandomGraph(graph, n);

      int s = (int) (random() * n);
      int e = (int) (random() * n);
      solver = new BreadthFirstSearchAdjacencyListIterative(graph);
      BellmanFordAdjacencyMatrix bfSolver = new BellmanFordAdjacencyMatrix(s, graph2);

      List<Integer> p1 = solver.reconstructPath(s, e);
      List<Integer> p2 = bfSolver.reconstructShortestPath(e);
      assertThat(p1.size()).isEqualTo(p2.size());
    }
  }

  public static double[][] generateRandomGraph(List<List<Edge>> graph1, int n) {
    boolean[][] edgeMatrix = new boolean[n][n];
    double[][] graph2 = new double[n][n];
    for (double[] r : graph2) Arrays.fill(r, Double.POSITIVE_INFINITY);

    int numEdges = max(1, (int) (random() * n * n));
    for (int i = 0; i < numEdges; i++) {
      int u = (int) (random() * n);
      int v = (int) (random() * n);
      if (!edgeMatrix[u][v]) {
        addUnweightedUndirectedEdge(graph1, u, v);
        graph2[u][v] = graph2[v][u] = 1;
        edgeMatrix[u][v] = edgeMatrix[v][u] = true;
      }
    }

    return graph2;
  }
}
