/**
 * bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:GenericSegmentTreeTest
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class GenericSegmentTreeTest {

  static int ITERATIONS = 250;
  static int MAX_N = 17;

  @Test
  public void testSumQuerySumUpdate_Simple() {
    long[] values = {1, 2, 3, 4, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            values,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery(0, 1)).isEqualTo(3);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(3);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(15);
  }

  @Test
  public void testSumQuerySumUpdate_RangeUpdate() {
    long[] ar = {1, 2, 1, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    st.rangeUpdate(0, 1, 5);
    st.rangeUpdate(3, 4, 2);
    st.rangeUpdate(0, 4, 3);

    assertThat(st.rangeQuery(0, 0)).isEqualTo(1 + 3 + 5);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(2 + 3 + 5);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(1 + 3);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(2 + 3 + 2);
    assertThat(st.rangeQuery(4, 4)).isEqualTo(2 + 3 + 1);

    assertThat(st.rangeQuery(0, 1)).isEqualTo(2 * 5 + 2 * 3 + 1 + 2);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(2 * 5 + 3 * 3 + 1 + 2 + 1);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(2 * 2 + 2 * 3 + 2 + 1);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(2 * 5 + 2 * 2 + 3 * 5 + 1 + 1 + 1 + 2 + 2);
  }

  @Test
  public void testSumQueryAssignUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    st.rangeUpdate(3, 4, 2);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(10);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(4);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(2);
    assertThat(st.rangeQuery(4, 4)).isEqualTo(2);

    st.rangeUpdate(1, 3, 4);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(16);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(6);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(6);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(4);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(4);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(12);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(8);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(8);

    st.rangeUpdate(2, 2, 5);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(17);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(11);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(11);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(13);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(5);
  }

  @Test
  public void testSumQueryMulUpdate_simple() {
    long[] ar = {1, 4, 5, 3, 2};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    st.rangeUpdate(1, 3, 3);

    assertThat(st.rangeQuery(1, 3)).isEqualTo(4 * 3 + 5 * 3 + 3 * 3);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(1 + 4 * 3 + 5 * 3 + 3 * 3 + 2);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(1 + 4 * 3 + 5 * 3);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(5 * 3 + 3 * 3 + 2);

    st.rangeUpdate(1, 3, 2);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(4 * 3 * 2 + 5 * 3 * 2 + 3 * 3 * 2);
  }

  @Test
  public void minQuerySumUpdates_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    st.rangeUpdate(0, 4, 1);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(0);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(0);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(5);

    st.rangeUpdate(3, 4, 4);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(2);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(2);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(4);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(9);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(4);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(2);

    st.rangeUpdate(1, 3, 3);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(3);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(3);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(4);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(5);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(3);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(5);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(7);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(12);
    assertThat(st.rangeQuery(4, 4)).isEqualTo(4);
  }

  @Test
  public void maxQuerySumUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MAX,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    st.rangeUpdate(0, 4, 1);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(5);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(3);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(4);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(5);

    st.rangeUpdate(3, 4, 4);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(9);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(3);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(9);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(9);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(9);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(9);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(4);

    st.rangeUpdate(1, 3, 3);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(12);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(7);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(12);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(12);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(3);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(5);
    assertThat(st.rangeQuery(2, 2)).isEqualTo(7);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(12);
    assertThat(st.rangeQuery(4, 4)).isEqualTo(4);
  }

  @Test
  public void maxQueryMulUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MAX,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    st.rangeUpdate(0, 4, 1);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(4);
  }

  @Test
  public void testGcdQueryAdditionUpdate_throwsException() {
    long[] v = {12, 24, 3};
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new GenericSegmentTree(
                v,
                GenericSegmentTree.SegmentCombinationFn.GCD,
                GenericSegmentTree.RangeUpdateFn.ADDITION));
  }

  @Test
  public void testProductQueryAdditionUpdate_throwsException() {
    long[] v = {2, 3, 4};
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            new GenericSegmentTree(
                v,
                GenericSegmentTree.SegmentCombinationFn.PRODUCT,
                GenericSegmentTree.RangeUpdateFn.ADDITION));
  }

  @Test
  public void testProductQueryMulUpdate_simple() {
    long[] v = {3, 2, 2, 1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    assertThat(st.rangeQuery(0, 3)).isEqualTo(12);

    st.rangeUpdate(1, 2, 4); // {3, 8, 8, 1} -> 192
    assertThat(st.rangeQuery(0, 3)).isEqualTo(192);

    st.rangeUpdate(2, 3, 2); // {3, 8, 16, 2} -> 768
    assertThat(st.rangeQuery(0, 3)).isEqualTo(768);
  }

  @Test
  public void testProductQueryAssignUpdate_simple() {
    long[] v = {2, 3, 1, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 3)).isEqualTo(30);

    st.rangeUpdate(1, 2, 4); // {2, 4, 4, 5} -> 160
    assertThat(st.rangeQuery(0, 3)).isEqualTo(160);

    st.rangeUpdate(0, 3, 3); // {3, 3, 3, 3} -> 81
    assertThat(st.rangeQuery(0, 3)).isEqualTo(81);
  }

  @Test
  public void testGcdQueryMulUpdate_simple() {
    long[] v = {12, 24, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.GCD,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    assertThat(st.rangeQuery(0, 2)).isEqualTo(3);
    st.rangeUpdate(2, 2, 2);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(6);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(12);
  }

  @Test
  public void testGcdQueryAssignUpdate_simple() {
    long[] v = {12, 24, 3, 12, 48};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.GCD,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 2)).isEqualTo(3);

    st.rangeUpdate(2, 2, 48); // {12, 24, 48, 12, 48}
    assertThat(st.rangeQuery(0, 2)).isEqualTo(12);

    st.rangeUpdate(2, 3, 24); // {12, 24, 24, 24, 48}
    assertThat(st.rangeQuery(0, 4)).isEqualTo(12);
  }

  // ======================================================
  //  Constructor validation tests
  // ======================================================

  @Test
  public void testNullValues_throwsException() {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            new GenericSegmentTree(
                null,
                GenericSegmentTree.SegmentCombinationFn.SUM,
                GenericSegmentTree.RangeUpdateFn.ADDITION));
  }

  @Test
  public void testNullCombinationFn_throwsException() {
    long[] v = {1, 2, 3};
    assertThrows(
        IllegalArgumentException.class,
        () -> new GenericSegmentTree(v, null, GenericSegmentTree.RangeUpdateFn.ADDITION));
  }

  @Test
  public void testNullRangeUpdateFn_throwsException() {
    long[] v = {1, 2, 3};
    assertThrows(
        IllegalArgumentException.class,
        () -> new GenericSegmentTree(v, GenericSegmentTree.SegmentCombinationFn.SUM, null));
  }

  // ======================================================
  //  Single element array tests
  // ======================================================

  @Test
  public void testSingleElement_sumAddition() {
    long[] v = {42};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery(0, 0)).isEqualTo(42);

    st.rangeUpdate(0, 0, 8);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(50);
  }

  @Test
  public void testSingleElement_minAssign() {
    long[] v = {7};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 0)).isEqualTo(7);

    st.rangeUpdate(0, 0, -3);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(-3);
  }

  @Test
  public void testSingleElement_productMul() {
    long[] v = {5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            v,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    assertThat(st.rangeQuery(0, 0)).isEqualTo(5);

    st.rangeUpdate(0, 0, 3);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(15);
  }

  // ======================================================
  //  MIN + ASSIGN simple test
  // ======================================================

  @Test
  public void testMinQueryAssignUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(-1);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(1);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(1);

    // Assign [1,3] = 5 -> {2, 5, 5, 5, -1}
    st.rangeUpdate(1, 3, 5);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(-1);
    assertThat(st.rangeQuery(0, 3)).isEqualTo(2);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(5);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(5);

    // Assign [0,4] = 0 -> {0, 0, 0, 0, 0}
    st.rangeUpdate(0, 4, 0);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(0);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(0);
  }

  // ======================================================
  //  MAX + ASSIGN simple test
  // ======================================================

  @Test
  public void testMaxQueryAssignUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MAX,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(4);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(3);

    // Assign [0,2] = 10 -> {10, 10, 10, 4, -1}
    st.rangeUpdate(0, 2, 10);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(10);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(4);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(10);

    // Assign [2,4] = -5 -> {10, 10, -5, -5, -5}
    st.rangeUpdate(2, 4, -5);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(10);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(-5);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(10);
  }

  // ======================================================
  //  MIN + MULTIPLICATION simple test
  // ======================================================

  @Test
  public void testMinQueryMulUpdate_simple() {
    long[] ar = {2, 1, 3, 4, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(1);

    // Multiply [0,4] by 2 -> {4, 2, 6, 8, 10}
    st.rangeUpdate(0, 4, 2);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(2);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(4);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(2);

    // Multiply [2,4] by 3 -> {4, 2, 18, 24, 30}
    st.rangeUpdate(2, 4, 3);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(18);
  }

  // ======================================================
  //  Edge case: multiply by zero
  // ======================================================

  @Test
  public void testSumQueryMulUpdate_multiplyByZero() {
    long[] ar = {5, 10, 15, 20};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    assertThat(st.rangeQuery(0, 3)).isEqualTo(50);

    // Multiply [1,2] by 0 -> {5, 0, 0, 20}
    st.rangeUpdate(1, 2, 0);
    assertThat(st.rangeQuery(0, 3)).isEqualTo(25);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(0);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(5);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(20);
  }

  // ======================================================
  //  Edge case: all same values
  // ======================================================

  @Test
  public void testAllSameValues_gcdAssign() {
    long[] ar = {6, 6, 6, 6, 6};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.GCD,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(6);

    // Assign [2,4] = 4 -> {6, 6, 4, 4, 4}
    st.rangeUpdate(2, 4, 4);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(2);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(6);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(4);
  }

  // ======================================================
  //  Edge case: update entire array
  // ======================================================

  @Test
  public void testUpdateEntireArray_sumAssign() {
    long[] ar = {1, 2, 3, 4, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    // Assign all to 7 -> {7, 7, 7, 7, 7}
    st.rangeUpdate(0, 4, 7);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(35);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(7);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(14);
  }

  @Test
  public void testUpdateEntireArray_maxAddition() {
    long[] ar = {3, -1, 7, 2, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MAX,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(7);

    // Add 10 to all -> {13, 9, 17, 12, 15}
    st.rangeUpdate(0, 4, 10);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(17);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(13);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(9);
  }

  // ======================================================
  //  Edge case: point updates (single index range update)
  // ======================================================

  @Test
  public void testPointUpdates_sumAddition() {
    long[] ar = {0, 0, 0, 0, 0};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    // Set individual elements via point updates
    st.rangeUpdate(0, 0, 1);
    st.rangeUpdate(1, 1, 2);
    st.rangeUpdate(2, 2, 3);
    st.rangeUpdate(3, 3, 4);
    st.rangeUpdate(4, 4, 5);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(15);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(1);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(12);
  }

  // ======================================================
  //  Negative values tests
  // ======================================================

  @Test
  public void testNegativeValues_sumAddition() {
    long[] ar = {-5, -3, -1, -4, -2};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(-15);

    // Add -5 to [1,3] -> {-5, -8, -6, -9, -2}
    st.rangeUpdate(1, 3, -5);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(-30);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(-23);
  }

  @Test
  public void testNegativeValues_minAddition() {
    long[] ar = {-5, -3, -1, -4, -2};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery(0, 4)).isEqualTo(-5);

    // Add 10 to [0,0] -> {5, -3, -1, -4, -2}
    st.rangeUpdate(0, 0, 10);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(-4);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(5);
  }

  // ======================================================
  //  Multiple overlapping range updates (non-SUM combos)
  // ======================================================

  @Test
  public void testOverlappingUpdates_minAssign() {
    long[] ar = {10, 20, 30, 40, 50};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    // Assign [0,3] = 5 -> {5, 5, 5, 5, 50}
    st.rangeUpdate(0, 3, 5);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(5);

    // Assign [2,4] = 3 -> {5, 5, 3, 3, 3}
    st.rangeUpdate(2, 4, 3);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(3);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(5);
    assertThat(st.rangeQuery(2, 4)).isEqualTo(3);

    // Assign [1,2] = 1 -> {5, 1, 1, 3, 3}
    st.rangeUpdate(1, 2, 1);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(1);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(5);
    assertThat(st.rangeQuery(1, 1)).isEqualTo(1);
    assertThat(st.rangeQuery(3, 4)).isEqualTo(3);
  }

  @Test
  public void testOverlappingUpdates_productMul() {
    long[] ar = {1, 2, 3, 4};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    // product = 1*2*3*4 = 24
    assertThat(st.rangeQuery(0, 3)).isEqualTo(24);

    // Multiply [0,1] by 2 -> {2, 4, 3, 4} -> product = 96
    st.rangeUpdate(0, 1, 2);
    assertThat(st.rangeQuery(0, 3)).isEqualTo(96);
    assertThat(st.rangeQuery(0, 1)).isEqualTo(8);

    // Multiply [1,2] by 3 -> {2, 12, 9, 4} -> product = 864
    st.rangeUpdate(1, 2, 3);
    assertThat(st.rangeQuery(0, 3)).isEqualTo(864);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(108);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(2);
    assertThat(st.rangeQuery(3, 3)).isEqualTo(4);
  }

  @Test
  public void testOverlappingUpdates_gcdMul() {
    long[] ar = {6, 12, 18};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.GCD,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

    // gcd(6, 12, 18) = 6
    assertThat(st.rangeQuery(0, 2)).isEqualTo(6);

    // Multiply [0,1] by 3 -> {18, 36, 18}, gcd = 18
    st.rangeUpdate(0, 1, 3);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(18);

    // Multiply [1,2] by 2 -> {18, 72, 36}, gcd = 18
    st.rangeUpdate(1, 2, 2);
    assertThat(st.rangeQuery(0, 2)).isEqualTo(18);
    assertThat(st.rangeQuery(1, 2)).isEqualTo(36);
  }

  // ======================================================
  //  Query before any updates (initial state)
  // ======================================================

  @Test
  public void testQueryWithoutUpdates_allCombos() {
    long[] ar = {6, 3, 12, 9};

    // SUM
    GenericSegmentTree sumSt =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.SUM,
            GenericSegmentTree.RangeUpdateFn.ADDITION);
    assertThat(sumSt.rangeQuery(0, 3)).isEqualTo(30);
    assertThat(sumSt.rangeQuery(1, 2)).isEqualTo(15);
    assertThat(sumSt.rangeQuery(0, 0)).isEqualTo(6);

    // MIN
    GenericSegmentTree minSt =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MIN,
            GenericSegmentTree.RangeUpdateFn.ADDITION);
    assertThat(minSt.rangeQuery(0, 3)).isEqualTo(3);
    assertThat(minSt.rangeQuery(2, 3)).isEqualTo(9);

    // MAX
    GenericSegmentTree maxSt =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.MAX,
            GenericSegmentTree.RangeUpdateFn.ADDITION);
    assertThat(maxSt.rangeQuery(0, 3)).isEqualTo(12);
    assertThat(maxSt.rangeQuery(0, 1)).isEqualTo(6);

    // GCD
    GenericSegmentTree gcdSt =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.GCD,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);
    assertThat(gcdSt.rangeQuery(0, 3)).isEqualTo(3);
    assertThat(gcdSt.rangeQuery(0, 2)).isEqualTo(3);
    assertThat(gcdSt.rangeQuery(2, 3)).isEqualTo(3);

    // PRODUCT
    GenericSegmentTree prodSt =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);
    assertThat(prodSt.rangeQuery(0, 3)).isEqualTo(6 * 3 * 12 * 9);
    assertThat(prodSt.rangeQuery(1, 2)).isEqualTo(36);
  }

  // ======================================================
  //  Product + Assign with overlapping updates
  // ======================================================

  @Test
  public void testProductQueryAssignUpdate_overlapping() {
    long[] ar = {1, 2, 3, 4, 5};
    GenericSegmentTree st =
        new GenericSegmentTree(
            ar,
            GenericSegmentTree.SegmentCombinationFn.PRODUCT,
            GenericSegmentTree.RangeUpdateFn.ASSIGN);

    // Assign [0,4] = 2 -> {2, 2, 2, 2, 2}, product = 32
    st.rangeUpdate(0, 4, 2);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(32);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(2);
    assertThat(st.rangeQuery(2, 3)).isEqualTo(4);

    // Assign [1,3] = 3 -> {2, 3, 3, 3, 2}, product = 108
    st.rangeUpdate(1, 3, 3);
    assertThat(st.rangeQuery(0, 4)).isEqualTo(108);
    assertThat(st.rangeQuery(1, 3)).isEqualTo(27);
    assertThat(st.rangeQuery(0, 0)).isEqualTo(2);
    assertThat(st.rangeQuery(4, 4)).isEqualTo(2);
  }

  // ======================================================
  //  MIN/MAX + MUL randomized test (positive-only values)
  // ======================================================

  @Test
  public void testMinMaxMulUpdate_randomized_positiveOnly() {
    GenericSegmentTree.SegmentCombinationFn[] combinationFns = {
      GenericSegmentTree.SegmentCombinationFn.MIN,
      GenericSegmentTree.SegmentCombinationFn.MAX,
    };

    for (GenericSegmentTree.SegmentCombinationFn combinationFn : combinationFns) {
      for (int n = 5; n < 50; n++) {
        long[] ar = TestUtils.randomLongArray(n, 1, 10);
        GenericSegmentTree st =
            new GenericSegmentTree(
                ar, combinationFn, GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

        for (int i = 0; i < n; i++) {
          int j = TestUtils.randValue(0, n - 1);
          int k = TestUtils.randValue(0, n - 1);
          int i1 = Math.min(j, k);
          int i2 = Math.max(j, k);

          j = TestUtils.randValue(0, n - 1);
          k = TestUtils.randValue(0, n - 1);
          int i3 = Math.min(j, k);
          int i4 = Math.max(j, k);

          // Only positive multipliers to avoid min/max sign-flip issues
          long randValue = TestUtils.randValue(1, 3);

          bruteForceMulRangeUpdate(ar, i3, i4, randValue);
          st.rangeUpdate(i3, i4, randValue);

          long bf;
          if (combinationFn == GenericSegmentTree.SegmentCombinationFn.MIN) {
            bf = bruteForceMin(ar, i1, i2);
          } else {
            bf = bruteForceMax(ar, i1, i2);
          }

          long segTreeAnswer = st.rangeQuery(i1, i2);
          assertThat(segTreeAnswer).isEqualTo(bf);
        }
      }
    }
  }

  // ======================================================
  //  PRODUCT randomized tests (small values to avoid overflow)
  // ======================================================

  @Test
  public void testProductMul_randomized() {
    for (int n = 5; n < 20; n++) {
      long[] ar = TestUtils.randomLongArray(n, 1, 3);
      GenericSegmentTree st =
          new GenericSegmentTree(
              ar,
              GenericSegmentTree.SegmentCombinationFn.PRODUCT,
              GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

      for (int i = 0; i < n; i++) {
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);

        long randValue = TestUtils.randValue(1, 2);
        bruteForceMulRangeUpdate(ar, i3, i4, randValue);
        st.rangeUpdate(i3, i4, randValue);

        long bf = bruteForceMul(ar, i1, i2);
        assertThat(st.rangeQuery(i1, i2)).isEqualTo(bf);
      }
    }
  }

  @Test
  public void testProductAssign_randomized() {
    for (int n = 5; n < 20; n++) {
      long[] ar = TestUtils.randomLongArray(n, 1, 3);
      GenericSegmentTree st =
          new GenericSegmentTree(
              ar,
              GenericSegmentTree.SegmentCombinationFn.PRODUCT,
              GenericSegmentTree.RangeUpdateFn.ASSIGN);

      for (int i = 0; i < n; i++) {
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);

        long randValue = TestUtils.randValue(1, 3);
        bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
        st.rangeUpdate(i3, i4, randValue);

        long bf = bruteForceMul(ar, i1, i2);
        assertThat(st.rangeQuery(i1, i2)).isEqualTo(bf);
      }
    }
  }

  // ======================================================
  //  Randomized test for all valid function combinations
  // ======================================================

  @Test
  public void testAllFunctionCombinations() {
    GenericSegmentTree.SegmentCombinationFn[] combinationFns = {
      GenericSegmentTree.SegmentCombinationFn.SUM,
      GenericSegmentTree.SegmentCombinationFn.MIN,
      GenericSegmentTree.SegmentCombinationFn.MAX,
      GenericSegmentTree.SegmentCombinationFn.GCD,
    };

    GenericSegmentTree.RangeUpdateFn[] rangeUpdateFns = {
      GenericSegmentTree.RangeUpdateFn.ADDITION,
      GenericSegmentTree.RangeUpdateFn.ASSIGN,
      GenericSegmentTree.RangeUpdateFn.MULTIPLICATION
    };

    for (GenericSegmentTree.SegmentCombinationFn combinationFn : combinationFns) {
      for (GenericSegmentTree.RangeUpdateFn rangeUpdateFn : rangeUpdateFns) {

        // MIN/MAX + MULTIPLICATION may produce incorrect results with negative multipliers
        if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.MULTIPLICATION
            && (combinationFn == GenericSegmentTree.SegmentCombinationFn.MIN
                || combinationFn == GenericSegmentTree.SegmentCombinationFn.MAX)) {
          continue;
        }

        // GCD + ADDITION is not supported
        if (combinationFn == GenericSegmentTree.SegmentCombinationFn.GCD
            && rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ADDITION) {
          continue;
        }

        for (int n = 5, loop = 0; loop < ITERATIONS; loop++, n++) {

          // Prevent overflow for gcd multiplication tests
          if (n > MAX_N && combinationFn == GenericSegmentTree.SegmentCombinationFn.GCD) {
            n = MAX_N;
          }

          long[] ar = generateRandomArrayByTestType(n, combinationFn);
          GenericSegmentTree st = new GenericSegmentTree(ar, combinationFn, rangeUpdateFn);

          for (int i = 0; i < n; i++) {
            int j = TestUtils.randValue(0, n - 1);
            int k = TestUtils.randValue(0, n - 1);
            int i1 = Math.min(j, k);
            int i2 = Math.max(j, k);

            j = TestUtils.randValue(0, n - 1);
            k = TestUtils.randValue(0, n - 1);
            int i3 = Math.min(j, k);
            int i4 = Math.max(j, k);

            long randValue = getRandValueByTestType(combinationFn);

            if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ADDITION) {
              bruteForceSumRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ASSIGN) {
              bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.MULTIPLICATION) {
              bruteForceMulRangeUpdate(ar, i3, i4, randValue);
            }

            st.rangeUpdate(i3, i4, randValue);

            long bf = 0;

            if (combinationFn == GenericSegmentTree.SegmentCombinationFn.SUM) {
              bf = bruteForceSum(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree.SegmentCombinationFn.MIN) {
              bf = bruteForceMin(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree.SegmentCombinationFn.MAX) {
              bf = bruteForceMax(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree.SegmentCombinationFn.GCD) {
              bf = bruteForceGcd(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree.SegmentCombinationFn.PRODUCT) {
              bf = bruteForceMul(ar, i1, i2);
            }

            long segTreeAnswer = st.rangeQuery(i1, i2);
            if (bf != segTreeAnswer) {
              System.out.printf(
                  "Range query type: %s, range update type: %s, QUERY [%d, %d], want = %d, got = %d\n",
                  combinationFn, rangeUpdateFn, i1, i2, bf, segTreeAnswer);
              System.out.println(java.util.Arrays.toString(ar));
            }
            assertThat(segTreeAnswer).isEqualTo(bf);
          }
        }
      }
    }
  }

  private static long getRandValueByTestType(
      GenericSegmentTree.SegmentCombinationFn combinationFn) {
    if (combinationFn != GenericSegmentTree.SegmentCombinationFn.GCD) {
      return TestUtils.randValue(-10, 10);
    }
    return TestUtils.randValue(1, 10);
  }

  private static long[] generateRandomArrayByTestType(
      int n, GenericSegmentTree.SegmentCombinationFn combinationFn) {
    if (combinationFn != GenericSegmentTree.SegmentCombinationFn.GCD) {
      return TestUtils.randomLongArray(n, -100, +100);
    }
    return TestUtils.randomLongArray(n, 1, +10);
  }

  private static long bruteForceSum(long[] values, int l, int r) {
    long s = 0;
    for (int i = l; i <= r; i++) {
      s += values[i];
    }
    return s;
  }

  private static long bruteForceMin(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.min(m, values[i]);
    }
    return m;
  }

  private static long bruteForceMax(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.max(m, values[i]);
    }
    return m;
  }

  private static long bruteForceMul(long[] values, int l, int r) {
    long m = 1L;
    for (int i = l; i <= r; i++) {
      m *= values[i];
    }
    return m;
  }

  private static long gcd(long a, long b) {
    while (b != 0) {
      long tmp = b;
      b = a % b;
      a = tmp;
    }
    return Math.abs(a);
  }

  private static long bruteForceGcd(long[] values, int l, int r) {
    long s = values[l];
    for (int i = l; i <= r; i++) {
      s = gcd(s, values[i]);
    }
    return s;
  }

  private static void bruteForceSumRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] += x;
    }
  }

  private static void bruteForceMulRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] *= x;
    }
  }

  private static void bruteForceAssignRangeUpdate(long[] values, int l, int r, long x) {
    for (int i = l; i <= r; i++) {
      values[i] = x;
    }
  }
}
