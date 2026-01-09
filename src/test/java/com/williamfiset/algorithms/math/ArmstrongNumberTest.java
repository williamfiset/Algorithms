package com.williamfiset.algorithms.math;

import static com.williamfiset.algorithms.math.ArmstrongNumber.isArmstrongNumber;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;


public class ArmstrongNumberTest {
    @Test
    public void armstrongNumberTest(){
        assertTrue(isArmstrongNumber(1)==true);
        assertTrue(isArmstrongNumber(153)==true);
        assertTrue(isArmstrongNumber(1634)==true);
        assertTrue(isArmstrongNumber(371)==true);
        assertFalse(isArmstrongNumber(10)==true);
        assertFalse(isArmstrongNumber(100)==true);
        assertFalse(isArmstrongNumber(1000)==true);
    }
}