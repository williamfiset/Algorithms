import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.ImmutableList;
import java.util.*;
import org.junit.*;

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
  
}