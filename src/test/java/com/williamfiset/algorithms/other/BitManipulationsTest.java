package com.williamfiset.algorithms.other;

import static com.google.common.truth.Truth.assertThat;

import org.junit.jupiter.api.*;

public class BitManipulationsTest {

  @Test
  public void testSetBit() {
    assertThat(BitManipulations.setBit(0, 0)).isEqualTo(0b1);
    assertThat(BitManipulations.setBit(0, 1)).isEqualTo(0b10);
    assertThat(BitManipulations.setBit(0, 2)).isEqualTo(0b100);
    assertThat(BitManipulations.setBit(0, 3)).isEqualTo(0b1000);
    assertThat(BitManipulations.setBit(0, 4)).isEqualTo(0b10000);
    assertThat(BitManipulations.setBit(0, 5)).isEqualTo(0b100000);
  }

  @Test
  public void testPowerOfTwo() {
    assertThat(BitManipulations.isPowerOfTwo(0)).isFalse();
    assertThat(BitManipulations.isPowerOfTwo(-1)).isFalse();
    assertThat(BitManipulations.isPowerOfTwo(7)).isFalse();
    assertThat(BitManipulations.isPowerOfTwo(9)).isFalse();
    assertThat(BitManipulations.isPowerOfTwo(123456789)).isFalse();

    assertThat(BitManipulations.isPowerOfTwo(1)).isTrue();
    assertThat(BitManipulations.isPowerOfTwo(2)).isTrue();
    assertThat(BitManipulations.isPowerOfTwo(4)).isTrue();
    assertThat(BitManipulations.isPowerOfTwo(2048)).isTrue();
    assertThat(BitManipulations.isPowerOfTwo(1 << 20)).isTrue();
  }

  @Test
  public void testClearBit() {
    assertThat(BitManipulations.clearBit(0b0000, 1)).isEqualTo(0);
    assertThat(BitManipulations.clearBit(0b0100, 2)).isEqualTo(0);
    assertThat(BitManipulations.clearBit(0b0001, 0)).isEqualTo(0);
    assertThat(BitManipulations.clearBit(0b1111, 0)).isEqualTo(14);
  }
}
