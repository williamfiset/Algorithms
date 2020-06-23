/**
 * gradle test --info --tests
 * "com.williamfiset.algorithms.datastructures.segmenttree.RangeQueryPointUpdateSegmentTreeTest"
 */
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;

public class RangeQueryPointUpdateSegmentTreeTest {

  @Before
  public void setup() {}

  @Test
  public void testSumQuery() {
    int[] values = {1, 2, 3, 4, 5};
    RangeQueryPointUpdateSegmentTree st = new RangeQueryPointUpdateSegmentTree(values);

    assertThat(st.sumQuery(0, 1)).isEqualTo(3);
    assertThat(st.sumQuery(2, 2)).isEqualTo(3);
    assertThat(st.sumQuery(0, 4)).isEqualTo(15);
  }

  @Test
  public void testAllSumQueries() {
    int n = 100;
    int[] ar = TestUtils.randomIntegerArray(n, -1000, +1000);
    RangeQueryPointUpdateSegmentTree st = new RangeQueryPointUpdateSegmentTree(ar);

    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        long bfSum = bruteForceSum(ar, i, j);
        long segTreeSum = st.sumQuery(i, j);
        assertThat(bfSum).isEqualTo(segTreeSum);
      }
    }
  }

  @Test
  public void testRandomPointQueries() {
    int n = 100;
    int[] ar = TestUtils.randomIntegerArray(n, -1000, +1000);
    RangeQueryPointUpdateSegmentTree st = new RangeQueryPointUpdateSegmentTree(ar);

    for (int i = 0; i < n; i++) {
      for (int j = i; j < n; j++) {
        long bfSum = bruteForceSum(ar, i, j);
        long segTreeSum = st.sumQuery(i, j);
        assertThat(bfSum).isEqualTo(segTreeSum);

        int randIndex = TestUtils.randValue(i, j + 1);
        int randValue = TestUtils.randValue(-1000, 1000);
        st.update(randIndex, randValue);
        ar[randIndex] = randValue;
        bfSum = bruteForceSum(ar, i, j);
        segTreeSum = st.sumQuery(i, j);
        assertThat(bfSum).isEqualTo(segTreeSum);
      }
    }
  }

  // Finds the sum in an array between [l, r] in the `values` array
  private static long bruteForceSum(int[] values, int l, int r) {
    long s = 0;
    for (int i = l; i <= r; i++) {
      s += values[i];
    }
    return s;
  }
}
