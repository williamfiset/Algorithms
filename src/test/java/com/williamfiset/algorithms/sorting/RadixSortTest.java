package com.williamfiset.algorithms.sorting;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

public class RadixSortTest {
  static Random random = new Random();

  @Test
  public void testGetMax() {
    int[] array = {5, 7, 1, 13, 1013, 287, 912};
    assertThat(RadixSort.getMax(array)).isEqualTo(1013);
  }

  @Test
  public void testCalculateNumberOfDigits() {
    assertThat(RadixSort.calculateNumberOfDigits(1089)).isEqualTo(4);
    assertThat(RadixSort.calculateNumberOfDigits(19)).isEqualTo(2);
  }

  @Test
  public void randomRadixSort_smallNumbers() {
    for (int size = 0; size < 1000; size++) {
      int[] values = new int[size];
      for (int i = 0; i < size; i++) {
        values[i] = randInt(1, 50);
      }
      int[] copy = values.clone();

      Arrays.sort(values);
      RadixSort.radixSort(copy);

      assertThat(values).isEqualTo(copy);
    }
  }

  @Test
  public void randomRadixSort_largeNumbers() {
    for (int size = 0; size < 1000; size++) {
      int[] values = new int[size];
      for (int i = 0; i < size; i++) {
        values[i] = randInt(1, Integer.MAX_VALUE);
      }
      int[] copy = values.clone();

      Arrays.sort(values);
      RadixSort.radixSort(copy);

      assertThat(values).isEqualTo(copy);
    }
  }

  // return a random number between [min, max]
  static int randInt(int min, int max) {
    return random.nextInt((max - min) + 1) + min;
  }
}
