/**
 * Dijkstra's Shortest Path — Adjacency List (Lazy)
 *
 * <p>Finds the shortest path from a source node to a target node in a weighted
 * directed graph with non-negative edge weights. Uses a lazy approach with a
 * standard {@link java.util.PriorityQueue}: instead of decreasing keys, stale
 * entries are simply skipped when polled.
 *
 * <p>Stops early once the target node is settled, so it does not necessarily
 * visit the entire graph.
 *
 * <p>Time:  O(E log E)
 * <p>Space: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstrasShortestPathAdjacencyList {

  private final int n;
  private final List<List<int[]>> graph;

  private double[] dist;
  private Integer[] prev;

  public DijkstrasShortestPathAdjacencyList(int n) {
    this.n = n;
    this.graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
  }

  /**
   * Adds a directed edge from node {@code from} to node {@code to} with the given cost.
   */
  public void addEdge(int from, int to, int cost) {
    graph.get(from).add(new int[] {to, cost});
  }

  /**
   * Runs Dijkstra's algorithm from {@code start} to {@code end}.
   *
   * @return the shortest distance, or {@code Double.POSITIVE_INFINITY} if unreachable.
   */
  public double dijkstra(int start, int end) {
    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

    prev = new Integer[n];
    boolean[] visited = new boolean[n];

    // PQ entries: {nodeId, distance}
    PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(a[1], b[1]));
    pq.offer(new double[] {start, 0});

    while (!pq.isEmpty()) {
      double[] entry = pq.poll();
      int nodeId = (int) entry[0];
      visited[nodeId] = true;

      // Skip stale entries.
      if (entry[1] > dist[nodeId]) {
        continue;
      }

      for (int[] edge : graph.get(nodeId)) {
        int to = edge[0];
        int cost = edge[1];
        if (visited[to]) {
          continue;
        }
        double newDist = dist[nodeId] + cost;
        if (newDist < dist[to]) {
          dist[to] = newDist;
          prev[to] = nodeId;
          pq.offer(new double[] {to, newDist});
        }
      }

      if (nodeId == end) {
        return dist[end];
      }
    }

    return Double.POSITIVE_INFINITY;
  }

  /**
   * Returns the shortest path from {@code start} to {@code end} as a list of node ids,
   * or an empty list if unreachable.
   */
  public List<Integer> reconstructPath(int start, int end) {
    double d = dijkstra(start, end);
    if (d == Double.POSITIVE_INFINITY) {
      return List.of();
    }
    List<Integer> path = new ArrayList<>();
    for (Integer at = end; at != null; at = prev[at]) {
      path.add(at);
    }
    Collections.reverse(path);
    return path;
  }

  // ==================== Main ====================

  //
  //    0 ---5---> 1 ---2---> 3
  //    |          |          ^
  //    1          3          |
  //    |          |          1
  //    v          v          |
  //    2 ---6---> 4 ---------
  //
  //  Shortest path 0 -> 3: [0, 1, 3] cost 7
  //  Shortest path 0 -> 4: [0, 2, 4] cost 7
  //
  public static void main(String[] args) {
    DijkstrasShortestPathAdjacencyList solver = new DijkstrasShortestPathAdjacencyList(5);

    solver.addEdge(0, 1, 5);
    solver.addEdge(0, 2, 1);
    solver.addEdge(1, 3, 2);
    solver.addEdge(1, 4, 3);
    solver.addEdge(2, 4, 6);
    solver.addEdge(4, 3, 1);

    System.out.println("Path 0->3: " + solver.reconstructPath(0, 3));
    System.out.printf("Cost 0->3: %.0f%n", solver.dijkstra(0, 3));

    System.out.println("Path 0->4: " + solver.reconstructPath(0, 4));
    System.out.printf("Cost 0->4: %.0f%n", solver.dijkstra(0, 4));
  }
}
