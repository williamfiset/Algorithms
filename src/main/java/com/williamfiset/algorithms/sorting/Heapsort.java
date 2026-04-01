/**
 * In-place heapsort. Builds a max-heap in O(n), then repeatedly extracts the maximum.
 *
 * <p>Time: O(n log(n))
 *
 * <p>Space: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Arrays;

public class Heapsort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    Heapsort.heapsort(values);
  }

  private static void heapsort(int[] ar) {
    if (ar == null)
      return;
    int n = ar.length;

    // Build max-heap from the array bottom-up in O(n).
    for (int i = n / 2 - 1; i >= 0; i--)
      sink(ar, n, i);

    // Repeatedly swap the max to the end and restore the heap.
    for (int i = n - 1; i >= 0; i--) {
      swap(ar, 0, i);
      sink(ar, i, 0);
    }
  }

  // Sinks element at index i down to its correct position in a max-heap of size n.
  private static void sink(int[] ar, int n, int i) {
    for (int left = 2 * i + 1; left < n; left = 2 * i + 1) {
      int right = left + 1;
      int largest = i;

      if (ar[left] > ar[largest])
        largest = left;
      if (right < n && ar[right] > ar[largest])
        largest = right;
      if (largest == i)
        return;

      swap(ar, largest, i);
      i = largest;
    }
  }

  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {
    Heapsort sorter = new Heapsort();
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);
    System.out.println(Arrays.toString(array)); // [-13, 2, 3, 4, 4, 6, 8, 10]
  }
}
