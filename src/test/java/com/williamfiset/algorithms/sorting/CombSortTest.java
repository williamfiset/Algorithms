package com.williamfiset.algorithms.sorting;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static com.google.common.truth.Truth.assertThat;

public class CombSortTest {
    static Random random = new Random();

    CombSort ob = new CombSort();

    @Test
    public void randomCombSort_smallNumbers() {
        for (int size = 0; size < 1000; size++) {
            int[] values = new int[size];
            for (int i = 0; i < size; i++) {
                values[i] = randInt(1, 50);
            }
            int[] copy = values.clone();

            Arrays.sort(values);
            ob.sort(copy);
            assertThat(values).isEqualTo(copy);
        }
    }

    @Test
    public void randomRadixSort_largeNumbers() {
        for (int size = 0; size < 1000; size++) {
            int[] values = new int[size];
            for (int i = 0; i < size; i++) {
                values[i] = randInt(1, Integer.MAX_VALUE);
            }
            int[] copy = values.clone();

            Arrays.sort(values);
            ob.sort(copy);

            assertThat(values).isEqualTo(copy);
        }
    }

    // return a random number between [min, max]
    static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}
