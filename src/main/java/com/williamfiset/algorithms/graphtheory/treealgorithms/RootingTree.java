/**
 * Rooting an Undirected Tree
 *
 * Given an undirected tree as an adjacency list, this algorithm converts it
 * into a rooted tree by performing a DFS from a chosen root node. Each node
 * in the resulting tree stores its parent and children.
 *
 * Time:  O(V + E)
 * Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.ArrayList;
import java.util.List;

public class RootingTree {

  public static class TreeNode {
    private final int id;
    private final TreeNode parent;
    private final List<TreeNode> children;

    // Useful constructor for root node.
    public TreeNode(int id) {
      this(id, /* parent= */ null);
    }

    public TreeNode(int id, TreeNode parent) {
      this.id = id;
      this.parent = parent;
      this.children = new ArrayList<>();
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

    @Override
    public int hashCode() {
      return id;
    }
  }

  /**
   * Roots the undirected tree at the given node and returns the root TreeNode.
   */
  public static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
    TreeNode root = new TreeNode(rootId);
    return buildTree(graph, root);
  }

  /**
   * Recursively builds the rooted tree via DFS. Skips the edge back to the
   * parent to avoid cycles.
   */
  private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
    for (int childId : graph.get(node.id())) {
      // Ignore the edge pointing back to parent.
      if (node.parent() != null && childId == node.parent().id()) {
        continue;
      }
      TreeNode child = new TreeNode(childId, node);
      node.addChildren(child);
      buildTree(graph, child);
    }
    return node;
  }

  /* Graph helpers */

  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  public static void addUndirectedEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  // ==================== Main ====================

  public static void main(String[] args) {

    //  Undirected tree:
    //
    //  0 - 1 - 2 - 3 - 4
    //          |    |
    //          6    5
    //         / \
    //        7   8
    //
    //  Rooted at 6:
    //
    //           6
    //      2    7     8
    //    1   3
    //  0    4 5

    List<List<Integer>> graph = createGraph(9);
    addUndirectedEdge(graph, 0, 1);
    addUndirectedEdge(graph, 2, 1);
    addUndirectedEdge(graph, 2, 3);
    addUndirectedEdge(graph, 3, 4);
    addUndirectedEdge(graph, 5, 3);
    addUndirectedEdge(graph, 2, 6);
    addUndirectedEdge(graph, 6, 7);
    addUndirectedEdge(graph, 6, 8);

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

    //  Rooted at 3:
    //
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
