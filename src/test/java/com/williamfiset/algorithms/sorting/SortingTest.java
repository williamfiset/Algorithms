package com.williamfiset.algorithms.sorting;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import java.util.Arrays;
import java.util.EnumSet;
import org.junit.Test;

// Test all sorting algorithms under various constraints.
//
// Not all sorting algorithms are suitable for every type for test. For instance,
// counting sort cannot sort a range of numbers between [Integer.MIN_VALUE,
// Integer.MAX_VALUE] without running out of memory. Similarly, radix sort
// doesn't play well with negative numbers, etc..
public class SortingTest {

  enum SortingAlgorithm {
    BUBBLE_SORT(new BubbleSort()),
    BUCKET_SORT(new BucketSort()),
    COUNTING_SORT(new CountingSort()),
    HEAP_SORT(new Heapsort()),
    INSERTION_SORT(new InsertionSort()),
    MERGE_SORT(new MergeSort()),
    QUICK_SORT(new QuickSort()),
    QUICK_SORT3(new QuickSort3()),
    RADIX_SORT(new RadixSort()),
    SELECTION_SORT(new SelectionSort());

    private InplaceSort algorithm;

    SortingAlgorithm(InplaceSort algorithm) {
      this.algorithm = algorithm;
    }

    public InplaceSort getSortingAlgorithm() {
      return algorithm;
    }
  }

  private static final EnumSet<SortingAlgorithm> sortingAlgorithms =
      EnumSet.of(
          SortingAlgorithm.BUBBLE_SORT,
          SortingAlgorithm.BUCKET_SORT,
          SortingAlgorithm.COUNTING_SORT,
          SortingAlgorithm.HEAP_SORT,
          SortingAlgorithm.INSERTION_SORT,
          SortingAlgorithm.MERGE_SORT,
          SortingAlgorithm.QUICK_SORT,
          SortingAlgorithm.QUICK_SORT3,
          SortingAlgorithm.RADIX_SORT,
          SortingAlgorithm.SELECTION_SORT);

  @Test
  public void verifySortingAlgorithms_smallPositiveIntegersOnly() {
    for (int size = 0; size < 1000; size++) {
      for (SortingAlgorithm algorithm : sortingAlgorithms) {
        InplaceSort sorter = algorithm.getSortingAlgorithm();
        int[] values = TestUtils.randomIntegerArray(size, 0, 51);
        int[] copy = values.clone();

        Arrays.sort(values);
        sorter.sort(copy);

        assertThat(values).isEqualTo(copy);
      }
    }
  }

  @Test
  public void verifySortingAlgorithms_smallNegativeIntegersOnly() {
    for (int size = 0; size < 1000; size++) {
      for (SortingAlgorithm algorithm : sortingAlgorithms) {
        // Skip radix sort since our implementation doesn't handle negative
        // numbers.
        if (algorithm == SortingAlgorithm.RADIX_SORT) {
          continue;
        }
        InplaceSort sorter = algorithm.getSortingAlgorithm();
        int[] values = TestUtils.randomIntegerArray(size, -50, 51);
        int[] copy = values.clone();

        Arrays.sort(values);
        sorter.sort(copy);

        assertThat(values).isEqualTo(copy);
      }
    }
  }
}
