package com.williamfiset.algorithms.math;

import java.util.Scanner;

public class RandomizedIsPrime{

    private static final Integer ITERATION = 20;

    public static boolean isPrime(long n, int iteration) {
        long a;

        long up = n - 2;
        long down = 2;
        for (int i = 0; i < iteration; i++) {
            a = (long) Math.floor(Math.random() * (up - down + 1) + down);
            if (modPow(a, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * *
     *
     * @param a basis
     * @param b exponent
     * @param c modulo
     * @return (a ^ b) mod c
     */
    private static long modPow(long a, long b, long c) {
        long res = 1;
        for (int i = 0; i < b; i++) {
            res *= a;
            res %= c;
        }
        return res % c;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");

        long n = scanner.nextInt();
        if (isPrime(n, ITERATION)) {
            System.out.println(n + " is Prime");
        } else {
            System.out.println(n + " isn't Prime");
        }

    }

}
