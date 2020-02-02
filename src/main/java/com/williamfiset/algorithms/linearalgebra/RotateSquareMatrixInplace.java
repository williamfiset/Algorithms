/**
 * Rotate the entries of a square matrix 90 degrees clockwise.
 *
 * <p>Time Complexity: O(n^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.linearalgebra;

public class RotateSquareMatrixInplace {

  // Rotates the entries of a square matrix 90 degrees clockwise.
  static void rotate(int[][] matrix) {
    int n = matrix.length;
    for (int i = 0; i < n / 2; i++) {
      int invI = n - i - 1;
      for (int j = i; j < invI; j++) {
        int invJ = n - j - 1, tmp = matrix[i][j];
        matrix[i][j] = matrix[invJ][i];
        matrix[invJ][i] = matrix[invI][invJ];
        matrix[invI][invJ] = matrix[j][invI];
        matrix[j][invI] = tmp;
      }
    }
  }

  public static void main(String[] args) {

    int[][] matrix = {
      {1, 2, 3, 4, 5},
      {6, 7, 8, 9, 10},
      {11, 12, 13, 14, 15},
      {16, 17, 18, 19, 20},
      {21, 22, 23, 24, 25}
    };

    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));
    // prints:
    // [21, 16, 11, 6, 1]
    // [22, 17, 12, 7, 2]
    // [23, 18, 13, 8, 3]
    // [24, 19, 14, 9, 4]
    // [25, 20, 15, 10, 5]

    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));
    // prints:
    // [25, 24, 23, 22, 21]
    // [20, 19, 18, 17, 16]
    // [15, 14, 13, 12, 11]
    // [10, 9, 8, 7, 6]
    // [5, 4, 3, 2, 1]

    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));
    // prints:
    // [5, 10, 15, 20, 25]
    // [4, 9, 14, 19, 24]
    // [3, 8, 13, 18, 23]
    // [2, 7, 12, 17, 22]
    // [1, 6, 11, 16, 21]

    rotate(matrix);
    for (int[] row : matrix) System.out.println(java.util.Arrays.toString(row));
    // prints:
    // [1, 2, 3, 4, 5]
    // [6, 7, 8, 9, 10]
    // [11, 12, 13, 14, 15]
    // [16, 17, 18, 19, 20]
    // [21, 22, 23, 24, 25]

  }
}
