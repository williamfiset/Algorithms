package com.williamfiset.algorithms.datastructures.segmenttree;

/**
 * Pointer-Based Segment Tree with Lazy Propagation
 *
 * A segment tree built with explicit left/right child pointers (rather than
 * a flat array). Supports range sum queries, range min queries, and range
 * updates (add a value to every element in an interval), all in O(log(n)).
 *
 * Lazy propagation defers updates to child nodes until they are actually
 * needed, keeping range updates at O(log(n)) instead of O(n).
 *
 * Each node covers a half-open interval [minPos, maxPos). Leaves cover a
 * single element [i, i+1). The combine function computes both sum and min
 * simultaneously as values propagate up.
 *
 * Use cases:
 *   - Range sum / min queries with range add updates
 *   - Problems requiring coordinate compression (easy to adapt constructor)
 *
 * Time:  O(n) construction, O(log(n)) per query and update
 * Space: O(n)
 *
 * @author Micah Stairs
 */
public class Node {

  private static final int INF = Integer.MAX_VALUE;

  private Node left, right;

  // This node covers the half-open interval [minPos, maxPos)
  private int minPos, maxPos;

  // Aggregate values for this node's range
  private int min = 0, sum = 0;

  // Pending update that hasn't been pushed to children yet
  private int lazy = 0;

  /**
   * Creates a segment tree from an array of values.
   *
   * @param values the initial values for the leaves
   * @throws IllegalArgumentException if values is null
   */
  public Node(int[] values) {
    if (values == null) throw new IllegalArgumentException("Null input to segment tree.");
    buildTree(0, values.length);
    for (int i = 0; i < values.length; i++) {
      update(i, i + 1, values[i]);
    }
  }

  /**
   * Creates an empty segment tree of the given size, with all values at 0.
   *
   * @param sz the number of elements
   */
  public Node(int sz) {
    buildTree(0, sz);
  }

  // Recursively builds the tree structure for the range [l, r).
  // Leaves cover [i, i+1); internal nodes split at the midpoint.
  private void buildTree(int l, int r) {
    if (l < 0 || r < 0 || r < l)
      throw new IllegalArgumentException("Illegal range: (" + l + "," + r + ")");

    minPos = l;
    maxPos = r;

    // Internal node — split at midpoint
    if (r - l > 1) {
      int mid = (l + r) / 2;
      left = new Node(l, mid);
      right = new Node(mid, r);
    }
  }

  private Node(int l, int r) {
    buildTree(l, r);
  }

  /**
   * Adds {@code change} to every element in the half-open interval [l, r).
   *
   * @param l      left endpoint (inclusive)
   * @param r      right endpoint (exclusive)
   * @param change the value to add to each element in [l, r)
   *
   * Time: O(log(n))
   */
  public void update(int l, int r, int change) {
    propagate();

    if (l <= minPos && maxPos <= r) {
      // Fully inside — apply update directly
      sum += change * (maxPos - minPos);
      min += change;
      // Lazily defer to children
      if (left != null) left.lazy += change;
      if (right != null) right.lazy += change;
    } else if (r <= minPos || l >= maxPos) {
      // No overlap
    } else {
      // Partial overlap — recurse into children.
      // Partial overlap only happens at internal nodes (leaves always
      // fully match or fully miss), so left and right are never null here.
      left.update(l, r, change);
      right.update(l, r, change);
      sum = left.sum + right.sum;
      min = Math.min(left.min, right.min);
    }
  }

  /**
   * Returns the sum of elements in the half-open interval [l, r).
   *
   * @param l left endpoint (inclusive)
   * @param r right endpoint (exclusive)
   * @return the sum of all elements in [l, r)
   *
   * Time: O(log(n))
   */
  public int sum(int l, int r) {
    propagate();
    if (l <= minPos && maxPos <= r) return sum;
    if (r <= minPos || l >= maxPos) return 0;
    return left.sum(l, r) + right.sum(l, r);
  }

  /**
   * Returns the minimum element in the half-open interval [l, r).
   *
   * @param l left endpoint (inclusive)
   * @param r right endpoint (exclusive)
   * @return the minimum value in [l, r)
   *
   * Time: O(log(n))
   */
  public int min(int l, int r) {
    propagate();
    if (l <= minPos && maxPos <= r) return min;
    if (r <= minPos || l >= maxPos) return INF;
    return Math.min(left.min(l, r), right.min(l, r));
  }

  /**
   * Applies any pending lazy update to this node and defers it to children.
   * Must be called before reading or modifying a node's values.
   */
  private void propagate() {
    if (lazy != 0) {
      sum += lazy * (maxPos - minPos);
      min += lazy;
      if (left != null) left.lazy += lazy;
      if (right != null) right.lazy += lazy;
      lazy = 0;
    }
  }
}
