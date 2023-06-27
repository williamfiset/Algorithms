/**
 * Often when working with trees we are given them as a graph with undirected edges, however
 * sometimes a better representation is a rooted tree.
 *
 * <p>Time Complexity: O(V+E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class RootingTree {

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

    // Only checks id equality not subtree equality.
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof TreeNode) {
        return id() == ((TreeNode) obj).id();
      }
      return false;
    }
  }

  public static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
    TreeNode root = new TreeNode(rootId);
    return buildTree(graph, root);
  }

  // Do dfs to construct rooted tree.
  private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
    for (int childId : graph.get(node.id())) {
      // Ignore adding an edge pointing back to parent.
      if (node.parent() != null && childId == node.parent().id()) {
        continue;
      }

      TreeNode child = new TreeNode(childId, node);
      node.addChildren(child);

      buildTree(graph, child);
    }
    return node;
  }

  /** ********** TESTING ********* */

  // Create a graph as a adjacency list
  private static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new LinkedList<>());
    return graph;
  }

  private static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  public static void main(String[] args) {

    List<List<Integer>> graph = createGraph(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);

    // Rooted at 6 the tree should look like:
    //           6
    //      2    7     8
    //    1   3
    //  0    4 5

    TreeNode root = rootTree(graph, 6);

    // Layer 0: [6]
    System.out.println(root);

    // Layer 1: [2, 7, 8]
    System.out.println(root.children);

    // Layer 2: [1, 3]
    System.out.println(root.children.get(0).children);

    // Layer 3: [0], [4, 5]
    System.out.println(
        root.children.get(0).children.get(0).children
            + ", "
            + root.children.get(0).children.get(1).children);

    // Rooted at 3 the tree should look like:
    //               3
    //     2         4        5
    //  6     1
    // 7 8    0

    // Layer 0: [3]
    root = rootTree(graph, 3);
    System.out.println();
    System.out.println(root);

    // Layer 1: [2, 4, 5]
    System.out.println(root.children);

    // Layer 2: [1, 6]
    System.out.println(root.children.get(0).children);

    // Layer 3: [0], [7, 8]
    System.out.println(
        root.children.get(0).children.get(0).children
            + ", "
            + root.children.get(0).children.get(1).children);
  }
}
