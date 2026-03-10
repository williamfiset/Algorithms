package com.williamfiset.algorithms.linearalgebra;

/**
 * Linear Recurrence Solver via Matrix Exponentiation
 *
 * Solves general linear recurrences of the form:
 *   f(n) = k + c_1*f(n-1) + c_2*f(n-2) + ... + c_m*f(n-m)
 * in O(m^3*log(n)) time by converting the recurrence into a matrix
 * multiplication problem and using binary exponentiation.
 *
 * The key idea: the recurrence can be expressed as a matrix equation
 *   [f(n), f(n-1), ..., f(n-m+1), k]^T = T^n * [f(0), ..., k]^T
 * where T is the (m+1) x (m+1) transformation matrix.
 *
 * Use cases:
 *   - Computing the nth Fibonacci number in O(log(n))
 *   - Any constant-coefficient linear recurrence
 *
 * Time:  O(m^3*log(n)) where m = number of recurrence terms
 * Space: O(m^2) for the transformation matrix
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
class LinearRecurrenceSolver {

  /**
   * Solves for the nth term of the recurrence f(n) = k + c_1*f(n-1) + ... + c_m*f(n-m).
   *
   * @param coefficients [c_1, c_2, ..., c_m]
   * @param f_0          the value of f(0), usually 1 or k
   * @param k            the constant added each step
   * @param n            which term to compute
   * @return f(n)
   *
   * Time: O(m^3*log(n))
   */
  static long solveRecurrence(long[] coefficients, long f_0, long k, long n) {
    if (n < 0) throw new IllegalArgumentException("n should probably be >= 0");
    long[] initialValues = computeInitialValues(coefficients, f_0, k);

    // Already computed during initialization
    if (n < initialValues.length) return initialValues[(int) n];

    // +1 to account for the extra constant k in the recurrence
    final int size = initialValues.length + 1;

    long[][] T = createTransformationMatrix(coefficients, size);
    long[][] result = matrixPower(T, n);

    // Multiply result matrix with the initial values vector (plus k)
    long ans = 0L;
    for (int j = 0; j < size; j++) {
      ans += result[0][j] * (j == size - 1 ? k : initialValues[j]);
    }
    return ans;
  }

  /**
   * Computes initial values [f(0), f(1), ..., f(m-1)] by brute-force DP.
   * Assumes f(n) = 0 when n < 0.
   */
  static long[] computeInitialValues(long[] coeffs, long f_0, long k) {
    final int m = coeffs.length;
    long[] dp = new long[m];
    dp[0] = f_0;
    for (int n = 1; n < m; n++) {
      for (int i = 1; i <= n; i++)
        dp[n] += dp[n - i] * coeffs[i - 1];
      dp[n] += k;
    }
    return dp;
  }

  /**
   * Builds the transformation matrix T such that:
   *   [f(n+1), f(n), ..., f(n-m+2), k] = T * [f(n), f(n-1), ..., f(n-m+1), k]
   */
  private static long[][] createTransformationMatrix(long[] coeffs, int size) {
    long[][] T = new long[size][size];
    // Shift rows: f(n-1) → f(n-2), etc.
    for (int i = 0; i + 1 < size; i++)
      T[i][i + 1] = 1L;
    // Recurrence coefficients in reversed order
    for (int i = 0; i < size - 1; i++)
      T[size - 2][i] = coeffs[coeffs.length - i - 1];
    // Constant k propagates unchanged
    T[size - 1][size - 1] = T[size - 2][size - 1] = 1L;
    return T;
  }

  private static long[][] matrixPower(long[][] matrix, long p) {
    if (p < 0) return null;
    final int n = matrix.length;
    if (p == 0) {
      long[][] identity = new long[n][n];
      for (int i = 0; i < n; i++)
        identity[i][i] = 1L;
      return identity;
    }
    long[][] result = null;
    long[][] base = matrixDeepCopy(matrix);
    while (p > 0) {
      if ((p & 1L) == 1L) {
        result = (result == null) ? matrixDeepCopy(base) : squareMatrixMult(result, base);
      }
      base = squareMatrixMult(base, base);
      p >>= 1L;
    }
    return result;
  }

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
    // Fibonacci: f(n) = 0 + 1*f(n-1) + 1*f(n-2), f(0) = 1
    long[] fibCoeffs = {1, 1};
    for (int i = 0; i <= 10; i++) {
      System.out.println(solveRecurrence(fibCoeffs, 1, 0, i));
    }

    // f(n) = 2 + 2*f(n-1) + f(n-3), f(0) = 2
    long[] coeffs = {2, 0, 1};
    long k = 2;
    final int N = 25;

    // Verify against brute-force DP
    long[] dp = new long[N + 1];
    for (int n = 0; n <= N; n++) {
      if (n - 1 >= 0) dp[n] += 2 * dp[n - 1];
      if (n - 3 >= 0) dp[n] += dp[n - 3];
      dp[n] += k;
    }
    long answer = solveRecurrence(coeffs, k, k, N);
    if (dp[N] != answer) throw new RuntimeException("Wrong answer!");
    System.out.printf("f(%d) = %d\n", N, answer);
  }
}
