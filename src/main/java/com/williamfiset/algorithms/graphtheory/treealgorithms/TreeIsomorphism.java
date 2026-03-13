/**
 * Tree Isomorphism — Canonical Encoding
 *
 * Determines if two unrooted trees are isomorphic (structurally identical
 * regardless of labeling). The algorithm works in three steps:
 *
 *   1. Find the center(s) of each tree by iteratively pruning leaf nodes.
 *      A tree has 1 or 2 centers.
 *   2. Root both trees at their center(s) and compute a canonical string
 *      encoding via DFS. Each subtree is encoded as "(children...)" with
 *      children sorted lexicographically so that isomorphic subtrees
 *      produce identical strings.
 *   3. Compare the encodings. If tree2 has two centers, try both — if
 *      either matches tree1's encoding, the trees are isomorphic.
 *
 * Can easily be adapted for rooted tree isomorphism by skipping step 1
 * and encoding directly from the given roots.
 *
 * Tested against: https://uva.onlinejudge.org/external/124/p12489.pdf
 *
 * Time:  O(V * log(V)) — dominated by sorting child encodings at each node
 * Space: O(V)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeIsomorphism {

  public static class TreeNode {
    private final int id;
    private final TreeNode parent;
    private final List<TreeNode> children;

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
  }

  /**
   * Returns true if the two unrooted trees are isomorphic.
   * Roots each tree at its center(s) and compares canonical encodings.
   */
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

  /**
   * Finds the center node(s) of the tree by iteratively removing leaf nodes.
   * A tree has either 1 center (odd diameter) or 2 centers (even diameter).
   */
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

  /** Recursively builds the rooted tree via DFS, skipping the edge back to parent. */
  private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
    for (int neighbor : graph.get(node.id())) {
      if (node.parent() != null && neighbor == node.parent().id()) {
        continue;
      }
      TreeNode child = new TreeNode(neighbor, node);
      node.addChildren(child);
      buildTree(graph, child);
    }
    return node;
  }

  /**
   * Constructs a canonical string encoding of the subtree rooted at the given node.
   * Children encodings are sorted lexicographically so that isomorphic subtrees
   * always produce the same string. Example: "((()())())" for a small tree.
   */
  public static String encode(TreeNode node) {
    if (node == null) {
      return "";
    }
    List<String> labels = new ArrayList<>();
    for (TreeNode child : node.children()) {
      labels.add(encode(child));
    }
    Collections.sort(labels);
    StringBuilder sb = new StringBuilder("(");
    for (String label : labels) {
      sb.append(label);
    }
    return sb.append(")").toString();
  }

  /* Graph helpers */

  public static List<List<Integer>> createEmptyGraph(int n) {
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
    simpleIsomorphismTest();
    testEncodingTreeFromSlides();
  }

  //  tree1 (rooted at center 2):    tree2 (rooted at center 1):
  //
  //        2                               1
  //      / | \                           / | \
  //     0  1  3                         0  3  2
  //           |                               |
  //           4                               4
  //
  //  Both are isomorphic — same structure, different labels.
  //
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

    // true
    System.out.println("Isomorphic: " + treesAreIsomorphic(tree1, tree2));
  }

  //  Rooted at node 0:
  //
  //           0
  //        /  |  \
  //       2   1   3
  //      / \ / \   \
  //     6  7 4  5   8
  //             |
  //             9
  //
  //  Canonical encoding: (((())())(()())(()))
  //
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

    // (((())())(()())(()))
    System.out.println("Encoding: " + encode(root0));
  }
}
