package com.williamfiset.algorithms.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class RadixSortTest {

  @Test
  public void testGetMax() {
    int[] array = {3, 90, 112, 56};
    int[] array2 = {5, 9, 1, 42, 117};
    int max = RadixSort.getMax(array);
    int max2 = RadixSort.getMax(array2);
    assertEquals(112, max);
    assertEquals(117, max2);
  }

  @Test
  public void testRadixSort() {
    int[] array = {90, 23, 101, 45, 65, 23, 67, 89, 34, 23};
    RadixSort.radixSort(array);
    assertEquals(23, array[0]);
    assertEquals(101, array[array.length - 1]);
    assertEquals(34, array[3]);
  }
}
