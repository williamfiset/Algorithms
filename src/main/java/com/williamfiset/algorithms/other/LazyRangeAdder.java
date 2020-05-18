/**
 * The LazyRangerAdder is a handy class for performing addition range updates of constant values on
 * an array. This range adder is especially useful for offline algorithms which know all range
 * updates ahead of time.
 *
 * <p>Time complexity to update O(1) but time complexity to finalize all additions is O(n)
 *
 * @author Atharva Thorve, aaathorve@gmail.com
 */
package com.williamfiset.algorithms.other;

public class LazyRangeAdder {

  // The number of elements in the input array.
  private int n;

  // The original input array
  private int[] array;

  // The difference array with the deltas between values, size n+1
  private int[] differenceArray;

  // Initialize an instance of a LazyRangeAdder on some input values
  public LazyRangeAdder(int[] array) {
    this.array = array;
    this.n = array.length;

    differenceArray = new int[n + 1];
    differenceArray[0] = array[0];
    for (int i = 1; i < n; i++) {
      differenceArray[i] = array[i] - array[i - 1];
    }
  }

  // Add `x` to the range [l, r] inclusive
  public void add(int l, int r, int x) {
    differenceArray[l] += x;
    differenceArray[r + 1] -= x;
  }

  // IMPORTANT: Make certain to call this method once all the additions
  // have been made with add(l, r, x)
  public void done() {
    for (int i = 0; i < n; i++) {
      if (i == 0) {
        array[i] = differenceArray[i];
      } else {
        array[i] = differenceArray[i] + array[i - 1];
      }
    }
  }

  public static void main(String[] args) {
    // Array to be updated
    int[] array = {10, 4, 6, 13, 8, 15, 17, 22};
    LazyRangeAdder lazyRangeAdder = new LazyRangeAdder(array);

    // After below add(l, r, x), the
    // elements should become [10, 14, 16, 23, 18, 15, 17, 22]
    lazyRangeAdder.add(1, 4, 10);
    lazyRangeAdder.done();
    System.out.println(java.util.Arrays.toString(array));

    // After below add(l, r, x), the
    // elements should become [22, 26, 28, 30, 25, 22, 24, 34]
    lazyRangeAdder.add(3, 6, -5);
    lazyRangeAdder.add(0, 7, 12);
    lazyRangeAdder.done();
    System.out.println(java.util.Arrays.toString(array));
  }
}
