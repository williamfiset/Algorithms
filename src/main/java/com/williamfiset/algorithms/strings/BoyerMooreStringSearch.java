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
   * @return List of indexes where the pattern occursq
   */
  public List<Integer> findOccurrences(String text, String pattern) {
    if (isNull(text)
        || isNull(pattern)
        || pattern.length() > text.length()
        || pattern.length() == 0) {
      return new ArrayList<>();
    }
    List<Integer> occurrences = new ArrayList<>();
    Map<Character, Integer> skipTable = generateSkipTable(pattern);

    int textIndex = pattern.length() - 1;
    int patternIndex = pattern.length() - 1;
    while (textIndex < text.length()) {
      if (patternIndex >= 0 && pattern.charAt(patternIndex) == text.charAt(textIndex)) {
        if (patternIndex == 0) {
          occurrences.add(textIndex);
        } else {
          textIndex--;
        }
        patternIndex--;
      } else {
        textIndex =
            textIndex
                + pattern.length()
                - Math.min(
                    Math.max(patternIndex, 0),
                    1 + skipTable.getOrDefault(text.charAt(textIndex), 0));
        patternIndex = pattern.length() - 1;
      }
    }
    return occurrences;
  }

  private Map<Character, Integer> generateSkipTable(String pattern) {
    Map<Character, Integer> characterIndexMap = new HashMap<>();
    for (int charIndex = 0; charIndex < pattern.length(); charIndex++) {
      characterIndexMap.put(pattern.charAt(charIndex), charIndex);
    }
    return characterIndexMap;
  }
}
