package com.williamfiset.algorithms.math;

import com.williamfiset.algorithms.other.BitManipulations;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;

public class CompoundInterestTest {
    @Test
    public void testInterest() {
        assertThat(CompoundInterest.CompoundInterest(120,0.1,12,2)).isEqualTo(146.45);
        assertThat(CompoundInterest.CompoundInterest(.5,0.25,4,12)).isEqualTo(9.18);
        assertThat(CompoundInterest.CompoundInterest(12000,0.05,1,5)).isEqualTo(15315.38);
        assertThat(CompoundInterest.CompoundInterest(10,0.1,2,10)).isEqualTo(26.53);
    }
}
