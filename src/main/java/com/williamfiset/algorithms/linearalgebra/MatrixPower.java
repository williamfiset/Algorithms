/**
 * Raise an nxn square matrix to a certain power p.
 *
 * <p>Time Complexity: O(n^3log(p))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.linearalgebra;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MatrixPower {

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

  // the result may be null if power passed is negative
  static long @Nullable[][] matrixPower(long[][] matrix, long p) {

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

  public static void main(String[] args) {

    long[][] matrix = {{2}};

    @SuppressWarnings("nullness")
    long @NonNull[][] ans0 = matrixPower(matrix, 0);
    System.out.println(ans0[0][0]); // 1

    @SuppressWarnings("nullness")
    long @NonNull[][] ans1 = matrixPower(matrix, 1);
    System.out.println(ans1[0][0]); // 2

    @SuppressWarnings("nullness")
    long @NonNull[][] ans2 = matrixPower(matrix, 2);
    System.out.println(ans2[0][0]); // 4

    @SuppressWarnings("nullness")
    long @NonNull[][] ans3 = matrixPower(matrix, 3);
    System.out.println(ans3[0][0]); // 8

    @SuppressWarnings("nullness")
    long @NonNull[][] ans4 = matrixPower(matrix, 4);
    System.out.println(ans4[0][0]); // 16

    @SuppressWarnings("nullness")
    long @NonNull[][] ans5 = matrixPower(matrix, 5);
    System.out.println(ans5[0][0]); // 32

    @SuppressWarnings("nullness")
    long @NonNull[][] ans6 = matrixPower(matrix, 6);
    System.out.println(ans6[0][0]); // 64

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

  // the passed matrix M can be null
  static void print2DMatrix(long @Nullable[][] M) {
    // nullability check before printing the matrix M
    if(M != null)
      for (long[] m : M) System.out.println(java.util.Arrays.toString(m));
    System.out.println();
  }
}
