package com.williamfiset.algorithms.linearalgebra;

/**
 * Matrix Determinant via Laplace (Cofactor) Expansion
 *
 * Computes the determinant of an n x n matrix by recursively expanding
 * along the first row. Each expansion reduces the matrix size by 1,
 * creating n subproblems of size (n-1) x (n-1).
 *
 * Mathematically elegant but computationally expensive — not practical
 * for matrices larger than about 7-8. For larger matrices, use
 * Gaussian elimination (O(n^3)) instead.
 *
 * Includes optimized closed-form formulas for 1x1, 2x2, and 3x3 bases.
 *
 * Time:  ~O((n+2)!)
 * Space: O(n^2*n!) due to recursive submatrix allocation
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class MatrixDeterminantLaplaceExpansion {

  private static final double EPS = 0.00000001;

  /**
   * Computes the determinant of an n x n matrix.
   *
   * @param matrix the square matrix
   * @return the determinant value
   *
   * Time: ~O((n+2)!)
   */
  public static double determinant(double[][] matrix) {
    final int n = matrix.length;
    if (n == 1) return matrix[0][0];
    if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    return laplace(matrix);
  }

  /**
   * Recursive cofactor expansion along the first row.
   * Base case is the 3x3 Sarrus formula.
   */
  private static double laplace(double[][] m) {
    final int n = m.length;
    if (n == 3) {
      double a = m[0][0], b = m[0][1], c = m[0][2];
      double d = m[1][0], e = m[1][1], f = m[1][2];
      double g = m[2][0], h = m[2][1], i = m[2][2];
      return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }
    int det = 0;
    for (int i = 0; i < n; i++) {
      double c = m[0][i];
      if (c > EPS) {
        int sign = ((i & 1) == 0) ? +1 : -1;
        det += sign * m[0][i] * laplace(constructMinor(m, 0, i));
      }
    }
    return det;
  }

  /**
   * Constructs the (n-1) x (n-1) minor matrix by excluding the
   * specified row and column from the input matrix.
   */
  private static double[][] constructMinor(double[][] mat, int excludingRow, int excludingCol) {
    int n = mat.length;
    double[][] minor = new double[n - 1][n - 1];
    int rPtr = -1;
    for (int i = 0; i < n; i++) {
      if (i == excludingRow) continue;
      ++rPtr;
      int cPtr = -1;
      for (int j = 0; j < n; j++) {
        if (j == excludingCol) continue;
        minor[rPtr][++cPtr] = mat[i][j];
      }
    }
    return minor;
  }

  public static void main(String[] args) {
    System.out.println(determinant(new double[][] {{6}})); // 6
    System.out.println(determinant(new double[][] {{1, 2}, {3, 4}})); // -2
    System.out.println(determinant(new double[][] {
      {1, -2, 3}, {4, -5, 6}, {7, -8, 10}
    })); // 3
    System.out.println(determinant(new double[][] {
      {1, -2, 3, 7}, {4, -5, 6, 2}, {7, -8, 10, 3}, {-8, 10, 3, 2}
    })); // -252
  }
}
