/**
 * Computes Euler's totient function
 *
 * @author Steven & Felix Halim
 */
package com.williamfiset.algorithms.math;

public class EulerTotientFunctionWithSieve {

  // TODO(williamfiset): Refactor this class to accept a max value in the constructor.

  // Set MAX to be the largest value you
  // wish to compute the totient for.
  private static int MAX = 1000000;
  private static int[] PRIMES = sieve(MAX);

  // Returns the value of Euler's totient/phi function
  // which computes how many numbers are relativity
  // prime to n less than or equal to n
  public static int totient(int n) {

    if (n >= MAX - 1) throw new IllegalStateException("MAX not large enough!");
    int ans = n;

    for (int i = 1, p = PRIMES[0]; p * p <= n; i++) {

      if (n % p == 0) ans -= ans / p;
      while (n % p == 0) n /= p;
      p = PRIMES[i];
    }

    // Last factor
    if (n != 1) ans -= ans / n;
    return ans;
  }

  // Gets all primes up to, but NOT including limit (returned as a list of primes)
  private static int[] sieve(int limit) {

    if (limit <= 2) return new int[0];

    // Find an upper bound on the number of primes below our limit.
    // https://en.wikipedia.org/wiki/Prime-counting_function#Inequalities
    final int numPrimes = (int) (1.25506 * limit / Math.log((double) limit));
    int[] primes = new int[numPrimes];
    int index = 0;

    boolean[] isComposite = new boolean[limit];
    final int sqrtLimit = (int) Math.sqrt(limit);
    for (int i = 2; i <= sqrtLimit; i++) {
      if (!isComposite[i]) {
        primes[index++] = i;
        for (int j = i * i; j < limit; j += i) isComposite[j] = true;
      }
    }
    for (int i = sqrtLimit + 1; i < limit; i++) if (!isComposite[i]) primes[index++] = i;
    return java.util.Arrays.copyOf(primes, index);
  }

  public static void main(String[] args) {
    // Prints 8 because 1,2,4,7,8,11,13,14 are all
    // less than 15 and relatively prime with 15
    System.out.printf("phi(15) = %d\n", totient(15));

    System.out.println();

    for (int x = 1; x <= 11; x++) {
      System.out.printf("phi(%d) = %d\n", x, totient(x));
    }
  }
}
