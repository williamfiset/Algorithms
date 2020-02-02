/**
 * Finds the longest repeated substring(s) of a string.
 *
 * <p>Time complexity: O(nlogn), bounded by suffix array construction
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.strings;

import java.util.*;

public class LongestRepeatedSubstring {

  // Example usage
  public static void main(String[] args) {

    String str = "ABC$BCA$CAB";
    SuffixArray sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());

    str = "aaaaa";
    sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());

    str = "abcde";
    sa = new SuffixArray(str);
    System.out.printf("LRS(s) of %s is/are: %s\n", str, sa.lrs());
  }

  public static class SuffixArray {

    // ALPHABET_SZ is the default alphabet size, this may need to be much larger
    int ALPHABET_SZ = 256, N;
    int[] T, lcp, sa, sa2, rank, tmp, c;

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
      kasai();
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

    // Use Kasai algorithm to build LCP array
    private void kasai() {
      lcp = new int[N];
      int[] inv = new int[N];
      for (int i = 0; i < N; i++) inv[sa[i]] = i;
      for (int i = 0, len = 0; i < N; i++) {
        if (inv[i] > 0) {
          int k = sa[inv[i] - 1];
          while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len]) len++;
          lcp[inv[i] - 1] = len;
          if (len > 0) len--;
        }
      }
    }

    // Finds the LRS(s) (Longest Repeated Substring) that occurs in a string.
    // Traditionally we are only interested in substrings that appear at
    // least twice, so this method returns an empty set if this is not the case.
    // @return an ordered set of longest repeated substrings
    public TreeSet<String> lrs() {

      int max_len = 0;
      TreeSet<String> lrss = new TreeSet<>();

      for (int i = 0; i < N; i++) {
        if (lcp[i] > 0 && lcp[i] >= max_len) {

          // We found a longer LRS
          if (lcp[i] > max_len) lrss.clear();

          // Append substring to the list and update max
          max_len = lcp[i];
          lrss.add(new String(T, sa[i], max_len));
        }
      }

      return lrss;
    }

    public void display() {
      System.out.printf("-----i-----SA-----LCP---Suffix\n");
      for (int i = 0; i < N; i++) {
        int suffixLen = N - sa[i];
        String suffix = new String(T, sa[i], suffixLen);
        System.out.printf("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
      }
    }
  }
}
