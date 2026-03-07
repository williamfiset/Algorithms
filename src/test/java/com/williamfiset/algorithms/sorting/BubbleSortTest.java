package com.williamfiset.algorithms.sorting;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class BubbleSortTest {

  private final BubbleSort sorter = new BubbleSort();

  @Test
  public void testEmptyArray() {
    int[] array = {};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {});
  }

  @Test
  public void testSingleElement() {
    int[] array = {7};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {7});
  }

  @Test
  public void testAlreadySorted() {
    int[] array = {1, 2, 3, 4, 5};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {1, 2, 3, 4, 5});
  }

  @Test
  public void testReverseSorted() {
    int[] array = {5, 4, 3, 2, 1};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {1, 2, 3, 4, 5});
  }

  @Test
  public void testWithDuplicates() {
    int[] array = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {1, 1, 2, 3, 3, 4, 5, 5, 6, 9});
  }

  @Test
  public void testAllSameElements() {
    int[] array = {5, 5, 5, 5};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {5, 5, 5, 5});
  }

  @Test
  public void testNegativeNumbers() {
    int[] array = {-3, -1, -4, -1, -5};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {-5, -4, -3, -1, -1});
  }

  @Test
  public void testMixedPositiveAndNegative() {
    int[] array = {3, -2, 0, 7, -5, 1};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {-5, -2, 0, 1, 3, 7});
  }

  @Test
  public void testTwoElements() {
    int[] array = {9, 1};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {1, 9});
  }

  @Test
  public void testRandomized() {
    for (int size = 0; size < 200; size++) {
      int[] values = TestUtils.randomIntegerArray(size, -50, 51);
      int[] expected = values.clone();
      Arrays.sort(expected);
      sorter.sort(values);
      assertThat(values).isEqualTo(expected);
    }
  }
}
