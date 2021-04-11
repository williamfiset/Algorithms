/**
 * Implementation of heapsort
 *
 * <p>Run with:
 *
 * <p>$ ./gradlew run -Palgorithm=sorting.Heapsort
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.sorting;

import java.util.*;

public class Heapsort implements InplaceSort {

    @Override
    public void sort(int[] values) {
        Heapsort.heapSort(values);
    }

    private static void heapSort(int[] array) {
        if (array == null) return;

        int size = array.length;
        buildMaxHeap(array, size);

        // Sorting bit
        for (int endIndex = size - 1; endIndex >= 0; endIndex--) {
            swap(array, endIndex, 0);
            siftDown(array, 0, endIndex);
        }
    }

    // Build max heap, converts array into binary heap O(n), see:
    // http://www.cs.umd.edu/~meesh/351/mount/lectures/lect14-heapsort-analysis-part.pdf
    private static void buildMaxHeap(int[] array, int size) {
        int parentIndex = Math.max(0, (size - 2) / 2);
        for (int currentIndex = parentIndex; currentIndex >= 0; currentIndex--) {
            siftDown(array, currentIndex, size);
        }
    }

    private static void siftDown(int[] array, int currentIndex, int endIndex) {
        boolean hasFinished = false;

        while (!hasFinished) {
            int leftIndex = currentIndex * 2 + 1;  // Left child node
            int rightIndex = currentIndex * 2 + 2; // Right child node
            int largestIndex = currentIndex;

            // Right child is larger than parent
            if (rightIndex < endIndex && array[rightIndex] > array[largestIndex]) {
                largestIndex = rightIndex;
            }

            // Left child is larger than parent
            if (leftIndex < endIndex && array[leftIndex] > array[largestIndex]) {
                largestIndex = leftIndex;
            }

            // Move down the tree following the largest node
            if (largestIndex != currentIndex) {
                swap(array, largestIndex, currentIndex);
                currentIndex = largestIndex;
            } else {
                hasFinished = true;
            }
        }
    }

    private static void swap(int[] array, int firstIndex, int secondIndex) {
        int temporary = array[secondIndex];
        array[secondIndex] = array[firstIndex];
        array[firstIndex] = temporary;
    }

    // Make sure to add to VM parameters "-ea" for assertions to work
    public static void main(String[] args) {
        Heapsort sorter = new Heapsort();
        int[] input = { 10, 4, 6, 4, 8, -13, 2, 3 };
        int[] expected = { -13, 2, 3, 4, 4, 6, 8, 10 };
        sorter.sort(input);
        assert (Arrays.equals(input, expected));
    }

}
