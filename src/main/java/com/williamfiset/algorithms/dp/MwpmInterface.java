// Simple interface for MinimumWeightPerfectMatching (MWPM) solutions to simplify testing.

package com.williamfiset.algorithms.dp;

public interface MwpmInterface {
  public double getMinWeightCost();

  public int[] getMatching();
}
