package com.williamfiset.algorithms.datastructures.suffixarray;

import java.util.Arrays;

/**
 * Fast Suffix Array Construction (Prefix Doubling with Radix Sort)
 *
 * Builds a suffix array using prefix doubling with counting sort (radix sort)
 * instead of comparison-based sorting. Each doubling round uses two passes of
 * counting sort to sort suffix pairs by their rank, achieving O(n) per round
 * instead of O(n*log(n)) with comparison sort.
 *
 * Compare with SuffixArraySlow (O(n^2*log(n))) for a naive approach, and
 * SuffixArrayMed (O(n*log^2(n))) for prefix doubling with comparison sort.
 *
 * Time:  O(n*log(n)) -- O(log(n)) doubling rounds, each O(n) with radix sort
 * Space: O(n + alphabetSize)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class SuffixArrayFast extends SuffixArray {

  private static final int DEFAULT_ALPHABET_SIZE = 256;

  private int alphabetSize;
  private int[] sa2, rank, tmp, c;

  public SuffixArrayFast(String text) {
    this(toIntArray(text), DEFAULT_ALPHABET_SIZE);
  }

  public SuffixArrayFast(int[] text) {
    this(text, DEFAULT_ALPHABET_SIZE);
  }

  /**
   * Creates a suffix array with a custom alphabet size.
   *
   * @param text         the input text as an integer array
   * @param alphabetSize the number of distinct symbols (e.g., 256 for ASCII)
   */
  public SuffixArrayFast(int[] text, int alphabetSize) {
    super(text);
    this.alphabetSize = alphabetSize;
  }

  /**
   * Constructs the suffix array using prefix doubling with radix sort.
   * Each round doubles the comparison window and re-ranks suffixes using
   * counting sort for O(n) per round, giving O(n*log(n)) total.
   */
  @Override
  protected void construct() {
    sa = new int[N];
    sa2 = new int[N];
    rank = new int[N];
    c = new int[Math.max(alphabetSize, N)];

    int i, p, r;

    // --- Initial sort: rank suffixes by their first character using counting sort ---

    // Count occurrences of each character
    for (i = 0; i < N; ++i) c[rank[i] = T[i]]++;
    // Convert counts to cumulative positions
    for (i = 1; i < alphabetSize; ++i) c[i] += c[i - 1];
    // Place suffixes into sa in sorted order (stable, right-to-left)
    for (i = N - 1; i >= 0; --i) sa[--c[T[i]]] = i;

    // --- Prefix doubling: sort by first 2^k characters each round ---
    for (p = 1; p < N; p <<= 1) {

      // Build sa2: suffixes sorted by their *second half* (positions i+p).
      // Suffixes near the end (i >= N-p) have no second half, so they sort first.
      for (r = 0, i = N - p; i < N; ++i) sa2[r++] = i;
      // Remaining suffixes inherit order from sa (already sorted by first half)
      for (i = 0; i < N; ++i) if (sa[i] >= p) sa2[r++] = sa[i] - p;

      // Counting sort sa2 by first-half rank to get the final sorted order.
      // This is a radix sort: sa2 provides second-key order, we sort by first key.
      Arrays.fill(c, 0, alphabetSize, 0);
      for (i = 0; i < N; ++i) c[rank[i]]++;
      for (i = 1; i < alphabetSize; ++i) c[i] += c[i - 1];
      for (i = N - 1; i >= 0; --i) sa[--c[rank[sa2[i]]]] = sa2[i];

      // Compute new ranks from the sorted order. Two suffixes get the same
      // rank only if both their first-half and second-half ranks match.
      for (sa2[sa[0]] = r = 0, i = 1; i < N; ++i) {
        if (!(rank[sa[i - 1]] == rank[sa[i]]
            && sa[i - 1] + p < N
            && sa[i] + p < N
            && rank[sa[i - 1] + p] == rank[sa[i] + p])) r++;
        sa2[sa[i]] = r;
      }

      // Swap rank and sa2 arrays to avoid allocation
      tmp = rank;
      rank = sa2;
      sa2 = tmp;

      // All ranks unique means sorting is complete
      if (r == N - 1) break;
      alphabetSize = r + 1;
    }
  }

  public static void main(String[] args) {
    SuffixArrayFast sa = new SuffixArrayFast("ABBABAABAA");
    System.out.println(sa);
  }
}
