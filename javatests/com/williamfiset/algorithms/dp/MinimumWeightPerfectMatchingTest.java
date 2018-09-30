package javatests.com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.utils.TestUtils;
import com.williamfiset.algorithms.dp.MinimumWeightPerfectMatching;

import java.util.*;
import org.junit.*;

public class MinimumWeightPerfectMatchingTest {
  
  @Test
  public void testMatchingAndCostAreConsistent() {

    for (int loop = 0; loop < 50; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2;
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      int[] matching = mwpm.getMinWeightCostMatching();
      double totalMinCost = 0;
      for (int i = 0; i < matching.length / 2; i++) {
        int ii = matching[2*i];
        int jj = matching[2*i+1];
        totalMinCost += costMatrix[ii][jj];
      }
      assertThat(totalMinCost).isEqualTo(mwpm.getMinWeightCost());
    }

  }

  public void randomFillSymmetricMatrix(double[][] dist) {
    for (int i = 0; i < dist.length; i++) {
      for (int j = i+1; j < dist.length; j++) {
        double val = (int)(Math.random() * 10000);
        dist[i][j] = dist[j][i] = val;
      }
    }
  }
  
}