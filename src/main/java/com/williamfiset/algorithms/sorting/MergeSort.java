/**
 * Mergesort implementation
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.Mergesort
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Arrays;

// Mergesort implements InplaceSort for ease of testings, but in reality
// it is not really a good fit for an inplace sorting algorithm.
public class MergeSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    int[] sortedValues = MergeSort.mergesort(values);
    for (int i = 0; i < values.length; i++) {
      values[i] = sortedValues[i];
    }
  }

  public static int[] mergesort(int[] ar) {
    // Base case is when a single element (which is already sorted)
    int n = ar.length;
    if (n <= 1) return ar;

    // Split array into two parts and recursively sort them
    int[] left = mergesort(Arrays.copyOfRange(ar, 0, n / 2));
    int[] right = mergesort(Arrays.copyOfRange(ar, n / 2, n));

    // Combine the two arrays into one larger array
    return merge(left, right);
  }

  // Merge two sorted arrays into a larger sorted array
  private static int[] merge(int[] ar1, int[] ar2) {
    int n1 = ar1.length, n2 = ar2.length;
    int n = n1 + n2, i1 = 0, i2 = 0;
    int[] ar = new int[n];

    for (int i = 0; i < n; i++) {
      if (i1 == n1) {
        ar[i] = ar2[i2++];
      } else if (i2 == n2) {
        ar[i] = ar1[i1++];
      } else {
        if (ar1[i1] < ar2[i2]) {
          ar[i] = ar1[i1++];
        } else {
          ar[i] = ar2[i2++];
        }
      }
    }
    return ar;
  }

  public static void main(String[] args) {
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    array = MergeSort.mergesort(array);
    // Prints:
    // [-13, 2, 3, 4, 4, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(array));
  }
}
