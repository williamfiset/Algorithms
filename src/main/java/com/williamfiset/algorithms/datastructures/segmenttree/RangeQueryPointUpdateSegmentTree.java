// NOTE: This file is a current WIP!
//
// Run with:
// ./gradlew run -Palgorithm=datastructures.segmenttree.RangeQueryPointUpdateSegmentTree
//
// Several thanks to cp-algorithms for their great article on segment trees:
// https://cp-algorithms.com/data_structures/segment_tree.html

package com.williamfiset.algorithms.datastructures.segmenttree;

public class RangeQueryPointUpdateSegmentTree {
  // TODO(william): make the members of this class private

  // Tree segment values.
  Integer[] t;

  // The number of values in the original input values array.
  int n;

  // The size of the segment tree `t`
  // NOTE: the size is not necessarily = number of segments.
  int N;

  public RangeQueryPointUpdateSegmentTree(int[] values) {
    if (values == null) {
      throw new NullPointerException("Segment tree values cannot be null.");
    }
    n = values.length;
    // TODO(william): Investigate to reduce this space. There are only 2n-1 segments, so we should
    // be able to reduce the space, but may need to reorganize the tree/queries. One idea is to use
    // the Eulerian tour structure of the tree to densely pack the segments.
    N = 4 * n;
    t = new Integer[N];

    buildTree(0, 0, n - 1, values);
    // System.out.println(java.util.Arrays.toString(values));
    // System.out.println(java.util.Arrays.toString(t));
  }

  /**
   * Builds the segment tree starting with leaf nodes and combining values on callback. This
   * construction method takes O(n) time since there are only 2n - 1 segments in the segment tree.
   *
   * @param i the index of the segment in the segment tree
   * @param l the left index of the range on the values array
   * @param r the right index of the range on the values array
   * @param values the initial values array
   *     <p>The range [l, r] over the values array is inclusive.
   */
  private int buildTree(int i, int tl, int tr, int[] values) {
    if (tl == tr) {
      return t[i] = values[tl];
    }
    int mid = (tl + tr) / 2;

    // TODO(william): fix segment index out of bounds issue
    // System.out.printf("Range [%d, %d] splits into: [%d, %d] and [%d, %d] | %d -> %d and %d\n", l,
    // r, l, mid, mid+1, r, i, tl, tr);
    int lSum = buildTree(2 * i + 1, tl, mid, values);
    int rSum = buildTree(2 * i + 2, mid + 1, tr, values);

    // TODO(william): Make generic to support min, max and other queries. One idea is to keep
    // segment multiple trees for each query type?
    return t[i] = lSum + rSum;
  }

  /**
   * Returns the sum of the range [l, r] in the original `values` array.
   *
   * @param l the left endpoint of the sum range query (inclusive)
   * @param r the right endpoint of the sum range query (inclusive)
   */
  public long sumQuery(int l, int r) {
    return sumQuery(0, 0, n - 1, l, r);
  }

  /**
   * @param i the index of the current segment in the tree
   * @param tl the left endpoint that the of the current segment
   * @param tr the right endpoint that the of the current segment
   * @param l the target left endpoint for the range query
   * @param r the target right endpoint for the range query
   */
  private long sumQuery(int i, int tl, int tr, int l, int r) {
    if (l > r) {
      return 0;
    }
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;
    // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
    // [l, r], simply recurse on both and return a sum of 0 if the interval is invalid.
    return sumQuery(2 * i + 1, tl, tm, l, Math.min(tm, r))
        + sumQuery(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
  }

  // Alternative implementation of summing that intelligently only digs into
  // the branches which overlap with the query [l, r]
  private long sumQuery2(int i, int tl, int tr, int l, int r) {
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;
    // Test how the current segment [tl, tr] overlaps with the query [l, r]
    boolean overlapsLeftSegment = (l <= tm);
    boolean overlapsRightSegment = (r > tm);
    if (overlapsLeftSegment && overlapsRightSegment) {
      return sumQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r))
          + sumQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
    } else if (overlapsLeftSegment) {
      return sumQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r));
    } else {
      return sumQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
    }
  }

  // Updates the segment tree to reflect that index `i` in the original `values` array was updated
  // to `newValue`.
  public void update(int i, int newValue) {
    update(0, i, 0, n - 1, newValue);
  }

  /**
   * Update a point segment to a new value and update all affected segments.
   *
   * <p>Do this by performing a binary search to find the interval containing the point, then update
   * the leaf segment with the new value, and re-compute all affected segment values on the
   * callback.
   *
   * @param at the index of the current segment in the tree
   * @param pos the target position to update
   * @param tl the left segment endpoint
   * @param tr the right segment endpoint
   * @param newValue the new value to update
   */
  private void update(int at, int pos, int tl, int tr, int newValue) {
    if (tl == tr) { // `tl == pos && tr == pos` might be clearer
      t[at] = newValue;
      return;
    }
    int tm = (tl + tr) / 2;
    // The point index `pos` is contained within the left segment [tl, tm]
    if (pos <= tm) {
      update(2 * at + 1, pos, tl, tm, newValue);
      // The point index `pos` is contained within the right segment [tm+1, tr]
    } else {
      update(2 * at + 2, pos, tm + 1, tr, newValue);
    }
    // Re-compute the segment value of the current segment on the callback
    t[at] = t[2 * at + 1] + t[2 * at + 2];
  }

  public static void main(String[] args) {
    int[] values = new int[6];
    java.util.Arrays.fill(values, 1);
    RangeQueryPointUpdateSegmentTree st = new RangeQueryPointUpdateSegmentTree(values);
    System.out.println(st.sumQuery(1, 4));

    st.update(1, 2);
    System.out.println(st.sumQuery(1, 1));
    System.out.println(st.sumQuery(0, 1));
    System.out.println(st.sumQuery(0, 2));
  }
}
