package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MinimumWeightPerfectMatchingTest {

  @Test
  public void testNullInput() {
    assertThrows(IllegalArgumentException.class, () -> new MinimumWeightPerfectMatching(null));
  }

  @Test
  public void testEmptyMatrix() {
    assertThrows(
        IllegalArgumentException.class, () -> new MinimumWeightPerfectMatching(new double[0][0]));
  }

  @Test
  public void testOddSizeMatrix() {
    double[][] cost = new double[3][3];
    assertThrows(IllegalArgumentException.class, () -> new MinimumWeightPerfectMatching(cost));
  }

  /** Two nodes — only one possible matching: (0, 1). */
  @Test
  public void testTwoNodes() {
    double[][] cost = {
      {0, 5},
      {5, 0}
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    assertThat(mwpm.getMinWeightCost()).isEqualTo(5.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    assertThat(matching).isEqualTo(new int[] {0, 1});
  }

  /** Four nodes where the optimal pairing is (0,2) and (1,3) with cost 1+1=2. */
  @Test
  public void testFourNodes_recursiveSolver() {
    double[][] cost = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    assertThat(mwpm.getMinWeightCost()).isEqualTo(2.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    assertThat(matching).isEqualTo(new int[] {0, 2, 1, 3});
  }

  /** Same four-node case but using the iterative solver. */
  @Test
  public void testFourNodes_iterativeSolver() {
    double[][] cost = {
      {0, 2, 1, 2},
      {2, 0, 2, 1},
      {1, 2, 0, 2},
      {2, 1, 2, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    mwpm.solveIterative();
    assertThat(mwpm.getMinWeightCost()).isEqualTo(2.0);
  }

  /** Six nodes with three clear cheapest pairs: (0,5), (1,2), (3,4). */
  @Test
  public void testSixNodes() {
    double[][] cost = {
      {0, 9, 9, 9, 9, 1},
      {9, 0, 1, 9, 9, 9},
      {9, 1, 0, 9, 9, 9},
      {9, 9, 9, 0, 1, 9},
      {9, 9, 9, 1, 0, 9},
      {1, 9, 9, 9, 9, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    assertThat(mwpm.getMinWeightCost()).isEqualTo(3.0);

    int[] matching = mwpm.getMinWeightCostMatching();
    assertThat(matching).isEqualTo(new int[] {0, 5, 1, 2, 3, 4});
  }

  /** All pairs have equal cost — any matching gives the same total. */
  @Test
  public void testUniformCost() {
    double[][] cost = {
      {0, 3, 3, 3},
      {3, 0, 3, 3},
      {3, 3, 0, 3},
      {3, 3, 3, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    assertThat(mwpm.getMinWeightCost()).isEqualTo(6.0);
  }

  /** Matching output contains each node exactly once. */
  @Test
  public void testMatchingContainsAllNodes() {
    double[][] cost = {
      {0, 1, 5, 5},
      {1, 0, 5, 5},
      {5, 5, 0, 2},
      {5, 5, 2, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    int[] matching = mwpm.getMinWeightCostMatching();

    boolean[] seen = new boolean[4];
    for (int node : matching)
      seen[node] = true;
    for (int i = 0; i < 4; i++)
      assertThat(seen[i]).isTrue();
  }

  /** Pairs in matching are sorted: left nodes ascending, and a < b in each pair (a,b). */
  @Test
  public void testMatchingIsSorted() {
    double[][] cost = {
      {0, 9, 9, 1, 9, 9},
      {9, 0, 9, 9, 9, 1},
      {9, 9, 0, 9, 1, 9},
      {1, 9, 9, 0, 9, 9},
      {9, 9, 1, 9, 0, 9},
      {9, 1, 9, 9, 9, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    int[] matching = mwpm.getMinWeightCostMatching();

    // Each pair (a,b) has a < b
    for (int i = 0; i < matching.length; i += 2)
      assertThat(matching[i]).isLessThan(matching[i + 1]);

    // Left nodes are in ascending order
    for (int i = 2; i < matching.length; i += 2)
      assertThat(matching[i]).isGreaterThan(matching[i - 2]);
  }

  /** Verify cost equals sum of matched pair costs. */
  @Test
  public void testCostMatchesMatchingSum() {
    double[][] cost = {
      {0, 3, 7, 2},
      {3, 0, 1, 8},
      {7, 1, 0, 4},
      {2, 8, 4, 0},
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    double minCost = mwpm.getMinWeightCost();
    int[] matching = mwpm.getMinWeightCostMatching();

    double sum = 0;
    for (int i = 0; i < matching.length; i += 2)
      sum += cost[matching[i]][matching[i + 1]];
    assertThat(minCost).isEqualTo(sum);
  }

  /** Solving twice returns the same result (tests caching via solved flag). */
  @Test
  public void testIdempotent() {
    double[][] cost = {
      {0, 4},
      {4, 0}
    };
    MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(cost);
    double first = mwpm.getMinWeightCost();
    double second = mwpm.getMinWeightCost();
    assertThat(first).isEqualTo(second);
  }
}
