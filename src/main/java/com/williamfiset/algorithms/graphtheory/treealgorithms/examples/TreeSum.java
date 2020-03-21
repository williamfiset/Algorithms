/**
 * Tree sum example
 *
 * <p>Download the code: <br>
 * $ git clone https://github.com/williamfiset/Algorithms
 *
 * <p>Run: <br>
 * $ ./gradlew run -Palgorithm=graphtheory.treealgorithms.examples.TreeSum
 *
 * <p>Time Complexity: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms.examples;

import java.util.*;

public class TreeSum {

  public static class TreeNode {
    int value;
    List<TreeNode> children = new ArrayList<>();

    public TreeNode(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public List<TreeNode> getChildren() {
      return children;
    }

    public void addChild(TreeNode... nodes) {
      for (TreeNode node : nodes) {
        children.add(node);
      }
    }
  }

  public static int treeSum(TreeNode node) {
    if (node == null) return 0;
    int total = 0;
    for (TreeNode child : node.getChildren()) total += treeSum(child);
    total += node.getValue();
    return total;
  }

  /* Examples */

  public static void main(String[] args) {
    TreeNode root = makeTree();
    System.out.printf("Tree sum: %d\n", treeSum(root));
  }

  private static TreeNode makeTree() {
    TreeNode root = new TreeNode(5);

    TreeNode node4 = new TreeNode(4);
    TreeNode node3 = new TreeNode(3);
    root.addChild(node4, node3);

    TreeNode node1 = new TreeNode(1);
    TreeNode nodem6 = new TreeNode(-6);
    node4.addChild(node1, nodem6);

    TreeNode node0 = new TreeNode(0);
    TreeNode node7 = new TreeNode(7);
    TreeNode nodem4 = new TreeNode(-4);
    node3.addChild(node0, node7, nodem4);

    TreeNode node2 = new TreeNode(2);
    TreeNode node9 = new TreeNode(9);
    node1.addChild(node2, node9);

    TreeNode node8 = new TreeNode(8);
    node7.addChild(node8);

    return root;
  }
}
