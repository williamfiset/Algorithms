package javatests.com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.math.ChineseRemainderTheorem;
import com.williamfiset.algorithms.utils.TestUtils;
import java.util.*;
import org.junit.*;

public class MathTest {

    @Test
    public void testIsPrime() {
      ChineseRemainderTheorem cRT = new ChineseRemainderTheorem();
      boolean v1 = cRT.isPrime(1);
      boolean v2 = cRT.isPrime(2);
      boolean v3 = cRT.isPrime(3);
      boolean v4 = cRT.isPrime(6);
      boolean v5 = cRT.isPrime(9);
      boolean v6 = cRT.isPrime(21169);
      boolean v7 = cRT.isPrime(21170);
  
      assertThat(v1).isEqualTo(false);
      assertThat(v2).isEqualTo(true);
      assertThat(v3).isEqualTo(true);
      assertThat(v4).isEqualTo(false);
      assertThat(v5).isEqualTo(false);
      assertThat(v6).isEqualTo(true);
      assertThat(v5).isEqualTo(false);
    }
  
    
  }
