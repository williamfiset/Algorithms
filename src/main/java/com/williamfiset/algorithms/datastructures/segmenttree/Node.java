/**
 * Segment Trees are an extremely useful data structure when dealing with ranges or intervals. They
 * take O(n) time and space to construct, but they can do range updates or queries in O(log(n))
 * time. This data structure is quite flexible; although the code below supports minimum and sum
 * queries, these could be modified to perform other types of queries. This implementation uses lazy
 * propagation (which allows for O(log(n)) range updates instead of O(n)). It should also be noted
 * that this implementation could easily be modified to support coordinate compression (you should
 * only have to change a few lines in the constructor).
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class Node {

  static final int INF = Integer.MAX_VALUE;

  Node left, right;
  int minPos, maxPos, min = 0, sum = 0, lazy = 0;

  public Node(int[] values) {
    if (values == null) throw new IllegalArgumentException("Null input to segment tree.");
    buildTree(0, values.length);
    for (int i = 0; i < values.length; i++) {
      update(i, i + 1, values[i]);
    }
  }

  public Node(int sz) {
    buildTree(0, sz);
  }

  private Node(int l, int r) {
    buildTree(l, r);
  }

  // Recursive method that builds the segment tree
  private void buildTree(int l, int r) {

    if (l < 0 || r < 0 || r < l)
      throw new IllegalArgumentException("Illegal range: (" + l + "," + r + ")");

    minPos = l;
    maxPos = r;

    // Reached leaf
    if (l == r - 1) {
      left = right = null;

      // Add children
    } else {
      int mid = (l + r) / 2;
      left = new Node(l, mid);
      right = new Node(mid, r);
    }
  }

  // Adjust all values in the interval [l, r) by a particular amount
  public void update(int l, int r, int change) {

    // Do lazy updates to children
    propagate();

    // Node's range fits inside query range
    if (l <= minPos && maxPos <= r) {

      sum += change * (maxPos - minPos);
      min += change;

      // Lazily propagate update to children
      if (left != null) left.lazy += change;
      if (right != null) right.lazy += change;

      // Ranges do not overlap
    } else if (r <= minPos || l >= maxPos) {

      // Do nothing

      // Ranges partially overlap
    } else {

      if (left != null) left.update(l, r, change);
      if (right != null) right.update(l, r, change);
      sum = (left == null ? 0 : left.sum) + (right == null ? 0 : right.sum);
      min = Math.min((left == null ? INF : left.min), (right == null ? INF : right.min));
    }
  }

  // Get the sum in the interval [l, r)
  public int sum(int l, int r) {

    // Do lazy updates to children
    propagate();

    // Node's range fits inside query range
    if (l <= minPos && maxPos <= r) return sum;

    // Ranges do not overlap
    else if (r <= minPos || l >= maxPos) return 0;

    // Ranges partially overlap
    else return (left == null ? 0 : left.sum(l, r)) + (right == null ? 0 : right.sum(l, r));
  }

  // Get the minimum value in the interval [l, r)
  public int min(int l, int r) {

    // Do lazy updates to children
    propagate();

    // Node's range fits inside query range
    if (l <= minPos && maxPos <= r) return min;

    // Ranges do not overlap
    else if (r <= minPos || l >= maxPos) return INF;

    // Ranges partially overlap
    else
      return Math.min(
          (left == null ? INF : left.min(l, r)), (right == null ? INF : right.min(l, r)));
  }

  // Does any updates to this node that haven't been done yet, and lazily updates its children
  // NOTE: This method must be called before updating or accessing a node
  private void propagate() {

    if (lazy != 0) {

      sum += lazy * (maxPos - minPos);
      min += lazy;

      // Lazily propagate updates to children
      if (left != null) left.lazy += lazy;
      if (right != null) right.lazy += lazy;

      lazy = 0;
    }
  }
}
