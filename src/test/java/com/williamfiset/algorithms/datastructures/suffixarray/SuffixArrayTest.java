package com.williamfiset.algorithms.datastructures.suffixarray;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

public class SuffixArrayTest {

  static final String ASCII_LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  // Helper: create all 3 implementations for the same text
  private static SuffixArray[] allImplementations(String text) {
    return new SuffixArray[] {
      new SuffixArraySlow(text),
      new SuffixArrayMed(text),
      new SuffixArrayFast(text)
    };
  }

  @Test
  public void testNullTextThrows() {
    assertThrows(IllegalArgumentException.class, () -> new SuffixArraySlow((int[]) null));
    assertThrows(IllegalArgumentException.class, () -> new SuffixArrayMed((int[]) null));
    assertThrows(IllegalArgumentException.class, () -> new SuffixArrayFast((int[]) null));
  }

  @Test
  public void testSingleCharacter() {
    for (SuffixArray sa : allImplementations("A")) {
      assertThat(sa.getSa()).isEqualTo(new int[] {0});
      assertThat(sa.getLcpArray()).isEqualTo(new int[] {0});
    }
  }

  @Test
  public void testTwoCharactersSorted() {
    // "AB" -> suffixes: "AB"(0), "B"(1) -> sorted: "AB","B" -> sa=[0,1]
    for (SuffixArray sa : allImplementations("AB")) {
      assertThat(sa.getSa()).isEqualTo(new int[] {0, 1});
      assertThat(sa.getLcpArray()).isEqualTo(new int[] {0, 0});
    }
  }

  @Test
  public void testTwoCharactersReversed() {
    // "BA" -> suffixes: "BA"(0), "A"(1) -> sorted: "A","BA" -> sa=[1,0]
    for (SuffixArray sa : allImplementations("BA")) {
      assertThat(sa.getSa()).isEqualTo(new int[] {1, 0});
      assertThat(sa.getLcpArray()).isEqualTo(new int[] {0, 0});
    }
  }

  @Test
  public void testSuffixArrayLength() {
    String str = "ABCDE";
    for (SuffixArray sa : allImplementations(str)) {
      assertThat(sa.getSa().length).isEqualTo(str.length());
      assertThat(sa.getTextLength()).isEqualTo(str.length());
    }
  }

  @Test
  public void testLcpAllZerosForUniqueCharacters() {
    for (SuffixArray sa : allImplementations(ASCII_LETTERS)) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertThat(sa.getLcpArray()[i]).isEqualTo(0);
      }
    }
  }

  @Test
  public void testLcpIncreasingForRepeatedCharacter() {
    // All same character: LCP[i] = i since suffixes are "KKK...", "KK...", "K..."
    String repeated = "KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK";
    for (SuffixArray sa : allImplementations(repeated)) {
      for (int i = 0; i < sa.getSa().length; i++) {
        assertThat(sa.getLcpArray()[i]).isEqualTo(i);
      }
    }
  }

  @Test
  public void testLcpKnownValues1() {
    String text = "ABBABAABAA";
    int[] expected = {0, 1, 2, 1, 4, 2, 0, 3, 2, 1};
    for (SuffixArray sa : allImplementations(text)) {
      assertThat(sa.getLcpArray()).isEqualTo(expected);
    }
  }

  @Test
  public void testLcpKnownValues2() {
    String text = "ABABABAABB";
    int[] expected = {0, 1, 3, 5, 2, 0, 1, 2, 4, 1};
    for (SuffixArray sa : allImplementations(text)) {
      assertThat(sa.getLcpArray()).isEqualTo(expected);
    }
  }

  // Verify the suffix array actually produces lexicographically sorted suffixes
  @Test
  public void testSuffixesAreSorted() {
    String text = "ABBABAABAA";
    for (SuffixArray sa : allImplementations(text)) {
      int[] arr = sa.getSa();
      for (int i = 0; i < arr.length - 1; i++) {
        String s1 = text.substring(arr[i]);
        String s2 = text.substring(arr[i + 1]);
        assertThat(s1.compareTo(s2)).isLessThan(0);
      }
    }
  }

  @Test
  public void testConstructionConsistency() {
    // All 3 implementations must produce the same SA
    String text = "BAAAAB0ABAAAAB1BABA2ABA3AAB4BBBB5BB";
    SuffixArray[] impls = allImplementations(text);
    for (int i = 0; i < impls.length; i++) {
      for (int j = i + 1; j < impls.length; j++) {
        assertThat(impls[i].getSa()).isEqualTo(impls[j].getSa());
      }
    }
  }

  @Test
  public void testIntArrayConstructor() {
    // "CAB" as int array
    int[] text = {67, 65, 66};
    SuffixArray sa1 = new SuffixArraySlow(text);
    SuffixArray sa2 = new SuffixArrayMed(text);
    SuffixArray sa3 = new SuffixArrayFast(text);

    // Suffixes: "CAB"(0), "AB"(1), "B"(2) -> sorted: "AB","B","CAB" -> sa=[1,2,0]
    int[] expected = {1, 2, 0};
    assertThat(sa1.getSa()).isEqualTo(expected);
    assertThat(sa2.getSa()).isEqualTo(expected);
    assertThat(sa3.getSa()).isEqualTo(expected);
  }

  // Randomized cross-validation: all implementations must agree on random inputs
  @Test
  public void testRandomStringsAllImplementationsAgree() {
    Random rand = new Random(42);
    for (int loop = 0; loop < 200; loop++) {
      int len = 2 + rand.nextInt(20);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < len; i++) {
        sb.append((char) ('A' + rand.nextInt(5)));
      }
      String text = sb.toString();

      SuffixArray[] impls = allImplementations(text);

      // All SAs must match
      for (int i = 1; i < impls.length; i++) {
        assertThat(impls[i].getSa()).isEqualTo(impls[0].getSa());
        assertThat(impls[i].getLcpArray()).isEqualTo(impls[0].getLcpArray());
      }

      // Verify sorted order
      int[] sa = impls[0].getSa();
      for (int i = 0; i < sa.length - 1; i++) {
        String s1 = text.substring(sa[i]);
        String s2 = text.substring(sa[i + 1]);
        assertThat(s1.compareTo(s2)).isLessThan(0);
      }
    }
  }
}
