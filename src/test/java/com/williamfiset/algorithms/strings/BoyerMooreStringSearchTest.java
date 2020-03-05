package com.williamfiset.algorithms.strings;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.strings.BoyerMooreStringSearch;
import org.junit.Before;
import org.junit.Test;

public class BoyerMooreStringSearchTest {

  private BoyerMooreStringSearch underTest;

  @Before
  public void setup() {
    underTest = new BoyerMooreStringSearch();
  }

  @Test
  public void shouldReturnEmptyListOnNullInput() {
    assertThat(underTest.findOccurrences(null, "")).isEmpty();
    assertThat(underTest.findOccurrences("", null)).isEmpty();
    assertThat(underTest.findOccurrences(null, null)).isEmpty();
  }

  @Test
  public void shouldReturnOneOccurrence() {
    assertThat(
            underTest.findOccurrences(
                "Sample text for testing the Boyer-Moore algorithm.", "Sample"))
        .containsExactly(0);
    assertThat(
            underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", "for"))
        .containsExactly(12);
    assertThat(underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", "."))
        .containsExactly(49);
  }

  @Test
  public void shouldReturnMultiplyOccurrences() {
    assertThat(
            underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", "te"))
        .containsAllOf(7, 16);
    assertThat(underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", " "))
        .containsAllOf(6, 11, 15, 23, 39);
  }
}
