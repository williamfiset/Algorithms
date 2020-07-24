/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.GenericSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import org.junit.Before;

public class GenericSegmentTreeTest {

  static int ITERATIONS = 100;

  @Before
  public void setup() {}

  // @Test
  // public void testSumQuery() {
  //   long[] values = {1, 2, 3, 4, 5};
  //   GenericSegmentTree st =
  //       new GenericSegmentTree(values, GenericSegmentTree.SegmentCombinationFn.SUM);

  //   assertThat(st.rangeQuery(0, 1)).isEqualTo(3);
  //   assertThat(st.rangeQuery(2, 2)).isEqualTo(3);
  //   assertThat(st.rangeQuery(0, 4)).isEqualTo(15);
  // }

  // @Test
  // public void testAllSumQueries() {
  //   int n = 100;
  //   long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //   GenericSegmentTree st = new GenericSegmentTree(ar,
  // GenericSegmentTree.SegmentCombinationFn.SUM);

  //   for (int i = 0; i < n; i++) {
  //     for (int j = i; j < n; j++) {
  //       long bfSum = bruteForceSum(ar, i, j);
  //       long segTreeSum = st.rangeQuery(i, j);
  //       assertThat(bfSum).isEqualTo(segTreeSum);
  //     }
  //   }
  // }

  // @Test
  // public void testSimpleAdditionRangeUpdate() {
  //   //           0, 1, 2, 3, 4
  //   long[] ar = {1, 2, 1, 2, 1};
  //   GenericSegmentTree st = new GenericSegmentTree(ar,
  // GenericSegmentTree.SegmentCombinationFn.SUM);

  //   // Do multiple range updates
  //   st.rangeUpdate(0, 1, 5);
  //   st.rangeUpdate(3, 4, 2);
  //   st.rangeUpdate(0, 4, 3);

  //   // Point queries
  //   assertThat(st.rangeQuery(0, 0)).isEqualTo(1 + 3 + 5);
  //   assertThat(st.rangeQuery(1, 1)).isEqualTo(2 + 3 + 5);
  //   assertThat(st.rangeQuery(2, 2)).isEqualTo(1 + 3);
  //   assertThat(st.rangeQuery(3, 3)).isEqualTo(2 + 3 + 2);
  //   assertThat(st.rangeQuery(4, 4)).isEqualTo(2 + 3 + 1);

  //   // Range queries
  //   assertThat(st.rangeQuery(0, 1)).isEqualTo(2 * 5 + 2 * 3 + 1 + 2);
  //   assertThat(st.rangeQuery(0, 2)).isEqualTo(2 * 5 + 3 * 3 + 1 + 2 + 1);
  //   assertThat(st.rangeQuery(3, 4)).isEqualTo(2 * 2 + 2 * 3 + 2 + 1);
  //   assertThat(st.rangeQuery(0, 4)).isEqualTo(2 * 5 + 2 * 2 + 3 * 5 + 1 + 1 + 1 + 2 + 2);
  // }

  // @Test
  // public void testRandomPointUpdatesAndSumRangeQueries() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.SUM);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfSum = bruteForceSum(ar, i, j);
  //         long segTreeSum = st.rangeQuery(i, j);
  //         assertThat(bfSum).isEqualTo(segTreeSum);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfSum = bruteForceSum(ar, i, j);
  //         segTreeSum = st.rangeQuery(i, j);
  //         assertThat(bfSum).isEqualTo(segTreeSum);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomPointUpdatesAndMinRangeQueries() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.MIN);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfMin = bruteForceMin(ar, i, j);
  //         long segTreeMin = st.rangeQuery(i, j);
  //         assertThat(bfMin).isEqualTo(segTreeMin);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfMin = bruteForceMin(ar, i, j);
  //         segTreeMin = st.rangeQuery(i, j);
  //         assertThat(bfMin).isEqualTo(segTreeMin);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomPointUpdatesAndMaxRangeQueries() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.MAX);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfMax = bruteForceMax(ar, i, j);
  //         long segTreeMax = st.rangeQuery(i, j);
  //         assertThat(bfMax).isEqualTo(segTreeMax);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfMax = bruteForceMax(ar, i, j);
  //         segTreeMax = st.rangeQuery(i, j);
  //         assertThat(bfMax).isEqualTo(segTreeMax);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomPointUpdatesAndSumRangeQueries_rangeQuery2() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.SUM);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfSum = bruteForceSum(ar, i, j);
  //         long segTreeSum = st.rangeQuery(i, j);
  //         assertThat(bfSum).isEqualTo(segTreeSum);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfSum = bruteForceSum(ar, i, j);
  //         segTreeSum = st.rangeQuery2(i, j);
  //         assertThat(bfSum).isEqualTo(segTreeSum);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomPointUpdatesAndMinRangeQueries_rangeQuery2() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.MIN);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfMin = bruteForceMin(ar, i, j);
  //         long segTreeMin = st.rangeQuery2(i, j);
  //         assertThat(bfMin).isEqualTo(segTreeMin);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfMin = bruteForceMin(ar, i, j);
  //         segTreeMin = st.rangeQuery2(i, j);
  //         assertThat(bfMin).isEqualTo(segTreeMin);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomPointUpdatesAndMaxRangeQueries_rangeQuery2() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(ar, GenericSegmentTree.SegmentCombinationFn.MAX);

