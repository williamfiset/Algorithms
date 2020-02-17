package com.williamfiset.algorithms.graphtheory.treealgorithms;

import java.util.*;

public class LowestCommonAncestorEulerTour {

  public static class TreeNode {
    // Number of nodes in the subtree. Computed when tree is built.
    private int n;

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

  private int n;
  private TreeNode root;
  private boolean preprocessed;

  // Populated during Euler Tour.
  private long[] heights;
  private TreeNode[] nodeOrder;

  // The last occurrence mapping. This mapping keeps track of the last occurrence of a TreeNode in
  // the Euler tour for easy indexing. You could also keep track of the first occurrence of a node
  // and use that instead.
  private int[] last;

  private MinSparseTable sparseTable;

  public LowestCommonAncestorEulerTour(TreeNode root) {
    this.n = root.size();
    this.root = root;
  }

  private void preprocess() {
    int eulerTourSize = 2 * n - 1;
    nodeOrder = new TreeNode[eulerTourSize];
    heights = new long[eulerTourSize];
    last = new int[n];

    dfs(root, 0, 0);

    // Initialize and build sparse table on the heights which will allow us to
    // index into the nodeOrder array to return the LCA.
    sparseTable = new MinSparseTable(heights);
  }

  // Finds the lowest common ancestor of the nodeOrder with id1 and id2.
  public TreeNode lca(int id1, int id2) {
    // Lazily preprocess here instead of in constructor.
    if (!preprocessed) {
      preprocess();
      preprocessed = true;
    }

    int l = Math.min(last[id1], last[id2]);
    int r = Math.max(last[id1], last[id2]);
    int i = sparseTable.queryIndex(l, r);
    return nodeOrder[i];
  }

  // Construct Euler Tour by populating the 'heights' and 'nodeOrder' arrays.
  private int dfs(TreeNode node, long height, int index) {
    if (node == null) {
      return index;
    }
    nodeOrder[index] = node;
    heights[index] = height;
    last[node.id()] = index;
    index++;

    for (TreeNode child : node.children()) {
      index = dfs(child, height + 1, index);

      nodeOrder[index] = node;
      heights[index] = height;
      last[node.id()] = index;
      index++;
    }
    return index;
  }

  // Sparse table for efficient minimum range queries in O(1) with O(nlogn) space
  private static class MinSparseTable {

    // The number of elements in the original input array.
    private int n;

    // The maximum power of 2 needed. This value is floor(log2(n))
    private int P;

    // Fast log base 2 logarithm lookup table, 1 <= i <= n
    private int[] log2;

    // The sprase table values.
    private long[][] dp;

    // Index Table (IT) associated with the values in the sparse table.
    private int[][] it;

    public MinSparseTable(long[] values) {
      init(values);
    }

    public MinSparseTable(List<Long> values) {
      long[] v = new long[values.size()];
      for (int i = 0; i < values.size(); i++) {
        v[i] = values.get(i);
      }
      init(v);
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

  private static void p(int[] a) {
    System.out.print("[");
    for (int i = 0; i < a.length; i++) {
      System.out.printf("% 3d,", a[i]);
    }
    System.out.print("]");
    System.out.println();
  }

  private static void p(long[] a) {
    System.out.print("[");
    for (int i = 0; i < a.length; i++) {
      System.out.printf("% 3d,", a[i]);
    }
    System.out.print("]");
    System.out.println();
  }

  private static void p(TreeNode[] a) {
    System.out.print("[");
    for (int i = 0; i < a.length; i++) {
      System.out.printf("%3s,", a[i].toString());
    }
    System.out.print("]");
    System.out.println();
  }

  private static void pl(List<Long> a) {
    System.out.print("[");
    for (int i = 0; i < a.size(); i++) {
      System.out.printf("%3d,", a.get(i));
    }
    System.out.print("]");
    System.out.println();
  }

  private static void ptn(List<TreeNode> a) {
    System.out.print("[");
    for (int i = 0; i < a.size(); i++) {
      System.out.printf("%3s,", a.get(i).toString());
    }
    System.out.print("]");
    System.out.println();
  }

  private static TreeNode createSimpleTree() {
    int n = 6;
    List<List<Integer>> tree = createEmptyGraph(n);

    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 2);
    addUndirectedEdge(tree, 0, 3);
    addUndirectedEdge(tree, 2, 4);
    addUndirectedEdge(tree, 2, 5);

    return TreeNode.rootTree(tree, 0);
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

  private static TreeNode makeTestTree() {
    int n = 11;
    List<List<Integer>> tree = createEmptyGraph(n);

    addUndirectedEdge(tree, 0, 1);
    addUndirectedEdge(tree, 0, 7);
    addUndirectedEdge(tree, 1, 2);
    addUndirectedEdge(tree, 1, 3);
    addUndirectedEdge(tree, 3, 4);
    addUndirectedEdge(tree, 3, 5);
    addUndirectedEdge(tree, 3, 6);
    addUndirectedEdge(tree, 7, 8);
    addUndirectedEdge(tree, 7, 9);
    addUndirectedEdge(tree, 9, 10);

    return TreeNode.rootTree(tree, 0);
  }

  public static void main(String[] args) {
    TreeNode root = createSimpleTree();
    LowestCommonAncestorEulerTour solver = new LowestCommonAncestorEulerTour(root);
    System.out.println(solver.lca(4, 5));

    root = createFirstTreeFromSlides();
    solver = new LowestCommonAncestorEulerTour(root);
    System.out.println(solver.lca(10, 15)); // should be 5

    root = makeTestTree();
    solver = new LowestCommonAncestorEulerTour(root);
    System.out.println(solver.lca(2, 5)); // should be 1
  }
}
