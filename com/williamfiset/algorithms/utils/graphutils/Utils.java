/**
 * A set of common graph theory functions.
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/
package com.williamfiset.algorithms.utils.graphutils;

import java.util.ArrayList;
import java.util.List;

public final class Utils {
  
  public static List<List<Integer>> createEmptyAdjacencyList(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for(int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    addDirectedEdge(graph, from, to);
    addDirectedEdge(graph, to, from);
  }

}