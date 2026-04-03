package com.williamfiset.algorithms.sorting;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import java.util.Arrays;
import org.junit.jupiter.api.*;

public class TimSortTest {

  private final TimSort sorter = new TimSort();

  @Test
  public void testEmptyArray() {
    int[] array = {};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {});
  }

  @Test
  public void testSingleElement() {
    int[] array = {42};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {42});
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
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {-13, 2, 3, 4, 4, 6, 8, 10});
  }

  @Test
  public void testAllSameElements() {
    int[] array = {3, 3, 3, 3, 3};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {3, 3, 3, 3, 3});
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
  public void testSmallerThanMinRun() {
    int[] array = {5, 3, 8, 1, 9, 2, 7};
    sorter.sort(array);
    assertThat(array).isEqualTo(new int[] {1, 2, 3, 5, 7, 8, 9});
  }

  @Test
  public void testLargerThanMinRun() {
    // 100 elements to exercise the merge phase
    int[] array = TestUtils.randomIntegerArray(100, -50, 51);
    int[] expected = array.clone();
    Arrays.sort(expected);
    sorter.sort(array);
    assertThat(array).isEqualTo(expected);
  }

  @Test
  public void testRandomized() {
    for (int size = 0; size < 500; size++) {
      int[] values = TestUtils.randomIntegerArray(size, -50, 51);
      int[] expected = values.clone();
      Arrays.sort(expected);
      sorter.sort(values);
      assertThat(values).isEqualTo(expected);
    }
  }
}
