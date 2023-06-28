/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTree2Test"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.*;

public class GenericSegmentTree2Test {

  static int ITERATIONS = 100;
  static int MAX_N = 28;

  @BeforeEach
  public void setup() {}

  @Test
  public void testSumQuerySumUpdate_Simple() {
    long[] values = {1, 2, 3, 4, 5};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            values,
            GenericSegmentTree2.SegmentCombinationFn.SUM,
            GenericSegmentTree2.RangeUpdateFn.ADDITION);

    assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(3);
    assertThat(st.rangeQuery1(0, 4)).isEqualTo(15);
  }

  @Test
  public void testSumQuerySumUpdate_RangeUpdate() {
    //           0, 1, 2, 3, 4
    long[] ar = {1, 2, 1, 2, 1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.SUM,
            GenericSegmentTree2.RangeUpdateFn.ADDITION);

    // Do multiple range updates
    st.rangeUpdate1(0, 1, 5);
    st.rangeUpdate1(3, 4, 2);
    st.rangeUpdate1(0, 4, 3);

    // Point queries
    assertThat(st.rangeQuery1(0, 0)).isEqualTo(1 + 3 + 5);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(2 + 3 + 5);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(1 + 3);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(2 + 3 + 2);
    assertThat(st.rangeQuery1(4, 4)).isEqualTo(2 + 3 + 1);

    // Range queries
    assertThat(st.rangeQuery1(0, 1)).isEqualTo(2 * 5 + 2 * 3 + 1 + 2);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(2 * 5 + 3 * 3 + 1 + 2 + 1);
    assertThat(st.rangeQuery1(3, 4)).isEqualTo(2 * 2 + 2 * 3 + 2 + 1);
    assertThat(st.rangeQuery1(0, 4)).isEqualTo(2 * 5 + 2 * 2 + 3 * 5 + 1 + 1 + 1 + 2 + 2);
  }

  @Test
  public void testSumQueryAssignUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.SUM,
            GenericSegmentTree2.RangeUpdateFn.ASSIGN);

    st.rangeUpdate1(3, 4, 2);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(10);
    assertThat(st.rangeQuery1(3, 4)).isEqualTo(4);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(2);
    assertThat(st.rangeQuery1(4, 4)).isEqualTo(2);

    st.rangeUpdate1(1, 3, 4);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(16);
    assertThat(st.rangeQuery1(0, 1)).isEqualTo(6);
    assertThat(st.rangeQuery1(3, 4)).isEqualTo(6);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(4);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(4);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(12);
    assertThat(st.rangeQuery1(2, 3)).isEqualTo(8);
    assertThat(st.rangeQuery1(1, 2)).isEqualTo(8);

    st.rangeUpdate1(2, 2, 5);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(17);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(11);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(11);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(13);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(5);
  }

  @Test
  public void testSumQueryMulUpdate_simple() {
    long[] ar = {1, 4, 5, 3, 2};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.SUM,
            GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION);

    st.rangeUpdate1(1, 3, 3);

    assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 + 5 * 3 + 3 * 3);
    assertThat(st.rangeQuery1(0, 4)).isEqualTo(1 + 4 * 3 + 5 * 3 + 3 * 3 + 2);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(1 + 4 * 3 + 5 * 3);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(5 * 3 + 3 * 3 + 2);

    st.rangeUpdate1(1, 3, 2);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 * 2 + 5 * 3 * 2 + 3 * 3 * 2);
  }

  @Test
  public void minQuerySumUpdates_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MIN,
            GenericSegmentTree2.RangeUpdateFn.ADDITION);

    st.rangeUpdate1(0, 4, 1);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(0);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(2);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(0);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(5);

    st.rangeUpdate1(3, 4, 4);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(2);
    assertThat(st.rangeQuery1(0, 1)).isEqualTo(2);
    assertThat(st.rangeQuery1(3, 4)).isEqualTo(4);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(2);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(9);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(2);
    assertThat(st.rangeQuery1(2, 3)).isEqualTo(4);
    assertThat(st.rangeQuery1(1, 2)).isEqualTo(2);

    st.rangeUpdate1(1, 3, 3);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(3);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(3);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(4);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(5);
    assertThat(st.rangeQuery1(0, 0)).isEqualTo(3);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(5);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(7);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(12);
    assertThat(st.rangeQuery1(4, 4)).isEqualTo(4);
  }

  @Test
  public void maxQuerySumUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MAX,
            GenericSegmentTree2.RangeUpdateFn.ADDITION);

    // st.printDebugInfo();
    st.rangeUpdate1(0, 4, 1);
    // st.printDebugInfo();

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(5);
    // st.printDebugInfo();
    assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);

    assertThat(st.rangeQuery1(1, 2)).isEqualTo(4);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(5);

    st.rangeUpdate1(3, 4, 4);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(9);
    assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);
    assertThat(st.rangeQuery1(3, 4)).isEqualTo(9);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(2);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(9);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(9);
    assertThat(st.rangeQuery1(2, 3)).isEqualTo(9);
    assertThat(st.rangeQuery1(1, 2)).isEqualTo(4);

    st.rangeUpdate1(1, 3, 3);

    assertThat(st.rangeQuery1(0, 4)).isEqualTo(12);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(7);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(12);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(12);
    assertThat(st.rangeQuery1(0, 0)).isEqualTo(3);
    assertThat(st.rangeQuery1(1, 1)).isEqualTo(5);
    assertThat(st.rangeQuery1(2, 2)).isEqualTo(7);
    assertThat(st.rangeQuery1(3, 3)).isEqualTo(12);
    assertThat(st.rangeQuery1(4, 4)).isEqualTo(4);
  }

  @Test
  public void maxminQueryMulUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st1 =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MAX,
            GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION);
    GenericSegmentTree2 st2 =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MIN,
            GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION);

    st1.rangeUpdate1(0, 4, 1);
    st2.rangeUpdate1(0, 4, 1);

    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(4);
    assertThat(st2.rangeQuery1(0, 4)).isEqualTo(-1);

    // TODO(issue/208): Negative numbers are a known issue
    st1.rangeUpdate1(0, 4, -2);
    st2.rangeUpdate1(0, 4, -2);

    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(2);
    assertThat(st2.rangeQuery1(0, 4)).isEqualTo(-8);

    st1.rangeUpdate1(0, 4, -1);
    st2.rangeUpdate1(0, 4, -1);

    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(8);
    assertThat(st2.rangeQuery1(0, 4)).isEqualTo(-2);
  }

  @Test
  public void maxQueryMulUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st1 =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MAX,
            GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION);

    // [4, 2, 6, 8, -2]
    st1.rangeUpdate1(0, 4, 2);
    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(8);
    assertThat(st1.rangeQuery1(0, 0)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 1)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 2)).isEqualTo(6);
    assertThat(st1.rangeQuery1(1, 3)).isEqualTo(8);

    // [4, 2, 6, -16, 4]
    st1.rangeUpdate1(3, 4, -2);
    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(6);
    assertThat(st1.rangeQuery1(0, 0)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 1)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 2)).isEqualTo(6);
    assertThat(st1.rangeQuery1(1, 3)).isEqualTo(6);
    assertThat(st1.rangeQuery1(3, 4)).isEqualTo(4);
  }

  @Test
  public void minQueryMulUpdate_simple() {
    long[] ar = {2, 1, 3, 4, -1};
    GenericSegmentTree2 st1 =
        new GenericSegmentTree2(
            ar,
            GenericSegmentTree2.SegmentCombinationFn.MIN,
            GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION);

    // [4, 2, 6, 8, -2]
    st1.rangeUpdate1(0, 4, 2);
    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(-2);
    assertThat(st1.rangeQuery1(0, 0)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 1)).isEqualTo(2);
    assertThat(st1.rangeQuery1(0, 2)).isEqualTo(2);
    assertThat(st1.rangeQuery1(1, 3)).isEqualTo(2);

    // [4, 2, 6, -16, 4]
    st1.rangeUpdate1(3, 4, -2);
    assertThat(st1.rangeQuery1(0, 4)).isEqualTo(-16);
    assertThat(st1.rangeQuery1(0, 0)).isEqualTo(4);
    assertThat(st1.rangeQuery1(0, 1)).isEqualTo(2);
    assertThat(st1.rangeQuery1(0, 2)).isEqualTo(2);
    assertThat(st1.rangeQuery1(1, 3)).isEqualTo(-16);
    assertThat(st1.rangeQuery1(3, 4)).isEqualTo(-16);
  }

  // Test segment tree min/max with mul range updates. These tests have smaller
  // values to avoid overflow
  // @Test
  // public void testMinMax_mul() {
  //   GenericSegmentTree2.SegmentCombinationFn[] combinationFns = {
  //     GenericSegmentTree2.SegmentCombinationFn.MIN, GenericSegmentTree2.SegmentCombinationFn.MAX
  //   };

  //   GenericSegmentTree2.RangeUpdateFn[] rangeUpdateFns = {
  //     GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION
  //   };

  //   for (GenericSegmentTree2.SegmentCombinationFn combinationFn : combinationFns) {
  //     for (GenericSegmentTree2.RangeUpdateFn rangeUpdateFn : rangeUpdateFns) {

  //       for (int n = 5; n < 20; n++) {
  //         long[] ar = TestUtils.randomLongArray(n, -5, +5);
  //         GenericSegmentTree2 st =
  //             new GenericSegmentTree2(
  //                 ar, GenericSegmentTree2.SegmentCombinationFn.MIN, rangeUpdateFn);
  //         GenericSegmentTree2 st2 =
  //             new GenericSegmentTree2(
  //                 ar, GenericSegmentTree2.SegmentCombinationFn.MAX, rangeUpdateFn);
  //         System.out.println();

  //         for (int i = 0; i < n; i++) {
  //           int j = TestUtils.randValue(0, n - 1);
  //           int k = TestUtils.randValue(0, n - 1);
  //           int i1 = Math.min(j, k);
  //           int i2 = Math.max(j, k);

  //           j = TestUtils.randValue(0, n - 1);
  //           k = TestUtils.randValue(0, n - 1);
  //           int i3 = Math.min(j, k);
  //           int i4 = Math.max(j, k);

  //           // Range update
  //           long randValue = TestUtils.randValue(-10, 10);
  //           System.out.printf("UPDATE [%d, %d] with %d\n", i3, i4, randValue);

  //           if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.ADDITION) {
  //             bruteForceSumRangeUpdate(ar, i3, i4, randValue);
  //           } else if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.ASSIGN) {
  //             bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
  //           } else if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION) {
  //             bruteForceMulRangeUpdate(ar, i3, i4, randValue);
  //           }

  //           st.rangeUpdate1(i3, i4, randValue);
  //           st2.rangeUpdate1(i3, i4, randValue);

  //           long bf = 0;

  //           if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.SUM) {
  //             bf = bruteForceSum(ar, i1, i2);
  //           } else if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.MIN) {
  //             bf = bruteForceMin(ar, i1, i2);
  //           } else if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.MAX) {
  //             bf = bruteForceMax(ar, i1, i2);
  //           }

  //           long segTreeAnswer = st.rangeQuery1(i1, i2);
  //           long segTreeAnswer2 = st2.rangeQuery1(i1, i2);
  //           System.out.printf(
  //               "QUERY [%d, %d] want: %d, got: %d, got2: %d\n",
  //               i1, i2, bf, segTreeAnswer, segTreeAnswer2);
  //           // System.out.printf("QUERY [%d, %d] want: %d, got: %d\n", i1, i2, bf,
  // segTreeAnswer2);
  //           if (bf != segTreeAnswer) {
  //             System.out.printf(
  //                 "(%s query, %s range update) | [%d, %d], want = %d, got = %d, got2 = %d\n",
  //                 combinationFn, rangeUpdateFn, i1, i2, bf, segTreeAnswer, segTreeAnswer2);
  //           }
  //           assertThat(bf).isEqualTo(segTreeAnswer);
  //         }
  //       }
  //     }
  //   }
  // }

  @Test
  public void testAllFunctionCombinations() {
    GenericSegmentTree2.SegmentCombinationFn[] combinationFns = {
      GenericSegmentTree2.SegmentCombinationFn.SUM,
      GenericSegmentTree2.SegmentCombinationFn.MIN,
      GenericSegmentTree2.SegmentCombinationFn.MAX,
    };

    GenericSegmentTree2.RangeUpdateFn[] rangeUpdateFns = {
      GenericSegmentTree2.RangeUpdateFn.ADDITION,
      GenericSegmentTree2.RangeUpdateFn.ASSIGN,
      GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION
    };

    for (GenericSegmentTree2.SegmentCombinationFn combinationFn : combinationFns) {
      for (GenericSegmentTree2.RangeUpdateFn rangeUpdateFn : rangeUpdateFns) {

        // TODO(issue/208): The multiplication range update function seems to be suffering
        // from overflow issues and not being able to handle negative numbers.
        //
        // One idea might be to also track the min value for the max query and vice versa
        // and swap values when a negative number is found?
        if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION
            && (combinationFn == GenericSegmentTree2.SegmentCombinationFn.MIN
                || combinationFn == GenericSegmentTree2.SegmentCombinationFn.MAX)) {
          continue;
        }

        for (int n = 5; n < ITERATIONS; n++) {
          long[] ar = TestUtils.randomLongArray(n, -100, +100);
          GenericSegmentTree2 st = new GenericSegmentTree2(ar, combinationFn, rangeUpdateFn);

          for (int i = 0; i < n; i++) {
            int j = TestUtils.randValue(0, n - 1);
            int k = TestUtils.randValue(0, n - 1);
            int i1 = Math.min(j, k);
            int i2 = Math.max(j, k);

            j = TestUtils.randValue(0, n - 1);
            k = TestUtils.randValue(0, n - 1);
            int i3 = Math.min(j, k);
            int i4 = Math.max(j, k);

            // Range update
            long randValue = TestUtils.randValue(-10, 10);
            // System.out.printf("UPDATE [%d, %d] with %d\n", i3, i4, randValue);

            if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.ADDITION) {
              bruteForceSumRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.ASSIGN) {
              bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree2.RangeUpdateFn.MULTIPLICATION) {
              bruteForceMulRangeUpdate(ar, i3, i4, randValue);
            }

            st.rangeUpdate1(i3, i4, randValue);

            long bf = 0;

            if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.SUM) {
              bf = bruteForceSum(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.MIN) {
              bf = bruteForceMin(ar, i1, i2);
            } else if (combinationFn == GenericSegmentTree2.SegmentCombinationFn.MAX) {
              bf = bruteForceMax(ar, i1, i2);
            }

            long segTreeAnswer = st.rangeQuery1(i1, i2);
            if (bf != segTreeAnswer) {
              System.out.printf(
                  "(%s query, %s range update) | [%d, %d], want = %d, got = %d\n",
                  combinationFn, rangeUpdateFn, i1, i2, bf, segTreeAnswer);
            }
            assertThat(bf).isEqualTo(segTreeAnswer);
          }
        }
      }
    }
  }

  // Finds the sum in an array between [l, r] in the `values` array
  private static long bruteForceSum(long[] values, int l, int r) {
    long s = 0;
    for (int i = l; i <= r; i++) {
      s += values[i];
    }
    return s;
  }

  // Finds the min value in an array between [l, r] in the `values` array
  private static long bruteForceMin(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.min(m, values[i]);
    }
    return m;
  }

  // Finds the max value in an array between [l, r] in the `values` array
  private static long bruteForceMax(long[] values, int l, int r) {
    long m = values[l];
    for (int i = l; i <= r; i++) {
      m = Math.max(m, values[i]);
    }
    return m;
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
