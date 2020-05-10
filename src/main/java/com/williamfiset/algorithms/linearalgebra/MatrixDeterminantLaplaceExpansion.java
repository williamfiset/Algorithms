/**
 * This is an implementation of finding the determinant of an nxn matrix using Laplace/cofactor
 * expansion. Although this method is mathematically beautiful, it is computationally intensive and
 * not practical for matrices beyond the size of 7-8.
 *
 * <p>Time Complexity: ~O((n+2)!)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.linearalgebra;

public class MatrixDeterminantLaplaceExpansion {

  // Define a small value of epsilon to compare double values
  static final double EPS = 0.00000001;

  public static void main(String[] args) {

    double[][] m = {{6}};
    System.out.println(determinant(m)); // 6

    m =
        new double[][] {
          {1, 2},
          {3, 4}
        };
    System.out.println(determinant(m)); // -2

    m =
        new double[][] {
          {1, -2, 3},
          {4, -5, 6},
          {7, -8, 10}
        };
    System.out.println(determinant(m)); // 3

    m =
        new double[][] {
          {1, -2, 3, 7},
          {4, -5, 6, 2},
          {7, -8, 10, 3},
          {-8, 10, 3, 2}
        };
    System.out.println(determinant(m)); // -252

    m =
        new double[][] {
          {1, -2, 3, 7},
          {4, -5, 6, 2},
          {7, -8, 10, 3},
          {-8, 10, 3, 2}
        };
    System.out.println(determinant(m)); // -252

    m =
        new double[][] {
          {1, -2, 3, 7, 12},
          {4, -5, 6, 2, 4},
          {7, -8, 10, 3, 1},
          {-8, 10, 8, 3, 2},
          {5, 5, 5, 5, 5}
        };
    System.out.println(determinant(m)); // -27435

    System.out.println();

    for (int n = 1; ; n++) {
      m = new double[n][n];
      for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++) m[i][j] = Math.floor(Math.random() * 10);
      System.out.printf("Found determinant of %dx%d matrix to be: %.4f\n", n, n, determinant(m));
    }
  }

  // Given an n*n matrix, this method finds the determinant using Laplace/cofactor expansion.
  // Time Complexity: ~O((n+2)!)
  public static double determinant(double[][] matrix) {

    final int n = matrix.length;

    // Use closed form for 1x1 determinant
    if (n == 1) return matrix[0][0];

    // Use closed form for 2x2 determinant
    if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

    // For 3x3 matrices and up use Laplace/cofactor expansion
    return laplace(matrix);
  }

  // This method uses cofactor expansion to compute the determinant
  // of a matrix. Unfortunately, this method is very slow and uses
  // A LOT of memory, hence it is not too practical for large matrices.
  private static double laplace(double[][] m) {

    final int n = m.length;

    // Base case is 3x3 determinant
    if (n == 3) {
      double a = m[0][0], b = m[0][1], c = m[0][2];
      double d = m[1][0], e = m[1][1], f = m[1][2];
      double g = m[2][0], h = m[2][1], i = m[2][2];
      return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }

    int det = 0;

    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {

        double c = m[i][j];
        if (Math.abs(c) > EPS) {
          double[][] newMatrix = constructMatrix(m, j);
          double parity = ((j & 1) == 0) ? +1 : -1;
          det += parity * c * laplace(newMatrix);
        }
      }
    }

    return det;
  }

  // Constructs a matrix one dimension smaller than the last by
  // excluding the top row and some selected column. This
  // method ends up consuming a lot of space we called recursively multiple times
  // since it allocates meory for a new matrix.
  private static double[][] constructMatrix(double[][] m, int skipColumn) {

    int n = m.length;
    double[][] newMatrix = new double[n - 1][n - 1];

    int ii = 0;
    for (int i = 1; i < n; i++, ii++) {
      int jj = 0;
      for (int j = 0; j < n; j++) {
        if (j == skipColumn) continue;
        double v = m[i][j];
        newMatrix[ii][jj++] = v;
      }
    }

    return newMatrix;
  }
}
