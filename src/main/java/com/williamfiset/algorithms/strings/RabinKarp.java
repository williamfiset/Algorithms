/**
 * An implementation of the Rabin-Karp algorithm using rolling positional hashing.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.strings;

import java.math.*;
import java.util.*;

public class RabinKarp {

  // Our alphabet size is 95 because there are only 95 printable ASCII characters
  // which are in the range between [32, 127). We also need to add +1 to our alphabet
  // size because we're going to redefine the first ASCII character (the space character)
  // to be 1 instead of 0 to avoid collisions where the string ' ' hashes to the
  // same value as '   ' since 0*95^0 = 0*95^0 + 0*95^1 + 0*95^2
  private static final long ALPHABET_BASE = 95 + 1;
  private static final long[] ALPHABET = new long[127];
  private static final BigInteger BIG_ALPHA = new BigInteger(String.valueOf(ALPHABET_BASE));

  // More primes: 1009, 1013, 1019, 10007, 10009, 10037, 100003, 100019, 100043, 1000003, 1000033,
  // 1000037,
  // 10000019, 10000079, 10000103, 100000007, 100000009, 100000023, 1000000007, 1000000009,
  // 1000000021, 1000000033
  private static final long[] MODS = {10_000_019, 10_000_079, 10_000_103};
  private static final int N_HASHES = MODS.length;
  private static final BigInteger[] BIG_MODS = new BigInteger[N_HASHES];
  private static final long[] MOD_INVERSES = new long[N_HASHES];

  static {

    // Assign a mapping from the printable ASCII characters to the natural numbers
    for (int i = 32, n = 1; i < ALPHABET.length; i++, n++) ALPHABET[i] = n;

    // Compute modular inverses for chosen mod values
    for (int i = 0; i < N_HASHES; i++) {
      java.math.BigInteger mod = new java.math.BigInteger(String.valueOf(MODS[i]));
      MOD_INVERSES[i] = BIG_ALPHA.modInverse(mod).longValue();
      BIG_MODS[i] = mod;
    }
  }

  // Example usage of the Rabin-Karp string matching algorithm
  public static void main(String[] args) {

    String str = "P@TTerNabcdefP@TTerNP@TTerNabcdefabcdefabcdefabcdefP@TTerN";
    String pat = "P@TTerN";

    System.out.println(rabinKarp(str, pat)); // [0, 13, 20, 51]

    // Pattern is a substring of the text multiple times
    str = "ababababa";
    pat = "a";

    System.out.println(rabinKarp(str, pat)); // [0, 2, 4, 6, 8]

    // String is smaller than pattern
    str = "123";
    pat = "123456";

    System.out.println(rabinKarp(str, pat)); // []

    // String and pattern are the same
    str = "123456";
    pat = "123456";

    System.out.println(rabinKarp(str, pat)); // [0]
  }

  // Given a text and a pattern Rabin-Karp finds all occurrences
  // of the pattern in the text in O(n+m) time.
  public static List<Integer> rabinKarp(String text, String pattern) {

    List<Integer> matches = new ArrayList<>();
    if (text == null || pattern == null) return matches;

    // Find pattern length (PL) and text length (TL)
    final int PL = pattern.length(), TL = text.length();
    if (PL > TL) return matches;

    // Compute the initial hash values
    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(0, PL));

    final BigInteger BIG_PL = new BigInteger(String.valueOf(PL));
    final long[] POWERS = new long[N_HASHES];
    for (int i = 0; i < N_HASHES; i++)
      POWERS[i] = BIG_ALPHA.modPow(BIG_PL, BIG_MODS[i]).longValue();

    for (int i = PL - 1; ; ) {

      if (Arrays.equals(patternHash, rollingHash)) {
        matches.add(i - PL + 1);
      }
      if (++i == TL) return matches;

      char firstValue = text.charAt(i - PL);
      char lastValue = text.charAt(i);

      // Update rolling hash
      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = addRight(rollingHash[j], lastValue, j);
        rollingHash[j] = removeLeft(rollingHash[j], POWERS[j], firstValue, j);
      }
    }
  }

  // This method performs Rabin-Karp backwards by starting at the end and
  // doing the rolling hashes in the other direction. This method has no true
  // advantage over the other rabinKarp method, it is simply for proof of concept.
  public static List<Integer> rabinKarpBackwards(String text, String pattern) {

    List<Integer> matches = new ArrayList<>();
    if (text == null || pattern == null) return matches;

    // Find pattern length (PL) and text length (TL)
    final int PL = pattern.length(), TL = text.length();
    if (PL > TL) return matches;

    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(TL - PL, TL));

    final BigInteger BIG_PL = new BigInteger(String.valueOf(PL));
    final long[] POWERS = new long[N_HASHES];
    for (int i = 0; i < N_HASHES; i++)
      POWERS[i] = BIG_ALPHA.modPow(BIG_PL, BIG_MODS[i]).longValue();

    for (int i = TL - PL; ; ) {

      if (Arrays.equals(patternHash, rollingHash)) {
        matches.add(i);
      }
      if (--i < 0) return matches;

      char firstValue = text.charAt(i);
      char lastValue = text.charAt(i + PL);

      // Update rolling hash
      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = addLeft(rollingHash[j], POWERS[j], firstValue, j);
        rollingHash[j] = removeRight(rollingHash[j], lastValue, j);
      }
    }
  }

  // This function adds a character to the end of the rolling hash
  private static long addRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash = (rollingHash * ALPHABET_BASE + ALPHABET[lastValue]) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  // Given a rolling hash x_n*A^n + x_n-1*A^(n-1) + ... + x_2*A^2 + x_1*A^1 + x_0*A^0
  // where x_i is a character value and 'A' is the alphabet size this method removes
  // the x_0*A^0 term and divides by A (multiplies by modular inverse)
  private static long removeRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash =
        (((rollingHash - ALPHABET[lastValue]) % MODS[modIndex]) + MODS[modIndex]) % MODS[modIndex];
    return (rollingHash * MOD_INVERSES[modIndex]) % MODS[modIndex];
  }

  // Given a rolling hash x_n*A^n + x_n-1*A^(n-1) + ... + x_2*A^2 + x_1*A^1 + x_0*A^0
  // where x_i is a character value and 'A' is the alphabet size this method adds
  // an additional term 'x_n+1*A^(n+1)' to the rolling hash.
  //
  // firstValue - This is x_n+1, the first character of this string
  // alphabetBasePower - A^(n+1)
  private static long addLeft(
      long rollingHash, long alphabetBasePower, char firstValue, int modIndex) {
    rollingHash = (ALPHABET[firstValue] * alphabetBasePower + rollingHash) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  // Given a rolling hash x_n*A^n + x_n-1*A^(n-1) + ... + x_2*A^2 + x_1*A^1 + x_0*A^0
  // where x_i is a character value and 'A' is the alphabet size this method removes
  // the first term 'x_n*A^n' from the rolling hash.
  //
  // firstValue - This is x_n, the first character of this string
  // alphabetBasePower - A^n
  private static long removeLeft(
      long rollingHash, long alphabetBasePower, char firstValue, int modIndex) {
    rollingHash = (rollingHash - ALPHABET[firstValue] * alphabetBasePower) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  // Computes the hashes for a particular string
  public static long[] computeHash(String str) {

    long[] rollingHashes = new long[N_HASHES];
    for (int k = 0; k < N_HASHES; k++)
      for (int i = 0; i < str.length(); i++)
        rollingHashes[k] = addRight(rollingHashes[k], str.charAt(i), k);
    return rollingHashes;
  }
}
