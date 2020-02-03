/*


javac -d src/main/java src/main/java/com/williamfiset/algorithms/graphtheory/treealgorithms/TreeCenterLongestPathImpl.java

java -cp src/main/java com/williamfiset/algorithms/graphtheory/treealgorithms/TreeCenterLongestPathImpl


*/

package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class TreeCenterLongestPathImpl {

  private static int[] dfs(List<List<Integer>> graph, boolean[] visited, int[] prev, int at, int parent) {

    // Already visited this node
    if (visited[at]) return new int[] {0, parent};

    // Visit this node
    visited[at] = true;

    // Remember where we came from to rebuild path later on.
    prev[at] = parent;

    int bestDist = 0, index = -1;
    List<Integer> edges = graph.get(at);

    for (int to : edges) {
      int[] tuple = dfs(graph, visited, prev, to, at);
      int dist = tuple[0] + 1;
      if (dist > bestDist) {
        bestDist = dist;
        index = tuple[1];
      }
    }

    return new int[] {bestDist, index};
  }

  public static List<Integer> treeCenter(List<List<Integer>> graph) {
    List<Integer> centers = new ArrayList<>();
    if (graph == null) return centers;

    int n = graph.size();
    boolean[] visited = new boolean[n];

    // Do DFS to find furthest node from the start
    int furthestIndex = dfs(graph, visited, new int[n], 0, -1)[1];

    // Singleton
    if (furthestIndex == -1) {
      centers.add(0);
      return centers;
    }

    // Do another DFS, but this time from the furthest
    // node and record the distance to that node.
    Arrays.fill(visited, false);
    int[] prev = new int[n];
    int newfurthestIndex = dfs(graph, visited, prev, furthestIndex, -1)[1];

    List<Integer> path = new LinkedList<>();
    for (int i = newfurthestIndex; i != -1; i = prev[i]) {
      path.add(i);
    }

    if (path.size() % 2 == 0) {
      centers.add(path.get(path.size()/2-1));
    }
    centers.add(path.get(path.size()/2));
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
    System.out.println(treeCenter(graph)+"\n");

    // Centers are 0
    List<List<Integer>> graph2 = createEmptyTree(1);
    System.out.println(treeCenter(graph2)+"\n");

    // Centers are 0,1
    List<List<Integer>> graph3 = createEmptyTree(2);
    addUndirectedEdge(graph3, 0, 1);
    System.out.println(treeCenter(graph3)+"\n");

    // Centers are 1
    List<List<Integer>> graph4 = createEmptyTree(3);
    addUndirectedEdge(graph4, 0, 1);
    addUndirectedEdge(graph4, 1, 2);
    System.out.println(treeCenter(graph4)+"\n");

    // Centers are 1,2
    List<List<Integer>> graph5 = createEmptyTree(4);
    addUndirectedEdge(graph5, 0, 1);
    addUndirectedEdge(graph5, 1, 2);
    addUndirectedEdge(graph5, 2, 3);
    System.out.println(treeCenter(graph5)+"\n");

    // Centers are 2,3
    List<List<Integer>> graph6 = createEmptyTree(7);
    addUndirectedEdge(graph6, 0, 1);
    addUndirectedEdge(graph6, 1, 2);
    addUndirectedEdge(graph6, 2, 3);
    addUndirectedEdge(graph6, 3, 4);
    addUndirectedEdge(graph6, 4, 5);
    addUndirectedEdge(graph6, 4, 6);
    System.out.println(treeCenter(graph6)+"\n");
  }
}
