package com.williamfiset.algorithms.datastructures.suffixarray;

import java.util.Arrays;

/**
 * Medium-speed Suffix Array Construction (Prefix Doubling)
 *
 * Builds a suffix array by repeatedly doubling the prefix length used for
 * ranking. In each round, suffixes are sorted by their first 2^k characters
 * using the ranks from the previous round as a two-key comparison.
 *
 * Compare with SuffixArraySlow (O(n^2 log n)) for a simpler but slower approach,
 * and SuffixArrayFast (O(n*log(n))) for an optimized version using radix sort.
 *
 * Time:  O(n*log^2(n)) — O(log(n)) doubling rounds, each with O(n*log(n)) sort
 * Space: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class SuffixArrayMed extends SuffixArray {

  // Holds the two-key rank (first half, second half) and original index
  // for sorting suffixes by their first 2^k characters.
  private static class SuffixRankTuple implements Comparable<SuffixRankTuple> {
    int firstHalf, secondHalf, originalIndex;

    @Override
    public int compareTo(SuffixRankTuple other) {
      int cmp = Integer.compare(firstHalf, other.firstHalf);
      if (cmp == 0) return Integer.compare(secondHalf, other.secondHalf);
      return cmp;
    }

    @Override
    public String toString() {
      return originalIndex + " -> (" + firstHalf + ", " + secondHalf + ")";
    }
  }

  public SuffixArrayMed(String text) {
    super(toIntArray(text));
  }

  public SuffixArrayMed(int[] text) {
    super(text);
  }

  /**
   * Constructs the suffix array using prefix doubling. Each iteration doubles
   * the window size and re-ranks suffixes until all ranks are unique.
   */
  @Override
  protected void construct() {
    sa = new int[N];

    // Two-row matrix: row 0 = current ranks, row 1 = new ranks
    int[][] suffixRanks = new int[2][N];
    SuffixRankTuple[] ranks = new SuffixRankTuple[N];

    // Initial ranks are the character values themselves
    for (int i = 0; i < N; i++) {
      suffixRanks[0][i] = T[i];
      ranks[i] = new SuffixRankTuple();
    }

    // Double the prefix length each round: 1, 2, 4, 8, ... → O(log(n)) rounds
    for (int pos = 1; pos < N; pos *= 2) {

      // Build two-key tuples: (rank of first half, rank of second half)
      for (int i = 0; i < N; i++) {
        SuffixRankTuple suffixRank = ranks[i];
        suffixRank.firstHalf = suffixRanks[0][i];
        suffixRank.secondHalf = i + pos < N ? suffixRanks[0][i + pos] : -1;
        suffixRank.originalIndex = i;
      }

      Arrays.sort(ranks);

      // Assign new ranks based on sorted order
      int newRank = 0;
      suffixRanks[1][ranks[0].originalIndex] = 0;

      for (int i = 1; i < N; i++) {
        SuffixRankTuple prev = ranks[i - 1];
        SuffixRankTuple cur = ranks[i];

        // Increment rank only when the tuple differs from the previous
        if (cur.firstHalf != prev.firstHalf || cur.secondHalf != prev.secondHalf)
          newRank++;

        suffixRanks[1][cur.originalIndex] = newRank;
      }

      suffixRanks[0] = suffixRanks[1];

      // All ranks unique means sorting is complete
      if (newRank == N - 1) break;
    }

    for (int i = 0; i < N; i++) {
      sa[i] = ranks[i].originalIndex;
    }
  }

  public static void main(String[] args) {
    SuffixArrayMed sa = new SuffixArrayMed("ABBABAABAA");
    System.out.println(sa);
  }
}
