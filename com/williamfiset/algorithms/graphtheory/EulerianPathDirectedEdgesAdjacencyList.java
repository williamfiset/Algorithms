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
  
  static List<Integer> ans = new ArrayList<>();
  static Map<Long, Integer> map = new HashMap<>();

  static void dfs(int at, int N, int[] indexes, List<List<Integer>> graph) {

    List<Integer> edges = graph.get(at);
    if (edges != null) {

      for (int i = indexes[at]; i < edges.size(); i = indexes[at]) {
        
        int from = at, to = edges.get(i);
        indexes[at]++;

        long edgeId = at*N + to;
        int count = map.get(edgeId);

        if (count > 0) {
          map.put(edgeId, --count);
          System.out.printf("FROM: %d TO: %d\n", from, to);
          dfs(to, N, indexes, graph);
        }

      }

    }

    ans.add(at);

  }

  // Assume that all belong to on connected component.
  static int[] eulerianPath(List<List<Integer>> graph) {

    if (graph == null) throw new IllegalArgumentException();
    
    final int N = graph.size();

    // Tracks in degrees and out degrees for each node.
    int[] in  = new int[N];
    int[] out = new int[N];
    
    // Edge count.
    int E = 0;

    for (int from = 0; from < N; from++) {
      List<Integer> edges = graph.get(from);
      for (int i = 0; i < edges.size(); i++) {

        int to = edges.get(i);
        out[from]++;
        in[to]++;
        E++;
        
        long hash = (long)(from * N) + to;
        Integer count = map.get(hash);
        if (count == null) count = 0;
        map.put(hash, ++count);

      }
    }
    
    // System.out.println(Arrays.toString(in));
    // System.out.println(Arrays.toString(out));
    // System.out.printf("%d\n", E);

    int start = 0, end = 0, inOut = 0, outIn = 0;
    for (int i = 0; i < N; i++) {
      if (in[i] - out[i] == 1) {
        end = i;
        inOut++;
      } else if (out[i] - in[i] == 1) {
        start = i;
        outIn++;
      } else if (in[i] != out[i]) return null;
    }
    
    // Assert that an eulerian path exists on this graph.
    if (inOut > 1 || outIn > 1) {
      // System.out.println("NOT Eulerian");
      // System.out.println(inOut + " " + outIn);
      return null;
    }
    
    // System.out.println("START: " + start);
    // System.out.println("END: " + end);

    // Main algorithm here.
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    
    int[] ordering = new int[E];
    int[] indexes = new int[N];

    dfs(start, N, indexes, graph);

    // Add start node?
    // System.out.println(ans);
    Collections.reverse(ans);
    System.out.println(ans);

    return new int[]{};
  }

  public static void main(String[] args) {
    // testSimplePath();
    testMoreComplexPath();
  }

  static List<List<Integer>> initializeEmptyGraph(int N) {
    List<List<Integer>> graph = new ArrayList<>(N);
    for (int i = 0; i < N; i++) 
      graph.add(new ArrayList<>());
    return graph;
  }

  /* TESTING */

  public static void testSimplePath() {

    int N = 5;
    List<List<Integer>> graph = initializeEmptyGraph(N);

    // Add edges.
    graph.get(0).add(1);
    graph.get(1).add(2);
    graph.get(1).add(3);
    graph.get(1).add(4);
    graph.get(2).add(1);
    graph.get(4).add(1);

    // [0, 1, 2, 1, 4, 1, 3]
    eulerianPath(graph);

  }

  public static void testMoreComplexPath() {
    int N = 9;
    List<List<Integer>> graph = initializeEmptyGraph(N);

    graph.get(0).add(1);

    graph.get(2).add(1);
    graph.get(1).add(3);
    graph.get(5).add(6);
    graph.get(1).add(2);
    graph.get(1).add(2);


    graph.get(3).add(4);
    graph.get(2).add(1);

    graph.get(6).add(5);
    graph.get(4).add(1);
    graph.get(6).add(5);

    graph.get(5).add(6);
    graph.get(1).add(5);
    graph.get(5).add(7);


    // graph.get(7).add(1);

    graph.get(8).add(7);
    graph.get(7).add(8);

    eulerianPath(graph);

  }

}












