package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MaximumSubarrayTest {

  @Test
  public void testNullInput() {
    assertThrows(
        IllegalArgumentException.class, () -> MaximumSubarray.maximumSubarrayValue(null));
  }

  @Test
  public void testEmptyInput() {
    assertThrows(
        IllegalArgumentException.class, () -> MaximumSubarray.maximumSubarrayValue(new int[] {}));
  }

  @Test
  public void testSinglePositive() {
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {7})).isEqualTo(7);
  }

  @Test
  public void testSingleNegative() {
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {-5})).isEqualTo(-5);
  }

  @Test
  public void testSingleZero() {
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {0})).isEqualTo(0);
  }

  @Test
  public void testAllPositive() {
    // Entire array is the max subarray
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {1, 2, 3, 4})).isEqualTo(10);
  }

  /** All negative: the max subarray is the single largest element. */
  @Test
  public void testAllNegative() {
    assertThat(MaximumSubarray.maximumSubarrayValue(
        new int[] {-5, -4, -10, -3, -1, -12, -6})).isEqualTo(-1);
  }

  @Test
  public void testMixedWithNegativeReset() {
    // [2, -1, 40] = 41
    assertThat(MaximumSubarray.maximumSubarrayValue(
        new int[] {1, 2, 1, -7, 2, -1, 40, -89})).isEqualTo(41);
  }

  @Test
  public void testMaxSubarrayAtStart() {
    // [5, 4] = 9
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {5, 4, -20, 1, 2})).isEqualTo(9);
  }

  @Test
  public void testMaxSubarrayAtEnd() {
    // [3, 7] = 10
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {1, -20, 3, 7})).isEqualTo(10);
  }

  @Test
  public void testMaxSubarrayInMiddle() {
    // [4, -1, 5] = 8
    assertThat(MaximumSubarray.maximumSubarrayValue(
        new int[] {-3, 4, -1, 5, -10})).isEqualTo(8);
  }

  @Test
  public void testEntireArrayIsMax() {
    assertThat(MaximumSubarray.maximumSubarrayValue(
        new int[] {2, -1, 3, -1, 2})).isEqualTo(5);
  }

  @Test
  public void testAllZeros() {
    assertThat(MaximumSubarray.maximumSubarrayValue(new int[] {0, 0, 0})).isEqualTo(0);
  }

  @Test
  public void testLargeValues() {
    // Ensure long return type handles values beyond int range
    assertThat(MaximumSubarray.maximumSubarrayValue(
        new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}))
        .isEqualTo(2L * Integer.MAX_VALUE);
  }
}
