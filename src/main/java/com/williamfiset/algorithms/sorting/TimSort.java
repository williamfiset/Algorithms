/**
 * Tim sort implementation — a hybrid sorting algorithm combining merge sort and insertion sort.
 *
 * Tim sort divides the array into small chunks called "runs" and sorts each run using insertion
 * sort (which is efficient for small or nearly-sorted data). It then merges the runs using a
 * merge step similar to merge sort. This is the algorithm used by Java's Arrays.sort() for objects
 * and Python's built-in sort.
 *
 * Time Complexity: O(n log n) worst case, O(n) best case (already sorted)
 * Space Complexity: O(n) for the merge buffer
 *
 * @author Claude
 */
package com.williamfiset.algorithms.sorting;

public class TimSort implements InplaceSort {

  private static final int MIN_RUN = 32;

  @Override
  public void sort(int[] values) {
    timSort(values);
  }

  public static void timSort(int[] ar) {
    if (ar == null || ar.length <= 1)
      return;

    int n = ar.length;

    // Sort individual runs using insertion sort
    for (int i = 0; i < n; i += MIN_RUN)
      insertionSort(ar, i, Math.min(i + MIN_RUN - 1, n - 1));

    // Merge runs, doubling the merge size each iteration
    for (int size = MIN_RUN; size < n; size *= 2) {
      for (int left = 0; left < n; left += 2 * size) {
        int mid = Math.min(left + size - 1, n - 1);
        int right = Math.min(left + 2 * size - 1, n - 1);
        if (mid < right)
          merge(ar, left, mid, right);
      }
    }
  }

  /** Insertion sort on ar[lo..hi] inclusive. */
  private static void insertionSort(int[] ar, int lo, int hi) {
    for (int i = lo + 1; i <= hi; i++) {
      int key = ar[i];
      int j = i - 1;
      while (j >= lo && ar[j] > key) {
        ar[j + 1] = ar[j];
        j--;
      }
      ar[j + 1] = key;
    }
  }

  /** Merges two sorted sub-arrays ar[lo..mid] and ar[mid+1..hi]. */
  private static void merge(int[] ar, int lo, int mid, int hi) {
    int len1 = mid - lo + 1;
    int len2 = hi - mid;

    int[] left = new int[len1];
    int[] right = new int[len2];
    System.arraycopy(ar, lo, left, 0, len1);
    System.arraycopy(ar, mid + 1, right, 0, len2);

    int i = 0, j = 0, k = lo;
    while (i < len1 && j < len2) {
      // Use <= to maintain stability: equal elements from the left run come first
      if (left[i] <= right[j])
        ar[k++] = left[i++];
      else
        ar[k++] = right[j++];
    }
    while (i < len1)
      ar[k++] = left[i++];
    while (j < len2)
      ar[k++] = right[j++];
  }

  public static void main(String[] args) {
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    timSort(array);
    // Prints:
    // [-13, 2, 3, 4, 4, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(array));
  }
}
