// bazel test //src/test/java/com/williamfiset/algorithms/datastructures/segmenttree:SegmentTreeWithPointersTest
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.Test;

public class SegmentTreeWithPointersTest {

  @Test
  public void testNullInputThrows() {
    assertThrows(IllegalArgumentException.class, () -> new Node(null));
  }

  @Test
  public void testNegativeSizeThrows() {
    assertThrows(IllegalArgumentException.class, () -> new Node(-10));
  }

  @Test
  public void testSingleElement() {
    int[] values = {7};
    Node tree = new Node(values);
    assertThat(tree.sum(0, 1)).isEqualTo(7);
    assertThat(tree.min(0, 1)).isEqualTo(7);
  }

  @Test
  public void testSumQuerySingleElements() {
    int[] values = {1, 2, 3, 4, 5};
    Node tree = new Node(values);
    for (int i = 0; i < values.length; i++) {
      assertThat(tree.sum(i, i + 1)).isEqualTo(values[i]);
    }
  }

  @Test
  public void testSumQueryFullRange() {
    int[] values = {1, 2, 3, 4, 5};
    Node tree = new Node(values);
    assertThat(tree.sum(0, 5)).isEqualTo(15);
  }

  @Test
  public void testMinQuerySingleElements() {
    int[] values = {5, 1, 3, 2, 4};
    Node tree = new Node(values);
    for (int i = 0; i < values.length; i++) {
      assertThat(tree.min(i, i + 1)).isEqualTo(values[i]);
    }
  }

  @Test
  public void testMinQueryFullRange() {
    int[] values = {5, 1, 3, 2, 4};
    Node tree = new Node(values);
    assertThat(tree.min(0, 5)).isEqualTo(1);
    assertThat(tree.min(0, 2)).isEqualTo(1);
    assertThat(tree.min(2, 5)).isEqualTo(2);
  }

  @Test
  public void testRangeUpdate() {
    int[] values = {1, 2, 3, 4, 5};
    Node tree = new Node(values);

    // Add 10 to elements in [1, 4): values become {1, 12, 13, 14, 5}
    tree.update(1, 4, 10);
    assertThat(tree.sum(0, 5)).isEqualTo(45);
    assertThat(tree.sum(1, 4)).isEqualTo(39);
    assertThat(tree.min(0, 5)).isEqualTo(1);
    assertThat(tree.min(1, 4)).isEqualTo(12);
  }

  @Test
  public void testNegativeValues() {
    int[] values = {-3, -1, -4, -1, -5};
    Node tree = new Node(values);
    assertThat(tree.sum(0, 5)).isEqualTo(-14);
    assertThat(tree.min(0, 5)).isEqualTo(-5);
    assertThat(tree.min(0, 3)).isEqualTo(-4);
  }

  @Test
  public void testMultipleUpdates() {
    Node tree = new Node(5);
    // Start with all zeros, add 1 to entire range
    tree.update(0, 5, 1);
    assertThat(tree.sum(0, 5)).isEqualTo(5);
    assertThat(tree.min(0, 5)).isEqualTo(1);

    // Add 2 to [2, 4): values become {1, 1, 3, 3, 1}
    tree.update(2, 4, 2);
    assertThat(tree.sum(0, 5)).isEqualTo(9);
    assertThat(tree.min(0, 5)).isEqualTo(1);
    assertThat(tree.min(2, 4)).isEqualTo(3);
  }

  // Brute-force cross-validation for sum queries on random data
  @Test
  public void testAllSumQueries() {
    int n = 100;
    int[] ar = TestUtils.randomIntegerArray(n, -1000, +1000);
    Node tree = new Node(ar);

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        long bfSum = bruteForceSum(ar, i, j);
        long segTreeSum = tree.sum(i, j);
        assertThat(bfSum).isEqualTo(segTreeSum);
      }
    }
  }

  // Brute-force cross-validation for min queries on random data
  @Test
  public void testAllMinQueries() {
    int n = 100;
    int[] ar = TestUtils.randomIntegerArray(n, -1000, +1000);
    Node tree = new Node(ar);

    for (int i = 0; i < n; i++) {
      for (int j = i + 1; j < n; j++) {
        int bfMin = bruteForceMin(ar, i, j);
        int segTreeMin = tree.min(i, j);
        assertThat(bfMin).isEqualTo(segTreeMin);
      }
    }
  }

  private static long bruteForceSum(int[] values, int l, int r) {
    long s = 0;
    for (int i = l; i < r; i++) s += values[i];
    return s;
  }

  private static int bruteForceMin(int[] values, int l, int r) {
    int m = Integer.MAX_VALUE;
    for (int i = l; i < r; i++) m = Math.min(m, values[i]);
    return m;
  }
}
