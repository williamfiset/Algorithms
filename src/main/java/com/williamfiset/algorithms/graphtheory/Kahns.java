/**
 * Implementation of Kahn's algorithm to find a topological ordering
 *
 * <p>Kahn's algorithm finds a topological ordering by iteratively removing nodes in the graph which
 * have no incoming edges. When a node is removed from the graph, it is added to the topological
 * ordering and all its edges are removed allowing for the next set of nodes with no incoming edges
 * to be selected.
 *
 * <p>Verified against: https://open.kattis.com/problems/builddeps
 *
 * <p>./gradlew run -Palgorithm=graphtheory.Kahns
 *
 * <p>Time complexity: O(V+E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import static com.williamfiset.algorithms.utils.graphutils.Utils.addDirectedEdge;
import static com.williamfiset.algorithms.utils.graphutils.Utils.createEmptyAdjacencyList;

import java.util.*;

public class Kahns {

  // Given a an acyclic graph `g` represented as a adjacency list, return a
  // topological ordering on the nodes of the graph.
  public int[] kahns(List<List<Integer>> g) {
    int n = g.size();

    // Calculate the in-degree of each node.
    int[] inDegree = new int[n];
    for (List<Integer> edges : g) {
      for (int to : edges) {
        inDegree[to]++;
      }
    }

    // q always contains the set nodes with no incoming edges.
    Queue<Integer> q = new ArrayDeque<>();

    // Find all start nodes.
    for (int i = 0; i < n; i++) {
      if (inDegree[i] == 0) {
        q.offer(i);
      }
    }

    int index = 0;
    int[] order = new int[n];
    while (!q.isEmpty()) {
      int at = q.poll();
      order[index++] = at;
      for (int to : g.get(at)) {
        inDegree[to]--;
        if (inDegree[to] == 0) {
          q.offer(to);
        }
      }
    }
    if (index != n) {
      throw new IllegalArgumentException("Graph is not acyclic! Detected a cycle.");
    }
    return order;
  }

  // Example usage:
  public static void main(String[] args) {
    exampleFromSlides();
    // test1();
    // test2();
    // cycleTest();
  }

  private static void exampleFromSlides() {
    List<List<Integer>> g = createEmptyAdjacencyList(14);
    addDirectedEdge(g, 0, 2);
    addDirectedEdge(g, 0, 3);
    addDirectedEdge(g, 0, 6);
    addDirectedEdge(g, 1, 4);
    addDirectedEdge(g, 2, 6);
    addDirectedEdge(g, 3, 1);
    addDirectedEdge(g, 3, 4);
    addDirectedEdge(g, 4, 5);
    addDirectedEdge(g, 4, 8);
    addDirectedEdge(g, 6, 7);
    addDirectedEdge(g, 6, 11);
    addDirectedEdge(g, 7, 4);
    addDirectedEdge(g, 7, 12);
    addDirectedEdge(g, 9, 2);
    addDirectedEdge(g, 9, 10);
    addDirectedEdge(g, 10, 6);
    addDirectedEdge(g, 11, 12);
    addDirectedEdge(g, 12, 8);

    Kahns solver = new Kahns();
    int[] ordering = solver.kahns(g);

    // Prints: [0, 9, 13, 3, 2, 10, 1, 6, 7, 11, 4, 12, 5, 8]
    System.out.println(java.util.Arrays.toString(ordering));
  }

  private static void test1() {
    List<List<Integer>> g = createEmptyAdjacencyList(6);
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
    List<List<Integer>> g = createEmptyAdjacencyList(6);
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
    List<List<Integer>> g = createEmptyAdjacencyList(4);
    addDirectedEdge(g, 0, 1);
    addDirectedEdge(g, 1, 2);
    addDirectedEdge(g, 2, 3);
    addDirectedEdge(g, 3, 0);
    Kahns solver = new Kahns();
    System.out.println(java.util.Arrays.toString(solver.kahns(g)));
  }
}
