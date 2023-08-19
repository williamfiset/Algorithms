/**
 * NOTE: An issue was found with this file when dealing with negative numbers when exponentiating!
 * See bug tracking progress on issue
 *
 * <p>An implementation of the modPow(a, n, mod) operation. This implementation is substantially
 * faster than Java's BigInteger class because it only uses primitive types.
 *
 * <p>Time Complexity O(lg(n))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

import java.math.BigInteger;

public class ModPow {

  // The values placed into the modPow function cannot be greater
  // than MAX or less than MIN otherwise long overflow will
  // happen when the values get squared (they will exceed 2^63-1)
  private static final long MAX = (long) Math.sqrt(Long.MAX_VALUE);
  private static final long MIN = -MAX;

  // Computes the Greatest Common Divisor (GCD) of a & b
  private static long gcd(long a, long b) {
    return b == 0 ? (a < 0 ? -a : a) : gcd(b, a % b);
  }

  // This function performs the extended euclidean algorithm on two numbers a and b.
  // The function returns the gcd(a,b) as well as the numbers x and y such
  // that ax + by = gcd(a,b). This calculation is important in number theory
  // and can be used for several things such as finding modular inverses and
  // solutions to linear Diophantine equations.
  private static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a < 0 ? -a : a, 1L, 0L};
    long[] v = egcd(b, a % b);
    long tmp = v[1] - v[2] * (a / b);
    v[1] = v[2];
    v[2] = tmp;
    return v;
  }

  // Returns the modular inverse of 'a' mod 'm'
  // Make sure m > 0 and 'a' & 'm' are relatively prime.
  private static long modInv(long a, long m) {

    a = ((a % m) + m) % m;

    long[] v = egcd(a, m);
    long x = v[1];

    return ((x % m) + m) % m;
  }

  // Computes a^n modulo mod very efficiently in O(lg(n)) time.
  // This function supports negative exponent values and a negative
  // base, however the modulus must be positive.
  public static long modPow(long a, long n, long mod) {

    if (mod <= 0) throw new ArithmeticException("mod must be > 0");
    if (a > MAX || mod > MAX)
      throw new IllegalArgumentException("Long overflow is upon you, mod or base is too high!");
    if (a < MIN || mod < MIN)
      throw new IllegalArgumentException("Long overflow is upon you, mod or base is too low!");

    // To handle negative exponents we can use the modular
    // inverse of a to our advantage since: a^-n mod m = (a^-1)^n mod m
    if (n < 0) {
      if (gcd(a, mod) != 1)
        throw new ArithmeticException("If n < 0 then must have gcd(a, mod) = 1");
      return modPow(modInv(a, mod), -n, mod);
    }

    if (n == 0L) return 1L;
    long p = a, r = 1L;

    for (long i = 0; n != 0; i++) {
      long mask = 1L << i;
      if ((n & mask) == mask) {
        r = (((r * p) % mod) + mod) % mod;
        n -= mask;
      }
      p = ((p * p) % mod + mod) % mod;
    }

    return ((r % mod) + mod) % mod;
  }

  // Example usage
  public static void main(String[] args) {

    BigInteger A, N, M, r1;
    long a, n, m, r2;

    A = BigInteger.valueOf(3);
    N = BigInteger.valueOf(4);
    M = BigInteger.valueOf(1000000);
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();

    // 3 ^ 4 mod 1000000
    r1 = A.modPow(N, M); // 81
    r2 = modPow(a, n, m); // 81
    System.out.println(r1 + " " + r2);

    A = BigInteger.valueOf(-45);
    N = BigInteger.valueOf(12345);
    M = BigInteger.valueOf(987654321);
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();

    // Finds -45 ^ 12345 mod 987654321
    r1 = A.modPow(N, M); // 323182557
    r2 = modPow(a, n, m); // 323182557
    System.out.println(r1 + " " + r2);

    A = BigInteger.valueOf(6);
    N = BigInteger.valueOf(-66);
    M = BigInteger.valueOf(101);
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();

    // Finds 6 ^ -66 mod 101
    r1 = A.modPow(N, M); // 84
    r2 = modPow(a, n, m); // 84
    System.out.println(r1 + " " + r2);

    A = BigInteger.valueOf(-5);
    N = BigInteger.valueOf(-7);
    M = BigInteger.valueOf(1009);
    a = A.longValue();
    n = N.longValue();
    m = M.longValue();

    // Finds -5 ^ -7 mod 1009
    r1 = A.modPow(N, M); // 675
    r2 = modPow(a, n, m); // 675
    System.out.println(r1 + " " + r2);

    for (int i = 0; i < 1000; i++) {
      A = BigInteger.valueOf(a);
      N = BigInteger.valueOf(n);
      M = BigInteger.valueOf(m);
      a = Math.random() < 0.5 ? randLong(MAX) : -randLong(MAX);
      n = randLong();
      m = randLong(MAX);
      try {
        r1 = A.modPow(N, M);
        r2 = modPow(a, n, m);
        if (r1.longValue() != r2)
          System.out.printf("Broke with: a = %d, n = %d, m = %d\n", a, n, m);
      } catch (ArithmeticException e) {
      }
    }
  }

  /* TESTING RELATED METHODS */

  static final java.util.Random RANDOM = new java.util.Random();

  // Returns long between [1, bound]
  public static long randLong(long bound) {
    return java.util.concurrent.ThreadLocalRandom.current().nextLong(1, bound + 1);
  }

  public static long randLong() {
    return RANDOM.nextLong();
  }
}
