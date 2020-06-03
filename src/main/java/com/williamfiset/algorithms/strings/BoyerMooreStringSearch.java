package com.williamfiset.algorithms.strings;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

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
    Map<Character, TreeSet<Integer>> skipTable = generateSkipTable(pattern);

    int textIndex = pattern.length() - 1;
    while (textIndex < text.length()) {
      int patternIndex = 0;
      boolean match = true;
      while (patternIndex < pattern.length() && match) {
        if (text.charAt(textIndex - patternIndex)
            != pattern.charAt(pattern.length() - patternIndex - 1)) {
          match = false;
          textIndex +=
              getSkipLength(
                  skipTable,
                  text.charAt(textIndex - patternIndex),
                  pattern.length() - patternIndex - 1);
        }
        patternIndex++;
      }
      if (match) {
        occurrences.add(textIndex - (pattern.length() - 1));
        textIndex +=
            getSkipLength(skipTable, pattern.charAt(pattern.length() - 1), pattern.length() - 1);
      }
    }

    return occurrences;
  }

  private int getSkipLength(
      Map<Character, TreeSet<Integer>> skipTable, char currentChar, int currentPositionInPat) {
    int skipLength = -1;
    if (skipTable.containsKey(currentChar)) {
      Integer loweThanPatternIndex = skipTable.get(currentChar).lower(currentPositionInPat);
      if (loweThanPatternIndex != null) {
        skipLength = currentPositionInPat - loweThanPatternIndex;
      }
    }
    return skipLength == -1 ? currentPositionInPat + 1 : skipLength;
  }

  private Map<Character, TreeSet<Integer>> generateSkipTable(String pattern) {
    Map<Character, TreeSet<Integer>> characterIndexMap = new HashMap<>();
    for (int charIndex = 0; charIndex < pattern.length(); charIndex++) {
      characterIndexMap
          .computeIfAbsent(pattern.charAt(charIndex), e -> new TreeSet<>())
          .add(charIndex);
    }
    return characterIndexMap;
  }
}
