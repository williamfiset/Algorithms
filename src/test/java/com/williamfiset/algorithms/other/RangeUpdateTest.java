package com.williamfiset.algorithms.other;

import static org.junit.Assert.*;

import org.junit.Test;

public class RangeUpdateTest {
  private static RangeUpdate rangeUpdate;

  public static void prepareTest() {
    rangeUpdate = new RangeUpdate();
  }

  @Test
  public void RangeUpdateTest1() {
    int[] a = {10, 5, 20, 40};
    int[] d = new int[a.length + 1];
    rangeUpdate.initializeDiffArray(a, d);
    rangeUpdate.update(d, 0, 1, 10);
    rangeUpdate.update(d, 1, 3, 20);
    rangeUpdate.update(d, 2, 2, 30);
    rangeUpdate.updateArray(a, d);
    int[] expected = {20, 35, 70, 60};
    assertArrayEquals(expected, a);
  }

  @Test
  public void RangeUpdateTest2() {
    int[] a = {270, 311, 427, 535, 334, 193, 174};
    int[] d = new int[a.length + 1];
    rangeUpdate.initializeDiffArray(a, d);
    rangeUpdate.update(d, 2, 5, 32);
    rangeUpdate.update(d, 0, 4, 101);
    rangeUpdate.update(d, 5, 6, -73);
    rangeUpdate.updateArray(a, d);
    int[] expected = {371, 412, 560, 668, 467, 152, 101};
    assertArrayEquals(expected, a);
  }
}
