package com.williamfiset.algorithms.math;

import com.williamfiset.algorithms.search.InterpolationSearch;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class isPrimeTest {
    @Test
    public void testIsPrime1() {
        int[] arr = {0, 1, 2, 3, 4, 5};
        boolean result = IsPrime.isPrime(1);
        assertThat(result).isEqualTo(false);
    }
    @Test
    public void testIsPrime2() {
        boolean result = IsPrime.isPrime(2);
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void testIsPrime3() {
        boolean result = IsPrime.isPrime(3);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testIsPrime4() {
        boolean result = IsPrime.isPrime(4);
        assertThat(result).isEqualTo(false);
    }
    @Test
    public void testIsPrime5() {
        boolean result = IsPrime.isPrime(5);
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testIsPrime6() {
        boolean result = IsPrime.isPrime(569);
        assertThat(result).isEqualTo(true);
    }
    @Test
    public void testIsPrime7() {
        boolean result = IsPrime.isPrime(32);
        assertThat(result).isEqualTo(false);
    }
}
