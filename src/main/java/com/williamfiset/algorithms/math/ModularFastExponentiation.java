package com.williamfiset.algorithms.math;

import java.math.BigInteger;

public class ModularFastExponentiation {

    // FastExponentiation is a regularly used algorithm in competitive algorithm
    // The function receives three integers a , n (positive) and m ,
    // then output the result a^n under module m (with complexity O(lg(n)) )
    // Function to calculate (x^n) % m
    public static long modPower(long x, long n, long m) {
        long result = 1;
        x = x % m;  // Handle large x values
        while (n > 0) {
            if (n % 2 == 1) {
                result = ((result * x) % m+m)%m;
            }
            x = ((x * x) % m+m)%m;
            n /= 2;
        }
        return result;
    }

    // Example usage
    public static void main(String[] args) {

        BigInteger A, N, M, r1;
        long a, n, m, r2;

        A = BigInteger.valueOf(2);
        N = BigInteger.valueOf(5);
        M = BigInteger.valueOf(998244353);
        a = A.longValue();
        n = N.longValue();
        m = M.longValue();

        // 2 ^ 5 mod 998244353
        r1 = A.modPow(N, M); // 32
        r2 = modPower(a, n, m); // 32
        System.out.println(r1 + " " + r2);

        A = BigInteger.valueOf(-2);
        N = BigInteger.valueOf(5);
        M = BigInteger.valueOf(998244353);
        a = A.longValue();
        n = N.longValue();
        m = M.longValue();

        // Finds -2 ^ 5 mod 998244353
        r1 = A.modPow(N, M); // 998244321
        r2 = modPower(a, n, m); // 998244321
        System.out.println(r1 + " " + r2);

        A = BigInteger.valueOf(6);
        N = BigInteger.valueOf(10000000);
        M = BigInteger.valueOf(998244353);
        a = A.longValue();
        n = N.longValue();
        m = M.longValue();

        // Finds 6 ^ 10000000 mod 998244353
        r1 = A.modPow(N, M);
        r2 = modPower(a, n, m);
        System.out.println(r1 + " " + r2);

    }

}
