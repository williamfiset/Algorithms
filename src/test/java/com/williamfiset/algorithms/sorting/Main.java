package com.williamfiset.algorithms.sorting;

import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    int[] numbers = {387, 468, 134, 123, 68, 221, 769, 37, 7, 890, 1, 587};
    RadixSort.radixSort(numbers);
    System.out.println(Arrays.toString(numbers));
  }
}
