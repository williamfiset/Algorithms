/**
 * An implementation of the TSP problem using a genetic algorithm. This implementation works well
 * for graph with < 30 nodes, beyond this it seems to struggle getting near the optimal solution.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.ai;

import java.util.*;

public class GeneticAlgorithm_travelingSalesman {

  static final Random RANDOM = new Random();

  // Genetic algorithm parameters (P = Population)
  static final int P = 250;
  static final int MAX_EPOCH = 100000;
  static final double MUTATION_RATE = 0.015;

  // The power variable tweaks the weight of the fitness function
  // to emphasize better individuals. The power slowly increments
  // over time to help get out of local minimums in later epochs.
  static double power;
  static final double POWER_INC = 0.0001;

  static double tsp(double[][] adjacencyMatrix) {

    power = 1.0;
    final int N = adjacencyMatrix.length;

    double max = Double.NEGATIVE_INFINITY;
    for (double[] row : adjacencyMatrix) {
      for (double elem : row) {
        max = Math.max(max, elem);
      }
    }

    // Create initial population
    Individual[] generation = new Individual[P + 1];
    Individual[] nextGeneration = new Individual[P + 1];
    for (int i = 1; i <= P; i++) generation[i] = new Individual(N);

    // Stores the ranges of individuals in the selection roulette
    double[] lo = new double[P + 1];
    double[] hi = new double[P + 1];

    double[] fitness = new double[P + 1];

    // Record data about the fittest individual
    int[] tour = null;
    Individual fittestIndv = null;
    double fittestIndvFitness = Double.NEGATIVE_INFINITY;

    for (int epoch = 1; epoch <= MAX_EPOCH; epoch++, power += POWER_INC) {

      // Compute the total fitness sum across all individuals in order
      // to be able to normalize and assign importance percentages
      double fitnessSum = 0;

      for (int i = 1; i <= P; i++) {
        Individual in = generation[i];
        fitness[i] = fitness(in, adjacencyMatrix, max, N);
        fitnessSum += fitness[i];
        lo[i] = hi[i] = 0;
      }

      // Track epoch fittest individual
      Individual bestEpochIndv = null;
      double bestEpochFitness = Double.NEGATIVE_INFINITY;

      // Setup selection roulette
      for (int i = 1; i <= P; i++) {

        Individual in = generation[i];
        double norm = fitness[i] / fitnessSum;

        lo[i] = hi[i - 1] = lo[i - 1] + norm;

        if (fitness[i] > bestEpochFitness) {

          bestEpochIndv = in;
          bestEpochFitness = fitness[i];
          if (fittestIndv == null) fittestIndv = in;

          // Compute the true tour distance
          double bestEpochTravelCost = trueTravelCost(bestEpochIndv, adjacencyMatrix, N);
          double bestTravelCost = trueTravelCost(fittestIndv, adjacencyMatrix, N);

          // Update fittest individual info
          if (bestEpochTravelCost <= bestTravelCost) {
            tour = in.cities.clone();
            fittestIndv = in;
            fittestIndvFitness = bestEpochTravelCost;
          }
        }
      }

      double bestEpochTravelCost = trueTravelCost(bestEpochIndv, adjacencyMatrix, N);
      double bestTravelCost = trueTravelCost(fittestIndv, adjacencyMatrix, N);

      if (epoch % 100 == 0)
        System.out.printf("Epoch: %d, %.0f, %.0f\n", epoch, bestEpochTravelCost, bestTravelCost);

      // Selection process
      for (int i = 1; i <= P; i++) {

        // Perform individual selection and crossover
        Individual parent1 = selectIndividual(generation, lo, hi);
        Individual parent2 = selectIndividual(generation, lo, hi);
        Individual child = crossover(parent1, parent2, N);

        // Apply mutations to all parts of the DNA
        // according to a predefined mutation rate
        for (int j = 0; j < N; j++) {
          if (Math.random() < MUTATION_RATE) {
            mutate(child);
          }
        }

        nextGeneration[i] = child;
      }

      generation = nextGeneration;
    }

    return trueTravelCost(fittestIndv, adjacencyMatrix, N);
    // return tour;

  }

  // Returns an approximate fitness of a give tour
  static double fitness(Individual in, double[][] adjacencyMatrix, double max, int n) {

    // Compute the cost of traveling to all the cities
    double fitness = 0;
    for (int i = 1; i < n; i++) {
      int from = in.cities[i - 1];
      int to = in.cities[i];
      fitness += max - adjacencyMatrix[from][to];
    }

    // Compute cost for trip back
    int last = in.cities[n - 1];
    int first = in.cities[0];
    fitness += max - adjacencyMatrix[last][first];

    // return fitness;
    return Math.pow(fitness, power);
  }

  static double trueTravelCost(Individual in, double[][] adjacencyMatrix, int n) {

    // Compute the cost of traveling to all the cities
    double fitness = 0;
    for (int i = 1; i < n; i++) {
      int from = in.cities[i - 1];
      int to = in.cities[i];
      fitness += adjacencyMatrix[from][to];
    }

    // Compute cost for trip back
    int last = in.cities[n - 1];
    int first = in.cities[0];
    fitness += adjacencyMatrix[last][first];

    return fitness;
  }

  static void mutate(Individual in) {
    in.mutate();
  }

  static Individual selectIndividual(Individual[] generation, double[] lo, double[] hi) {

    double r = Math.random();

    // Binary search to find individual
    int mid, l = 0, h = P - 1;
    while (true) {
      mid = (l + h) >>> 1;
      if (lo[mid] <= r && r < hi[mid]) return generation[mid + 1];
      if (r < lo[mid]) h = mid - 1;
      else l = mid + 1;
    }
  }

  static Individual crossover(Individual p1, Individual p2, int n) {

    int[] newPath = new int[n];
    int start = RANDOM.nextInt(n);
    int end = RANDOM.nextInt(n);
    int minimum = Math.min(start, end);
    int maximum = Math.max(start, end);

    int[] missing = new int[n - ((maximum - minimum) + 1)];

    int j = 0;
    for (int i = 0; i < n; i++) {
      if (i >= minimum && i <= maximum) {
        newPath[i] = p1.cities[i];
      } else {
        missing[j++] = p1.cities[i];
      }
    }

    Individual.shuffleArray(missing);

    j = 0;
    for (int i = 0; i < n; i++) {
      if (i < minimum || i > maximum) {
        newPath[i] = missing[j++];
      }
    }
    return new Individual(newPath);
  }

  public static void main(String[] args) {

    int n = 64;

    double[][] m = new double[n][n];
    for (double[] row : m) {
      Arrays.fill(row, 10.0);
    }

    // Construct an optimal path
    List<Integer> path = new ArrayList<>(n);
    for (int i = 0; i < n; i++) path.add(i);
    for (int i = 1; i < n; i++) {
      int from = path.get(i - 1);
      int to = path.get(i);
      m[from][to] = 1.0;
    }
    int last = path.get(n - 1);
    int first = path.get(0);
    m[last][first] = 1.0;

    System.out.println(tsp(m));
  }

  // An individual in the TSP is an order in which
  // the cities are visited.
  static class Individual {

    int[] cities;
    static Random RANDOM = new Random();

    // Constructs a random individual
    public Individual(int n) {
      cities = new int[n];
      for (int i = 0; i < n; i++) cities[i] = i;
      shuffleArray(cities);
    }

    // Constructs a random individual
    public Individual(int[] cities) {
      this.cities = cities;
    }

    // public boolean ass() {
    //   int s = 0;
    //   for (int x : cities) s += x;
    //   int n = cities.length-1;
    //   return s == (n*(n-1))/2;
    // }

    // Swap the order in which the cities are visited
    public void mutate() {
      int i = RANDOM.nextInt(cities.length);
      int j = RANDOM.nextInt(cities.length);
      int tmp = cities[i];
      cities[i] = cities[j];
      cities[j] = tmp;
    }

    // Fisher–Yates shuffle
    public static void shuffleArray(int[] array) {
      int index;
      for (int i = array.length - 1; i > 0; i--) {
        index = RANDOM.nextInt(i + 1);
        if (index != i) {
          array[index] ^= array[i];
          array[i] ^= array[index];
          array[index] ^= array[i];
        }
      }
    }

    @Override
    public String toString() {
      return java.util.Arrays.toString(cities);
    }
  }
}
