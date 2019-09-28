/**
 * Insertion sort implementation
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Random;

public class InsertionSort {

  // Sort the given array using insertion sort. The idea behind
  // insertion sort is that at the array is already sorted from
  // [0, i] and you want to add the element at position i+1, so
  // you 'insert' it at the appropriate location.
  public static void insertionSort(int[] ar) {

    if (ar == null) return;

    final int N = ar.length;

    for (int i = 1; i < N; i++) for (int j = i; j > 0 && ar[j] < ar[j - 1]; j--) swap(ar, j - 1, j);
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {

    int[] array = {10, 4, 6, 8, 13, 2, 3};
    insertionSort(array);
    System.out.println(java.util.Arrays.toString(array));

    // TODO(williamfiset): move to javatests/...
    runTests();
  }

  static Random RANDOM = new Random();

  public static void runTests() {
    final int NUM_TESTS = 1000;
    for (int i = 1; i <= NUM_TESTS; i++) {

      int[] array = new int[i];
      for (int j = 0; j < i; j++) array[j] = randInt(-1000000, +1000000);
      int[] arrayCopy = array.clone();

      insertionSort(array);
      java.util.Arrays.sort(arrayCopy);

      if (!java.util.Arrays.equals(array, arrayCopy)) System.out.println("ERROR");
    }
  }

  static int randInt(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }
}
