package com.williamfiset.algorithms.linearalgebra;

import java.util.Arrays;

/**
 * In-Place Square Matrix Rotation (90 Degrees Clockwise)
 *
 * Rotates an n x n matrix 90 degrees clockwise by cycling four elements
 * at a time in concentric rings from the outside in. No extra matrix is
 * allocated — the rotation is done in-place using a single temp variable.
 *
 * The key insight is that element (i, j) moves to (j, n-1-i). By cycling
 * four elements at once — top→right→bottom→left→top — each element is
 * visited exactly once.
 *
 * Time:  O(n^2)
 * Space: O(1)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class RotateSquareMatrixInplace {

  /**
   * Rotates the entries of a square matrix 90 degrees clockwise in-place.
   *
   * @param matrix the n x n matrix to rotate
   *
   * Time: O(n^2)
   */
  static void rotate(int[][] matrix) {
    int n = matrix.length;
    // Process concentric rings from outside in
    for (int i = 0; i < n / 2; i++) {
      int invI = n - i - 1;
      // Cycle four elements at a time along this ring
      for (int j = i; j < invI; j++) {
        int invJ = n - j - 1, tmp = matrix[i][j];
        matrix[i][j] = matrix[invJ][i];       // left → top
        matrix[invJ][i] = matrix[invI][invJ];  // bottom → left
        matrix[invI][invJ] = matrix[j][invI];  // right → bottom
        matrix[j][invI] = tmp;                 // top → right
      }
    }
  }

  public static void main(String[] args) {
    int[][] matrix = {
      {1, 2, 3, 4, 5},
      {6, 7, 8, 9, 10},
      {11, 12, 13, 14, 15},
      {16, 17, 18, 19, 20},
      {21, 22, 23, 24, 25}
    };

    // Four rotations should return to the original
    for (int r = 0; r < 4; r++) {
      rotate(matrix);
      for (int[] row : matrix) System.out.println(Arrays.toString(row));
      System.out.println();
    }
  }
}
