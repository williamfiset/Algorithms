/**
 * This is an implementation of finding the determinant of an nxn matrix using Laplace/cofactor
 * expansion. Although this method is mathematically beautiful, it is computationally intensive and
 * not practical for matrices beyond the size of 7-8.
 *
 * <p>Time Complexity: ~O((n+2)!)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.linearalgebra;

/**
 * This is an implementation of finding the determinant of an nxn matrix using Laplace/cofactor
 * expansion. Although this method is mathematically beautiful, it is computationally intensive and
 * not practical for matrices beyond the size of 7-8.
 *
 * <p>Time Complexity: ~O((n+2)!)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
/**
 * This is an implementation of finding the determinant of an nxn matrix using Laplace/cofactor
 * expansion. Although this method is mathematically beautiful, it is computationally intensive and
 * not practical for matrices beyond the size of 7-8.
 *
 * <p>Time Complexity: ~O((n+2)!)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */

public class MatrixDeterminantLaplaceExpansion {

    // Define a small value of epsilon to compare double values
    static final double EPS = 0.00000001;

    public static void main(String[] args) {

        double[][] m = {{6}};
        System.out.println(determinant(m)); // 6

        m =
                new double[][] {
                        {1, 2},
                        {3, 4}
                };
        System.out.println(determinant(m)); // -2

        m =
                new double[][] {
                        {1, -2, 3},
                        {4, -5, 6},
                        {7, -8, 10}
                };
        System.out.println(determinant(m)); // 3

        m =
                new double[][] {
                        {1, -2, 3, 7},
                        {4, -5, 6, 2},
                        {7, -8, 10, 3},
                        {-8, 10, 3, 2}
                };
        System.out.println(determinant(m)); // -252

        m =
                new double[][] {
                        {1, -2, 3, 7},
                        {4, -5, 6, 2},
                        {7, -8, 10, 3},
                        {-8, 10, 3, 2}
                };
        System.out.println(determinant(m)); // -252

        m =
                new double[][] {
                        {1, -2, 3, 7, 12},
                        {4, -5, 6, 2, 4},
                        {7, -8, 10, 3, 1},
                        {-8, 10, 8, 3, 2},
                        {5, 5, 5, 5, 5}
                };
        System.out.println(determinant(m)); // -27435

        m = new double[][]{
                {1 , 3 , 5 , 9},
                {1 , 3 , 1 , 7},
                {4 , 3 , 9 , 7},
                {5 , 2 , 0 , 9},
        }; // determinant(mat1) = -376 , mat(4 * 4)
        System.out.println(determinant(m));

        m = new double[][]{
                {1 , 3 , 5 , 4},
                {2 , 3 , 1 , 3},
                {4 , 3 , 9 , 7},
                {5 , 2 , 6 , 9},
        }; // determinant(mat2) = -152 , mat(4 * 4)
        System.out.println(determinant(m));
        m = new double[][]{
                {4 , 7 , 2 , 3},
                {1 , 3 , 1 , 2},
                {2 , 5 , 3 , 4},
                {1 , 4 , 2 , 3},
        }; // determinant(mat3) = -3 , mat(4 * 4)
        System.out.println(determinant(m));
         m = new double[][]{
                {1 , 0 , 0 , 0 , 0 , 2},
                {0 , 1 , 0 , 0 , 2 , 0},
                {0 , 0 , 1 , 2 , 0 , 0},
                {0 , 0 , 2 , 1 , 0 , 0},
                {0 , 2 , 0 , 0 , 1 , 0},
                {2 , 0 , 0 , 0 , 0 , 1},
        }; // determinant(mat4) = -27 , mat(6 * 6)
        System.out.println(determinant(m));
        m = new double[][]{
                {1 , 1 , 9 , 3 , 1 , 2 , 3},
                {9 , 1 , 8 , 4 , 2 , 3 , 1},
                {3 , 2 , 7 , 2 , 9 , 5 , 5},
                {4 , 6 , 2 , 1 , 7 , 9 , 6},
                {5 , 3 , 1 , 3 , 1 , 5 , 3},
                {2 , 7 , 9 , 5 , 0 , 1 , 2},
                {2 , 1 , 3 , 8 , 9 , 1 , 4}
        }; // determinant(mat5) = 66704 mat(7 * 7)
        System.out.println(determinant(m));
        m = new double[][]{
                {1 , 1 , 9 , 3 , 1 , 2 , 3 , 9},
                {9 , 1 , 8 , 4 , 2 , 3 , 1 , 8},
                {3 , 2 , 7 , 2 , 9 , 5 , 5 , 7},
                {4 , 6 , 2 , 1 , 7 , 9 , 6 , 6},
                {5 , 3 , 1 , 3 , 1 , 5 , 3 , 5},
                {2 , 7 , 9 , 5 , 0 , 1 , 2 , 4},
                {2 , 1 , 3 , 8 , 9 , 1 , 4 , 3},
                {6 , 1 , 6 , 7 , 9 , 1 , 4 , 2}
        }; // determinant(mat6) = -39240 , mat(8 * 8)
        System.out.println(determinant(m));
        m = new double[][]{
                {1 , 1 , 9 , 3 , 1 , 2 , 3 , 9 , 1},
                {9 , 1 , 8 , 4 , 2 , 3 , 1 , 8 , 2},
                {3 , 2 , 7 , 2 , 9 , 5 , 5 , 7 , 3},
                {4 , 6 , 2 , 1 , 7 , 9 , 6 , 6 , 4},
                {5 , 3 , 1 , 3 , 1 , 5 , 3 , 5 , 5},
                {2 , 7 , 9 , 5 , 0 , 1 , 2 , 4 , 6},
                {2 , 1 , 3 , 8 , 9 , 1 , 4 , 3 , 7},
                {6 , 1 , 6 , 7 , 9 , 1 , 4 , 2 , 8},
                {9 , 8 , 7 , 4 , 3 , 3 , 4 , 2 , 9}
        }; // determinant(mat7) = 1910870 , mat( 9 * 9)
        System.out.println(determinant(m));
        m = new double[][]{
                {1 , 2 , 4 , 8 , 6 , 3 , 4 , 8 , 0 , 2},
                {2 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 1},
                {5 , 2 , 3 , 4 , 8 , 9 , 1 , 9 , 8 , 3},
                {1 , 1 , 1 , 6 , 4 , 2 , 5 , 9 , 8 , 7},
                {9 , 5 , 0 , 1 , 2 , 0 , 6 , 0 , 0 , 0},
                {8 , 4 , 0 , 1 , 2 , 3 , 4 , 5 , 8 , 4},
                {7 , 3 , 3 , 6 , 7 , 8 , 9 , 1 , 7 , 3},
                {1 , 2 , 4 , 0 , 0 , 0 , 0 , 3 , 5 , 2},
                {1 , 1 , 0 , 4 , 5 , 0 , 0 , 4 , 2 , 1},
                {1 , 0 , 0 , 0 , 9 , 0 , 0 , 1 , 1 , 6}
        }; // determinant(mat0) = 17265530 (1.726553E7)
        System.out.println(determinant(m));
    }

