package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.*;

public class BinomialCoefficientModPrimeTest {

  private static final int MOD = 1_000_000_007;

  /** Computes C(n, r) mod p using BigInteger as a reference. */
  private static long bigIntCnr(int n, int r, int p) {
    BigInteger num = BigInteger.ONE;
    BigInteger den = BigInteger.ONE;
    for (int i = 0; i < r; i++) {
      num = num.multiply(BigInteger.valueOf(n - i));
      den = den.multiply(BigInteger.valueOf(i + 1));
    }
    return num.divide(den).mod(BigInteger.valueOf(p)).longValue();
  }

  @Test
  public void rEqualsZero() {
    assertThat(BinomialCoefficientModPrime.compute(10, 0, MOD)).isEqualTo(1);
  }

  @Test
  public void rEqualsN() {
    assertThat(BinomialCoefficientModPrime.compute(10, 10, MOD)).isEqualTo(1);
  }

  @Test
  public void smallValues() {
    assertThat(BinomialCoefficientModPrime.compute(5, 2, MOD)).isEqualTo(10);
    assertThat(BinomialCoefficientModPrime.compute(6, 3, MOD)).isEqualTo(20);
    assertThat(BinomialCoefficientModPrime.compute(10, 4, MOD)).isEqualTo(210);
  }

  @Test
  public void knownLargeValue() {
    // C(500, 250) mod 10^9+7 = 515561345
    assertThat(BinomialCoefficientModPrime.compute(500, 250, MOD)).isEqualTo(515561345L);
  }

  @Test
  public void symmetry() {
    // C(n, r) == C(n, n-r)
    assertThat(BinomialCoefficientModPrime.compute(100, 30, MOD))
        .isEqualTo(BinomialCoefficientModPrime.compute(100, 70, MOD));
  }

  @Test
  public void smallPrime() {
    // C(6, 2) = 15, mod 7 = 1
    assertThat(BinomialCoefficientModPrime.compute(6, 2, 7)).isEqualTo(1);
  }

  @Test
  public void matchesBigInteger() {
    int[] ns = {20, 50, 100, 200, 500};
    for (int n : ns) {
      for (int r = 0; r <= n; r += Math.max(1, n / 10)) {
        long expected = bigIntCnr(n, r, MOD);
        assertThat(BinomialCoefficientModPrime.compute(n, r, MOD)).isEqualTo(expected);
      }
    }
  }

  @Test
  public void negativeNThrows() {
    assertThrows(IllegalArgumentException.class,
        () -> BinomialCoefficientModPrime.compute(-1, 0, MOD));
  }

  @Test
  public void rGreaterThanNThrows() {
    assertThrows(IllegalArgumentException.class,
        () -> BinomialCoefficientModPrime.compute(5, 6, MOD));
  }

  @Test
  public void invalidModulusThrows() {
    assertThrows(IllegalArgumentException.class,
        () -> BinomialCoefficientModPrime.compute(5, 2, 1));
  }
}
