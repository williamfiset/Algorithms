/**
 * Generates the power set of a set, which is the set of all subsets.
 *
 * Two approaches are provided: an iterative method using binary representation of numbers, and a
 * recursive backtracking method. Both produce the same result.
 *
 * Time Complexity: O(n * 2^n)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.other;

import java.util.*;

public class PowerSet {

  /**
   * Generates the power set using binary representation. Each integer from 0 to 2^n - 1 represents
   * a subset, where bit i indicates whether element i is included.
   */
  public static <T> List<List<T>> powerSetBinary(List<T> set) {
    int n = set.size();
    if (n > 30)
      throw new IllegalArgumentException("Set too large: n=" + n + " (max 30)");
    int total = 1 << n;
    List<List<T>> result = new ArrayList<>(total);

    for (int mask = 0; mask < total; mask++) {
      List<T> subset = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        if ((mask & (1 << i)) != 0)
          subset.add(set.get(i));
      }
      result.add(subset);
    }
    return result;
  }

  /**
   * Generates the power set using recursive backtracking. At each element, branches into including
   * or excluding it.
   */
  public static <T> List<List<T>> powerSetRecursive(List<T> set) {
    List<List<T>> result = new ArrayList<>();
    recurse(0, set, new ArrayList<>(), result);
    return result;
  }

  private static <T> void recurse(int at, List<T> set, List<T> current, List<List<T>> result) {
    if (at == set.size()) {
      // Snapshot the current subset — must copy since 'current' is mutated during backtracking
      result.add(new ArrayList<>(current));
      return;
    }
    // Include set[at] and explore all subsets of the remaining elements
    current.add(set.get(at));
    recurse(at + 1, set, current, result);

    // Backtrack: undo the inclusion of set[at], then explore without it
    current.remove(current.size() - 1);
    recurse(at + 1, set, current, result);
  }
}
