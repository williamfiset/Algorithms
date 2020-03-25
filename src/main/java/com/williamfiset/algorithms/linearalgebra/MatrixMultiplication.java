/**
 * Multiply two matrices together and get their product
 *
 * <p>Time Complexity: O(n^3)
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.linearalgebra;
import org.checkerframework.checker.nullness.qual.Nullable;

class MatrixMultiplication {

  // Returns the result of the product of the matrices 'a' and 'b'
  // or null if the matrices are the wrong dimensions

  // As stated in the above comment, the return value will be null if the
  // two matrices have wrong dimensions for multiplication. Hence, return
  // value can be null also.

  // if number of rows of a is not equal to number of columns of b,
  // then matrix multiplication is not possible and null will be returned
  static double @Nullable[][] multiply(double[][] a, double[][] b) {
    int aRows = a.length, aCols = a[0].length;
    int bRows = b.length, bCols = b[0].length;
    if (aCols != bRows) return null;
    double[][] c = new double[aRows][bCols];
    for (int i = 0; i < aRows; i++)
      for (int j = 0; j < bCols; j++) for (int k = 0; k < aCols; k++) c[i][j] += a[i][k] * b[k][j];
    return c;
  }

  public static void main(String[] args) {

    double[][] a = {
      {1, 2, 3, 4},
      {4, 3, 2, 1},
      {1, 2, 2, 1}
    };

    double[][] b = {
      {1, 0},
      {2, 1},
      {0, 3},
      {0, 0}
    };

    // if matrix a and b are not multiplicable, then null value will be returned
    double @Nullable[][] c = multiply(a, b);

    // dereferencing a possibly null matrix c may throw null pointer exception
    if(c != null)
      for (double[] row : c) System.out.println(java.util.Arrays.toString(row));
  }
}
