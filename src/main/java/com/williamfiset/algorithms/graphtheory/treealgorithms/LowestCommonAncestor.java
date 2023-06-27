package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class LowestCommonAncestor {

  public static class TreeNode {
    // Number of nodes in the subtree. Computed when tree is built.
    private int n;

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

    public void setSize(int n) {
      this.n = n;
    }

    // Number of nodes in the subtree (including the node itself)
    public int size() {
      return n;
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

    public static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
      TreeNode root = new TreeNode(rootId);
      return buildTree(graph, root);
    }

    // Do dfs to construct rooted tree.
    private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
      int subtreeNodeCount = 1;
      for (int neighbor : graph.get(node.id())) {
        // Ignore adding an edge pointing back to parent.
        if (node.parent() != null && neighbor == node.parent().id()) {
          continue;
        }

        TreeNode child = new TreeNode(neighbor, node);
        node.addChildren(child);

        buildTree(graph, child);
        subtreeNodeCount += child.size();
      }
      node.setSize(subtreeNodeCount);
      return node;
    }

    @Override
    public String toString() {
      return String.valueOf(id);
    }
  }

  private TreeNode lcaNode = null;
  private TreeNode root;

  public LowestCommonAncestor(TreeNode root) {
    this.root = root;
  }

  // Finds the lowest common ancestor of the nodes with id1 and id2.
  public TreeNode lca(int id1, int id2) {
    lcaNode = null;
    helper(root, id1, id2);
    return lcaNode;
  }

  private boolean helper(TreeNode node, int id1, int id2) {
    if (node == null) {
      return false;
    }
    int count = 0;
    if (node.id() == id1) {
      count++;
    }
    if (node.id() == id2) {
      count++;
    }
    for (TreeNode child : node.children()) {
      if (helper(child, id1, id2)) {
        count++;
      }
    }
    if (count == 2) {
      lcaNode = node;
    }
    return count > 0;
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
    TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestor solver = new LowestCommonAncestor(root);
    System.out.println(solver.lca(10, 15).id());
  }

  private static TreeNode createFirstTreeFromSlides() {
    int n = 17;
    List<List<Integer>> tree = createEmptyGraph(n);

    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 2);
    addUndirectedEdge(tree, 1, 3);
    addUndirectedEdge(tree, 1, 4);
    addUndirectedEdge(tree, 2, 5);
    addUndirectedEdge(tree, 2, 6);
    addUndirectedEdge(tree, 2, 7);
    addUndirectedEdge(tree, 3, 8);
    addUndirectedEdge(tree, 3, 9);
    addUndirectedEdge(tree, 5, 10);
    addUndirectedEdge(tree, 5, 11);
    addUndirectedEdge(tree, 7, 12);
    addUndirectedEdge(tree, 7, 13);
    addUndirectedEdge(tree, 11, 14);
    addUndirectedEdge(tree, 11, 15);
    addUndirectedEdge(tree, 11, 16);

    return TreeNode.rootTree(tree, 0);
  }
}
