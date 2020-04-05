/** NOTE: This algorithm is still a work in progress! See issue #18 to track progress. */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class AStar_GridHeuristic {

  // An edge class to represent a directed edge
  // between two nodes with a certain non-negative cost.
  static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      if (cost < 0) throw new IllegalArgumentException("No negative edge weights");
      this.from = from;
      this.to = to;
      this.cost = cost;
    }

    @Override
    public String toString() {
      return from + ":" + to;
    }
  }

  // Node class to track the nodes to visit while running A*
  private static class Node implements Comparable<Node> {

    int id;
    double f, g, h;

    private static final double EPS = 1e-7;

    public Node(int nodeID, double gg, double hh) {
      id = nodeID;
      g = gg;
      h = hh;
      f = g + h;
    }

    // Sort by f cost and break ties with h
    @Override
    public int compareTo(Node other) {
      if (Math.abs(f - other.f) < EPS) {
        if (Math.abs(h - other.h) < EPS) return 0;
        return (h - other.h) > 0 ? +1 : -1;
      }
      return (f - other.f) > 0 ? +1 : -1;
    }
  }

  // Run A* algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be
  // Double.POSITIVE_INFINITY.
  public static double astar(
      double[] X, double[] Y, Map<Integer, List<Edge>> graph, int start, int end, int n) {

    // In the event that you wish to rebuild the shortest path
    // you can do so using the prev array and starting at some node 'end'
    // and finding the previous node using prev[end] and the previous node
    // after that prev[prev[end]] etc... working all the way back until
    // the index of start is found. Simply uncomment where the prev array is used.
    // int[] prev = new int[n];

    double[] G = new double[n];
    double[] F = new double[n];
    Arrays.fill(G, Double.POSITIVE_INFINITY);
    Arrays.fill(F, Double.POSITIVE_INFINITY);
    G[start] = 0;
    F[start] = heuristic(X, Y, start, end);

    Set<Integer> openSet = new HashSet<>();
    Set<Integer> closedSet = new HashSet<>();

    // Keep a priority queue of the next most promising node to visit
    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(start, G[start], F[start]));
    openSet.add(start);

    while (!pq.isEmpty()) {

      Node node = pq.poll();
      openSet.remove(node.id);
      closedSet.add(node.id);

      if (node.id == end) return G[end];

      List<Edge> edges = graph.get(node.id);
      if (edges != null) {
        for (int i = 0; i < edges.size(); i++) {

          Edge edge = edges.get(i);
          if (closedSet.contains(edge.to)) continue;

          double g = node.g + edge.cost;
          double h = heuristic(X, Y, edge.to, end);

          if (g < G[edge.to] || !openSet.contains(edge.to)) {

            G[edge.to] = g;
            // prev[edge.to] = edge.from;

            if (!openSet.contains(edge.to)) {
              pq.offer(new Node(edge.to, g, h));
              openSet.add(edge.to);
            }
          }
        }
      }
    }

    // End node is unreachable
    return Double.POSITIVE_INFINITY;
  }

  // Euclidean distance is used as a heuristic
  static double heuristic(double[] X, double[] Y, int at, int end) {
    // double dx = Math.abs(X[at] - X[end]);
    // double dy = Math.abs(Y[at] - Y[end]);
    // return dx*dx + dy*dy; // Avoid square root
    // return Math.sqrt(dx*dx + dy*dy);
    return 0;
  }

  // Run a random test between A* and Dijkstra
  public static void main(String[] args) {

    Random RANDOM = new Random();

    int n = 20 * 20;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());

    double[] X = new double[n];
    double[] Y = new double[n];
    int N = (int) Math.sqrt(n);

    int connections = (n * (n - 1));
    int[] locations = new int[connections * 2];

    boolean[][] m = new boolean[n][n];

    for (int k = 0; k < connections; k++) {

      int i = RANDOM.nextInt(N);
      int j = RANDOM.nextInt(N);
      int ii = RANDOM.nextInt(N);
      int jj = RANDOM.nextInt(N);

      int node1 = i * N + j;
      int node2 = ii * N + jj;
      if (m[node1][node2]) continue;

      locations[2 * k] = node1;
      locations[2 * k + 1] = node2;

      X[node1] = i;
      Y[node1] = j;
      X[node2] = ii;
      Y[node2] = jj;

      addEdge(graph, node1, node2, i, j, ii, jj);
      m[node1][node2] = true;
    }

    System.out.println(graph);

    for (int i = 0; i < 10 * n; i++) {

      int s = locations[RANDOM.nextInt(2 * connections)];
      int e = locations[RANDOM.nextInt(2 * connections)];

      double d = dijkstra(graph, s, e, n);
      double a = astar(X, Y, graph, s, e, n);
      // System.out.println(a + " " + d);
      if (a != d) {
        System.out.println("ERROR: " + a + " " + d + " " + s + " " + e);
        // System.out.println(graph);
        break;
      }
      System.out.println();
    }
  }

  static void addEdge(
      Map<Integer, List<Edge>> graph, int f, int t, int fx, int fy, int tx, int ty) {
    double dx = Math.abs(fx - tx);
    double dy = Math.abs(fy - ty);
    graph.get(f).add(new Edge(f, t, dx + dy));
  }

  // Node class to track the nodes to visit while running Dijkstra's
  private static class DNode implements Comparable<DNode> {
    int id;
    double value;
    private static final double EPS = 1e-7;

    public DNode(int nodeID, double nodeValue) {
      id = nodeID;
      value = nodeValue;
    }

    @Override
    public int compareTo(DNode other) {
      if (Math.abs(value - other.value) < EPS) return 0;
      return (value - other.value) > 0 ? +1 : -1;
    }
  }

  // Run Dijkstra's algorithm on a directed graph to find the shortest path
  // from a starting node to an ending node. If there is no path between the
  // starting node and the destination node the returned value is set to be
  // Double.POSITIVE_INFINITY.
  public static double dijkstra(Map<Integer, List<Edge>> graph, int start, int end, int n) {

    // Maintain an array of the minimum distance to each node
    double[] dists = new double[n];
    Arrays.fill(dists, Double.POSITIVE_INFINITY);
    dists[start] = 0;

    // Keep a priority queue of the next most promising node to visit
    PriorityQueue<DNode> pq = new PriorityQueue<>();
    pq.offer(new DNode(start, 0.0));

    // Track which nodes have already been visited
    boolean[] visited = new boolean[n];

    // In the event that you wish to rebuild the shortest path
    // you can do so using the prev array and starting at some node 'end'
    // and finding the previous node using prev[end] and the previous node
    // after that prev[prev[end]] etc... working all the way back until
    // the index of start is found. Simply uncomment where the prev array is used.
    // int[] prev = new int[n];

    while (!pq.isEmpty()) {

      DNode node = pq.poll();
      visited[node.id] = true;

      // We already found a better path before we got to
      // processing this node so we can ignore it.
      if (node.value > dists[node.id]) continue;

      List<Edge> edges = graph.get(node.id);
      if (edges != null) {
        for (int i = 0; i < edges.size(); i++) {
          Edge edge = edges.get(i);

          // You cannot get a shorter path by revisiting
          // a node you have already visited before
          if (visited[edge.to]) continue;

          // Update minimum cost if applicable
          double newDist = dists[edge.from] + edge.cost;
          if (newDist < dists[edge.to]) {
            // prev[edge.to] = edge.from;
            dists[edge.to] = newDist;
            pq.offer(new DNode(edge.to, dists[edge.to]));
          }
        }
      }

      // Once we've visited all the nodes spanning from the end
      // node we know we can return the minimum distance value to
      // the end node because it cannot get any better after this point.
      if (node.id == end) {
        //         int v = 0;
        // for (boolean b : visited) if (b) v++;
        // System.out.println("V: " + v);
        return dists[end];
      }
    }

    // End node is unreachable
    return Double.POSITIVE_INFINITY;
  }
}
