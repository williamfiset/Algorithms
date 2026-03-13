package com.williamfiset.algorithms.graphtheory.networkflow;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.networkflow.BipartiteGraphCheckAdjacencyList.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.networkflow.BipartiteGraphCheckAdjacencyList.createGraph;

import java.util.List;
import org.junit.jupiter.api.Test;

public class BipartiteGraphCheckAdjacencyListTest {

  @Test
  public void testSelfLoop() {
    List<List<Integer>> g = createGraph(1);
    addUndirectedEdge(g, 0, 0);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isFalse();
  }

  @Test
  public void testTwoNodeGraph() {
    List<List<Integer>> g = createGraph(2);
    addUndirectedEdge(g, 0, 1);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isTrue();
  }

  @Test
  public void testTriangleGraph() {
    List<List<Integer>> g = createGraph(3);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 2, 0);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isFalse();
  }

  @Test
  public void testDisjointComponents() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 2, 3);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isFalse();
  }

  @Test
  public void testSquareBipartiteGraph() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 2, 3);
    addUndirectedEdge(g, 3, 0);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isTrue();
  }

  @Test
  public void testSquareWithDiagonal() {
    List<List<Integer>> g = createGraph(4);
    addUndirectedEdge(g, 0, 1);
    addUndirectedEdge(g, 1, 2);
    addUndirectedEdge(g, 0, 2);
    addUndirectedEdge(g, 2, 3);
    addUndirectedEdge(g, 3, 0);
    assertThat(new BipartiteGraphCheckAdjacencyList(g).isBipartite()).isFalse();
  }
}
