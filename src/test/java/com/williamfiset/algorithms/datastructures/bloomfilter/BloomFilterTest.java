package com.williamfiset.algorithms.datastructures.bloomfilter;

import static com.google.common.truth.Truth.assertThat;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.*;

public class BloomFilterTest {

  static final SecureRandom random = new SecureRandom();
  static final Random rand = new Random();

  static final int MIN_RAND_NUM = -1000;
  static final int MAX_RAND_NUM = +1000;

  static final int TEST_SZ = 1000;
  static final int LOOPS = 1000;

  @BeforeEach
  public void setup() {}

  @Test
  public void testStringSetAllSubsets() {

    final String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringSet set = new StringSet(s.length());
    set.addAllSubstrings(s);

    for (int i = 0; i < s.length(); i++) {
      for (int j = i + 1; j < s.length(); j++) {
        String sub = s.substring(i, j + 1);
        assertThat(set.contains(sub)).isTrue();
      }
    }
  }

  @Test
  public void testStringSetAllSubsetsFailure() {

    int[] smallPrimes = {113, 107};
    boolean collisionHappened = false;
    final String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringSet set = new StringSet(smallPrimes, s.length());

    // Add a few substrings to the set
    int len = 26;
    for (int i = 0; i < len; i++) {
      for (int j = i + 1; j < len; j++) {
        String sub = s.substring(i, j + 1);
        set.add(sub);
      }
    }

    // Check for strings that shouldn't be in the set
    // there should be plenty of collisions
    len = s.length();
    for (int i = 27; i < len; i++) {
      for (int j = i + 1; j < len; j++) {
        String sub = s.substring(i, j + 1);
        if (set.contains(sub)) collisionHappened = true;
      }
    }

    // The probablity of a collision should be really high
    // because we're using small prime numbers
    assertThat(collisionHappened).isTrue();
  }

  @Test
  public void containsTests() {

    for (int sz = 1; sz <= 10; sz++) {

      for (int loops = 5; loops <= 50; loops += 5) {

        StringSet set = new StringSet(sz);
        Set<String> javaset = new HashSet<>();

        for (int l = 0; l < loops; l++) {
          String randStr = randomString(sz);
          javaset.add(randStr);
          set.add(randStr);
        }

        for (String s : javaset) assertThat(set.contains(s)).isTrue();

        // Check that strings that aren't in the string set actually aren't
        // in the set, the probablity should be low enough that a false positive
        // should not happen.
        for (int l = 0; l < 100; l++) {
          String randStr = randomString(sz);
          if (!randStr.contains(randStr)) {
            assertThat(set.contains(randStr)).isFalse();
          }
        }
      }
    }
  }

  static int randNum(int min, int max) {
    int range = max - min + 1;
    return rand.nextInt(range) + min;
  }

  static final String AB =
      " )(*&^%$#@!0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  static String randomString(int len) {
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) sb.append(AB.charAt(rand.nextInt(AB.length())));
    return sb.toString();
  }
}
