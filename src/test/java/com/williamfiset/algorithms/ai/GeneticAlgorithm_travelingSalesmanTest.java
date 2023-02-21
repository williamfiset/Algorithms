package com.williamfiset.algorithms.ai;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.ai.GeneticAlgorithm_travelingSalesman.*;

import org.junit.*;

public class GeneticAlgorithm_travelingSalesmanTest {

    @Test
    public void testTspReturnsCorrectResult() {
        double[][] matrix = {{0, 10, 15, 20}, {5, 0, 9, 10}, {6, 13, 0, 12}, {8, 8, 9, 0}};
        double result = GeneticAlgorithm_travelingSalesman.tsp(matrix);
        assertThat(35.0).isEqualTo(result);
    }

    @Test
    public void testGetNormalizedFitness() {
        // Define inputs
        double[][] adjacencyMatrix = {
                {0, 1, 1},
                {1, 0, 1},
                {1, 1, 0}
        };
        int N = 3;

        Individual[] generation = new Individual[3];
        for (int i = 1; i <= 2; i++) generation[i] = new Individual(N);
        generation[1].cities = new int[]{0, 2, 1};
        generation[2].cities = new int[]{1, 0, 2};

        double[] fitness = new double[3];
        double[] lo = new double[3];
        double[] hi = new double[3];

        double[] expectedFitness = new double[]{0, 1, 1};
        double[] expectedLo = new double[]{0, 0.5, 1};
        double[] expectedHi = new double[]{0.5, 1, 0};

        getNormalizedFitness(2, adjacencyMatrix, N, generation, fitness, lo, hi);

        assertThat(expectedFitness).isEqualTo(fitness);
        assertThat(expectedLo).isEqualTo(lo);
        assertThat(expectedHi).isEqualTo(hi);
    }
}