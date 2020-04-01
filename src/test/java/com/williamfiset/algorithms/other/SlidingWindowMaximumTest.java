package com.williamfiset.algorithms.other;

import static org.junit.Assert.*;

import org.junit.*;

public class SlidingWindowMaximumTest {

  final int TESTS = 1500;

  @Test
  public void smallWindowTest() {

    int[] values = {1, 2, 1, 3, 0, 4};
    SlidingWindowMaximum w = new SlidingWindowMaximum(values);

    w.advance();
    assertEquals(1, w.getMax());
    w.advance();
    assertEquals(2, w.getMax());
    w.advance();
    assertEquals(2, w.getMax());
    w.shrink();
    assertEquals(2, w.getMax());
    w.shrink();
    assertEquals(1, w.getMax());
    w.advance();
    assertEquals(3, w.getMax());
    w.advance();
    assertEquals(3, w.getMax());
    w.advance();
    assertEquals(4, w.getMax());
    w.shrink();
    assertEquals(4, w.getMax());
    w.shrink();
    assertEquals(4, w.getMax());
    w.shrink();
    assertEquals(4, w.getMax());
  }

  @Test
  public void randomizedSlidingWindowTest() {
    for (int sz = 1; sz <= TESTS; sz++) {
      randomizedTest(sz);
    }
  }

  private static void fillRandom(int[] ar) {
    for (int i = 0; i < ar.length; i++) {
      if (Math.random() < 0.5) {
        ar[i] = (int) (Math.random() * +25);
      } else {
        ar[i] = (int) (Math.random() * -25);
      }
    }
  }

  public static void randomizedTest(int n) {

    double r = Math.max(0.1, Math.random());
    int[] ar = new int[n];
    fillRandom(ar);

    SlidingWindowMaximum window = new SlidingWindowMaximum(ar);
    int lo = 0, hi = 0;
    while (hi < n) {

      // increase hi
      if (Math.random() < r) {
        window.advance();
        hi++;

        // increase lo if we can
      } else {
        if (lo + 1 < hi) {
          lo++;
          window.shrink();
        }
      }

      // Ignore invalid queries
      if (window.lo == window.hi) continue;

      // Manually find the window maximum
      int max = Integer.MIN_VALUE;
      for (int i = lo; i < hi; i++) max = Math.max(max, ar[i]);

      assertEquals(max, window.getMax());
    }
  }
}
