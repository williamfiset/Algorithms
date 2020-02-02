/**
 * This file contains an implementation of Booths algorithms which finds the lexicographically
 * smallest string rotation.
 */
package com.williamfiset.algorithms.strings;

public class BoothsAlgorithm {

  // Performs Booths algorithm returning the earliest index of the
  // lexicographically smallest string rotation. Note that comparisons
  // are done using ASCII values, so mixing lowercase and uppercase
  // letters may give you unexpected results, O(n)
  public static int leastCyclicRotation(String s) {
    s += s;
    int[] f = new int[s.length()];
    java.util.Arrays.fill(f, -1);
    int k = 0;
    for (int j = 1; j < s.length(); j++) {
      char sj = s.charAt(j);
      int i = f[j - k - 1];
      while (i != -1 && sj != s.charAt(k + i + 1)) {
        if (sj < s.charAt(k + i + 1)) k = j - i - 1;
        i = f[i];
      }
      if (sj != s.charAt(k + i + 1)) {
        if (sj < s.charAt(k)) k = j;
        f[j - k] = -1;
      } else f[j - k] = i + 1;
    }
    return k;
  }

  public static void main(String[] args) {

    String s = "abcde";
    int index = leastCyclicRotation(s);

    // Outputs 0 since the string is already in its least rotation
    System.out.println(index);

    s = "cdeab";
    index = leastCyclicRotation(s);

    // Outputs 3 since rotating the string 3 times to the left makes
    // the smallest rotation: "cdeab" -> "deabc" -> "eabcd" -> "abcde"
    System.out.println(index);
  }
}
