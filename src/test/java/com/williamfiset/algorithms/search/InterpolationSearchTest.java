package com.williamfiset.algorithms.search;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

public class InterpolationSearchTest {

  private static Arguments[] inputs() {
    return new Arguments[] {
      Arguments.of(2, 2), Arguments.of(5, 5), Arguments.of(-1, -1), Arguments.of(8, -1)
    };
  }

  @ParameterizedTest(name = "Search value: {0}, Expected index: {1}")
  @MethodSource("inputs")
  public void testCoverage(int val, int expected) {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, val);
    assertThat(index).isEqualTo(expected);
  }
}
