package com.williamfiset.algorithms.math;

public class StandardDeviation {

    public static double SD(double arr[]) {
        double sum = 0, StandardDeviation = 0;
        int length = arr.length;
        for (double num : arr) {
            sum += num;
        }
        double mean = sum / length;
        for (double num : arr) {
            StandardDeviation = StandardDeviation + Math.pow(num - mean, 2);
        }
        return Math.sqrt((StandardDeviation / length));
    }
}
