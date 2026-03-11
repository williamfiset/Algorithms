package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import org.junit.jupiter.api.Test;

public class LongestPalindromeSubsequenceTest {

  @Test
  public void testLps() {
    String s1 = "bbbab";
    assertThat(LongestPalindromeSubsequence.lpsRecursive(s1)).isEqualTo(4);
    assertThat(LongestPalindromeSubsequence.lpsIterative(s1)).isEqualTo(4);

    String s2 = "bccd";
    assertThat(LongestPalindromeSubsequence.lpsRecursive(s2)).isEqualTo(2);
    assertThat(LongestPalindromeSubsequence.lpsIterative(s2)).isEqualTo(2);

    String s3 = "abcde";
    assertThat(LongestPalindromeSubsequence.lpsRecursive(s3)).isEqualTo(1);
    assertThat(LongestPalindromeSubsequence.lpsIterative(s3)).isEqualTo(1);

    String s4 = "aaaaa";
    assertThat(LongestPalindromeSubsequence.lpsRecursive(s4)).isEqualTo(5);
    assertThat(LongestPalindromeSubsequence.lpsIterative(s4)).isEqualTo(5);
  }

  @Test
  public void testEmptyStrings() {
    assertThat(LongestPalindromeSubsequence.lpsRecursive("")).isEqualTo(0);
    assertThat(LongestPalindromeSubsequence.lpsIterative("")).isEqualTo(0);
  }

  @Test
  public void testNullInputs() {
    assertThat(LongestPalindromeSubsequence.lpsRecursive(null)).isEqualTo(0);
    assertThat(LongestPalindromeSubsequence.lpsIterative(null)).isEqualTo(0);
  }
}
