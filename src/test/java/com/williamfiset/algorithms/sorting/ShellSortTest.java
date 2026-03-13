package com.williamfiset.algorithms.sorting;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class ShellSortTest {

    @Test
    public void testPositiveNumbers() {
        int[] input = {5, 2, 9, 1, 5, 6};
        int[] expected = {1, 2, 5, 5, 6, 9};
        ShellSort.shellSort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testNegativeNumbers() {
        int[] input = {-3, -1, -4, -2, -5};
        int[] expected = {-5, -4, -3, -2, -1};
        ShellSort.shellSort(input);
        assertArrayEquals(expected, input);
    }

    @Test
    public void testMixedNumbers() {
        int[] input = {10, -3, 0, 2, -15, 7};
        int[] expected = {-15, -3, 0, 2, 7, 10};
        ShellSort.shellSort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    public void testSingleElementArray() {
        int[] input = {42};
        int[] expected = {42};
        ShellSort.shellSort(input);
        assertArrayEquals(expected, input);
    }
    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        ShellSort.shellSort(input);
        assertArrayEquals(expected, input);
    }
}
