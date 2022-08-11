package com.williamfiset.algorithms.math;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.math.LCM.lcm;

public class LcmTest {

    @Test
    public void testLCM1() {
        long result = (lcm(12, 18));
        assertThat(result).isEqualTo(36);
        assertThat(result).isNotEqualTo(35);
        assertThat(result).isNotEqualTo(37);
    }
    @Test
    public void testLCM2() {
        long result = (lcm(-12, 18));
        assertThat(result).isEqualTo(36);
        assertThat(result).isNotEqualTo(35);
        assertThat(result).isNotEqualTo(37);
    }
    @Test
    public void testLCM3() {
        long result = (lcm(12, -18));
        assertThat(result).isEqualTo(36);
        assertThat(result).isNotEqualTo(35);
        assertThat(result).isNotEqualTo(37);
    }

    @Test
    public void testLCM4() {
        long result = (lcm(-12, -18));
        assertThat(result).isEqualTo(36);
        assertThat(result).isNotEqualTo(35);
        assertThat(result).isNotEqualTo(37);
    }
    @Test
    public void testLCM15() {
        long result = (lcm(12, 12));
        assertThat(result).isEqualTo(12);
        assertThat(result).isNotEqualTo(0);
        assertThat(result).isNotEqualTo(13);
    }
}