    // Given an n*n matrix, this method finds the determinant using Laplace/cofactor expansion.
    // Time Complexity: ~O((n+2)!)
    public static double determinant(double[][] matrix) {

        final int n = matrix.length;

        // Use closed form for 1x1 determinant
        if (n == 1) return matrix[0][0];

        // Use closed form for 2x2 determinant
        if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        // For 3x3 matrices and up use Laplace/cofactor expansion
        return laplace(matrix);
    }

    // This method uses cofactor expansion to compute the determinant
    // of a matrix. Unfortunately, this method is very slow and uses
    // A LOT of memory, hence it is not too practical for large matrices.
    private static double laplace(double[][] m) {

        final int n = m.length;

        // Base case is 3x3 determinant
        if (n == 3) {
            /*
             * Used as a temporary variables to make calculation easy
             * | a  b  c |
             * | d  e  f |
             * | g  h  i |
             */
            double a = m[0][0], b = m[0][1], c = m[0][2];
            double d = m[1][0], e = m[1][1], f = m[1][2];
            double g = m[2][0], h = m[2][1], i = m[2][2];
            return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
        }
        int det = 0;
        for (int i = 0; i < n; i++) {
            double c = m[0][i];
            if (c > EPS){
                int sign = ((i & 1) == 0) ? +1 : -1;
                det += sign * m[0][i] * laplace( constructMatrix(m ,0 , i) );
            }
        }
        return det;
    }

    // Constructs a matrix one dimension smaller than the last by
    // excluding the top row and some selected column. This
    // method ends up consuming a lot of space we called recursively multiple times
    // since it allocates memory for a new matrix.
    private static double[][] constructMatrix(double[][] mat , int excludingRow , int excludingCol){
        int n = mat.length;
        double[][] newMatrix = new double[n - 1][n - 1];
        int rPtr = -1;
        for (int i = 0; i < n; i++) {
            if (i == excludingRow)continue;
            ++rPtr;
            int cPtr = -1;
            for (int j = 0; j < n; j++) {
                if (j == excludingCol)continue;
                newMatrix[rPtr][++cPtr] = mat[i][j];
            } // end of inner loop
        } // end of outer loop
        return newMatrix;
    } // end of createSubMatrix
}
