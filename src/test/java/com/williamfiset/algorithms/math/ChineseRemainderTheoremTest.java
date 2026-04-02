package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.*;

public class ChineseRemainderTheoremTest {

  // --- eliminateCoefficient tests ---

  @Test
  public void eliminateCoefficient_simple() {
    // 3x ≡ 6 (mod 9) → x ≡ 2 (mod 3)
    long[] result = ChineseRemainderTheorem.eliminateCoefficient(3, 6, 9);
    assertThat(result).isNotNull();
    assertThat(result[0]).isEqualTo(2);
    assertThat(result[1]).isEqualTo(3);
  }

  @Test
  public void eliminateCoefficient_coefficientOne() {
    // 1x ≡ 5 (mod 7) → x ≡ 5 (mod 7)
    long[] result = ChineseRemainderTheorem.eliminateCoefficient(1, 5, 7);
    assertThat(result).isNotNull();
    assertThat(result[0]).isEqualTo(5);
    assertThat(result[1]).isEqualTo(7);
  }

  @Test
  public void eliminateCoefficient_unsolvable() {
    // 2x ≡ 3 (mod 4) — no solution since gcd(2,4)=2 does not divide 3
    assertNull(ChineseRemainderTheorem.eliminateCoefficient(2, 3, 4));
  }

  @Test
  public void eliminateCoefficient_coprime() {
    // 3x ≡ 1 (mod 7) → x ≡ 5 (mod 7) since 3*5=15≡1 (mod 7)
    long[] result = ChineseRemainderTheorem.eliminateCoefficient(3, 1, 7);
    assertThat(result).isNotNull();
    assertThat(result[0]).isEqualTo(5);
    assertThat(result[1]).isEqualTo(7);
  }

  // --- crt tests ---

  @Test
  public void crt_classicExample() {
    // x ≡ 2 (mod 3), x ≡ 3 (mod 5), x ≡ 2 (mod 7) → x ≡ 23 (mod 105)
    long[] result = ChineseRemainderTheorem.crt(new long[] {2, 3, 2}, new long[] {3, 5, 7});
    assertThat(result[0]).isEqualTo(23);
    assertThat(result[1]).isEqualTo(105);
  }

  @Test
  public void crt_twoEquations() {
    // x ≡ 1 (mod 2), x ≡ 2 (mod 3) → x ≡ 5 (mod 6)
    long[] result = ChineseRemainderTheorem.crt(new long[] {1, 2}, new long[] {2, 3});
    assertThat(result[0]).isEqualTo(5);
    assertThat(result[1]).isEqualTo(6);
  }

  @Test
  public void crt_singleEquation() {
    long[] result = ChineseRemainderTheorem.crt(new long[] {3}, new long[] {7});
    assertThat(result[0]).isEqualTo(3);
    assertThat(result[1]).isEqualTo(7);
  }

  @Test
  public void crt_resultSatisfiesAllCongruences() {
    long[] a = {1, 2, 3};
    long[] m = {5, 7, 11};
    long[] result = ChineseRemainderTheorem.crt(a, m);
    for (int i = 0; i < a.length; i++)
      assertThat(result[0] % m[i]).isEqualTo(a[i]);
  }

  @Test
  public void crt_zeroRemainders() {
    // x ≡ 0 (mod 3), x ≡ 0 (mod 5) → x ≡ 0 (mod 15)
    long[] result = ChineseRemainderTheorem.crt(new long[] {0, 0}, new long[] {3, 5});
    assertThat(result[0]).isEqualTo(0);
    assertThat(result[1]).isEqualTo(15);
  }

  // --- reduce tests ---

  @Test
  public void reduce_alreadyCoprime() {
    long[][] result = ChineseRemainderTheorem.reduce(new long[] {1, 2}, new long[] {3, 5});
    assertThat(result).isNotNull();
    // Should preserve the equations since 3 and 5 are already coprime
    assertThat(result[0]).asList().containsExactly(1L, 2L);
    assertThat(result[1]).asList().containsExactly(3L, 5L);
  }

  @Test
  public void reduce_sharedPrimeFactor() {
    // x ≡ 1 (mod 6), x ≡ 3 (mod 10) — share factor 2
    // 6 = 2·3, 10 = 2·5 → split to mod {2,3,2,5}
    long[][] result = ChineseRemainderTheorem.reduce(new long[] {1, 3}, new long[] {6, 10});
    assertThat(result).isNotNull();
    // The reduced system should be solvable via CRT
    long[] crtResult = ChineseRemainderTheorem.crt(result[0], result[1]);
    // Verify solution satisfies original congruences
    assertThat(crtResult[0] % 6).isEqualTo(1);
    assertThat(crtResult[0] % 10).isEqualTo(3);
  }

  @Test
  public void reduce_inconsistent() {
    // x ≡ 1 (mod 4), x ≡ 2 (mod 8) — inconsistent since 2 mod 4 = 2 ≠ 1
    assertNull(ChineseRemainderTheorem.reduce(new long[] {1, 2}, new long[] {4, 8}));
  }

  @Test
  public void reduce_redundantEquation() {
    // x ≡ 1 (mod 2), x ≡ 1 (mod 4) — second subsumes first
    long[][] result = ChineseRemainderTheorem.reduce(new long[] {1, 1}, new long[] {2, 4});
    assertThat(result).isNotNull();
    long[] crtResult = ChineseRemainderTheorem.crt(result[0], result[1]);
    assertThat(crtResult[0] % 4).isEqualTo(1);
  }

  // --- egcd tests ---

  @Test
  public void egcd_basicProperties() {
    long[] result = ChineseRemainderTheorem.egcd(35, 15);
    assertThat(result[0]).isEqualTo(5); // gcd(35,15) = 5
    // Verify Bezout's identity: 35*x + 15*y = 5
    assertThat(35 * result[1] + 15 * result[2]).isEqualTo(5);
  }

  @Test
  public void egcd_coprime() {
    long[] result = ChineseRemainderTheorem.egcd(7, 11);
    assertThat(result[0]).isEqualTo(1);
    assertThat(7 * result[1] + 11 * result[2]).isEqualTo(1);
  }

  // --- Integration: reduce + crt ---

  @Test
  public void reduceAndCrt_fullPipeline() {
    // Solve x ≡ 2 (mod 12), x ≡ 8 (mod 10)
    // 12 = 4·3, 10 = 2·5 — share factor 2, consistent since 2 ≡ 0 (mod 2) and 8 ≡ 0 (mod 2)
    long[][] reduced = ChineseRemainderTheorem.reduce(new long[] {2, 8}, new long[] {12, 10});
    assertThat(reduced).isNotNull();
    long[] result = ChineseRemainderTheorem.crt(reduced[0], reduced[1]);
    assertThat(result[0] % 12).isEqualTo(2);
    assertThat(result[0] % 10).isEqualTo(8);
  }

  @Test
  public void reduceAndCrt_threeCoprime() {
    // Already coprime — reduce should pass through, CRT solves directly
    long[] a = {2, 3, 2};
    long[] m = {3, 5, 7};
    long[][] reduced = ChineseRemainderTheorem.reduce(a, m);
    assertThat(reduced).isNotNull();
    long[] result = ChineseRemainderTheorem.crt(reduced[0], reduced[1]);
    assertThat(result[0]).isEqualTo(23);
    assertThat(result[1]).isEqualTo(105);
  }
}
