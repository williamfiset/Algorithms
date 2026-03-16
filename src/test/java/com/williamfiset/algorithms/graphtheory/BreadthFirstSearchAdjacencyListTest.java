package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyList.Edge;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyList.addUnweightedUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.BreadthFirstSearchAdjacencyList.createEmptyGraph;
import static java.lang.Math.max;
import static java.lang.Math.random;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;

import com.williamfiset.algorithms.graphtheory.BellmanFordEdgeList;

public class BreadthFirstSearchAdjacencyListTest {

  BreadthFirstSearchAdjacencyList solver;

  @BeforeEach
  public void setup() {
    solver = null;
  }

  @Test
  public void testNullGraphInput() {
    assertThrows(
        IllegalArgumentException.class, () -> new BreadthFirstSearchAdjacencyList(null));
  }

  @Test
  public void testSingletonGraph() {
    int n = 1;
    List<List<Edge>> graph = createEmptyGraph(n);

    solver = new BreadthFirstSearchAdjacencyList(graph);
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
    solver = new BreadthFirstSearchAdjacencyList(graph);

    List<Integer> expected = new ArrayList<>();
    expected.add(0);
    expected.add(1);

    List<Integer> path = solver.reconstructPath(0, 1);
    assertThat(path).isEqualTo(expected);
  }

  @Test
  public void testThreeNodeGraph() {
    int n = 3;
    List<List<Edge>> graph = BreadthFirstSearchAdjacencyList.createEmptyGraph(n);
    addUnweightedUndirectedEdge(graph, 0, 1);
    addUnweightedUndirectedEdge(graph, 2, 1);
    solver = new BreadthFirstSearchAdjacencyList(graph);

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
      solver = new BreadthFirstSearchAdjacencyList(graph);

      // Convert adjacency matrix to edge list for BellmanFord
      List<BellmanFordEdgeList.Edge> edgeList = new ArrayList<>();
      for (int u = 0; u < n; u++) {
        for (int v = 0; v < n; v++) {
          if (u != v && graph2[u][v] != Double.POSITIVE_INFINITY) {
            edgeList.add(new BellmanFordEdgeList.Edge(u, v, graph2[u][v]));
          }
        }
      }
      BellmanFordEdgeList.Edge[] edges = edgeList.toArray(new BellmanFordEdgeList.Edge[0]);
      double[] dist = BellmanFordEdgeList.bellmanFord(edges, n, s);

      List<Integer> p1 = solver.reconstructPath(s, e);
      // All edges have weight 1, so BF distance equals number of edges = path size - 1
      int expectedPathSize = (dist[e] == Double.POSITIVE_INFINITY) ? 0 : (int) dist[e] + 1;
      assertThat(p1.size()).isEqualTo(expectedPathSize);
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
