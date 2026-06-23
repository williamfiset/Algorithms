/**
 * Shell sort implementation — a generalization of insertion sort that allows the exchange of
 * elements that are far apart.
 *
 * <p>Plain insertion sort only ever compares and swaps neighbouring elements, so an element that
 * belongs near the front but starts near the back has to crawl there one step at a time. Shell sort
 * speeds this up by first sorting elements that are a fixed distance ("gap") apart, then repeatedly
 * shrinking the gap until it reaches 1. The large-gap passes move badly-placed elements most of the
 * way to their final position in big jumps, so by the time we run the final gap-1 pass (an ordinary
 * insertion sort) the array is "almost sorted" and that cheap pass has very little work left to do.
 *
 * <p>This implementation uses the original Shell gap sequence (n/2, n/4, ..., 1). Smarter gap
 * sequences (e.g. Hibbard, Sedgewick) give better worst-case bounds.
 *
 * <p>Time Complexity: O(n^2) worst case with the Shell sequence, roughly O(n^1.5) on average, and
 * O(n log n) best case (already sorted). Space Complexity: O(1) — sorts in place.
 *
 * @author Sculptor
 */
package com.williamfiset.algorithms.sorting;

public class ShellSort implements InplaceSort {

  @Override
  public void sort(int[] values) {
    ShellSort.shellSort(values);
  }

  // Sort the given array in place using shell sort. For each gap in the
  // decreasing sequence n/2, n/4, ..., 1 we run a gapped insertion sort:
  // each element is shifted backwards in steps of `gap` until it sits in the
  // correct position relative to the other elements of its gapped subsequence.
  private static void shellSort(int[] ar) {
    if (ar == null) {
      return;
    }

    int n = ar.length;
    for (int gap = n / 2; gap > 0; gap /= 2) {
      // Gapped insertion sort: ar[gap..n) is inserted into the already
      // gap-sorted elements that precede it.
      for (int i = gap; i < n; i++) {
        int tmp = ar[i];
        int j = i;
        for (; j >= gap && ar[j - gap] > tmp; j -= gap) {
          ar[j] = ar[j - gap];
        }
        ar[j] = tmp;
      }
    }
  }

  public static void main(String[] args) {
    InplaceSort sorter = new ShellSort();
    int[] array = {10, 4, 6, 4, 8, -13, 2, 3};
    sorter.sort(array);
    // Prints:
    // [-13, 2, 3, 4, 4, 6, 8, 10]
    System.out.println(java.util.Arrays.toString(array));
  }
}
