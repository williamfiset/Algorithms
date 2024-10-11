/**
 * This file contains an implementation of Dijkstra's shortest path algorithm from a start node to a
 * specific ending node. Dijkstra's can also be modified to find the shortest path between a
 * starting node and all other nodes in the graph with minimal effort.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import com.williamfiset.algorithms.datastructures.priorityqueue.MinIndexedDHeap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DijkstrasShortestPathAdjacencyListWithDHeap {

  // An edge class to represent a directed edge
  // between two nodes with a certain cost.
  public static class Edge {
    int to;
    double cost;

    public Edge(int to, double cost) {
      this.to = to;
      this.cost = cost;
    }
  }

  private final int n;

  private int edgeCount;
  private double[] dist;
  private Integer[] prev;
  private List<List<Edge>> graph;

  /**
   * Initialize the solver by providing the graph size and a starting node. Use the {@link #addEdge}
   * method to actually add edges to the graph.
   *
   * @param n - The number of nodes in the graph.
   */
  public DijkstrasShortestPathAdjacencyListWithDHeap(int n) {
    this.n = n;
    createEmptyGraph();
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void createEmptyGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

  /**
   * Adds a directed edge to the graph.
   *
   * @param from - The index of the node the directed edge starts at.
   * @param to - The index of the node the directed edge end at.
   * @param cost - The cost of the edge.
   */
  public void addEdge(int from, int to, int cost) {
    edgeCount++;
    graph.get(from).add(new Edge(to, cost));
  }

  /**
   * Use {@link #addEdge} method to add edges to the graph and use this method to retrieve the
   * constructed graph.
   */
  public List<List<Edge>> getGraph() {
    return graph;
  }

  // Run Dijkstra's algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be
  // Double.POSITIVE_INFINITY.
  public double dijkstra(int start, int end) {

    // Keep an Indexed Priority Queue (ipq) of the next most promising node
    // to visit.
    int degree = edgeCount / n;
    MinIndexedDHeap<Double> ipq = new MinIndexedDHeap<>(degree, n);
    ipq.insert(start, 0.0);

    // Maintain an array of the minimum distance to each node.
    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0.0;

    boolean[] visited = new boolean[n];
    prev = new Integer[n];

    while (!ipq.isEmpty()) {
      int nodeId = ipq.peekMinKeyIndex();

      visited[nodeId] = true;
      double minValue = ipq.pollMinValue();

      // We already found a better path before we got to
      // processing this node so we can ignore it.
      if (minValue > dist[nodeId]) continue;

      for (Edge edge : graph.get(nodeId)) {

        // We cannot get a shorter path by revisiting
        // a node we have already visited before.
        if (visited[edge.to]) continue;

        // Relax edge by updating minimum cost if applicable.
        double newDist = dist[nodeId] + edge.cost;
        if (newDist < dist[edge.to]) {
          prev[edge.to] = nodeId;
          dist[edge.to] = newDist;
          // Insert the cost of going to a node for the first time in the PQ,
          // or try and update it to a better value by calling decrease.
          if (!ipq.contains(edge.to)) ipq.insert(edge.to, newDist);
          else ipq.decrease(edge.to, newDist);
        }
      }
      // Once we've processed the end node we can return early (without
      // necessarily visiting the whole graph) because we know we cannot get a
      // shorter path by routing through any other nodes since Dijkstra's is
      // greedy and there are no negative edge weights.
      if (nodeId == end) return dist[end];
    }
    // End node is unreachable.
    return Double.POSITIVE_INFINITY;
  }

  /**
   * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
   *
   * @return An array of node indexes of the shortest path from 'start' to 'end'. If 'start' and
   *     'end' are not connected then an empty array is returned.
   */
  public List<Integer> reconstructPath(int start, int end) {
    if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
    List<Integer> path = new ArrayList<>();
    double dist = dijkstra(start, end);
    if (dist == Double.POSITIVE_INFINITY) return path;
    for (Integer at = end; at != null; at = prev[at]) path.add(at);
    Collections.reverse(path);
    return path;
  }
}
