package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class DijkstrasShortestPathAdjacencyListTest {

  @Test
  public void singleNode() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(1);
    double dist = solver.dijkstra(0, 0);
    assertThat(dist).isEqualTo(0.0);
  }

  @Test
  public void twoNodesDirectEdge() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(2);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(0, 1);

    assertThat(dist).isEqualTo(5.0);
  }

  @Test
  public void unreachableNodeReturnsInfinity() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(3);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(0, 2);

    assertThat(dist).isPositiveInfinity();
  }

  @Test
  public void shortestPathChosenOverLonger() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(3);
    solver.addEdge(0, 2, 10);
    solver.addEdge(0, 1, 3);
    solver.addEdge(1, 2, 4);

    double dist = solver.dijkstra(0, 2);

    assertThat(dist).isEqualTo(7.0);
  }

  @Test
  public void reconstructPathSimple() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(4);
    solver.addEdge(0, 1, 1);
    solver.addEdge(1, 2, 1);
    solver.addEdge(2, 3, 1);

    List<Integer> path = solver.reconstructPath(0, 3);

    assertThat(path).containsExactly(0, 1, 2, 3).inOrder();
  }

  @Test
  public void reconstructPathChoosesShortest() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(3);
    solver.addEdge(0, 2, 10);
    solver.addEdge(0, 1, 3);
    solver.addEdge(1, 2, 4);

    List<Integer> path = solver.reconstructPath(0, 2);

    assertThat(path).containsExactly(0, 1, 2).inOrder();
  }

  @Test
  public void reconstructPathUnreachableReturnsEmpty() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(3);
    solver.addEdge(0, 1, 5);

    List<Integer> path = solver.reconstructPath(0, 2);

    assertThat(path).isEmpty();
  }

  @Test
  public void reconstructPathStartEqualsEnd() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(2);
    solver.addEdge(0, 1, 1);

    List<Integer> path = solver.reconstructPath(0, 0);

    assertThat(path).containsExactly(0);
  }

  @Test
  public void directedEdgeNotTraversedBackwards() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(2);
    solver.addEdge(0, 1, 5);

    double dist = solver.dijkstra(1, 0);

    assertThat(dist).isPositiveInfinity();
  }

  @Test
  public void longerPathWithMoreHops() {
    // 0→1→2→3→4 with cost 1 each (total 4) vs 0→4 with cost 100
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(5);
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
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(4);
    solver.addEdge(0, 1, 3);
    solver.addEdge(2, 3, 1);

    assertThat(solver.dijkstra(0, 1)).isEqualTo(3.0);
    assertThat(solver.dijkstra(0, 2)).isPositiveInfinity();
    assertThat(solver.dijkstra(0, 3)).isPositiveInfinity();
  }

  @Test
  public void getGraphReturnsAdjacencyList() {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(3);
    solver.addEdge(0, 1, 5);
    solver.addEdge(0, 2, 10);

    List<List<DijkstrasShortestPathAdjacencyList.Edge>> graph = solver.getGraph();

    assertThat(graph).hasSize(3);
    assertThat(graph.get(0)).hasSize(2);
    assertThat(graph.get(1)).isEmpty();
    assertThat(graph.get(2)).isEmpty();
  }
}
