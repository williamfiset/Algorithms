package com.williamfiset.algorithms.datastructures.suffixarray;

import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.util.Random;
import org.junit.*;

public class SuffixArrayTest {

  static final SecureRandom random = new SecureRandom();
  static final Random rand = new Random();

  static final int LOOPS = 1000;
  static final int TEST_SZ = 40;
  static final int NUM_NULLS = TEST_SZ / 5;
  static final int MAX_RAND_NUM = 250;

  String ASCII_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  @Before
  public void setup() {}

  @Test
  public void suffixArrayLength() {
    String str = "ABCDE";

    SuffixArray sa1 = new SuffixArraySlow(str);
    SuffixArray sa2 = new SuffixArrayMed(str);
    SuffixArray sa3 = new SuffixArrayFast(str);

    assertEquals(str.length(), sa1.getSa().length);
    assertEquals(str.length(), sa2.getSa().length);
    assertEquals(str.length(), sa3.getSa().length);
  }

  @Test
  public void lcsUniqueCharacters() {

    SuffixArray sa1 = new SuffixArraySlow(ASCII_LETTERS);
    SuffixArray sa2 = new SuffixArrayMed(ASCII_LETTERS);
    SuffixArray sa3 = new SuffixArrayFast(ASCII_LETTERS);

    SuffixArray[] suffixArrays = {sa1, sa2, sa3};

    for (SuffixArray sa : suffixArrays) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertEquals(0, sa.getLcpArray()[i]);
      }
    }
  }

  @Test
  public void increasingLCPTest() {

    String UNIQUE_CHARS = "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK";

    SuffixArray sa1 = new SuffixArraySlow(UNIQUE_CHARS);
    SuffixArray sa2 = new SuffixArrayMed(UNIQUE_CHARS);
    SuffixArray sa3 = new SuffixArrayFast(UNIQUE_CHARS);

    SuffixArray[] suffixArrays = {sa1, sa2, sa3};

    for (SuffixArray sa : suffixArrays) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertEquals(i, sa.getLcpArray()[i]);
      }
    }
  }

  @Test
  public void lcpTest1() {

    String text = "ABBABAABAA";
    int[] lcpValues = {0, 1, 2, 1, 4, 2, 0, 3, 2, 1};

    SuffixArray sa1 = new SuffixArraySlow(text);
    SuffixArray sa2 = new SuffixArrayMed(text);
    SuffixArray sa3 = new SuffixArrayFast(text);

    SuffixArray[] suffixArrays = {sa1, sa2, sa3};

    for (SuffixArray sa : suffixArrays) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertEquals(lcpValues[i], sa.getLcpArray()[i]);
      }
    }
  }

  @Test
  public void lcpTest2() {
    String text = "ABABABAABB";
    int[] lcpValues = {0, 1, 3, 5, 2, 0, 1, 2, 4, 1};

    SuffixArray sa1 = new SuffixArraySlow(text);
    SuffixArray sa2 = new SuffixArrayMed(text);
    SuffixArray sa3 = new SuffixArrayFast(text);

    SuffixArray[] suffixArrays = {sa1, sa2, sa3};

    for (SuffixArray sa : suffixArrays) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertEquals(lcpValues[i], sa.getLcpArray()[i]);
      }
    }
  }

  @Test
  public void saConstruction() {
    // Test inspired by LCS. Make sure constructed SAs are equal.
    // Use digits 0-9 to fake unique tokens
    String text = "BAAAAB0ABAAAAB1BABA2ABA3AAB4BBBB5BB";

    SuffixArray sa1 = new SuffixArraySlow(text);
    SuffixArray sa2 = new SuffixArrayMed(text);
    SuffixArray sa3 = new SuffixArrayFast(text);
    SuffixArray[] suffixArrays = {sa1, sa2, sa3};

    for (int i = 0; i < suffixArrays.length; i++) {
      for (int j = i + 1; j < suffixArrays.length; j++) {
        SuffixArray s1 = suffixArrays[i];
        SuffixArray s2 = suffixArrays[j];
        for (int k = 0; k < s1.getSa().length; k++) {
          assertEquals(s1.getSa()[k], s2.getSa()[k]);
        }
      }
    }
  }
}
