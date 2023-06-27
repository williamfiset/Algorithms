package com.williamfiset.algorithms.strings;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.*;

public class BoyerMooreStringSearchTest {

  private BoyerMooreStringSearch underTest;
  private Random random;
  private final int MAX_ITERATION = 20;

  @BeforeEach
  public void setup() {
    underTest = new BoyerMooreStringSearch();
    random = new Random();
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
  public void shouldReturnMultipleOccurrences() {
    assertThat(underTest.findOccurrences("SAAT TE", "TE")).containsExactly(5);
    assertThat(
            underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", "te"))
        .containsExactly(7, 16);
    assertThat(underTest.findOccurrences("Sample text for testing the Boyer-Moore algorithm.", " "))
        .containsExactly(6, 11, 15, 23, 27, 39);
    assertThat(underTest.findOccurrences("AABAACAADAABAABA", "AABA")).containsExactly(0, 9, 12);
    assertThat(underTest.findOccurrences("AAAAAAA", "AA")).containsExactly(0, 1, 2, 3, 4, 5);
  }

  @Test
  public void shouldReturnEmptyForPatternLengthLargerThenText() {
    assertThat(underTest.findOccurrences("This is a Test Text", "This is a test Pattern"))
        .isEmpty();
  }

  @Test
  public void shouldReturnDynamicString() {
    int runLength = random.nextInt(MAX_ITERATION) + 1;
    for (int run = 0; run < runLength; run++) {
      int upperCharText = random.nextInt(3);
      int upperCharPattern = random.nextInt(3);
      int maxLengthText =
          random.nextInt(1000) + 100; // random length of the string between [100=1000]
      int maxLengthPattern = random.nextInt(10);
      String text = generateRandomString(upperCharText, maxLengthText);
      String pattern = generateRandomString(upperCharPattern, maxLengthPattern);
      assertThat(underTest.findOccurrences(text, pattern))
          .containsExactlyElementsIn(getOccurrencesBruteForce(text, pattern));
    }
  }

  /**
   * @param text the text being searched in
   * @param pattern the pattern that needs to be searched in text
   * @return a list of beginning index of text where pattern exits
   */
  private List<Integer> getOccurrencesBruteForce(String text, String pattern) {
    if (isNull(text)
        || isNull(pattern)
        || text.length() < pattern.length()
        || pattern.length() == 0) {
      return new ArrayList<>();
    }
    List<Integer> occurrences = new ArrayList<>();
    for (int textIndex = 0; textIndex < text.length() - pattern.length() + 1; textIndex++) {
      boolean match = true;
      for (int patIndex = 0; patIndex < pattern.length(); patIndex++) {
        if (text.charAt(textIndex + patIndex) != pattern.charAt(patIndex)) {
          match = false;
          break;
        }
      }
      if (match) {
        occurrences.add(textIndex);
      }
    }
    return occurrences;
  }

  /**
   * @param upperLimitAscii Largest element in the random string
   * @param length Length of the random string
   * @return Returns a random string containing character between [a-z]
   */
  private String generateRandomString(int upperLimitAscii, int length) {
    return random
        .ints('a', 'a' + upperLimitAscii + 1)
        .limit(length)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
        .toString();
  }

  // TODO(william): Add a test that compares this implementation of Boyermoore
  // with that of KMP.
}
