package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class LowestCommonAncestor {

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

  public static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
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

  // Finds the lowest common ancestor of the nodes with id1 and id2.
  public static TreeNode lca(TreeNode root, int id1, int id2) {
    // TODO(william): implement.
    return root;
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

  public static void main(String[] args) {
    
  }

}
