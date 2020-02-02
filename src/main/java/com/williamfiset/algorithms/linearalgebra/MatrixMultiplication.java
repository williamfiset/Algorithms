/**
 * Multiply two matrices together and get their product
 *
 * <p>Time Complexity: O(n^3)
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.linearalgebra;

class MatrixMultiplication {

  // Returns the result of the product of the matrices 'a' and 'b'
  // or null if the matrices are the wrong dimensions
  static double[][] multiply(double[][] a, double[][] b) {
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

    double[][] c = multiply(a, b);
    for (double[] row : c) System.out.println(java.util.Arrays.toString(row));
  }
}
