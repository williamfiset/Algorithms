/**
 * Computes the binomial coefficient C(n, r) mod p using Fermat's Little Theorem.
 *
 * Given a prime p, the binomial coefficient C(n, r) = n! / (r! * (n-r)!) can be computed modulo p
 * by precomputing factorials mod p and using modular inverses for the denominator. Fermat's Little
 * Theorem gives a^(p-1) ≡ 1 (mod p) for prime p, so the modular inverse of x is x^(p-2) mod p.
 * Here we use the extended Euclidean algorithm via ModularInverse instead.
 *
 * Requires p to be prime so that modular inverses exist for all non-zero values mod p, and n < p
 * so that factorials are non-zero mod p.
 *
 * Time Complexity: O(n) for factorial precomputation, O(log(p)) for each modular inverse.
 *
 * @author Rohit Mazumder, mazumder.rohit7@gmail.com
 */
package com.williamfiset.algorithms.math;

public class BinomialCoefficientModPrime {

  /**
   * Computes C(n, r) mod p.
   *
   * @param n total items (must be >= 0 and < p).
   * @param r items to choose (must be >= 0 and <= n).
   * @param p a prime modulus.
   * @return C(n, r) mod p.
   * @throws IllegalArgumentException if parameters are out of range.
   */
  public static long compute(int n, int r, int p) {
    if (n < 0 || r < 0 || r > n)
      throw new IllegalArgumentException("Requires 0 <= r <= n, got n=" + n + ", r=" + r);
    if (p <= 1)
      throw new IllegalArgumentException("Modulus p must be > 1, got p=" + p);

    if (r == 0 || r == n)
      return 1;

    long[] factorial = new long[n + 1];
    factorial[0] = 1;
    for (int i = 1; i <= n; i++)
      factorial[i] = factorial[i - 1] * i % p;

    return factorial[n]
        % p * ModularInverse.modInv(factorial[r], p)
        % p * ModularInverse.modInv(factorial[n - r], p)
        % p;
  }
}
