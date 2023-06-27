package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.jupiter.api.*;

public class WeightedMaximumCardinalityMatchingTest {

  static final int LOOPS = 50;
  static final double INF = 987654321.0;

  static class BruteForceMwpm {
    private int n;
    private Double[][] matrix;
    private double minWeightMatching = Double.POSITIVE_INFINITY;

    public BruteForceMwpm(Double[][] matrix) {
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

  private static MwpmInterface[] getImplementations(Double[][] costMatrix) {
    return new MwpmInterface[] {new WeightedMaximumCardinalityMatchingRecursive(costMatrix)
      // new WeightedMaximumCardinalityMatchingIterative(costMatrix)
    };
  }

  private static Double[][] createEmptyMatrix(int n) {
    Double[][] costMatrix = new Double[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (i == j) continue;
        costMatrix[i][j] = null;
      }
    }
    return costMatrix;
  }

  private static void addUndirectedWeightedEdge(Double[][] g, int from, int to, double weight) {
    g[from][to] = weight;
    g[to][from] = weight;
  }

  @Test
  public void testSmallGraph_oddSize() {
    int n = 5;
    Double[][] g = createEmptyMatrix(n);
    // 0, 2; 3, 4
    addUndirectedWeightedEdge(g, 0, 1, 8);
    addUndirectedWeightedEdge(g, 0, 2, 1);
    addUndirectedWeightedEdge(g, 1, 3, 8);
    addUndirectedWeightedEdge(g, 2, 3, 8);
    addUndirectedWeightedEdge(g, 2, 4, 8);
    addUndirectedWeightedEdge(g, 3, 4, 2);

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(3.0);

    int[] matching = mwpm.getMatching();
    int[] expectedMatching = {0, 2, 3, 4};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testSmallestMatrix1() {
    // nodes 0 & 1 make the mwpm
    Double[][] costMatrix = {
      {0.0, 1.0},
      {1.0, 0.0},
    };
    MwpmInterface[] impls = getImplementations(costMatrix);
    for (MwpmInterface mwpm : impls) {
      double cost = mwpm.getMinWeightCost();
      assertThat(cost).isEqualTo(1.0);

      int[] matching = mwpm.getMatching();
      int[] expectedMatching = {0, 1};
      assertThat(matching).isEqualTo(expectedMatching);
    }
  }

  @Test
  public void testSmallMatrix1() {
    // nodes 0 & 2 and 1 & 3 make the mwpm
    Double[][] costMatrix = {
      {0.0, 2.0, 1.0, 2.0},
      {2.0, 0.0, 2.0, 1.0},
      {1.0, 2.0, 0.0, 2.0},
      {2.0, 1.0, 2.0, 0.0},
    };

    MwpmInterface[] impls = getImplementations(costMatrix);
    for (MwpmInterface mwpm : impls) {
      double cost = mwpm.getMinWeightCost();
      assertThat(cost).isEqualTo(2.0);

      int[] matching = mwpm.getMatching();
      int[] expectedMatching = {0, 2, 1, 3};
      assertThat(matching).isEqualTo(expectedMatching);
    }
  }

  @Test
  public void testSmallMatrix2() {
    // nodes 0 & 1 and 2 & 3 make the mwpm
    Double[][] costMatrix = {
      {0.0, 1.0, 2.0, 2.0},
      {1.0, 0.0, 2.0, 2.0},
      {2.0, 2.0, 0.0, 1.0},
      {2.0, 2.0, 1.0, 0.0},
    };

    MwpmInterface[] impls = getImplementations(costMatrix);
    for (MwpmInterface mwpm : impls) {
      double cost = mwpm.getMinWeightCost();
      assertThat(cost).isEqualTo(2.0);

      int[] matching = mwpm.getMatching();
      int[] expectedMatching = {0, 1, 2, 3};
      assertThat(matching).isEqualTo(expectedMatching);
    }
  }

  @Test
  public void testMediumMatrix1() {
    // mwpm between 0 & 5, 1 & 2, 3 & 4
    Double[][] costMatrix = {
      {0.0, 9.0, 9.0, 9.0, 9.0, 1.0},
      {9.0, 0.0, 1.0, 9.0, 9.0, 9.0},
      {9.0, 1.0, 0.0, 9.0, 9.0, 9.0},
      {9.0, 9.0, 9.0, 0.0, 1.0, 9.0},
      {9.0, 9.0, 9.0, 1.0, 0.0, 9.0},
      {1.0, 9.0, 9.0, 9.0, 9.0, 0.0},
    };

    MwpmInterface[] impls = getImplementations(costMatrix);
    for (MwpmInterface mwpm : impls) {
      double cost = mwpm.getMinWeightCost();
      assertThat(cost).isEqualTo(3.0);

      int[] matching = mwpm.getMatching();
      int[] expectedMatching = {0, 5, 1, 2, 3, 4};
      assertThat(matching).isEqualTo(expectedMatching);
    }
  }

  @Test
  public void testMediumMatrix2() {
    // mwpm between 0 & 1, 2 & 4, 3 & 5
    Double[][] costMatrix = {
      {0.0, 1.0, 9.0, 9.0, 9.0, 9.0},
      {1.0, 0.0, 9.0, 9.0, 9.0, 9.0},
      {9.0, 9.0, 0.0, 9.0, 1.0, 9.0},
      {9.0, 9.0, 9.0, 0.0, 9.0, 1.0},
      {9.0, 9.0, 1.0, 9.0, 0.0, 9.0},
      {9.0, 9.0, 9.0, 1.0, 9.0, 0.0},
    };

    MwpmInterface[] impls = getImplementations(costMatrix);
    for (MwpmInterface mwpm : impls) {
      double cost = mwpm.getMinWeightCost();
      assertThat(cost).isEqualTo(3.0);

      int[] matching = mwpm.getMatching();
      int[] expectedMatching = {0, 1, 2, 4, 3, 5};
      assertThat(matching).isEqualTo(expectedMatching);
    }
  }

  @Test
  public void testMediumGraph_evenSize_fromSlides() {
    int n = 6;
    Double[][] g = createEmptyMatrix(n);

    addUndirectedWeightedEdge(g, 0, 1, 7);
    addUndirectedWeightedEdge(g, 0, 2, 6);
    addUndirectedWeightedEdge(g, 0, 4, -1);
    addUndirectedWeightedEdge(g, 1, 3, 1);
    addUndirectedWeightedEdge(g, 1, 4, 3);
    addUndirectedWeightedEdge(g, 1, 5, 5);
    addUndirectedWeightedEdge(g, 2, 4, 5);
    addUndirectedWeightedEdge(g, 3, 5, 3);
    addUndirectedWeightedEdge(g, 4, 5, 8);

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(12);

    int[] matching = mwpm.getMatching();

    int[] expectedMatching = {0, 2, 1, 4, 3, 5};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testMediumGraph_evenSize_nonPerfectMatchingFromSlides() {
    int n = 6;
    Double[][] g = createEmptyMatrix(n);

    addUndirectedWeightedEdge(g, 0, 1, 6);
    addUndirectedWeightedEdge(g, 1, 2, 7);
    addUndirectedWeightedEdge(g, 1, 5, 8);
    addUndirectedWeightedEdge(g, 1, 4, 9);
    addUndirectedWeightedEdge(g, 1, 3, 10);
    addUndirectedWeightedEdge(g, 3, 4, 11);

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(17);

    int[] matching = mwpm.getMatching();

    int[] expectedMatching = {0, 1, 3, 4};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testNegativeEdgeWeights() {
    int n = 6;
    Double[][] g = createEmptyMatrix(n);
    addUndirectedWeightedEdge(g, 0, 1, -1); // selected
    addUndirectedWeightedEdge(g, 1, 2, -2);
    addUndirectedWeightedEdge(g, 2, 3, -3); // selected
    addUndirectedWeightedEdge(g, 3, 4, -4);
    addUndirectedWeightedEdge(g, 4, 5, -5); // selected

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(-1 + -3 + -5);

    int[] matching = mwpm.getMatching();
    int[] expectedMatching = {0, 1, 2, 3, 4, 5};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testNegativeEdge_smallerThanINFWeights() {
    int n = 6;
    Double[][] g = createEmptyMatrix(n);
    addUndirectedWeightedEdge(g, 0, 1, -1 - 50 * INF); // selected
    addUndirectedWeightedEdge(g, 1, 2, -2 - 50 * INF);
    addUndirectedWeightedEdge(g, 2, 3, -3 - 50 * INF); // selected
    addUndirectedWeightedEdge(g, 3, 4, -4 - 50 * INF);
    addUndirectedWeightedEdge(g, 4, 5, -5 - 50 * INF); // selected

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    double expectedCost = -1.0 + -3.0 + -5.0 + (-3 * 50 * INF);
    assertThat(cost).isEqualTo(expectedCost);

    int[] matching = mwpm.getMatching();
    int[] expectedMatching = {0, 1, 2, 3, 4, 5};
    assertThat(matching).isEqualTo(expectedMatching);
    assertOptimalMatching(matching, g, expectedCost);
  }

  @Test
  public void testDisjointGraph() {
    int n = 8;
    Double[][] g = createEmptyMatrix(n);
    addUndirectedWeightedEdge(g, 0, 1, 3);
    addUndirectedWeightedEdge(g, 0, 2, 5);
    addUndirectedWeightedEdge(g, 1, 2, 1);
    addUndirectedWeightedEdge(g, 1, 3, 4);
    addUndirectedWeightedEdge(g, 2, 3, 2);

    addUndirectedWeightedEdge(g, 4, 5, 3);
    addUndirectedWeightedEdge(g, 4, 6, 5);
    addUndirectedWeightedEdge(g, 5, 6, 1);
    addUndirectedWeightedEdge(g, 5, 7, 4);
    addUndirectedWeightedEdge(g, 6, 7, 2);

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(10);

    int[] matching = mwpm.getMatching();
    assertOptimalMatching(matching, g, 10);
  }

  @Test
  public void testHarderWmcm_fromSlides() {
    int n = 11;
    Double[][] g = createEmptyMatrix(n);
    addUndirectedWeightedEdge(g, 0, 1, 1);
    addUndirectedWeightedEdge(g, 0, 3, 8);
    addUndirectedWeightedEdge(g, 0, 4, 9);
    addUndirectedWeightedEdge(g, 0, 5, 7); // selected
    addUndirectedWeightedEdge(g, 1, 2, 1);
    addUndirectedWeightedEdge(g, 1, 6, 7); // selected
    addUndirectedWeightedEdge(g, 1, 7, 8);
    addUndirectedWeightedEdge(g, 1, 8, 9);
    addUndirectedWeightedEdge(g, 2, 9, 9);
    addUndirectedWeightedEdge(g, 2, 10, 7); // selected

    MwpmInterface mwpm = new WeightedMaximumCardinalityMatchingRecursive(g);
    double cost = mwpm.getMinWeightCost();
    assertThat(cost).isEqualTo(7 + 7 + 7);

    int[] matching = mwpm.getMatching();
    int[] expectedMatching = {0, 5, 1, 6, 2, 10};
    assertThat(matching).isEqualTo(expectedMatching);
  }

  @Test
  public void testMatchingOutputsUniqueNodes() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
      Double[][] costMatrix = new Double[n][n];
      randomFillSymmetricMatrix(costMatrix, 100);

      MwpmInterface[] impls = getImplementations(costMatrix);
      for (MwpmInterface mwpm : impls) {
        int[] matching = mwpm.getMatching();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < matching.length; i++) {
          set.add(matching[i]);
        }

        assertThat(set.size()).isEqualTo(matching.length);
      }
    }
  }

  @Test
  public void testMatchingAndCostAreConsistent() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
      Double[][] costMatrix = new Double[n][n];
      randomFillSymmetricMatrix(costMatrix, 100);

      MwpmInterface[] impls = getImplementations(costMatrix);
      for (MwpmInterface mwpm : impls) {
        assertOptimalMatching(mwpm.getMatching(), costMatrix, mwpm.getMinWeightCost());
      }
    }
  }

