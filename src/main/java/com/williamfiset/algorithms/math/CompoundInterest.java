package com.williamfiset.algorithms.math;

public class CompoundInterest {
    public static double CompoundInterest(double principalAmount, double interestRateDecimal, double compoundsPerYear, double totalYears) {
        double unroundedNum = (principalAmount * Math.pow((1+(interestRateDecimal/compoundsPerYear)),compoundsPerYear*totalYears));
        //rounds to the nearest cent
        return Math.round(unroundedNum*100.00)/100.00;
    }
    public static void main(String[] args) {
        System.out.println(CompoundInterest(100,0.12,12,2));
        System.out.println(CompoundInterest(2001,0.21,4,8));
    }
}
