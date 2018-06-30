/**
 * Min Cost Max Flow algorithm implemented with Dijkstra's algorithm as a means 
 * of finding augmenting paths. This approach does not support negative edge 
 * weights in the flow graph.
 *
 * Time Complexity: O(E²Vlog(V))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.util.Collections.*;
import static java.lang.System.*;
import static java.util.Arrays.*;
import static java.lang.Math.*;
import java.awt.geom.*;
import java.math.*;
import java.util.*;
import java.io.*;

public class MinCostMaxFlow {

  private static class Edge {
    Edge residual;
    int from, to, capacity, cost;
    final int originalCapacity;
    public Edge(int from, int to, int capacity, int cost) {
      this.to = to; 
      this.from = from;
      this.cost = cost;
      this.originalCapacity = this.capacity = capacity;
    }
  }

  // Inputs
  private int n, source, sink;

  // Internal
  private boolean solved;

  // Outputs
  private long maxFlow;
  private long minCost;
  private List<List<Edge>> graph;

  /**
   * Creates an instance of a flow network solver. Use the 
   * {@link #addEdge(int, int, int, int)} method to add edges to the graph.
   *
   * @param n      - The number of nodes in the graph including source and sink nodes.
   * @param source - The index of the source node, 0 <= source < n
   * @param sink   - The index of the source node, 0 <= sink < n
   */
  public MinCostMaxFlow(int n, int source, int sink) {
    this.n = n;
    this.source = source;
    this.sink = sink;
    initializeGraph();
  }

  // Construct an empty graph with n nodes including the source and sink nodes.
  private void initializeGraph() {
    graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
  }

  /**
   * Adds a directed edge (and residual edge) to the flow graph.
   *
   * @param from     - The index of the node the directed edge starts at.
   * @param to       - The index of the node the directed edge end at.
   * @param capacity - The number of units of capacity for this edge.
   * @param cost     - The cost of using up 1 unit of capacity along this edge.
   */
  public void addEdge(int from, int to, int capacity, int cost) {
    Edge e1 = new Edge(from, to, capacity, cost);
    Edge e2 = new Edge(to, from, 0, 0);
    e1.residual = e2;
    e2.residual = e1;
    graph.get(from).add(e1);
    graph.get(to).add(e2);
  }

  /**
   * Returns the graph after the solver has been executed. This allow you to
   * inspect each edge's remaining {@link Edge#capacity} compared to the
   * {@link Edge.originalCapacity} value. This is useful if you want to figure 
   * out which edges were used during the max flow.
   */
  public List<List<Edge>> getGraph() {
    solve();
    return graph;
  }

  // Returns the maximum flow from the source to the sink.
  public long getMaxFlow() {
    solve();
    return maxFlow;
  }

  // Returns the minimum cost that achieves the maximum flow.
  public long getMinCost() {
    solve();
    return minCost;
  }

  public void solve() {
    if (solved) return;

    maxFlow = minCost = 0;

    // Sum up the bottlenecks on each augmenting path to find the max flow.
    for(int flow = findBottleNeck(); flow != 0; flow = findBottleNeck())
      maxFlow += flow;

    // Compute the min cost.
    for (List<Edge> edges : graph) {
      for (Edge edge : edges) {
        int consumedCapacity = (edge.originalCapacity - edge.capacity);
        minCost += consumedCapacity * edge.cost;
      }
    }

    solved = true;
  }

  // Priority Queue comparator used for Dijkstra's
  private Comparator<Node> comparator = new Comparator<Node>() {
    @Override
    public int compare(Node node1, Node node2) {
      if (Math.abs(node1.value-node2.value) < 0.000001) return 0;
      return (node1.value - node2.value) > 0 ? +1 : -1;
    }
  };

  // Node class used for Dijkstra's
  private static class Node {
    int id;
    double value;
    public Node(int id, double value) {
      this.id = id; 
      this.value = value;
    }
  }

  // Use Dijkstra's algorithm to find an augmenting path through the flow network.
  private List<Edge> getAugmentingPath() {
    double[] dist = new double[n];
    Arrays.fill(dist, Double.POSITIVE_INFINITY);
    dist[source] = 0;

    boolean[] visited = new boolean[n];
    Edge[] prev = new Edge[n];

    PriorityQueue<Node> pq = new PriorityQueue<>(2*n, comparator);
    pq.offer(new Node(source, 0));

    // Run Dijkstra's to find augmenting path.
    while(!pq.isEmpty()) {
      Node node = pq.poll();
      visited[node.id] = true;
      if (dist[node.id] < node.value) continue;
      List<Edge> edges = graph.get(node.id);
      for(int i = 0; i < edges.size(); i++) {
        Edge edge = edges.get(i);
        if (visited[edge.to]) continue;
        double newDist = dist[edge.from] + edge.cost;
        if (edge.capacity > 0 && newDist < dist[edge.to]) {
          prev[edge.to] = edge;
          dist[edge.to] = newDist;
          pq.offer(new Node(edge.to, dist[edge.to]));
        }
      }
      if (node.id == sink) break;
    }

    // Retrace augmenting path from sink back to the source.
    LinkedList<Edge> path = new LinkedList<>();
    if (dist[sink] == Double.POSITIVE_INFINITY) return path;
    for(Edge edge = prev[sink]; edge != null; edge = prev[edge.from])
      path.addFirst(edge);
    return path;
  }

  private int findBottleNeck() {
    List<Edge> path = getAugmentingPath();
    
    // Sink not reachable!
    if (path.isEmpty()) return 0;

    // Find bottle neck edge value along path.
    int bottleNeck = Integer.MAX_VALUE;
    for(Edge edge : path) bottleNeck = Math.min(bottleNeck, edge.capacity);
    
    // Retrace path and update edge capacities.
    for(Edge edge : path) {
      Edge res = edge.residual;
      edge.capacity -= bottleNeck;
      res.capacity  += bottleNeck;
    }

    return bottleNeck;
  }
}
