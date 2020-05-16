package com.williamfiset.algorithms.graphtheory;

import java.util.*;

/**
 * An A* algorithm implementation for a adjacency list.
 *
 * @see DijkstrasShortestPathAdjacencyList
 * @author Nishant Chatterjee, nishatc1527@gmail.com
 */
public class AStar_GridHeuristic {

  public static class Pair<A, B> {

    public final A fst;
    public final B snd;

    public Pair(A fst, B snd) {
      this.fst = fst;
      this.snd = snd;
    }

    public String toString() {
      return "Pair[" + fst + "," + snd + "]";
    }

    public boolean equals(Object other) {
      return other instanceof Pair<?, ?>
          && Objects.equals(fst, ((Pair<?, ?>) other).fst)
          && Objects.equals(snd, ((Pair<?, ?>) other).snd);
    }

    public int hashCode() {
      if (fst == null) return (snd == null) ? 0 : snd.hashCode() + 1;
      else if (snd == null) return fst.hashCode() + 2;
      else return fst.hashCode() * 17 + snd.hashCode();
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
      return new Pair<>(a, b);
    }
  }

  // Create a graph with V vertices
  @SuppressWarnings("unchecked")
  public static List<Edge>[] createGraph(final int V) {
    List<Edge>[] graph = new List[V];
    for (int i = 0; i < V; i++) graph[i] = new ArrayList<>();
    return graph;
  }

  // Helper function to add an edge to the graph
  public static void addEdge(List<Edge>[] graph, int from, int to, int cost) {
    graph[from].add(new Edge(to, cost));
  }

  /**
   * The main A* algorithm.
   *
   * @param graph The graph containing the vertices and adjacency list.
   * @param start The starting vertex.
   * @param end The target vertex.
   * @return The smallest distance with the weights from the start to end.
   */
  public static long aStar(List<Edge>[] graph, int start, int end) {
    final int V = graph.length;
    int[] distances = new int[V];
    int[] heuristics = heuristics(graph, end);
    boolean[] visited = new boolean[V];
    int visitedCount = 0;
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[start] = 0;

    while (visitedCount < V) {
      int nextMin = chooseNext(heuristics, distances, visited);
      visitedCount++;

      distances[nextMin] = distances[nextMin];
      if (nextMin == end) {
        return distances[nextMin];
      }

      List<Edge> neighbors = graph[nextMin];

      for (Edge edge : neighbors) {
        if (distances[nextMin] + edge.weight < distances[edge.to]) {
          distances[edge.to] = distances[nextMin] + edge.weight;
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  /**
   * Chooses the next smallest vertex according to heuristic values and the smallest distance
   * calculated so far.
   *
   * @param heuristics The heuristic values.
   * @param distances The smallest distances found so far.
   * @param visited The vertices already visited.
   * @return The next smallest vertex.
   */
  private static int chooseNext(int[] heuristics, int[] distances, boolean[] visited) {
    final int V = distances.length;
    long minVal = Integer.MAX_VALUE;
    int min = -1;
    for (int i = 0; i < V; i++) {
      if (visited[i]) {
        continue;
      }

      long nextVal = Math.abs((long) heuristics[i] + (long) distances[i]);

      if (nextVal < minVal) {
        minVal = nextVal;
        min = i;
      }
    }

    visited[min] = true;
    return min;
  }

  /**
   * Fills the heuristic values and puts them in an array. The heuristic value of a vertex is
   * calculated by the distance from that vertex to the ending vertex, which can easily be done
   * using breadth first search. Note that you can use whatever technique to fill the heuristic
   * values.
   *
   * @param graph The graph that contains the vertices.
   * @param end The end vertex.
   * @return An array resembling the heuristic value of each vertex.
   */
  private static int[] heuristics(List<Edge>[] graph, int end) {
    final int V = graph.length;
    int[] heuristics = new int[V];

    for (int i = 0; i < graph.length; i++) {
      heuristics[i] =
          minDist(
              graph, i,
              end); // Simply fill each heuristic with the minimum distance from source to target.
    }

    return heuristics;
  }

  /**
   * Calculates the shortest path from a start vertex to an end vertex.
   *
   * @param graph The graph containing the vertices.
   * @param start The start vertex.
   * @param end The ending vertex.
   * @return The smallest path from the start to end vertex.
   */
  private static int minDist(List<Edge>[] graph, int start, int end) {
    Queue<Pair<Integer, Integer>> queue =
        new LinkedList<>(); // A queue where each entry is the vertex and the depth of the vertex.
    queue.offer(Pair.of(start, 0));
    boolean[] visited = new boolean[graph.length]; // To check if the vertex is visited.
    visited[start] = true;

    while (!queue.isEmpty()) {
      Pair<Integer, Integer> next = queue.poll();
      int nextVertex = next.fst;
      int nextDepth = next.snd;

      if (nextVertex == end) { // Reached target
        return nextDepth;
      }

      List<Edge> neighbors = graph[nextVertex];

      for (Edge edge : neighbors) {
        if (!visited[edge.to]) {
          queue.offer(Pair.of(edge.to, nextDepth + 1)); // Add every neighbor that wasn't visited.
        }
      }
    }

    return Integer.MAX_VALUE;
  }

  public static void main(String[] args) {
    List<Edge>[] graph = createGraph(5);

    // 0 -> 1: 6
    // 1 -> 2: 5
    // 3 -> 2: 3
    // 0 -> 4: 1
    // 4 -> 1: 1
    // 4 -> 3: 2

    // 0 -> 1 ↘
    // ⬇ ↗     2
    // 4 -> 3 ↗

    addEdge(graph, 0, 1, 6);
    addEdge(graph, 1, 2, 5);
    addEdge(graph, 3, 2, 3);
    addEdge(graph, 0, 4, 1);
    addEdge(graph, 4, 1, 1);
    addEdge(graph, 4, 3, 2);

    System.out.println(
        "The smallest distance from vertex 0 to vertex 1 is "
            + aStar(graph, 0, 1)); // Excepted output is 2.
  }

  /** An edge in the graph. */
  public static class Edge {
    /** The weight of this edge. */
    int weight;
    /** The ending vertex of the edge. */
    int to;

    /**
     * Creates an edge with a from vertex, to vertex, and a weight.
     *
     * @param to The to vertex.
     * @param weight The weight of the edge.
     */
    public Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
  }
}
