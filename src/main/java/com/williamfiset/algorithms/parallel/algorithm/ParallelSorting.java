package com.williamfiset.algorithms.parallel.algorithm;

import java.util.concurrent.RecursiveAction;

public class ParallelSorting extends RecursiveAction{

    private final int[] arr;
    private final int low;
    private final int high;

    public ParallelSorting(int[] arr, int low, int high) {
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            invokeAll(new ParallelSorting(arr, low, pivotIndex - 1), new ParallelSorting(arr, pivotIndex + 1, high));
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
}
