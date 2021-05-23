package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.*;

public class MinimumWeightPerfectMatchingTest {

  static final int LOOPS = 50;

  static class BruteForceMwpm {
    private int n;
    private double[][] matrix;
    private double minWeightMatching = Double.POSITIVE_INFINITY;

    public BruteForceMwpm(double[][] matrix) {
      this.matrix = matrix;
      this.n = matrix.length;
    }

    public double getMinWeightCost() {
      solve();
      return minWeightMatching;
    }

    public double computeMatchingCost(int[] p) {
      double t = 0;
      for (int i = 0; i < n / 2; i++) {
        int ii = p[2 * i];
        int jj = p[2 * i + 1];
        t += matrix[ii][jj];
      }
      return t;
    }

    public void solve() {
      int[] permutation = new int[n];
      for (int i = 0; i < n; i++) permutation[i] = i;

      // Try all matchings
      do {
        double matchingCost = computeMatchingCost(permutation);
        if (matchingCost < minWeightMatching) {
          minWeightMatching = matchingCost;
        }
      } while (nextPermutation(permutation));
    }

    // Generates the next ordered permutation in-place (skips repeated permutations).
    // Calling this when the array is already at the highest permutation returns false.
    // Recommended usage is to start with the smallest permutations and use a do while
    // loop to generate each successive permutations (see main for example).
    public static boolean nextPermutation(int[] sequence) {
      int first = getFirst(sequence);
      if (first == -1) return false;
      int toSwap = sequence.length - 1;
      while (sequence[first] >= sequence[toSwap]) --toSwap;
      swap(sequence, first++, toSwap);
      toSwap = sequence.length - 1;
      while (first < toSwap) swap(sequence, first++, toSwap--);
      return true;
    }

    private static int getFirst(int[] sequence) {
      for (int i = sequence.length - 2; i >= 0; --i) if (sequence[i] < sequence[i + 1]) return i;
      return -1;
    }

    private static void swap(int[] sequence, int i, int j) {
      int tmp = sequence[i];
      sequence[i] = sequence[j];
      sequence[j] = tmp;
    }
  }

  @Test
  public void testSmallestMatrix1() {
    // nodes 0 & 1 make the mwpm
    double[][] costMatrix = {
      {0, 1},
      {1, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(1.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    int[] expectedMatching = {0, 1};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testSmallMatrix1() {
    // nodes 0 & 2 and 1 & 3 make the mwpm
    double[][] costMatrix = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(2.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    int[] expectedMatching = {0, 2, 1, 3};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testSmallMatrix2() {
    // nodes 0 & 1 and 2 & 3 make the mwpm
    double[][] costMatrix = {
      {0, 1, 2, 2},
      {1, 0, 2, 2},
      {2, 2, 0, 1},
      {2, 2, 1, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(2.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    int[] expectedMatching = {0, 1, 2, 3};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testMediumMatrix1() {
    // mwpm between 0 & 5, 1 & 2, 3 & 4
    double[][] costMatrix = {
      {0, 9, 9, 9, 9, 1},
      {9, 0, 1, 9, 9, 9},
      {9, 1, 0, 9, 9, 9},
      {9, 9, 9, 0, 1, 9},
      {9, 9, 9, 1, 0, 9},
      {1, 9, 9, 9, 9, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(3.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    int[] expectedMatching = {0, 5, 1, 2, 3, 4};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testMediumMatrix2() {
    // mwpm between 0 & 1, 2 & 4, 3 & 5
    double[][] costMatrix = {
      {0, 1, 9, 9, 9, 9},
      {1, 0, 9, 9, 9, 9},
      {9, 9, 0, 9, 1, 9},
      {9, 9, 9, 0, 9, 1},
      {9, 9, 1, 9, 0, 9},
      {9, 9, 9, 1, 9, 0},
    };

    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(3.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    int[] expectedMatching = {0, 1, 2, 4, 3, 5};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testMatchingOutputsUniqueNodes() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix, 100);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      int[] matching = mwpm.getMinWeightCostMatching();
      Set<Integer> set = new HashSet<>();
      for (int i = 0; i < matching.length; i++) {
        set.add(matching[i]);
      }

      assertThat(set.size()).isEqualTo(matching.length);
    }
  }

  @Test
  public void testMatchingAndCostAreConsistent() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix, 100);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      int[] matching = mwpm.getMinWeightCostMatching();
      double totalMinCost = 0;
      for (int i = 0; i < matching.length / 2; i++) {
        int ii = matching[2 * i];
        int jj = matching[2 * i + 1];
        totalMinCost += costMatrix[ii][jj];
      }
      assertThat(totalMinCost).isEqualTo(mwpm.getMinWeightCost());
    }
  }

  @Test
  public void testAgainstBruteForce_largeValues() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 6)) * 2; // n is either 2,4,6,8, or 10
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix, /*maxValue=*/ 10000);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      BruteForceMwpm bfMwpm = new BruteForceMwpm(costMatrix);
      double dpSoln = mwpm.getMinWeightCost();
      double bfSoln = bfMwpm.getMinWeightCost();
      assertThat(dpSoln).isEqualTo(bfSoln);
    }
  }

  @Test
  public void testAgainstBruteForce_smallValues() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 6)) * 2; // n is either 2,4,6,8, or 10
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix, /*maxValue=*/ 3);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      BruteForceMwpm bfMwpm = new BruteForceMwpm(costMatrix);
      double dpSoln = mwpm.getMinWeightCost();
      double bfSoln = bfMwpm.getMinWeightCost();

      assertThat(dpSoln).isEqualTo(bfSoln);
    }
  }

  public void randomFillSymmetricMatrix(double[][] dist, int maxValue) {
    for (int i = 0; i < dist.length; i++) {
      for (int j = i + 1; j < dist.length; j++) {
        double val = (int) (Math.random() * maxValue);
        dist[i][j] = dist[j][i] = val;
      }
    }
  }
}
