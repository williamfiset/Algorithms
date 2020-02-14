package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class LowestCommonAncestorEulerTour {

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

  private boolean preprocessed;

  private int index;
  private TreeNode[] nodes;
  private int[] heights;

  private void preprocess(TreeNode root, int n) {
    index = 0;
    nodes = new TreeNode[n];
    heights = new int[n];

    dfs(root, 0);

    System.out.println(java.util.Arrays.toString(heights));
    System.out.println(java.util.Arrays.toString(nodes));

    // TODO(william): Implement RMQ data structure that supporting returning the index of the 
    // min value in the original array.
  }

  // Finds the lowest common ancestor of the nodes with id1 and id2.
  public TreeNode lca(TreeNode root, int id1, int id2, int n) {
    if (!preprocessed) {
      preprocess(root, n);
      preprocessed = true;
    }

    // TODO(william): to RMQ

    return null;
  }

  // Do Euler tour (preorder traversal)
  private void dfs(TreeNode node, int height) {
    if (node == null) {
      return;
    }
    nodes[index] = node;
    heights[index] = height;
    index++;

    for (TreeNode child : node.children()) {
      dfs(child, height+1);
    }
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
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour();
    TreeNode root = createFirstTreeFromSlides();
    System.out.println(solver.lca(root, 10, 15, 17));
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

    return LowestCommonAncestorEulerTour.rootTree(tree, 0);
  }

}
