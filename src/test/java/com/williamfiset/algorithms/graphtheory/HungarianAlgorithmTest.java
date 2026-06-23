package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.jupiter.api.*;

class HungarianAlgorithmTest {

  @Test
  void findMaxWeightMatching() {
    HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm();

    double[][] matrix = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };

    int[] matching = hungarianAlgorithm.findMaxWeightMatching(matrix);

    int[] expectedMatching = {2, 1, 0};

    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  void expandMatrix() {
    HungarianAlgorithm hungarianAlgorithm = new HungarianAlgorithm();

    double[][] matrix = {
      {1, 2},
      {3, 4}
    };

    int targetRows = 3;
    int targetCols = 3;

    double[][] expandedMatrix = hungarianAlgorithm.expandMatrix(matrix, targetRows, targetCols);

    double[][] expectedMatrix = {
      {1, 2, 0},
      {3, 4, 0},
      {0, 0, 0}
    };

    assertThat(expandedMatrix).isEqualTo(expectedMatrix);
  }
}
