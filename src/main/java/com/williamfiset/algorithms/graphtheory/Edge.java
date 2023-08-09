package com.williamfiset.algorithms.graphtheory;

import java.util.Objects;

/**
 * A generic representation of an edge of a graph.
 *
 * @author Sung Ho Yoon
 */
public class Edge {
  /** The source node */
  private int from;

  /** The destination node */
  private int to;

  /**
   * Constructs a new {@code Edge}.
   *
   * @param from the source node
   * @param to the destination node
   */
  public Edge(int from, int to) {
    this.from = from;
    this.to = to;
  }

  /**
   * Returns the source node of this edge.
   *
   * @return the source node of this edge
   */
  public final int getFrom() {
    return from;
  }

  /**
   * Returns the destination node of this edge.
   *
   * @return the destination node of this edge
   */
  public final int getTo() {
    return to;
  }

  /**
   * Checks whether some object is "equal to" this edge.
   *
   * @return {@code true} if argument is equal to this edge
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    else if (obj instanceof Edge) {
      Edge edge = (Edge) obj;
      return this.from == edge.from && this.to == edge.to;
    } else return false;
  }

  /**
   * Returns a hash code value for this edge.
   *
   * @return a hash code value
   */
  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }

  /**
   * Returns a string representation of this edge.
   *
   * @return a string representation of this edge
   */
  @Override
  public String toString() {
    return from + ":" + to;
  }
}
