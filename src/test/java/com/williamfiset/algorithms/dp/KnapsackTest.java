package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;

public class KnapsackTest {

  // ==================== 0/1 Knapsack ====================

  @Test
  public void testKnapsack01_nullWeights() {
    assertThrows(
        IllegalArgumentException.class, () -> Knapsack_01.knapsack(10, null, new int[] {1}));
  }

  @Test
  public void testKnapsack01_nullValues() {
    assertThrows(
        IllegalArgumentException.class, () -> Knapsack_01.knapsack(10, new int[] {1}, null));
  }

  @Test
  public void testKnapsack01_mismatchedArrays() {
    assertThrows(
        IllegalArgumentException.class,
        () -> Knapsack_01.knapsack(10, new int[] {1, 2}, new int[] {1}));
  }

  @Test
  public void testKnapsack01_zeroCapacity() {
    assertThat(Knapsack_01.knapsack(0, new int[] {1, 2}, new int[] {10, 20})).isEqualTo(0);
  }

  @Test
  public void testKnapsack01_noItems() {
    assertThat(Knapsack_01.knapsack(10, new int[] {}, new int[] {})).isEqualTo(0);
  }

  @Test
  public void testKnapsack01_singleItemFits() {
    assertThat(Knapsack_01.knapsack(5, new int[] {3}, new int[] {10})).isEqualTo(10);
  }

  @Test
  public void testKnapsack01_singleItemTooHeavy() {
    assertThat(Knapsack_01.knapsack(2, new int[] {3}, new int[] {10})).isEqualTo(0);
  }

  @Test
  public void testKnapsack01_example1() {
    // capacity=10, items: (w=3,v=1), (w=3,v=4), (w=5,v=8), (w=6,v=5)
    // Optimal: items 1 and 2 (w=3+5=8, v=4+8=12)
    int[] W = {3, 3, 5, 6};
    int[] V = {1, 4, 8, 5};
    assertThat(Knapsack_01.knapsack(10, W, V)).isEqualTo(12);
  }

  @Test
  public void testKnapsack01_example2() {
    // capacity=7, items: (w=3,v=2), (w=1,v=2), (w=3,v=4), (w=4,v=5), (w=2,v=3)
    // Optimal: items 1,3 (w=1+4=5, v=2+5=7) or items 1,2,4 (w=1+3+2=6, v=2+4+3=9)
    int[] W = {3, 1, 3, 4, 2};
    int[] V = {2, 2, 4, 5, 3};
    assertThat(Knapsack_01.knapsack(7, W, V)).isEqualTo(10);
  }

  /** Verify that selected items match the reported optimal value. */
  @Test
  public void testKnapsack01_itemsConsistentWithValue() {
    int[] W = {3, 3, 5, 6};
    int[] V = {1, 4, 8, 5};
    int capacity = 10;

    int maxValue = Knapsack_01.knapsack(capacity, W, V);
    List<Integer> items = Knapsack_01.knapsackItems(capacity, W, V);

    int totalWeight = 0, totalValue = 0;
    for (int idx : items) {
      totalWeight += W[idx];
      totalValue += V[idx];
    }

    assertThat(totalValue).isEqualTo(maxValue);
    assertThat(totalWeight).isAtMost(capacity);
  }

  /** Each item should appear at most once in the solution. */
  @Test
  public void testKnapsack01_noDuplicateItems() {
    int[] W = {2, 3, 4, 5};
    int[] V = {3, 4, 5, 6};
    List<Integer> items = Knapsack_01.knapsackItems(10, W, V);
    assertThat(items).containsNoDuplicates();
  }

  // ==================== Unbounded Knapsack ====================

  @Test
  public void testUnbounded_nullWeights() {
    assertThrows(
        IllegalArgumentException.class,
        () -> KnapsackUnbounded.unboundedKnapsack(10, null, new int[] {1}));
  }

  @Test
  public void testUnbounded_zeroCapacity() {
    assertThat(KnapsackUnbounded.unboundedKnapsack(0, new int[] {1}, new int[] {10})).isEqualTo(0);
  }

  @Test
  public void testUnbounded_noItems() {
    assertThat(KnapsackUnbounded.unboundedKnapsack(10, new int[] {}, new int[] {})).isEqualTo(0);
  }

  @Test
  public void testUnbounded_singleItemReused() {
    // Item (w=3, v=5) can be used 3 times in capacity 10 → value 15
    assertThat(KnapsackUnbounded.unboundedKnapsack(10, new int[] {3}, new int[] {5})).isEqualTo(15);
  }

  @Test
  public void testUnbounded_example1() {
    // Items: (w=3,v=5), (w=6,v=20), (w=2,v=3)
    // Capacity 10: best is one item of w=6,v=20 + one of w=3,v=5 = 25? No...
    // Actually w=6 + w=3 = 9, leaving 1 unused. v=25
    // Or two w=6 = 12 > 10, doesn't fit.
    // w=6 + w=2 + w=2 = 10, v=20+3+3=26? No, w=6+2+2=10, v=26
    // Actually let's just check: best for cap=10
    int[] W = {3, 6, 2};
    int[] V = {5, 20, 3};
    int result = KnapsackUnbounded.unboundedKnapsack(10, W, V);
    assertThat(result).isEqualTo(KnapsackUnbounded.unboundedKnapsackSpaceEfficient(10, W, V));
  }

  /** Both implementations should always agree. */
  @Test
  public void testUnbounded_bothImplementationsAgree() {
    int[][] cases = {
      {10, 3, 6, 2, 5, 20, 3},  // cap=10, W={3,6,2}, V={5,20,3}
      {12, 3, 6, 2, 5, 20, 3},  // cap=12
      {7, 1, 3, 4, 1, 4, 5},    // cap=7, W={1,3,4}, V={1,4,5}
      {15, 5, 10, 3, 10, 30, 5}, // cap=15
    };
    for (int[] c : cases) {
      int cap = c[0];
      int n = (c.length - 1) / 2;
      int[] W = new int[n], V = new int[n];
      for (int i = 0; i < n; i++) {
        W[i] = c[1 + i];
        V[i] = c[1 + n + i];
      }
      assertThat(KnapsackUnbounded.unboundedKnapsack(cap, W, V))
          .isEqualTo(KnapsackUnbounded.unboundedKnapsackSpaceEfficient(cap, W, V));
    }
  }

  /** Unbounded should be >= 0/1 since it has more freedom (reuse allowed). */
  @Test
  public void testUnbounded_atLeastAsMuchAs01() {
    int[] W = {2, 3, 5};
    int[] V = {3, 4, 8};
    int capacity = 10;
    int bounded = Knapsack_01.knapsack(capacity, W, V);
    int unbounded = KnapsackUnbounded.unboundedKnapsack(capacity, W, V);
    assertThat(unbounded).isAtLeast(bounded);
  }

  @Test
  public void testUnbounded_exactFit() {
    // Capacity exactly fits 2 copies of the item
    assertThat(KnapsackUnbounded.unboundedKnapsack(6, new int[] {3}, new int[] {7})).isEqualTo(14);
  }
}
