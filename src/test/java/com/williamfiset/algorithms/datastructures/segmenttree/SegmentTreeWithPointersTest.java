// gradle test --info --tests
// "com.williamfiset.algorithms.datastructures.segmenttree.SegmentTreeWithPointersTest"
package com.williamfiset.algorithms.datastructures.segmenttree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.jupiter.api.*;

public class SegmentTreeWithPointersTest {

  @BeforeEach
  public void setup() {}

  @Test
  public void testIllegalSegmentTreeCreation1() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          Node tree = new Node(null);
        });
  }

  @Test
  public void testIllegalSegmentTreeCreation2() {
    assertThrows(
        IllegalArgumentException.class,
        () -> {
          int size = -10;
          Node tree = new Node(size);
        });
  }

  @Test
  public void testSumQuery() {
    int[] values = {1, 2, 3, 4, 5};
    Node tree = new Node(values);

    assertThat(tree.sum(0, 1)).isEqualTo(1);
    assertThat(tree.sum(1, 2)).isEqualTo(2);
    assertThat(tree.sum(2, 3)).isEqualTo(3);
    assertThat(tree.sum(3, 4)).isEqualTo(4);
    assertThat(tree.sum(4, 5)).isEqualTo(5);
  }

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

  // Finds the sum in an array between [l, r) in the `values` array
  private static long bruteForceSum(int[] values, int l, int r) {
    long s = 0;
    for (int i = l; i < r; i++) {
      s += values[i];
    }
    return s;
  }
}
