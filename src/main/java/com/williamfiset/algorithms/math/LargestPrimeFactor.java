package com.williamfiset.algorithms.math;

/** get the maximum prime number for the given number */
public class LargestPrimeFactor {

  static long maxPrime(long val) {
    long max_prime = -1;
    while (val % 2 == 0) {
      max_prime = 2;
      val = 1;
    }
    for (int i = 3; i <= Math.sqrt(val); i += 2) {
      while (val % i == 0) {
        max_prime = i;
        val = val / i;
      }
    }
    if (val > 2) max_prime = val;
    return max_prime;
  }

  public static void main(String[] args) {
    System.out.println(maxPrime(600851475143L));
  }
}
