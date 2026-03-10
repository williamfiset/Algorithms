package com.williamfiset.algorithms.datastructures.segmenttree;

import java.util.Arrays;

/**
 * Compact Array-Based Segment Tree
 *
 * A space-efficient segment tree stored in a flat array of size 2*n (no
 * recursion, no pointers). Supports point updates and range queries using
 * any associative combine function (sum, min, max, product, GCD, etc.).
 *
 * The tree is stored bottom-up: leaves occupy indices [n, 2n) and internal
 * nodes occupy [1, n). Index 0 is unused. Each internal node i is the
 * combination of its children at 2i and 2i+1.
 *
 * Use cases:
 *   - Range sum / min / max queries with point updates
 *   - Competitive programming (very short, cache-friendly implementation)
 *
 * Time:  O(n) construction, O(log(n)) per query and update
 * Space: O(n)
 *
 * @author Al.Cash & William Fiset, william.alexandre.fiset@gmail.com
 */
public class CompactSegmentTree {

  private int N;

  // Flat array storing the segment tree. Leaves are at indices [N, 2N),
  // internal nodes at [1, N). Index 0 is unused. Uninitialized slots
  // are null, which acts as the identity element for the combine function.
  private Long[] tree;

  /**
   * Creates an empty segment tree of the given size, with all slots
   * initialized to null.
   *
   * @param size the number of elements (leaves) in the segment tree
   */
  public CompactSegmentTree(int size) {
    tree = new Long[2 * (N = size)];
  }

  /**
   * Creates a segment tree from an array of values.
   *
   * @param values the initial leaf values
   */
  public CompactSegmentTree(long[] values) {
    this(values.length);
    for (int i = 0; i < N; i++) modify(i, values[i]);
  }

  /**
   * The associative combine function used for queries. This function must
   * satisfy f(f(a,b), c) = f(a, f(b,c)) for correct segment tree behavior.
   * Null acts as the identity element: f(null, x) = f(x, null) = x.
   *
   * Change this to customize the query type:
   *   return a + b;                 // sum over a range
   *   return (a > b) ? a : b;      // maximum over a range
   *   return (a < b) ? a : b;      // minimum over a range
   *   return a * b;                // product over a range (watch for overflow!)
   */
  private Long function(Long a, Long b) {
    if (a == null) return b;
    if (b == null) return a;
    return a + b;
  }

  /**
   * Updates the value at index i by combining it with the given value
   * using the combine function, then propagates changes up to the root.
   *
   * @param i     the leaf index to update (0-based)
   * @param value the value to combine at position i
   *
   * Time: O(log(n))
   */
  public void modify(int i, long value) {
    // Update the leaf node
    tree[i + N] = function(tree[i + N], value);
    // Propagate up: recompute each ancestor from its two children
    for (i += N; i > 1; i >>= 1) {
      tree[i >> 1] = function(tree[i], tree[i ^ 1]);
    }
  }

  /**
   * Queries the aggregate value over the half-open interval [l, r).
   *
   * Works by starting at the leaves and moving up. At each level, if the
   * left boundary is a right child, include it and move right. If the right
   * boundary is a right child, move left and include it.
   *
   * @param l left endpoint (inclusive, 0-based)
   * @param r right endpoint (exclusive, 0-based)
   * @return the combined result over [l, r)
   * @throws IllegalStateException if the query range is empty
   *
   * Time: O(log(n))
   */
  public long query(int l, int r) {
    Long res = null;
    for (l += N, r += N; l < r; l >>= 1, r >>= 1) {
      // If l is a right child, include it and move to next subtree
      if ((l & 1) != 0) res = function(res, tree[l++]);
      // If r is a right child, include its left sibling
      if ((r & 1) != 0) res = function(res, tree[--r]);
    }
    if (res == null) {
      throw new IllegalStateException("Empty query range.");
    }
    return res;
  }

  public static void main(String[] args) {
    long[] values = new long[] {1, 1, 1, 1, 1, 1};
    CompactSegmentTree st = new CompactSegmentTree(values);
    System.out.println(Arrays.toString(st.tree));
    System.out.println(st.query(0, 6)); // 6
    System.out.println(st.query(1, 5)); // 4
    System.out.println(st.query(0, 2)); // 2
  }
}
