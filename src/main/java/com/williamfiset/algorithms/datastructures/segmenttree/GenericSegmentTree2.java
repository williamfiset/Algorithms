/**
 * A generic segment tree implementation that supports several range update and aggregation
 * functions.
 *
 * <p>Run with: ./gradlew run -Palgorithm=datastructures.segmenttree.GenericSegmentTree2
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

public class GenericSegmentTree2 {

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

  private static class Segment { // implements PrintableNode
    // TODO(william): investigate if we really need this, it's unlikely that we do since it should
    // be able to implicitly determine the index.
    int i;

    Long value;
    Long lazy;

    // Used only for Min/Max mul queries. Used in an attempt to resolve:
    // https://github.com/williamfiset/Algorithms/issues/208
    Long min;
    Long max;

    // The range of the segment [tl, tr]
    int tl;
    int tr;

    public Segment(int i, Long value, Long min, Long max, int tl, int tr) {
      this.i = i;
      this.value = value;
      this.min = min;
      this.max = max;
      this.tl = tl;
      this.tr = tr;
    }

    // @Override
    // public PrintableNode getLeft() {
    //   return left;
    // }

    // @Override
    // public PrintableNode getRight() {
    //   return right;
    // }

    // @Override
    // public String getText() {
    //   return value.toString();
    // }

    @Override
    public String toString() {
      return String.format("[%d, %d], value = %d, lazy = %d", tl, tr, value, lazy);
    }
  }

  // The number of elements in the original input values array.
  private int n;

  // The segment tree represented as a binary tree of ranges where st[0] is the
  // root node and the left and right children of node i are i*2+1 and i*2+2.
  private Segment[] st;

  // The chosen range combination function
  private BinaryOperator<Long> combinationFn;

  private interface Ruf {
    Long apply(Segment segment, Long delta);
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

  // TODO(william): Document the justification for each function below

  // Range update functions
  private Ruf minQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  private Ruf lminQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  // // TODO(issue/208): support this multiplication update
  private Ruf minQueryMulUpdate =
      (s, x) -> {
        if (x == 0) {
          return 0L;
        } else if (x < 0) {
          // s.min was already calculated
          if (safeMul(s.value, x) == s.min) {
            return s.max;
          } else {
            return s.min;
          }
        } else {
          return safeMul(s.value, x);
        }
      };
  private Ruf lminQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf minQueryAssignUpdate = (s, x) -> x;
  private Ruf lminQueryAssignUpdate = (s, x) -> x;

  private Ruf maxQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  private Ruf lmaxQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  // TODO(issue/208): support this multiplication update
  private Ruf maxQueryMulUpdate =
      (s, x) -> {
        if (x == 0) {
          return 0L;
        } else if (x < 0) {
          if (safeMul(s.value, x) == s.min) {
            return s.max;
          } else {
            return s.min;
          }
        } else {
          return safeMul(s.value, x);
        }
      };
  private Ruf lmaxQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf maxQueryAssignUpdate = (s, x) -> x;
  private Ruf lmaxQueryAssignUpdate = (s, x) -> x;

  private Ruf sumQuerySumUpdate = (s, x) -> s.value + (s.tr - s.tl + 1) * x;
  private Ruf lsumQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  private Ruf sumQueryMulUpdate = (s, x) -> safeMul(s.value, x);
  private Ruf lsumQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  private Ruf sumQueryAssignUpdate = (s, x) -> (s.tr - s.tl + 1) * x;
  private Ruf lsumQueryAssignUpdate = (s, x) -> x;

  public GenericSegmentTree2(
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

    st = new Segment[N];

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
      st[i] = new Segment(i, values[tl], values[tl], values[tl], tl, tr);
      return;
    }
    int tm = (tl + tr) / 2;
    buildSegmentTree(2 * i + 1, tl, tm, values);
    buildSegmentTree(2 * i + 2, tm + 1, tr, values);

    Long segmentValue = combinationFn.apply(st[2 * i + 1].value, st[2 * i + 2].value);
    Long minValue = Math.min(st[2 * i + 1].min, st[2 * i + 2].min);
    Long maxValue = Math.max(st[2 * i + 1].max, st[2 * i + 2].max);
    Segment segment = new Segment(i, segmentValue, minValue, maxValue, tl, tr);

    st[i] = segment;
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
      return st[i].value;
    }
    int tm = (tl + tr) / 2;
    // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
    // [l, r], simply recurse on both segments and let the base case return the
    // default value for invalid intervals.
    return combinationFn.apply(
        rangeQuery1(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  // Apply the delta value to the current node and push it to the child segments
  private void propagate1(int i, int tl, int tr) {
    if (st[i].lazy != null) {
      // Only used for min/max mul queries
      st[i].min = st[i].min * st[i].lazy;
      st[i].max = st[i].max * st[i].lazy;

      // Apply the delta to the current segment.
      st[i].value = ruf.apply(st[i], st[i].lazy);
      // Push the delta to left/right segments for non-leaf nodes
      propagateLazy1(i, tl, tr, st[i].lazy);
      st[i].lazy = null;
    }
  }

  private void propagateLazy1(int i, int tl, int tr, long delta) {
    // Ignore leaf segments
    if (tl == tr) return;
    st[2 * i + 1].lazy = lruf.apply(st[2 * i + 1], delta);
    st[2 * i + 2].lazy = lruf.apply(st[2 * i + 2], delta);
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
      // Only used for min/max mul queries
      st[i].min = st[i].min * x;
      st[i].max = st[i].max * x;

      st[i].value = ruf.apply(st[i], x);
      propagateLazy1(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;
      // Instead of checking if [tl, tm] overlaps [l, r] and [tm+1, tr] overlaps
      // [l, r], simply recurse on both segments and let the base case disregard
      // invalid intervals.
      rangeUpdate1(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate1(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);

      st[i].value = combinationFn.apply(st[2 * i + 1].value, st[2 * i + 2].value);
      st[i].max = Math.max(st[2 * i + 1].max, st[2 * i + 2].max);
      st[i].min = Math.min(st[2 * i + 1].min, st[2 * i + 2].min);
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
    printDebugInfo(0);
    System.out.println();
  }

  private void printDebugInfo(int i) {
    System.out.println(st[i]);
    if (st[i].tl == st[i].tr) {
      return;
    }
    printDebugInfo(2 * i + 1);
    printDebugInfo(2 * i + 2);
  }

  ////////////////////////////////////////////////////
  //              Example usage:                    //
  ////////////////////////////////////////////////////

  public static void main(String[] args) {
    minQuerySumUpdate();
    sumQuerySumUpdateExample();
    minQueryAssignUpdateExample();
  }

  private static void minQuerySumUpdate() {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.MIN, RangeUpdateFn.ADDITION);

    int l = 1;
    int r = 3;
    long q = st.rangeQuery1(l, r);
    if (q != 1) System.out.println("Error");
    System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);

    st.printDebugInfo();
  }

  private static void sumQuerySumUpdateExample() {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

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

  private static void minQueryAssignUpdateExample() {
    //          0, 1, 2, 3,  4
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);

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
