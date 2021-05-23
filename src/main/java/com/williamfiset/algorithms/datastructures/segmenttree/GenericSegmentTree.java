/**
 * A generic segment tree implementation that supports several range update and aggregation
 * functions. This implementation of the segment tree differs from the `GenericSegmentTree2` impl in
 * that it stores the segment tree information inside multiple arrays for node.
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

import java.util.function.BinaryOperator;

public class GenericSegmentTree {

  // The type of segment combination function to use
  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX,
    GCD,
    PRODUCT
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
  private Long[] t;

  // The delta values associates with each segment. Used for lazy propagation
  // when doing range updates.
  private Long[] lazy;

  // The chosen range combination function
  private BinaryOperator<Long> combinationFn;

  // The Range Update Function (RUF) interface.
  private interface Ruf {
    // base = the existing value
    // tl, tr = the index value of the left/right endpoints, i.e: [tl, tr]
    // delta = the delta value
    // TODO(william): reorder to be base, delta, tl, tr
    Long apply(Long base, long tl, long tr, Long delta);
  }

  // The Range Update Function (RUF) that chooses how a lazy delta value is
  // applied to a segment.
  private Ruf ruf;

  // The Lazy Range Update Function (LRUF) associated with the RUF. How you
  // propagate the lazy delta values is sometimes different than how you apply
  // them to the current segment (but most of the time the RUF = LRUF).
  private Ruf lruf;

  private long safeSum(Long a, Long b) {
    if (a == null) a = 0L;
    if (b == null) b = 0L;
    return a + b;
  }

  private Long safeMul(Long a, Long b) {
    if (a == null) a = 1L;
    if (b == null) b = 1L;
    return a * b;
  }

  private Long safeMin(Long a, Long b) {
    if (a == null) return b;
    if (b == null) return a;
    return Math.min(a, b);
  }

  private Long safeMax(Long a, Long b) {
    if (a == null) return b;
    if (b == null) return a;
    return Math.max(a, b);
  }

  private BinaryOperator<Long> sumCombinationFn = (a, b) -> safeSum(a, b);
  private BinaryOperator<Long> minCombinationFn = (a, b) -> safeMin(a, b);
  private BinaryOperator<Long> maxCombinationFn = (a, b) -> safeMax(a, b);
  private BinaryOperator<Long> productCombinationFn = (a, b) -> safeMul(a, b);
  private BinaryOperator<Long> gcdCombinationFn =
      (a, b) -> {
        if (a == null) return b;
        if (b == null) return a;
        long gcd = a;
        while (b != 0) {
          gcd = b;
          b = a % b;
          a = gcd;
        }
        return Math.abs(gcd);
      };

  // TODO(william): Document the justification for each function below

  // Range update functions
  private Ruf minQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);
  private Ruf lminQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  // TODO(issue/208): Can negative multiplication updates be supported?
  private Ruf minQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lminQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf minQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lminQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf maxQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);
  private Ruf lmaxQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  // TODO(issue/208): Can negative multiplication updates be supported?
  private Ruf maxQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lmaxQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf maxQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lmaxQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf sumQuerySumUpdate = (b, tl, tr, d) -> b + (tr - tl + 1) * d;
  private Ruf lsumQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  private Ruf sumQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lsumQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf sumQueryAssignUpdate = (b, tl, tr, d) -> (tr - tl + 1) * d;
  private Ruf lsumQueryAssignUpdate = (b, tl, tr, d) -> d;

  // TODO(william): confirm this cannot be supported? Can we maintain additional
  // information to make it possible?
  private Ruf gcdQuerySumUpdate = (b, tl, tr, d) -> null;
  private Ruf lgcdQuerySumUpdate = (b, tl, tr, d) -> null;

  private Ruf gcdQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);
  private Ruf lgcdQueryMulUpdate = (b, tl, tr, d) -> safeMul(b, d);

  private Ruf gcdQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lgcdQueryAssignUpdate = (b, tl, tr, d) -> d;

  private Ruf productQuerySumUpdate = (b, tl, tr, d) -> b + (long) (Math.pow(d, (tr - tl + 1)));
  private Ruf lproductQuerySumUpdate = (b, tl, tr, d) -> safeSum(b, d);

  private Ruf productQueryMulUpdate = (b, tl, tr, d) -> b * (long) (Math.pow(d, (tr - tl + 1)));
  private Ruf lproductQueryMulUpdate =
      (b, tl, tr, d) -> safeMul(b, d); // safeMul(b, (long)(Math.pow(d, (tr - tl + 1))));

  private Ruf productQueryAssignUpdate = (b, tl, tr, d) -> d;
  private Ruf lproductQueryAssignUpdate = (b, tl, tr, d) -> d;

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

    // The size of the segment tree `t`
    //
    // TODO(william): Investigate to reduce this space. There are only 2n-1 segments, so we should
    // be able to reduce the space, but may need to reorganize the tree/queries. One idea is to use
    // the Eulerian tour structure of the tree to densely pack the segments.
    int N = 4 * n;

    t = new Long[N];
    // TODO(william): Change this to be of size n to reduce memory from O(4n) to O(3n)
    lazy = new Long[N];

    // Select the specified combination function
    if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
      combinationFn = sumCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = sumQuerySumUpdate;
        lruf = lsumQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = sumQueryAssignUpdate;
        lruf = lsumQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = sumQueryMulUpdate;
        lruf = lsumQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
      combinationFn = minCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = minQuerySumUpdate;
        lruf = lminQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = minQueryAssignUpdate;
        lruf = lminQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = minQueryMulUpdate;
        lruf = lminQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.MAX) {
      combinationFn = maxCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = maxQuerySumUpdate;
        lruf = lmaxQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = maxQueryAssignUpdate;
        lruf = lmaxQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = maxQueryMulUpdate;
        lruf = lmaxQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.GCD) {
      combinationFn = gcdCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = gcdQuerySumUpdate;
        lruf = lgcdQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = gcdQueryAssignUpdate;
        lruf = lgcdQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = gcdQueryMulUpdate;
        lruf = lgcdQueryMulUpdate;
      }
    } else if (segmentCombinationFunction == SegmentCombinationFn.PRODUCT) {
      combinationFn = productCombinationFn;
      if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
        ruf = productQuerySumUpdate;
        lruf = lproductQuerySumUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
        ruf = productQueryAssignUpdate;
        lruf = lproductQueryAssignUpdate;
      } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
        ruf = productQueryMulUpdate;
        lruf = lproductQueryMulUpdate;
      }
    } else {
      throw new UnsupportedOperationException(
          "Combination function not supported: " + segmentCombinationFunction);
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
    // Different segment tree types have different base cases
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
    return combinationFn.apply(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  // Apply the lazy delta value to the current node and push it to the child segments
  private void propagate1(int i, int tl, int tr) {
    // No lazy value to propagate
    if (lazy[i] == null) {
      return;
    }
    // Apply the lazy delta to the current segment.
    t[i] = ruf.apply(t[i], tl, tr, lazy[i]);
    // Push the lazy delta to left/right segments for non-leaf nodes
    propagateLazy1(i, tl, tr, lazy[i]);
    lazy[i] = null;
  }

  private void propagateLazy1(int i, int tl, int tr, long delta) {
    // Ignore leaf segments
    if (tl == tr) return;
    lazy[2 * i + 1] = lruf.apply(lazy[2 * i + 1], tl, tr, delta);
    lazy[2 * i + 2] = lruf.apply(lazy[2 * i + 2], tl, tr, delta);
  }

  public void rangeUpdate1(int l, int r, long x) {
    rangeUpdate1(0, 0, n - 1, l, r, x);
  }

  private void rangeUpdate1(int i, int tl, int tr, int l, int r, long x) {
    propagate1(i, tl, tr);
    if (l > r) {
      return;
    }

    if (tl == l && tr == r) {
      t[i] = ruf.apply(t[i], tl, tr, x);
      propagateLazy1(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
    }
  }

  // // Updates the value at index `i` in the original `values` array to be `newValue`.
  // public void pointUpdate(int i, long newValue) {
  //   pointUpdate(0, i, 0, n - 1, newValue);
  // }

  // /**
  //  * Update a point value to a new value and update all affected segments, O(log(n))
  //  *
  //  * <p>Do this by performing a binary search to find the interval containing the point, then
  // update
  //  * the leaf segment with the new value, and re-compute all affected segment values on the
  //  * callback.
  //  *
  //  * @param i the index of the current segment in the tree
  //  * @param pos the target position to update
  //  * @param tl the left segment endpoint (inclusive)
  //  * @param tr the right segment endpoint (inclusive)
  //  * @param newValue the new value to update
  //  */
  // private void pointUpdate(int i, int pos, int tl, int tr, long newValue) {
  //   if (tl == tr) { // `tl == pos && tr == pos` might be clearer
  //     t[i] = newValue;
  //     return;
  //   }
  //   int tm = (tl + tr) / 2;
  //   if (pos <= tm) {
  //     // The point index `pos` is contained within the left segment [tl, tm]
  //     pointUpdate(2 * i + 1, pos, tl, tm, newValue);
  //   } else {
  //     // The point index `pos` is contained within the right segment [tm+1, tr]
  //     pointUpdate(2 * i + 2, pos, tm + 1, tr, newValue);
  //   }
  //   // Re-compute the segment value of the current segment on the callback
  //   // t[i] = rangeUpdateFn.apply(t[2 * i + 1], t[2 * i + 2]);
  //   t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  // }

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
    t();
    // sumQuerySumUpdateExample();
    // minQueryAssignUpdateExample();
    // gcdQueryMulUpdateExample();
    // gcdQueryAssignUpdateExample();
    // productQueryMulUpdateExample();
  }

  private static void productQueryMulUpdateExample() {
    //        0, 1, 2, 3
    long[] v = {3, 2, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.PRODUCT, RangeUpdateFn.MULTIPLICATION);

    int l = 0;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, q);

    // 3, 8, 8, 1
    // 3 * 8 * 8 * 1 = 192
    st.rangeUpdate1(1, 2, 4);
    q = st.rangeQuery1(l, r);
    if (q != 192) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));

    // 3, 8, 16, 2
    // 3 * 8 * 16 * 2 = 768
    st.rangeUpdate1(2, 3, 2);
    q = st.rangeQuery1(l, r);
    if (q != 768) System.out.println("Error");
    System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));

    // 12, 24, 24, 24, 48
    // st.rangeUpdate1(2, 3, 24);
    // l = 0;
    // r = 4;
    // q = st.rangeQuery1(l, r);
    // if (q != 12) System.out.println("Error");
    // System.out.printf("The product between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l,
    // r));
  }

  private static void gcdQueryMulUpdateExample() {
    //           0,  1, 2, 3,  4
    long[] v = {12, 24, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.MULTIPLICATION);

    int l = 0;
    int r = 2;
    long q = st.rangeQuery1(l, r);
    if (q != 3) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(2, 2, 2);
    q = st.rangeQuery1(l, r);
    if (q != 6) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));

    r = 1; // [l, r] = [0, 1]
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void gcdQueryAssignUpdateExample() {
    //           0,  1, 2, 3,  4
    long[] v = {12, 24, 3, 12, 48};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.ASSIGN);

    int l = 0;
    int r = 2;
    long q = st.rangeQuery1(l, r);
    if (q != 3) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, q);

    // 12, 24, 48, 12, 48
    st.rangeUpdate1(2, 2, 48);
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));

    // 12, 24, 24, 24, 48
    st.rangeUpdate1(2, 3, 24);
    l = 0;
    r = 4;
    q = st.rangeQuery1(l, r);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void sumQuerySumUpdateExample() {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 8) System.out.println("Error");
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(1, 3, 3);
    q = st.rangeQuery1(l, r);
    if (q != 17) System.out.println("Error");
    System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }

  private static void t() {
    long[] v = {1, 4, 3, 0, 5, 8, -2, 7, 5, 2, 9};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);
    st.printDebugInfo();
  }

  private static void minQueryAssignUpdateExample() {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);

    // System.out.println(java.util.Arrays.toString(st.t));

    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 1) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate1(1, 3, 3);
    l = 0;
    r = 1;
    q = st.rangeQuery1(l, r);
    if (q != 2) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  }
}
