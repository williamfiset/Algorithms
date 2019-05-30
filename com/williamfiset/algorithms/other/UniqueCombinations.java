/**
 * This file shows you how to generate all the unique combinations of a set even though some
 * elements may be repeated. For example, if your set is {2, 2, 3, 3, 3} and you care only about
 * sets of size two (r = 2) then the unique sets are {{2,2}, {2,3}, {3,3}}.
 *
 * <p>Time Complexity: O( n choose r )
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.other;

import java.util.ArrayList;
import java.util.List;

public class UniqueCombinations {

  public static void combinations(int[] set, int r) {

    if (set == null) return;
    if (r < 0) return;

    // Sort the numbers so we can easily skip duplicates.
    java.util.Arrays.sort(set);

    boolean[] used = new boolean[set.length];
    combinations(0, r, used, set);
  }

  private static void combinations(int at, int r, boolean[] used, int[] set) {

    final int n = set.length;

    // We select 'r' elements so we found a valid subset!
    if (r == 0) {

      List<Integer> subset = new ArrayList<>(r);
      for (int i = 0; i < n; i++) if (used[i]) subset.add(set[i]);
      System.out.println(subset);

    } else {
      for (int i = at; i < n; i++) {

        // Since the elements are sorted we can skip duplicate
        // elements to ensure the uniqueness of our output.
        if (i > at && set[i - 1] == set[i]) continue;

        used[i] = true;
        combinations(i + 1, r - 1, used, set);
        used[i] = false;
      }
    }
  }

  public static void main(String[] args) {

    // Example #1
    int r = 2;
    int[] set = {2, 3, 3, 2, 3};
    combinations(set, r);
    // Prints:
    // [2, 2]
    // [2, 3]
    // [3, 3]

    r = 3;
    set = new int[] {1, 2, 2, 2, 3, 3, 4, 4};
    combinations(set, r);
    // Prints:
    // [1, 2, 2]
    // [1, 2, 3]
    // [1, 2, 4]
    // [1, 3, 3]
    // [1, 3, 4]
    // [1, 4, 4]
    // [2, 2, 2]
    // [2, 2, 3]
    // [2, 2, 4]
    // [2, 3, 3]
    // [2, 3, 4]
    // [2, 4, 4]
    // [3, 3, 4]
    // [3, 4, 4]
  }
}
