/**
 * Update range of an array from index l to index r by value x by difference array method
 *
 * <p>Time complexity to update O(1) but time complexity to print is still O(n);
 *
 * @author Atharva Thorve, aaathorve@gmail.com
 */
package com.williamfiset.algorithms.other;

public class RangeUpdate {

  // Creates a diff array differenceArray[] for array[] and returns
  // it after filling initial values.
  public static void initializeDiffArray(int[] array, int[] differenceArray) {
    int n = array.length;
    differenceArray[0] = array[0];
    differenceArray[n] = 0;
    for (int i = 1; i < n; i++) {
      differenceArray[i] = array[i] - array[i - 1];
    }
  }

  // Does range update
  public static void update(int[] differenceArray, int l, int r, int x) {
    differenceArray[l] += x;
    differenceArray[r + 1] -= x;
  }

  // Call this method after all the queries are done
  public static void updateArray(int[] array, int[] differenceArray) {
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

  public static void printArray(int[] array) {
    int n = array.length;
    for (int i = 0; i < n; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {

    // Array to be updated
    int[] array = {10, 4, 6, 13, 8, 15, 17, 22};
    int n = array.length;
    // Create and fill difference Array
    // We use one extra space because
    // update(l, r, x) updates D[r+1]
    int differenceArray[] = new int[n + 1];
    initializeDiffArray(array, differenceArray);

    // After below update(l, r, x), the
    // elements should become 10, 14, 16, 23, 18, 15, 17, 22
    update(differenceArray, 1, 4, 10);
    updateArray(array, differenceArray);
    printArray(array);

    // After below update(l, r, x), the
    // elements should become 22, 26, 28, 30, 25, 22, 24, 34
    update(differenceArray, 3, 6, -5);
    update(differenceArray, 0, 7, 12);
    updateArray(array, differenceArray);
    printArray(array);
  }
}
