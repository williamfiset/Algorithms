/**
 * Here we present two methods (recursive and iterative) of generating all the permutations of a
 * list of elements.
 *
 * <p>Time Complexity: O(n!)
 *
 * @author William Fiset, Micah Stairs
 */
package com.williamfiset.algorithms.other;

public class Permutations {

  /* RECURSIVE APPROACH */

  // Generates all the permutations of a sequence of objects
  public static void generatePermutations(Object[] sequence) {
    if (sequence == null) return;
    boolean[] used = new boolean[sequence.length];
    int[] picked = new int[sequence.length];
    permutations(0, used, picked, sequence);
  }

  // Recursive method to generate all the permutations of a sequence
  // at       -> Current element we're considering
  // used     -> The elements we have currently selected in our permutation
  // picked   -> The order of the indexes we have selected in our permutation
  // sequence -> The array we're generating permutations for
  private static void permutations(int at, boolean[] used, int[] picked, Object[] sequence) {

    final int N = sequence.length;

    // We reached the end, so we've found a valid permutation!
    if (at == N) {

      // Print permutation
      System.out.print("[ ");
      for (int i = 0; i < N; i++) System.out.print(sequence[picked[i]] + " ");
      System.out.println("]");

    } else {

      for (int i = 0; i < N; i++) {

        // We can only select elements once, so make sure we do
        // not select an element which has already been chosen
        if (!used[i]) {

          // Select this element and track in picked which
          // element was chosen for this permutations
          used[i] = true;
          picked[at] = i;
          permutations(at + 1, used, picked, sequence);

          // Backtrack (unselect element)
          used[i] = false;
        }
      }
    }
  }

  /* ITERATIVE APPROACH */

  // Generates the next ordered permutation in-place (skips repeated permutations).
  // Calling this when the array is already at the highest permutation returns false.
  // Recommended usage is to start with the smallest permutations and use a do while
  // loop to generate each successive permutations (see main for example).
  static <T extends Comparable<? super T>> boolean nextPermutation(T[] sequence) {
    int first = getFirst(sequence);
    if (first == -1) return false;
    int toSwap = sequence.length - 1;
    while (sequence[first].compareTo(sequence[toSwap]) >= 0) --toSwap;
    swap(sequence, first++, toSwap);
    toSwap = sequence.length - 1;
    while (first < toSwap) swap(sequence, first++, toSwap--);
    return true;
  }

  static <T extends Comparable<? super T>> int getFirst(T[] sequence) {
    for (int i = sequence.length - 2; i >= 0; --i)
      if (sequence[i].compareTo(sequence[i + 1]) < 0) return i;
    return -1;
  }

  static <T extends Comparable<? super T>> void swap(T[] sequence, int i, int j) {
    T tmp = sequence[i];
    sequence[i] = sequence[j];
    sequence[j] = tmp;
  }

  public static void main(String[] args) {

    Integer[] sequence = {1, 1, 2, 3};
    generatePermutations(sequence);
    // prints:
    // [ 1 1 2 3 ]
    // [ 1 1 3 2 ]
    // [ 1 2 1 3 ]
    // [ 1 2 3 1 ]
    // [ 1 3 1 2 ]
    // [ 1 3 2 1 ]
    // [ 1 1 2 3 ]
    // [ 1 1 3 2 ]
    // [ 1 2 1 3 ]
    // [ 1 2 3 1 ]
    // [ 1 3 1 2 ]
    // [ 1 3 2 1 ]
    // [ 2 1 1 3 ]
    // [ 2 1 3 1 ]
    // [ 2 1 1 3 ]
    // [ 2 1 3 1 ]
    // [ 2 3 1 1 ]
    // [ 2 3 1 1 ]
    // [ 3 1 1 2 ]
    // [ 3 1 2 1 ]
    // [ 3 1 1 2 ]
    // [ 3 1 2 1 ]
    // [ 3 2 1 1 ]
    // [ 3 2 1 1 ]

    String[] alpha = {"A", "B", "C", "D"};
    do {

      System.out.println(java.util.Arrays.toString(alpha));

      // Loop while alpha is not at its highest permutation ordering
    } while (nextPermutation(alpha));
    // prints:
    // [A, B, C, D]
    // [A, B, D, C]
    // [A, C, B, D]
    // [A, C, D, B]
    // [A, D, B, C]
    // [A, D, C, B]
    // [B, A, C, D]
    // [B, A, D, C]
    // [B, C, A, D]
    // [B, C, D, A]
    // [B, D, A, C]
    // [B, D, C, A]
    // [C, A, B, D]
    // [C, A, D, B]
    // [C, B, A, D]
    // [C, B, D, A]
    // [C, D, A, B]
    // [C, D, B, A]
    // [D, A, B, C]
    // [D, A, C, B]
    // [D, B, A, C]
    // [D, B, C, A]
    // [D, C, A, B]
    // [D, C, B, A]

  }
}
