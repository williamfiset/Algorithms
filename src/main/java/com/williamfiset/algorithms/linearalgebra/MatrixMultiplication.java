package com.williamfiset.algorithms.linearalgebra;

import java.util.Arrays;

/**
 * Standard Matrix Multiplication
 *
 * Computes the product C = A * B using the naive triple-loop algorithm.
 * Matrix A has dimensions (aRows x aCols) and B has (bRows x bCols);
 * multiplication is only valid when aCols == bRows, producing a
 * (aRows x bCols) result.
 *
 * Time:  O(n^3) for n x n matrices
 * Space: O(n^2) for the result matrix
 *
 * @author Micah Stairs
 */
class MatrixMultiplication {

  /**
   * Returns the product of matrices a and b, or null if dimensions are incompatible.
   *
   * @param a the left matrix (aRows x aCols)
   * @param b the right matrix (bRows x bCols), requires aCols == bRows
   * @return the product matrix (aRows x bCols), or null if aCols != bRows
   *
   * Time: O(aRows * bCols * aCols)
   */
  static double[][] multiply(double[][] a, double[][] b) {
    int aRows = a.length, aCols = a[0].length;
    int bRows = b.length, bCols = b[0].length;
    if (aCols != bRows) return null;
    double[][] c = new double[aRows][bCols];
    for (int i = 0; i < aRows; i++)
      for (int j = 0; j < bCols; j++)
        for (int k = 0; k < aCols; k++)
          c[i][j] += a[i][k] * b[k][j];
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
    double[][] c = multiply(a, b);
    for (double[] row : c) System.out.println(Arrays.toString(row));
  }
}
