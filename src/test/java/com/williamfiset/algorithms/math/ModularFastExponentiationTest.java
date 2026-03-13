package com.williamfiset.algorithms.math;

import static com.williamfiset.algorithms.math.ModularFastExponentiation.modPower;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;


public class ModularFastExponentiationTest {
    @Test
    public void modularFastExponentiationTest(){
        assertEquals(8, modPower(2, 3, 100));
        assertEquals(76, modPower(2, 100, 100));
        assertEquals(5376, modPower(2, 100, 10000));
        assertEquals(712394910, modPower(9, 999999999, 998244353));
        assertEquals(285849443, modPower(-9, 999999999, 998244353));
    }
}
