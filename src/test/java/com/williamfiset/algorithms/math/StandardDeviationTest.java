package com.williamfiset.algorithms.math;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StandardDeviationTest {

    @Test
    public void StandardDeviation1(){
        double[] array = {3, 5, 7};
        double result = StandardDeviation.SD(array);
        assertEquals(1.632993161855452, result);
    }

    @Test
    public void StandardDeviation2(){
        double[] array = {3, 5, 7, 20, 55, 12, 1, 3, 5, 4};
        double result = StandardDeviation.SD(array);
        assertEquals(15.428869044748549, result);
    }

    @Test
    public void StandardDeviation3(){
        double[] array = {-3, -12, 1000, 3, 5};
        double result = StandardDeviation.SD(array);
        assertEquals(400.7436088074269, result);
    }

    @Test
    public void StandardDeviation4(){
        double[] array = {-1, -2, -3, -4, -5};
        double result = StandardDeviation.SD(array);
        assertEquals(1.4142135623730951, result);
    }

}