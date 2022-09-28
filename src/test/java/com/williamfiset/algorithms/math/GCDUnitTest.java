package com.williamfiset.algorithms.math;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class GCDUnitTest {
    @Test
    public void GCDEntre12e18(){
        long result = GCD.gcd(12,18);
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void GCDEntreM12e18() {
        long result = GCD.gcd(-12, 18);
        assertThat(result).isEqualTo(6);
    }
    @Test
    public void GCDEntre12eM18() {
        long result = GCD.gcd(12, -18);
        assertThat(result).isEqualTo(6);
    }
    @Test
    public void GCDEntreM12eM18() {
        long result = GCD.gcd(-12, -18);
        assertThat(result).isEqualTo(6);
    }
    @Test
    public void GCDEntre0e0() {
        long result = GCD.gcd(0, 0);
        assertThat(result).isEqualTo(0);
    }
}

