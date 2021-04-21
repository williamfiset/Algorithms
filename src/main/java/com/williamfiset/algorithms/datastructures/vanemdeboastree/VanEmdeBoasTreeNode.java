package com.williamfiset.algorithms.datastructures.vanemdeboastree;

public class VanEmdeBoasTreeNode {

  public int size;
  public int min;
  public int max;
  public VanEmdeBoasTreeNode summary;
  public VanEmdeBoasTreeNode[] cluster;

  public VanEmdeBoasTreeNode(int size) {
    this.size = size;
    min = -1;
    max = -1;

    initializeChildren(size);
  }

  private void initializeChildren(int size) {
    if (size <= 2) {
      summary = null;
      cluster = null;
    } else {
      int childSize = (int) Math.pow(2, Math.ceil((Math.log10(size) / Math.log10(2)) / 2));

      summary = new VanEmdeBoasTreeNode(childSize);
      cluster = new VanEmdeBoasTreeNode[childSize];

      for (int i = 0; i < childSize; i++) {
        cluster[i] = new VanEmdeBoasTreeNode(childSize);
      }
    }
  }
}
