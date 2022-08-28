package test.java.com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.*;
import org.junit.jupiter.api.DisplayName;

public class ArmstrongNumberTest {
    @Test
    @DisplayName("Valid inputs and invalid inputs")
    public void armstrongNumberTest(){
        ArmstrongNumber armstrong = new Armstrong();
        assertThat(armstrong.isArmstrongNumber(1)).isTrue();
        assertThat(armstrong.isArmstrongNumber(153)).isTrue();
        assertThat(armstrong.isArmstrongNumber(10)).isFalse();
        assertThat(armstrong.isArmstrongNumber(300)).isFalse();
    }
}