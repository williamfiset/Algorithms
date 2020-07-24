/**
 * Run with: ./gradlew run -Palgorithm=datastructures.segmenttree.MinQueryAssignUpdateSegmentTree
 *
 * <p>Several thanks to cp-algorithms for their great article on segment trees:
 * https://cp-algorithms.com/data_structures/segment_tree.html
 *
 * <p>NOTE: This file is still a WIP
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import java.util.Arrays;

public class MinQueryAssignUpdateSegmentTree {

  // The number of elements in the original input values array.
  private int n;

  // The segment tree represented as a binary tree of ranges where t[0] is the
  // root node and the left and right children of node i are i*2+1 and i*2+2.
  private long[] t;

  // The delta values associates with each segment. Used for lazy propagation
  // when doing range updates.
  private long[] lazy;

  // TODO(william): change these values once you're done debugging overflow issues.
  private static long POS_INF = +99999999999999L;
  private static long NEG_INF = -99999999999999L;

  public MinQueryAssignUpdateSegmentTree(long[] values) {
    if (values == null) {
      throw new IllegalArgumentException("Segment tree values cannot be null.");
    }

    n = values.length;

    // The size of the segment tree `t`
    //
    // TODO(william): Investigate to reduce this space. There are only 2n-1 segments, so we should
    // be able to reduce the space, but may need to reorganize the tree/queries. One idea is to use
    // the Eulerian tour structure of the tree to densely pack the segments.
    int N = 4 * n;

    t = new long[N];
    lazy = new long[N];

    Arrays.fill(t, POS_INF);
    Arrays.fill(lazy, POS_INF);

    buildSegmentTree(0, 0, n - 1, values);
  }

  /**
   * Builds a segment tree by starting with the leaf nodes and combining segment values on callback.
   *
   * @param i the index of the segment in the segment tree
   * @param tl the left index (inclusive) of the segment range
   * @param tr the right index (inclusive) of the segment range
   * @param values the initial values array
   */
  private void buildSegmentTree(int i, int tl, int tr, long[] values) {
    if (tl == tr) {
      t[i] = values[tl];
      return;
    }
    int tm = (tl + tr) / 2;
    buildSegmentTree(2 * i + 1, tl, tm, values);
    buildSegmentTree(2 * i + 2, tm + 1, tr, values);

    t[i] = Math.min(t[2 * i + 1], t[2 * i + 2]);
  }

  private long defaultValue() {
    return POS_INF;
  }

  /**
   * Returns the query of the range [l, r] on the original `values` array (+ any updates made to it)
   *
   * @param l the left endpoint of the range query (inclusive)
   * @param r the right endpoint of the range query (inclusive)
   */
  public long rangeQuery1(int l, int r) {
    return rangeQuery1(0, 0, n - 1, l, r);
  }

  /**
   * Returns the query of the range [l, r] on the original `values` array (+ any updates made to it)
   *
   * @param l the left endpoint of the range query (inclusive)
   * @param r the right endpoint of the range query (inclusive)
   */
  public long rangeQuery2(int l, int r) {
    return rangeQuery2(0, 0, n - 1, l, r);
  }

  /**
   * Returns the range query value of the range [l, r]
   *
   * @param i the index of the current segment in the tree
   * @param tl the left endpoint (inclusive) of the current segment
   * @param tr the right endpoint (inclusive) of the current segment
   * @param l the target left endpoint (inclusive) for the range query
   * @param r the target right endpoint (inclusive) for the range query
   */
  private long rangeQuery1(int i, int tl, int tr, int l, int r) {
    if (l > r) {
      return defaultValue();
    }
    propagate1(i, tl, tr);
    if (tl == l && tr == r) {
      // System.out.printf("[%d, %d], t[i] = %d, lazy[i] = %d\n", tl, tr, t[i], lazy[i]);
      return t[i];
    }
    // System.out.printf("[%d, %d]\n", tl, tr);
    int tm = (tl + tr) / 2;
    // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
    // [l, r], simply recurse on both segments and let the base case return the
    // default value for invalid intervals.
    return Math.min(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  /**
   * Returns the range query value of the range [l, r]
   *
   * <p>An alternative implementation of the range query function that intelligently only digs into
   * the branches of the segment tree which overlap with the query [l, r].
   *
   * <p>This version of the range query implementation has the advantage that it doesn't need to
   * know the explicit base case value for each range query type.
   *
   * @param i the index of the current segment in the tree
   * @param tl the left endpoint (inclusive) of the current segment
   * @param tr the right endpoint (inclusive) of the current segment
   * @param l the target left endpoint (inclusive) for the range query
   * @param r the target right endpoint (inclusive) for the range query
   */
  private long rangeQuery2(int i, int tl, int tr, int l, int r) {
    if (tl == l && tr == r) {
      return t[i];
    }
    propagate2(i, tl, tr);
    int tm = (tl + tr) / 2;
    // Test how the left and right segments of the interval [tl, tr] overlap with the query [l, r]
    boolean overlapsLeftSegment = (l <= tm);
    boolean overlapsRightSegment = (r > tm);
    if (overlapsLeftSegment && overlapsRightSegment) {
      return Math.min(
          rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r)),
          rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
    } else if (overlapsLeftSegment) {
      return rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r));
    } else {
      return rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
    }
  }

  public void rangeUpdate1(int l, int r, long x) {
    rangeUpdate1(0, 0, n - 1, l, r, x);
  }

  private void propagate1(int i, int tl, int tr) {
    // Check for default value because you don't want to assign to the lazy
    // value if it's the default value.
    if (lazy[i] != defaultValue()) {
      t[i] = lazy[i];
      // Push delta to left/right segments for non-leaf nodes
      if (tl != tr) {
        lazy[2 * i + 1] = lazy[i];
        lazy[2 * i + 2] = lazy[i];
      }
    }

    lazy[i] = defaultValue();
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {
    propagate1(i, tl, tr);
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = x;
      if (tl != tr) {
        lazy[2 * i + 1] = x;
        lazy[2 * i + 2] = x;
      }
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = Math.min(t[2 * i + 1], t[2 * i + 2]);
    }
  }

  // Alternative range update impl that propagates a little differently.
  public void rangeUpdate2(int l, int r, long x) {
    rangeUpdate2(0, 0, n - 1, l, r, x);
  }

  // Propagates ahead so that when you lookup a value of a node it's already pre-propagated
  // in a sense. Cleans up the code a bit. You don't want to call this method on the leaf nodes.
  private void propagate2(int i, int tl, int tr) {
    // Check for default value because you don't want to assign to the lazy
    // value if it's the default value.
    if (lazy[i] != defaultValue()) {
      t[2 * i + 1] = lazy[i];
      lazy[2 * i + 1] = lazy[i];
      t[2 * i + 2] = lazy[i];
      lazy[2 * i + 2] = lazy[i];
    }

    lazy[i] = defaultValue();
  }

  private void rangeUpdate2(int i, int tl, int tr, int l, int r, long x) {
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = x;
      lazy[i] = x;
    } else {
      propagate2(i, tl, tr);
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate2(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = Math.min(t[2 * i + 1], t[2 * i + 2]);
    }
  }

  public void printDebugInfo() {
    printDebugInfo(0, 0, n - 1);
    System.out.println();
  }

  private void printDebugInfo(int i, int tl, int tr) {
    System.out.printf("[%d, %d], t[i] = %d, lazy[i] = %d\n", tl, tr, t[i], lazy[i]);
    if (tl == tr) {
      return;
    }
    int tm = (tl + tr) / 2;
    printDebugInfo(2 * i + 1, tl, tm);
    printDebugInfo(2 * i + 2, tm + 1, tr);
  }

  ////////////////////////////////////////////////////
  //              Example usage:                    //
  ////////////////////////////////////////////////////

  public static void main(String[] args) {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    MinQueryAssignUpdateSegmentTree st = new MinQueryAssignUpdateSegmentTree(v);
    st.printDebugInfo();
    System.out.println(st.rangeQuery1(0, 4)); // -1
    System.out.println(st.rangeQuery1(3, 3)); // 4
    System.out.println(st.rangeQuery1(4, 4)); // -1
    System.out.println(st.rangeQuery1(3, 4)); // -1
    System.out.println();

    //          0, 1, 2, 3, 4
    //     v = {2, 1, 3, 2, 2};
    st.rangeUpdate1(3, 4, 2);

    System.out.println(st.rangeQuery1(0, 4)); // 1
    System.out.println(st.rangeQuery1(3, 4)); // 2
    System.out.println(st.rangeQuery1(3, 3)); // 2
    System.out.println(st.rangeQuery1(4, 4)); // 2

    //          0, 1, 2, 3, 4
    //     v = {2, 4, 4, 4, 2};
    st.printDebugInfo();
    st.rangeUpdate1(1, 3, 4);
    st.printDebugInfo();

    System.out.println(st.rangeQuery1(0, 4)); // 2
    System.out.println(st.rangeQuery1(0, 1)); // 2
    System.out.println(st.rangeQuery1(3, 4)); // 2
    System.out.println(st.rangeQuery1(1, 1)); // 4
    System.out.println(st.rangeQuery1(2, 2)); // 4
    System.out.println(st.rangeQuery1(3, 3)); // 4
    System.out.println(st.rangeQuery1(1, 3)); // 4
    System.out.println(st.rangeQuery1(2, 3)); // 4
    System.out.println(st.rangeQuery1(1, 2)); // 4
  }
}
