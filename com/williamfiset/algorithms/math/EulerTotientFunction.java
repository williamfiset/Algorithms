package com.williamfiset.algorithms.math;

import java.util.*;

public class EulerTotientFunction {

  public static long eulersTotient(long n) {
    for (long p : new HashSet<Long>(primeFactorization(n))) n -= (n / p);
    return n;
  }

  private static ArrayList<Long> primeFactorization(long n) {
    ArrayList<Long> factors = new ArrayList<Long>();
    if (n <= 0) throw new IllegalArgumentException();
    else if (n == 1) return factors;
    PriorityQueue<Long> divisorQueue = new PriorityQueue<Long>();
    divisorQueue.add(n);
    while (!divisorQueue.isEmpty()) {
      long divisor = divisorQueue.remove();
      if (isPrime(divisor)) {
        factors.add(divisor);
        continue;
      }
      long next_divisor = pollardRho(divisor);
      if (next_divisor == divisor) {
        divisorQueue.add(divisor);
      } else {
        divisorQueue.add(next_divisor);
        divisorQueue.add(divisor / next_divisor);
      }
    }
    return factors;
  }

  private static long pollardRho(long n) {
    if (n % 2 == 0) return 2;
    // Get a number in the range [2, 10^6]
    long x = 2 + (long) (999999 * Math.random());
    long c = 2 + (long) (999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = (x * x + c) % n;
      y = (y * y + c) % n;
      y = (y * y + c) % n;
      d = gcf(Math.abs(x - y), n);
      if (d == n) break;
    }
    return d;
  }

  private static long gcf(long a, long b) {
    return b == 0 ? a : gcf(b, a % b);
  }

  private static boolean isPrime(long n) {

    if (n < 2) return false;
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;

    int limit = (int) Math.sqrt(n);

    for (int i = 5; i <= limit; i += 6) if (n % i == 0 || n % (i + 2) == 0) return false;

    return true;
  }

  public static void main(String[] args) {

    // Prints 8 because 1,2,4,7,8,11,13,14 are all
    // less than 15 and relatively prime with 15
    System.out.printf("phi(15) = %d\n", eulersTotient(15));

    System.out.println();

    for (int x = 1; x <= 11; x++) {
      System.out.printf("phi(%d) = %d\n", x, eulersTotient(x));
    }
  }
}
