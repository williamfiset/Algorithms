/**
 * This file shows you how to use a suffix array to determine if a pattern exists within a text.
 * This implementation has the advantage that once the suffix array is built queries can be very
 * fast.
 *
 * <p>Time complexity: O(nlogn) for suffix array construction and O(mlogn) time for individual
 * queries (where m is query string length). As noted below, depending on the length of the string
 * (if it is very large) it may be faster to use KMP or if you're doing a lot of queries on small
 * strings then Rabin-Karp in combination with a bloom filter.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.strings;

import java.util.*;

// Example usage
public class SubstringVerificationSuffixArray {
  public static void main(String[] args) {

    String pattern = "hello world";
    String text = "hello lemon Lennon wallet world tree cabbage hello world teapot calculator";
    SuffixArray sa = new SuffixArray(text);
    System.out.println(sa.contains(pattern));
    System.out.println(sa.contains("this pattern does not exist"));
  }

  public static class SuffixArray {

    // ALPHABET_SZ is the default alphabet size, this may need to be much
    // larger if you're using the LCS method with multiple sentinels
    int ALPHABET_SZ = 256, N;
    int[] T, sa, sa2, rank, tmp, c;

    public SuffixArray(String str) {
      this(toIntArray(str));
    }

    private static int[] toIntArray(String s) {
      int[] text = new int[s.length()];
      for (int i = 0; i < s.length(); i++) text[i] = s.charAt(i);
      return text;
    }

    // Designated constructor
    public SuffixArray(int[] text) {
      T = text;
      N = text.length;
      sa = new int[N];
      sa2 = new int[N];
      rank = new int[N];
      c = new int[Math.max(ALPHABET_SZ, N)];
      construct();
    }

    private void construct() {
      int i, p, r;
      for (i = 0; i < N; ++i) c[rank[i] = T[i]]++;
      for (i = 1; i < ALPHABET_SZ; ++i) c[i] += c[i - 1];
      for (i = N - 1; i >= 0; --i) sa[--c[T[i]]] = i;
      for (p = 1; p < N; p <<= 1) {
        for (r = 0, i = N - p; i < N; ++i) sa2[r++] = i;
        for (i = 0; i < N; ++i) if (sa[i] >= p) sa2[r++] = sa[i] - p;
        Arrays.fill(c, 0, ALPHABET_SZ, 0);
        for (i = 0; i < N; ++i) c[rank[i]]++;
        for (i = 1; i < ALPHABET_SZ; ++i) c[i] += c[i - 1];
        for (i = N - 1; i >= 0; --i) sa[--c[rank[sa2[i]]]] = sa2[i];
        for (sa2[sa[0]] = r = 0, i = 1; i < N; ++i) {
          if (!(rank[sa[i - 1]] == rank[sa[i]]
              && sa[i - 1] + p < N
              && sa[i] + p < N
              && rank[sa[i - 1] + p] == rank[sa[i] + p])) r++;
          sa2[sa[i]] = r;
        }
        tmp = rank;
        rank = sa2;
        sa2 = tmp;
        if (r == N - 1) break;
        ALPHABET_SZ = r + 1;
      }
    }

    // Runs on O(mlog(n)) where m is the length of the substring
    // and n is the length of the text. Depending on the length
    // of the string (if it is very large) it may be faster to
    // use KMP or if you're doing a lot of queries then Rabin-Karp
    // in combination with a bloom filter.
    //
    // NOTE: This is the naive implementation. There exists an
    // implementation which runs in O(m + log(n)) time
    public boolean contains(String substr) {

      if (substr == null) return false;
      if (substr.equals("")) return true;

      String suffix_str;
      int lo = 0, hi = N - 1;
      int substr_len = substr.length();

      // Do binary search
      while (lo <= hi) {

        int mid = (lo + hi) >>> 1;
        int suffix_index = sa[mid];
        int suffix_len = N - suffix_index;

        // Extract part of the suffix we need to compare
        if (suffix_len <= substr_len) suffix_str = new String(T, suffix_index, suffix_len);
        else suffix_str = new String(T, suffix_index, substr_len);

        int cmp = suffix_str.compareTo(substr);

        // Found a match
        if (cmp == 0) {
          return true;
        } else if (cmp < 0) {
          lo = mid + 1;
        } else {
          hi = mid - 1;
        }
      }
      return false;
    }
  }
}
