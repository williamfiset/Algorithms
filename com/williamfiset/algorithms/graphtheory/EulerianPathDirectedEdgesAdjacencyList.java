/**
 * NOTE: This file is still under development!
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

// A directed graph has an Eulerian trail if and only if at most one vertex has
// (out-degree) − (in-degree) = 1, at most one vertex has (in-degree) − 
// (out-degree) = 1, every other vertex has equal in-degree and out-degree, 
// and all of its vertices with nonzero degree belong to a single connected 
// component of the underlying undirected graph.

public class EulerianPathDirectedEdgesAdjacencyList {
  
  public int n, start, end, orderIndex;
  public int[] indexes, in, out, ordering;
  public List<List<Integer>> graph;

  public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    n = graph.size();
    this.graph = graph;
  }

  // Assume that all belong to on connected component.
  public int[] getEulerianPath() {

    // Tracks in degrees and out degrees for each node.
    in  = new int[n];
    out = new int[n];

    int edgeCount, endNodes, startNodes;
    start = end = edgeCount = endNodes = startNodes = 0;

    for (int from = 0; from < n; from++) {
      List<Integer> edges = graph.get(from);
      for (int i = 0; i < edges.size(); i++) {
        int to = edges.get(i);
        in[to]++;
        out[from]++;
        edgeCount++;
      }
    }

    for (int i = 0; i < n; i++) {
      if (in[i] - out[i] == 1) {
        end = i;
        endNodes++;
      } else if (out[i] - in[i] == 1) {
        start = i;
        startNodes++;
      } else if (in[i] != out[i]) {
        return null;
      }
    }
    
    // Graph is does not contain Eulerian Path. Too many start/end nodes.
    if (endNodes > 1 || startNodes > 1)
      return null;
    
    orderIndex = edgeCount;
    ordering = new int[edgeCount+1];
    indexes = new int[n];

    dfs(start);
    return ordering;
  }

  private void dfs(int at) {
    while(out[at] != 0) {
      out[at]--;  
      int next = graph.get(at).get(indexes[at]);
      indexes[at]++;
      dfs(next);
    }
    ordering[orderIndex--] = at;
  }

  public static void main(String[] args) {
    testSimplePath();
    // testMoreComplexPath();
  }

  public static List<List<Integer>> initializeEmptyGraph(int N) {
    List<List<Integer>> graph = new ArrayList<>(N);
    for (int i = 0; i < N; i++) 
      graph.add(new ArrayList<>());
    return graph;
  }

  public static void addDirectedEdge(List<List<Integer>> g, int from, int to) {
    g.get(from).add(to);
  }

  /* TESTING */

  public static void testSimplePath() {

    int n = 5;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    graph.get(0).add(1);
    graph.get(1).add(2);
    graph.get(1).add(3);
    graph.get(1).add(4);
    graph.get(2).add(1);
    graph.get(4).add(1);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // [0, 1, 2, 1, 4, 1, 3]
    solver.getEulerianPath();
  }


}












