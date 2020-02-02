/**
 * An implementation of an iterative BFS with an adjacency list Time Complexity: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

// A custom implementation of a circular integer only queue which is
// extremely quick and lightweight. In terms of performance it can outperform
// java.util.ArrayDeque (Java's fastest queue implementation) by a factor of 40+!
// However, the downside is you need to know an upper bound on the number of elements
// that will be inside the queue at any given time for this queue to work.
class IntQueue {

  private int[] ar;
  private int front, end, sz;

  // max_sz is the maximum number of items
  // that can be in the queue at any given time
  public IntQueue(int max_sz) {
    front = end = 0;
    this.sz = max_sz + 1;
    ar = new int[sz];
  }

  public boolean isEmpty() {
    return front == end;
  }

  public int peek() {
    return ar[front];
  }

  // Add an element to the queue
  public void enqueue(int value) {
    ar[end] = value;
    if (++end == sz) end = 0;
    if (end == front) throw new RuntimeException("Queue too small!");
  }

  // Make sure you check is the queue is not empty before calling dequeue!
  public int dequeue() {
    int ret_val = ar[front];
    if (++front == sz) front = 0;
    return ret_val;
  }
}

public class BreadthFirstSearchAdjacencyListIterativeFastQueue {

  static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  // Perform a breadth first search on a graph with n nodes
  // from a starting point to count the number of nodes
  // in a given component.
  static int bfs(Map<Integer, List<Edge>> graph, int start, int n) {

    int count = 0;
    boolean[] visited = new boolean[n];
    IntQueue queue = new IntQueue(n + 1);

    // For each breadth first search layer gets separated by a DEPTH_TOKEN
    // to easily augment this method for additional functionality
    int DEPTH_TOKEN = -1;

    // Start by visiting the starting node
    queue.enqueue(start);
    queue.enqueue(DEPTH_TOKEN);
    visited[start] = true;

    // Continue until the BFS is done
    while (true) {

      Integer node = queue.dequeue();

      // If we encounter a depth token this means that we
      // have finished the current frontier and are about
      // to start the new layer (some of which may already
      // be in the queue) or have reached the end.
      if (node == DEPTH_TOKEN) {

        // No more nodes to process
        if (queue.isEmpty()) break;

        // Add another DEPTH_TOKEN
        queue.enqueue(DEPTH_TOKEN);

      } else {

        count++;

        List<Edge> edges = graph.get(node);
        if (edges != null) {

          // Loop through all edges attached to this node. Mark nodes as
          // visited once they're in the queue. This will prevent having
          // duplicate nodes in the queue and speedup the BFS.
          for (Edge edge : edges) {
            if (!visited[edge.to]) {
              visited[edge.to] = true;
              queue.enqueue(edge.to);
            }
          }
        }
      }
    }

    return count;
  }

  // Example usage of DFS
  public static void main(String[] args) {

    // Create a fully connected graph
    int numNodes = 8;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    addDirectedEdge(graph, 1, 2, 1);
    addDirectedEdge(graph, 1, 2, 1); // Double edge
    addDirectedEdge(graph, 1, 3, 1);
    addDirectedEdge(graph, 2, 4, 1);
    addDirectedEdge(graph, 2, 5, 1);
    addDirectedEdge(graph, 3, 6, 1);
    addDirectedEdge(graph, 3, 7, 1);
    addDirectedEdge(graph, 2, 2, 1); // Self loop
    addDirectedEdge(graph, 2, 3, 1);
    addDirectedEdge(graph, 6, 2, 1);
    addDirectedEdge(graph, 1, 6, 1);

    long nodeCount = bfs(graph, 0, numNodes);
    System.out.println("DFS node count starting at node 0: " + nodeCount);

    nodeCount = bfs(graph, 2, numNodes);
    System.out.println("DFS node count starting at node 4: " + nodeCount);

    // Complete graph with self loops
    graph.clear();
    numNodes = 100;
    for (int i = 0; i < numNodes; i++)
      for (int j = 0; j < numNodes; j++) addDirectedEdge(graph, i, j, 1);

    nodeCount = bfs(graph, 6, numNodes);
    System.out.println("BFS node count starting at node 6: " + nodeCount);
    if (nodeCount != 100) System.err.println("Error with BFS");
  }

  // Helper method to setup graph
  private static void addDirectedEdge(Map<Integer, List<Edge>> graph, int from, int to, int cost) {
    List<Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
  }
}
