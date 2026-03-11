package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest {

  // ==================== Null and empty input tests ====================

  @Test
  public void testNullInputs() {
    assertThat(LongestCommonSubsequence.lcs((String) null, "abc")).isNull();
    assertThat(LongestCommonSubsequence.lcs("abc", (String) null)).isNull();
    assertThat(LongestCommonSubsequence.lcs((char[]) null, "abc".toCharArray())).isNull();
    assertThat(LongestCommonSubsequence.lcsIterative(null, "abc".toCharArray())).isNull();
    assertThat(LongestCommonSubsequence.lcsRecursive(null, "abc".toCharArray())).isNull();
  }

  @Test
  public void testEmptyInputs() {
    assertThat(LongestCommonSubsequence.lcs("", "abc")).isEmpty();
    assertThat(LongestCommonSubsequence.lcs("abc", "")).isEmpty();
    assertThat(LongestCommonSubsequence.lcs("", "")).isEmpty();
    assertThat(LongestCommonSubsequence.lcsIterative("".toCharArray(), "abc".toCharArray())).isEmpty();
    assertThat(LongestCommonSubsequence.lcsRecursive("".toCharArray(), "abc".toCharArray())).isEmpty();
  }

  // ==================== Single character tests ====================

  @Test
  public void testSingleCharMatch() {
    assertThat(LongestCommonSubsequence.lcs("X", "X")).isEqualTo("X");
  }

  @Test
  public void testSingleCharNoMatch() {
    assertThat(LongestCommonSubsequence.lcs("X", "Y")).isEmpty();
  }

  // ==================== Standard cases ====================

  @Test
  public void testBasicLCS() {
    assertThat(LongestCommonSubsequence.lcs("AXBCY", "ZAYWBC")).isEqualTo("ABC");
  }

  @Test
  public void testLcsCharArrayOverload() {
    assertThat(LongestCommonSubsequence.lcs("AXBCY".toCharArray(), "ZAYWBC".toCharArray()))
        .isEqualTo("ABC");
  }

  @Test
  public void testLcsIterative() {
    assertThat(LongestCommonSubsequence.lcsIterative("AXBCY".toCharArray(), "ZAYWBC".toCharArray()))
        .isEqualTo("ABC");
  }

  @Test
  public void testLcsRecursive() {
    assertThat(LongestCommonSubsequence.lcsRecursive("AXBCY".toCharArray(), "ZAYWBC".toCharArray()))
        .isEqualTo("ABC");
  }

  @Test
  public void testNumericSequence() {
    char[] A = "398397970".toCharArray();
    char[] B = "3399917206".toCharArray();
    // LCS length is 6, though the exact LCS may vary (not unique)
    assertThat(LongestCommonSubsequence.lcsIterative(A, B).length()).isEqualTo(6);
    assertThat(LongestCommonSubsequence.lcsRecursive(A, B).length()).isEqualTo(6);
  }

  @Test
  public void testNoCommonSubsequence() {
    assertThat(LongestCommonSubsequence.lcs("ABC", "XYZ")).isEmpty();
  }

  @Test
  public void testIdenticalStrings() {
    assertThat(LongestCommonSubsequence.lcs("ABCDE", "ABCDE")).isEqualTo("ABCDE");
  }

  /** One string is a subsequence of the other. */
  @Test
  public void testOneIsSubsequence() {
    assertThat(LongestCommonSubsequence.lcs("abcde", "ace")).isEqualTo("ace");
  }

  /** One string is a prefix of the other. */
  @Test
  public void testPrefixMatch() {
    assertThat(LongestCommonSubsequence.lcs("ABCXYZ", "ABC")).isEqualTo("ABC");
  }

  /** One string is a suffix of the other. */
  @Test
  public void testSuffixMatch() {
    assertThat(LongestCommonSubsequence.lcs("XYZABC", "ABC")).isEqualTo("ABC");
  }

  // ==================== Cross-validation: iterative vs recursive lengths agree ====================

  @Test
  public void testIterativeAndRecursiveAgreeOnLength() {
    String[][] pairs = {
        {"AGGTAB", "GXTXAYB"},
        {"ABCBDAB", "BDCAB"},
        {"AAAA", "AA"},
        {"ABAB", "BABA"},
    };
    for (String[] pair : pairs) {
      char[] A = pair[0].toCharArray();
      char[] B = pair[1].toCharArray();
      int iterLen = LongestCommonSubsequence.lcsIterative(A, B).length();
      int recLen = LongestCommonSubsequence.lcsRecursive(A, B).length();
      assertThat(iterLen).isEqualTo(recLen);
    }
  }
}
