package com.williamfiset.algorithms.sorting;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
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

  @Test
  public void testRadixSort() {
    // Random Test
    Random random = new Random();
    int[] array2 = new int[50];
    for (int i = 0; i < 50; i++) {
      array2[i] = random.nextInt(50) + 1;
    }
    int[] copy = array2.clone();

    RadixSort.radixSort(array2);
    Arrays.sort(copy);

    assertTrue(Arrays.equals(array2, copy));
  }
}
