package com.williamfiset.algorithms.math;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
public class StandardDeviationTest {

    @Test
    public void StandardDeviation4(){
        double[] array = {-1, -2, -3, -4, -5};
        double result = StandardDeviation.SD(array);
        assertEquals(1.4142135623730951, result);
    }

    @Test
    public void StandardDeviation2(){
        double[] array = {3, 5, 7, 20, 55, 12, 1, 3, 5, 4};
        double result = StandardDeviation.SD(array);
        assertEquals(15.428869044748549, result);
    }
}
