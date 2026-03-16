/**
 * Breadth-First Search (BFS) — Adjacency List
 *
 * <p>Explores a graph level by level outward from a starting node using a
 * FIFO queue. Because BFS visits nodes in order of increasing distance,
 * it naturally finds the shortest path (fewest edges) between two nodes
 * in an unweighted graph.
 *
 * <p>Algorithm:
 * <ol>
 *   <li>Enqueue the start node and mark it visited.</li>
 *   <li>Dequeue a node, then enqueue all its unvisited neighbours
 *       (marking each visited and recording the parent pointer).</li>
 *   <li>Repeat until the queue is empty.</li>
 *   <li>Reconstruct the shortest path by following parent pointers
 *       from the end node back to the start.</li>
 * </ol>
 *
 * <p>Time:  O(V + E)
 * <p>Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class BreadthFirstSearchAdjacencyList {

  public static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  private final int n;
  private final List<List<Edge>> graph;
  private Integer[] prev;

  public BreadthFirstSearchAdjacencyList(List<List<Edge>> graph) {
    if (graph == null) {
      throw new IllegalArgumentException("Graph can not be null");
    }
    this.n = graph.size();
    this.graph = graph;
  }

  /**
   * Returns the shortest path (fewest edges) from {@code start} to {@code end}.
   *
   * @return node indices along the path, or an empty list if unreachable.
   */
  public List<Integer> reconstructPath(int start, int end) {
    bfs(start);
    List<Integer> path = new ArrayList<>();
    for (Integer at = end; at != null; at = prev[at]) {
      path.add(at);
    }
    Collections.reverse(path);
    if (path.get(0) == start) {
      return path;
    }
    path.clear();
    return path;
  }

  private void bfs(int start) {
    prev = new Integer[n];
    boolean[] visited = new boolean[n];
    Deque<Integer> queue = new ArrayDeque<>(n);

    queue.offer(start);
    visited[start] = true;

    while (!queue.isEmpty()) {
      int node = queue.poll();
      for (Edge edge : graph.get(node)) {
        if (!visited[edge.to]) {
          visited[edge.to] = true;
          prev[edge.to] = node;
          queue.offer(edge.to);
        }
      }
    }
  }

  /* Graph helpers */

  public static List<List<Edge>> createEmptyGraph(int n) {
    List<List<Edge>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }
    return graph;
  }

  public static void addDirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
    graph.get(u).add(new Edge(u, v, cost));
  }

  public static void addUndirectedEdge(List<List<Edge>> graph, int u, int v, int cost) {
    addDirectedEdge(graph, u, v, cost);
    addDirectedEdge(graph, v, u, cost);
  }

  public static void addUnweightedUndirectedEdge(List<List<Edge>> graph, int u, int v) {
    addUndirectedEdge(graph, u, v, 1);
  }

  // ==================== Main ====================

  //
  //   0 --- 1 --- 2
  //   |     |     |
  //   3     4     5
  //    \   / \   /
  //      6 --- 7
  //
  // Shortest path from 0 to 7: [0, 1, 4, 7]
  //
  public static void main(String[] args) {
    int n = 8;
    List<List<Edge>> graph = createEmptyGraph(n);

    addUnweightedUndirectedEdge(graph, 0, 1);
    addUnweightedUndirectedEdge(graph, 1, 2);
    addUnweightedUndirectedEdge(graph, 0, 3);
    addUnweightedUndirectedEdge(graph, 1, 4);
    addUnweightedUndirectedEdge(graph, 2, 5);
    addUnweightedUndirectedEdge(graph, 3, 6);
    addUnweightedUndirectedEdge(graph, 4, 6);
    addUnweightedUndirectedEdge(graph, 4, 7);
    addUnweightedUndirectedEdge(graph, 5, 7);
    addUnweightedUndirectedEdge(graph, 6, 7);

    BreadthFirstSearchAdjacencyList solver = new BreadthFirstSearchAdjacencyList(graph);

    System.out.println(solver.reconstructPath(0, 7)); // [0, 1, 4, 7]
    System.out.println(solver.reconstructPath(3, 5)); // [3, 6, 7, 5]
  }
}
