package com.williamfiset.algorithms.math;

import org.junit.Test;

public class PrimeTest {
    @Test
    public void testPrimeSieve() {
        int[] limitArray = {8, 9, 15};
        for (int i = 0; i < limitArray.length; i++) {
            long[] response = CompressedPrimeSieve.primeSieve(limitArray[i]);
            if (limitArray[i] == 8 && response[0] == 1) {
                System.out.println("Pass");
            } else if (limitArray[i] == 8 && response[0] != 1) {
                System.out.println("Fail");
            }

            if (limitArray[i] == 9 && response[0] == 17) {
                System.out.println("Pass");
            } else if (limitArray[i] == 9 && response[0] != 17) {
                System.out.println("Fail");
            }

            if (limitArray[i] == 15 && response[0] == 145) {
                System.out.println("Pass");
            } else if (limitArray[i] == 15 && response[0] != 145) {
                System.out.println("Fail");
            }

        }

    }
}
