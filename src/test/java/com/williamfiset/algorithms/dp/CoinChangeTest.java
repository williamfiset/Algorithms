package com.williamfiset.algorithms.dp;

import com.google.common.primitives.Ints;
import com.williamfiset.algorithms.utils.TestUtils;
import org.junit.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class CoinChangeTest {

  static final int LOOPS = 100;

  @Test
  public void testCoinChange() {
    for (int i = 1; i < LOOPS; i++) {
      List<Integer> values = TestUtils.randomIntegerList(i, 1, 1000);
      int[] coinValues = Ints.toArray(values);

      int amount = TestUtils.randValue(1, 1000);

      int v1 = CoinChange.coinChange(coinValues, amount);
      int v2 = CoinChange.coinChangeSpaceEfficient(coinValues, amount);
      int v3 = CoinChange.coinChangeRecursive(coinValues, amount);

      assertThat(v1).isEqualTo(v2);
      assertThat(v2).isEqualTo(v3);
    }
  }
}
