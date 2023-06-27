package com.williamfiset.algorithms.datastructures.fenwicktree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

public class FenwickTreeRangeQueryPointUpdateTest {

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
  public void testIntervalSumPositiveValues() {

    // System.out.println("testIntervalSumPositiveValues");
    long[] ar = {UNUSED_VAL, 1, 2, 3, 4, 5, 6};
    FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(ar);

    assertThat(ft.sum(1, 6)).isEqualTo(21);
    assertThat(ft.sum(1, 5)).isEqualTo(15);
    assertThat(ft.sum(1, 4)).isEqualTo(10);
    assertThat(ft.sum(1, 3)).isEqualTo(6);
    assertThat(ft.sum(1, 2)).isEqualTo(3);
    assertThat(ft.sum(1, 1)).isEqualTo(1);
    // assertThat(ft.sum(1, 0)).isEqualTo(0);

    assertThat(ft.sum(3, 4)).isEqualTo(7);
    assertThat(ft.sum(2, 6)).isEqualTo(20);
    assertThat(ft.sum(4, 5)).isEqualTo(9);
    assertThat(ft.sum(6, 6)).isEqualTo(6);
    assertThat(ft.sum(5, 5)).isEqualTo(5);
    assertThat(ft.sum(4, 4)).isEqualTo(4);
    assertThat(ft.sum(3, 3)).isEqualTo(3);
    assertThat(ft.sum(2, 2)).isEqualTo(2);
    assertThat(ft.sum(1, 1)).isEqualTo(1);
  }

  @Test
  public void testIntervalSumNegativeValues() {

    // System.out.println("testIntervalSumNegativeValues");
    long[] ar = {UNUSED_VAL, -1, -2, -3, -4, -5, -6};
    FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(ar);

    assertThat(ft.sum(1, 6)).isEqualTo(-21);
    assertThat(ft.sum(1, 5)).isEqualTo(-15);
    assertThat(ft.sum(1, 4)).isEqualTo(-10);
    assertThat(ft.sum(1, 3)).isEqualTo(-6);
    assertThat(ft.sum(1, 2)).isEqualTo(-3);
    assertThat(ft.sum(1, 1)).isEqualTo(-1);

    assertThat(ft.sum(6, 6)).isEqualTo(-6);
    assertThat(ft.sum(5, 5)).isEqualTo(-5);
    assertThat(ft.sum(4, 4)).isEqualTo(-4);
    assertThat(ft.sum(3, 3)).isEqualTo(-3);
    assertThat(ft.sum(2, 2)).isEqualTo(-2);
    assertThat(ft.sum(1, 1)).isEqualTo(-1);
  }

  @Test
  public void testIntervalSumNegativeValues2() {

    // System.out.println("testIntervalSumNegativeValues2");
    long[] ar = {UNUSED_VAL, -76871, -164790};
    FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(ar);

    for (int i = 0; i < LOOPS; i++) {
      assertThat(ft.sum(1, 1)).isEqualTo(-76871);
      assertThat(ft.sum(1, 1)).isEqualTo(-76871);
      assertThat(ft.sum(1, 2)).isEqualTo(-241661);
      assertThat(ft.sum(1, 2)).isEqualTo(-241661);
      assertThat(ft.sum(1, 2)).isEqualTo(-241661);
      assertThat(ft.sum(2, 2)).isEqualTo(-164790);
      assertThat(ft.sum(2, 2)).isEqualTo(-164790);
      assertThat(ft.sum(2, 2)).isEqualTo(-164790);
    }
  }

  @Test
  public void testRandomizedStaticSumQueries() {

    // System.out.println("testRandomizedStaticSumQueries");
    for (int i = 1; i <= LOOPS; i++) {

      long[] randList = genRandList(i);
      FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(randList);

      for (int j = 0; j < LOOPS / 10; j++) {
        doRandomRangeQuery(randList, ft);
      }
    }
  }

  public void doRandomRangeQuery(long[] arr, FenwickTreeRangeQueryPointUpdate ft) {

    long sum = 0L;
    int N = arr.length - 1;

    int lo = lowBound(N);
    int hi = highBound(lo, N);

    // System.out.println("LO: " + lo + " HI: " + hi + " N: " + N);

    for (int k = lo; k <= hi; k++) sum += arr[k];

    assertThat(ft.sum(lo, hi)).isEqualTo(sum);
  }

  @Test
  public void testRandomizedSetSumQueries() {

    // System.out.println("testRandomizedSetSumQueries");
    for (int n = 2; n <= LOOPS; n++) {

      long[] randList = genRandList(n);
      FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(randList);

      for (int j = 0; j < LOOPS / 10; j++) {

        int index = 1 + ((int) Math.random() * n);
        long rand_val = randValue();

        randList[index] += rand_val;
        ft.add(index, rand_val);

        doRandomRangeQuery(randList, ft);
      }
    }
  }

  @Test
  public void testReusability() {

    int SIZE = 1000;
    FenwickTreeRangeQueryPointUpdate ft = new FenwickTreeRangeQueryPointUpdate(SIZE);
    long[] arr = new long[SIZE + 1];

    for (int loop = 0; loop < LOOPS; loop++) {

      for (int i = 1; i <= SIZE; i++) {
        long val = randValue();
        ft.set(i, val);
        arr[i] = val;
      }
      doRandomRangeQuery(arr, ft);
    }
  }

  public static int lowBound(int N) {
    return 1 + (int) (Math.random() * N);
  }

  public static int highBound(int low, int N) {
    return Math.min(N, low + (int) (Math.random() * N));
  }

  public static long randValue() {
    return (long) (Math.random() * MAX_RAND_NUM * 2) + MIN_RAND_NUM;
  }

  @Test
  public void testIllegalCreation() {
    assertThrows(IllegalArgumentException.class, () -> new FenwickTreeRangeQueryPointUpdate(null));
  }

  // Generate a list of random numbers, one based
  static long[] genRandList(int sz) {
    long[] lst = new long[sz + 1];
    for (int i = 1; i <= sz; i++) {
      lst[i] = randValue();
    }
    return lst;
  }
}
