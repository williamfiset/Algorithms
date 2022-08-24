package com.williamfiset.algorithms.math;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class CompoundInterestTest {
    @Test
    public void  CompoundInterest1() {
        double main = 1000;
        double rate = 0.05;
        int t = 3;
        String result = CompoundInterest.compoundInterestCalculate(main,rate,t);
        assertThat(result).isEqualTo("1.157,63");
    }
    @Test
    public void  CompoundInterest2() {
        double main = 1000;
        double rate = 0.05;
        int t = -3;
        String result = CompoundInterest.compoundInterestCalculate(main,rate,t);
        assertThat(result).isEqualTo("Não eh possivel calcular para um tempo negativo");
    }
    @Test
    public void  CompoundInterest3() {
        double main = 1000;
        double rate = -0.05;
        int t = 3;
        String result = CompoundInterest.compoundInterestCalculate(main,rate,t);
        assertThat(result).isEqualTo("Não eh possivel calcular para uma taxa  de juros negativa");
    }
}
