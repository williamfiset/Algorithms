/* 
 * This program solves a system of linear congruence equations using the Chinese Remainder Theorem (CRT).
 * 
 * The user is prompted to enter the number of congruence equations, followed by the coefficients (a) and moduli (m)
 * for each equation of the form: x ≡ a[i] (mod m[i]), where all moduli must be pairwise coprime.
 * 
 * The program checks if the moduli are pairwise coprime, computes the unique solution modulo the product of all moduli,
 * and prints the result.
 * 
 * Time Complexity: O(k × log²(n)), where k is the number of equations and n is the product of all moduli.
 * 
 */

package com.williamfiset.algorithms.math;

import java.util.Scanner;
import java.math.BigInteger;

public class ChineseRemainderTheorem {
  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);

    System.out.print("Enter the number of congruence equations: ");

    int k = scanner.nextInt();

    // Ensure there are at least two equations
    if (k < 2) {
      System.out.println("\nThe number of congruence equations must be at least 2.");
      scanner.close();
      return;
    }

    BigInteger a[] = new BigInteger[k];
    BigInteger m[] = new BigInteger[k];

    System.out.println("Enter the coefficient values (a): ");

    for (int i = 0; i < k; i++) {

      System.out.print("a[" + (i + 1) + "] = ");

      a[i] = scanner.nextBigInteger();

    }

    System.out.println("Enter the moduli values (m): ");

    for (int i = 0; i < k; i++) {

      System.out.print("m[" + (i + 1) + "] = ");

      m[i] = scanner.nextBigInteger();

    }

    if (!arePairwiseCoprime(k, m)) {
      System.out.println("\nModuli are not pairwise coprime.");
      scanner.close();
      return;
    }

    BigInteger x = chineseRemainder(k, a, m);

    System.out.println("\nx = " + x);

    scanner.close();

  }

  /*
   * Computes the solution to the system of congruence equations using CRT.
   */
  public static BigInteger chineseRemainder(int k, BigInteger a[], BigInteger m[]) {

    BigInteger moduliProduct = BigInteger.ONE;
    BigInteger result = BigInteger.ZERO;

    BigInteger M[] = new BigInteger[k];
    BigInteger y[] = new BigInteger[k];

    // Compute the product of all moduli
    for (int i = 0; i < k; i++) {
      moduliProduct = moduliProduct.multiply(m[i]);
    }

    // Compute M[i] = product of all moduli divided by m[i]
    for (int i = 0; i < k; i++) {
      M[i] = moduliProduct.divide(m[i]);
    }

    // Compute modular inverse of M[i] modulo m[i]
    for (int i = 0; i < k; i++) {
      y[i] = M[i].modInverse(m[i]);
    }

    // Calculate the result using the CRT formula
    for (int i = 0; i < k; i++) {
      result = result.add(a[i].multiply(M[i]).multiply(y[i]));
    }

    result = result.mod(moduliProduct);

    return result;

  }

  /*
   * Checks if all moduli are pairwise coprime.
   */
  private static boolean arePairwiseCoprime(int k, BigInteger m[]) {

    for (int i = 0; i < k; i++) {
      for (int j = i + 1; j < k; j++) {
        if (!m[i].gcd(m[j]).equals(BigInteger.ONE)) {
          return false;
        }
      }
    }

    return true;

  }
}