/**
 * This simplex algorithm maximizes an expression subject to a set of constraints
 *
 * <p>Time complexity: O(n^3)
 *
 * @author Thomas Finn Lidbetter
 */
package com.williamfiset.algorithms.linearalgebra;

public class Simplex {

  static final double EPS = 1e-9;

  // The matrix given as an argument represents the function to be maximized
  // and each of the constraints. Constraints and objective function must be
  // normalized first through the following steps:
  // 1) RHS must be non-negative so multiply any inequalities failing this by -1
  // 2) Add positive coefficient slack variable on LHS of any <= inequality
  // 3) Add negative coefficient surplus variable on LHS of any >= inequality
  // 4) Add positive coefficient artificial variable on LHS of any >= inequality and any = equality.
  //
  // If any artificial variables were added, perform simplex once, maximizing the
  // negated sum of the artificial variables. If the maximum value is 0, take the
  // resulting matrix and remove the artificial variable columns and replace function
  // to maximise with original and run simplex again. If maximum value of simplex with
  // artificial variables is non-zero there is no solution. First column of m is the constants
  // on the RHS of all constraints. First row is the expression to maximise with all
  // coefficients negated. M[i][j] is the coefficient of the (j-1)th term in the
  // (i-1)th constraint (0 based).
  public static double simplex(double[][] m) {
    while (true) {
      double min = -EPS;
      int c = -1;
      for (int j = 1; j < m[0].length; j++) {
        if (m[0][j] < min) {
          min = m[0][j];
          c = j;
        }
      }
      if (c < 0) break;
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
      double v = m[r][c];
      for (int j = 0; j < m[r].length; j++) m[r][j] /= v;
      for (int i = 0; i < m.length; i++) {
        if (i != r) {
          v = m[i][c];
          for (int j = 0; j < m[i].length; j++) m[i][j] -= m[r][j] * v;
        }
      }
    }
    return m[0][0];
  }
}
