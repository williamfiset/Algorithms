package com.williamfiset.algorithms.graphtheory;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class TravelingSalesmanProblemTest {

  private static final double EPS = 1e-5;

  @Test
  public void testTspRecursiveInvalidStartNode() {
    double[][] dist = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };
    assertThrows(
        IllegalArgumentException.class, () -> new TspDynamicProgrammingRecursive(321, dist));
  }

  @Test
  public void testTspIterativeInvalidStartNode() {
    double[][] dist = {
      {1, 2, 3},
      {4, 5, 6},
      {7, 8, 9}
    };
    assertThrows(
        IllegalArgumentException.class, () -> new TspDynamicProgrammingIterative(321, dist));
  }

  @Test
  public void testTspRecursiveNonSquareMatrix() {
    double[][] dist = {
      {1, 2, 3},
      {4, 5, 6}
    };
    assertThrows(IllegalStateException.class, () -> new TspDynamicProgrammingRecursive(dist));
  }

  @Test
  public void testTspIterativeNonSquareMatrix() {
    double[][] dist = {
      {1, 2, 3},
      {4, 5, 6}
    };
    assertThrows(IllegalStateException.class, () -> new TspDynamicProgrammingIterative(dist));
  }

  @Test
  public void testTspRecursiveSmallGraph() {
    double[][] dist = {
      {0, 1},
      {1, 0}
    };
    assertThrows(IllegalStateException.class, () -> new TspDynamicProgrammingRecursive(dist));
  }

  @Test
  public void testTspIterativeSmallGraph() {
    double[][] dist = {
      {0, 1},
      {1, 0}
    };
    assertThrows(IllegalStateException.class, () -> new TspDynamicProgrammingIterative(dist));
  }

  @Test
  public void testTsp_small1() {
    int n = 5;
    double[][] dist = new double[n][n];
    for (double[] row : dist) java.util.Arrays.fill(row, 100);

    // Assume matrix is symmetric for simplicity.
    dist[1][3] = dist[3][1] = 1;
    dist[3][0] = dist[0][3] = 2;
    dist[0][2] = dist[2][0] = 3;
    dist[2][4] = dist[4][2] = 4;
    dist[4][1] = dist[1][4] = 5;

    double expected = 1 + 2 + 3 + 4 + 5;
    double tspRecursiveTourCost = new TspDynamicProgrammingRecursive(dist).getTourCost();
    double tspIterativeTourCost = new TspDynamicProgrammingIterative(dist).getTourCost();

    assertThat(tspRecursiveTourCost).isWithin(EPS).of(expected);
    assertThat(tspIterativeTourCost).isWithin(EPS).of(expected);
  }

  @Test
  public void testDpVsBf() {
    for (int n = 3; n <= 9; n++) {
      for (int i = 0; i < 10; i++) {

        double[][] dist = new double[n][n];
        randomFillDistMatrix(dist);

        TspDynamicProgrammingRecursive dpRecursiveSolver = new TspDynamicProgrammingRecursive(dist);
        TspDynamicProgrammingIterative dpIterativeSolver = new TspDynamicProgrammingIterative(dist);

        double dp1 = dpRecursiveSolver.getTourCost();
        double dp2 = dpIterativeSolver.getTourCost();
        double bf = TspBruteForce.computeTourCost(TspBruteForce.tsp(dist), dist);

        assertThat(dp1).isWithin(EPS).of(bf);
        assertThat(dp2).isWithin(EPS).of(bf);
      }
    }
  }

  @Test
  public void testGeneratedTour() {
    for (int n = 3; n <= 9; n++) {
      for (int i = 0; i < 10; i++) {

        double[][] dist = new double[n][n];
        randomFillDistMatrix(dist);

        TspDynamicProgrammingRecursive dpRecursiveSolver = new TspDynamicProgrammingRecursive(dist);
        TspDynamicProgrammingIterative dpIterativeSolver = new TspDynamicProgrammingIterative(dist);
        int[] bfPath = TspBruteForce.tsp(dist);

        double dp1 = dpRecursiveSolver.getTourCost();
        double dp2 = dpIterativeSolver.getTourCost();
        double bf = TspBruteForce.computeTourCost(bfPath, dist);

        assertThat(dp1).isWithin(EPS).of(bf);
        assertThat(dp2).isWithin(EPS).of(bf);

        assertThat(getTourCost(dist, dpRecursiveSolver.getTour())).isWithin(EPS).of(bf);
        assertThat(getTourCost(dist, dpIterativeSolver.getTour())).isWithin(EPS).of(bf);
      }
    }
  }

  @Test
  public void testDifferentStartingNodes() {
    for (int n = 3; n <= 9; n++) {

      double[][] dist = new double[n][n];
      randomFillDistMatrix(dist);
      int[] bfPath = TspBruteForce.tsp(dist);
      double bf = TspBruteForce.computeTourCost(bfPath, dist);

      for (int startNode = 0; startNode < n; startNode++) {
        TspDynamicProgrammingRecursive dpRecursiveSolver =
            new TspDynamicProgrammingRecursive(startNode, dist);
        TspDynamicProgrammingIterative dpIterativeSolver =
            new TspDynamicProgrammingIterative(startNode, dist);

        double dp1 = dpRecursiveSolver.getTourCost();
        double dp2 = dpIterativeSolver.getTourCost();

        assertThat(dp1).isWithin(EPS).of(bf);
        assertThat(dp2).isWithin(EPS).of(bf);

        assertThat(getTourCost(dist, dpRecursiveSolver.getTour())).isWithin(EPS).of(bf);
        assertThat(getTourCost(dist, dpIterativeSolver.getTour())).isWithin(EPS).of(bf);
      }
    }
  }

  // Try slightly larger matrices to make sure they run is a reasonable amount of time.
  @Test
  public void testTspRecursivePerformance() {
    for (int n = 3; n <= 16; n++) {
      double[][] dist = new double[n][n];
      randomFillDistMatrix(dist);
      TspDynamicProgrammingRecursive solver = new TspDynamicProgrammingRecursive(dist);
      solver.solve();
    }
  }

  // Try slightly larger matrices to make sure they run is a reasonable amount of time.
  @Test
  public void testTspIterativePerformance() {
    for (int n = 3; n <= 16; n++) {
      double[][] dist = new double[n][n];
      randomFillDistMatrix(dist);
      TspDynamicProgrammingIterative solver = new TspDynamicProgrammingIterative(dist);
      solver.solve();
    }
  }

  public void randomFillDistMatrix(double[][] dist) {
    for (int i = 0; i < dist.length; i++) {
      for (int j = 0; j < dist.length; j++) {
        if (i == j) continue;

        // Add a random edge value (possibly negative)
        double val = (int) (Math.random() * 1000);
        if (Math.random() < 0.8) dist[i][j] = val;
        else dist[i][j] = -val;
      }
    }
  }

  private double getTourCost(double[][] dist, List<Integer> tour) {
    double total = 0;
    for (int i = 1; i < tour.size(); i++) total += dist[tour.get(i - 1)][tour.get(i)];
    return total;
  }
}
