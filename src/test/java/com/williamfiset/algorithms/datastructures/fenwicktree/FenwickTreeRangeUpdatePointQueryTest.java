package com.williamfiset.algorithms.datastructures.fenwicktree;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FenwickTreeRangeUpdatePointQueryTest {

  static final int MIN_RAND_NUM = -1000;
  static final int MAX_RAND_NUM = +1000;
  static final int TEST_SZ = 1000;
  static final int LOOPS = 1000;
  static long UNUSED_VAL;

  // Select a lower bound index for the Fenwick tree
  public static int lowBound(int N) {
    return 1 + (int) (Math.random() * N);
  }

  // Select an upper bound index for the Fenwick tree
  public static int highBound(int low, int N) {
    return Math.min(N, low + (int) (Math.random() * N));
  }

  public static long randValue() {
    return (long) (Math.random() * MAX_RAND_NUM * 2) + MIN_RAND_NUM;
  }

  @Before
  public void setup() {
    UNUSED_VAL = randValue();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCreation() {
    new FenwickTreeRangeUpdatePointQuery(null);
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQueryNegativeNumbers() {

    long[] values = {UNUSED_VAL, -1, -1, -1, -1, -1};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertEquals(-1, ft.get(1));
    assertEquals(9, ft.get(2));
    assertEquals(9, ft.get(3));
    assertEquals(9, ft.get(4));
    assertEquals(-1, ft.get(5));
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQuerySimple() {

    long[] values = {UNUSED_VAL, 2, 3, 4, 5, 6};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertEquals(2, ft.get(1));
    assertEquals(13, ft.get(2));
    assertEquals(14, ft.get(3));
    assertEquals(15, ft.get(4));
    assertEquals(6, ft.get(5));
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQuerySimple2() {

    long[] values = {UNUSED_VAL, 2, -3, -4, 5, 6};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertEquals(2, ft.get(1));
    assertEquals(7, ft.get(2));
    assertEquals(6, ft.get(3));
    assertEquals(15, ft.get(4));
    assertEquals(6, ft.get(5));
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQueryRepeatedAddition() {

    int n = 100;
    long[] values = new long[n];
    values[0] = UNUSED_VAL;
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);

    int sum = 0;
    int delta = 10;

    for (int loop = 0; loop < TEST_SZ; loop++) {
      for (int i = 1; i < n; i++) assertEquals(sum, ft.get(i));
      ft.updateRange(1, n - 1, delta);
      sum += delta;
    }
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQueryOverlappingRanges() {

    // Setup values
    int n = 100;
    long[] values = new long[n];
    for (int i = 0; i < n; i++) values[i] = randValue();

    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    MockRangeUpdateFt mockedFt = new MockRangeUpdateFt(values);

    for (int loop = 0; loop < TEST_SZ; loop++) {

      for (int i = 1; i < n; i++) assertEquals(mockedFt.get(i), ft.get(i));

      long delta = randValue();
      int lo = lowBound(n);
      int hi = highBound(lo, n - 1);

      mockedFt.updateRange(lo, hi, delta);
      ft.updateRange(lo, hi, delta);
    }
  }

  static class MockRangeUpdateFt {
    long[] ar;

    public MockRangeUpdateFt(long[] values) {
      ar = values.clone();
    }

    public long get(int i) {
      return ar[i];
    }

    public void updateRange(int i, int j, long v) {
      for (int k = i; k <= j; k++) ar[k] += v;
    }
  }
}
