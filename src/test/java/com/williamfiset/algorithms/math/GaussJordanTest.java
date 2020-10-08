package com.williamfiset.algorithms.math;

import java.util.Random;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class GaussJordanTest {

  public static final int RAND_VAR_AMOUNT = 100;
  public static final int MAX_RAND_VALUE = 100;

  @Test
  public void test3x3() {
    // 1x+1y+1z=0
    // 4x+2y+1z=1
    // 9x+3y+1z=3
    double[][] matrix = { // example from wikipedia (german)
      {1, 1, 1, 0},
      {4, 2, 1, 1},
      {9, 3, 1, 3}
    };
    test(matrix);
  }

  @Test
  public void testEquationWithoutSolution() {
    double[][] matrix = {
      {1, 3, 1, 9}, // 1x+3y+1z=9
      {1, 1, -1, 1}, // 1x+1y-1z=1
      {3, 11, 5, 32} // 3x+11y+5z=32
    };
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> test(matrix),
        "An unsolvable equation system was solved.");
  }

  @Test
  public void testEquationWithInfinitySolutions() {
    double[][] matrix = {
      {1, 2, 3},
      {1, 2, 3},
    };
    Assertions.assertThrows(
        IllegalArgumentException.class,
        () -> test(matrix),
        "An equation with infinity solutions system was solved.");
  }

  @Test
  public void text6x6() {
    double[][] matrix = {
      {0, 0, 1, 0, 0, 0, 0},
      {9, 3, 1, 0, 0, 0, 2},
      {0, 0, 0, 9, 3, 1, 2},
      {0, 0, 0, 16, 4, 1, -1},
      {6, 1, 0, -6, 1, 0, 0},
      {0, 0, 0, 8, 1, 0, 0}
    };
    test(matrix);
  }

  @Test
  public void testRandom() {
    Random rand = new Random();
    double[][] randMatrix = new double[RAND_VAR_AMOUNT][RAND_VAR_AMOUNT + 1]; // create matrix
    for (int i = 0; i < randMatrix.length; i++) { // fill with random numbers
      for (int j = 0; j < randMatrix[i].length; j++) {
        // from -MAX_RAND_VALUE to MAX_RAND_VALUE
        randMatrix[i][j] = rand.nextInt(MAX_RAND_VALUE * 2) - MAX_RAND_VALUE;
        if (j == randMatrix.length) { // result column
          // let result be higher for more "realistic" results
          randMatrix[i][j] *= RAND_VAR_AMOUNT;
        }
      }
    }
    test(randMatrix);
  }

  private void test(double[][] matrix) {
    // double[][] copy = deepCopy(matrix);//save the original equation system
    double[] result = GaussJordan.calculate(matrix); // calculate the solution
    validateResult(matrix, result);
  }

  private static void validateResult(double[][] matrix, double[] result) {
    // region test result
    for (int i = 0; i < matrix.length; i++) {
      double value = 0;
      for (int j = 0; j < result.length; j++) { // for each element in that row
        // add the factor (in the original matrix) multiplied with the calculated value to the
        // result
        value += matrix[i][j] * result[j];
      }
      Assertions.assertEquals(
          matrix[i][matrix.length],
          value,
          Math.max(Math.abs(matrix[i][matrix.length]), 0.01),
          "The result of line " + i + " is invalid.");
    }
    // endregion
  }
}
