package com.williamfiset.algorithms.math;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PrimeFactorization {

  public static ArrayList<Long> primeFactorization(long n) {
    ArrayList<Long> factors = new ArrayList<>();
    if (n <= 0) throw new IllegalArgumentException();
    else if (n == 1) return factors;
    PriorityQueue<Long> divisorQueue = new PriorityQueue<>();
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
    long x = 2 + (long) (999999 * Math.random());
    long c = 2 + (long) (999999 * Math.random());
    long y = x;
    long d = 1;
    while (d == 1) {
      x = (x * x + c) % n;
      y = (y * y + c) % n;
      y = (y * y + c) % n;
      d = gcd(Math.abs(x - y), n);
      if (d == n) break;
    }
    return d;
  }

  private static long gcd(long a, long b) {
    return b == 0 ? a : gcd(b, a % b);
  }

  private static boolean isPrime(final long n) {
    if (n < 2) return false;
    if (n == 2 || n == 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    long limit = (long) Math.sqrt(n);
    for (long i = 5; i <= limit; i += 6) if (n % i == 0 || n % (i + 2) == 0) return false;
    return true;
  }

  public static void main(String[] args) {
    System.out.println(primeFactorization(7)); // [7]
    System.out.println(primeFactorization(100)); // [2,2,5,5]
    System.out.println(primeFactorization(666)); // [2,3,3,37]
    System.out.println(primeFactorization(872342345)); // [5, 7, 7, 67, 19, 2797]
  }
}
