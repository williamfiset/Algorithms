/**
 * Determines if two unrooted trees are isomorphic. This algorithm can easily be modified to support
 * checking if two rooted trees are isomorphic.
 *
 * <p>Tested code against: https://uva.onlinejudge.org/external/124/p12489.pdf
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class TreeIsomorphism {

  public static class TreeNode {
    private int id;
    private TreeNode parent;
    private List<TreeNode> children;

    // Useful constructor for root node.
    public TreeNode(int id) {
      this(id, /* parent= */ null);
    }

    public TreeNode(int id, TreeNode parent) {
      this.id = id;
      this.parent = parent;
      children = new LinkedList<>();
    }

    public void addChildren(TreeNode... nodes) {
      for (TreeNode node : nodes) {
        children.add(node);
      }
    }

    public int id() {
      return id;
    }

    public TreeNode parent() {
      return parent;
    }

    public List<TreeNode> children() {
      return children;
    }

    @Override
    public String toString() {
      return String.valueOf(id);
    }
  }

  // Determines if two unrooted trees are isomorphic
  public static boolean treesAreIsomorphic(List<List<Integer>> tree1, List<List<Integer>> tree2) {
    if (tree1.isEmpty() || tree2.isEmpty()) {
      throw new IllegalArgumentException("Empty tree input");
    }

    List<Integer> centers1 = findTreeCenters(tree1);
    List<Integer> centers2 = findTreeCenters(tree2);

    TreeNode rootedTree1 = rootTree(tree1, centers1.get(0));
    String tree1Encoding = encode(rootedTree1);

    for (int center : centers2) {
      TreeNode rootedTree2 = rootTree(tree2, center);
      String tree2Encoding = encode(rootedTree2);

      if (tree1Encoding.equals(tree2Encoding)) {
        return true;
      }
    }
    return false;
  }

  private static List<Integer> findTreeCenters(List<List<Integer>> tree) {
    int n = tree.size();

    int[] degree = new int[n];
    List<Integer> leaves = new ArrayList<>();

    // Find the first outer layer of leaf nodes.
    for (int i = 0; i < n; i++) {
      List<Integer> edges = tree.get(i);
      degree[i] = edges.size();
      if (degree[i] <= 1) {
        leaves.add(i);
        degree[i] = 0;
      }
    }

    int processedLeafs = leaves.size();

    // Iteratively remove leaf nodes layer by layer until only the centers remain.
    while (processedLeafs < n) {
      List<Integer> newLeaves = new ArrayList<>();
      for (int node : leaves) {
        for (int neighbor : tree.get(node)) {
          if (--degree[neighbor] == 1) {
            newLeaves.add(neighbor);
          }
        }
        degree[node] = 0;
      }
      processedLeafs += newLeaves.size();
      leaves = newLeaves;
    }

    return leaves;
  }

  private static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
    TreeNode root = new TreeNode(rootId);
    return buildTree(graph, root);
  }

  // Do dfs to construct rooted tree.
  private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
    for (int neighbor : graph.get(node.id())) {
      // Ignore adding an edge pointing back to parent.
      if (node.parent() != null && neighbor == node.parent().id()) {
        continue;
      }

      TreeNode child = new TreeNode(neighbor, node);
      node.addChildren(child);

      buildTree(graph, child);
    }
    return node;
  }

  // Constructs the canonical form representation of a tree as a string.
  public static String encode(TreeNode node) {
    if (node == null) {
      return "";
    }
    List<String> labels = new LinkedList<>();
    for (TreeNode child : node.children()) {
      labels.add(encode(child));
    }
    Collections.sort(labels);
    StringBuilder sb = new StringBuilder();
    for (String label : labels) {
      sb.append(label);
    }
    return "(" + sb.toString() + ")";
  }

  /* Graph/Tree creation helper methods. */

  // Create a graph as a adjacency list with 'n' nodes.
  public static List<List<Integer>> createEmptyGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new LinkedList<>());
    return graph;
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  /* Example usage */

  public static void main(String[] args) {
    simpleIsomorphismTest();
    testEncodingTreeFromSlides();
  }

  // Test if two tree are isomorphic, meaning they are structurally equivalent
  // but are labeled differently.
  private static void simpleIsomorphismTest() {
    List<List<Integer>> tree1 = createEmptyGraph(5);
    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 3, 4);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);

    List<List<Integer>> tree2 = createEmptyGraph(5);
    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 2, 4);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 2);

    if (!treesAreIsomorphic(tree1, tree2)) {
      System.out.println("Oops, these tree should be isomorphic!");
    }
  }

  private static void testEncodingTreeFromSlides() {
    List<List<Integer>> tree = createEmptyGraph(10);
    addUndirectedEdge(tree, 0, 2);
    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 3);
    addUndirectedEdge(tree, 2, 6);
    addUndirectedEdge(tree, 2, 7);
    addUndirectedEdge(tree, 1, 4);
    addUndirectedEdge(tree, 1, 5);
    addUndirectedEdge(tree, 5, 9);
    addUndirectedEdge(tree, 3, 8);

    TreeNode root0 = rootTree(tree, 0);

    if (!encode(root0).equals("(((())())(()())(()))")) {
      System.out.println("Tree encoding is wrong: " + encode(root0));
    }
  }
}
