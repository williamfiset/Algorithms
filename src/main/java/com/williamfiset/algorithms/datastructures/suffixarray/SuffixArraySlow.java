package com.williamfiset.algorithms.datastructures.suffixarray;

import java.util.Arrays;

/**
 * Naive Suffix Array Construction
 *
 * Builds a suffix array by generating all suffixes, sorting them with
 * a standard comparison sort, and extracting the sorted indices.
 * Simple to understand but slow for large inputs.
 *
 * Compare with SuffixArrayMed (O(n*log^2(n))) and SuffixArrayFast (O(n*log(n)))
 * to see progressively more efficient construction algorithms.
 *
 * Time:  O(n^2*log(n)) — sorting is O(n*log(n)) comparisons, each O(n)
 * Space: O(n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
public class SuffixArraySlow extends SuffixArray {

  private static class Suffix implements Comparable<Suffix> {
    final int index, len;
    final int[] text;

    Suffix(int[] text, int index) {
      this.len = text.length - index;
      this.index = index;
      this.text = text;
    }

    // Lexicographic comparison of two suffixes, character by character.
    // If one suffix is a prefix of the other, the shorter one comes first.
    @Override
    public int compareTo(Suffix other) {
      if (this == other) return 0;
      int minLen = Math.min(len, other.len);
      for (int i = 0; i < minLen; i++) {
        if (text[index + i] < other.text[other.index + i]) return -1;
        if (text[index + i] > other.text[other.index + i]) return +1;
      }
      return len - other.len;
    }

    @Override
    public String toString() {
      return new String(text, index, len);
    }
  }

  private Suffix[] suffixes;

  public SuffixArraySlow(String text) {
    super(toIntArray(text));
  }

  public SuffixArraySlow(int[] text) {
    super(text);
  }

  /**
   * Constructs the suffix array by creating all n suffixes, sorting
   * them lexicographically, then storing the sorted starting indices.
   */
  @Override
  protected void construct() {
    sa = new int[N];
    suffixes = new Suffix[N];

    for (int i = 0; i < N; i++) suffixes[i] = new Suffix(T, i);

    Arrays.sort(suffixes);

    for (int i = 0; i < N; i++) {
      sa[i] = suffixes[i].index;
    }

    suffixes = null;
  }

  public static void main(String[] args) {
    SuffixArraySlow sa = new SuffixArraySlow("ABBABAABAA");
    System.out.println(sa);
  }
}
