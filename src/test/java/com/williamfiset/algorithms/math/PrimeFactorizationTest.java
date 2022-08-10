package com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;

public class PrimeFactorizationTest {
    @Test
    @DisplayName("Valid inputs and invalid inputs")
    public void primeFactorizationTest(){
        assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.primeFactorization(0));
        assertThrows(IllegalArgumentException.class, () -> PrimeFactorization.primeFactorization(-1));
        assertThat(PrimeFactorization.primeFactorization(1)).containsExactly();
        assertThat(PrimeFactorization.primeFactorization(7)).containsExactly(7l);
        assertThat(PrimeFactorization.primeFactorization(100)).containsExactly(2l,2l,5l,5l);
        assertThat(PrimeFactorization.primeFactorization(666)).containsExactly(2l,3l,3l,37l);
        assertThat(PrimeFactorization.primeFactorization(872342345)).containsExactly(5l, 7l, 7l, 67l, 19l, 2797l);
    }

    @Test
    @DisplayName("Conditions on pollarRho")
    public void pollardRhoTest(){
        assertThat(PrimeFactorization.pollardRho(7)).isEqualTo(7);
        assertThat(PrimeFactorization.pollardRho(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Conditions on isPrime")
    public void isPrimeTest(){
        assertThat(PrimeFactorization.isPrime(1)).isFalse();
        assertThat(PrimeFactorization.isPrime(2)).isTrue();
        assertThat(PrimeFactorization.isPrime(3)).isTrue();
        assertThat(PrimeFactorization.isPrime(100)).isTrue();
        assertThat(PrimeFactorization.isPrime(666)).isTrue();
        assertThat(PrimeFactorization.isPrime(7)).isFalse();
    }
}
