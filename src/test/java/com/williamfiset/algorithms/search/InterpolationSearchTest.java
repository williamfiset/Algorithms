package com.williamfiset.algorithms.search;

import static org.junit.Assert.*;

import org.junit.Test;

public class InterpolationSearchTest {

  @Test
  public void testCoverage1() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 2);
    assertTrue(index == 2);
  }

  @Test
  public void testCoverage2() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 5);
    assertTrue(index == 5);
  }

  @Test
  public void testCoverage3() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, -1);
    assertTrue(index == -1);
  }

  @Test
  public void testCoverage4() {
    int[] arr = {0, 1, 2, 3, 4, 5};
    int index = InterpolationSearch.interpolationSearch(arr, 8);
    assertTrue(index == -1);
  }
}
