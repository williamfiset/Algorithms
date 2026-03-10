package com.williamfiset.algorithms.linearalgebra;

/**
 * Gaussian Elimination for Solving Linear Systems
 *
 * Solves a system of linear equations by reducing an augmented matrix
 * [A | b] to reduced row echelon form (RREF). After reduction, the
 * solution (if unique) appears in the rightmost column.
 *
 * The algorithm uses partial pivoting (row swaps) for numerical stability
 * and eliminates both above and below each pivot to produce RREF directly.
 *
 * Use cases:
 *   - Solving systems of linear equations
 *   - Checking for inconsistency or infinite solutions
 *
 * Time:  O(r^2*c) where r = rows, c = columns
 * Space: O(1) (in-place)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
class GaussianElimination {

  private static final double EPS = 0.00000001;

  /**
   * Reduces an augmented matrix to RREF in-place. After solving, the
   * rightmost column contains the solution values (if the system is
   * consistent with a unique solution).
   *
   * @param augmentedMatrix the [A | b] matrix to reduce
   *
   * Time: O(r^2*c)
   */
  static void solve(double[][] augmentedMatrix) {
    int nRows = augmentedMatrix.length, nCols = augmentedMatrix[0].length, lead = 0;
    for (int r = 0; r < nRows; r++) {
      if (lead >= nCols) break;

      // Find pivot: row with non-zero entry in the lead column
      int i = r;
      while (Math.abs(augmentedMatrix[i][lead]) < EPS) {
        if (++i == nRows) {
          i = r;
          if (++lead == nCols) return;
        }
      }

      // Swap pivot row into position
      double[] temp = augmentedMatrix[r];
      augmentedMatrix[r] = augmentedMatrix[i];
      augmentedMatrix[i] = temp;

      // Scale pivot row so leading entry becomes 1
      double lv = augmentedMatrix[r][lead];
      for (int j = 0; j < nCols; j++)
        augmentedMatrix[r][j] /= lv;

      // Eliminate all other rows in this column
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

  /**
   * Checks if the reduced matrix represents an inconsistent system
   * (a row of all zeros on the left with a non-zero constant on the right).
   */
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

  /**
   * Checks if the reduced matrix has more unknowns than non-empty rows,
   * indicating infinitely many solutions. Call after verifying consistency.
   */
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
    // Suppose we want to solve the following system for
    // the variables x, y, z:
    //
    // 2x - 3y + 5z = 10
    // x  + 2y - z  = 18
    // 6x -  y + 0  = 12
    // Then we would setup the following augment matrix:
    double[][] augmentedMatrix = {
      {2, -3, 5, 10},
      {1, 2, -1, 18},
      {6, -1, 0, 12}
    };

    solve(augmentedMatrix);

    if (!hasMultipleSolutions(augmentedMatrix) && !isInconsistent(augmentedMatrix)) {
      double x = augmentedMatrix[0][3];
      double y = augmentedMatrix[1][3];
      double z = augmentedMatrix[2][3];
      // x ~ 3.755, y ~ 10.531, z ~ 6.816
      System.out.printf("x = %.3f, y = %.3f, z = %.3f\n", x, y, z);
    }
  }
}
