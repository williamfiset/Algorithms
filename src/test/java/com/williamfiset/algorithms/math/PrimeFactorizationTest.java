package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;
import org.junit.jupiter.api.*;

public class PrimeFactorizationTest {

  private static void assertFactors(long n, Long... expected) {
    List<Long> factors = PrimeFactorization.primeFactorization(n);
    Collections.sort(factors);
    assertThat(factors).containsExactlyElementsIn(Arrays.asList(expected)).inOrder();
  }

  @Test
  public void testOne() {
    assertFactors(1);
  }

  @Test
  public void testSmallPrimes() {
    assertFactors(2, 2L);
    assertFactors(3, 3L);
    assertFactors(5, 5L);
    assertFactors(7, 7L);
    assertFactors(11, 11L);
    assertFactors(13, 13L);
  }

  @Test
  public void testPowersOfTwo() {
    assertFactors(4, 2L, 2L);
    assertFactors(8, 2L, 2L, 2L);
    assertFactors(1024, 2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L, 2L);
  }

  @Test
  public void testPrimePowers() {
    assertFactors(27, 3L, 3L, 3L);
    assertFactors(125, 5L, 5L, 5L);
    assertFactors(49, 7L, 7L);
  }

  @Test
  public void testComposites() {
    assertFactors(100, 2L, 2L, 5L, 5L);
    assertFactors(666, 2L, 3L, 3L, 37L);
    assertFactors(872342345, 5L, 7L, 7L, 19L, 67L, 2797L);
  }

  @Test
  public void testProductEqualsOriginal() {
    long[] values = {12, 360, 872342345, 1000000007, 239821585064027L};
    for (long n : values) {
      List<Long> factors = PrimeFactorization.primeFactorization(n);
      long product = 1;
      for (long f : factors)
        product *= f;
      assertThat(product).isEqualTo(n);
    }
  }

  @Test
  public void testLargePrime() {
    assertFactors(8763857775536878331L, 8763857775536878331L);
  }

  @Test
  public void testLargeSemiprime() {
    // 15485867 and 15486481 are both prime
    assertFactors(239821585064027L, 15485867L, 15486481L);
  }

  @Test
  public void testLargeComposite() {
    assertFactors(1000000007L * 999999937L, 999999937L, 1000000007L);
  }

  @Test
  public void testZeroThrows() {
    assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.primeFactorization(0));
  }

  @Test
  public void testNegativeThrows() {
    assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.primeFactorization(-5));
  }
}
