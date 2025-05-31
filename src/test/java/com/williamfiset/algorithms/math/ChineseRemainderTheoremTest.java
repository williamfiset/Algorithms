package com.williamfiset.algorithms.math;

import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import static org.junit.jupiter.api.Assertions.*;

class ChineseRemainderTheoremTest {

    @Test
    void testCRT_TwoEquations() {
        BigInteger[] a = {BigInteger.valueOf(0), BigInteger.valueOf(3)};
        BigInteger[] m = {BigInteger.valueOf(4), BigInteger.valueOf(5)};
        BigInteger result = ChineseRemainderTheorem.chineseRemainder(2, a, m);
        assertEquals(BigInteger.valueOf(8), result);
    }

    @Test
    void testCRT_ThreeEquations() {
        BigInteger[] a = {BigInteger.valueOf(2), BigInteger.valueOf(3), BigInteger.valueOf(2)};
        BigInteger[] m = {BigInteger.valueOf(3), BigInteger.valueOf(5), BigInteger.valueOf(7)};
        BigInteger result = ChineseRemainderTheorem.chineseRemainder(3, a, m);
        assertEquals(BigInteger.valueOf(23), result);
    }

    @Test
    void testCRT_FourEquations() {
        BigInteger[] a = {
            BigInteger.valueOf(1),
            BigInteger.valueOf(2),
            BigInteger.valueOf(3),
            BigInteger.valueOf(4)
        };
        BigInteger[] m = {
            BigInteger.valueOf(5),
            BigInteger.valueOf(7),
            BigInteger.valueOf(9),
            BigInteger.valueOf(11)
        };
        BigInteger result = ChineseRemainderTheorem.chineseRemainder(4, a, m);
        
        assertEquals(BigInteger.valueOf(1731), result);
    }

    @Test
    void testArePairwiseCoprime_True() {
        BigInteger[] m = {
            BigInteger.valueOf(3),
            BigInteger.valueOf(5),
            BigInteger.valueOf(7)
        };
        assertTrue(ChineseRemainderTheorem.arePairwiseCoprime(3, m));
    }

    @Test
    void testArePairwiseCoprime_False() {
        BigInteger[] m = {
            BigInteger.valueOf(6),
            BigInteger.valueOf(8),
            BigInteger.valueOf(9)
        };
        assertFalse(ChineseRemainderTheorem.arePairwiseCoprime(3, m));
    }

    @Test
    void testCRT_LargeNumbers() {
        BigInteger[] a = {
            BigInteger.valueOf(123456),
            BigInteger.valueOf(789012),
            BigInteger.valueOf(345678)
        };
        BigInteger[] m = {
            BigInteger.valueOf(1000003),
            BigInteger.valueOf(1000033),
            BigInteger.valueOf(1000037)
        };
        BigInteger result = ChineseRemainderTheorem.chineseRemainder(3, a, m);
        
        assertEquals(a[0], result.mod(m[0]));
        assertEquals(a[1], result.mod(m[1]));
        assertEquals(a[2], result.mod(m[2]));
    }
}