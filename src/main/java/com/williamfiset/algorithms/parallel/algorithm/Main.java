package com.williamfiset.algorithms.parallel.algorithm;

import java.util.concurrent.ForkJoinPool;

public class Main{

    public static void main(String[] args) {
        int[] arr = {7, 6, 5, 4, 3, 2, 1};

        System.out.println("available processors = " + Runtime.getRuntime().availableProcessors());

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new ParallelSorting(arr, 0, arr.length-1));

        for (int num : arr){
            System.out.println(num);
        }
    }
}
