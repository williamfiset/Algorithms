/**
 * NOTE: This file is still under development!
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EulerianPathDirectedEdgesAdjacencyList {
  
  public int n, edgeCount, orderIndex;
  public int[] in, out, ordering;
  public List<List<Integer>> graph;

  public EulerianPathDirectedEdgesAdjacencyList(List<List<Integer>> graph) {
    if (graph == null) throw new IllegalArgumentException("Graph cannot be null");
    n = graph.size();
    this.graph = graph;
  }

  private void setUp() {
    // Arrays that track the in degree and out degree of each node.
    in  = new int[n];
    out = new int[n];

    edgeCount = 0;

    // Compute in and out node degrees.
    for (int from = 0; from < n; from++) {
      List<Integer> edges = graph.get(from);
      for (int i = 0; i < edges.size(); i++) {
        int to = edges.get(i);
        in[to]++;
        out[from]++;
        edgeCount++;
      }
    }
  }

  private int findStartNode() {
    int start = 0;
    for (int i = 0; i < n; i++) {
      // Unique starting node.
      if (out[i] - in[i] == 1) return i;

      // Start in a non-empty component
      if (out[i] > 0) start = i;
    }
    return start;
  }

  private boolean graphHasEulerianPath() {
    int endNodes = 0, startNodes = 0;
    for (int i = 0; i < n; i++) {
      if (out[i] - in[i] > 1) // equivalently, in[i] - out[i] > 1 by symmetry.
        return false;
      if (in[i] - out[i] == 1) {
        endNodes++;
      } else if (out[i] - in[i] == 1) {
        startNodes++;
      }
    }
    return (endNodes == 0 && startNodes == 0) || 
           (endNodes == 1 && startNodes == 1);
  }

  // Make sure all edges of the graph were traversed. It could be the case that
  // the graph is disconnected in which case return null.
  private boolean graphIsConnected() {
    for (int i = 0; i < n; i++)
      if (out[i] != 0)
        return false;
    return true;
  }

  // Returns a list of edgeCount + 1 node ids that give the Eulerian path or
  // null if no path exists or the graph is disconnected.
  public int[] getEulerianPath() {

    setUp();
    if (edgeCount == 0) return null;

    if (!graphHasEulerianPath()) return null;
    
    orderIndex = edgeCount;
    ordering = new int[edgeCount+1];
    dfs(findStartNode());

    if (!graphIsConnected()) return null;

    return ordering;
  }

  // Perform DFS to find Eulerian path.
  private void dfs(int at) {
    while(out[at] != 0) {
      int next = graph.get(at).get(--out[at]);
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
    smallExample();
  }

  public static void smallExample() {

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

}












