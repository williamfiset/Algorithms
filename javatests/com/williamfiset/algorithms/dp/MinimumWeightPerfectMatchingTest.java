package javatests.com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import com.williamfiset.algorithms.dp.MinimumWeightPerfectMatching;

import java.util.*;
import org.junit.*;

public class MinimumWeightPerfectMatchingTest {

  static class BruteForceMwpm {
    private int n;
    private double[][] matrix;
    private double minWeightMatching;

    public BruteForceMwpm(double[][] matrix) {
      this.matrix = matrix;
      this.n = matrix.length;
    }

    public double getMinWeightCost() {
      solve();
      return minWeightMatching;
    }

    public double computeMatchingCost(int[] p) {
      double t = 0;
      for (int i = 0; i < n/2; i++) {
        int ii = p[2*i];
        int jj = p[2*i+1];
        t += matrix[ii][jj];
      }
      return t;
    }

    public void solve() {
      int[] permutation = new int[n];
      for(int i = 0; i < n; i++) permutation[i] = i;

      double bestMatchingCost = Double.POSITIVE_INFINITY;

      // Try all matchings
      do {
        double matchingCost = computeMatchingCost(permutation);
        if (matchingCost < bestMatchingCost) {
          bestMatchingCost = matchingCost;
        }
      } while(nextPermutation(permutation));
      minWeightMatching = bestMatchingCost;
    }

    // Generates the next ordered permutation in-place (skips repeated permutations).
    // Calling this when the array is already at the highest permutation returns false.
    // Recommended usage is to start with the smallest permutations and use a do while
    // loop to generate each successive permutations (see main for example).
    public static boolean nextPermutation(int [] sequence) {
      int first = getFirst(sequence);
      if (first == -1) return false;
      int toSwap = sequence.length - 1;
      while (sequence[first] >= sequence[toSwap]) --toSwap;
      swap(sequence, first++, toSwap); toSwap = sequence.length - 1;
      while (first < toSwap) swap(sequence, first++, toSwap--);
      return true;
    }
    
    private static int getFirst(int [] sequence) {
      for (int i = sequence.length - 2; i >= 0; --i)
        if (sequence[i] < sequence[i + 1]) return i;
      return -1;
    }

    private static void swap(int [] sequence, int i, int j) {
      int tmp = sequence[i];
      sequence[i] = sequence[j];
      sequence[j] = tmp;
    }
  }

  @Test
  public void testMatchingOutputsUniqueNodes() {
    for (int loop = 0; loop < 10; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      int[] matching = mwpm.getMinWeightCostMatching();
      Set<Integer> set = new HashSet<>();
      for (int i = 0; i < matching.length; i++) {
        set.add(matching[i]);
      }
      
      assertThat(set.size()).isEqualTo(matching.length);
    }
  }
  
  @Test
  public void testMatchingAndCostAreConsistent() {
    for (int loop = 0; loop < 50; loop++) {
      int n = Math.max(1, (int) (Math.random() * 11)) * 2; // n is either 2,4,6,8,10,12,14,16,18,20
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

  @Test
  public void testAgainstBruteForce() {
    for (int loop = 0; loop < 50; loop++) {
      int n = Math.max(1, (int) (Math.random() * 6)) * 2; // n is either 2,4,6,8, or 10
      double[][] costMatrix = new double[n][n];
      randomFillSymmetricMatrix(costMatrix);

      MinimumWeightPerfectMatching mwpm = new MinimumWeightPerfectMatching(costMatrix);
      BruteForceMwpm bfMwpm = new BruteForceMwpm(costMatrix);
      double dpSoln = mwpm.getMinWeightCost();
      double bfSoln = bfMwpm.getMinWeightCost();
      assertThat(dpSoln).isEqualTo(bfSoln);
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












