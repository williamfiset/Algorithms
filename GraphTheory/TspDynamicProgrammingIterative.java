import java.util.*;
public class TspDynamicProgrammingIterative {

  public static double tsp(double[][] distance) {
    return tsp(0, distance);
  }

  public static double tsp(int start, double[][] distance) {
    final int n = distance.length;
    
    if (n <= 1) return 0;
    if (n != distance[0].length) throw new IllegalStateException("Matrix must be square (n x n)");
    if (n == 2) return distance[0][1] + distance[1][0];
    if (start < 0 || start >= n) throw new IllegalArgumentException("Invalid start node.");

    final int END_STATE = (1 << n) - 1;
    Double[][] memo = new Double[n][1 << n];

    // Add all outgoing edges from the starting node to memo table.
    for (int end = 0; end < n; end++) {
      if (end == start) continue;
      memo[end][(1 << start) | (1 << end)] = distance[start][end];
    }

    for (int r = 3; r <= n; r++) {
      for (int subset : combinations(r, n)) {
        if (notIn(start, subset)) continue;
        for (int next = 0; next < n; next++) {
          if (next == start) continue;
          if (notIn(next, subset)) continue;
          int subsetWithoutNext = subset & ~(1 << next);
          double minDist = Double.POSITIVE_INFINITY;
          for (int end = 0; end < n; end++) {
            if (end == start || end == next) continue;
            if (notIn(end, subset)) continue;
            double newDistance = memo[end][subsetWithoutNext] + distance[end][next];
            if (newDistance < minDist) 
              minDist = newDistance;
          }
          memo[next][subset] = minDist;
        }
      }
    }

    // Connect tour back to starting node.
    double minTourCost = Double.POSITIVE_INFINITY;
    for (int end = 0; end < n; end++) {
      if (end == start) continue;
      double tourCost = memo[end][END_STATE] + distance[end][start];
      if (tourCost < minTourCost) minTourCost = tourCost;
    }

    return minTourCost;
  }

  static boolean in(int elem, int subset) {
    return ((1 << elem) & subset) != 0;
  }

  static boolean notIn(int elem, int subset) {
    return !in(elem, subset);
  }

  // This method finds all the combinations of {0,1...n-1} of size 'r' 
  // returned as a list of integer masks.
  public static List<Integer> combinations(int r, int n) {
    int[] set = new int[n];
    for(int i = 0; i < n; i++) set[i] = i;
    List<Integer> subsets = new ArrayList<>();
    boolean [] used = new boolean[set.length];
    combinations(set, r, 0, used, subsets);
    return subsets;
  }

  // To find all the combinations of size r we need to recurse until we have
  // selected r elements (aka r = 0), otherwise if r != 0 then we need to select
  // an element which is found after the position of our last selected element
  private static void combinations(int[] set, int r, int at, boolean[] used, List<Integer> subsets) {
    final int N = set.length;

    // Return early if there are more elements left to select than what is available.
    int elementsLeftToPick = N - at;
    if (elementsLeftToPick < r) return;

    // We selected 'r' elements so we found a valid subset!
    if (r == 0) {
      int subset = 0;
      for (int i = 0; i < N; i++)
        if (used[i]) subset |= 1 << i;
      subsets.add(subset);
    } else {
      for (int i = at; i < N; i++) {

        // Try including this element
        used[i] = true;

        combinations(set, r - 1, i + 1, used, subsets);

        // Backtrack and try the instance where we did not include this element
        used[i] = false;
      }
    }
  }

  public static void main(String[] args) {
    // Create adjacency matrix
    int n = 6;
    double[][] distanceMatrix = new double[n][n];
    for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    distanceMatrix[0][5] = distanceMatrix[5][0] = 10;
    distanceMatrix[5][1] = distanceMatrix[1][5] = 12;
    distanceMatrix[1][4] = distanceMatrix[4][1] = 2;
    distanceMatrix[4][2] = distanceMatrix[2][4] = 4;
    distanceMatrix[2][3] = distanceMatrix[3][2] = 6;
    distanceMatrix[3][0] = distanceMatrix[0][3] = 8;
    System.out.println(tsp(distanceMatrix));

    // int n = 4;
    // double[][] distanceMatrix = new double[n][n];
    // for (double[] row : distanceMatrix) java.util.Arrays.fill(row, 10000);
    // distanceMatrix[0][2] = 1;
    // distanceMatrix[2][1] = 2;
    // distanceMatrix[1][3] = 4;
    // distanceMatrix[3][0] = 8;
    // System.out.println(tsp(distanceMatrix));

    // int n = 4;
    // double[][] m = {
    //   {0, 2, 9, 10},
    //   {1, 0, 6,  4},
    //   {15, 7, 0, 8},
    //   {6, 3, 12, 0}
    // };
    // System.out.println(tsp(m));

    // printCombs(3, 5);

  }

  static void printCombs(int r, int n) {
    for (Integer x : combinations(r, n)) {
      printState(x, n);
    }
  }

  static void printState(int state, int n) {
    for (int i = 0; i < n; i++)
      if ((state & (1 << i)) != 0)
        System.out.print(i + " ");
    System.out.println();
  }

}












