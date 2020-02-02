/**
 * Here I show how you can generate all the combinations of a sequence of size r which are repeated
 * at most k times.
 *
 * <p>Time Complexity: O(n+r-1 choose r) = O((n+r-1)!/(r!(n-1)!))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.other;

public class CombinationsWithRepetition {

  /**
   * Computes all combinations of elements of 'r' elements which can be repeated at most 'k' times
   * each.
   *
   * @param sequence - The sequence containing all the elements we wish to take combinations from
   * @param usedCount - Tracks how many of each element we currently have selected
   * @param at - The current position we're at in the sequence
   * @param r - The number of elements we're choosing
   * @param k - The maximum number of times each element is allowed to be picked
   */
  private static void combinationsWithRepetition(
      int[] sequence, int[] usedCount, int at, int r, int k) {

    final int N = sequence.length;

    // We reached the end
    if (at == N) {

      // We selected 'r' elements in total
      if (r == 0) {

        // Print combination
        System.out.print("{ ");
        for (int i = 0; i < N; i++)
          for (int j = 0; j < usedCount[i]; j++) System.out.print(sequence[i] + " ");
        System.out.println("}");
      }

    } else {

      // For this particular time at position 'at' try including it each of [0, k] times
      for (int itemCount = 0; itemCount <= k; itemCount++) {

        // Try including this element itemCount number of times (this is possibly more than once)
        usedCount[at] = itemCount;

        combinationsWithRepetition(sequence, usedCount, at + 1, r - itemCount, k);
      }
    }
  }

  // Given a sequence this method prints all the combinations of size
  // 'r' in a given sequence which has each element repeated at most 'k' times
  public static void printCombinationsWithRepetition(int[] sequence, int r, int k) {

    if (sequence == null) return;
    final int n = sequence.length;
    if (r > n) throw new IllegalArgumentException("r must be <= n");
    if (k > r) throw new IllegalArgumentException("k must be <= r");

    int[] usedCount = new int[sequence.length];
    combinationsWithRepetition(sequence, usedCount, 0, r, k);
  }

  public static void main(String[] args) {

    // Prints all combinations of size 3 where
    // each element is repeated at most twice
    int[] seq = {1, 2, 3, 4};
    printCombinationsWithRepetition(seq, 3, 2);
    // prints:
    // { 3 4 4 }
    // { 3 3 4 }
    // { 2 4 4 }
    // { 2 3 4 }
    // { 2 3 3 }
    // { 2 2 4 }
    // { 2 2 3 }
    // { 1 4 4 }
    // { 1 3 4 }
    // { 1 3 3 }
    // { 1 2 4 }
    // { 1 2 3 }
    // { 1 2 2 }
    // { 1 1 4 }
    // { 1 1 3 }
    // { 1 1 2 }

  }
}
