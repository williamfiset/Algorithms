
/**
 * An implementation of the Rabin-Karp algorithm using rolling positional hashing
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;
import java.math.*;

public class RabinKarp {

  // Our alphabet size is 95 because there are only 95 printable ASCII characters 
  // which are in the range between [32, 127). We also need to add +1 to our alphabet
  // size because we're going to redefine the first ASCII character (the space character)
  // to be 1 instead of 0 to avoid collisions where the string ' ' hashes to the 
  // same value as '   ' since 0*95^0 = 0*95^0 + 0*95^1 + 0*95^2
  private static final long ALPHABET_BASE = 95 + 1;
  private static final long[] ALPHABET = new long[127];
  private static final BigInteger BIG_ALPHA = new BigInteger(String.valueOf(ALPHABET_BASE));

  // More primes: 1009, 1013, 1019, 10007, 10009, 10037, 100003, 100019, 100043, 1000003, 1000033, 1000037,
  // 10000019, 10000079, 10000103, 100000007, 100000009, 100000023, 1000000007, 1000000009, 1000000021, 1000000033
  private static final long[] MODS = { 10_000_019, 10_000_079, 10_000_103 };
  private static final int N_HASHES = MODS.length;
  private static final long[] MOD_INVERSES = new long[N_HASHES];

  // Caches the values of the powers of ALPHABET_BASE for each mod value
  private static List <List <Long>> powers = new ArrayList<>();

  static {

    // Assign a mapping from the printable ASCII characters to the natural numbers
    for (int i = 32, n = 1; i < ALPHABET.length; i++, n++) ALPHABET[i] = n;

    for (int i = 0; i < N_HASHES; i++ ) {
      
      // Compute the modular inverse values
      java.math.BigInteger mod = new java.math.BigInteger(String.valueOf(MODS[i]));
      MOD_INVERSES[i] = BIG_ALPHA.modInverse(mod).longValue();

      List <Long> modPowers = new ArrayList<>();
      modPowers.add(1L); // ALPHABET_BASE ^ 0 = 1
      powers.add(modPowers);

    }

  }

  public static void main(String[] args) {

    String s = "dsf923dajiosdjfhg392fhh20f3hef23abaaabaghikababbbabaghikaabbabaghikababbabdsf923dajiosdjfhg392fhh20f3hef23aghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaadsf923dajiosdjfhg392fhh20f3hef23abaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabagdsf923dajiosdjfhg392fhh20f3hef23hikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghidsf923dajiosdjfhg392fhh20f3hef23kababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghikababbbabaghikaabbabaghikababbabaghikabaabaaabaghdsf923dajiosdjfhg392fhh20f3hef23ikababbbabaghikaabbabaghikababbabaghikabadsf923dajiosdjfhg392fhh20f3hef23";
    String p = "dsf923dajiosdjfhg392fhh20f3hef23";

    System.out.println(rabinKarp(s, p));
    List <Integer> lst = rabinKarpBackwards(s,p);
    Collections.sort(lst);
    System.out.println(lst);

  }

  public static List <Integer> rabinKarp(String text, String pattern) {

    final int PL = pattern.length(), TL = text.length();

    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(0, PL));

    expandPowers(PL);
    List <Integer> matches = new ArrayList<>();

    // System.out.println(powers);

    for (int i = PL-1;;) {

      if (Arrays.equals(patternHash, rollingHash)) { matches.add(i-PL+1); }
      if (++i == TL) return matches;

      char firstValue = text.charAt(i-PL);
      char lastValue  = text.charAt(i);

      // Update rolling hash
      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = removeLeft(rollingHash[j], firstValue, j, PL);
        rollingHash[j] = addRight(rollingHash[j], lastValue, j);
      }

    }

  }

  public static List <Integer> rabinKarpBackwards(String text, String pattern) {
    
    List <Integer> matches = new ArrayList<>();

    final int PL = pattern.length(), TL = text.length();

    long[] patternHash = computeHash(pattern);
    long[] rollingHash = computeHash(text.substring(TL-PL, TL));

    for (int i = TL-PL;;) {

      if (Arrays.equals(patternHash, rollingHash)) { matches.add(i); }
      if (--i < 0) return matches; 

      char firstValue = text.charAt(i);
      char lastValue  = text.charAt(i+PL);

      // Update rolling hash
      for (int j = 0; j < patternHash.length; j++) {
        rollingHash[j] = addLeft(rollingHash[j], firstValue, j, PL);
        rollingHash[j] = removeRight(rollingHash[j], lastValue, j); 
      }

    }

  }

  // This function adds a character to the end of the rolling hash
  private static long addRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash = (rollingHash * ALPHABET_BASE + ALPHABET[lastValue]) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }
  
  // This function adds a character to the beginning of the rolling hash
  private static long addLeft(long rollingHash, char firstValue, int modIndex, int len) {
    long alphabetBasePower = powers.get(modIndex).get(len);
    rollingHash = (ALPHABET[firstValue] * alphabetBasePower + rollingHash) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  // Given a rolling hash x_n*A^n + x_n-1*A^(n-1) + ... + x_2*A^2 + x_1*A^1 + x_0*A^0
  // where x_i is a string character value and 'A' is the alphabet size we
  // want to remove the first term 'x_n*A^n' from our rolling hash.
  //
  // firstValue - This is x_n, the first character of this string
  private static long removeLeft(long rollingHash, char firstValue, int modIndex, int len) {
    long alphabetBasePower = powers.get(modIndex).get(len-1);
    rollingHash = (rollingHash - ALPHABET[firstValue] * alphabetBasePower) % MODS[modIndex];
    return (rollingHash + MODS[modIndex]) % MODS[modIndex];
  }

  private static long removeRight(long rollingHash, char lastValue, int modIndex) {
    rollingHash = (((rollingHash-ALPHABET[lastValue])%MODS[modIndex])+MODS[modIndex]) % MODS[modIndex];
    return (rollingHash * MOD_INVERSES[modIndex]) % MODS[modIndex];
  }

  private static void expandPowers(int n) {
    for (int i = 0; i < N_HASHES; i++) {
      List <Long> modPowers = powers.get(i);
      int len = modPowers.size();
      long mod = MODS[i];
      for (int j = 0; j <= n - len + 1; j++ ) {
        modPowers.add( (modPowers.get(modPowers.size()-1) * ALPHABET_BASE) % mod );
      }
    }
  }

  public static long[] computeHash(String str) {

    long[] rollingHashes = new long[N_HASHES];
    for (int k = 0; k < N_HASHES; k++)
      for (int i = 0; i < str.length(); i++)
        rollingHashes[k] = addRight(rollingHashes[k], str.charAt(i), k);
    return rollingHashes;

  }
  
}

