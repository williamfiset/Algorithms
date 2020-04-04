/**
 * Update range of an array from index l to index r by value x by difference array method.
 * RangeUpdate is primarily used for offline algorithms
 *
 * <p>Time complexity to update O(1) but time complexity to print is still O(n);
 *
 * @author Atharva Thorve, aaathorve@gmail.com
 */
package com.williamfiset.algorithms.other;

public class RangeUpdate {

  private int[] array;
  private int[] differenceArray;

  // Assigns the value to the array instance.
  // Creates a diff array differenceArray[] for array[] and returns
  // it after filling initial values. The size of the difference array needs
  // to be 1 greater than array to be worked on.
  // We use one extra space because
  // update(l, r, x) updates D[r+1]
  public RangeUpdate(int[] array) {
    this.array = array;
    int n = array.length;
    differenceArray = new int[n + 1];
    differenceArray[0] = array[0];
    differenceArray[n] = 0;
    for (int i = 1; i < n; i++) {
      differenceArray[i] = array[i] - array[i - 1];
    }
  }

  // Does range update
  public void update(int l, int r, int x) {
    differenceArray[l] += x;
    differenceArray[r + 1] -= x;
  }

  // Call this method after all the queries are done
  public void finalize() {
    int n = array.length;
    for (int i = 0; i < n; i++) {
      if (i == 0) {
        array[i] = differenceArray[i];
      }
      // Note that A[0] or D[0] decides
      // values of rest of the elements.
      else {
        array[i] = differenceArray[i] + array[i - 1];
      }
    }
  }

  public static void main(String[] args) {

    // Array to be updated
    int[] array = {10, 4, 6, 13, 8, 15, 17, 22};
    RangeUpdate rangeUpdate = new RangeUpdate(array);

    // After below update(l, r, x), the
    // elements should become [10, 14, 16, 23, 18, 15, 17, 22]
    rangeUpdate.update(1, 4, 10);
    rangeUpdate.finalize();
    System.out.println(java.util.Arrays.toString(array));

    // After below update(l, r, x), the
    // elements should become [22, 26, 28, 30, 25, 22, 24, 34]
    rangeUpdate.update(3, 6, -5);
    rangeUpdate.update(0, 7, 12);
    rangeUpdate.finalize();
    System.out.println(java.util.Arrays.toString(array));
  }
}
