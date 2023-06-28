/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.SumQueryAssignUpdateSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.*;

public class SumQueryAssignUpdateSegmentTreeTest {

  static int ITERATIONS = 100;

  @BeforeEach
  public void setup() {}

  @Test
  public void simpleTest() {
    long[] ar = {2, 1, 3, 4, -1};
    SumQueryAssignUpdateSegmentTree st = new SumQueryAssignUpdateSegmentTree(ar);

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
  public void testRandomRangeAssignUpdatesWithSumRangeQueries() {
    for (int n = 5; n < ITERATIONS; n++) {
      long[] ar = TestUtils.randomLongArray(n, -100, +100);
      SumQueryAssignUpdateSegmentTree st = new SumQueryAssignUpdateSegmentTree(ar);

      for (int i = 0; i < n; i++) {
        // System.out.printf("n = %d, i = %d\n", n, i);
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        // Range query
        long bfSum = bruteForceSum(ar, i1, i2);
        long segTreeSum = st.rangeQuery1(i1, i2);
        assertThat(bfSum).isEqualTo(segTreeSum);

        // Range update
        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);
        long randValue = TestUtils.randValue(-100, 100);
        // System.out.printf("Update [%d, %d] to %d\n", i3, i4, randValue);
        st.rangeUpdate1(i3, i4, randValue);
        bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
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
