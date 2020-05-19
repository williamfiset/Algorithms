package com.williamfiset.algorithms.graphtheory;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AStar_GridHeuristicTest {

  private List<AStar_GridHeuristic.Edge>[] aStar;
  private List<BellmanFordAdjacencyList.Edge>[] bellmanFord;
  private int V = 1;

  private void createEdges() {
    for (int i = 0; i < V; i++) {
      AStar_GridHeuristic.addEdge(aStar, i, (int) (Math.random() * i), (int) (Math.random() * 50));
      BellmanFordAdjacencyList.addEdge(
          bellmanFord, i, (int) (Math.random() * i), (int) (Math.random() * 50));
    }
  }

  @Test
  public void test() {
    for (int i = 0; i < 20; i++, V++) {
      aStar = AStar_GridHeuristic.createGraph(V);
      bellmanFord = BellmanFordAdjacencyList.createGraph(V);

      createEdges();

      for (int j = 0; j < V; j++) {
        assertEquals(
            AStar_GridHeuristic.aStar(aStar, j, (int) (Math.random() * j)),
            BellmanFordAdjacencyList.bellmanFord(bellmanFord, V, j)[j],
            50.0);
      }
    }
  }
}
