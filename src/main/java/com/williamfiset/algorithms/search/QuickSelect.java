package com.williamfiset.algorithms.search;

/*
 *  quickselect is a selection algorithm to find the kth smallest element in an unordered list
 *  Worst-case performance	О(n2)
 *  Best-case performance	О(n)
 *  Average performance	O(n)
 *  See: https://en.wikipedia.org/wiki/Quickselect
 *  for further details
 */

public class QuickSelect {
  public static int partition(int[] array, int start, int end) {
    int pivot = start;
    int temp;
    while (start < end) {
      while (start <= end && array[start] < array[pivot]) start++;

      while (start <= end && array[end] > array[pivot]) end--;

      if (start > end) break;
      temp = array[start];
      array[start] = array[end];
      array[end] = temp;
    }
    temp = array[end];
    array[end] = array[pivot];
    array[pivot] = temp;
    return end;
  }

  public static int findKthSmallest(int[] array, int k) {
    int start = 0;
    int end = array.length - 1;
    int index = k-1;
    while (start < end) {
      int pivot = partition(array, start, end);
      if (pivot < index) {
        start = pivot + 1;
      } else if (pivot > index) {
        end = pivot - 1;
      } else {
        return array[pivot];
      }
    }
    return array[start];
  }
}