  @Test
  public void testAgainstBruteForce_largeValues() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 6)) * 2; // n is either 2,4,6,8, or 10
      Double[][] costMatrix = new Double[n][n];
      randomFillSymmetricMatrix(costMatrix, /* maxValue= */ 10000);

      MwpmInterface[] impls = getImplementations(costMatrix);
      for (MwpmInterface mwpm : impls) {
        int[] matching = mwpm.getMatching();
        BruteForceMwpm bfMwpm = new BruteForceMwpm(costMatrix);
        double dpSoln = mwpm.getMinWeightCost();
        double bfSoln = bfMwpm.getMinWeightCost();
        assertThat(dpSoln).isEqualTo(bfSoln);
      }
    }
  }

  @Test
  public void testAgainstBruteForce_smallValues() {
    for (int loop = 0; loop < LOOPS; loop++) {
      int n = Math.max(1, (int) (Math.random() * 6)) * 2; // n is either 2,4,6,8, or 10
      Double[][] costMatrix = new Double[n][n];
      randomFillSymmetricMatrix(costMatrix, /* maxValue= */ 3);

      MwpmInterface[] impls = getImplementations(costMatrix);
      for (MwpmInterface mwpm : impls) {

        BruteForceMwpm bfMwpm = new BruteForceMwpm(costMatrix);
        double dpSoln = mwpm.getMinWeightCost();
        double bfSoln = bfMwpm.getMinWeightCost();

        assertThat(dpSoln).isEqualTo(bfSoln);
      }
    }
  }

  public void randomFillSymmetricMatrix(Double[][] dist, int maxValue) {
    for (int i = 0; i < dist.length; i++) {
      for (int j = i + 1; j < dist.length; j++) {
        double val = (int) (Math.random() * maxValue);
        dist[i][j] = dist[j][i] = val;
      }
    }
  }

  private void assertOptimalMatching(
      int[] matching, Double[][] costMatrix, double expectedMatchingCost) {
    double total = 0;
    for (int i = 0; i < matching.length; i += 2) {
      total += costMatrix[matching[i]][matching[i + 1]];
    }
    assertThat(total).isEqualTo(expectedMatchingCost);
  }
}
