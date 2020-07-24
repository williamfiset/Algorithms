/**
 * A generic segment tree implementation that supports several range update and aggregation
 * functions.
 *
 * <p>Run with: ./gradlew run -Palgorithm=datastructures.segmenttree.GenericSegmentTree
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
import java.util.function.BinaryOperator;

public class GenericSegmentTree {

  // The type of segment combination function to use
  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX
  }

  // When updating the value of a specific index position, or a range of values,
  // modify the affected values using the following function:
  public static enum RangeUpdateFn {
    // When a range update is issued, assign all the values in the range [l, r] to be `x`
    ASSIGN,
    // When a range update is issued, add a value of `x` to all the elements in the range [l, r]
    ADDITION,
    // When a range update is issued, multiply all elements in the range [l, r] by a value of `x`
    MULTIPLICATION
  }

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

  private SegmentCombinationFn segmentCombinationFunction;
  private RangeUpdateFn rangeUpdateFunction;

  // The chosen range combination function
  private BinaryOperator<Long> combinationFn;

  // The chosen range update function
  private BinaryOperator<Long> rangeUpdateFn;

  private BinaryOperator<Long> sumFn = (a, b) -> a + b;
  private BinaryOperator<Long> mulFn = (a, b) -> a * b;
  private BinaryOperator<Long> minFn = (a, b) -> Math.min(a, b);
  private BinaryOperator<Long> maxFn = (a, b) -> Math.max(a, b);

  public GenericSegmentTree(long[] values, SegmentCombinationFn segmentCombinationFunction) {
    // By default, specify ADDITION as the range update function.
    this(values, segmentCombinationFunction, RangeUpdateFn.ADDITION);
  }

  public GenericSegmentTree(
      long[] values,
      SegmentCombinationFn segmentCombinationFunction,
      RangeUpdateFn rangeUpdateFunction) {
    if (values == null) {
      throw new IllegalArgumentException("Segment tree values cannot be null.");
    }
    if (segmentCombinationFunction == null) {
      throw new IllegalArgumentException("Please specify a valid segment combination function.");
    }
    if (rangeUpdateFunction == null) {
      throw new IllegalArgumentException("Please specify a valid range update function.");
    }
    n = values.length;
    this.segmentCombinationFunction = segmentCombinationFunction;
    this.rangeUpdateFunction = rangeUpdateFunction;

    // The size of the segment tree `t`
    //
    // TODO(william): Investigate to reduce this space. There are only 2n-1 segments, so we should
    // be able to reduce the space, but may need to reorganize the tree/queries. One idea is to use
    // the Eulerian tour structure of the tree to densely pack the segments.
    int N = 4 * n;

    t = new long[N];
    lazy = new long[N];

    // Select the specified combination function
    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      combinationFn = sumFn;
    } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
      Arrays.fill(t, POS_INF);
      Arrays.fill(lazy, POS_INF);
      combinationFn = minFn;
    } else if (segmentCombinationFunction == SegmentCombinationFn.MAX) {
      Arrays.fill(t, NEG_INF);
      Arrays.fill(lazy, NEG_INF);
      combinationFn = maxFn;
    }

    // Select the specified range update function
    if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
      rangeUpdateFn = sumFn;
    } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
      rangeUpdateFn = mulFn;
    }

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

    t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  }

  private long defaultValue() {
    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      return 0;
    } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
      return POS_INF;
    } else {
      return NEG_INF;
    }
  }

  /**
   * Returns the query of the range [l, r] on the original `values` array (+ any updates made to it)
   *
   * @param l the left endpoint of the range query (inclusive)
   * @param r the right endpoint of the range query (inclusive)
   */
  public long rangeQuery(int l, int r) {
    return rangeQuery(0, 0, n - 1, l, r);
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
  private long rangeQuery(int i, int tl, int tr, int l, int r) {
    if (l > r) {
      // Different segment tree types have different base cases
      return defaultValue();
    }
    propagateWithCombinationFn(i, tl, tr);
    // propagateWithRangeUpdateFn(i, tl, tr);
    if (tl == l && tr == r) {
      // System.out.printf("[%d, %d], t[i] = %d, lazy[i] = %d\n", tl, tr, t[i], lazy[i]);
      return t[i];
    }
    // System.out.printf("[%d, %d]\n", tl, tr);
    int tm = (tl + tr) / 2;
    // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
    // [l, r], simply recurse on both segments and let the base case return the
    // default value for invalid intervals.
    return combinationFn.apply(
        rangeQuery(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
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
    propagateWithCombinationFn(i, tl, tr);
    // propagateWithRangeUpdateFn(i, tl, tr);
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;
    // Test how the left and right segments of the interval [tl, tr] overlap with the query [l, r]
    boolean overlapsLeftSegment = (l <= tm);
    boolean overlapsRightSegment = (r > tm);
    if (overlapsLeftSegment && overlapsRightSegment) {
      return combinationFn.apply(
          rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r)),
          rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
    } else if (overlapsLeftSegment) {
      return rangeQuery2(2 * i + 1, tl, tm, l, Math.min(tm, r));
    } else {
      return rangeQuery2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r);
    }
  }

  // Updates the value at index `i` in the original `values` array to be `newValue`.
  public void pointUpdate(int i, long newValue) {
    pointUpdate(0, i, 0, n - 1, newValue);
  }

  /**
   * Update a point value to a new value and update all affected segments, O(log(n))
   *
   * <p>Do this by performing a binary search to find the interval containing the point, then update
   * the leaf segment with the new value, and re-compute all affected segment values on the
   * callback.
   *
   * @param i the index of the current segment in the tree
   * @param pos the target position to update
   * @param tl the left segment endpoint (inclusive)
   * @param tr the right segment endpoint (inclusive)
   * @param newValue the new value to update
   */
  private void pointUpdate(int i, int pos, int tl, int tr, long newValue) {
    if (tl == tr) { // `tl == pos && tr == pos` might be clearer
      t[i] = newValue;
      return;
    }
    int tm = (tl + tr) / 2;
    if (pos <= tm) {
      // The point index `pos` is contained within the left segment [tl, tm]
      pointUpdate(2 * i + 1, pos, tl, tm, newValue);
    } else {
      // The point index `pos` is contained within the right segment [tm+1, tr]
      pointUpdate(2 * i + 2, pos, tm + 1, tr, newValue);
    }
    // Re-compute the segment value of the current segment on the callback
    // t[i] = rangeUpdateFn.apply(t[2 * i + 1], t[2 * i + 2]);
    t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  }

  // Updates the range of values between [l, r] the segment tree with `x` based
  // on what RangeUpdateFn was chosen.
  public void rangeUpdate(int l, int r, long x) {
    if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
      rangeUpdate1(0, 0, n - 1, l, r, x);
    } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
      rangeUpdate2(0, 0, n - 1, l, r, x);
    } else {
      throw new UnsupportedOperationException(
          "This range update type is not supported yet: " + rangeUpdateFunction);
    }
  }

  private void propagateWithRangeUpdateFn(int i, int tl, int tr) {
    // TODO(william): What if range updates don't apply? Assume sum point updates?
    if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
      t[i] = rangeUpdateFn.apply(t[i], (tr - tl + 1) * lazy[i]);
    } else {
      throw new UnsupportedOperationException("Not supported ATM");
    }

    // Push delta to left/right segments
    if (tl != tr) {
      lazy[2 * i + 1] = rangeUpdateFn.apply(lazy[2 * i + 1], lazy[i]);
      lazy[2 * i + 2] = rangeUpdateFn.apply(lazy[2 * i + 2], lazy[i]);
    }
    lazy[i] = defaultValue();
  }

  private void propagateWithCombinationFn(int i, int tl, int tr) {
    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      t[i] = combinationFn.apply(t[i], (tr - tl + 1) * lazy[i]);
    } else {
      t[i] = combinationFn.apply(t[i], lazy[i]);
    }

    // Push delta to left/right segments
    if (tl != tr) {
      lazy[2 * i + 1] = combinationFn.apply(lazy[2 * i + 1], lazy[i]);
      lazy[2 * i + 2] = combinationFn.apply(lazy[2 * i + 2], lazy[i]);
    }
    lazy[i] = defaultValue();
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {

    // // TODO(william): re-factor into propagate method
    // t[i] = combinationFn.apply(t[i], (tr - tl + 1) * lazy[i]);
    // // Push delta to left/right segments
    // if (tl != tr) {
    //   lazy[2 * i + 1] = combinationFn.apply(lazy[2 * i + 1], lazy[i]);
    //   lazy[2 * i + 2] = combinationFn.apply(lazy[2 * i + 2], lazy[i]);
    // }
    // lazy[i] = defaultValue();

    propagateWithRangeUpdateFn(i, tl, tr);
    // propagateWithCombinationFn(i, tl, tr);

    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        t[i] = rangeUpdateFn.apply(t[i], (tr - tl + 1) * x);
      } else {
        throw new UnsupportedOperationException("Not supported ATM");
      }
      if (tl != tr) {
        lazy[2 * i + 1] = rangeUpdateFn.apply(lazy[2 * i + 1], x);
        lazy[2 * i + 2] = rangeUpdateFn.apply(lazy[2 * i + 2], x);
      }

      // t[i] = combinationFn.apply(t[i], (tr - tl + 1) * x);
      // if (tl != tr) {
      //   lazy[2 * i + 1] = combinationFn.apply(lazy[2 * i + 1], x);
      //   lazy[2 * i + 2] = combinationFn.apply(lazy[2 * i + 2], x);
      // }
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
      // t[i] = rangeUpdateFn.apply(t[2 * i + 1], t[2 * i + 2]);
    }
  }

  private void rangeUpdate2(int i, int tl, int tr, int l, int r, long x) {
    // propagateWithRangeUpdateFn(i, tl, tr);
    propagateWithCombinationFn(i, tl, tr);

    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = x;
      if (tl != tr) {
        lazy[2 * i + 1] = combinationFn.apply(lazy[2 * i + 1], x);
        lazy[2 * i + 2] = combinationFn.apply(lazy[2 * i + 2], x);
      }
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate2(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate2(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
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
    example1();
    // rangeSumQueryExample();
    // rangeMinQueryExample();
    // rangeMaxQueryExample();
    // additionRangeUpdateExample();
    // multiplicationRangeUpdateExample();
  }

  private static void example1() {
    long[] values = {2, 1, 3, 4, -1};
    GenericSegmentTree st = new GenericSegmentTree(values, SegmentCombinationFn.MIN);
    st.printDebugInfo();
  }

  private static void rangeSumQueryExample() {
    //               0  1  2  3
    long[] values = {1, 2, 3, 2};
    GenericSegmentTree st = new GenericSegmentTree(values, SegmentCombinationFn.SUM);

    int l = 0, r = 3;
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
    // Prints:
    // The sum between indeces [0, 3] is: 8
  }

  private static void rangeMinQueryExample() {
    //               0  1  2  3
    long[] values = {1, 2, 3, 2};
    GenericSegmentTree st = new GenericSegmentTree(values, SegmentCombinationFn.MIN);

    int l = 0, r = 3;
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
    // Prints:
    // The min between indeces [0, 3] is: 1
  }

  private static void rangeMaxQueryExample() {
    //               0  1  2  3
    long[] values = {1, 2, 3, 2};
    GenericSegmentTree st = new GenericSegmentTree(values, SegmentCombinationFn.MAX);

    int l = 0, r = 3;
    System.out.printf("The max between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
    // Prints:
    // The max between indeces [0, 3] is: 3
  }

  private static void additionRangeUpdateExample() {
    //               0  1  2  3  4
    long[] values = {1, 2, 3, 2, 1};
    GenericSegmentTree st = new GenericSegmentTree(values, SegmentCombinationFn.SUM);

    int l = 1, r = 3;
    st.rangeUpdate(1, 3, 4); // update [1, 3] with 4
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery(l, r));
    // Prints:
    // The sum between indeces [1, 3] is: 19
  }

  private static void multiplicationRangeUpdateExample() {
    //               0  1  2  3  4
    long[] values = {1, 2, 3, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(values, SegmentCombinationFn.SUM, RangeUpdateFn.MULTIPLICATION);

    int l = 1, r = 3;
    st.rangeUpdate(1, 3, 3); // Multiply each element in the range [1, 3] by 3
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery2(l, r));
    // Prints:
    // The sum between indeces [1, 3] is: 21
  }
}
