package com.williamfiset.algorithms.linearalgebra;

import java.util.Arrays;

/**
 * Matrix Inverse via Gaussian Elimination
 *
 * Finds the inverse of an n x n matrix by augmenting it with the identity
 * matrix [A | I] and reducing to RREF. If A is invertible, the result is
 * [I | A^(-1)].
 *
 * Time:  O(n^3)
 * Space: O(n^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
class MatrixInverse {

  private static final double EPS = 0.00000001;

  /**
   * Computes the inverse of a square matrix using Gaussian elimination.
   *
   * @param matrix the n x n matrix to invert
   * @return the inverse matrix, or null if the matrix is not square
   *
   * Time: O(n^3)
   */
  static double[][] inverse(double[][] matrix) {
    if (matrix.length != matrix[0].length) return null;
    int n = matrix.length;

    // Build augmented matrix [A | I]
    double[][] augmented = new double[n][n * 2];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++)
        augmented[i][j] = matrix[i][j];
      augmented[i][i + n] = 1;
    }

    solve(augmented);

    // Extract the inverse from the right half
    double[][] inv = new double[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        inv[i][j] = augmented[i][j + n];
    return inv;
  }

  /** Reduces an augmented matrix to RREF in-place. */
  private static void solve(double[][] augmentedMatrix) {
    int nRows = augmentedMatrix.length, nCols = augmentedMatrix[0].length, lead = 0;
    for (int r = 0; r < nRows; r++) {
      if (lead >= nCols) break;
      int i = r;
      while (Math.abs(augmentedMatrix[i][lead]) < EPS) {
        if (++i == nRows) {
          i = r;
          if (++lead == nCols) return;
        }
      }
      double[] temp = augmentedMatrix[r];
      augmentedMatrix[r] = augmentedMatrix[i];
      augmentedMatrix[i] = temp;
      double lv = augmentedMatrix[r][lead];
      for (int j = 0; j < nCols; j++)
        augmentedMatrix[r][j] /= lv;
      for (i = 0; i < nRows; i++) {
        if (i != r) {
          lv = augmentedMatrix[i][lead];
          for (int j = 0; j < nCols; j++)
            augmentedMatrix[i][j] -= lv * augmentedMatrix[r][j];
        }
      }
      lead++;
    }
  }

  public static void main(String[] args) {
    double[][] matrix = {
      {2, -4, 0},
      {0, 6, 0},
      {2, 2, -2}
    };
    double[][] inv = inverse(matrix);
    for (double[] row : inv) System.out.println(Arrays.toString(row));
  }
}
