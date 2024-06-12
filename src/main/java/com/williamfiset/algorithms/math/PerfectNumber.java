package com.williamfiset.algorithms.math;

import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PerfectNumber {
    public static boolean isPerfect(int n) {
        if(n <= 1) return false;

        return IntStream.range(1, n / 2 + 1).filter(i -> n % i == 0).sum() == n;
    }

    public static boolean isPerfect(long n) {
        if(n <= 1L) return false;

        return LongStream.range(1, n / 2 + 1).filter(i -> n % i == 0).sum() == n;
    }
}
