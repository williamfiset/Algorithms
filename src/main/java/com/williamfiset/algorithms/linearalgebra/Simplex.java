package com.williamfiset.algorithms.linearalgebra;

/**
 * Simplex Algorithm for Linear Programming
 *
 * Maximizes a linear objective function subject to linear inequality
 * constraints. Uses the standard tableau simplex method with Bland's-like
 * pivot selection (most negative coefficient in the objective row).
 *
 * Input format (tableau matrix m):
 *   - m[0] = objective row: m[0][0] is the current objective value,
 *     m[0][j] (j >= 1) are the negated coefficients of the objective function
 *   - m[i] (i >= 1) = constraint rows: m[i][0] is the RHS constant,
 *     m[i][j] (j >= 1) are the constraint coefficients
 *
 * Before calling, normalize the problem:
 *   1) RHS must be non-negative (multiply by -1 if needed)
 *   2) Add slack variables for <= inequalities
 *   3) Add surplus + artificial variables for >= inequalities
 *   4) For artificial variables, first maximize -(sum of artificials);
 *      if optimum is 0, remove artificial columns and re-run
 *
 * Time:  O(n^3) per pivot, exponential worst case (rare in practice)
 * Space: O(1) (in-place)
 *
 * @author Thomas Finn Lidbetter
 */
public class Simplex {

  private static final double EPS = 1e-9;

  /**
   * Runs the simplex algorithm on the given tableau and returns the
   * maximum value of the objective function.
   *
   * @param m the simplex tableau (modified in-place)
   * @return the maximum objective value (m[0][0] after termination)
   */
  public static double simplex(double[][] m) {
    while (true) {
      // Find the most negative coefficient in the objective row (pivot column)
      double min = -EPS;
      int c = -1;
      for (int j = 1; j < m[0].length; j++) {
        if (m[0][j] < min) {
          min = m[0][j];
          c = j;
        }
      }
      if (c < 0) break; // All coefficients non-negative → optimal

      // Find the pivot row using the minimum ratio test
      min = Double.MAX_VALUE;
      int r = -1;
      for (int i = 1; i < m.length; i++) {
        if (m[i][c] > EPS) {
          double v = m[i][0] / m[i][c];
          if (v < min) {
            min = v;
            r = i;
          }
        }
      }

      // Pivot: scale pivot row, then eliminate pivot column from all other rows
      double v = m[r][c];
      for (int j = 0; j < m[r].length; j++)
        m[r][j] /= v;
      for (int i = 0; i < m.length; i++) {
        if (i != r) {
          v = m[i][c];
          for (int j = 0; j < m[i].length; j++)
            m[i][j] -= m[r][j] * v;
        }
      }
    }
    return m[0][0];
  }
}
