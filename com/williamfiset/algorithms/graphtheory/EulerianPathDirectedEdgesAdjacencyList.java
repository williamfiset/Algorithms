/**
 * NOTE: This file is still under development!
 *
 * A directed graph has an Eulerian trail if and only if at most one vertex has
 * (out-degree) − (in-degree) = 1, at most one vertex has (in-degree) − 
 * (out-degree) = 1, every other vertex has equal in-degree and out-degree, 
 * and all of its vertices with nonzero degree belong to a single connected 
 * component of the underlying undirected graph.
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class EulerianPathDirectedEdgesAdjacencyList {
  
  public int n, start, end, orderIndex;
  public int[] indexes, in, out, ordering;
  public List<List<Integer>> graph;

  public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    n = graph.size();
    this.graph = graph;
  }

  // Assumes that graph is one connected component.
  // Returns a list of edgeCount + 1 node ids that give the
  // Eulerian path or null if no path exists.
  public int[] getEulerianPath() {

    if (n < 2) return null;

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

    boolean allEqual = true;
    for (int i = 0; i < n; i++) {
      allEqual &= (in[i] == out[i]);
      if (in[i] - out[i] == 1) {
        end = i;
        endNodes++;
      } else if (out[i] - in[i] == 1) {
        start = i;
        startNodes++;
      }
    }

    // Graph is does not contain Eulerian Path. Too many start/end nodes.
    if ((endNodes != 1 || startNodes != 1) && !allEqual)
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

    /* Graph creation helper methods */

  public static List<List<Integer>> initializeEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) 
      graph.add(new ArrayList<>());
    return graph;
  }

  public static void addDirectedEdge(List<List<Integer>> g, int from, int to) {
    g.get(from).add(to);
  }

    /* Example */

  public static void main(String[] args) {
    // testSimplePath();
    testComplexPath1();
    testComplexPath2();
    testComplexPath2_large();
    testComplexPath3();
    testComplexPath4();
  }

  public static void testSimplePath() {

    int n = 5;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    graph.get(0).add(1);
    graph.get(1).add(2);
    graph.get(1).add(4);
    graph.get(1).add(3);    
    graph.get(2).add(1);
    graph.get(4).add(1);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    // Outputs: [0, 1, 2, 1, 4, 1, 3]
    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  // NO path should exist
  public static void testComplexPath1() {
    int n = 4;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    addDirectedEdge(graph, 0, 2);
    addDirectedEdge(graph, 2, 3);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 1, 0);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 0, 3);
    // addDirectedEdge(graph, 2, 1);
    // addDirectedEdge(graph, 3, 0);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }


  // Path should exist - all equals case
  public static void testComplexPath2() {
    int n = 4;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    addDirectedEdge(graph, 0, 2);
    addDirectedEdge(graph, 2, 1);
    addDirectedEdge(graph, 2, 3);
    addDirectedEdge(graph, 3, 0);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 1, 0);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 0, 3);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  // Valid path with unique start and end nodes.
  public static void testComplexPath3() {
    int n = 4;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    addDirectedEdge(graph, 0, 2);
    addDirectedEdge(graph, 2, 1);
    addDirectedEdge(graph, 2, 3);
    addDirectedEdge(graph, 3, 0);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 1, 0);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 0, 3);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  // Impossible path
  public static void testComplexPath4() {
    int n = 4;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    addDirectedEdge(graph, 0, 2);
    addDirectedEdge(graph, 2, 1);
    addDirectedEdge(graph, 2, 3);
    addDirectedEdge(graph, 3, 0);
    addDirectedEdge(graph, 3, 1);
    addDirectedEdge(graph, 1, 3);
    addDirectedEdge(graph, 1, 0);
    addDirectedEdge(graph, 1, 2);
    addDirectedEdge(graph, 0, 3);
    addDirectedEdge(graph, 2, 0);

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

  // Impossible path
  public static void testComplexPath2_large() {
    int n = 4;
    List<List<Integer>> graph = initializeEmptyGraph(n);

    // Add edges.
    for (int i = 0; i < 10; i++) {
      addDirectedEdge(graph, 0, 2);
      addDirectedEdge(graph, 2, 1);
      addDirectedEdge(graph, 2, 3);
      addDirectedEdge(graph, 3, 0);
      addDirectedEdge(graph, 3, 1);
      addDirectedEdge(graph, 1, 0);
      addDirectedEdge(graph, 1, 2);
      addDirectedEdge(graph, 0, 3);
    }

    for (int i = 0; i < n; i++) {
      Collections.shuffle(graph.get(i));
    }

    EulerianPathDirectedEdgesAdjacencyList solver;
    solver = new EulerianPathDirectedEdgesAdjacencyList(graph);

    System.out.println(Arrays.toString(solver.getEulerianPath()));
  }

}












