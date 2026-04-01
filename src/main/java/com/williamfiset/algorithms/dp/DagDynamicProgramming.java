package com.williamfiset.algorithms.dp;

import java.util.*;

/**
 * Dynamic Programming on Directed Acyclic Graphs (DAG).
 *
 * <p>This implementation demonstrates how to apply dynamic programming on a DAG
 * using a topological ordering (Kahn's algorithm).
 *
 * <p>Although DAGs are graph structures, this implementation emphasizes the DP
 * formulation where each state depends on previously computed states in a
 * linearized order.
 *
 * <p>Example use-case: counting the number of ways to reach each node from a source.
 *
 * <p>Time Complexity: O(V + E)
 * <p>Space Complexity: O(V + E)
 */
public class DagDynamicProgramming {

  private static final long MOD = 1_000_000_007;

  // Make public so test classes can access it
  public static class Edge {
    int from, to, weight;

    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  /**
   * Performs topological sorting using Kahn's algorithm.
   *
   * @param graph adjacency list
   * @param numNodes number of nodes
   * @return topological ordering or empty array if cycle exists
   */
  public static int[] kahnTopoSort(Map<Integer, List<Edge>> graph, int numNodes) {

    int[] indegree = new int[numNodes];

    // Compute indegree of each node
    for (int u = 0; u < numNodes; u++) {
      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        indegree[edge.to]++;
      }
    }

    Queue<Integer> q = new ArrayDeque<>();
    for (int i = 0; i < numNodes; i++) {
      if (indegree[i] == 0) q.add(i);
    }

    int[] topo = new int[numNodes];
    int index = 0;

    while (!q.isEmpty()) {
      int u = q.poll();
      topo[index++] = u;

      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        if (--indegree[edge.to] == 0) q.add(edge.to);
      }
    }

    // Cycle detection
    if (index != numNodes) return new int[0]; // Graph contains a cycle

    return topo;
  }

  /**
   * Counts number of ways to reach each node from a source in a DAG.
   *
   * @param graph adjacency list
   * @param source starting node
   * @param numNodes number of nodes
   * @return dp array where dp[i] = number of ways to reach node i,
   *         or null if the graph contains a cycle
   */
  public static long[] countWaysDAG(
      Map<Integer, List<Edge>> graph, int source, int numNodes) {

    int[] topo = kahnTopoSort(graph, numNodes);
    if (topo.length == 0) return null; // Graph contains a cycle

    long[] dp = new long[numNodes];
    dp[source] = 1;

    for (int u : topo) {
      if (dp[u] == 0L) continue;

      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        dp[edge.to] = (dp[edge.to] + dp[u]) % MOD;
      }
    }

    return dp;
  }

  public static void main(String[] args) {

    final int N = 6;
    Map<Integer, List<Edge>> graph = new HashMap<>();

    for (int i = 0; i < N; i++) {
      graph.put(i, new ArrayList<>());
    }

    // Example DAG
    graph.get(0).add(new Edge(0, 1, 1));
    graph.get(0).add(new Edge(0, 2, 1));
    graph.get(1).add(new Edge(1, 3, 1));
    graph.get(2).add(new Edge(2, 3, 1));
    graph.get(3).add(new Edge(3, 4, 1));

    int source = 0;

    long[] dp = countWaysDAG(graph, source, N);

    if (dp == null) {
      System.out.println("Graph contains a cycle!");
      return;
    }

    System.out.println("Ways from source:");
    System.out.println(Arrays.toString(dp));
  }
}