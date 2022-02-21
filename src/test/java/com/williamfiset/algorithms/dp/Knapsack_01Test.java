package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;
import org.junit.jupiter.api.Assertions;

import java.awt.geom.Point2D;

public class Knapsack_01Test {
    @Test
    public void example1() {
        int capacity = 10;
        int[] V = {1, 4, 8, 5};
        int[] W = {3, 3, 5, 6};
        int answer = Knapsack_01.knapsack(capacity, W, V);
        assertThat(answer).isEqualTo(12);
    }

    @Test
    public void example2() {
        int capacity = 7;
        int[] V = new int[] {2, 2, 4, 5, 3};
        int[] W = new int[] {3, 1, 3, 4, 2};
        int answer = Knapsack_01.knapsack(capacity, W, V);
        assertThat(answer).isEqualTo(10);
    }

    @Test
    public void invalidInput_V() {
        int capacity = 7;
        int[] V = null;
        int[] W = new int[] {3, 1, 3, 4, 2};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Knapsack_01.knapsack(capacity, W, V);
        });
    }

    @Test
    public void invalidInput_W() {
        int capacity = 7;
        int[] V = new int[] {1, 4, 8, 5};
        int[] W = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Knapsack_01.knapsack(capacity, W, V);
        });
    }

    @Test
    public void invalidInput_different_length() {
        int capacity = 7;
        int[] V = new int[] {1, 4, 8, 5};
        int[] W = new int[] {3, 1, 3, 4, 2};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Knapsack_01.knapsack(capacity, W, V);
        });
    }

    @Test
    public void invalidInput_capacity() {
        int capacity = -1;
        int[] V = new int[] {1, 4, 8, 5};
        int[] W = new int[] {3, 3, 5, 6};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Knapsack_01.knapsack(capacity, W, V);
        });
    }
}