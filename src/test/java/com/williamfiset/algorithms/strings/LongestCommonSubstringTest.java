/**
 * Run like: $ gradle test --tests "com.williamfiset.algorithms.strings.LongestCommonSubstringTest"
 */
package com.williamfiset.algorithms.strings;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.strings.LongestCommonSubstring.LcsSolver;

import com.google.common.collect.ImmutableList;
import com.williamfiset.algorithms.utils.TestUtils;
import java.util.*;
import org.junit.*;

public class LongestCommonSubstringTest {

  private static class SlowLcsSolver {
    String[] strings;

    public SlowLcsSolver(String[] strings) {
      if (strings == null || strings.length < 2)
        throw new IllegalArgumentException("Invalid strings input to SlowLcsSolver.");
      this.strings = strings;
    }

    public TreeSet<String> getLongestCommonSubstrings(int k) {

      Set<String> allSubstrings = new HashSet<>();
      List<Set<String>> sets = new ArrayList<>();
      for (int h = 0; h < strings.length; h++) {
        String string = strings[h];
        Set<String> set = new HashSet<>();
        for (int i = 0; i < string.length(); i++) {
          for (int j = i + 1; j <= string.length(); j++) {
            String substring = string.substring(i, j);
            set.add(substring);
            allSubstrings.add(substring);
          }
        }
        sets.add(set);
      }

      TreeSet<String> ans = new TreeSet<>();
      for (String substring : allSubstrings) {
        int count = 0;
        for (Set<String> set : sets) {
          if (set.contains(substring)) {
            count++;
          }
        }
        if (count < k) continue;
        if (ans.size() == 0) {
          ans.add(substring);
        } else {
          String x = ans.last();
          if (substring.length() > x.length()) {
            ans.clear();
            ans.add(substring);
          } else if (substring.length() == x.length()) {
            ans.add(substring);
          }
        }
      }

      return ans;
    }
  }

  public void verifyMultipleKValues(String[] strings, Map<Integer, TreeSet<String>> answers) {
    for (int k : answers.keySet()) {
      TreeSet<String> expectedLcss = answers.get(k);

      LcsSolver solver = new LcsSolver(strings);
      TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
      assertThat(expectedLcss).isEqualTo(lcss);
    }
  }

  @Test
  public void multipleKValueTest1() {
    String[] strs = {"TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT"};
    Map<Integer, TreeSet<String>> answers = new HashMap<>();

    answers.put(2, new TreeSet<>(ImmutableList.of("TAAAAT")));
    answers.put(3, new TreeSet<>(ImmutableList.of("ATA", "AAT")));
    answers.put(4, new TreeSet<>(ImmutableList.of("AT", "TA")));
    answers.put(5, new TreeSet<>(ImmutableList.of("AT")));
    answers.put(6, new TreeSet<>(ImmutableList.of("T")));
    answers.put(7, new TreeSet<>(ImmutableList.of("T")));

    verifyMultipleKValues(strs, answers);
  }

  @Test
  public void multipleKValueTest2() {
    String[] strs = {"AABAABA", "BBAABA", "BAABA", "ABBABB", "BBA", "ABA"};
    Map<Integer, TreeSet<String>> answers = new HashMap<>();

    answers.put(2, new TreeSet<>(ImmutableList.of("BAABA")));
    answers.put(3, new TreeSet<>(ImmutableList.of("BAABA")));
    answers.put(4, new TreeSet<>(ImmutableList.of("ABA")));

    verifyMultipleKValues(strs, answers);
  }

  @Test
  public void multipleKValueTest3() {
    String[] strs = {"A", "CA", "EB", "CB", "D", "EDA"};
    Map<Integer, TreeSet<String>> answers = new HashMap<>();

    answers.put(2, new TreeSet<>(ImmutableList.of("A", "B", "C", "D", "E")));
    verifyMultipleKValues(strs, answers);
  }

  @Test
  public void multipleKValueTest4() {
    String[] strs = {"ABCBDAB", "BDCABA", "BADACB"};
    Map<Integer, TreeSet<String>> answers = new HashMap<>();

    answers.put(2, new TreeSet<>(ImmutableList.of("AB", "CB", "BD", "DA", "BA")));
    verifyMultipleKValues(strs, answers);
  }

  @Test
  public void multipleKValueTest5() {
    String[] strs = {"abcde", "f", "ghij", "kmlop", "qrs", "tu", "v", "wxyz"};
    Map<Integer, TreeSet<String>> answers = new HashMap<>();

    answers.put(2, new TreeSet<>());
    answers.put(3, new TreeSet<>());
    answers.put(4, new TreeSet<>());
    answers.put(5, new TreeSet<>());
    answers.put(6, new TreeSet<>());
    answers.put(7, new TreeSet<>());
    answers.put(8, new TreeSet<>());
    verifyMultipleKValues(strs, answers);
  }

  @Test
  public void noLongestCommonSubstringTest() {
    int k = 2;
    String[] strs = {"abcd", "efgh"};

    TreeSet<String> ans = new TreeSet<>();
    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);

