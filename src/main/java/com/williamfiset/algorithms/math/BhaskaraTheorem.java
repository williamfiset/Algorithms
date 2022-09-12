package com.williamfiset.algorithms.math;
import java.util.*;
public class BhaskaraTheorem {

    public static int bhask(int a, int b, int c) {
        int delta;
        double aux, aux1;

        delta = (b * b) + (-4 * (a * c));
        System.out.println("Delta: " + delta);

        if (delta >= 0) {

            System.out.println("Delta Positivo");
           aux = ((-(b) + Math.sqrt(delta)) / 2 * a);
            aux1 = ((-(b) - Math.sqrt(delta)) / 2 * a);

            System.out.println("x1 = " + aux);
            System.out.println("x2 = " + aux1);

        } else {
            System.out.println("Delta Negativo");
            return -1; //Status de erro.
        }
        return delta;
    }
    public static void main(String[] args) {
    System.out.println(bhask(4, 48, 6)); //
    System.out.println(bhask(4, 5, 6));
    System.out.println(bhask(0,0 , 0));
    }
}
