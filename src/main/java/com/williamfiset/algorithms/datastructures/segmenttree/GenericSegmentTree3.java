/**
 * A generic segment tree implementation that supports several range update and aggregation
 * functions.
 *
 * <p>Run with: ./gradlew run -Palgorithm=datastructures.segmenttree.GenericSegmentTree3
 *
 * <p>Several thanks to cp-algorithms for their great article on segment trees:
 * https://cp-algorithms.com/data_structures/segment_tree.html
 *
 * <p>NOTE: This file is still a WIP
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

public class GenericSegmentTree3 {

  // // The type of segment combination function to use
  // public static enum SegmentCombinationFn {
  //   SUM,
  //   MIN,
  //   MAX
  // }

  // // When updating the value of a specific index position, or a range of values,
  // // modify the affected values using the following function:
  // public static enum RangeUpdateFn {
  //   // When a range update is issued, assign all the values in the range [l, r] to be `x`
  //   ASSIGN,
  //   // When a range update is issued, add a value of `x` to all the elements in the range [l, r]
  //   ADDITION,
  //   // When a range update is issued, multiply all elements in the range [l, r] by a value of `x`
  //   MULTIPLICATION
  // }

  // // TODO(william): Make this class static if possible to avoid sharing members with parent ST
  // class
  // private class SegmentNode implements PrintableNode {
  //   // TODO(william): investigate if we really need this, it's unlikely that we do since it
  // should
  //   // be able to implicitly determine the index.
  //   int i;

  //   Long value;
  //   Long lazy;

  //   // Used only for Min/Max mul queries. Used in an attempt to resolve:
  //   // https://github.com/williamfiset/Algorithms/issues/208
  //   Long min;
  //   Long max;

  //   // The range of the segment [l, r]
  //   int l;
  //   int r;

  //   // The two child segments of this segment (null otherwise).
  //   // Left segment is [l, m) and right segment is [m, r] where m = (l+r)/2
  //   SegmentNode left;
  //   SegmentNode right;

  //   public SegmentNode(int i, Long value, Long min, Long max, int l, int r) {
  //     this.i = i;
  //     this.value = value;
  //     this.min = min;
  //     this.max = max;
  //     this.l = l;
  //     this.r = r;
  //   }

  //   public Long rangeQuery1(int ll, int rr) {
  //     // Different segment tree types have different base cases
  //     if (ll > rr) {
  //       return null;
  //     }
  //     propagate1();
  //     if (exactOverlap(l, r)) {
  //       return value;
  //     }
  //     int m = (l + r) / 2;
  //     // Instead of checking if [ll, m] overlaps [l, r] and [m+1, rr] overlaps
  //     // [l, r], simply recurse on both segments and let the base case return the
  //     // default value for invalid intervals.
  //     return combinationFn.apply(
  //         rangeQuery1(left, l, Math.min(m, rr)),
  //         rangeQuery1(right, Math.max(ll, m + 1), rr));
  //   }

  //   // Apply the delta value to the current node and push it to the child segments
  //   public void propagate1() {
  //     if (lazy != null) {
  //       // Only used for min/max mul queries
  //       min = min * lazy;
  //       max = max * lazy;

  //       // Apply the delta to the current segment.
  //       value = ruf.apply(node, lazy);
  //       // Push the delta to left/right segments for non-leaf nodes
  //       propagateLazy1(lazy);
  //       lazy = null;
  //     }
  //   }

  //   public void propagateLazy1(long delta) {
  //     // Ignore leaf segments since they don't have children.
  //     if (isLeaf()) {
  //       return;
  //     }
  //     left.lazy = lruf.apply(left, delta);
  //     right.lazy = lruf.apply(right, delta);
  //   }

  //   public boolean exactOverlap(int ll, int rr) {
  //     return l == ll && r = rr;
  //   }

  //   public boolean isLeaf() {
  //     return l == r;
  //   }

  //   @Override
  //   public PrintableNode getLeft() {
  //     return left;
  //   }

  //   @Override
  //   public PrintableNode getRight() {
  //     return right;
  //   }

  //   @Override
  //   public String getText() {
  //     return value.toString();
  //   }

  //   @Override
  //   public String toString() {
  //     return String.format("[%d, %d], value = %d, lazy = %d", tl, tr, value, lazy);
  //   }
  // }

  // // The number of elements in the original input values array.
  // private int n;

  // private SegmentNode root;

  // // The chosen range combination function
  // private BinaryOperator<Long> combinationFn;

  // private interface Ruf {
  //   Long apply(SegmentNode segment, Long delta);
  // }

  // // The Range Update Function (RUF) that chooses how a lazy delta value is
  // // applied to a segment.
  // private Ruf ruf;

  // // The Lazy Range Update Function (LRUF) associated with the RUF. How you
  // // propagate the lazy delta values is sometimes different than how you apply
  // // them to the current segment (but most of the time the RUF = LRUF).
  // private Ruf lruf;

  // private long safeSum(Long a, Long b) {
  //   if (a == null) a = 0L;
  //   if (b == null) b = 0L;
  //   return a + b;
  // }

  // private Long safeMul(Long a, Long b) {
  //   if (a == null) a = 1L;
  //   if (b == null) b = 1L;
  //   return a * b;
  // }

  // private Long safeMin(Long a, Long b) {
  //   if (a == null) return b;
  //   if (b == null) return a;
  //   return Math.min(a, b);
  // }

  // private Long safeMax(Long a, Long b) {
  //   if (a == null) return b;
  //   if (b == null) return a;
  //   return Math.max(a, b);
  // }

  // private BinaryOperator<Long> sumCombinationFn = (a, b) -> safeSum(a, b);
  // private BinaryOperator<Long> minCombinationFn = (a, b) -> safeMin(a, b);
  // private BinaryOperator<Long> maxCombinationFn = (a, b) -> safeMax(a, b);

  // // TODO(william): Document the justification for each function below

  // // Range update functions
  // private Ruf minQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  // private Ruf lminQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  // // // TODO(issue/208): support this multiplication update
  // private Ruf minQueryMulUpdate =
  //     (s, x) -> {
  //       if (x == 0) {
  //         return 0L;
  //       } else if (x < 0) {
  //         // s.min was already calculated
  //         if (safeMul(s.value, x) == s.min) {
  //           return s.max;
  //         } else {
  //           return s.min;
  //         }
  //       } else {
  //         return safeMul(s.value, x);
  //       }
  //     };
  // private Ruf lminQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  // private Ruf minQueryAssignUpdate = (s, x) -> x;
  // private Ruf lminQueryAssignUpdate = (s, x) -> x;

  // private Ruf maxQuerySumUpdate = (s, x) -> safeSum(s.value, x);
  // private Ruf lmaxQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  // // TODO(issue/208): support this multiplication update
  // private Ruf maxQueryMulUpdate =
  //     (s, x) -> {
  //       if (x == 0) {
  //         return 0L;
  //       } else if (x < 0) {
  //         if (safeMul(s.value, x) == s.min) {
  //           return s.max;
  //         } else {
  //           return s.min;
  //         }
  //       } else {
  //         return safeMul(s.value, x);
  //       }
  //     };
  // private Ruf lmaxQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  // private Ruf maxQueryAssignUpdate = (s, x) -> x;
  // private Ruf lmaxQueryAssignUpdate = (s, x) -> x;

  // private Ruf sumQuerySumUpdate = (s, x) -> s.value + (s.tr - s.tl + 1) * x;
  // private Ruf lsumQuerySumUpdate = (s, x) -> safeSum(s.lazy, x);

  // private Ruf sumQueryMulUpdate = (s, x) -> safeMul(s.value, x);
  // private Ruf lsumQueryMulUpdate = (s, x) -> safeMul(s.lazy, x);

  // private Ruf sumQueryAssignUpdate = (s, x) -> (s.tr - s.tl + 1) * x;
  // private Ruf lsumQueryAssignUpdate = (s, x) -> x;

  // public GenericSegmentTree3(
  //     long[] values,
  //     SegmentCombinationFn segmentCombinationFunction,
  //     RangeUpdateFn rangeUpdateFunction) {
  //   if (values == null) {
  //     throw new IllegalArgumentException("Segment tree values cannot be null.");
  //   }
  //   if (segmentCombinationFunction == null) {
  //     throw new IllegalArgumentException("Please specify a valid segment combination function.");
  //   }
  //   if (rangeUpdateFunction == null) {
  //     throw new IllegalArgumentException("Please specify a valid range update function.");
  //   }
  //   n = values.length;

  //   // The size of the segment tree `t`
  //   //
  //   // TODO(william): Investigate to reduce this space. There are only 2n-1 segments, so we
  // should
  //   // be able to reduce the space, but may need to reorganize the tree/queries. One idea is to
  // use
  //   // the Eulerian tour structure of the tree to densely pack the segments.
  //   int N = 4 * n;

  //   // Select the specified combination function
  //   if (segmentCombinationFunction == SegmentCombinationFn.SUM) {
  //     combinationFn = sumCombinationFn;
  //     if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
  //       ruf = sumQuerySumUpdate;
  //       lruf = lsumQuerySumUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
  //       ruf = sumQueryAssignUpdate;
  //       lruf = lsumQueryAssignUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
  //       ruf = sumQueryMulUpdate;
  //       lruf = lsumQueryMulUpdate;
  //     }
  //   } else if (segmentCombinationFunction == SegmentCombinationFn.MIN) {
  //     combinationFn = minCombinationFn;
  //     if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
  //       ruf = minQuerySumUpdate;
  //       lruf = lminQuerySumUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
  //       ruf = minQueryAssignUpdate;
  //       lruf = lminQueryAssignUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
  //       ruf = minQueryMulUpdate;
  //       lruf = lminQueryMulUpdate;
  //     }
  //   } else if (segmentCombinationFunction == SegmentCombinationFn.MAX) {
  //     combinationFn = maxCombinationFn;
  //     if (rangeUpdateFunction == RangeUpdateFn.ADDITION) {
  //       ruf = maxQuerySumUpdate;
  //       lruf = lmaxQuerySumUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.ASSIGN) {
  //       ruf = maxQueryAssignUpdate;
  //       lruf = lmaxQueryAssignUpdate;
  //     } else if (rangeUpdateFunction == RangeUpdateFn.MULTIPLICATION) {
  //       ruf = maxQueryMulUpdate;
  //       lruf = lmaxQueryMulUpdate;
  //     }
  //   } else {
  //     throw new UnsupportedOperationException(
  //         "Combination function not supported: " + segmentCombinationFunction);
  //   }

  //   root = buildSegmentTree(0, 0, n - 1, values);
  // }

  // /**
  //  * Builds a segment tree by starting with the leaf nodes and combining segment values on
  // callback.
  //  *
  //  * @param i the index of the segment in the segment tree
  //  * @param l the left index (inclusive) of the segment range
  //  * @param r the right index (inclusive) of the segment range
  //  * @param values the initial values array
  //  */
  // private SegmentNode buildSegmentTree(int i, int l, int r, long[] values) {
  //   if (l == r) {
  //     return new SegmentNode(i, values[l], values[l], values[l], l, r);
  //   }
  //   int tm = (l + r) / 2;
  //   SegmentNode left = buildSegmentTree(2 * i + 1, l, tm, values);
  //   SegmentNode right = buildSegmentTree(2 * i + 2, tm + 1, r, values);

  //   Long segmentValue = combinationFn.apply(left.value, right.value);
  //   Long minValue = Math.min(left.min, right.min);
  //   Long maxValue = Math.max(left.max, right.max);

  //   // TODO(william): move assigning children to the constructor?
  //   Segment segment = new Segment(i, segmentValue, minValue, maxValue, l, r);
  //   segment.left = left;
  //   segment.right = right;

  //   return segment;
  // }

  // /**
  //  * Returns the query of the range [l, r] on the original `values` array (+ any updates made to
  // it)
  //  *
  //  * @param l the left endpoint of the range query (inclusive)
  //  * @param r the right endpoint of the range query (inclusive)
  //  */
  // public Long rangeQuery1(int l, int r) {
  //   return root.rangeQuery1(l, r);
  // }

  // public void rangeUpdate1(int l, int r, long x) {
  //   rangeUpdate1(node, l, r, x);
  // }

  // private void rangeUpdate1(SegmentNode node, int l, int r, long x) {
  //   node.propagate1();
  //   if (l > r) {
  //     return;
  //   }

  //   if (node.exactOverlap(l, r)) {
  //     // Only used for min/max mul queries
  //     node.min = node.min * x;
  //     node.max = node.max * x;

  //     node.value = ruf.apply(node, x);
  //     node.propagateLazy1(x);
  //   } else {
  //     int m = (l + r) / 2;
  //     // Instead of checking if [tl, m] overlaps [l, r] and [m+1, tr] overlaps
  //     // [l, r], simply recurse on both segments and let the base case disregard
  //     // invalid intervals.
  //     rangeUpdate1(node.left, l, Math.min(m, r), x);
  //     rangeUpdate1(node.right, Math.max(l, m + 1), r, x);

  //     node.value = combinationFn.apply(node.left.value, node.right.value);
  //     node.max = Math.max(node.left.max, node.right.max);
  //     node.min = Math.min(node.left.min, node.right.min);
  //   }
  // }

  // // // Updates the value at index `i` in the original `values` array to be `newValue`.
  // // public void pointUpdate(int i, long newValue) {
  // //   pointUpdate(0, i, 0, n - 1, newValue);
  // // }

  // // /**
  // //  * Update a point value to a new value and update all affected segments, O(log(n))
  // //  *
  // //  * <p>Do this by performing a binary search to find the interval containing the point, then
  // // update
  // //  * the leaf segment with the new value, and re-compute all affected segment values on the
  // //  * callback.
  // //  *
  // //  * @param i the index of the current segment in the tree
  // //  * @param pos the target position to update
  // //  * @param tl the left segment endpoint (inclusive)
  // //  * @param tr the right segment endpoint (inclusive)
  // //  * @param newValue the new value to update
  // //  */
  // // private void pointUpdate(int i, int pos, int tl, int tr, long newValue) {
  // //   if (tl == tr) { // `tl == pos && tr == pos` might be clearer
  // //     t[i] = newValue;
  // //     return;
  // //   }
  // //   int tm = (tl + tr) / 2;
  // //   if (pos <= tm) {
  // //     // The point index `pos` is contained within the left segment [tl, tm]
  // //     pointUpdate(2 * i + 1, pos, tl, tm, newValue);
  // //   } else {
  // //     // The point index `pos` is contained within the right segment [tm+1, tr]
  // //     pointUpdate(2 * i + 2, pos, tm + 1, tr, newValue);
  // //   }
  // //   // Re-compute the segment value of the current segment on the callback
  // //   // t[i] = rangeUpdateFn.apply(t[2 * i + 1], t[2 * i + 2]);
  // //   t[i] = combinationFn.apply(t[2 * i + 1], t[2 * i + 2]);
  // // }

  // public void printDebugInfo() {
  //   printDebugInfo(0);
  //   System.out.println();
  // }

  // private void printDebugInfo(int i) {
  //   // System.out.println(st[i]);
  //   // if (st[i].tl == st[i].tr) {
  //   //   return;
  //   // }
  //   // printDebugInfo(2 * i + 1);
  //   // printDebugInfo(2 * i + 2);
  // }

  // ////////////////////////////////////////////////////
  // //              Example usage:                    //
  // ////////////////////////////////////////////////////

  // public static void main(String[] args) {
  //   minQuerySumUpdate();
  //   sumQuerySumUpdateExample();
  //   minQueryAssignUpdateExample();
  // }

  // private static void minQuerySumUpdate() {
  //   //          0, 1, 2, 3,  4
  //   long[] v = {2, 1, 3, 4, -1};
  //   GenericSegmentTree3 st =
  //       new GenericSegmentTree3(v, SegmentCombinationFn.MIN, RangeUpdateFn.ADDITION);

  //   int l = 1;
  //   int r = 3;
  //   long q = st.rangeQuery1(l, r);
  //   if (q != 1) System.out.println("Error");
  //   System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);

  //   st.printDebugInfo();
  // }

  // private static void sumQuerySumUpdateExample() {
  //   //          0, 1, 2, 3,  4
  //   long[] v = {2, 1, 3, 4, -1};
  //   GenericSegmentTree3 st =
  //       new GenericSegmentTree3(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

  //   int l = 1;
  //   int r = 3;
  //   long q = st.rangeQuery1(l, r);
  //   if (q != 8) System.out.println("Error");
  //   System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, q);
  //   st.rangeUpdate1(1, 3, 3);
  //   q = st.rangeQuery1(l, r);
  //   if (q != 17) System.out.println("Error");
  //   System.out.printf("The sum between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  // }

  // private static void minQueryAssignUpdateExample() {
  //   //          0, 1, 2, 3,  4
  //   long[] v = {2, 1, 3, 4, -1};
  //   GenericSegmentTree3 st =
  //       new GenericSegmentTree3(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);

  //   int l = 1;
  //   int r = 3;
  //   long q = st.rangeQuery1(l, r);
  //   if (q != 1) System.out.println("Error");
  //   System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, q);
  //   st.rangeUpdate1(1, 3, 3);
  //   l = 0;
  //   r = 1;
  //   q = st.rangeQuery1(l, r);
  //   if (q != 2) System.out.println("Error");
  //   System.out.printf("The min between indeces [%d, %d] is: %d\n", l, r, st.rangeQuery1(l, r));
  // }
}
