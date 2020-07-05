package com.williamfiset.algorithms.search;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class InterpolationSearchTest {

  @Test
  public void testCoverage1() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 2);
    assertThat(index).isEqualTo(2);
  }

  @Test
  public void testCoverage2() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 5);
    assertThat(index).isEqualTo(5);
  }

  @Test
  public void testCoverage3() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, -1);
    assertThat(index).isEqualTo(-1);
  }

  @Test
  public void testCoverage4() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 8);
    assertThat(index).isEqualTo(-1);
  }
}
