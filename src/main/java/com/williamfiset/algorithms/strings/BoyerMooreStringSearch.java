package com.williamfiset.algorithms.strings;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoyerMooreStringSearch {

  /**
   * Performs Boyer-Moore search on a given string with a given pattern
   *
   * @param text the string being searched in
   * @param pattern the string being searched for
   * @return List of indexes where the pattern occurs
   */
  public List<Integer> findOccurrences(String text, String pattern) {
    if (isNull(text) || isNull(pattern)) {
      return new ArrayList<>();
    }
    List<Integer> occurrences = new ArrayList<>();

    Map<Character, Integer> skipTable = new HashMap<>();
    for (int i = 0; i < pattern.length(); i++) {
      skipTable.put(pattern.charAt(i), pattern.length() - i - 1);
    }

    int textIndex = pattern.length() - 1;
    while (textIndex < text.length()) {
      int patternIndex = 0;
      boolean match = true;
      while (patternIndex < pattern.length() && match) {
        if (text.charAt(textIndex - patternIndex)
            != pattern.charAt(pattern.length() - patternIndex - 1)) {
          match = false;
          textIndex +=
              skipTable.getOrDefault(text.charAt(textIndex - patternIndex), pattern.length());
        }
        patternIndex++;
      }
      if (match) {
        occurrences.add(textIndex - (pattern.length() - 1));
        textIndex += pattern.length();
      }
    }

    return occurrences;
  }
}
