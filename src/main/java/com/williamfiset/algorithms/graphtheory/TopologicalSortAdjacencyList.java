/**
 * Topological sort implementation using DFS on an adjacency list of a Directed Acyclic Graph (DAG).
 * Returns an array with the node indexes in a (non-unique) topological order: for every directed
 * edge u -> v, u comes before v in the ordering.
 *
 * <p>Also includes a method to find shortest paths in a DAG using the topological ordering.
 *
 * <p>Time: O(V + E)
 *
 * <p>Space: O(V + E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class TopologicalSortAdjacencyList {

  static class Edge {
    int from, to, weight;

    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  /**
   * Finds a topological ordering of the nodes in a DAG.
   *
   * <p>NOTE: {@code numNodes} is not necessarily the number of nodes in the adjacency list since
   * singleton nodes with no edges wouldn't be present but are still part of the graph.
   *
   * @param graph adjacency list mapping node index to outgoing edges.
   * @param numNodes total number of nodes in the graph.
   * @return array of node indexes in topological order.
   */
  public static int[] topologicalSort(Map<Integer, List<Edge>> graph, int numNodes) {
    int[] ordering = new int[numNodes];
    boolean[] visited = new boolean[numNodes];

    int i = numNodes - 1;
    for (int at = 0; at < numNodes; at++)
      if (!visited[at])
        i = dfs(i, at, visited, ordering, graph);

    return ordering;
  }

  // Places nodes into the ordering array in reverse DFS post-order.
  private static int dfs(
      int i, int at, boolean[] visited, int[] ordering, Map<Integer, List<Edge>> graph) {
    visited[at] = true;

    List<Edge> edges = graph.get(at);
    if (edges != null)
      for (Edge edge : edges)
        if (!visited[edge.to])
          i = dfs(i, edge.to, visited, ordering, graph);

    ordering[i] = at;
    return i - 1;
  }

  /**
   * Finds the shortest path from {@code start} to all other reachable nodes in a DAG.
   *
   * <p>NOTE: {@code numNodes} is not necessarily the number of nodes in the adjacency list since
   * singleton nodes with no edges wouldn't be present but are still part of the graph.
   *
   * @param graph adjacency list mapping node index to outgoing edges.
   * @param start the source node.
   * @param numNodes total number of nodes in the graph.
   * @return array of shortest distances, null for unreachable nodes.
   */
  public static Integer[] dagShortestPath(
      Map<Integer, List<Edge>> graph, int start, int numNodes) {
    int[] topsort = topologicalSort(graph, numNodes);
    Integer[] dist = new Integer[numNodes];
    dist[start] = 0;

    for (int i = 0; i < numNodes; i++) {
      int nodeIndex = topsort[i];
      if (dist[nodeIndex] != null) {
        List<Edge> adjacentEdges = graph.get(nodeIndex);
        if (adjacentEdges != null) {
          for (Edge edge : adjacentEdges) {
            int newDist = dist[nodeIndex] + edge.weight;
            if (dist[edge.to] == null)
              dist[edge.to] = newDist;
            else
              dist[edge.to] = Math.min(dist[edge.to], newDist);
          }
        }
      }
    }

    return dist;
  }

  public static void main(String[] args) {
    final int N = 7;
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < N; i++)
      graph.put(i, new ArrayList<>());

    graph.get(0).add(new Edge(0, 1, 3));
    graph.get(0).add(new Edge(0, 2, 2));
    graph.get(0).add(new Edge(0, 5, 3));
    graph.get(1).add(new Edge(1, 3, 1));
    graph.get(1).add(new Edge(1, 2, 6));
    graph.get(2).add(new Edge(2, 3, 1));
    graph.get(2).add(new Edge(2, 4, 10));
    graph.get(3).add(new Edge(3, 4, 5));
    graph.get(5).add(new Edge(5, 4, 7));

    int[] ordering = topologicalSort(graph, N);
    System.out.println(Arrays.toString(ordering));

    Integer[] dists = dagShortestPath(graph, 0, N);
    System.out.println(dists[4]); // 8
    System.out.println(dists[6]); // null (unreachable)
  }
}
