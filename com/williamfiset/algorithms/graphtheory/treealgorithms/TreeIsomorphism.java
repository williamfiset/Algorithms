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
      this(id, /*parent=*/ null);
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

    public boolean isLeaf() {
      return children.size() == 0;
    }

    public List<TreeNode> children() {
      return children;
    }

    @Override
    public String toString() {
      return String.valueOf(id);
    }
  }

  private static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
    TreeNode root = new TreeNode(rootId);
    return buildTree(graph, root, /*parent=*/ null);
  }

  // Do dfs to construct rooted tree.
  private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node, TreeNode parent) {
    for (int childId : graph.get(node.id)) {
      // Ignore adding an edge pointing back to parent.
      if (parent != null && childId == parent.id) continue;

      TreeNode child = new TreeNode(childId, node);
      node.addChildren(child);

      buildTree(graph, child, node);
    }
    return node;
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
      for (int node : leaves) {
        for (int neighbor : tree.get(node)) {
          if (--degrees[neighbor] == 1) {
            newLeaves.add(neighbor);
          }
        }
      }
      processedLeafs += newLeaves.size();
      leaves = newLeaves;
    }

    return leaves;
  }

  // Determines if two unrooted trees are isomorphic
  public static boolean treesAreIsomorphic(List<List<Integer>> tree1, List<List<Integer>> tree2) {
    if (tree1.isEmpty() || tree2.isEmpty()) {
      throw new IllegalArgumentException("Empty tree input");
    }

    List<Integer> centers1 = findTreeCenters(tree1);
    List<Integer> centers2 = findTreeCenters(tree2);

    TreeNode rootedTree1 = rootTree(tree1, centers1.get(0));
    for (int center : centers2) {
      if (areIsomorphic(rootedTree1, rootTree(tree2, center))) {
        return true;
      }
    }
    return false;
  }

  private static boolean areIsomorphic(TreeNode root1, TreeNode root2) {
    return encode(root1).equals(encode(root2));
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

  // Create a graph as a adjacency list
  public static List<List<Integer>> createGraph(int n) {
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

  private static void simpleIsomorphismTest() {
    // Test if two tree are isomorphic, meaning they are structurally equivalent
    // but are labeled differently.
    List<List<Integer>> tree1 = createGraph(5);
    List<List<Integer>> tree2 = createGraph(5);

    addUndirectedEdge(tree1, 2, 0);
    addUndirectedEdge(tree1, 3, 4);
    addUndirectedEdge(tree1, 2, 1);
    addUndirectedEdge(tree1, 2, 3);

    addUndirectedEdge(tree2, 1, 0);
    addUndirectedEdge(tree2, 2, 4);
    addUndirectedEdge(tree2, 1, 3);
    addUndirectedEdge(tree2, 1, 2);

    if (!treesAreIsomorphic(tree1, tree2)) {
      System.out.println("Oops something is not right.");
    }
  }

  private static void testEncodingTreeFromSlides() {
    List<List<Integer>> tree = createGraph(10);
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

    System.out.println("Tree from slides encoding:");
    System.out.println(encode(root0));
  }
}
