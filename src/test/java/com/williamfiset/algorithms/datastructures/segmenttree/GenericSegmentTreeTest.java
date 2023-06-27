/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class GenericSegmentTreeTest {

  static int ITERATIONS = 250;
  static int MAX_N = 17;

  // @Before
  // public void setup() {}

  // @Test
  // public void testSumQuerySumUpdate_Simple() {
  //   long[] values = {1, 2, 3, 4, 5};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           values,
  //           GenericSegmentTree.SegmentCombinationFn.SUM,
  //           GenericSegmentTree.RangeUpdateFn.ADDITION);

  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(15);
  // }

  // @Test
  // public void testSumQuerySumUpdate_RangeUpdate() {
  //   //           0, 1, 2, 3, 4
  //   long[] ar = {1, 2, 1, 2, 1};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.SUM,
  //           GenericSegmentTree.RangeUpdateFn.ADDITION);

  //   // Do multiple range updates
  //   st.rangeUpdate1(0, 1, 5);
  //   st.rangeUpdate1(3, 4, 2);
  //   st.rangeUpdate1(0, 4, 3);

  //   // Point queries
  //   assertThat(st.rangeQuery1(0, 0)).isEqualTo(1 + 3 + 5);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(2 + 3 + 5);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(1 + 3);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(2 + 3 + 2);
  //   assertThat(st.rangeQuery1(4, 4)).isEqualTo(2 + 3 + 1);

  //   // Range queries
  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(2 * 5 + 2 * 3 + 1 + 2);
  //   assertThat(st.rangeQuery1(0, 2)).isEqualTo(2 * 5 + 3 * 3 + 1 + 2 + 1);
  //   assertThat(st.rangeQuery1(3, 4)).isEqualTo(2 * 2 + 2 * 3 + 2 + 1);
  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(2 * 5 + 2 * 2 + 3 * 5 + 1 + 1 + 1 + 2 + 2);
  // }

  // @Test
  // public void testSumQueryAssignUpdate_simple() {
  //   long[] ar = {2, 1, 3, 4, -1};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.SUM,
  //           GenericSegmentTree.RangeUpdateFn.ASSIGN);

  //   st.rangeUpdate1(3, 4, 2);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(10);
  //   assertThat(st.rangeQuery1(3, 4)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(4, 4)).isEqualTo(2);

  //   st.rangeUpdate1(1, 3, 4);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(16);
  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(6);
  //   assertThat(st.rangeQuery1(3, 4)).isEqualTo(6);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(2, 3)).isEqualTo(8);
  //   assertThat(st.rangeQuery1(1, 2)).isEqualTo(8);

  //   st.rangeUpdate1(2, 2, 5);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(17);
  //   assertThat(st.rangeQuery1(0, 2)).isEqualTo(11);
  //   assertThat(st.rangeQuery1(2, 4)).isEqualTo(11);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(13);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(5);
  // }

  // @Test
  // public void testSumQueryMulUpdate_simple() {
  //   long[] ar = {1, 4, 5, 3, 2};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.SUM,
  //           GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

  //   st.rangeUpdate1(1, 3, 3);

  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 + 5 * 3 + 3 * 3);
  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(1 + 4 * 3 + 5 * 3 + 3 * 3 + 2);
  //   assertThat(st.rangeQuery1(0, 2)).isEqualTo(1 + 4 * 3 + 5 * 3);
  //   assertThat(st.rangeQuery1(2, 4)).isEqualTo(5 * 3 + 3 * 3 + 2);

  //   st.rangeUpdate1(1, 3, 2);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 * 2 + 5 * 3 * 2 + 3 * 3 * 2);
  // }

  // @Test
  // public void minQuerySumUpdates_simple() {
  //   long[] ar = {2, 1, 3, 4, -1};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.MIN,
  //           GenericSegmentTree.RangeUpdateFn.ADDITION);

  //   st.rangeUpdate1(0, 4, 1);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(0);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(2, 4)).isEqualTo(0);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(5);

  //   st.rangeUpdate1(3, 4, 4);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(3, 4)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(2, 3)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(1, 2)).isEqualTo(2);

  //   st.rangeUpdate1(1, 3, 3);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(0, 2)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(2, 4)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(5);
  //   assertThat(st.rangeQuery1(0, 0)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(5);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(7);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(4, 4)).isEqualTo(4);
  // }

  // @Test
  // public void maxQuerySumUpdate_simple() {
  //   long[] ar = {2, 1, 3, 4, -1};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.MAX,
  //           GenericSegmentTree.RangeUpdateFn.ADDITION);

  //   st.printDebugInfo();
  //   st.rangeUpdate1(0, 4, 1);
  //   st.printDebugInfo();

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(5);
  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(1, 2)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(5);

  //   st.rangeUpdate1(3, 4, 4);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(0, 1)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(3, 4)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(2);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(4);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(2, 3)).isEqualTo(9);
  //   assertThat(st.rangeQuery1(1, 2)).isEqualTo(4);

  //   st.rangeUpdate1(1, 3, 3);

  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(0, 2)).isEqualTo(7);
  //   assertThat(st.rangeQuery1(2, 4)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(1, 3)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(0, 0)).isEqualTo(3);
  //   assertThat(st.rangeQuery1(1, 1)).isEqualTo(5);
  //   assertThat(st.rangeQuery1(2, 2)).isEqualTo(7);
  //   assertThat(st.rangeQuery1(3, 3)).isEqualTo(12);
  //   assertThat(st.rangeQuery1(4, 4)).isEqualTo(4);
  // }

  // @Test
  // public void maxQueryMulUpdate_simple() {
  //   long[] ar = {2, 1, 3, 4, -1};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(
  //           ar,
  //           GenericSegmentTree.SegmentCombinationFn.MAX,
  //           GenericSegmentTree.RangeUpdateFn.MULTIPLICATION);

  //   st.rangeUpdate1(0, 4, 1);
  //   assertThat(st.rangeQuery1(0, 4)).isEqualTo(4);

  //   // TODO(issue/208): Negative numbers are a known issue
  //   // st.rangeUpdate1(0, 4, -2);
  //   // assertThat(st.rangeQuery1(0, 4)).isEqualTo(2); // Returns -8 as max but should be 2
  // }

  @Test
  public void testAllFunctionCombinations() {
    GenericSegmentTree.SegmentCombinationFn[] combinationFns = {
      GenericSegmentTree.SegmentCombinationFn.SUM,
      GenericSegmentTree.SegmentCombinationFn.MIN,
      GenericSegmentTree.SegmentCombinationFn.MAX,
      GenericSegmentTree.SegmentCombinationFn.GCD,
      // GenericSegmentTree.SegmentCombinationFn.PRODUCT,
    };

    GenericSegmentTree.RangeUpdateFn[] rangeUpdateFns = {
      GenericSegmentTree.RangeUpdateFn.ADDITION,
      GenericSegmentTree.RangeUpdateFn.ASSIGN,
      GenericSegmentTree.RangeUpdateFn.MULTIPLICATION
    };

    for (GenericSegmentTree.SegmentCombinationFn combinationFn : combinationFns) {
      for (GenericSegmentTree.RangeUpdateFn rangeUpdateFn : rangeUpdateFns) {

        // TODO(issue/208): The multiplication range update function seems to be suffering
        // from overflow issues and not being able to handle negative numbers.
        //
        // One idea might be to also track the min value for the max query and vice versa
        // and swap values when a negative number is found?
        if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.MULTIPLICATION
            && (combinationFn == GenericSegmentTree.SegmentCombinationFn.MIN
                || combinationFn == GenericSegmentTree.SegmentCombinationFn.MAX)) {
          continue;
        }

        if (combinationFn == GenericSegmentTree.SegmentCombinationFn.GCD
            && rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ADDITION) {
          // Not supported
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
            // System.out.printf("i = %d\n", i);
            int j = TestUtils.randValue(0, n - 1);
            int k = TestUtils.randValue(0, n - 1);
            int i1 = Math.min(j, k);
            int i2 = Math.max(j, k);

            j = TestUtils.randValue(0, n - 1);
            k = TestUtils.randValue(0, n - 1);
            int i3 = Math.min(j, k);
            int i4 = Math.max(j, k);

            // Range update
            long randValue = getRandValueByTestType(combinationFn);
            // System.out.printf("UPDATE [%d, %d] with %d\n", i3, i4, randValue);

            if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ADDITION) {
              bruteForceSumRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.ASSIGN) {
              bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
            } else if (rangeUpdateFn == GenericSegmentTree.RangeUpdateFn.MULTIPLICATION) {
              bruteForceMulRangeUpdate(ar, i3, i4, randValue);
            }

            st.rangeUpdate1(i3, i4, randValue);

            // Range query
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

            long segTreeAnswer = st.rangeQuery1(i1, i2);
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
    // GCD doesn't play well with negative numbers
    if (combinationFn != GenericSegmentTree.SegmentCombinationFn.GCD) {
      return TestUtils.randomLongArray(n, -100, +100);
    }
    return TestUtils.randomLongArray(n, 1, +10);
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

  private static long bruteForceMul(long[] values, int l, int r) {
    long m = 1L;
    for (int i = l; i <= r; i++) {
      m *= values[i];
    }
    return m;
  }

  private static long gcd(long a, long b) {
    long gcd = a;
    while (b != 0) {
      gcd = b;
      b = a % b;
      a = gcd;
    }
    return Math.abs(gcd);
  }

  // Finds the sum in an array between [l, r] in the `values` array
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
