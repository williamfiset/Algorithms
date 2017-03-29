/**
 *
 * Homogeneous linear recurrence relations with constant coefficients 
 * are some of the most common types of recurrences. The recurrence for 
 * the Fibonacci numbers: f(n) = f(n-1) + f(n-2) with f(0) = f(1) = 1
 * is a classic example of such a recurrence.
 *
 * In this file I present some code to solve a general linear recurrence of the form:
 * f(n) = k + c_1*f(n-1) + c_2*f(n-2) + c_3*f(n-3) + ...
 * where k is a constant, c_i is a constant and all the initial values are known
 * Look at the examples given in the main method to see how to use this code
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

class LinearRecurrenceSolver {

  static long[][] matrixDeepCopy(long[][] M) {
    final int N = M.length;
    long[][] newMatrix = new long[N][N];
    for(int i = 0; i < N; i++)
      newMatrix[i] = M[i].clone();
    return newMatrix;
  }

  // Perform matrix multiplication whilst modding the values, O(n^3)
  static long[][] squareMatrixMult(long[][] m1, long[][] m2) {

    final int N = m1.length;
    long[][] newMatrix = new long[N][N];

    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        for (int k = 0; k < N; k++ )
          newMatrix[i][j] = newMatrix[i][j] + m1[i][k]*m2[k][j]; // Overflow can happen here, watch out!

    return newMatrix;

  }

  // Raise a matrix to the nth power. If n is negative 
  // return null and if n is zero return the identity.
  // NOTE: Make sure the matrix is a square matrix and
  // also watch out for overflow as the numbers climb quickly
  static long[][] matrixPower(long[][] matrix, long n) {

    if (n < 0) return null;

    final int N = matrix.length;
    long[][] newMatrix = null;

    if (n == 0) {
      newMatrix = new long[N][N];
      for(int i = 0; i < N; i++)
        newMatrix[i][i] = 1L;
    } else {

      long[][] P = matrixDeepCopy(matrix);

      while(n > 0) {

        if ( (n & 1L) == 1L ) {
          if (newMatrix == null) newMatrix = matrixDeepCopy(P);
          else newMatrix = squareMatrixMult(newMatrix, P);
        }

        // Repeatedly square P every loop, O(m³)
        P = squareMatrixMult(P, P);
        n >>= 1L;

      }
    }

    return newMatrix;

  }

  // Construct the transformation matrix
  // http://fusharblog.com/solving-linear-recurrence-for-programming-contest/
  static long [][] createTransformationMatrix(long[] coeffs, int size) {

    long T[][] = new long[size][size];
    for(int i = 0; i+1 < size; i++) T[i][i+1] = 1L;
    for(int i = 0; i < size-1; i++) T[size-2][i] = coeffs[coeffs.length-i-1];
    T[size-1][size-1] = T[size-2][size-1] = 1L;
    return T;

  }

  // Solves for the nth term in a linear recurrence of the following form:
  // f(n) = k + c_1*f(n-1) + c_2*f(n-2) + c_3*f(n-3) + ...
  // initialValues contains the first N initial values for [f(0), f(1), f(2), ...] 
  // coeffs contains the coefficients for the recurrence, so [c_1, c_2, c_3, ...]
  // Make sure the dimensions of coeffs and initialValues are no bigger than they need to be!
  // For instance, do not add an additional term to the recurrence with a zero coefficient.
  static long solveRecurrence(long[] initialValues, long [] coeffs, long k, long n) {

    if (n < 0) throw new IllegalArgumentException();
    if (initialValues.length != coeffs.length) throw new IllegalArgumentException();

    // We already know the value
    if (n < initialValues.length) return initialValues[ (int) n ];

    // Account for the extra constant 'k' in the recurrence: f(n) = k + c_1*f(n-1) + ...
    final int size = initialValues.length + 1;

    long[][] T = createTransformationMatrix(coeffs, size);
    long[][] result = matrixPower(T, n);

    // Find answer by multiplying resultant matrix with multiplication
    // vector, that is the initial values appended with the constant k
    long ans = 0L;
    for (int j = 0; j < size; j++) {
      if (j == size-1) {
        ans = ans + result[0][j]*k;
      } else {
        ans = ans + result[0][j]*initialValues[j];
      }
    }

    return ans;

  }

  public static void main(String[] args) {
    
    // Setup the Fibonacci recurrence
    long[] initialValues = {0, 1}; // f(0) = 0 and f(1) = 1
    long[] coefficients  = {1, 1}; // f(n) = 1*f(n-1) + 1*f(n-2) + 0
    long k = 0;

    for (int i = 0; i <= 10; i++) {
      long fib  = solveRecurrence( initialValues, coefficients, k, i );
      System.out.println(fib);
    }

    // Suppose we have the following recurrence:
    // f(n) = 2 + 2f(n-1) + f(n-3) with f(0) = 2 and f(n) = 0 if n < 0
    // and we want to know what f(25) is here is what we would do:
    // We need to find f(1), f(2) to invoke the recurrence solver:
    // 
    // f(n) = 0 if n < 0
    // f(0) = 2
    // f(1) = 2 + 2f(0) + f(-2) = 2 + 2*2 = 6
    // f(2) = 2 + 2f(1) + f(-1) = 2 + 2*6 = 14

    long[] coefficients2  = {2, 0, 1};
    long[] initialValues2 = {2, 6, 14};
    k = 2;
    
    final int N = 25;
    long[] DP = new long[N+1];

    for (int n = 0; n <= N; n++) {

      // Compute the answers for the recurrence using dynamic programming (DP)
      // then use these generated answers to verify we got the right answer
      if (n - 1 >= 0) DP[n] += 2*DP[n-1];
      if (n - 3 >= 0) DP[n] += DP[n-3];
      DP[n] += k;

      long answer = solveRecurrence(initialValues2, coefficients2, k, n);
      if (DP[n] != answer) throw new RuntimeException("Wrong answer!");
      System.out.printf("f(%d) = %d\n", n, answer);

    }

  }
  
}


