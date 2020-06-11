/**
 * QuickSort3 or Dutch National Flag algorithm is similar to the QuickSort algorithm but has an
 * improved partitioning algorithm. QuickSort is quite slow in the case where very few unique
 * elements exist in the array so the QuickSort3 algorithm is used at that time.
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.QuickSort3
 *
 * @author Atharva Thorve, aaathorve@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.Random;

public class QuickSort3 implements InplaceSort {
  private static Random random = new Random();

  @Override
  public void sort(int[] values) {
    QuickSort3.quickSort3(values);
  }

  public static void quickSort3(int[] ar) {
    if (ar == null) return;
    QuickSort3.randomizedQuickSort(ar, 0, ar.length - 1);
  }

  // partiton array in such a way that all the elements whose value is equal to
  // pivot are grouped together
  private static int[] partition3(int[] a, int l, int r) {
    int j, k;
    if (r - l <= 1) {
      if (a[r] < a[l]) {
        swap(a, l, r);
      }
      j = l;
      k = r;
      int[] m = {j, k};
      return m;
    }
    int mid = l;
    int p = a[r];
    while (mid <= r) {
      if (a[mid] < p) {
        swap(a, l, mid);
        l++;
        mid++;
      } else if (a[mid] == p) {
        mid++;
      } else {
        swap(a, mid, r);
        r--;
      }
    }
    j = l - 1;
    k = mid;
    int[] m = {j, k};
    return m;
  }

  // Sort interval [lo, hi] inplace recursively
  // This chooses random pivot value thus improving time complexity
  private static void randomizedQuickSort(int[] a, int l, int r) {
    if (l >= r) {
      return;
    }
    int k = random.nextInt(r - l + 1) + l;
    int t = a[l];
    a[l] = a[k];
    a[k] = t;
    // use partition3
    int[] m = partition3(a, l, r);
    randomizedQuickSort(a, l, m[0]);
    randomizedQuickSort(a, m[1], r);
  }

  // Swap two elements
  private static void swap(int[] ar, int i, int j) {
    int tmp = ar[i];
    ar[i] = ar[j];
    ar[j] = tmp;
  }

  public static void main(String[] args) {
    InplaceSort sorter = new QuickSort3();
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);
    // Prints:
    // [-13, 2, 3, 4, 4, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(array));
  }
}
