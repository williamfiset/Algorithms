/**
 * Use Gaussian elimination on an augmented matrix to find the inverse of a matrix.
 *
 * <p>Time Complexity: O(n^3)
 */
package com.williamfiset.algorithms.linearalgebra;

class MatrixInverse {

  // Define a small value of epsilon to compare double values
  static final double EPS = 0.00000001;

  // Invert the specified matrix. Assumes invertibility. Time Complexity: O(r²c)
  static double[][] inverse(double[][] matrix) {
    if (matrix.length != matrix[0].length) return null;
    int n = matrix.length;
    double[][] augmented = new double[n][n * 2];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) augmented[i][j] = matrix[i][j];
      augmented[i][i + n] = 1;
    }
    solve(augmented);
    double[][] inv = new double[n][n];
    for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) inv[i][j] = augmented[i][j + n];
    return inv;
  }

  // Solves a system of linear equations as an augmented matrix
  // with the rightmost column containing the constants. The answers
  // will be stored on the rightmost column after the algorithm is done.
  // NOTE: make sure your matrix is consistent and does not have multiple
  // solutions before you solve the system if you want a unique valid answer.
  // Time Complexity: O(r²c)
  static void solve(double[][] augmentedMatrix) {
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
      for (int j = 0; j < nCols; j++) augmentedMatrix[r][j] /= lv;
      for (i = 0; i < nRows; i++) {
        if (i != r) {
          lv = augmentedMatrix[i][lead];
          for (int j = 0; j < nCols; j++) augmentedMatrix[i][j] -= lv * augmentedMatrix[r][j];
        }
      }
      lead++;
    }
  }

  // Checks if the matrix is inconsistent
  static boolean isInconsistent(double[][] arr) {
    int nCols = arr[0].length;
    outer:
    for (int y = 0; y < arr.length; y++) {
      if (Math.abs(arr[y][nCols - 1]) > EPS) {
        for (int x = 0; x < nCols - 1; x++) if (Math.abs(arr[y][x]) > EPS) continue outer;
        return true;
      }
    }
    return false;
  }

  // Make sure your matrix is consistent as well
  static boolean hasMultipleSolutions(double[][] arr) {
    int nCols = arr[0].length, nEmptyRows = 0;
    outer:
    for (int y = 0; y < arr.length; y++) {
      for (int x = 0; x < nCols; x++) if (Math.abs(arr[y][x]) > EPS) continue outer;
      nEmptyRows++;
    }
    return nCols - 1 > arr.length - nEmptyRows;
  }

  public static void main(String[] args) {

    // Check this matrix is invertable
    double[][] matrix = {
      {2, -4, 0},
      {0, 6, 0},
      {2, 2, -2}
    };

    double[][] inv = inverse(matrix);
    for (double[] row : inv) System.out.println(java.util.Arrays.toString(row));
  }
}
