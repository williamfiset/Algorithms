package com.williamfiset.algorithms.linearalgebra;

import java.util.Arrays;

/**
 * Matrix Exponentiation (Binary Exponentiation)
 *
 * Raises an n x n square matrix to the power p using repeated squaring
 * (binary exponentiation). Instead of multiplying the matrix p times
 * (O(n^3*p)), this decomposes p into binary and squares the matrix at
 * each bit, achieving O(n^3*log(p)).
 *
 * Use cases:
 *   - Computing Fibonacci numbers in O(log(n))
 *   - Solving linear recurrences efficiently
 *   - Graph path counting (A^k gives the number of k-length paths)
 *
 * Time:  O(n^3*log(p))
 * Space: O(n^2)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class MatrixPower {

  /**
   * Raises a square matrix to the power p using binary exponentiation.
   *
   * @param matrix the n x n matrix to exponentiate
   * @param p      the exponent (returns identity for p=0, null for p<0)
   * @return matrix^p, or null if p is negative
   *
   * Time: O(n^3*log(p))
   */
  static long[][] matrixPower(long[][] matrix, long p) {
    if (p < 0) return null;

    final int n = matrix.length;

    // p = 0 → return identity matrix
    if (p == 0) {
      long[][] identity = new long[n][n];
      for (int i = 0; i < n; i++)
        identity[i][i] = 1L;
      return identity;
    }

    long[][] result = null;
    long[][] base = matrixDeepCopy(matrix);

    // Binary exponentiation: decompose p into bits
    while (p > 0) {
      if ((p & 1L) == 1L) {
        result = (result == null) ? matrixDeepCopy(base) : squareMatrixMult(result, base);
      }
      base = squareMatrixMult(base, base);
      p >>= 1L;
    }

    return result;
  }

  /** Standard O(n^3) matrix multiplication for square matrices. */
  private static long[][] squareMatrixMult(long[][] m1, long[][] m2) {
    final int n = m1.length;
    long[][] result = new long[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        for (int k = 0; k < n; k++)
          result[i][j] += m1[i][k] * m2[k][j];
    return result;
  }

  private static long[][] matrixDeepCopy(long[][] m) {
    final int n = m.length;
    long[][] copy = new long[n][n];
    for (int i = 0; i < n; i++)
      copy[i] = m[i].clone();
    return copy;
  }

  public static void main(String[] args) {

    long[][] matrix = {{2}};

    System.out.println(matrixPower(matrix, 0)[0][0]); // 1
    System.out.println(matrixPower(matrix, 1)[0][0]); // 2
    System.out.println(matrixPower(matrix, 2)[0][0]); // 4
    System.out.println(matrixPower(matrix, 3)[0][0]); // 8
    System.out.println(matrixPower(matrix, 4)[0][0]); // 16
    System.out.println(matrixPower(matrix, 5)[0][0]); // 32
    System.out.println(matrixPower(matrix, 6)[0][0]); // 64

    long[][] matrix2 = {
      {1, 2},
      {3, 4}
    };

    long[][] result = matrixPower(matrix2, 5);
    print2DMatrix(result);
    // prints:
    // [1069, 1558]
    // [2337, 3406]

    result = matrixPower(matrix2, 23);
    print2DMatrix(result);
    // prints:
    // [14853792659417413, 21648320074827046]
    // [32472480112240569, 47326272771657982]

    long[][] identity = {
      {1, 0, 0, 0, 0, 0},
      {0, 1, 0, 0, 0, 0},
      {0, 0, 1, 0, 0, 0},
      {0, 0, 0, 1, 0, 0},
      {0, 0, 0, 0, 1, 0},
      {0, 0, 0, 0, 0, 1}
    };

    result = matrixPower(identity, 987654321987654321L);
    print2DMatrix(result);
    // prints:
    // [1, 0, 0, 0, 0, 0]
    // [0, 1, 0, 0, 0, 0]
    // [0, 0, 1, 0, 0, 0]
    // [0, 0, 0, 1, 0, 0]
    // [0, 0, 0, 0, 1, 0]
    // [0, 0, 0, 0, 0, 1]
  }

  static void print2DMatrix(long[][] M) {
    for (long[] m : M)
      System.out.println(Arrays.toString(m));
    System.out.println();
  }
}
