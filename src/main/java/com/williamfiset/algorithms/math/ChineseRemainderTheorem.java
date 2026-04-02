/**
 * Solve a set of congruence equations using the Chinese Remainder Theorem.
 *
 * <p>{@link #eliminateCoefficient(long, long, long)} reduces cx ≡ a (mod m) to x ≡ a' (mod m'),
 * removing the coefficient. Returns null if unsolvable.
 *
 * <p>{@link #reduce(long[], long[])} rewrites a system so that moduli become pairwise coprime,
 * enabling CRT. The number of equations may change. Returns null if the system is inconsistent.
 *
 * <p>{@link #crt(long[], long[])} is the core CRT step. It assumes all moduli are pairwise coprime
 * and returns x ≡ a' (mod M) where M is the product of all moduli.
 *
 * @author Micah Stairs
 */
package com.williamfiset.algorithms.math;

import java.util.*;

public class ChineseRemainderTheorem {

  /**
   * Reduces cx ≡ a (mod m) to x ≡ a' (mod m').
   *
   * @return {a', m'} or null if unsolvable.
   */
  public static long[] eliminateCoefficient(long c, long a, long m) {
    long d = egcd(c, m)[0];

    if (a % d != 0)
      return null;

    c /= d;
    a /= d;
    m /= d;

    long inv = egcd(c, m)[1];
    m = Math.abs(m);
    a = (((a * inv) % m) + m) % m;

    return new long[] {a, m};
  }

  /**
   * Reduces a system x ≡ a[i] (mod m[i]) to an equivalent system with pairwise coprime moduli.
   *
   * @return {a[], m[]} with coprime moduli, or null if the system is inconsistent.
   */
  public static long[][] reduce(long[] a, long[] m) {
    List<Long> aNew = new ArrayList<>();
    List<Long> mNew = new ArrayList<>();

    // Split each modulus into prime-power factors
    for (int i = 0; i < a.length; i++) {
      List<Long> factors = primeFactorization(m[i]);
      Collections.sort(factors);

      int j = 0;
      while (j < factors.size()) {
        long p = factors.get(j);
        long pk = p;
        j++;
        while (j < factors.size() && factors.get(j) == p) {
          pk *= p;
          j++;
        }
        aNew.add(a[i] % pk);
        mNew.add(pk);
      }
    }

    // Remove redundant equations and detect conflicts
    for (int i = 0; i < aNew.size(); i++) {
      for (int j = i + 1; j < aNew.size(); j++) {
        if (mNew.get(i) % mNew.get(j) == 0 || mNew.get(j) % mNew.get(i) == 0) {
          if (mNew.get(i) > mNew.get(j)) {
            if ((aNew.get(i) % mNew.get(j)) == aNew.get(j)) {
              aNew.remove(j);
              mNew.remove(j);
              j--;
              continue;
            } else
              return null;
          } else {
            if ((aNew.get(j) % mNew.get(i)) == aNew.get(i)) {
              aNew.remove(i);
              mNew.remove(i);
              i--;
              break;
            } else
              return null;
          }
        }
      }
    }

    long[][] res = new long[2][aNew.size()];
    for (int i = 0; i < aNew.size(); i++) {
      res[0][i] = aNew.get(i);
      res[1][i] = mNew.get(i);
    }
    return res;
  }

  /**
   * Solves x ≡ a[i] (mod m[i]) assuming all moduli are pairwise coprime.
   *
   * @return {x, M} where M is the product of all moduli.
   */
  public static long[] crt(long[] a, long[] m) {
    long M = 1;
    for (long mi : m)
      M *= mi;

    long x = 0;
    for (int i = 0; i < m.length; i++) {
      long Mi = M / m[i];
      long inv = egcd(Mi, m[i])[1];
      x += Mi * a[i] * inv;
      x = ((x % M) + M) % M;
    }

    return new long[] {x, M};
  }

  /** Extended Euclidean algorithm. Returns {gcd(a,b), x, y} where ax + by = gcd(a,b). */
  static long[] egcd(long a, long b) {
    if (b == 0)
      return new long[] {a, 1, 0};
    long[] ret = egcd(b, a % b);
    long tmp = ret[1] - ret[2] * (a / b);
    ret[1] = ret[2];
    ret[2] = tmp;
    return ret;
  }

  private static List<Long> primeFactorization(long n) {
    if (n <= 0)
      throw new IllegalArgumentException();
    List<Long> factors = new ArrayList<>();
    factor(n, factors);
    return factors;
  }

  private static void factor(long n, List<Long> factors) {
    if (n == 1)
      return;
    if (isPrime(n)) {
      factors.add(n);
      return;
    }
    long d = pollardRho(n);
    factor(d, factors);
    factor(n / d, factors);
  }

  private static long pollardRho(long n) {
    if (n % 2 == 0)
      return 2;
    long x = 2 + (long) (999999 * Math.random());
    long c = 2 + (long) (999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = mulMod(x, x, n) + c;
      if (x >= n)
        x -= n;
      y = mulMod(y, y, n) + c;
      if (y >= n)
        y -= n;
      y = mulMod(y, y, n) + c;
      if (y >= n)
        y -= n;
      d = gcd(Math.abs(x - y), n);
      if (d == n)
        break;
    }
    return d;
  }

  /**
   * Deterministic Miller-Rabin primality test, correct for all long values. Uses 12 witnesses
   * guaranteed correct for n < 3.317 × 10^24.
   */
  private static boolean isPrime(long n) {
    if (n < 2)
      return false;
    if (n < 4)
      return true;
    if (n % 2 == 0 || n % 3 == 0)
      return false;

    long d = n - 1;
    int r = Long.numberOfTrailingZeros(d);
    d >>= r;

    for (long a : new long[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
      if (a >= n)
        continue;
      long x = powMod(a, d, n);
      if (x == 1 || x == n - 1)
        continue;
      boolean composite = true;
      for (int i = 0; i < r - 1; i++) {
        x = mulMod(x, x, n);
        if (x == n - 1) {
          composite = false;
          break;
        }
      }
      if (composite)
        return false;
    }
    return true;
  }

  private static long powMod(long base, long exp, long mod) {
    long result = 1;
    base %= mod;
    while (exp > 0) {
      if ((exp & 1) == 1)
        result = mulMod(result, base, mod);
      exp >>= 1;
      base = mulMod(base, base, mod);
    }
    return result;
  }

  private static long mulMod(long a, long b, long mod) {
    return java.math.BigInteger.valueOf(a)
        .multiply(java.math.BigInteger.valueOf(b))
        .mod(java.math.BigInteger.valueOf(mod))
        .longValue();
  }

  private static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }
}
