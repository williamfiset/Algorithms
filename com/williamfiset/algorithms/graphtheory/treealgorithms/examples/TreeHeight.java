/**
 * Tree height example
 *
 * <p>Download the code: $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Change directory to the root of the Algorithms directory: $ cd Algorithms
 *
 * <p>Compile: $ javac
 * com/williamfiset/algorithms/graphtheory/treealgorithms/examples/TreeHeight.java
 *
 * <p>Run: $ java com/williamfiset/algorithms/graphtheory/treealgorithms/examples/TreeHeight
 *
 * <p>Time Complexity: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms.examples;

import java.util.*;

public class TreeHeight {

  public static class TreeNode {
    int value;
    TreeNode left, right;

    public TreeNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  // Returns the height of the binary tree which is the number of edges from the
  // root to the deepest leaf node, or -1 if the input is an empty tree.
  public static int treeHeight(TreeNode node) {
    // Handle empty tree edge case.
    if (node == null) return -1;
    if (isLeafNode(node)) return 0;
    return Math.max(treeHeight(node.left), treeHeight(node.right)) + 1;
  }

  private static boolean isLeafNode(TreeNode node) {
    return node.left == null && node.right == null;
  }

  /* Examples */

  public static void main(String[] args) {
    System.out.printf("Singleton height: %d\n", treeHeight(new TreeNode(0)));
    TreeNode root = makeTree();
    System.out.printf("Tree height: %d\n", treeHeight(root));

    // Prints:
    // Singleton height: 0
    // Tree height: 3
  }

  //        0
  //       / \
  //      1   2
  //     / \ / \
  //    3  4 5  6
  //   / \
  //  7   8
  private static TreeNode makeTree() {
    TreeNode node0 = new TreeNode(0);

    TreeNode node1 = new TreeNode(1);
    TreeNode node2 = new TreeNode(2);
    node0.left = node1;
    node0.right = node2;

    TreeNode node3 = new TreeNode(3);
    TreeNode node4 = new TreeNode(4);
    node1.left = node3;
    node1.right = node4;

    TreeNode node5 = new TreeNode(5);
    TreeNode node6 = new TreeNode(6);
    node2.left = node5;
    node2.right = node6;

    TreeNode node7 = new TreeNode(7);
    TreeNode node8 = new TreeNode(8);
    node3.left = node7;
    node3.right = node8;

    return node0;
  }
}
