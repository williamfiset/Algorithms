package com.williamfiset.algorithms.math;

public class DecimalToBinary {
    public static long decimaltobinary(long numero) {

        long resultado = 0l;
        long y = 1;
        while (numero > 0) {
            if (numero % 2 == 1) {
                resultado += (long) Math.pow(10, 0);
                numero -= 1;
            }
            long x = (long) Math.pow(2, y);
            if ((numero - x) % (2 * x) == 0) {
                resultado += (long) Math.pow(10, y);
                numero -= x;
            } else {
                y++;
            }
        }
    return resultado;
    }
}
