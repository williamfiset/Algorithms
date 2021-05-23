/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.SumQueryMultiplicationUpdateSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class SumQueryMultiplicationUpdateSegmentTreeTest {

  static int ITERATIONS = 100;
  static int MAX_N = 28;

  @Before
  public void setup() {}

  @Test
  public void simpleTest() {
    long[] ar = {1, 4, 5, 3, 2};
    SumQueryMultiplicationUpdateSegmentTree st = new SumQueryMultiplicationUpdateSegmentTree(ar);

    st.rangeUpdate1(1, 3, 3);

    assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 + 5 * 3 + 3 * 3);
    assertThat(st.rangeQuery1(0, 4)).isEqualTo(1 + 4 * 3 + 5 * 3 + 3 * 3 + 2);
    assertThat(st.rangeQuery1(0, 2)).isEqualTo(1 + 4 * 3 + 5 * 3);
    assertThat(st.rangeQuery1(2, 4)).isEqualTo(5 * 3 + 3 * 3 + 2);

    st.rangeUpdate1(1, 3, 2);
    assertThat(st.rangeQuery1(1, 3)).isEqualTo(4 * 3 * 2 + 5 * 3 * 2 + 3 * 3 * 2);
  }

  @Test
  public void testRandomRangeSumUpdatesWithSumRangeQueries() {
    for (int n = 5; n < MAX_N; n++) {
      long[] ar = TestUtils.randomLongArray(n, -10, +10);
      SumQueryMultiplicationUpdateSegmentTree st =
          new SumQueryMultiplicationUpdateSegmentTree(ar.clone());

      for (int i = 0; i < ITERATIONS; i++) {
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);

        // Range update.
        // Yes, these values will likely cause overflow through excessive
        // multiplication of segment values
        long randValue = TestUtils.randValue(-100, +100);
        bruteForceMulRangeUpdate(ar, i3, i4, randValue);
        st.rangeUpdate1(i3, i4, randValue);

        // Range query
        long bfSum = bruteForceSum(ar, i1, i2);
        long segTreeSum = st.rangeQuery1(i1, i2);
        assertThat(bfSum).isEqualTo(segTreeSum);
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
