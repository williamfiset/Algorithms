/**
 * Finds the center(s) of a tree by finding the longest path through the tree.
 *
 * <p>./gradlew run
 * -Pmain=com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCenterLongestPathImpl
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class TreeCenterLongestPathImpl {

  private static class DfsResult {
    // The distance to the furthest node (from where the DFS started)
    int distance;

    // The index of the furthest node (from where the DFS started)
    int index;

    public DfsResult(int distance, int index) {
      this.distance = distance;
      this.index = index;
    }
  }

  private static DfsResult dfs(
      List<List<Integer>> graph, boolean[] visited, int[] prev, int at, int parent) {

    // Already visited this node
    if (visited[at]) return new DfsResult(0, parent);

    // Visit this node
    visited[at] = true;

    // Remember where we came from to rebuild path later on.
    prev[at] = parent;

    int bestDist = 0, index = -1;
    List<Integer> edges = graph.get(at);

    for (int to : edges) {
      DfsResult result = dfs(graph, visited, prev, to, at);
      int dist = result.distance + 1;
      if (dist > bestDist) {
        bestDist = dist;
        index = result.index;
      }
    }

    return new DfsResult(bestDist, index);
  }

  public static List<Integer> findTreeCenters(List<List<Integer>> graph) {
    List<Integer> centers = new ArrayList<>();
    if (graph == null) return centers;

    int n = graph.size();
    boolean[] visited = new boolean[n];
    int[] prev = new int[n];

    // Do DFS to find furthest node from the start
    DfsResult result = dfs(graph, visited, prev, 0, -1);
    int furthestNode1 = result.index;

    // Singleton
    if (furthestNode1 == -1) {
      centers.add(0);
      return centers;
    }

    // Do another DFS, but this time from the furthest node.
    Arrays.fill(visited, false);
    Arrays.fill(prev, 0);

    result = dfs(graph, visited, prev, furthestNode1, -1);
    int furthestNode2 = result.index;

    List<Integer> path = new LinkedList<>();
    for (int i = furthestNode2; i != -1; i = prev[i]) {
      path.add(i);
    }

    if (path.size() % 2 == 0) {
      centers.add(path.get(path.size() / 2 - 1));
    }
    centers.add(path.get(path.size() / 2));
    return centers;
  }

  /** ********** TESTING ********* */

  // Create an empty tree as a adjacency list.
  public static List<List<Integer>> createEmptyTree(int n) {
    List<List<Integer>> tree = new ArrayList<>(n);
    for (int i = 0; i < n; i++) tree.add(new LinkedList<>());
    return tree;
  }

  public static void addUndirectedEdge(List<List<Integer>> tree, int from, int to) {
    tree.get(from).add(to);
    tree.get(to).add(from);
  }

  public static void main(String[] args) {

    List<List<Integer>> graph = createEmptyTree(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);

    // Centers are 2
    System.out.println(findTreeCenters(graph));

    // Centers are 0
    List<List<Integer>> graph2 = createEmptyTree(1);
    System.out.println(findTreeCenters(graph2));

    // Centers are 0,1
    List<List<Integer>> graph3 = createEmptyTree(2);
    addUndirectedEdge(graph3, 0, 1);
    System.out.println(findTreeCenters(graph3));

    // Centers are 1
    List<List<Integer>> graph4 = createEmptyTree(3);
    addUndirectedEdge(graph4, 0, 1);
    addUndirectedEdge(graph4, 1, 2);
    System.out.println(findTreeCenters(graph4));

    // Centers are 1,2
    List<List<Integer>> graph5 = createEmptyTree(4);
    addUndirectedEdge(graph5, 0, 1);
    addUndirectedEdge(graph5, 1, 2);
    addUndirectedEdge(graph5, 2, 3);
    System.out.println(findTreeCenters(graph5));

    // Centers are 2,3
    List<List<Integer>> graph6 = createEmptyTree(7);
    addUndirectedEdge(graph6, 0, 1);
    addUndirectedEdge(graph6, 1, 2);
    addUndirectedEdge(graph6, 2, 3);
    addUndirectedEdge(graph6, 3, 4);
    addUndirectedEdge(graph6, 4, 5);
    addUndirectedEdge(graph6, 4, 6);
    System.out.println(findTreeCenters(graph6));
  }
}
