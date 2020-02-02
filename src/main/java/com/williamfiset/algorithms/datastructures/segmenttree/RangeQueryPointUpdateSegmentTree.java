/** NOTE: This file is a current WIP */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class RangeQueryPointUpdateSegmentTree {

  // Tree values
  private int[] t;

  private int n;

  public RangeQueryPointUpdateSegmentTree(int[] values) {
    if (values == null) {
      throw new NullPointerException("Segment tree values cannot be null.");
    }
    n = values.length;
    t = new int[2 * n];

    // buildTree(0, 0, n-1);
  }

  // Builds tree bottom up starting with leaf nodes and combining
  // values on callback.
  // Range is inclusive: [l, r]
  private int buildTree(int i, int l, int r, int[] values) {
    if (l == r) {
      return 0;
    }
    int leftChild = (i * 2);
    int rightChild = (i * 2) + 1;
    int mid = (l + r) / 2;
    // TODO(will): herm... doesn't look quite righT?
    // t[i] = buildTree(leftChild, l, mid, values) + buildTree(rightChild, mid, r, values);

    return 0;
  }

  public int query(int l, int r) {
    return 0;
  }

  public void set(int i, int value) {
    // update(i, 0, n-1, value);
  }

  private void update(int at, int to, int l, int r, int value) {
    if (l == r) {
      return;
    } else {
      int lv = t[at * 2];
      int rv = t[at * 2 + 1];
      int m = (l + r) >>> 1;
      if (l <= r) {}
    }
  }
}
