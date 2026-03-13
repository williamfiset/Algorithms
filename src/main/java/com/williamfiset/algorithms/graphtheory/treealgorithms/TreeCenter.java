/**
 * Tree Center(s)
 *
 * The center of a tree is the set of nodes that minimizes the maximum
 * distance (eccentricity) to any other node. A tree has either 1 center
 * (odd diameter) or 2 adjacent centers (even diameter).
 *
 * The algorithm works by iteratively peeling leaf nodes layer by layer
 * (like peeling an onion) until only the center node(s) remain. Each
 * round removes all current leaves and decrements the degree of their
 * neighbors, creating a new set of leaves for the next round.
 *
 * Time:  O(V + E)
 * Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.ArrayList;
import java.util.List;

public class TreeCenter {

  /**
   * Returns the center node(s) of the tree. The result contains either
   * 1 node (odd diameter) or 2 adjacent nodes (even diameter).
   */
  public static List<Integer> findTreeCenters(List<List<Integer>> tree) {
    final int n = tree.size();
    int[] degree = new int[n];
    List<Integer> leaves = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      degree[i] = tree.get(i).size();
      if (degree[i] <= 1) leaves.add(i);
    }

    // Iteratively peel leaf layers until only the center(s) remain.
    int processed = leaves.size();
    while (processed < n) {
      List<Integer> newLeaves = new ArrayList<>();
      for (int node : leaves)
        for (int neighbor : tree.get(node))
          if (--degree[neighbor] == 1) newLeaves.add(neighbor);
      processed += newLeaves.size();
      leaves = newLeaves;
    }

    return leaves;
  }

  /* Graph helpers */

  public static List<List<Integer>> createEmptyTree(int n) {
    List<List<Integer>> tree = new ArrayList<>(n);
    for (int i = 0; i < n; i++) tree.add(new ArrayList<>());
    return tree;
  }

  public static void addUndirectedEdge(List<List<Integer>> tree, int from, int to) {
    tree.get(from).add(to);
    tree.get(to).add(from);
  }

  // ==================== Main ====================

  public static void main(String[] args) {

    //  0 - 1 - 2 - 3 - 4
    //          |    |
    //          6    5
    //         / \
    //        7   8
    //
    //  Center: [2]

    List<List<Integer>> graph = createEmptyTree(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);

    System.out.println(findTreeCenters(graph)); // [2]
  }
}
