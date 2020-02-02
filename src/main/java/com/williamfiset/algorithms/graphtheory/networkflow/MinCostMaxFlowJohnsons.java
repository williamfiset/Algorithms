/**
 * Min cost max flow implementation using Johnson's algorithm (initial Bellman- Ford + subsequent
 * Dijkstra runs) as a method of finding augmenting paths.
 *
 * <p>Tested against: - https://open.kattis.com/problems/mincostmaxflow -
 * https://open.kattis.com/problems/jobpostings
 *
 * <p>Time Complexity: O(E²Vlog(V))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.*;

public class MinCostMaxFlowJohnsons extends NetworkFlowSolverBase {

  /**
   * Creates an instance of a flow network solver. Use the {@link NetworkFlowSolverBase#addEdge}
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= s < n
   * @param t - The index of the sink node, 0 <= t < n, t != s
   */
  public MinCostMaxFlowJohnsons(int n, int s, int t) {
    super(n, s, t);
  }

  private void init() {
    long[] dist = new long[n];
    Arrays.fill(dist, INF);
    dist[s] = 0;

    // Run Bellman-Ford algorithm to get the optimal distance to each node, O(VE)
    for (int i = 0; i < n - 1; i++)
      for (List<Edge> edges : graph)
        for (Edge edge : edges)
          if (edge.remainingCapacity() > 0 && dist[edge.from] + edge.cost < dist[edge.to])
            dist[edge.to] = dist[edge.from] + edge.cost;

    adjustEdgeCosts(dist);
  }

  // Adjust edge costs to be non-negative for Dijkstra's algorithm, O(E)
  private void adjustEdgeCosts(long[] dist) {
    for (int from = 0; from < n; from++) {
      for (Edge edge : graph[from]) {
        if (edge.remainingCapacity() > 0) {
          edge.cost += dist[from] - dist[edge.to];
        } else {
          edge.cost = 0;
        }
      }
    }
  }

  @Override
  public void solve() {
    init();

    // Sum up the bottlenecks on each augmenting path to find the max flow and min cost.
    List<Edge> path;
    while ((path = getAugmentingPath()).size() != 0) {

      // Find bottle neck edge value along path.
      long bottleNeck = Long.MAX_VALUE;
      for (Edge edge : path) bottleNeck = min(bottleNeck, edge.remainingCapacity());

      // Retrace path while augmenting the flow
      for (Edge edge : path) {
        edge.augment(bottleNeck);
        minCost += bottleNeck * edge.originalCost;
      }
      maxFlow += bottleNeck;
    }
  }

  // Finds an augmenting path from the source node to the sink using Johnson's
  // shortest path algorithm. First, Bellman-Ford was ran to get the shortest
  // path from the source to every node, and then the graph was cost adjusted
  // to remove negative edge weights so that Dijkstra's can be used in
  // subsequent runs for improved time complexity.
  private List<Edge> getAugmentingPath() {

    class Node implements Comparable<Node> {
      int id;
      long value;

      public Node(int id, long value) {
        this.id = id;
        this.value = value;
      }

      @Override
      public int compareTo(Node other) {
        return (int) (value - other.value);
      }
    }

    long[] dist = new long[n];
    Arrays.fill(dist, INF);
    dist[s] = 0;

    markAllNodesAsUnvisited();
    Edge[] prev = new Edge[n];

    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(s, 0));

    // Run Dijkstra's to find augmenting path.
    while (!pq.isEmpty()) {
      Node node = pq.poll();
      visit(node.id);
      if (dist[node.id] < node.value) continue;
      List<Edge> edges = graph[node.id];
      for (int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);
        if (visited(edge.to)) continue;
        long newDist = dist[edge.from] + edge.cost;
        if (edge.remainingCapacity() > 0 && newDist < dist[edge.to]) {
          prev[edge.to] = edge;
          dist[edge.to] = newDist;
          pq.offer(new Node(edge.to, dist[edge.to]));
        }
      }
    }

    LinkedList<Edge> path = new LinkedList<>();
    if (dist[t] == INF) return path;

    adjustEdgeCosts(dist);

    for (Edge edge = prev[t]; edge != null; edge = prev[edge.from]) path.addFirst(edge);
    return path;
  }
}
