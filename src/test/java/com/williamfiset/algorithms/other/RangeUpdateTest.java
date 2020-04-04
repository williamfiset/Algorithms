package com.williamfiset.algorithms.other;

import static org.junit.Assert.*;

import org.junit.Test;

public class RangeUpdateTest {

  @Test
  public void RangeUpdateTest1() {
    int[] a = {10, 5, 20, 40};
    RangeUpdate rangeUpdate = new RangeUpdate(a);
    rangeUpdate.update(0, 1, 10);
    rangeUpdate.update(1, 3, 20);
    rangeUpdate.update(2, 2, 30);
    rangeUpdate.finalize();
    int[] expected = {20, 35, 70, 60};
    assertArrayEquals(expected, a);
  }

  @Test
  public void RangeUpdateTest2() {
    int[] a = {270, 311, 427, 535, 334, 193, 174};
    RangeUpdate rangeUpdate = new RangeUpdate(a);
    rangeUpdate.update(2, 5, 32);
    rangeUpdate.update(0, 4, 101);
    rangeUpdate.update(5, 6, -73);
    rangeUpdate.finalize();
    int[] expected = {371, 412, 560, 668, 467, 152, 101};
    assertArrayEquals(expected, a);
  }
}
