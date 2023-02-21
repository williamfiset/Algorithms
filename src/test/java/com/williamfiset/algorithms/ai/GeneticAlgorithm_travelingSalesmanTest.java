package com.williamfiset.algorithms.ai;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.ai.GeneticAlgorithm_travelingSalesman.*;

import org.junit.*;

import java.util.Arrays;

public class GeneticAlgorithm_travelingSalesmanTest {

    boolean[] branches = new boolean[7];

    @Test
    public void testTspReturnsCorrectResult1() {
        double[][] matrix = {{0, 10, 15, 20}, {5, 0, 9, 10}, {6, 13, 0, 12}, {8, 8, 9, 0}};
        Arrays.fill(branches, false);
        double result = GeneticAlgorithm_travelingSalesman.tsp(matrix, branches);
        printBranches(branches);
        assertThat(35.0).isEqualTo(result);
    }

    @Test
    public void testTspReturnsCorrectResult2() {
        double[][] matrix = {{0, 1, 5}, {1, 0, 2}, {5, 2, 0}};
        Arrays.fill(branches, false);
        double result = GeneticAlgorithm_travelingSalesman.tsp(matrix, branches);
        printBranches(branches);
        assertThat(8.0).isEqualTo(result);
    }

    @Test
    public void testGetNormalizedFitness() {
        // Define inputs
        double[][] adjacencyMatrix = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };

        Individual[] generation = new Individual[3];
        for (int i = 1; i <= 2; i++) generation[i] = new Individual(3);
        generation[1].cities = new int[]{0, 2, 1};
        generation[2].cities = new int[]{1, 0, 2};

        double[] fitness = new double[3];
        double[] lo = new double[3];
        double[] hi = new double[3];

        double[] expectedFitness = new double[]{0, 1, 1};
        double[] expectedLo = new double[]{0, 0.5, 1};
        double[] expectedHi = new double[]{0.5, 1, 0};

        getNormalizedFitness(2, adjacencyMatrix, 3, generation, fitness, lo, hi);

        assertThat(expectedFitness).isEqualTo(fitness);
        assertThat(expectedLo).isEqualTo(lo);
        assertThat(expectedHi).isEqualTo(hi);
    }

    @Test
    public void testGetMaxElement() {
        double[][] adjacencyMatrix = {
                {1.0, 2.0, 3.0},
                {4.0, 11.0, 6.0},
                {7.0, 8.0, 9.0}
        };
        double expectedMax = 11.0;
        double actualMax = getMaxElement(adjacencyMatrix);
        assertThat(expectedMax).isEqualTo(actualMax);
    }

    public void printBranches(boolean[] branches) {
        for (int i = 0; i < branches.length; i++) {
            if (branches[i]) {
                System.out.printf("Branch %d was taken\n", i);
            } else {
                System.out.printf("Branch %d was not taken\n", i);
            }
        }
    }
}