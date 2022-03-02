package com.williamfiset.algorithms.dp;

// Simple interface for MinimumWeightPerfectMatching (MWPM) solutions to simplify testing.
public interface MwpmInterface {
  public double getMinWeightCost();

  public int[] getMatching();
}
