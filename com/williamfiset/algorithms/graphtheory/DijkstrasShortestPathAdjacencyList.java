/**
 * This file contains an implementation of Dijkstra's shortest path algorithm from a start node to a
 * specific ending node. Dijkstra can also be modified to find the shortest path between a starting
 * node and all other nodes in the graph. However, in this implementation since we're only going
 * from a starting node to an ending node we can employ an optimization to stop early once we've
 * visited all the neighbors of the ending node.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstrasShortestPathAdjacencyList {

  // Small epsilon value to comparing double values.
  private static final double EPS = 1e-6;

  // An edge class to represent a directed edge
  // between two nodes with a certain cost.
  public static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  // Node class to track the nodes to visit while running Dijkstra's
  public static class Node {
    int id;
    double value;

    public Node(int id, double value) {
      this.id = id;
      this.value = value;
    }
  }

  private int n;
  private double[] dist;
  private Integer[] prev;
  private List<List<Edge>> graph;

  private Comparator<Node> comparator =
      new Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
          if (Math.abs(node1.value - node2.value) < EPS) return 0;
          return (node1.value - node2.value) > 0 ? +1 : -1;
        }
      };

  /**
   * Initialize the solver by providing the graph size and a starting node. Use the {@link #addEdge}
   * method to actually add edges to the graph.
   *
   * @param n - The number of nodes in the graph.
   */
  public DijkstrasShortestPathAdjacencyList(int n) {
    this.n = n;
    createEmptyGraph();
  }

  public DijkstrasShortestPathAdjacencyList(int n, Comparator<Node> comparator) {
    this(n);
    if (comparator == null) throw new IllegalArgumentException("Comparator cannot be null");
    this.comparator = comparator;
  }

  /**
   * Adds a directed edge to the graph.
   *
   * @param from - The index of the node the directed edge starts at.
   * @param to - The index of the node the directed edge end at.
   * @param cost - The cost of the edge.
   */
  public void addEdge(int from, int to, int cost) {
    graph.get(from).add(new Edge(from, to, cost));
  }

  // Use {@link #addEdge} method to add edges to the graph and use this method
  // to retrieve the constructed graph.
  public List<List<Edge>> getGraph() {
    return graph;
  }

  /**
   * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
   *
   * @return An array of nodes indexes of the shortest path from 'start' to 'end'. If 'start' and
   *     'end' are not connected then an empty array is returned.
   */
  public List<Integer> reconstructPath(int start, int end) {
    if (end < 0 || end >= n) throw new IllegalArgumentException("Invalid node index");
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid node index");
    double dist = dijkstra(start, end);
    List<Integer> path = new ArrayList<>();
    if (dist == Double.POSITIVE_INFINITY) return path;
    for (Integer at = end; at != null; at = prev[at]) path.add(at);
    Collections.reverse(path);
    return path;
  }

  // Run Dijkstra's algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be
  // Double.POSITIVE_INFINITY.
  public double dijkstra(int start, int end) {
    // Maintain an array of the minimum distance to each node
    dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[start] = 0;

    // Keep a priority queue of the next most promising node to visit.
    PriorityQueue<Node> pq = new PriorityQueue<>(2 * n, comparator);
    pq.offer(new Node(start, 0));

    // Array used to track which nodes have already been visited.
    boolean[] visited = new boolean[n];
    prev = new Integer[n];

    while (!pq.isEmpty()) {
      Node node = pq.poll();
      visited[node.id] = true;

      // We already found a better path before we got to
      // processing this node so we can ignore it.
      if (dist[node.id] < node.value) continue;

      List<Edge> edges = graph.get(node.id);
      for (int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);

        // You cannot get a shorter path by revisiting
        // a node you have already visited before.
        if (visited[edge.to]) continue;

        // Relax edge by updating minimum cost if applicable.
        double newDist = dist[edge.from] + edge.cost;
        if (newDist < dist[edge.to]) {
          prev[edge.to] = edge.from;
          dist[edge.to] = newDist;
          pq.offer(new Node(edge.to, dist[edge.to]));
        }
      }
      // Once we've visited all the nodes spanning from the end
      // node we know we can return the minimum distance value to
      // the end node because it cannot get any better after this point.
      if (node.id == end) return dist[end];
    }
    // End node is unreachable
    return Double.POSITIVE_INFINITY;
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void createEmptyGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }
}
