package com.williamfiset.algorithms.strings;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ZAlgorithmTest {
  private ZAlgorithm underTest;

  @Before
  public void setup() {
    underTest = new ZAlgorithm();
  }

  @Test
  public void shouldReturnEmptyArrayOnNullOrEmptyInput() {
    assertThat(underTest.calculateZ(null)).isEmpty();
    assertThat(underTest.calculateZ("")).isEmpty();
  }

  @Test
  public void textContainsASingleCharacterRepeated() {
    assertThat(underTest.calculateZ("aaaaaaa")).isEqualTo(new int[] {7, 6, 5, 4, 3, 2, 1});
    assertThat(underTest.calculateZ("bbbbbbbb")).isEqualTo(new int[] {8, 7, 6, 5, 4, 3, 2, 1});
  }

  @Test
  public void textContainsAllDistinctCharacters() {
    assertThat(underTest.calculateZ("abcdefgh")).isEqualTo(new int[] {8, 0, 0, 0, 0, 0, 0, 0});
  }

  @Test
  public void textContainsRepeatedPattern() {
    assertThat(underTest.calculateZ("abababab")).isEqualTo(new int[] {8, 0, 6, 0, 4, 0, 2, 0});
    assertThat(underTest.calculateZ("ababababa")).isEqualTo(new int[] {9, 0, 7, 0, 5, 0, 3, 0, 1});
    assertThat(underTest.calculateZ("abcabcabca"))
        .isEqualTo(new int[] {10, 0, 0, 7, 0, 0, 4, 0, 0, 1});
  }
}
