/**
 * Given a graph as a adjacency list this file shows you how to find the diameter/radius of the
 * graph.
 *
 * <p>Time Complexity: O(V(V + E)) = O(V^2 + VE))= O(VE)
 *
 * <p>NOTE: This file could use some tests.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class GraphDiameter {

  static class Edge {
    int from, to;

    public Edge(int from, int to) {
      this.from = from;
      this.to = to;
    }
  }

  // Separate each breadth first search layer with a DEPTH_TOKEN
  // to easily determine the distance to other nodes
  static final int DEPTH_TOKEN = -1;

  static Integer VISITED_TOKEN = 0;
  static Map<Integer, Integer> visited = new HashMap<>();
  static ArrayDeque<Integer> queue = new ArrayDeque<>();

  // Compute the eccentricity from a given node. The eccentricity
  // is the distance to the furthest node(s).
  private static int eccentricity(int nodeID, Map<Integer, List<Edge>> graph) {

    VISITED_TOKEN++;

    queue.offer(nodeID);
    queue.offer(DEPTH_TOKEN);
    visited.put(nodeID, VISITED_TOKEN);

    int depth = 0;

    // Do BFS to count the
    while (true) {

      Integer id = queue.poll();

      // If we encounter a depth token this means that we
      // have finished the current frontier and are about
      // to start the new layer (some of which may already
      // be in the queue) or have reached the end.
      if (id == DEPTH_TOKEN) {

        // No more nodes to process
        if (queue.isEmpty()) break;

        // Add another DEPTH_TOKEN
        queue.offer(DEPTH_TOKEN);

        // Increase the max depth for each DEPTH_TOKEN seen
        depth++;

      } else {

        List<Edge> edges = graph.get(id);
        if (edges != null) {
          for (Edge edge : edges) {
            if (visited.get(edge.to) != VISITED_TOKEN) {
              visited.put(edge.to, VISITED_TOKEN);
              queue.offer(edge.to);
            }
          }
        }
      }
    }

    return depth;
  }

  // Compute the diameter of an arbitrary graph
  // NOTE: The input graph should be undirected
  public static int graphDiameter(Map<Integer, List<Edge>> graph) {

    if (graph == null) return 0;

    int diameter = 0;
    int radius = Integer.MAX_VALUE;

    // The diameter of a graph is the maximum of all the eccentricity values from all nodes.
    // The radius on the other hand is the minimum of all the eccentricity values from all nodes.
    for (Integer nodeID : graph.keySet()) {
      int eccentricity = eccentricity(nodeID, graph);
      diameter = Math.max(diameter, eccentricity);
      radius = Math.min(radius, eccentricity);
    }

    // return radius;
    return diameter;
  }

  // Example usage of how to compute the diameter of a graph
  public static void main(String[] args) {

    Map<Integer, List<Edge>> graph = createGraph(5);
    addUndirectedEdge(graph, 4, 2);
    addUndirectedEdge(graph, 2, 0);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 1, 2);
    addUndirectedEdge(graph, 1, 3);

    int diameter = graphDiameter(graph);
    if (diameter != 3) System.out.println("Wrong diameter!");

    // No edges
    graph = createGraph(5);
    diameter = graphDiameter(graph);
    if (diameter != 0) System.out.println("Wrong diameter!");

    graph = createGraph(8);
    addUndirectedEdge(graph, 0, 5);
    addUndirectedEdge(graph, 1, 5);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 3, 5);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 6, 5);
    addUndirectedEdge(graph, 7, 5);
    diameter = graphDiameter(graph);
    if (diameter != 2) System.out.println("Wrong diameter!");

    graph = createGraph(9);
    addUndirectedEdge(graph, 0, 5);
    addUndirectedEdge(graph, 1, 5);
    addUndirectedEdge(graph, 2, 5);
    addUndirectedEdge(graph, 3, 5);
    addUndirectedEdge(graph, 4, 5);
    addUndirectedEdge(graph, 6, 5);
    addUndirectedEdge(graph, 7, 5);
    addUndirectedEdge(graph, 3, 8);
    diameter = graphDiameter(graph);
    if (diameter != 3) System.out.println("Wrong diameter!");
  }

  private static Map<Integer, List<Edge>> createGraph(int numNodes) {
    Map<Integer, List<Edge>> graph = new HashMap<>();
    for (int i = 0; i < numNodes; i++) graph.put(i, new ArrayList<Edge>());
    return graph;
  }

  private static void addUndirectedEdge(Map<Integer, List<Edge>> graph, int from, int to) {
    graph.get(from).add(new Edge(from, to));
    graph.get(to).add(new Edge(to, from));
  }
}
