/**
 * This is an implementation of doing a breadth first search recursively with a slight cheat of
 * passing in a queue as an argument to the function. A breadth first search
 *
 * <p>Time Complexity: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearchRecursive {

  // Each breadth first search layer gets separated by a DEPTH_TOKEN.
  // DEPTH_TOKENs help count the distance from one node to another because
  // we can increment the depth counter each time a DEPTH_TOKEN is encountered
  public static int DEPTH_TOKEN = -1;

  // Computes the eccentricity (distance to furthest node) from the starting node.
  public static int bfs(List<List<Integer>> graph, int start, int n) {
    boolean[] visited = new boolean[n];
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    queue.offer(DEPTH_TOKEN);
    return bfs(visited, queue, graph);
  }

  private static int bfs(boolean[] visited, Queue<Integer> queue, List<List<Integer>> graph) {

    int at = queue.poll();

    if (at == DEPTH_TOKEN) {
      queue.offer(DEPTH_TOKEN);
      return 1;
    }

    // This node is already visited.
    if (visited[at]) return 0;

    // Visit this node.
    visited[at] = true;

    // Add all neighbors to queue.
    List<Integer> neighbors = graph.get(at);
    if (neighbors != null) for (int next : neighbors) if (!visited[next]) queue.add(next);

    int depth = 0;

    while (true) {
      // Stop when the queue is empty (i.e there's only one depth token remaining)
      if (queue.size() == 1 && queue.peek() == DEPTH_TOKEN) break;

      // The depth is the sum of all DEPTH_TOKENS encountered.
      depth += bfs(visited, queue, graph);
    }

    return depth;
  }

  public static void main(String[] args) {
    int n = 14;
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 0, 2);
    addUndirectedEdge(graph, 0, 3);
    addUndirectedEdge(graph, 2, 9);
    addUndirectedEdge(graph, 8, 2);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 10, 11);
    addUndirectedEdge(graph, 12, 13);
    addUndirectedEdge(graph, 3, 5);
    addUndirectedEdge(graph, 5, 7);
    addUndirectedEdge(graph, 5, 6);
    addUndirectedEdge(graph, 0, 10);
    addUndirectedEdge(graph, 11, 12);

    System.out.printf("BFS depth: %d\n", bfs(graph, 12, n));
  }

  // Helper method to setup graph
  private static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
}
