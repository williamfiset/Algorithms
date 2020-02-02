/**
 * This code snippet shows how to generate the powerset of a set which is the set of all subsets of
 * a set. There are two common ways of doing this which are to use the binary representation of
 * numbers on a computer or to do it recursively. Both methods are shown here, pick your flavor!
 *
 * <p>Time Complexity: O( 2^n )
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.other;

public class PowerSet {

  // Use the fact that numbers represented in binary can be
  // used to generate all the subsets of an array
  static void powerSetUsingBinary(int[] set) {

    final int N = set.length;
    final int MAX_VAL = 1 << N;

    for (int subset = 0; subset < MAX_VAL; subset++) {
      System.out.print("{ ");
      for (int i = 0; i < N; i++) {
        int mask = 1 << i;
        if ((subset & mask) == mask) System.out.print(set[i] + " ");
      }
      System.out.println("}");
    }
  }

  // Recursively generate the powerset (set of all subsets) of an array by maintaining
  // a boolean array used to indicate which element have been selected
  static void powerSetRecursive(int at, int[] set, boolean[] used) {

    if (at == set.length) {

      // Print found subset!
      System.out.print("{ ");
      for (int i = 0; i < set.length; i++) if (used[i]) System.out.print(set[i] + " ");
      System.out.println("}");

    } else {

      // Include this element
      used[at] = true;
      powerSetRecursive(at + 1, set, used);

      // Backtrack and don't include this element
      used[at] = false;
      powerSetRecursive(at + 1, set, used);
    }
  }

  public static void main(String[] args) {

    // Example usage:
    int[] set = {1, 2, 3};

    powerSetUsingBinary(set);
    // prints:
    // { }
    // { 1 }
    // { 2 }
    // { 1 2 }
    // { 3 }
    // { 1 3 }
    // { 2 3 }
    // { 1 2 3 }

    System.out.println();

    powerSetRecursive(0, set, new boolean[set.length]);
    // prints:
    // { 1 2 3 }
    // { 1 2 }
    // { 1 3 }
    // { 1 }
    // { 2 3 }
    // { 2 }
    // { 3 }
    // { }

  }
}
