package com.williamfiset.algorithms.search;

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

  public static int findKthLargest(int[] array, int k) {
    int start = 0;
    int end = array.length - 1;
    int index = array.length - k;
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
