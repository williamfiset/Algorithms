/**
 *
 * $ gradle test --tests "javatests.com.williamfiset.algorithms.strings.LongestCommonSubstringTest" 
 *
 */

package javatests.com.williamfiset.algorithms.strings;

// TODO(williamfiset): Replace junit asserts with all Google truth assertions.
import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.strings.LongestCommonSubstring;

import org.junit.*;
import java.util.*;

public class LongestCommonSubstringTest {

  @Test
  public void k2Lcs() {
    int k = 2;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("TAAAAT");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void k3Lcs() {
    int k = 3;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("ATA");
    ans.add("AAT");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void k4Lcs() {
    int k = 4;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("AT");
    ans.add("TA");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void k5Lcs() {
    int k = 5;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("AT");
    ans.add("TA");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void k6Lcs() {
    int k = 6;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("T");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void k7Lcs() {
    int k = 7;
    String[] strs = { "TAAAAT", "ATAAAAT", "TATA", "ATA", "AAT", "TTTT", "TT" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("T");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);    
  }

  @Test
  public void noLongestCommonSubstringTest() {
    int k = 2;
    String[] strs = { "abcd", "efgh" };

    TreeSet<String> ans = new TreeSet<>();
    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void simple1() {
    int k = 2;
    String[] strs = { "abcde", "habcab", "ghabcdf" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("abcd");
    ans.add("habc");
  
    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);

  }

  @Test
  public void simple2() {
    int k = 3;
    String[] strs = { "AAGAAGC", "AGAAGT", "CGAAGC" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("GAAG");
  
    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void simple3() {
    int k = 2;
    String[] strs = { "AABC", "BCDC", "BCDE", "CDED", "CDCABC" };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("ABC");
    ans.add("BCD");
    ans.add("CDC");
    ans.add("CDE");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
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

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void simple5() {
    int k = 2;
    String[] strs = { "AABC", "BCDC", "BCDE", "CDED" };
    TreeSet<String> ans = new TreeSet<>();
    ans.add("BCD");
    ans.add("CDE");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void kValueTest() {
    int k = 5;
    String[] strs = { 
      "AAAAA", "AAAAA", "AAAAA", 
      "BB", "BB", "BB", "BB", 
      "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("CC");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void kValueTest2() {
    int k = 4;
    String[] strs = { 
      "AAAAA", "AAAAA", "AAAAA", 
      "BB", "BB", "BB", "BB", 
      "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("BB");
    ans.add("CC");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void kValueTest3() {
    int k = 3;
    String[] strs = { 
      "AAAAA", "AAAAA", "AAAAA", 
      "BB", "BB", "BB", "BB", 
      "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("AAAAA");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void kValueTest4() {
    int k = 2;
    String[] strs = {
      "AAAAA", "AAAAA", "AAAAA", 
      "BB", "BB", "BB", "BB", 
      "CC", "CC", "CC", "CC", "CC"
    };

    // The 'A's are not included because we need four of them.
    TreeSet<String> ans = new TreeSet<>();
    ans.add("AAAAA");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void smallStrings() {
    int k = 6;
    String[] strs = { 
      "A", "A", "A", "A", "A", "A",
      "B", "B", "B", "B", "B", "B",
      "C", "C", "C", "C", "C", "C",
      "D", "D", "D", "D", "D", "D",
      "E", "E", "E", "E", "E",
      "F", "F", "F", "F",
      "G", "G", "G",
      "H", "H",
      "I"
    };

    TreeSet<String> ans = new TreeSet<>();
    ans.add("A");
    ans.add("B");
    ans.add("C");
    ans.add("D");

    TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
    assertEquals(ans, lcss);
  }

  @Test
  public void testLargeAlphabet() {
    for (int k = 2; k <= 2500; k++ ) {
      String[] strs = new String[k];
      for (int i = 0; i < k; i++) strs[i] = "ABABAB";

      TreeSet<String> ans = new TreeSet<>();
      ans.add("ABABAB");

      TreeSet<String> lcss = LongestCommonSubstring.lcs(strs, k);
      assertEquals(ans, lcss);
    }
  }
}

