package javatests.com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.primitives.Ints;
import com.williamfiset.algorithms.dp.MaximumSubarray;
import com.williamfiset.algorithms.utils.TestUtils;
import java.util.*;
import org.junit.*;

public class MaximumSubarray.java {

  @Test
  public void maximumSubarray() {
    MaximumSubarray maxSS = new MaximumSubarray();

    int maxSumSubarray = maxSS.maximumSubarrayValue(new int[] {1, 2, 1, -7, 2, -1, 40, 25}));

    assertThat(maxSumSubarray).isEqualTo(66);
  }
}
