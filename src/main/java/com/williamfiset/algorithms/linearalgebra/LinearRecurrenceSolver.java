/**
 * In this file I present some code to solve a general linear recurrence of the form: f(n) = k +
 * c_1*f(n-1) + c_2*f(n-2) + c_3*f(n-3) + ... c_m*f(n-m) in O(m^3log(n)) time where k is a constant,
 * c_i is a constant.
 *
 * <p>Homogeneous linear recurrence relations with constant coefficients (like above) are some of
 * the most common types of recurrences. The recurrence for the Fibonacci numbers: f(n) = f(n-1) +
 * f(n-2) with f(0) = f(1) = 1 is a classic example of such a recurrence.
 *
 * <p>To understand how to use this code look at the examples given in the main method
 *
 * <p>Time Complexity: O(m^3log(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.linearalgebra;

class LinearRecurrenceSolver {

  static long[][] matrixDeepCopy(long[][] M) {
    final int N = M.length;
    long[][] newMatrix = new long[N][N];
    for (int i = 0; i < N; i++) newMatrix[i] = M[i].clone();
    return newMatrix;
  }

  // Perform matrix multiplication, O(n^3)
  static long[][] squareMatrixMult(long[][] m1, long[][] m2) {

    final int N = m1.length;
    long[][] newMatrix = new long[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        for (int k = 0; k < N; k++)
          // Overflow can happen here, watch out!
          newMatrix[i][j] = newMatrix[i][j] + m1[i][k] * m2[k][j];

    return newMatrix;
  }

  // Raise a matrix to the pth power. If p is negative
  // return null and if p is zero return the identity.
  // NOTE: Make sure the matrix is a square matrix and
  // also watch out for overflow as the numbers climb quickly!
  static long[][] matrixPower(long[][] matrix, long p) {

    if (p < 0) return null;

    final int N = matrix.length;
    long[][] newMatrix = null;

    // Return identity matrix
    if (p == 0) {
      newMatrix = new long[N][N];
      for (int i = 0; i < N; i++) newMatrix[i][i] = 1L;
    } else {

      long[][] P = matrixDeepCopy(matrix);

      while (p > 0) {

        if ((p & 1L) == 1L) {
          if (newMatrix == null) newMatrix = matrixDeepCopy(P);
          else newMatrix = squareMatrixMult(newMatrix, P);
        }

        // Repeatedly square P every loop, O(n^3)
        P = squareMatrixMult(P, P);
        p >>= 1L;
      }
    }

    return newMatrix;
  }

  // Construct the transformation matrix
  // http://fusharblog.com/solving-linear-recurrence-for-programming-contest/
  static long[][] createTransformationMatrix(long[] coeffs, int size) {

    long T[][] = new long[size][size];
    for (int i = 0; i + 1 < size; i++) T[i][i + 1] = 1L;
    for (int i = 0; i < size - 1; i++) T[size - 2][i] = coeffs[coeffs.length - i - 1];
    T[size - 1][size - 1] = T[size - 2][size - 1] = 1L;
    return T;
  }

  /**
   * Solve for the nth term in a linear recurrence of the following form f(n) = k + c_1*f(n-1) +
   * c_2*f(n-2) + ... + c_m*f(n-m) in O(m^3log(n)) time
   *
   * @param coefficients - contains the coefficients for the recurrence, so [c_1, c_2, c_3, ... ,
   *     c_m]
   * @param f_0 - The value of the function at f(0). This is usually 1 or k.
   * @param k - The constant k added to the recurrence
   * @param n - The nth term of the recurrence you wish to find
   *     <p>NOTE1: The numbers produced by this method can get VERY LARGE quickly so watch out for
   *     overflow because there is a very high probability it will occur.
   *     <p>NOTE2: Make sure the dimension of coefficients array is no bigger than it needs to be.
   *     For instance do not add any additional zero coefficient terms at the end of the
   *     coefficients array as this is throw off the recurrence.
   *     <p>EXAMPLE: If your recurrence is f(n) = 2 + 3*f(n-1) + 5f(n-4) with f(0) = 2 and you want
   *     to find f(100) call the function like: solveRecurrence([3, 0, 0, 5], 2, 2, 100)
   */
  static long solveRecurrence(long[] coefficients, long f_0, long k, long n) {

    if (n < 0) throw new IllegalArgumentException("n should probably be >= 0");
    long[] initialValues = computeInitialValues(coefficients, f_0, k);

    // We already know the value
    if (n < initialValues.length) return initialValues[(int) n];

    // Add 1 to account for the extra constant 'k' in the recurrence: f(n) = k + c_1*f(n-1) + ...
    final int size = initialValues.length + 1;

    long[][] T = createTransformationMatrix(coefficients, size);
    long[][] result = matrixPower(T, n);

    // Find answer by multiplying resultant matrix with multiplication
    // vector, that is the initial values appended with the constant k
    long ans = 0L;
    for (int j = 0; j < size; j++) {
      if (j == size - 1) {
        ans = ans + result[0][j] * k;
      } else {
        ans = ans + result[0][j] * initialValues[j];
      }
    }

    return ans;
  }

  /**
   * You may not always know what the initial values for your recurrence relation are, so sometimes
   * it's useful to brute force them using dynamic programming.
   *
   * <p>Given the constants [c_1, c_2, c_3, ...] and the constant 'k' in the recurrence f(n) = k +
   * c_1*f(n-1) + c_2*f(n-2) + ... + c_m*f(n-m) this function computes and returns the initial
   * values of the function: [f(0), f(1), f(2), ...]
   *
   * @param coeffs - The coefficients on your linear recurrence
   * @param f_0 - The value of the function at f(0), this is usually 1 or k
   * @param k - The constant value in the linear recurrence
   *     <p>NOTE: This method assumes that f(n) = 0 when n < 0
   */
  static long[] computeInitialValues(long[] coeffs, long f_0, long k) {

    final int N = coeffs.length;
    long[] DP = new long[N];
    DP[0] = f_0;

    for (int n = 1; n < N; n++) {
      for (int i = 1; i <= n; i++) DP[n] += DP[n - i] * coeffs[i - 1];
      DP[n] += k;
    }

    return DP;
  }

  public static void main(String[] args) {

    // Setup the Fibonacci recurrence: f(n) = 0 + 1*f(n-1) + 1*f(n-2)
    long[] coefficients = {1, 1};
    long k = 0;

    for (int i = 0; i <= 10; i++) {
      long fib = solveRecurrence(coefficients, 1, k, i);
      System.out.println(fib);
    }

    // Suppose we have the following recurrence:
    // f(n) = 2 + 2f(n-1) + f(n-3) with f(0) = 2 and f(n) = 0 if n < 0
    // and we want to know what f(25) is here is what we would do:
    long[] coefficients2 = {2, 0, 1};
    k = 2;

    final int N = 25;
    long[] DP = new long[N + 1];

    // Compute the answers for the recurrence using dynamic programming (DP)
    // then use these generated answers to verify we got the right answer
    for (int n = 0; n <= N; n++) {
      if (n - 1 >= 0) DP[n] += 2 * DP[n - 1];
      if (n - 3 >= 0) DP[n] += DP[n - 3];
      DP[n] += k;
    }

    long answer = solveRecurrence(coefficients2, k, k, N);
    if (DP[N] != answer) throw new RuntimeException("Wrong answer!");
    System.out.printf("f(%d) = %d\n", N, answer);
  }
}
