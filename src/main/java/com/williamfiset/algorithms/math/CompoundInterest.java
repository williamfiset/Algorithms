package com.williamfiset.algorithms.math;

import java.text.DecimalFormat;

public class CompoundInterest {
    public static String compoundInterestCalculate(double main, double rate, int time) {

        double juros, amount ;

        if(time<0) {
            return "Não eh possivel calcular para um tempo negativo";
        }
        if(rate < 0 ) {
            return  "Não eh possivel calcular para uma taxa  de juros negativa";
        }

        juros = 1;

        for (int i =0 ; i<time ; i++) {
             juros = juros * (1.0 + rate);
        }

        amount = main * juros;
        return new DecimalFormat("#,##0.00").format(amount);
    }

    public static void main (String[] args ) {
        compoundInterestCalculate(1000,0.05,3);

    }
}

