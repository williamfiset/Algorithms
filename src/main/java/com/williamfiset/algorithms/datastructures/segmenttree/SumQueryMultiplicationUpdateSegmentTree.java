/**
 * Run with: ./gradlew run -Palgorithm=datastructures.segmenttree.SumQuerySumUpdateSegmentTree
 *
 * <p>Several thanks to cp-algorithms for their great article on segment trees:
 * https://cp-algorithms.com/data_structures/segment_tree.html
 *
 * <p>NOTE: This file is still a WIP
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class SumQueryMultiplicationUpdateSegmentTree {

  // The number of elements in the original input values array.
  private final int n;

  // The segment tree represented as a binary tree of ranges where t[0] is the
  // root node and the left and right children of node i are i*2+1 and i*2+2.
  private Long[] t;

  // The delta values associates with each segment. Used for lazy propagation
  // when doing range updates.
  private Long[] lazy;

  // Sum sumFunction
  private Long sumFunction(Long a, Long b) {
    if (a == null) a = 0L;
    if (b == null) b = 0L;
    return a + b;
  }

  // Multiplication range update function
  private Long multRuf(Long base, int tl, int tr, Long delta) {
    // When we hit a null value, multiply by 1 since this is the
    // multiplication identity, i.e: 1*x = x
    if (base == null) base = 1L;
    if (delta == null) delta = 1L;
    return base * delta;
  }

  // Lazy multiplication range update function
  private long multLruf(Long delta1, Long delta2) {
    // When we hit a null value, multiply by 1 since this is the
    // multiplication identity, i.e: 1*x = x
    if (delta1 == null) delta1 = 1L;
    if (delta2 == null) delta2 = 1L;
    // Multiply together the existing delta and the new delta to properly
    // propagate the changes.
    return delta1 * delta2;
  }

  public SumQueryMultiplicationUpdateSegmentTree(long[] values) {
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

    t = new Long[N];
    lazy = new Long[N];

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

    t[i] = sumFunction(t[2 * i + 1], t[2 * i + 2]);
  }

  /**
   * Returns the query of the range [l, r] on the original `values` array (+ any updates made to it)
   *
   * @param l the left endpoint of the range query (inclusive)
   * @param r the right endpoint of the range query (inclusive)
   */
  public Long rangeQuery1(int l, int r) {
    return rangeQuery1(0, 0, n - 1, l, r);
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
  private Long rangeQuery1(int i, int tl, int tr, int l, int r) {
    if (l > r) {
      return null;
    }
    propagate1(i, tl, tr);
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;
    // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
    // [l, r], simply recurse on both segments and let the base case return the
    // default value for invalid intervals.
    return sumFunction(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  public void rangeUpdate1(int l, int r, long x) {
    rangeUpdate1(0, 0, n - 1, l, r, x);
  }

  private void propagateLazy(int i, int tl, int tr, long val) {
    // Ignore leaf segments
    if (tl == tr) return;
    lazy[2 * i + 1] = multLruf(lazy[2 * i + 1], val);
    lazy[2 * i + 2] = multLruf(lazy[2 * i + 2], val);
  }

  private void propagate1(int i, int tl, int tr) {
    // Check for default value because you don't want to assign to the lazy
    // value if it's the default value.
    if (lazy[i] != null) {
      t[i] = multRuf(t[i], /*unused*/ 0, /*unused*/ 0, lazy[i]);
      // Push delta to left/right segments for non-leaf nodes
      propagateLazy(i, tl, tr, lazy[i]);
      lazy[i] = null;
    }
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {
    propagate1(i, tl, tr);
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = multRuf(t[i], /*unused*/ 0, /*unused*/ 0, x);
      propagateLazy(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = sumFunction(t[2 * i + 1], t[2 * i + 2]);
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
    SumQuerySumUpdateSegmentTree st = new SumQuerySumUpdateSegmentTree(v);
  }
}
