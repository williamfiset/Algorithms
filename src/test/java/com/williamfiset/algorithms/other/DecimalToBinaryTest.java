package com.williamfiset.algorithms.other;
import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.math.DecimalToBinary.decimaltobinary;

public class DecimalToBinaryTest {

    @Test
    public void testDecimalToBinary1() {
        long result = decimaltobinary(10);
        assertThat(result).isEqualTo(1010);
    }

    @Test
    public void testDecimalToBinary2() {
        long result = decimaltobinary(11);
        assertThat(result).isEqualTo(1011);
    }

    @Test
    public void testDecimalToBinary3() {
        long result = decimaltobinary(12);
        assertThat(result).isEqualTo(1100);
    }

    @Test
    public void testDecimalToBinary4() {
        long result = decimaltobinary(0);
        assertThat(result).isGreaterThan(-1);
    }

    @Test
    public void testDecimalToBinary5() {
        long result = decimaltobinary(100);
        assertThat(result).isNotNull();
    }
}

