package com.williamfiset.algorithms.graphtheory.networkflow;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.graphutils.Utils;
import java.util.*;
import org.junit.*;

public class BipartiteGraphCheckAdjacencyListTest {

  private List<List<Integer>> graph;
  private BipartiteGraphCheckAdjacencyList solver;

  @Before
  public void setUp() {}

  @Test
  public void testSingleton() {
    int n = 1;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 0);
    assertThat(solver.isBipartite()).isFalse();
  }

  @Test
  public void testTwoNodeGraph() {
    int n = 2;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 1);
    assertThat(solver.isBipartite()).isTrue();
  }

  @Test
  public void testTriangleGraph() {
    int n = 3;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 0);
    assertThat(solver.isBipartite()).isFalse();
  }

  @Test
  public void testDisjointBipartiteGraphComponents() {
    int n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 2, 3);
    assertThat(solver.isBipartite()).isFalse();
  }

  @Test
  public void testSquareBipartiteGraph() {
    int n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    assertThat(solver.isBipartite()).isTrue();
  }

  @Test
  public void testSquareBipartiteGraphWithAdditionalEdge() {
    int n = 4;
    graph = Utils.createEmptyAdjacencyList(n);
    solver = new BipartiteGraphCheckAdjacencyList(graph);
    Utils.addUndirectedEdge(graph, 0, 1);
    Utils.addUndirectedEdge(graph, 1, 2);
    Utils.addUndirectedEdge(graph, 0, 2);
    Utils.addUndirectedEdge(graph, 2, 3);
    Utils.addUndirectedEdge(graph, 3, 0);
    assertThat(solver.isBipartite()).isFalse();
  }
}
