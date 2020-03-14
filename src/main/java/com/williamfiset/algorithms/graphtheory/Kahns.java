// ./gradlew run -Palgorithm=graphtheory.Kahns

package com.williamfiset.algorithms.graphtheory;

import java.util.*;

public class Kahns {

  public int[] kahns(List<List<Integer>> g) {
    int n = g.size();
    Stack<Integer> nodesWithNoIncomingEdges = new Stack<>();
    int[] inDegree = new int[n];
    for (List<Integer> edges : g) {
      for (int to : edges) {
        inDegree[to]++;
      }
    }
    for (int i = 0; i < n; i++) {
      if (inDegree[i] == 0) {
        nodesWithNoIncomingEdges.push(i);
      }
    }
    int[] order = new int[n];
    int index = 0;
    while (!nodesWithNoIncomingEdges.isEmpty()) {
      int at = nodesWithNoIncomingEdges.pop();
      order[index++] = at;
      for (int to : g.get(at)) {
        inDegree[to]--;
        if (inDegree[to] == 0) {
          nodesWithNoIncomingEdges.push(to);
        }
      }
    }
    if (index != n) {
      throw new IllegalStateException("Detected cycle in graph!");
    }
    return order;
  }  

	  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addDirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  public static void main(String[] args) {
    test1();
    test2();
    // cycleTest();
  }

  private static void test1() {
    List<List<Integer>> g = createGraph(6);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 3, 1);
    addDirectedEdge(g, 3, 2);
    addDirectedEdge(g, 2, 4);
    addDirectedEdge(g, 4, 5);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));    
  }

  private static void test2() {
    List<List<Integer>> g = createGraph(6);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 0, 5);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 1, 3);
    addDirectedEdge(g, 2, 3);
    addDirectedEdge(g, 2, 4);
    addDirectedEdge(g, 3, 4);
    addDirectedEdge(g, 5, 4);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));    
  }

  private static void cycleTest() {
    List<List<Integer>> g = createGraph(4);
    addDirectedEdge(g, 0, 1);    
    addDirectedEdge(g, 1, 2);    
    addDirectedEdge(g, 2, 3);    
    addDirectedEdge(g, 3, 0);    
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));
  }

}

















