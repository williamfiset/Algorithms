package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;

public class EulerTotientFunctionTest {

  // Known values: https://oeis.org/A000010
  private static final long[] EXPECTED = {
    0, 1, 1, 2, 2, 4, 2, 6, 4, 6, 4, 10, 4, 12, 6, 8, 8, 16, 6, 18, 8
  };

  @Test
  public void testKnownValues() {
    for (int n = 1; n < EXPECTED.length; n++)
      assertThat(EulerTotientFunction.eulersTotient(n)).isEqualTo(EXPECTED[n]);
  }

  @Test
  public void testOne() {
    assertThat(EulerTotientFunction.eulersTotient(1)).isEqualTo(1);
  }

  @Test
  public void testPrimeReturnsPMinusOne() {
    int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 97};
    for (int p : primes)
      assertThat(EulerTotientFunction.eulersTotient(p)).isEqualTo(p - 1);
  }

  @Test
  public void testPowerOfTwo() {
    // phi(2^k) = 2^(k-1)
    for (int k = 1; k <= 20; k++)
      assertThat(EulerTotientFunction.eulersTotient(1L << k)).isEqualTo(1L << (k - 1));
  }

  @Test
  public void testLargeValue() {
    // phi(1000000) = 400000
    assertThat(EulerTotientFunction.eulersTotient(1000000)).isEqualTo(400000);
  }

  @Test
  public void testZeroThrows() {
    assertThrows(IllegalArgumentException.class, () -> EulerTotientFunction.eulersTotient(0));
  }

  @Test
  public void testNegativeThrows() {
    assertThrows(IllegalArgumentException.class, () -> EulerTotientFunction.eulersTotient(-5));
  }
}
