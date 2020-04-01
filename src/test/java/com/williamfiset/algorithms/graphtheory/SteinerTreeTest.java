package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;

import org.junit.*;

public class SteinerTreeTest {

  static final double INF = Double.POSITIVE_INFINITY;

  ///////////////////////////////////
  //                               //
  //  Visualization of the graph:  //
  //                               //
  //            [O]                //
  //            / \                //
  //           3   4               //
  //          /     \              //
  //        [1]     [2]            //
  //         |       |             //
  //         5       6             //
  //         |       |             //
  //        [3]     [4]            //
  //                               //
  ///////////////////////////////////
  static double[][] matrix1 =
      new double[][] {
        {0, 3, 4, INF, INF},
        {3, 0, INF, 5, INF},
        {4, INF, 0, INF, 6},
        {INF, 5, INF, 0, INF},
        {INF, INF, 6, INF, 0}
      };

  ///////////////////////////////////
  //                               //
  //  Visualization of the graph:  //
  //                               //
  //           [0]-3-[1]           //
  //            |     |            //
  //            5     4            //
  //            |     |            //
  //           [2]   [3]           //
  //            | \   |            //
  //            7  8  1            //
  //            |   \ |            //
  //           [4]-2-[5]           //
  //                               //
  ///////////////////////////////////
  static double[][] matrix2 =
      new double[][] {
        {0, 3, 5, INF, INF, INF},
        {3, 0, INF, 4, INF, INF},
        {5, INF, 0, INF, 7, 8},
        {INF, 4, INF, 0, INF, 1},
        {INF, INF, 7, INF, 0, 2},
        {INF, INF, 8, 1, 2, 0}
      };

  @Test
  public void testNoTerminalNodesGivesZero() {
    assertThat(testSteiner(matrix1)).isEqualTo(0.0);
    assertThat(testSteiner(matrix2)).isEqualTo(0.0);
  }

  @Test
  public void testOneTerminalNodeGivesZero() {
    for (int i = 0; i < matrix1.length; i++) {
      assertThat(testSteiner(matrix1, 0)).isEqualTo(0.0);
    }
    for (int i = 0; i < matrix2.length; i++) {
      assertThat(testSteiner(matrix2, 0)).isEqualTo(0.0);
    }
  }

  @Test
  public void testTreeGraph() {
    assertThat(testSteiner(matrix1, 2, 4)).isEqualTo(6.0);
    assertThat(testSteiner(matrix1, 0, 3)).isEqualTo(8.0);
    assertThat(testSteiner(matrix1, 1, 2, 4)).isEqualTo(13.0);
    assertThat(testSteiner(matrix1, 4, 2, 1)).isEqualTo(13.0);
    assertThat(testSteiner(matrix1, 3, 0, 4)).isEqualTo(18.0);
    assertThat(testSteiner(matrix1, 1, 2, 3, 4)).isEqualTo(18.0);
  }

  @Test
  public void testCycleGraph() {
    assertThat(testSteiner(matrix2, 0, 5)).isEqualTo(8.0);
    assertThat(testSteiner(matrix2, 5, 0)).isEqualTo(8.0);
    assertThat(testSteiner(matrix2, 4, 0)).isEqualTo(10.0);
    assertThat(testSteiner(matrix2, 2, 4, 5)).isEqualTo(9.0);
    assertThat(testSteiner(matrix2, 3, 1, 0)).isEqualTo(7.0);
    assertThat(testSteiner(matrix2, 3, 0)).isEqualTo(7.0);
    assertThat(testSteiner(matrix2, 3, 0, 5)).isEqualTo(8.0);
    assertThat(testSteiner(matrix2, 0, 4, 5)).isEqualTo(10.0);
  }

  private double testSteiner(double[][] distances, int... subsetToConnect) {
    return SteinerTree.minLengthSteinerTree(distances, subsetToConnect);
  }
}
