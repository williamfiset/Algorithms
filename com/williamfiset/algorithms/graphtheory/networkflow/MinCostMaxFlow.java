/**
 * Min cost max flow implementation using Johnson's algorithm (initial Bellman-
 * Ford + subsequent Dijkstra runs) as a method of finding augmenting paths.
 * 
 * Tests against:
 * - https://open.kattis.com/problems/mincostmaxflow
 *
 * Time Complexity: O(E²Vlog(V))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

package com.williamfiset.algorithms.graphtheory.networkflow;

import java.util.*;

public class class MinCostMaxFlowSolver {

  private static final long INF = 987654321;

  private static class Edge {
    Edge residual;
    int from, to;
    long flow, capacity, cost;
    final long originalCapacity, originalCost;
    public Edge(int from, int to, long capacity, long cost) {
      this.to = to; 
      this.from = from;
      this.originalCost = this.cost = cost;
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
  public MinCostMaxFlowSolver(int n, int source, int sink) {
    this.n = n;
    initializeGraph();
    this.source = source;
    this.sink = sink;
  }

  // Construct an empty graph with n nodes.
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
    Edge e2 = new Edge(to, from, 0, -cost);
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

  private void init() {
    maxFlow = minCost = 0;

    long[] dist = new long[n];
    java.util.Arrays.fill(dist, INF);
    dist[source] = 0;

    // Run Bellman-Ford algorithm to get the optimal distance to each node, O(VE)
    for (int i = 0; i < n-1; i++)
      for(List<Edge> edges : graph)
        for (Edge edge : edges)
          if (edge.capacity > 0 && dist[edge.from] + edge.cost < dist[edge.to])
            dist[edge.to] = dist[edge.from] + edge.cost;

    adjustEdgeCosts(dist);
  }

  // Adjust edge costs to be non-negative for Dijkstra's algorithm, O(E)
  private void adjustEdgeCosts(long[] dist) {
    for (int from = 0; from < n; from++) {
      for (Edge edge : graph.get(from)) {
        if (edge.capacity > 0) {
          edge.cost += dist[from] - dist[edge.to];
        } else {
          edge.cost = 0;
        }
      }
    }
  }

  public void solve() {
    if (solved) return;

    init();

    // Sum up the bottlenecks on each augmenting path to find the max flow.
    for(long flow = findBottleNeck(); flow != 0; flow = findBottleNeck())
      maxFlow += flow;

    // Compute the min cost.
    for (List<Edge> edges : graph)
      for (Edge edge : edges)
        minCost += edge.flow * edge.originalCost;

    solved = true;
  }

  private long findBottleNeck() {
    List<Edge> path = getAugmentingPath();
    
    // Sink not reachable!
    if (path.isEmpty()) return 0;

    // Find bottle neck edge value along path.
    long bottleNeck = Long.MAX_VALUE;
    for(Edge edge : path) bottleNeck = Math.min(bottleNeck, edge.capacity);
    
    // Retrace path and update edge capacities.
    for(Edge edge : path) {
      Edge res = edge.residual;
      edge.flow += bottleNeck;
      edge.capacity -= bottleNeck;
      res.capacity  += bottleNeck;
    }

    return bottleNeck;
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
        return (int)(value - other.value);
      }
    }

    long[] dist = new long[n];
    java.util.Arrays.fill(dist, INF);
    dist[source] = 0;

    boolean[] visited = new boolean[n];
    Edge[] prev = new Edge[n];

    PriorityQueue<Node> pq = new PriorityQueue<>();
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
        long newDist = dist[edge.from] + edge.cost;
        if (edge.capacity > 0 && newDist < dist[edge.to]) {
          prev[edge.to] = edge;
          dist[edge.to] = newDist;
          pq.offer(new Node(edge.to, dist[edge.to]));
        }
      }
    }

    LinkedList<Edge> path = new LinkedList<>();
    if (dist[sink] == INF) return path;

    adjustEdgeCosts(dist);

    for(Edge edge = prev[sink]; edge != null; edge = prev[edge.from])
      path.addFirst(edge);
    return path;
  }

}
