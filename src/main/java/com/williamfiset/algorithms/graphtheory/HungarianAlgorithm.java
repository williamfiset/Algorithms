package com.williamfiset.algorithms.graphtheory;

import java.util.Arrays;

public class HungarianAlgorithm {
  private double[][] weightMatrix;
  private int numRows;
  private int numCols;
  private int[] rowMatches;
  private int[] colMatches;
  private boolean[] rowVisited;

  public int[] findMaxWeightMatching(double[][] matrix) {
    weightMatrix = matrix;
    numRows = matrix.length;
    numCols = matrix[0].length;
    int maxDim = Math.max(numRows, numCols);
    rowMatches = new int[maxDim];
    colMatches = new int[maxDim];
    rowVisited = new boolean[maxDim];

    // Initialize matching array
    Arrays.fill(rowMatches, -1);
    Arrays.fill(colMatches, -1);

    // Add virtual vertices to equalize the number of personnel and tasks
    if (numRows > numCols) {
      weightMatrix = expandMatrix(matrix, numRows, numRows);
      numCols = numRows;
    } else if (numCols > numRows) {
      weightMatrix = expandMatrix(matrix, numCols, numCols);
      numRows = numCols;
    }

    // Find the match with the maximum weight for each row
    for (int row = 0; row < numRows; row++) {
      Arrays.fill(rowVisited, false);
      if (findAugmentingPath(row)) {
        Arrays.fill(rowVisited, false); // Reset access status
      }
    }

    return Arrays.copyOf(rowMatches, numRows);
  }

  private boolean findAugmentingPath(int row) {
    if (!rowVisited[row]) {
      rowVisited[row] = true;
      for (int col = 0; col < numCols; col++) {
        if (isMatchValid(row, col)) {
          // The current match is valid
          if (colMatches[col] == -1 || findAugmentingPath(colMatches[col])) {
            // Augmentation path found, update matching
            rowMatches[row] = col;
            colMatches[col] = row;
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isMatchValid(int row, int col) {
    // Check if there are edges in the weight matrix, or modify other conditions as needed
    return weightMatrix[row][col] > 0;
  }

  public static double[][] expandMatrix(double[][] matrix, int targetRows, int targetCols) {
    double[][] expandedMatrix = new double[targetRows][targetCols];
    for (int row = 0; row < targetRows; row++) {
      if (row < matrix.length) {
        System.arraycopy(matrix[row], 0, expandedMatrix[row], 0, matrix[row].length);
      } else {
        Arrays.fill(expandedMatrix[row], 0.0);
      }
    }
    return expandedMatrix;
  }
}
