package com.williamfiset.algorithms.graphtheory;

/**
 * A type of weighted edge whose weight is an integer and the ordering is defined by its weight
 * (cost).
 *
 * @author Sung Ho Yoon
 */
public class CostComparingEdge extends WeightedEdge<Integer>
    implements Comparable<CostComparingEdge> {
  /**
   * Constructs a {@code CostComparingEdge}.
   *
   * @param from the source node
   * @param to the destination node
   * @param cost the cost associated with the edge
   */
  public CostComparingEdge(int from, int to, int cost) {
    super(from, to, cost);
  }

  /**
   * Compares this edge with the specified edge for order. If the weights of the two edges are
   * equal, then the ordering is determined by natural ordering of the source and destination nodes.
   *
   * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
   *     or greater than the specified edge
   */
  @Override
  public int compareTo(CostComparingEdge other) {
    if (getCost() != other.getCost()) return getCost() - other.getCost();
    if (getFrom() != other.getFrom()) return getFrom() - other.getFrom();
    return getTo() - other.getTo();
  }
}
