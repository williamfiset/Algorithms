/**
 * Bucket sort implementation
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.*;

public class BucketSort {

  // Performs a bucket sort of an array in which all the elements are
  // bounded in the range [minVal, maxVal]. For bucket sort to give linear
  // performance the elements need to be uniformly distributed
  public static void bucketSort(int[] ar, final int minVal, final int maxVal) {

    if (ar == null || ar.length == 0 || minVal == maxVal) return;

    // N is number elements and M is the range of values
    final int N = ar.length, M = maxVal - minVal, NUM_BUCKETS = M / N + 1;
    List<List<Integer>> buckets = new ArrayList<>(NUM_BUCKETS);
    for (int i = 0; i < NUM_BUCKETS; i++) buckets.add(new ArrayList<>());

    // Place each element in a bucket
    for (int i = 0; i < N; i++) {
      int bi = (ar[i] - minVal) / M;
      List<Integer> bucket = buckets.get(bi);
      bucket.add(ar[i]);
    }

    // Sort buckets and stitch together answer
    for (int bi = 0, j = 0; bi < NUM_BUCKETS; bi++) {
      List<Integer> bucket = buckets.get(bi);
      if (bucket != null) {
        Collections.sort(bucket);
        for (int k = 0; k < bucket.size(); k++) {
          ar[j++] = bucket.get(k);
        }
      }
    }
  }

  public static void main(String[] args) {

    int[] array = {10, 4, 6, 8, 13, 2, 3};
    bucketSort(array, 2, 13);
    System.out.println(java.util.Arrays.toString(array));

    array = new int[] {10, 10, 10, 10, 10};
    bucketSort(array, 10, 10);
    System.out.println(java.util.Arrays.toString(array));

    // TODO(williamfiset): move to javatests/...
    runTests();
  }

  static Random RANDOM = new Random();

  public static void runTests() {
    final int NUM_TESTS = 1000;
    for (int i = 1; i <= NUM_TESTS; i++) {

      int[] array = new int[i];
      int maxVal = Integer.MIN_VALUE, minVal = Integer.MAX_VALUE;
      for (int j = 0; j < i; j++) {
        array[j] = randInt(-1000000, +1000000);
        maxVal = Math.max(maxVal, array[j]);
        minVal = Math.min(minVal, array[j]);
      }
      int[] arrayCopy = array.clone();

      bucketSort(array, minVal, maxVal);
      Arrays.sort(arrayCopy);

      if (!Arrays.equals(array, arrayCopy)) System.out.println("ERROR");
    }
  }

  static int randInt(int min, int max) {
    return RANDOM.nextInt((max - min) + 1) + min;
  }
}
