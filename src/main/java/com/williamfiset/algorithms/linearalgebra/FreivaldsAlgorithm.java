package com.williamfiset.algorithms.linearalgebra;

import java.util.Arrays;

/**
 * Freivald's Algorithm for Probabilistic Matrix Multiplication Verification
 *
 * Given three n x n matrices A, B, and C, determines whether A * B = C
 * without computing the full product. Instead, it picks a random binary
 * vector r and checks if A * (B * r) = C * r, which takes O(n^2) per trial.
 *
 * Repeating k independent trials gives a failure probability less than 2^(-k).
 *
 * Use cases:
 *   - Verifying matrix multiplication faster than recomputing it
 *   - Randomized algorithms and Monte Carlo methods
 *
 * Time:  O(k*n^2)
 * Space: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class FreivaldsAlgorithm {

  /**
   * Verifies whether A * B = C using k independent random trials.
   *
   * @param A first n x n matrix
   * @param B second n x n matrix
   * @param C the alleged product matrix (A * B)
   * @param k number of trials (failure probability < 2^(-k))
   * @return true if the test passes all k rounds (likely equal), false if definitely not equal
   *
   * Time: O(k*n^2)
   */
  public static boolean freivalds(int[][] A, int[][] B, int[][] C, int k) {
    final int n = A.length;
    if (A[0].length != n || B.length != n || B[0].length != n || C.length != n || C[0].length != n)
      throw new IllegalArgumentException("Input must be three nxn matrices");

    int[] v = new int[n];
    do {
      randomizeVector(v);

      // Compare C*v against A*(B*v) — both are O(n^2) matrix-vector products
      int[] expected = product(v, C);
      int[] result = product(product(v, B), A);

      if (!Arrays.equals(expected, result)) return false;
    } while (--k > 0);

    return true;
  }

  /** Randomly sets each element of the vector to 0 or 1. */
  private static void randomizeVector(int[] vector) {
    for (int i = 0; i < vector.length; i++) {
      vector[i] = (Math.random() < 0.5) ? 0 : 1;
    }
  }

  /** Computes the product of a vector with a matrix, O(n^2). */
  private static int[] product(int[] v, int[][] matrix) {
    int n = matrix.length;
    int[] result = new int[n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        result[i] += v[j] * matrix[i][j];
    return result;
  }
}
