package com.williamfiset.algorithms.sorting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class RadixSortTest {
  @Test
  public void testGetMax() {
    int[] array = {5, 7, 1, 13, 42, 101, 512, 67, 1013, 287, 912};
    assertEquals(1013, RadixSort.getMax(array));
  }

  @Test
  public void testCalculateNumberOfDigits() {
    assertTrue(4 == RadixSort.calculateNumberOfDigits(1089));
    assertTrue(2 == RadixSort.calculateNumberOfDigits(19));
  }
}
