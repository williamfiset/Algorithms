/**
 * The graph isomorphism problem for general graphs can be quite difficult, however there exists an
 * elegant solution to uniquely encode a graph if it is a tree. Here is a brilliant explanation with
 * animations:
 *
 * <p>http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
 *
 * <p>This implementation uses a breadth first search on an undirected graph to generate the tree's
 * canonical encoding.
 *
 * <p>Tested code against: https://uva.onlinejudge.org/external/124/p12489.pdf
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class TreeIsomorphismWithBfs {

  public static List<List<Integer>> createEmptyTree(int n) {
    List<List<Integer>> tree = new ArrayList<>(n);
    for (int i = 0; i < n; i++) tree.add(new ArrayList<>());
    return tree;
  }

  public static void addUndirectedEdge(List<List<Integer>> tree, int from, int to) {
    tree.get(from).add(to);
    tree.get(to).add(from);
  }

  private static List<Integer> findTreeCenters(List<List<Integer>> tree) {
    final int n = tree.size();
    int[] degrees = new int[n];

    // Find all leaf nodes
    List<Integer> leaves = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      List<Integer> edges = tree.get(i);
      degrees[i] = edges.size();
      if (degrees[i] <= 1) leaves.add(i);
    }

    int processedLeafs = leaves.size();

    // Remove leaf nodes and decrease the degree of
    // each node adding new leaf nodes progressively
    // until only the centers remain.
    while (processedLeafs < n) {
      List<Integer> newLeaves = new ArrayList<>();
      for (int node : leaves)
        for (int neighbor : tree.get(node)) if (--degrees[neighbor] == 1) newLeaves.add(neighbor);
      processedLeafs += newLeaves.size();
      leaves = newLeaves;
    }

    return leaves;
  }

  // Encodes a tree as a string such that any isomorphic tree
  // also has the same encoding.
  // TODO(william): make this method private and test only with the treesAreIsomorphic method
  public static String encodeTree(List<List<Integer>> tree) {
    if (tree == null || tree.size() == 0) return "";
    if (tree.size() == 1) return "()";
    final int n = tree.size();

    int root = findTreeCenters(tree).get(0);

    int[] degree = new int[n];
    int[] parent = new int[n];
    boolean[] visited = new boolean[n];
    List<Integer> leafs = new ArrayList<>();

    Queue<Integer> q = new ArrayDeque<>();
    visited[root] = true;
    parent[root] = -1; // unused.
    q.offer(root);

    // Do a BFS to find all the leaf nodes
    while (!q.isEmpty()) {
      int at = q.poll();
      List<Integer> edges = tree.get(at);
      degree[at] = edges.size();
      for (int next : edges) {
        if (!visited[next]) {
          visited[next] = true;
          parent[next] = at;
          q.offer(next);
        }
      }
      if (degree[at] == 1) leafs.add(at);
    }

    List<Integer> newLeafs = new ArrayList<>();
    String[] map = new String[n];
    for (int i = 0; i < n; i++) {
      visited[i] = false;
      map[i] = "()";
    }

    int treeSize = n;
    while (treeSize > 2) {
      for (int leaf : leafs) {

        // Find parent of leaf node and check if the parent
        // is a candidate for the next cycle of leaf nodes
        visited[leaf] = true;
        int p = parent[leaf];
        if (--degree[p] == 1) newLeafs.add(p);

        treeSize--;
      }

      // Update parent labels
      for (int p : newLeafs) {

        List<String> labels = new ArrayList<>();
        for (int child : tree.get(p))
          // Recall edges are bidirectional so we don't want to
          // access the parent's parent here.
          if (visited[child]) labels.add(map[child]);

        String parentInnerParentheses = map[p].substring(1, map[p].length() - 1);
        labels.add(parentInnerParentheses);

        Collections.sort(labels);
        map[p] = "(".concat(String.join("", labels)).concat(")");
      }

      leafs.clear();
      leafs.addAll(newLeafs);
      newLeafs.clear();
    }

    // Only one node remains and it holds the canonical form
    String l1 = map[leafs.get(0)];
    if (treeSize == 1) return l1;

    // Two nodes remain and we need to combine their labels
    String l2 = map[leafs.get(1)];
    return ((l1.compareTo(l2) < 0) ? (l1 + l2) : (l2 + l1));
  }

  public static boolean treesAreIsomorphic(List<List<Integer>> tree1, List<List<Integer>> tree2) {
    return encodeTree(tree1).equals(encodeTree(tree2));
  }

  /* Example usage */

  public static void main(String[] args) {
    // Test if two tree are isomorphic, meaning they are structurally equivalent
    // but are labeled differently.
    List<List<Integer>> tree1 = createEmptyTree(5);
    List<List<Integer>> tree2 = createEmptyTree(5);

    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 3, 4);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);

    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 2, 4);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 2);

    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);

    System.out.println("Tree1 encoding: " + encoding1);
    System.out.println("Tree2 encoding: " + encoding1);
    System.out.println("Trees are isomorphic: " + (encoding1.equals(encoding2)));

    // Print:
    // Tree1 encoding: (()())(())
    // Tree2 encoding: (()())(())
    // Trees are isomorphic: true
  }
}
