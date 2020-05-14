package com.williamfiset.algorithms.sorting;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

import com.williamfiset.algorithms.utils.TestUtils;
import java.util.Arrays;
import org.junit.Test;

public class SortingTest {

  @Test
  public void verifySortingAlgorithms_smallPositiveIntegersOnly() {
    InplaceSort[] sortingAlgorithms = {
      new BubbleSort(),
      new BucketSort(),
      new CountingSort(),
      new Heapsort(),
      new InsertionSort(),
      new MergeSort(),
      new QuickSort(),
      new RadixSort(),
      new SelectionSort()
    };
    for (int size = 0; size < 1000; size++) {
      for (InplaceSort sorter : sortingAlgorithms) {
        int[] values = TestUtils.randomIntegerArray(size, 1, 50);
        int[] copy = values.clone();

        Arrays.sort(values);
        sorter.sort(copy);

        assertThat(values).isEqualTo(copy);
      }
    }
  }
}
