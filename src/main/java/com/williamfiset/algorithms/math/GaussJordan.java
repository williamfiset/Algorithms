package com.williamfiset.algorithms.math;

import java.util.Arrays;

public final class GaussJordan {

  private GaussJordan() { // prevent instantiation
  }

  /**
   * solves a linear equation system<br>
   * A linear equation system like <code>
   * ax*by=e cx*dy=f
   * </code> is defined as <code>
   * new double[][]{ {a,b,e}, {c,d,f} }
   * </code> If the equation system has n variables, the array needs to have n rows and every row
   * needs to have n+1 elements. <br>
   * If variables are left out, those must be represented with <code>0</code>.
   *
   * @param matrix the equation system in matrix representation where the last column is the result
   * @return the solution of the equation system
   * @throws IllegalArgumentException if the equation system has an invalid size, is unsolvable or
   *     if it has infinity solutions
   */
  public static double[] calculate(double[][] matrix) {
    validate(matrix);

    matrix = deepCopy(matrix);
    // region By removing the first row and column,
    // you get a residual matrix where the recursive steps are executed.
    // This is done as long as the matrix is in line step shape
    for (int start = 0; start < matrix.length; start++) {
      calculate(matrix, start);
    }
    // endregion
    // region Remove the corresponding multiples from the above rows so that only zeros are above a
    // leading 1
    for (int i = 0; i < matrix.length; i++) {
      for (int j = i + 1; j < matrix[i].length - 1; j++) {
        if (!isZero(matrix[i][j])) {
          if (isZero(matrix[j][j])) {
            throw new IllegalArgumentException("Equation cannot be solved.");
          }
          double factor = matrix[i][j] / matrix[j][j];
          for (int k = 0; k < matrix[i].length; k++) {
            matrix[i][k] -= factor * matrix[j][k];
          }
        }
      }
    }
    // endregion
    // region convert result to an array
    double[] result = new double[matrix.length];
    for (int i = 0; i < result.length; i++) {
      result[i] = matrix[i][matrix[i].length - 1];
    }
    // endregion
    return result;
  }

  /**
   * performs a deep copy of a two-dimensional double-array
   *
   * @param original the array to copy
   * @return a deep copy of the passed array
   */
  private static double[][] deepCopy(double[][] original) {
    final double[][] result = new double[original.length][];
    for (int i = 0; i < original.length; i++) {
      result[i] = Arrays.copyOf(original[i], original[i].length);
    }
    return result;
  }

  /**
   * tests if a <code>double</code> is zero or not.<br>
   *
   * @param value the <code>double</code> value to test
   * @return <code>true</code> if it is zero or negative zero, else <code>false</code>
   */
  private static boolean isZero(double value) {
    return value == 0 || value == -0;
  }

  /**
   * Tests if a linear equation system has the correct size in order to be solved.
   *
   * @param matrix the equation system represented as <code>double[][]</code>
   */
  private static void validate(double[][] matrix) {
    if (matrix.length == 0) {
      throw new IllegalArgumentException("The matrix is not allowed to have a length of zero.");
    }
    for (int i = 0; i < matrix.length; i++) {
      if (matrix[i].length != matrix.length + 1) {
        throw new IllegalArgumentException("The matrix needs to have one more column than rows.");
      }
    }
  }

  /**
   * The recursive part of the Gauss-Jordan algorithm<br>
   *
   * @param matrix the equation system matrix
   * @param start the index where the query should start
   */
  private static void calculate(double[][] matrix, int start) { // recursive part of the operation
    // region Select the first column from the left that contains at least one non-zero value
    int index = getIndexFromColumnWithNonZeroValues(matrix, start);
    if (index == -1) {
      throw new IllegalArgumentException("The linear equation system has infinity solutions.");
    }
    // endregion
    // region If the top number of the selected column is zero,
    // swap the first line with another row where a non-zero value exist in this column
    if (isZero(matrix[start][index])) {
      int swpIndex = getIndexFromRowWhereElementIsNotZero(matrix, index, start);
      // swap matrix[0] and matrix[swpIndex]
      double[] tmp = matrix[start];
      matrix[start] = matrix[swpIndex];
      matrix[swpIndex] = tmp;
    }
    // endregion
    // region Substract the corresponding multiple of the first line with the next line.
    // The goal of this is that the first element in every row (except the first) is zero.
    for (int i = start + 1; i < matrix.length; i++) {
      double factor = matrix[i][start] / matrix[start][start];
      for (int j = start; j < matrix[i].length; j++) {
        matrix[i][j] -= factor * matrix[start][j];
      }
    }
    // endregion
    // region Divide the first row by the top element of the selected column
    double quotient = matrix[start][index];
    for (int i = start; i < matrix[start].length; i++) {
      matrix[start][i] /= quotient;
    }
    // endregion
  }

  /**
   * Gets the index of the row where a non-zero element exists at a specified index
   *
   * @param matrix the two-dimensional array to test
   * @param elemIndex the column index where a non-zero element exists
   * @param start the minimum row index to look for
   * @return the index of a row with a non-zero element at <code>elemIndex</code> or <code>-1</code>
   *     if no such row exists
   */
  private static int getIndexFromRowWhereElementIsNotZero(
      double[][] matrix, int elemIndex, int start) {
    for (int i = start; i < matrix.length; i++) {
      if (!isZero(matrix[i][elemIndex])) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Gets the index of a column that contains at least one non-zero value
   *
   * @param matrix the matrix to look for
   * @param start the minimum row index to look for
   * @return the index of a column that contains a non-zero value or <code>-1</code> if no such
   *     column exists
   */
  private static int getIndexFromColumnWithNonZeroValues(double[][] matrix, int start) {
    for (int i = start; i < matrix[start].length; i++) {
      for (int j = start; j < matrix.length; j++) {
        if (!isZero(matrix[j][i])) {
          return i;
        }
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    System.out.println(
        Arrays.toString(
            calculate(
                new double[][] {
                  {1, 2, 5},
                  {2, 1, 4}
                })));
  }
}
