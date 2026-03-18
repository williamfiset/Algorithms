package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class DijkstrasShortestPathAdjacencyListWithDHeapTest {

  @Test
  public void singleNode() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(1);
    double dist = solver.dijkstra(0, 0);
    assertThat(dist).isEqualTo(0.0);
  }

  @Test
  public void twoNodesDirectEdge() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(2);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(0, 1);

    assertThat(dist).isEqualTo(5.0);
  }

  @Test
  public void unreachableNodeReturnsInfinity() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(3);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(0, 2);

    assertThat(dist).isPositiveInfinity();
  }

  @Test
  public void shortestPathChosenOverLonger() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(3);
    solver.addEdge(0, 2, 10);
    solver.addEdge(0, 1, 3);
    solver.addEdge(1, 2, 4);

    double dist = solver.dijkstra(0, 2);

    assertThat(dist).isEqualTo(7.0);
  }

  @Test
  public void reconstructPathSimple() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(4);
    solver.addEdge(0, 1, 1);
    solver.addEdge(1, 2, 1);
    solver.addEdge(2, 3, 1);

    List<Integer> path = solver.reconstructPath(0, 3);

    assertThat(path).containsExactly(0, 1, 2, 3).inOrder();
  }

  @Test
  public void reconstructPathChoosesShortest() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(3);
    solver.addEdge(0, 2, 10);
    solver.addEdge(0, 1, 3);
    solver.addEdge(1, 2, 4);

    List<Integer> path = solver.reconstructPath(0, 2);

    assertThat(path).containsExactly(0, 1, 2).inOrder();
  }

  @Test
  public void reconstructPathUnreachableReturnsEmpty() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(3);
    solver.addEdge(0, 1, 5);

    List<Integer> path = solver.reconstructPath(0, 2);

    assertThat(path).isEmpty();
  }

  @Test
  public void reconstructPathStartEqualsEnd() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(2);
    solver.addEdge(0, 1, 1);

    List<Integer> path = solver.reconstructPath(0, 0);

    assertThat(path).containsExactly(0);
  }

  @Test
  public void directedEdgeNotTraversedBackwards() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(2);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(1, 0);

    assertThat(dist).isPositiveInfinity();
  }

  @Test
  public void longerPathWithMoreHops() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(5);
    solver.addEdge(0, 4, 100);
    solver.addEdge(0, 1, 1);
    solver.addEdge(1, 2, 1);
    solver.addEdge(2, 3, 1);
    solver.addEdge(3, 4, 1);

    double dist = solver.dijkstra(0, 4);
    List<Integer> path = solver.reconstructPath(0, 4);

    assertThat(dist).isEqualTo(4.0);
    assertThat(path).containsExactly(0, 1, 2, 3, 4).inOrder();
  }

  @Test
  public void disconnectedGraph() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(4);
    solver.addEdge(0, 1, 3);
    solver.addEdge(2, 3, 1);

    assertThat(solver.dijkstra(0, 1)).isEqualTo(3.0);
    assertThat(solver.dijkstra(0, 2)).isPositiveInfinity();
    assertThat(solver.dijkstra(0, 3)).isPositiveInfinity();
  }

  @Test
  public void getGraphReturnsAdjacencyList() {
    DijkstrasShortestPathAdjacencyListWithDHeap solver =
        new DijkstrasShortestPathAdjacencyListWithDHeap(3);
    solver.addEdge(0, 1, 5);
    solver.addEdge(0, 2, 10);

    List<List<DijkstrasShortestPathAdjacencyListWithDHeap.Edge>> graph = solver.getGraph();

    assertThat(graph).hasSize(3);
    assertThat(graph.get(0)).hasSize(2);
    assertThat(graph.get(1)).isEmpty();
    assertThat(graph.get(2)).isEmpty();
  }
}
