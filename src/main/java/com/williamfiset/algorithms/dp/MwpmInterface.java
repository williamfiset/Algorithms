package com.williamfiset.algorithms.dp;

// Simple interface for MinimumWeightPerfectMatching (MWPM) solutions to simplify testing.
public interface MwpmInterface {
  // Gets the minimum weight matching cost
  public double getMinWeightCost();

  // Returns an optimal matching.
  public int[] getMatching();
}
