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
    //I want to print a on each branch of the program
    while (true) {
      System.out.println("simplex: Branch 0");
      double min = -EPS;
      int c = -1;
      for (int j = 1; j < m[0].length; j++) {
        System.out.println("simplex: Branch 1");
        if (m[0][j] < min) {
          System.out.println("simplex: Branch 2");
          min = m[0][j];
          c = j;
        }
      }
      if (c < 0) {
        System.out.println("simplex: Branch 3");
        break;
      };
      min = Double.MAX_VALUE;
      int r = -1;
      for (int i = 1; i < m.length; i++) {
        System.out.println("simplex: Branch 4");
        if (m[i][c] > EPS) {
          System.out.println("simplex: Branch 5");
          double v = m[i][0] / m[i][c];
          if (v < min) {
            System.out.println("simplex: Branch 6");
            min = v;
            r = i;
          }
        }
      }
      double v = m[r][c];
      for (int j = 0; j < m[r].length; j++) {
        System.out.println("simplex: Branch 7");
        m[r][j] /= v;
      };
      for (int i = 0; i < m.length; i++) {
        System.out.println("simplex: Branch 8");
        if (i != r) {
          System.out.println("simplex: Branch 9");
          v = m[i][c];
          for (int j = 0; j < m[i].length; j++){
            System.out.println("simplex: Branch 10");
            m[i][j] -= m[r][j] * v;
          };
        }
      }
    }
    System.out.println("simplex: Main Branch");
    return m[0][0];
  }
}
