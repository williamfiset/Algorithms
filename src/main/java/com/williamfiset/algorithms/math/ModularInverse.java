/** Time Complexity ~O(log(a + b)) */
package com.williamfiset.algorithms.math;

public class ModularInverse {

  // This function performs the extended euclidean algorithm on two numbers a and b.
  // The function returns the gcd(a,b) as well as the numbers x and y such
  // that ax + by = gcd(a,b). This calculation is important in number theory
  // and can be used for several things such as finding modular inverses and
  // solutions to linear Diophantine equations.
  private static long[] egcd(long a, long b) {
    if (b == 0) return new long[] {a, 1L, 0L};
    long[] v = egcd(b, a % b);
    long tmp = v[1] - v[2] * (a / b);
    v[1] = v[2];
    v[2] = tmp;
    return v;
  }

  // Returns the modular inverse of 'a' mod 'm' if it exists.
  // Make sure m > 0 and 'a' & 'm' are relatively prime.
  public static Long modInv(long a, long m) {

    if (m <= 0) throw new ArithmeticException("mod must be > 0");

    // Avoid a being negative
    a = ((a % m) + m) % m;

    long[] v = egcd(a, m);
    long gcd = v[0];
    long x = v[1];

    if (gcd != 1) return null;
    return ((x + m) % m) % m;
  }

  public static void main(String[] args) {

    // Prints 3 since 2*3 mod 5 = 1
    System.out.println(modInv(2, 5));

    // Prints null because there is no
    // modular inverse such that 4*x mod 18 = 1
    System.out.println(modInv(4, 18));
  }
}
