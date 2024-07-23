package com.williamfiset.algorithms.math;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static com.williamfiset.algorithms.math.PerfectNumber.isPerfect;

class PerfectNumberTest {
    private final Random random = new Random();

    @Test
    void testIsPerfect() {
        assertFalse(isPerfect(random.nextInt(Integer.MIN_VALUE, 6)));
        assertFalse(isPerfect(random.nextLong(Long.MIN_VALUE, 6L)));

        assertTrue(isPerfect(6));
        assertTrue(isPerfect(6L));

        assertFalse(isPerfect(random.nextInt(7, 28)));
        assertFalse(isPerfect(random.nextLong(7L, 28L)));

        assertTrue(isPerfect(28));
        assertTrue(isPerfect(28L));

        assertFalse(isPerfect(random.nextInt(29, 8128)));
        assertFalse(isPerfect(random.nextLong(29L, 8128L)));

        assertTrue(isPerfect(8128));
        assertTrue(isPerfect(8128L));

        assertFalse(isPerfect(random.nextInt(8129, 33550336)));
        assertFalse(isPerfect(random.nextLong(8129L, 33550336L)));

        assertTrue(isPerfect(33550336));
        assertTrue(isPerfect(33550336L));
    }
}