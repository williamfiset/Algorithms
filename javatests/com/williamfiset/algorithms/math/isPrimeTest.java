package javatests.com.williamfiset.algorithms.math;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.math.ChineseRemainderTheorem;
import com.williamfiset.algorithms.utils.TestUtils;
import java.util.*;
import org.junit.*;

public class isPrimeTest {

    @Test
    public void testIsPrime() {
      ChineseRemainderTheorem cRT = new ChineseRemainderTheorem();
      boolean output = cRT.isPrime(21169);
      assertThat(output).isEqualTo(true);
    }
  }
