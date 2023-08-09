package com.williamfiset.algorithms.graphtheory;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * A generic representation of a weighted edge of a graph.
 *
 * @author Sung Ho Yoon
 */
public class WeightedEdge<T extends Number> extends Edge {
  /** The weight (cost) associated with this edge */
  private T cost;

  private Predicate<T> costCondition;

  /**
   * Constructs a new weighted edge.
   *
   * @param from the source node
   * @param to the destination node
   * @param cost the cost associated with the edge
   */
  public WeightedEdge(int from, int to, T cost) {
    super(from, to);
    this.costCondition = (c) -> true;
    this.cost = Objects.requireNonNull(cost);
  }

  /**
   * Constructs a new weighted edge.
   *
   * @param from the source node
   * @param to the destination node
   * @param cost the cost associated with the edge
   * @param costCondition the condition that the cost must satisfy
   * @throws IllegalArgumentException if the specified cost does not satisfy the provided condition
   * @throws NullPointerException if any argument is {@code null}
   */
  public WeightedEdge(int from, int to, T cost, Predicate<T> costCondition) {
    super(from, to);
    this.costCondition = Objects.requireNonNull(costCondition);
    setCost(cost);
  }

  /**
   * Returns the weight (cost) associated with this edge.
   *
   * @return the weight (cost) associated with this edge
   */
  public final T getCost() {
    return cost;
  }

  /**
   * Sets the weight (cost) associated with this edge.
   *
   * @param cost the new weight (cost)
   * @throws IllegalArgumentException if the specified cost does not satisfy the provided condition
   *     (if one was provided)
   * @throws NullPointerException if argument is {@code null}
   */
  public void setCost(T cost) {
    if (!this.costCondition.test(Objects.requireNonNull(cost))) {
      throw new IllegalArgumentException("Invalid cost: " + cost);
    }
    this.cost = cost;
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    else if (super.equals(obj) && obj instanceof WeightedEdge) {
      WeightedEdge<?> edge = (WeightedEdge<?>) obj;
      return Objects.equals(this.cost, edge.cost);
    } else return false;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return Objects.hash(getFrom(), getTo(), cost);
  }
}
