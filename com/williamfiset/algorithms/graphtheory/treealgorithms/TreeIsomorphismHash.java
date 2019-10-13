/** Determines if two rooted trees are isomorphic. */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class TreeIsomorphismHash {
  public static class TreeNode {
    private int id;
    private List<TreeNode> children;

    public TreeNode(int id) {
      this.id = id;
      children = new LinkedList<>();
    }

    public void addChildren(TreeNode... nodes) {
      for (TreeNode node : nodes) {
        children.add(node);
      }
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

  private static int MOD = 1_000_000_007;
  private static int PRIME = 131;
  private static int LEAF_VALUE = 37;

  public static boolean areIsomorphic(TreeNode root1, TreeNode root2) {
    return treeHash(root1) == treeHash(root2);
  }

  private static int treeHash(TreeNode node) {
    if (node == null || node.isLeaf()) {
      return LEAF_VALUE;
    }
    List<Integer> hashes = new LinkedList<>();
    for (TreeNode child : node.children()) {
      hashes.add(treeHash(child));
    }
    int hash = 1;
    Collections.sort(hashes);
    for (int h : hashes) {
      hash = ((PRIME * hash) ^ h) % MOD;
    }
    return hash;
  }

  public static void main(String[] args) {
    TreeNode root1 = new TreeNode(0);
    TreeNode n1 = new TreeNode(1);
    TreeNode n2 = new TreeNode(2);
    TreeNode n3 = new TreeNode(3);
    TreeNode n4 = new TreeNode(4);
    TreeNode n5 = new TreeNode(5);
    TreeNode n6 = new TreeNode(6);
    TreeNode n7 = new TreeNode(7);
    TreeNode n8 = new TreeNode(8);
    TreeNode n9 = new TreeNode(9);
    TreeNode n10 = new TreeNode(10);
    root1.addChildren(n1, n4, n8);
    n1.addChildren(n2, n3);
    n4.addChildren(n5, n6, n7);
    n6.addChildren(n10);
    n8.addChildren(n9);

    TreeNode root2 = new TreeNode(0);
    TreeNode x1 = new TreeNode(1);
    TreeNode x2 = new TreeNode(2);
    TreeNode x3 = new TreeNode(3);
    TreeNode x4 = new TreeNode(4);
    TreeNode x5 = new TreeNode(5);
    TreeNode x6 = new TreeNode(6);
    TreeNode x7 = new TreeNode(7);
    TreeNode x8 = new TreeNode(8);
    TreeNode x9 = new TreeNode(9);
    TreeNode x10 = new TreeNode(10);
    root2.addChildren(x4, x8, x1);
    x4.addChildren(x6, x5, x7);
    x7.addChildren(x10);
    x8.addChildren(x9);
    x1.addChildren(x3, x2);

    System.out.println(areIsomorphic(root1, root2));
  }
}
