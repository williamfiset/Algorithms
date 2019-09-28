/**
 * Selection sort implementation
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Random;

public class SelectionSort {

  public static void selectionSort(int[] array) {
    if (array == null) return;
    final int N = array.length;

    for (int i = 0; i < N; i++) {

      // Find the index beyond i with a lower value than i
      int swapIndex = i;
      for (int j = i + 1; j < N; j++) if (array[j] < array[swapIndex]) swapIndex = j;

      swap(array, i, swapIndex);
    }
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {

    int[] array = {10, 4, 6, 8, 13, 2, 3};
    selectionSort(array);
    System.out.println(java.util.Arrays.toString(array));

    runTests();
  }

  // TODO(williamfiset): move this to a test file.

  static Random RANDOM = new Random();

  public static void runTests() {
    final int NUM_TESTS = 1000;
    for (int i = 1; i <= NUM_TESTS; i++) {

      int[] array = new int[i];
      for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
      int[] arrayCopy = array.clone();

      selectionSort(array);
      java.util.Arrays.sort(arrayCopy);

      if (!java.util.Arrays.equals(array, arrayCopy)) System.out.println("ERROR");
    }
  }

  static int randInt(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }
}
