/**
 * This file shows you how to solve the traveling salesman problem using a brute force approach.
 * Since the time complexity is on the order of O(n!) this method is not convenient for n > 12
 *
 * <p>Time Complexity: O(n!)
 *
 * @author William Fiset, Micah Stairs
 */
package com.williamfiset.algorithms.graphtheory;

public class TspBruteForce {

  // Given an nxn complete graph represented as an adjacency
  // matrix this method finds the best tour that visits all
  // the nodes while minimizing the overall visit cost.
  public static int[] tsp(double[][] matrix) {

    int n = matrix.length;
    int[] permutation = new int[n];
    for (int i = 0; i < n; i++) permutation[i] = i;

    int[] bestTour = permutation.clone();
    double bestTourCost = Double.POSITIVE_INFINITY;

    // Try all n! tours
    do {

      double tourCost = computeTourCost(permutation, matrix);

      if (tourCost < bestTourCost) {
        bestTourCost = tourCost;
        bestTour = permutation.clone();
      }

    } while (nextPermutation(permutation));

    return bestTour;
  }

  public static double computeTourCost(int[] tour, double[][] matrix) {

    double cost = 0;

    // Compute the cost of going to each city
    for (int i = 1; i < matrix.length; i++) {
      int from = tour[i - 1];
      int to = tour[i];
      cost += matrix[from][to];
    }

    // Compute the cost to return to the starting city
    int last = tour[matrix.length - 1];
    int first = tour[0];
    return cost + matrix[last][first];
  }

  // Generates the next ordered permutation in-place (skips repeated permutations).
  // Calling this when the array is already at the highest permutation returns false.
  // Recommended usage is to start with the smallest permutations and use a do while
  // loop to generate each successive permutations (see main for example).
  public static boolean nextPermutation(int[] sequence) {
    int first = getFirst(sequence);
    if (first == -1) return false;
    int toSwap = sequence.length - 1;
    while (sequence[first] >= sequence[toSwap]) --toSwap;
    swap(sequence, first++, toSwap);
    toSwap = sequence.length - 1;
    while (first < toSwap) swap(sequence, first++, toSwap--);
    return true;
  }

  private static int getFirst(int[] sequence) {
    for (int i = sequence.length - 2; i >= 0; --i) if (sequence[i] < sequence[i + 1]) return i;
    return -1;
  }

  private static void swap(int[] sequence, int i, int j) {
    int tmp = sequence[i];
    sequence[i] = sequence[j];
    sequence[j] = tmp;
  }

  public static void main(String[] args) {

    int n = 10;
    double[][] matrix = new double[n][n];
    for (double[] row : matrix) java.util.Arrays.fill(row, 100);

    // Construct an optimal tour
    int edgeCost = 5;
    int[] optimalTour = {2, 7, 6, 1, 9, 8, 5, 3, 4, 0, 2};
    for (int i = 1; i < optimalTour.length; i++)
      matrix[optimalTour[i - 1]][optimalTour[i]] = edgeCost;

    int[] bestTour = tsp(matrix);
    System.out.println(java.util.Arrays.toString(bestTour));

    double tourCost = computeTourCost(bestTour, matrix);
    System.out.println("Tour cost: " + tourCost);
  }
}
