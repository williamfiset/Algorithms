/**
 * An implementation of the lazy Prim's algorithm with an adjacency matrix which upon visiting a new
 * node adds all the edges to the min priority queue and also removes already seen edges when
 * polling.
 *
 * <p>Time Complexity: O(V^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class LazyPrimsAdjacencyMatrix {

  static class Edge implements Comparable<Edge> {
    int to, cost;

    public Edge(int to, int cost) {
      this.to = to;
      this.cost = cost;
    }

    @Override
    public int compareTo(Edge other) {
      return cost - other.cost;
    }
  }

  // Given an N*N undirected adjacency matrix, that is a
  // graph with matrix[i][j] = matrix[j][i] for all i,j this method
  // finds the minimum spanning tree cost using Prim's algorithm
  public static Long prims(Integer[][] graph) {

    int n = graph.length;
    long sum = 0, visitedNodes = 1;
    PriorityQueue<Edge> pq = new PriorityQueue<>();

    boolean[] connected = new boolean[n];
    connected[0] = true;

    for (int i = 1; i < n; i++) if (graph[0][i] != null) pq.offer(new Edge(i, graph[0][i]));

    // Loop while the MST is not complete
    while (visitedNodes != n && !pq.isEmpty()) {

      Edge edge = pq.poll();

      if (!connected[edge.to]) {

        // Update minimum distances
        for (int i = 0; i < n; i++)
          if (!connected[i] && graph[edge.to][i] != null) pq.offer(new Edge(i, graph[edge.to][i]));

        connected[edge.to] = true;
        sum += edge.cost;
        visitedNodes++;
      }
    }

    // Make sure MST spans the whole graph
    if (visitedNodes != n) return null;
    return sum;
  }

  // Example usage
  public static void main(String[] args) {

    final int NUM_NODES = 10;
    Integer[][] graph = new Integer[NUM_NODES][NUM_NODES];

    // Make an undirected graph
    graph[0][1] = graph[1][0] = 1;
    graph[0][3] = graph[3][0] = 4;
    graph[0][4] = graph[4][0] = 5;
    graph[1][3] = graph[3][1] = 2;
    graph[1][2] = graph[2][1] = 1;
    graph[2][3] = graph[3][2] = 5;
    graph[2][5] = graph[5][2] = 7;
    graph[3][4] = graph[4][3] = 2;
    graph[3][6] = graph[6][3] = 2;
    graph[3][5] = graph[5][3] = 11;
    graph[4][7] = graph[7][4] = 4;
    graph[5][6] = graph[6][5] = 1;
    graph[5][8] = graph[8][5] = 4;
    graph[6][7] = graph[7][6] = 4;
    graph[6][8] = graph[8][6] = 6;
    graph[7][8] = graph[8][7] = 1;
    graph[7][9] = graph[9][7] = 2;
    graph[8][9] = graph[9][8] = 0;

    Long mstCost = prims(graph);
    System.out.println("MST cost: " + mstCost);
  }
}
