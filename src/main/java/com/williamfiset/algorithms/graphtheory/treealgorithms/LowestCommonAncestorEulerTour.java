/**
 * Implementation of finding the Lowest Common Ancestor (LCA) of a tree. This impl first finds an
 * Euler tour from the root node which visits all the nodes in the tree. The node height values
 * obtained from the Euler tour can then be used in combination with a sparse table to find the LCA
 * in O(1).
 *
 * <p>Time Complexity: O(1) queries, O(n*log2(n)) pre-processing.
 *
 * <p>Space Complexity: O(n*log2(n))
 *
 * <p>To run script:
 *
 * <p>./gradlew run -Palgorithm=graphtheory.treealgorithms.LowestCommonAncestorEulerTour
 *
 * @author William Fiset
 */
package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class LowestCommonAncestorEulerTour {

  public static void main(String[] args) {
    TreeNode root = createFirstTreeFromSlides();
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);

    // LCA of 13 and 14 = 2
    TreeNode lca = solver.lca(13, 14);
    System.out.printf("LCA of 13 and 14 = %s\n", lca);
    if (lca.index() != 2) {
      System.out.println("Error, expected lca to be 2");
    }

    // LCA of 9 and 11 = 0
    lca = solver.lca(9, 11);
    System.out.printf("LCA of 9 and 11 = %s\n", lca);
    if (lca.index() != 0) {
      System.out.println("Error, expected lca to be 0");
    }

    // LCA of 12 and 12 = 12
    lca = solver.lca(12, 12);
    System.out.printf("LCA of 12 and 12 = %s\n", lca);
    if (lca.index() != 12) {
      System.out.println("Error, expected lca to be 12");
    }
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

  public static class TreeNode {
    // Number of nodes in the subtree. Computed when tree is built.
    private int n;

    private int index;
    private TreeNode parent;
    private List<TreeNode> children;

    // Useful constructor for root node.
    public TreeNode(int index) {
      this(index, /* parent= */ null);
    }

    public TreeNode(int index, TreeNode parent) {
      this.index = index;
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

    public int index() {
      return index;
    }

    public TreeNode parent() {
      return parent;
    }

    public List<TreeNode> children() {
      return children;
    }

    public static TreeNode rootTree(List<List<Integer>> graph, int rootId) {
      TreeNode root = new TreeNode(rootId);
      TreeNode rootedTree = buildTree(graph, root);
      if (rootedTree.size() < graph.size()) {
        System.out.println(
            "WARNING: Input graph malformed. Did you forget to include all n-1 edges?");
      }
      return rootedTree;
    }

    // Do dfs to construct rooted tree.
    private static TreeNode buildTree(List<List<Integer>> graph, TreeNode node) {
      int subtreeNodeCount = 1;
      for (int neighbor : graph.get(node.index())) {
        // Ignore adding an edge pointing back to parent.
        if (node.parent() != null && neighbor == node.parent().index()) {
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
      return String.valueOf(index);
    }
  }

  private final int n;

  private int tourIndex = 0;

  // Populated when constructing Euler Tour.
  private long[] nodeDepth;
  private TreeNode[] nodeOrder;

  // The last occurrence mapping. This mapping keeps track of the last occurrence of a TreeNode in
  // the Euler tour for easy indexing.
  private int[] last;

  // Sparse table impl which can efficiently do Range Minimum Queries (RMQs).
  private MinSparseTable sparseTable;

  public LowestCommonAncestorEulerTour(TreeNode root) {
    this.n = root.size();
    setup(root);
  }

  private void setup(TreeNode root) {
    int eulerTourSize = 2 * n - 1;
    nodeOrder = new TreeNode[eulerTourSize];
    nodeDepth = new long[eulerTourSize];
    last = new int[n];

    // Do depth first search to construct Euler tour.
    dfs(root, /* depth= */ 0);

    // Initialize and build sparse table on the `nodeDepth` array which will
    // allow us to index into the `nodeOrder` array and return the LCA.
    sparseTable = new MinSparseTable(nodeDepth);
  }

  // Construct Euler Tour by populating the `nodeDepth` and `nodeOrder` arrays.
  private void dfs(TreeNode node, long depth) {
    if (node == null) {
      return;
    }

    visit(node, depth);
    for (TreeNode child : node.children()) {
      dfs(child, depth + 1);
      visit(node, depth);
    }
  }

  private void visit(TreeNode node, long depth) {
    nodeOrder[tourIndex] = node;
    nodeDepth[tourIndex] = depth;
    last[node.index()] = tourIndex;
    tourIndex++;
  }

  // Finds the lowest common ancestor of the nodes with `index1` and `index2`.
  public TreeNode lca(int index1, int index2) {
    int l = Math.min(last[index1], last[index2]);
    int r = Math.max(last[index1], last[index2]);
    int i = sparseTable.queryIndex(l, r);
    return nodeOrder[i];
  }

  // Sparse table for efficient minimum range queries in O(1) with O(nlogn) space
  private static class MinSparseTable {

    // The number of elements in the original input array.
    private int n;

    // The maximum power of 2 needed. This value is floor(log2(n))
    private int P;

    // Fast log base 2 logarithm lookup table, 1 <= i <= n
    private int[] log2;

    // The sparse table values.
    private long[][] dp;

    // Index Table (IT) associated with the values in the sparse table.
    private int[][] it;

    public MinSparseTable(long[] values) {
      init(values);
    }

    private void init(long[] v) {
      n = v.length;
      P = (int) (Math.log(n) / Math.log(2));
      dp = new long[P + 1][n];
      it = new int[P + 1][n];

      for (int i = 0; i < n; i++) {
        dp[0][i] = v[i];
        it[0][i] = i;
      }

      log2 = new int[n + 1];
      for (int i = 2; i <= n; i++) {
        log2[i] = log2[i / 2] + 1;
      }

      // Build sparse table combining the values of the previous intervals.
      for (int p = 1; p <= P; p++) {
        for (int i = 0; i + (1 << p) <= n; i++) {
          long leftInterval = dp[p - 1][i];
          long rightInterval = dp[p - 1][i + (1 << (p - 1))];
          dp[p][i] = Math.min(leftInterval, rightInterval);

          // Propagate the index of the best value
          if (leftInterval <= rightInterval) {
            it[p][i] = it[p - 1][i];
          } else {
            it[p][i] = it[p - 1][i + (1 << (p - 1))];
          }
        }
      }
    }

    // Returns the index of the minimum element in the range [l, r].
    public int queryIndex(int l, int r) {
      int len = r - l + 1;
      int p = log2[r - l + 1];
      long leftInterval = dp[p][l];
      long rightInterval = dp[p][r - (1 << p) + 1];
      if (leftInterval <= rightInterval) {
        return it[p][l];
      } else {
        return it[p][r - (1 << p) + 1];
      }
    }
  }
}
