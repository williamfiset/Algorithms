package com.williamfiset.algorithms.math;

public class StandardDeviation {
    public static void main(String[] args) {
        double[] inputArray = {3, 5, 7};
        double standard_deviation = SD(inputArray);
        System.out.format("%.3f", standard_deviation);
    }
    public static double SD(double inputArray[]) {
        double sum = 0.0, x = 0.0;
        int arrayLength = inputArray.length;

        for(double temp : inputArray) {
            sum += temp;
        }

        double mean = sum/arrayLength;

        for(double temp: inputArray) {
            x += Math.pow(temp - mean, 2);
        }

        double returnSD = Math.sqrt(x/arrayLength);
        return returnSD;
    }
}
