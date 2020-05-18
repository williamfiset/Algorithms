/**
 * An implementation of Manacher's algorithm which can be used to find/count palindromic strings in
 * linear time. In particular, it finds the length of the maximal palindrome centered at each index.
 */
package com.williamfiset.algorithms.strings;

public class ManachersAlgorithm {

  // Manacher's algorithm finds the length of the longest palindrome
  // centered at a specific index. Since even length palindromes have
  // a center in between two characters we expand the string to insert
  // those centers, for example "abba" becomes "^#a#b#b#a#$" where the
  // '#' sign represents the center of an even length string and '^' & '$'
  // are the front and the back of the string respectively. The output
  // of this function gives the diameter of each palindrome centered
  // at each character in this expanded string, for instance:
  // manachers("abba") -> [0, 0, 1, 0, 1, 4, 1, 0, 1, 0, 0]
  public static int[] manachers(char[] str) {
    char[] arr = preProcess(str);
    int n = arr.length, c = 0, r = 0;
    int[] p = new int[n];
    for (int i = 1; i < n - 1; i++) {
      int invI = 2 * c - i;
      p[i] = r > i ? Math.min(r - i, p[invI]) : 0;
      while (arr[i + 1 + p[i]] == arr[i - 1 - p[i]]) p[i]++;
      if (i + p[i] > r) {
        c = i;
        r = i + p[i];
      }
    }
    return p;
  }

  // Pre-process the string by injecting separator characters.
  // We do this to account for even length palindromes, so we can
  // assign them a unique center, for example: "abba" -> "^#a#b#b#a#$"
  private static char[] preProcess(char[] str) {
    char[] arr = new char[str.length * 2 + 3];
    arr[0] = '^';
    for (int i = 0; i < str.length; i++) {
      arr[i * 2 + 1] = '#';
      arr[i * 2 + 2] = str[i];
    }
    arr[arr.length - 2] = '#';
    arr[arr.length - 1] = '$';
    return arr;
  }

  // This method finds all the palindrome substrings found inside
  // a string it uses Manacher's algorithm to find the diameter
  // of each palindrome centered at each position.
  public static java.util.TreeSet<String> findPalindromeSubstrings(String str) {
    char[] S = str.toCharArray();
    int[] centers = manachers(S);
    java.util.TreeSet<String> palindromes = new java.util.TreeSet<>();

    for (int i = 0; i < centers.length; i++) {
      int diameter = centers[i];
      if (diameter >= 1) {

        // Even palindrome substring
        if (i % 2 == 1) {
          while (diameter > 1) {
            int index = (i - 1) / 2 - diameter / 2;
            palindromes.add(new String(S, index, diameter));
            diameter -= 2;
          }
          // Odd palindrome substring
        } else {
          while (diameter >= 1) {
            int index = (i - 2) / 2 - (diameter - 1) / 2;
            palindromes.add(new String(S, index, diameter));
            diameter -= 2;
          }
        }
      }
    }
    return palindromes;
  }

  public static void main(String[] args) {
    String s = "abbaabba";

    // Outputs: [a, aa, abba, abbaabba, b, baab, bb, bbaabb]
    System.out.println(findPalindromeSubstrings(s));
  }
}
