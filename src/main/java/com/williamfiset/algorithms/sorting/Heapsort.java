/**
 * Implementation of heapsort
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.Heapsort
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.*;

public class Heapsort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    Heapsort.heapsort(values);
  }

  private static void heapsort(int[] ar) {
    if (ar == null) return;
    int n = ar.length;

    // Heapify, converts array into binary heap O(n), see:
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    for (int i = Math.max(0, (n / 2) - 1); i >= 0; i--) {
      sink(ar, n, i);
    }

    // Sorting bit
    for (int i = n - 1; i >= 0; i--) {
      swap(ar, 0, i);
      sink(ar, i, 0);
    }
  }

  private static void sink(int[] ar, int n, int i) {
    while (true) {
      int left = 2 * i + 1; // Left  node
      int right = 2 * i + 2; // Right node
      int largest = i;

      // Right child is larger than parent
      if (right < n && ar[right] > ar[largest]) largest = right;

      // Left child is larger than parent
      if (left < n && ar[left] > ar[largest]) largest = left;

      // Move down the tree following the largest node
      if (largest != i) {
        swap(ar, largest, i);
        i = largest;
      } else break;
    }
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  /* TESTING */

  public static void main(String[] args) {
    Heapsort sorter = new Heapsort();
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);
    // Prints:
    // [-13, 2, 3, 4, 4, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(array));
  }
}
