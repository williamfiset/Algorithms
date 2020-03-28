package com.williamfiset.algorithms.utils.graphutils;

import java.util.*;

public class GraphGenerator {

  public static class DagGenerator {
    double edgeProbability;
    int minLevels, maxLevels, minNodesPerLevel, maxNodesPerLevel;

    // Generates a DAg gives several parameters. Make sure the edge probability
    // is high enough to make a mostly connected graph.
    public DagGenerator(
        int minLevels,
        int maxLevels,
        int minNodesPerLevel,
        int maxNodesPerLevel,
        double edgeProbability) {
      this.minLevels = minLevels;
      this.maxLevels = maxLevels;
      this.minNodesPerLevel = minNodesPerLevel;
      this.maxNodesPerLevel = maxNodesPerLevel;
      this.edgeProbability = edgeProbability;
    }

    /**
     * @param min - The minimum.
     * @param max - The maximum.
     * @return A random double between these numbers (inclusive the minimum and maximum).
     */
    private static int rand(int min, int max) {
      return min + (int) (Math.random() * ((max - min) + 1));
    }

    public List<List<Integer>> createDag() {
      int levels = rand(minLevels, maxLevels);
      int[] nodesPerLevel = new int[levels];

      int n = 0;
      for (int l = 0; l < levels; l++) {
        nodesPerLevel[l] = rand(minNodesPerLevel, maxNodesPerLevel);
        n += nodesPerLevel[l];
      }
      List<List<Integer>> g = Utils.createEmptyAdjacencyList(n);
      int levelIndex = 0;
      for (int l = 0; l < levels - 1; l++) { // For each level
        for (int i = 0; i < nodesPerLevel[l]; i++) { // for each node on each level
          for (int j = 0; j < nodesPerLevel[l + 1]; j++) { // for each possible edge link
            if (Math.random() <= edgeProbability) {
              Utils.addDirectedEdge(g, levelIndex + i, levelIndex + nodesPerLevel[l] + j);
            }
          }
        }
        levelIndex += nodesPerLevel[l];
      }
      return g;
    }
  }

  public static void main(String[] args) {
    DagGenerator gen = new DagGenerator(10, 10, 5, 5, 0.9);
    gen.createDag();
  }
}
