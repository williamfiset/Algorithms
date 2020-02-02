/**
 * Quicksort implementation using Hoare partitioning
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Random;

public class Quicksort {

  public static void quicksort(int[] ar) {
    if (ar == null) return;
    quicksort(ar, 0, ar.length - 1);
  }

  // Sort interval [lo, hi] inplace recursively
  private static void quicksort(int[] ar, int lo, int hi) {
    if (lo < hi) {
      int splitPoint = partition(ar, lo, hi);
      quicksort(ar, lo, splitPoint);
      quicksort(ar, splitPoint + 1, hi);
    }
  }

  // Performs Hoare partition algorithm for quicksort
  private static int partition(int[] ar, int lo, int hi) {
    int pivot = ar[lo];
    int i = lo - 1, j = hi + 1;
    while (true) {
      do {
        i++;
      } while (ar[i] < pivot);
      do {
        j--;
      } while (ar[j] > pivot);
      if (i < j) swap(ar, i, j);
      else return j;
    }
  }

  // Swap two elements
  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {

    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    quicksort(array);
    System.out.println(java.util.Arrays.toString(array));

    // TODO(williamfiset): Move to test file
    runTests();
  }

  /* TESTING BELOW */

  static Random RANDOM = new Random();

  public static void runTests() {
    final int NUM_TESTS = 1000;
    for (int i = 1; i <= NUM_TESTS; i++) {

      int[] array = new int[i];
      for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
      int[] arrayCopy = array.clone();

      quicksort(array);
      java.util.Arrays.sort(arrayCopy);

      if (!java.util.Arrays.equals(array, arrayCopy)) System.out.println("ERROR");
    }
  }

  static int randInt(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }
}
