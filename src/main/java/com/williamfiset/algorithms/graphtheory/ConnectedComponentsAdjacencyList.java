/**
 * This file contains an algorithm to find all the connected components of an undirected graph. If
 * the graph you're dealing with is directed have a look at Tarjan's algorithm to find strongly
 * connected components.
 *
 * <p>The approach I will use to find all the strongly connected components is to use a union find
 * data structure to merge together nodes connected by an edge. An alternative approach would be to
 * do a breadth first search from each node (except the ones already visited of course) to determine
 * the individual components.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class ConnectedComponentsAdjacencyList {

  static class Edge {
    int from, to, cost;

    public Edge(int from, int to, int cost) {
      this.from = from;
      this.to = to;
      this.cost = cost;
    }
  }

  static int countConnectedComponents(Map<Integer, List<Edge>> graph, int n) {

    UnionFind uf = new UnionFind(n);

    for (int i = 0; i < n; i++) {
      List<Edge> edges = graph.get(i);
      if (edges != null) {
        for (Edge edge : edges) {
          uf.unify(edge.from, edge.to);
        }
      }
    }

    return uf.components();
  }

  // Finding connected components example
  public static void main(String[] args) {

    final int numNodes = 7;
    Map<Integer, List<Edge>> graph = new HashMap<>();

    // Setup a graph with four connected components
    // namely: {0,1,2}, {3,4}, {5}, {6}
    addUndirectedEdge(graph, 0, 1, 1);
    addUndirectedEdge(graph, 1, 2, 1);
    addUndirectedEdge(graph, 2, 0, 1);
    addUndirectedEdge(graph, 3, 4, 1);
    addUndirectedEdge(graph, 5, 5, 1); // Self loop

    int components = countConnectedComponents(graph, numNodes);
    System.out.printf("Number of components: %d\n", components);
  }

  // Helper method to setup graph
  private static void addUndirectedEdge(
      Map<Integer, List<Edge>> graph, int from, int to, int cost) {
    List<Edge> list = graph.get(from);
    if (list == null) {
      list = new ArrayList<Edge>();
      graph.put(from, list);
    }
    list.add(new Edge(from, to, cost));
    list.add(new Edge(to, from, cost));
  }
}
