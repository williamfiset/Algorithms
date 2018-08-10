/**
 * Min Cost Max Flow algorithm implemented with Bellman-Ford as a means of 
 * finding augmenting paths to support negative edge weights. 
 *
 * Time Complexity: O(E²V²)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.graphtheory.networkflow;

import static java.lang.Math.min;

import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class MinCostMaxFlowWithBellmanFord extends NetworkFlowSolverBase {

  /**
   * Creates a min-cost maximum flow network solver. To construct the flow 
   * network use the {@link NetworkFlowSolverBase#addEdge(int, int, int, int)} 
   * method to add edges to the graph.
   *
   * @param n - The number of nodes in the graph including source and sink nodes.
   * @param s - The index of the source node, 0 <= source < n
   * @param t - The index of the source node, 0 <= sink < n
   */
  public MinCostMaxFlowWithBellmanFord(int n, int s, int t) {
    super(n, s, t);
  }

  @Override
  public void solve() {
    // Sum up the bottlenecks on each augmenting path to find the max flow.
    for(long flow = findBottleNeck(); flow != 0; flow = findBottleNeck())
      maxFlow += flow;

    // Compute the min cost.
    for (List<Edge> edges : graph)
      for (Edge edge : edges)
        minCost += edge.flow * edge.cost;

    // TODO(williamfiset): Compute mincut.
  }

  private long findBottleNeck() {
    List<Edge> path = getAugmentingPath();

    // Sink not reachable!
    if (path.isEmpty()) return 0;

    // Find bottle neck edge value along path.
    long bottleNeck = Long.MAX_VALUE;
    for(Edge edge : path) 
      bottleNeck = min(bottleNeck, edge.capacity);

    // Retrace path and update edge capacities.
    for(Edge edge : path) {
      Edge res = edge.residual;
      edge.flow += bottleNeck;
      edge.capacity -= bottleNeck;
      res.capacity += bottleNeck;
    }

    return bottleNeck;
  }

  /**
   * Use the Bellman-Ford algorithm (which work with negative edge weights) to 
   * find an augmenting path through the flow network.
   */
  private List<Edge> getAugmentingPath() {
    long[] dist = new long[n];
    Arrays.fill(dist, INF);
    dist[s] = 0;

    Edge[] prev = new Edge[n];

    // For each vertex, relax all the edges in the graph, O(VE)
    for (int i = 0; i < n-1; i++) {
      for(int from = 0; from < n; from++) {
        for (Edge edge : graph[from]) {
          if (edge.capacity > 0 && dist[from] + edge.cost < dist[edge.to]) {
            dist[edge.to] = dist[from] + edge.cost;
            prev[edge.to] = edge;
          }
        }
      }
    }

    // Retrace augmenting path from sink back to the source.
    LinkedList<Edge> path = new LinkedList<>();
    for(Edge edge = prev[t]; edge != null; edge = prev[edge.from])
      path.addFirst(edge);
    return path;
  }

    /* Example usage. */

  public static void main(String[] args) {
    testSmallNetwork();
  }

  private static void testSmallNetwork() {
    int n = 4;
    int s = n;
    int t = n+1;
    MinCostMaxFlowWithBellmanFord solver;
    solver = new MinCostMaxFlowWithBellmanFord(n+2, s, t);

    solver.addEdge(s, 1, 4, 10);
    solver.addEdge(s, 2, 2, 30);
    solver.addEdge(1, 2, 2, 10);
    solver.addEdge(1, t, 0, 9999);
    solver.addEdge(2, t, 4, 10);

    // Prints: Max flow: 4, Min cost: 140
    System.out.printf("Max flow: %d, Min cost: %d\n", solver.getMaxFlow(), solver.getMinCost());
  }

}


