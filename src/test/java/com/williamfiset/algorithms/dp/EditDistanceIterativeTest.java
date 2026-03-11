package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class EditDistanceIterativeTest {

  @Test
  public void testNullInputA() {
    assertThrows(
        IllegalArgumentException.class,
        () -> EditDistanceIterative.editDistance(null, "abc", 1, 1, 1));
  }

  @Test
  public void testNullInputB() {
    assertThrows(
        IllegalArgumentException.class,
        () -> EditDistanceIterative.editDistance("abc", null, 1, 1, 1));
  }

  @Test
  public void testIdenticalStrings() {
    assertThat(EditDistanceIterative.editDistance("abcdefg", "abcdefg", 10, 10, 10)).isEqualTo(0);
  }

  @Test
  public void testBothEmpty() {
    assertThat(EditDistanceIterative.editDistance("", "", 1, 1, 1)).isEqualTo(0);
  }

  @Test
  public void testEmptyToNonEmpty() {
    // Converting "" to "abc" requires 3 insertions at cost 5 each
    assertThat(EditDistanceIterative.editDistance("", "abc", 5, 1, 1)).isEqualTo(15);
  }

  @Test
  public void testNonEmptyToEmpty() {
    // Converting "abc" to "" requires 3 deletions at cost 4 each
    assertThat(EditDistanceIterative.editDistance("abc", "", 1, 4, 1)).isEqualTo(12);
  }

  @Test
  public void testInsertionsOnly() {
    // "aaa" -> "aaabbb" requires 3 insertions at cost 10
    assertThat(EditDistanceIterative.editDistance("aaa", "aaabbb", 10, 2, 3)).isEqualTo(30);
  }

  @Test
  public void testSubstitutionsAndInsertions() {
    // "1023" -> "10101010": 2 substitutions (cost 2) + 4 insertions (cost 5) = 24
    assertThat(EditDistanceIterative.editDistance("1023", "10101010", 5, 7, 2)).isEqualTo(24);
  }

  @Test
  public void testDeletionsAndSubstitution() {
    // "923456789" -> "12345": 1 substitution (cost 1) + 4 deletions (cost 4) = 17
    assertThat(EditDistanceIterative.editDistance("923456789", "12345", 2, 4, 1)).isEqualTo(17);
  }

  /** When substitution is expensive, insert+delete can be cheaper. */
  @Test
  public void testInsertDeleteCheaperThanSubstitute() {
    // "aaaaa" -> "aabaa": substituting costs 10, but insert 'b' (2) + delete 'a' (3) = 5
    assertThat(EditDistanceIterative.editDistance("aaaaa", "aabaa", 2, 3, 10)).isEqualTo(5);
  }

  @Test
  public void testSingleCharSubstitution() {
    assertThat(EditDistanceIterative.editDistance("a", "b", 1, 1, 1)).isEqualTo(1);
  }

  @Test
  public void testSingleCharInsertion() {
    assertThat(EditDistanceIterative.editDistance("a", "ab", 3, 1, 1)).isEqualTo(3);
  }

  @Test
  public void testSingleCharDeletion() {
    assertThat(EditDistanceIterative.editDistance("ab", "a", 1, 7, 1)).isEqualTo(7);
  }

  /** Verify iterative and recursive solvers agree on the same inputs. */
  @Test
  public void testMatchesRecursiveSolver() {
    String[][] pairs = {
      {"abcdefg", "abcdefg"},
      {"aaa", "aaabbb"},
      {"1023", "10101010"},
      {"923456789", "12345"},
      {"aaaaa", "aabaa"},
      {"kitten", "sitting"},
      {"", "hello"},
      {"world", ""},
    };
    int[][] costs = {{10, 10, 10}, {10, 2, 3}, {5, 7, 2}, {2, 4, 1}, {2, 3, 10}, {1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

    for (int k = 0; k < pairs.length; k++) {
      String a = pairs[k][0], b = pairs[k][1];
      int ins = costs[k][0], del = costs[k][1], sub = costs[k][2];

      int iterative = EditDistanceIterative.editDistance(a, b, ins, del, sub);
      int recursive = new EditDistanceRecursive(a, b, ins, del, sub).editDistance();
      assertThat(iterative).isEqualTo(recursive);
    }
  }
}
