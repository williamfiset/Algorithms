/**
 * This snippet multiplies 2 polynomials with possibly negative coefficients very efficiently using
 * the Fast Fourier Transform. NOTE: This code only works for polynomials with coefficients in the
 * range of a signed integer.
 *
 * <p>Time Complexity: O( nlogn )
 *
 * @author David Brink
 */
package com.williamfiset.algorithms.math;

public class FastFourierTransform {

  // p is a prime number set to be larger than 2^31-1
  private static long p = 4300210177L;

  // q is 2^64 mod p used to compute x*y mod p
  // Note: If x*y mod p is negative it is because 2^64
  // has been subtracted and so it must be added again.
  private static long q = 857728777;

  // A number that has order 2^20 modulo p
  private static long zeta = 3273;

  private static int exp = 20;
  private static long[] powers;

  static {
    powers = new long[(1 << exp) + 1];
    powers[0] = 1;
    for (int i = 1; i < powers.length; i++) powers[i] = mult(zeta, powers[i - 1]);
  }

  // Computes the polynomial product modulo p
  public static long[] multiply(long[] x, long[] y) {

    // If the coefficients are negative place them in the range of [0, p)
    for (int i = 0; i < x.length; i++) if (x[i] < 0) x[i] += p;
    for (int i = 0; i < y.length; i++) if (y[i] < 0) y[i] += p;

    int zLength = x.length + y.length - 1;
    int logN = 32 - Integer.numberOfLeadingZeros(zLength - 1);
    long[] xx = transform(x, logN, false);
    long[] yy = transform(y, logN, false);
    long[] zz = new long[1 << logN];
    for (int i = 0; i < zz.length; i++) zz[i] = mult(xx[i], yy[i]);
    long[] nZ = transform(zz, logN, true);
    long[] z = new long[zLength];
    long nInverse = p - ((p - 1) >>> logN);
    for (int i = 0; i < z.length; i++) {

      z[i] = mult(nInverse, nZ[i]);

      // Allow for negative coefficients. If you know the answer cannot be
      // greater than 2^31-1 subtract p to obtain the negative coefficient.
      if (z[i] >= Integer.MAX_VALUE) z[i] -= p;
    }
    return z;
  }

  private static long mult(long x, long y) {
    long z = x * y;
    if (z < 0) {
      z = z % p + q;
      return z < 0 ? z + p : z;
    }
    if (z < (1L << 56) && x > (1 << 28) && y > (1 << 28)) {
      z = z % p + q;
      return z < p ? z : z - p;
    }
    return z % p;
  }

  private static long[] transform(long[] v, int logN, boolean inverse) {
    int n = 1 << logN;
    long[] w = new long[n];
    for (int i = 0; i < v.length; i++) w[Integer.reverse(i) >>> 32 - logN] = v[i];
    for (int i = 0; i < logN; i++) {
      int jMax = 1 << i;
      int kStep = 2 << i;
      int index = 0;
      int step = 1 << exp - i - 1;
      if (inverse) {
        index = 1 << exp;
        step = -step;
      }
      for (int j = 0; j < jMax; j++) {
        long zeta = powers[index];
        index += step;
        for (int k = j; k < n; k += kStep) {
          int kk = jMax | k;
          long x = w[k];
          long y = mult(zeta, w[kk]);
          long z = x + y;
          w[k] = z < p ? z : z - p;
          z = x - y;
          w[kk] = z < 0 ? z + p : z;
        }
      }
    }
    return w;
  }

  /* Example usage */

  public static void main(String[] args) {

    // 1*x^0 + 5*x^1 + 3*x^2 + 2*x^3
    long[] polynomial1 = {1, 5, 3, 2};

    // 0*x^0 + 0*x^1 + 6*x^2 + 2*x^3 + 5*x^4
    long[] polynomial2 = {0, 0, 6, 2, 5};

    // Multiply the polynomials using the FFT algorithm
    long[] result = FastFourierTransform.multiply(polynomial1, polynomial2);

    // Prints [0, 0, 6, 32, 33, 43, 19, 10] or equivalently
    // 6*x^2 + 32*x^3 + 33*x^4 + 43*x^5 + 19*x^6 + 10*x^7
    System.out.println(java.util.Arrays.toString(result));
  }
}
