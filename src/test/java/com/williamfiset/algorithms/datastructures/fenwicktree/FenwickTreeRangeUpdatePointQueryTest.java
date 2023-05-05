package com.williamfiset.algorithms.datastructures.fenwicktree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

public class FenwickTreeRangeUpdatePointQueryTest {

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

  static final int MIN_RAND_NUM = -1000;
  static final int MAX_RAND_NUM = +1000;

  static final int TEST_SZ = 1000;
  static final int LOOPS = 1000;

  static long UNUSED_VAL;

  @BeforeEach
  public void setup() {
    UNUSED_VAL = randValue();
  }

  @Test
  public void testIllegalCreation() {
    assertThrows(IllegalArgumentException.class, () -> new FenwickTreeRangeUpdatePointQuery(null));
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQueryNegativeNumbers() {

    long[] values = {UNUSED_VAL, -1, -1, -1, -1, -1};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertThat(ft.get(1)).isEqualTo(-1);
    assertThat(ft.get(2)).isEqualTo(9);
    assertThat(ft.get(3)).isEqualTo(9);
    assertThat(ft.get(4)).isEqualTo(9);
    assertThat(ft.get(5)).isEqualTo(-1);
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQuerySimple() {

    long[] values = {UNUSED_VAL, 2, 3, 4, 5, 6};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertThat(ft.get(1)).isEqualTo(2);
    assertThat(ft.get(2)).isEqualTo(13);
    assertThat(ft.get(3)).isEqualTo(14);
    assertThat(ft.get(4)).isEqualTo(15);
    assertThat(ft.get(5)).isEqualTo(6);
  }

  @Test
  public void testFenwickTreeRangeUpdatePointQuerySimple2() {

    long[] values = {UNUSED_VAL, 2, -3, -4, 5, 6};
    FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);
    ft.updateRange(2, 4, 10);
    assertThat(ft.get(1)).isEqualTo(2);
    assertThat(ft.get(2)).isEqualTo(7);
    assertThat(ft.get(3)).isEqualTo(6);
    assertThat(ft.get(4)).isEqualTo(15);
    assertThat(ft.get(5)).isEqualTo(6);
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
      for (int i = 1; i < n; i++) assertThat(ft.get(i)).isEqualTo(sum);
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

      for (int i = 1; i < n; i++) assertThat(ft.get(i)).isEqualTo(mockedFt.get(i));

      long delta = randValue();
      int lo = lowBound(n);
      int hi = highBound(lo, n - 1);

      mockedFt.updateRange(lo, hi, delta);
      ft.updateRange(lo, hi, delta);
    }
  }

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
}