    assertThat(ans.size()).isEqualTo(lcss.size());
  }

  @Test
  public void simple1() {
    int k = 2;
    String[] strs = {"abcde", "habcab", "ghabcdf"};

    TreeSet<String> ans = new TreeSet<>();
    ans.add("abcd");
    ans.add("habc");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void simple2() {
    int k = 3;
    String[] strs = {"AAGAAGC", "AGAAGT", "CGAAGC"};

    TreeSet<String> ans = new TreeSet<>();
    ans.add("GAAG");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void simple3() {
    int k = 2;
    String[] strs = {"AABC", "BCDC", "BCDE", "CDED", "CDCABC"};

    TreeSet<String> ans = new TreeSet<>();
    ans.add("ABC");
    ans.add("BCD");
    ans.add("CDC");
    ans.add("CDE");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void simple4() {
    int k = 4;
    String[] strs = {
      "XXXXXXX", "VVV",
      "XXXXXXX", "ZZZ",
      "XXXXXXX", "YYY",
      "XXXXXXX"
    };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("XXXXXXX");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void simple5() {
    int k = 2;
    String[] strs = {"AABC", "BCDC", "BCDE", "CDED"};
    TreeSet<String> ans = new TreeSet<>();
    ans.add("BCD");
    ans.add("CDE");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void kValueTest() {
    int k = 5;
    String[] strs = {
      "AAAAA", "AAAAA", "AAAAA", "BB", "BB", "BB", "BB", "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("CC");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void kValueTest2() {
    int k = 4;
    String[] strs = {
      "AAAAA", "AAAAA", "AAAAA", "BB", "BB", "BB", "BB", "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("BB");
    ans.add("CC");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void kValueTest3() {
    int k = 3;
    String[] strs = {
      "AAAAA", "AAAAA", "AAAAA", "BB", "BB", "BB", "BB", "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("AAAAA");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void kValueTest4() {
    int k = 2;
    String[] strs = {
      "AAAAA", "AAAAA", "AAAAA", "BB", "BB", "BB", "BB", "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("AAAAA");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void smallStrings() {
    int k = 6;
    String[] strs = {
      "A", "A", "A", "A", "A", "A", "B", "B", "B", "B", "B", "B", "C", "C", "C", "C", "C", "C", "D",
      "D", "D", "D", "D", "D", "E", "E", "E", "E", "E", "F", "F", "F", "F", "G", "G", "G", "H", "H",
      "I"
    };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("A");
    ans.add("B");
    ans.add("C");
    ans.add("D");

    LcsSolver solver = new LcsSolver(strs);
    TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
    assertThat(ans).isEqualTo(lcss);
  }

  @Test
  public void randomLcssWithBruteForceSolver1() {
    for (int len = 2; len < 20; len++) {
      String[] strings = createRandomStrings(len, 12, 20, 3);
      for (int k = 2; k <= len; k++) {
        LcsSolver solver = new LcsSolver(strings);
        TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);

        SlowLcsSolver slowSolver = new SlowLcsSolver(strings);
        TreeSet<String> lcss2 = slowSolver.getLongestCommonSubstrings(k);
        assertThat(lcss).isEqualTo(lcss2);
      }
    }
  }

  // This test makes sure the LCS behaves well with longer strings that have
  // lots of matchings.
  @Test
  public void randomLcssWithBruteForceSolver2() {
    for (int len = 2; len < 10; len++) {
      String[] strings = createRandomStrings(len, 150, 200, 2);
      for (int k = 2; k <= len; k++) {
        LcsSolver solver = new LcsSolver(strings);
        TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);

        SlowLcsSolver slowSolver = new SlowLcsSolver(strings);
        TreeSet<String> lcss2 = slowSolver.getLongestCommonSubstrings(k);
        assertThat(lcss).isEqualTo(lcss2);
      }
    }
  }

  @Test
  public void randomLcssWithBruteForceSolver3() {
    for (int len = 2; len < 10; len++) {
      String[] strings = createRandomStrings(len, 6, 10, 15);
      for (int k = 2; k <= len; k++) {
        LcsSolver solver = new LcsSolver(strings);
        TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);

        SlowLcsSolver slowSolver = new SlowLcsSolver(strings);
        TreeSet<String> lcss2 = slowSolver.getLongestCommonSubstrings(k);
        assertThat(lcss).isEqualTo(lcss2);
      }
    }
  }

  static String[] createRandomStrings(int numStrings, int minSz, int maxSz, int alphabetSize) {
    String[] strings = new String[numStrings];
    for (int i = 0; i < numStrings; i++) {
      strings[i] = createString(minSz, maxSz, alphabetSize);
    }
    return strings;
  }

  static String createString(int minSz, int maxSz, int alphabetSize) {
    int sz = TestUtils.randValue(minSz, maxSz + 1);
    char[] chrs = new char[sz];
    for (int i = 0; i < sz; i++) {
      chrs[i] = (char) ('A' + TestUtils.randValue(0, alphabetSize));
    }
    return new String(chrs);
  }

  // TODO(williamfiset): crank up the numbers once implementation is faster.
  @Test
  public void testLargeAlphabet() {
    for (int k = 2; k <= 10; k++) {
      String[] strs = new String[k];
      for (int i = 0; i < k; i++) strs[i] = "ABABAB";

      TreeSet<String> ans = new TreeSet<>();
      ans.add("ABABAB");

      LcsSolver solver = new LcsSolver(strs);
      TreeSet<String> lcss = solver.getLongestCommonSubstrings(k);
      assertThat(ans).isEqualTo(lcss);
    }
  }
}
