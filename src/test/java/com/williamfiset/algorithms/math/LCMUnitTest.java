package com.williamfiset.algorithms.math;
import static com.google.common.truth.Truth.assertThat;
import org.junit.Test;

public class LCMUnitTest {
 @Test
   public void LCMEntre12e18(){
    long result = LCM.lcm(12,18);
    assertThat(result).isEqualTo(36);
    }

 @Test
   public void LCMEntreMenos12e18() {
     long result = LCM.lcm(-12, 18);
     assertThat(result).isEqualTo(36);
   }
 @Test
    public void LCMEntre12eMenos18() {
      long result = LCM.lcm(12, -18);
      assertThat(result).isEqualTo(36);
    }
 @Test
    public void LCMEntreMenos12eMenos18() {
     long result = LCM.lcm(-12, -18);
     assertThat(result).isEqualTo(36);
    }
 @Test
    public void LCMEntreMenos18eMenos5() {
        long result = LCM.lcm(-18, 5);
        assertThat(result).isEqualTo(90);
    }
 @Test
    public void LCMEntre15e20() {
        long result = LCM.lcm(15, 20);
        assertThat(result).isEqualTo(60);
    }
}
