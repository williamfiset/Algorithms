package com.williamfiset.algorithms.other;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import static com.google.common.truth.Truth.assertThat;

public class FactorialTest {
    @Test
    @DisplayName("TDD - Red")
    public void factorialTest(){
        assertThat(Factorial.calcFactorial(5)).isEqualTo(120);
    }
}