  //     for (int i = 0; i < n; i++) {
  //       for (int j = i; j < n; j++) {
  //         // Range query
  //         long bfMax = bruteForceMax(ar, i, j);
  //         long segTreeMax = st.rangeQuery(i, j);
  //         assertThat(bfMax).isEqualTo(segTreeMax);

  //         // Point update
  //         int randIndex = TestUtils.randValue(i, j + 1);
  //         long randValue = TestUtils.randValue(-100, 100);
  //         st.pointUpdate(randIndex, randValue);
  //         ar[randIndex] = randValue;
  //         bfMax = bruteForceMax(ar, i, j);
  //         segTreeMax = st.rangeQuery(i, j);
  //         assertThat(bfMax).isEqualTo(segTreeMax);
  //       }
  //     }
  //   }
  // }

  // @Test
  // public void testRandomRangeSumUpdatesWithSumRangeQueries() {
  //   for (int n = 1; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(
  //             ar,
  //             GenericSegmentTree.SegmentCombinationFn.SUM,
  //             GenericSegmentTree.RangeUpdateFn.ADDITION);

  //     for (int i = 0; i < n; i++) {
  //       int j = TestUtils.randValue(0, n - 1);
  //       int k = TestUtils.randValue(0, n - 1);
  //       int i1 = Math.min(j, k);
  //       int i2 = Math.max(j, k);

  //       // Range query
  //       long bfSum = bruteForceSum(ar, i1, i2);
  //       long segTreeSum = st.rangeQuery(i1, i2);
  //       assertThat(bfSum).isEqualTo(segTreeSum);

  //       // Range update
  //       j = TestUtils.randValue(0, n - 1);
  //       k = TestUtils.randValue(0, n - 1);
  //       int i3 = Math.min(j, k);
  //       int i4 = Math.max(j, k);
  //       long randValue = TestUtils.randValue(-100, 100);
  //       st.rangeUpdate(i3, i4, randValue);
  //       bruteForceSumRangeUpdate(ar, i3, i4, randValue);
  //     }
  //   }
  // }

  // @Test
  // public void testRandomRangeAssignUpdatesWithMinRangeQueries() {
  //   for (int n = 5; n < ITERATIONS; n++) {
  //     long[] ar = TestUtils.randomLongArray(n, -100, +100);
  //     GenericSegmentTree st =
  //         new GenericSegmentTree(
  //             ar,
  //             GenericSegmentTree.SegmentCombinationFn.MIN,
  //             GenericSegmentTree.RangeUpdateFn.ASSIGN);

  //     for (int i = 0; i < n; i++) {
  //       System.out.printf("n = %d, i = %d\n", n, i);
  //       int j = TestUtils.randValue(0, n - 1);
  //       int k = TestUtils.randValue(0, n - 1);
  //       int i1 = Math.min(j, k);
  //       int i2 = Math.max(j, k);

  //       // Range query
  //       long bfMin = bruteForceMin(ar, i1, i2);
  //       long segTreeMin = st.rangeQuery(i1, i2);
  //       assertThat(bfMin).isEqualTo(segTreeMin);

  //       // Range update
  //       j = TestUtils.randValue(0, n - 1);
  //       k = TestUtils.randValue(0, n - 1);
  //       int i3 = Math.min(j, k);
  //       int i4 = Math.max(j, k);
  //       long randValue = TestUtils.randValue(-100, 100);
  //       st.rangeUpdate(i3, i4, randValue);
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
