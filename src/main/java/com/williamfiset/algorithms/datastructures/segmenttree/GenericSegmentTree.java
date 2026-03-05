/**
 * A generic segment tree implementation that supports several range update and aggregation
 * functions.
 *
 * <p>Run with: bazel run
 * //src/main/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTree
 *
 * <p>Several thanks to cp-algorithms for their great article on segment trees:
 * https://cp-algorithms.com/data_structures/segment_tree.html
 *
 * <p>Supported combinations of (SegmentCombinationFn, RangeUpdateFn):
 *
 * <ul>
 *   <li>SUM + {ADDITION, ASSIGN, MULTIPLICATION}
 *   <li>MIN + {ADDITION, ASSIGN, MULTIPLICATION}
 *   <li>MAX + {ADDITION, ASSIGN, MULTIPLICATION}
 *   <li>GCD + {ASSIGN, MULTIPLICATION}
 *   <li>PRODUCT + {ASSIGN, MULTIPLICATION}
 * </ul>
 *
 * <p>Unsupported (will throw UnsupportedOperationException):
 *
 * <ul>
 *   <li>GCD + ADDITION: gcd(a+d, b+d) cannot be derived from gcd(a,b) and d
 *   <li>PRODUCT + ADDITION: product(a_i + d) cannot be derived from product(a_i) and d
 * </ul>
 *
 * <p>NOTE: MIN/MAX + MULTIPLICATION may produce incorrect results when multiplying by negative
 * values, since the min can become the max and vice versa.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import java.util.function.BinaryOperator;

public class GenericSegmentTree {

  // The type of segment combination function to use.
  // This determines how child segments are merged to form the parent segment value.
  public static enum SegmentCombinationFn {
    SUM,
    MIN,
    MAX,
    GCD,
    PRODUCT
  }

  // When updating a range of values, modify the affected values using one of the following
  // functions. The choice of range update function affects how lazy values are applied and
  // propagated through the tree.
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

  // The delta values associated with each segment. Used for lazy propagation.
  private Long[] lazy;

  // The chosen range combination function used to merge two child segments into a parent.
  private BinaryOperator<Long> combinationFn;

  // The Range Update Function (RUF) interface.
  //
  // This functional interface defines how a delta (lazy) value is applied to transform a
  // segment's aggregated value. The parameters are:
  //   base  - the existing segment value
  //   tl,tr - the left/right endpoints of the segment range [tl, tr]
  //   delta - the pending lazy delta value
  //
  // The segment size (tr - tl + 1) is needed by some RUFs. For example, when applying
  // a SUM + ADDITION update, adding `d` to each of `count` elements increases the sum
  // by `count * d`.
  private interface Ruf {
    Long apply(Long base, long tl, long tr, Long delta);
  }

  // The Range Update Function (RUF) that determines how a lazy delta value is applied to
  // update a segment's value.
  private Ruf ruf;

  // The Lazy Range Update Function (LRUF) associated with the RUF. This determines how
  // lazy delta values are composed when propagated to children. For example:
  //   - ADDITION lazies compose by summing: new_lazy = old_lazy + delta
  //   - MULTIPLICATION lazies compose by multiplying: new_lazy = old_lazy * delta
  //   - ASSIGN lazies compose by overwriting: new_lazy = delta
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

  private long gcd(long a, long b) {
    while (b != 0) {
      long tmp = b;
      b = a % b;
      a = tmp;
    }
    return Math.abs(a);
  }

  // Reusable RUF lambdas shared across multiple query/update combos:
  //   addDelta    - composes two additive deltas, or applies an additive delta to a value
  //   mulDelta    - composes two multiplicative deltas, or applies a multiplicative delta
  //   assignDelta - overwrites with the new delta (used for ASSIGN updates)
  private final Ruf addDelta = (b, tl, tr, d) -> safeSum(b, d);
  private final Ruf mulDelta = (b, tl, tr, d) -> safeMul(b, d);
  private final Ruf assignDelta = (b, tl, tr, d) -> d;

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
    t = new Long[4 * n];
    lazy = new Long[4 * n];

    // Select the combination function
    switch (segmentCombinationFunction) {
      case SUM:
        combinationFn = (a, b) -> safeSum(a, b);
        break;
      case MIN:
        combinationFn = (a, b) -> safeMin(a, b);
        break;
      case MAX:
        combinationFn = (a, b) -> safeMax(a, b);
        break;
      case GCD:
        combinationFn =
            (a, b) -> {
              if (a == null) return b;
              if (b == null) return a;
              return gcd(a, b);
            };
        break;
      case PRODUCT:
        combinationFn = (a, b) -> safeMul(a, b);
        break;
    }

    // Select the range update function (ruf) and lazy propagation function (lruf).
    //
    // For most combos, the ruf and lruf are one of the three shared lambdas above.
    // Special cases arise when the segment size matters:
    //
    //   SUM + ADDITION:      ruf applies delta*count to the sum;     lruf adds deltas
    //   SUM + ASSIGN:        ruf sets sum to delta*count;            lruf overwrites
    //   SUM + MUL:           ruf multiplies the sum;                 lruf multiplies deltas
    //
    //   MIN/MAX + ADDITION:  ruf/lruf both add (min/max shift uniformly)
    //   MIN/MAX + ASSIGN:    ruf/lruf both overwrite
    //   MIN/MAX + MUL:       ruf/lruf both multiply (only correct for non-negative multipliers)
    //
    //   GCD + ASSIGN:        ruf/lruf both overwrite
    //   GCD + MUL:           ruf/lruf both multiply (gcd(a*d,b*d) = |d|*gcd(a,b))
    //
    //   PRODUCT + ASSIGN:    ruf sets product to d^count;            lruf overwrites
    //   PRODUCT + MUL:       ruf multiplies product by d^count;      lruf multiplies deltas
    //
    switch (segmentCombinationFunction) {
      case SUM:
        switch (rangeUpdateFunction) {
          case ADDITION:
            ruf = (b, tl, tr, d) -> b + (tr - tl + 1) * d;
            lruf = addDelta;
            break;
          case ASSIGN:
            ruf = (b, tl, tr, d) -> (tr - tl + 1) * d;
            lruf = assignDelta;
            break;
          case MULTIPLICATION:
            ruf = mulDelta;
            lruf = mulDelta;
            break;
        }
        break;

      case MIN:
        switch (rangeUpdateFunction) {
          case ADDITION:
            ruf = addDelta;
            lruf = addDelta;
            break;
          case ASSIGN:
            ruf = assignDelta;
            lruf = assignDelta;
            break;
          case MULTIPLICATION:
            ruf = mulDelta;
            lruf = mulDelta;
            break;
        }
        break;

      case MAX:
        switch (rangeUpdateFunction) {
          case ADDITION:
            ruf = addDelta;
            lruf = addDelta;
            break;
          case ASSIGN:
            ruf = assignDelta;
            lruf = assignDelta;
            break;
          case MULTIPLICATION:
            ruf = mulDelta;
            lruf = mulDelta;
            break;
        }
        break;

      case GCD:
        switch (rangeUpdateFunction) {
          case ADDITION:
            throw new UnsupportedOperationException(
                "Can't use GCD with range addition updates; gcd(a+d, b+d) "
                    + "cannot be computed from gcd(a,b) and d alone.");
          case ASSIGN:
            ruf = assignDelta;
            lruf = assignDelta;
            break;
          case MULTIPLICATION:
            ruf = mulDelta;
            lruf = mulDelta;
            break;
        }
        break;

      case PRODUCT:
        switch (rangeUpdateFunction) {
          case ADDITION:
            throw new UnsupportedOperationException(
                "Can't use PRODUCT with range addition updates; product(a_i + d) "
                    + "cannot be computed from product(a_i) and d alone.");
          case ASSIGN:
            ruf = (b, tl, tr, d) -> (long) Math.pow(d, tr - tl + 1);
            lruf = assignDelta;
            break;
          case MULTIPLICATION:
            ruf = (b, tl, tr, d) -> b * (long) Math.pow(d, tr - tl + 1);
            lruf = mulDelta;
            break;
        }
        break;
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
   * Returns the result of the aggregation function over the range [l, r] on the original `values`
   * array (including any updates made to it), O(log(n)).
   *
   * @param l the left endpoint of the range query (inclusive)
   * @param r the right endpoint of the range query (inclusive)
   */
  public Long rangeQuery(int l, int r) {
    return rangeQuery(0, 0, n - 1, l, r);
  }

  private Long rangeQuery(int i, int tl, int tr, int l, int r) {
    if (l > r) {
      return null;
    }
    propagate(i, tl, tr);
    if (tl == l && tr == r) {
      return t[i];
    }
    int tm = (tl + tr) / 2;
    return combinationFn.apply(
        rangeQuery(2 * i + 1, tl, tm, l, Math.min(tm, r)),
        rangeQuery(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r));
  }

  // Apply the lazy delta value to the current node and push it to the child segments.
  private void propagate(int i, int tl, int tr) {
    if (lazy[i] == null) {
      return;
    }
    t[i] = ruf.apply(t[i], tl, tr, lazy[i]);
    propagateLazy(i, tl, tr, lazy[i]);
    lazy[i] = null;
  }

  // Push the lazy delta to the left and right child segments. Leaf nodes are skipped
  // since they have no children.
  private void propagateLazy(int i, int tl, int tr, long delta) {
    if (tl == tr) return;
    lazy[2 * i + 1] = lruf.apply(lazy[2 * i + 1], tl, tr, delta);
    lazy[2 * i + 2] = lruf.apply(lazy[2 * i + 2], tl, tr, delta);
  }

  /**
   * Updates all elements in the range [l, r] using the configured range update function, O(log(n)).
   *
   * @param l the left endpoint of the range update (inclusive)
   * @param r the right endpoint of the range update (inclusive)
   * @param x the value to apply (added, multiplied, or assigned depending on the RangeUpdateFn)
   */
  public void rangeUpdate(int l, int r, long x) {
    rangeUpdate(0, 0, n - 1, l, r, x);
  }

  private void rangeUpdate(int i, int tl, int tr, int l, int r, long x) {
    propagate(i, tl, tr);
    if (l > r) {
      return;
    }
    if (tl == l && tr == r) {
      t[i] = ruf.apply(t[i], tl, tr, x);
      propagateLazy(i, tl, tr, x);
    } else {
      int tm = (tl + tr) / 2;
      rangeUpdate(2 * i + 1, tl, tm, l, Math.min(tm, r), x);
      rangeUpdate(2 * i + 2, tm + 1, tr, Math.max(l, tm + 1), r, x);
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
    sumQuerySumUpdateExample();
    minQueryAssignUpdateExample();
    gcdQueryMulUpdateExample();
    gcdQueryAssignUpdateExample();
    productQueryMulUpdateExample();
    productQueryAssignUpdateExample();
  }

  private static void sumQuerySumUpdateExample() {
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.SUM, RangeUpdateFn.ADDITION);

    int l = 1, r = 3;
    long q = st.rangeQuery(l, r);
    if (q != 8) System.out.println("Error");
    System.out.printf("The sum between [%d, %d] is: %d\n", l, r, q);
    st.rangeUpdate(1, 3, 3);
    q = st.rangeQuery(l, r);
    if (q != 17) System.out.println("Error");
    System.out.printf("The sum between [%d, %d] is: %d\n", l, r, q);
  }

  private static void minQueryAssignUpdateExample() {
    long[] v = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.MIN, RangeUpdateFn.ASSIGN);

    long q = st.rangeQuery(1, 3);
    if (q != 1) System.out.println("Error");
    System.out.printf("The min between [1, 3] is: %d\n", q);
    st.rangeUpdate(1, 3, 3);
    q = st.rangeQuery(0, 1);
    if (q != 2) System.out.println("Error");
    System.out.printf("The min between [0, 1] is: %d\n", q);
  }

  private static void gcdQueryMulUpdateExample() {
    long[] v = {12, 24, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.MULTIPLICATION);

    long q = st.rangeQuery(0, 2);
    if (q != 3) System.out.println("Error");
    System.out.printf("The gcd between [0, 2] is: %d\n", q);
    st.rangeUpdate(2, 2, 2);
    q = st.rangeQuery(0, 2);
    if (q != 6) System.out.println("Error");
    System.out.printf("The gcd between [0, 2] is: %d\n", q);
  }

  private static void gcdQueryAssignUpdateExample() {
    long[] v = {12, 24, 3, 12, 48};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.GCD, RangeUpdateFn.ASSIGN);

    long q = st.rangeQuery(0, 2);
    if (q != 3) System.out.println("Error");

    st.rangeUpdate(2, 2, 48);
    q = st.rangeQuery(0, 2);
    if (q != 12) System.out.println("Error");

    st.rangeUpdate(2, 3, 24);
    q = st.rangeQuery(0, 4);
    if (q != 12) System.out.println("Error");
    System.out.printf("The gcd between [0, 4] is: %d\n", q);
  }

  private static void productQueryMulUpdateExample() {
    long[] v = {3, 2, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.PRODUCT, RangeUpdateFn.MULTIPLICATION);

    long q = st.rangeQuery(0, 3);
    if (q != 12) System.out.println("Error");

    st.rangeUpdate(1, 2, 4); // {3, 8, 8, 1} -> 192
    q = st.rangeQuery(0, 3);
    if (q != 192) System.out.println("Error");

    st.rangeUpdate(2, 3, 2); // {3, 8, 16, 2} -> 768
    q = st.rangeQuery(0, 3);
    if (q != 768) System.out.println("Error");
    System.out.printf("The product between [0, 3] is: %d\n", q);
  }

  private static void productQueryAssignUpdateExample() {
    long[] v = {2, 3, 1, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(v, SegmentCombinationFn.PRODUCT, RangeUpdateFn.ASSIGN);

    long q = st.rangeQuery(0, 3);
    if (q != 30) System.out.println("Error");

    st.rangeUpdate(1, 2, 4); // {2, 4, 4, 5} -> 160
    q = st.rangeQuery(0, 3);
    if (q != 160) System.out.println("Error");
    System.out.printf("The product between [0, 3] is: %d\n", q);
  }
}
