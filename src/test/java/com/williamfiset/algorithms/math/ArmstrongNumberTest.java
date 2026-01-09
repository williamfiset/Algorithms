package test.java.com.williamfiset.algorithms.math;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArmstrongNumberTest {

    @Test
    @DisplayName("Valid inputs and invalid inputs")
    public void armstrongNumberTest(){
    	assertTrue(ArmstrongNumber.isArmstrongNumber(1));
        assertTrue(ArmstrongNumber.isArmstrongNumber(153));
        assertFalse(ArmstrongNumber.isArmstrongNumber(10));
        assertFalse(ArmstrongNumber.isArmstrongNumber(300));
    }

}
