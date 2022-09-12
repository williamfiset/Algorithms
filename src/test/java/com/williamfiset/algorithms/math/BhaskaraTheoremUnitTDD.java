package com.williamfiset.algorithms.math;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;

public class BhaskaraTheoremUnitTDD {

    @Test
    public void CalculoBhaskaraEntre4e5e6() {
        int result = BhaskaraTheorem.bhask(4, 5, 6);
        assertThat(result).isEqualTo(-1);
    }
    @Test
    public void CalculoBhaskaraEntre4e48e6() {
        int result = BhaskaraTheorem.bhask(4, 48, 6);
        assertThat(result).isEqualTo(2208);
    }

    @Test
    public void CalculoBhaskaraEntre0e0e0() {
        int result = BhaskaraTheorem.bhask(0, 0, 0);
        assertThat(result).isEqualTo(0);

    }
}