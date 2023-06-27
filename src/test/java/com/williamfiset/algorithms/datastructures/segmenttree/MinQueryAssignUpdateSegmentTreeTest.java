/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.MinQueryAssignUpdateSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.*;

public class MinQueryAssignUpdateSegmentTreeTest {

  static int ITERATIONS = 500;

  @BeforeEach
  public void setup() {}

  @Test
  public void testRandomRangeAssignUpdates1WithMinRangeQueries1() {
    for (int n = 5; n < ITERATIONS; n++) {
      long[] ar = TestUtils.randomLongArray(n, -1000, +1000);
      MinQueryAssignUpdateSegmentTree st = new MinQueryAssignUpdateSegmentTree(ar);

      for (int i = 0; i < n; i++) {
        // System.out.printf("n = %d, i = %d\n", n, i);
        int j = TestUtils.randValue(0, n - 1);
        int k = TestUtils.randValue(0, n - 1);
        int i1 = Math.min(j, k);
        int i2 = Math.max(j, k);

        // Range query
        long bfMin = bruteForceMin(ar, i1, i2);
        long segTreeMin = st.rangeQuery1(i1, i2);
        assertThat(bfMin).isEqualTo(segTreeMin);

        // Range update
        j = TestUtils.randValue(0, n - 1);
        k = TestUtils.randValue(0, n - 1);
        int i3 = Math.min(j, k);
        int i4 = Math.max(j, k);
        long randValue = TestUtils.randValue(-1000, 1000);
        st.rangeUpdate1(i3, i4, randValue);
        bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
      }
    }
  }

  // @Test
  // public void testRandomRangeAssignUpdates2WithMinRangeQueries1() {
  //   for (int n = 5; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -1000, +1000);
  //     MinQueryAssignUpdateSegmentTree st = new MinQueryAssignUpdateSegmentTree(ar);

  //     for (int i = 0; i < n; i++) {
  //       // System.out.printf("n = %d, i = %d\n", n, i);
  //       int j = TestUtils.randValue(0, n - 1);
  //       int k = TestUtils.randValue(0, n - 1);
  //       int i1 = Math.min(j, k);
  //       int i2 = Math.max(j, k);

  //       // Range query
  //       long bfMin = bruteForceMin(ar, i1, i2);
  //       long segTreeMin = st.rangeQuery2(i1, i2);
  //       assertThat(bfMin).isEqualTo(segTreeMin);

  //       // Range update
  //       j = TestUtils.randValue(0, n - 1);
  //       k = TestUtils.randValue(0, n - 1);
  //       int i3 = Math.min(j, k);
  //       int i4 = Math.max(j, k);
  //       long randValue = TestUtils.randValue(-1000, 1000);
  //       st.rangeUpdate2(i3, i4, randValue);
  //       bruteForceAssignRangeUpdate(ar, i3, i4, randValue);
  //     }
  //   }
  // }

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
