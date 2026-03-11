package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest {

  @Test
  public void testNullInputs() {
    assertThat(LongestCommonSubsequence.lcs((String) null, "abc")).isNull();
    assertThat(LongestCommonSubsequence.lcs("abc", (String) null)).isNull();
    assertThat(LongestCommonSubsequence.lcs((char[]) null, "abc".toCharArray())).isNull();
  }

  @Test
  public void testEmptyInputs() {
    assertThat(LongestCommonSubsequence.lcs("", "abc")).isEmpty();
    assertThat(LongestCommonSubsequence.lcs("abc", "")).isEmpty();
    assertThat(LongestCommonSubsequence.lcs("", "")).isEmpty();
  }

  @Test
  public void testSingleCharMatch() {
    assertThat(LongestCommonSubsequence.lcs("X", "X")).isEqualTo("X");
  }

  @Test
  public void testSingleCharNoMatch() {
    assertThat(LongestCommonSubsequence.lcs("X", "Y")).isEmpty();
  }

  @Test
  public void testBasicLCS() {
    assertThat(LongestCommonSubsequence.lcs("AXBCY", "ZAYWBC")).isEqualTo("ABC");
  }

  @Test
  public void testCharArrayOverload() {
    assertThat(LongestCommonSubsequence.lcs("AXBCY".toCharArray(), "ZAYWBC".toCharArray()))
        .isEqualTo("ABC");
  }

  /** The LCS is not unique for this input; just verify the length. */
  @Test
  public void testNumericSequence() {
    assertThat(LongestCommonSubsequence.lcs("398397970", "3399917206").length()).isEqualTo(6);
  }

  @Test
  public void testNoCommonSubsequence() {
    assertThat(LongestCommonSubsequence.lcs("ABC", "XYZ")).isEmpty();
  }

  @Test
  public void testIdenticalStrings() {
    assertThat(LongestCommonSubsequence.lcs("ABCDE", "ABCDE")).isEqualTo("ABCDE");
  }

  @Test
  public void testOneIsSubsequence() {
    assertThat(LongestCommonSubsequence.lcs("abcde", "ace")).isEqualTo("ace");
  }

  @Test
  public void testPrefixMatch() {
    assertThat(LongestCommonSubsequence.lcs("ABCXYZ", "ABC")).isEqualTo("ABC");
  }

  @Test
  public void testSuffixMatch() {
    assertThat(LongestCommonSubsequence.lcs("XYZABC", "ABC")).isEqualTo("ABC");
  }

  @Test
  public void testRepeatedCharacters() {
    assertThat(LongestCommonSubsequence.lcs("AAAA", "AA")).isEqualTo("AA");
  }

  @Test
  public void testInterleavedPattern() {
    // LCS of "ABAB" and "BABA" is length 3
    assertThat(LongestCommonSubsequence.lcs("ABAB", "BABA").length()).isEqualTo(3);
  }
}
