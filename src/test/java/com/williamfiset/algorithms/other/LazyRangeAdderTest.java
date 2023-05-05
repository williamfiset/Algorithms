package com.williamfiset.algorithms.other;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class LazyRangeAdderTest {

  @Test
  public void rangeUpdateTest1() {
    int[] a = {10, 5, 20, 40};
    LazyRangeAdder lazyRangeAdder = new LazyRangeAdder(a);
    lazyRangeAdder.add(0, 1, 10);
    lazyRangeAdder.add(1, 3, 20);
    lazyRangeAdder.add(2, 2, 30);
    lazyRangeAdder.done();
    int[] expected = {20, 35, 70, 60};
    assertThat(a).isEqualTo(expected);
  }

  @Test
  public void rangeUpdateTest2() {
    int[] a = {270, 311, 427, 535, 334, 193, 174};
    LazyRangeAdder lazyRangeAdder = new LazyRangeAdder(a);
    lazyRangeAdder.add(2, 5, 32);
    lazyRangeAdder.add(0, 4, 101);
    lazyRangeAdder.add(5, 6, -73);
    lazyRangeAdder.done();
    int[] expected = {371, 412, 560, 668, 467, 152, 101};
    assertThat(a).isEqualTo(expected);
  }

  @Test
  public void randomRangeAdditionTests() {
    // Try several different array sizes
    for (int n = 1; n < 1000; n++) {

      int[] arr1 = new int[n];
      randomFill(arr1);
      int[] arr2 = arr1.clone();

      LazyRangeAdder lazyRangeAdder = new LazyRangeAdder(arr1);

      // Do 50 random range adds
      for (int i = 0; i < 50; i++) {
        // Generate a random range
        int l = randValue(0, n);
        int r = randValue(0, n);
        l = Math.min(l, r);
        r = Math.max(l, r);

        int x = randValue(-100, 100);
        lazyRangeAdder.add(l, r, x);
        slowRangeAdd(arr2, l, r, x);
      }

      lazyRangeAdder.done();

      assertThat(arr1).isEqualTo(arr2);
    }
  }

  // Adds `x` to the range [l, r] in arr
  private static void slowRangeAdd(int[] arr, int l, int r, int x) {
    for (int i = l; i <= r; i++) {
      arr[i] += x;
    }
  }

  private static void randomFill(int[] arr) {
    for (int i = 0; i < arr.length; i++) {
      arr[i] = randValue(0, 1000);
    }
  }

  // Generates a random number between [min, max)
  private static int randValue(int min, int max) {
    return min + (int) (Math.random() * ((max - min)));
  }
}
