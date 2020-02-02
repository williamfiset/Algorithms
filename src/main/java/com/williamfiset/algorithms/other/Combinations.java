/**
 * Here we present two methods (recursive and iterative) of generating all the combinations of a set
 * by choosing only r of n elements.
 *
 * <p>Time Complexity: O( n choose r )
 *
 * @author William Fiset, Micah Stairs
 */
package com.williamfiset.algorithms.other;

public class Combinations {

  // This method finds all the combinations of size r in a set
  public static void combinationsChooseR(int[] set, int r) {

    if (r < 0) return;
    if (set == null) return;

    boolean[] used = new boolean[set.length];
    combinations(set, r, 0, used);
  }

  // To find all the combinations of size r we need to recurse until we have
  // selected r elements (aka r = 0), otherwise if r != 0 then we need to select
  // an element which is found after the position of our last selected element
  private static void combinations(int[] set, int r, int at, boolean[] used) {

    final int N = set.length;

    // Return early if there are more elements left to select than what is available.
    int elementsLeftToPick = N - at;
    if (elementsLeftToPick < r) return;

    // We selected 'r' elements so we found a valid subset!
    if (r == 0) {

      System.out.print("{ ");
      for (int i = 0; i < N; i++) if (used[i]) System.out.print(set[i] + " ");
      System.out.println("}");

    } else {

      for (int i = at; i < N; i++) {

        // Try including this element
        used[i] = true;

        combinations(set, r - 1, i + 1, used);

        // Backtrack and try the instance where we did not include this element
        used[i] = false;
      }
    }
  }

  // Use this method in combination with a do while loop to generate all the combinations
  // of a set choosing r elements in a iterative fashion. This method returns
  // false once the last combination has been generated.
  // NOTE: Originally the selection needs to be initialized to {0,1,2,3 ... r-1}
  public static boolean nextCombination(int[] selection, int N, int r) {
    if (r > N) throw new IllegalArgumentException("r must be <= N");
    int i = r - 1;
    while (selection[i] == N - r + i) if (--i < 0) return false;
    selection[i]++;
    for (int j = i + 1; j < r; j++) selection[j] = selection[i] + j - i;
    return true;
  }

  public static void main(String[] args) {

    // Recursive approach
    int R = 3;
    int[] set = {1, 2, 3, 4, 5};
    combinationsChooseR(set, R);
    // prints:
    // { 1 2 3 }
    // { 1 2 4 }
    // { 1 2 5 }
    // { 1 3 4 }
    // { 1 3 5 }
    // { 1 4 5 }
    // { 2 3 4 }
    // { 2 3 5 }
    // { 2 4 5 }
    // { 3 4 5 }

    // Suppose we want to select all combinations of colors where R = 3
    String[] colors = {"red", "purple", "green", "yellow", "blue", "pink"};
    R = 3;

    // Initialize the selection to be {0, 1, ... , R-1}
    int[] selection = {0, 1, 2};
    do {

      // Print each combination
      for (int index : selection) System.out.print(colors[index] + " ");
      System.out.println();

    } while (nextCombination(selection, colors.length, R));
    // prints:
    // red purple green
    // red purple yellow
    // red purple blue
    // red purple pink
    // red green yellow
    // red green blue
    // red green pink
    // red yellow blue
    // red yellow pink
    // red blue pink
    // purple green yellow
    // purple green blue
    // purple green pink
    // purple yellow blue
    // purple yellow pink
    // purple blue pink
    // green yellow blue
    // green yellow pink
    // green blue pink
    // yellow blue pink

  }
}
