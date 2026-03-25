/**
 * Computes the modular inverse of a number using the Extended Euclidean Algorithm.
 *
 * <p>The modular inverse of 'a' mod 'm' is a value x such that a*x ≡ 1 (mod m). It exists if and
 * only if gcd(a, m) = 1 (i.e. a and m are coprime).
 *
 * <p>Time: ~O(log(a + m))
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.math;

public class ModularInverse {

  /**
   * Returns the modular inverse of 'a' mod 'm', or null if it does not exist.
   *
   * @param a the value to invert.
   * @param m the modulus (must be positive).
   * @return the modular inverse, or null if gcd(a, m) != 1.
   * @throws ArithmeticException if m is not positive.
   */
  public static Long modInv(long a, long m) {
    if (m <= 0)
      throw new ArithmeticException("mod must be > 0");
    a = ((a % m) + m) % m;
    long[] v = egcd(a, m);
    if (v[0] != 1)
      return null;
    return ((v[1] % m) + m) % m;
  }

  // Returns [gcd(a, b), x, y] such that ax + by = gcd(a, b).
  private static long[] egcd(long a, long b) {
    if (b == 0)
      return new long[] {a, 1, 0};
    long[] v = egcd(b, a % b);
    long tmp = v[1] - v[2] * (a / b);
    v[1] = v[2];
    v[2] = tmp;
    return v;
  }

  public static void main(String[] args) {
    // 2*3 mod 5 = 1, so modInv(2, 5) = 3.
    System.out.println(modInv(2, 5));

    // gcd(4, 18) != 1, so no inverse exists.
    System.out.println(modInv(4, 18));
  }
}
