package com.williamfiset.algorithms.dp;

import java.util.*;

/**
 * Dynamic Programming on Directed Acyclic Graphs (DAG).
 *
 * <p>
 * This implementation demonstrates how to apply dynamic programming on a DAG
 * using a topological ordering (Kahn's algorithm).
 *
 * <p>
 * Example use-case: counting the number of ways to reach each node from a
 * source.
 *
 * <p>
 * Time Complexity: O(V + E)
 * Space Complexity: O(V + E)
 */
public class DagDynamicProgramming {

  // Minimal edge representation (only what is needed)
  public static class Edge {
    int to;

    public Edge(int to) {
      this.to = to;
    }
  }

  /**
   * Performs topological sorting using Kahn's algorithm.
   */
  public static int[] kahnTopoSort(Map<Integer, List<Edge>> graph, int numNodes) {

    int[] indegree = new int[numNodes];

    // Compute indegree
    for (int u = 0; u < numNodes; u++) {
      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        indegree[edge.to]++;
      }
    }

    Queue<Integer> q = new ArrayDeque<>();
    for (int i = 0; i < numNodes; i++) {
      if (indegree[i] == 0)
        q.add(i);
    }

    int[] topo = new int[numNodes];
    int index = 0;

    while (!q.isEmpty()) {
      int u = q.poll();
      topo[index++] = u;

      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        if (--indegree[edge.to] == 0) {
          q.add(edge.to);
        }
      }
    }

    // Cycle detection
    if (index != numNodes)
      return new int[0];

    return topo;
  }

  /**
   * Counts number of ways to reach each node from a source in a DAG.
   */
  public static long[] countWaysDAG(
      Map<Integer, List<Edge>> graph, int source, int numNodes) {

    int[] topo = kahnTopoSort(graph, numNodes);
    if (topo.length == 0)
      return null;

    long[] dp = new long[numNodes];
    dp[source] = 1;

    for (int u : topo) {
      if (dp[u] == 0L)
        continue;

      for (Edge edge : graph.getOrDefault(u, Collections.emptyList())) {
        dp[edge.to] += dp[u];
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
    graph.get(0).add(new Edge(1));
    graph.get(0).add(new Edge(2));
    graph.get(1).add(new Edge(3));
    graph.get(2).add(new Edge(3));
    graph.get(3).add(new Edge(4));

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