package com.williamfiset.algorithms.strings;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LevenshteinDistanceTest {

  private LevenshteinDistance levenshteinDistance;

  @BeforeEach
  public void setUp() {
    levenshteinDistance = new LevenshteinDistance();
  }

  @Test
  public void calculateDistanceTest() {
    assertEquals(2, levenshteinDistance.calculateDistance("Java", "javac"));
    assertEquals(1, levenshteinDistance.calculateDistance("Rust", "rust"));
    assertEquals(0, levenshteinDistance.calculateDistance("Spring", "Spring"));
    assertEquals(0, levenshteinDistance.calculateDistance("Rest API", "Rest API"));
    assertEquals(3, levenshteinDistance.calculateDistance("Rest API", "Rest api"));
    assertEquals(
        416,
        levenshteinDistance.calculateDistance(
            "gPfjkLwbvCdRmnXiQhZtyuEsWvaYozLqBMneRxPqOmgLjdSkFzuVrCtXpoKmBvJnzTiSqWmxOtPqlReVdSkiUmW"
                + "vtXyNpsJfZoElQuRhKjGnLbZtYuVmAsPnRjMiQxOeLsKmWpTvZcQdNfHbRvTyWlXpMjOoKbZvFnTrXsPnMdJlQyUwHrKjGmAzXoVoPqTiS"
                + "fYzUcNdLgKjBvQrXySnJtZlMpFqWvGsNzIrYoXtPuKdVcQhWpYlRnMgJsKqOtPiYxZrCwFvShNtLkMoQjTxUbWrXpVzFmJrGuSnOpKzJyR"
                + "mQhPzTlWvBqMvJkKsLnGrXyFtNkZoPqJmEyWsFuCzHrRkTuLbJoQvNfGtYwVrYsXzPqLeVjTdKcWoMnZbRfHuJpXqNsMiVvZoFtKlDjUyN"
                + "rVwYtPsBoLgKvZdHrLqUmXpNzJwOvFyRdHtCsKxVmPoJzWbQvShLpNr",
            "nWjPuQfRkTgLxMbVhZySdIcKnOwXoEqVzCjBpSlDrUmAtYvJzWgNxQmUoErCsPlRfTgLbVwXtMjQhXvNsMpKzDrYcVtWlBzJsPkQnUvHaZr"
                + "LdNiVwQmGfXoUeArSkQmWtHpVrLyQkNwTrXsUzDcKoJmLvHpRkYoQwUsFiXbMjGtVkPnWoUsXvZfRqOiTnLrGmYqKsPhXwDbTjLgVkQpNj"
                + "WxFbErVhMuCnXoWpVjLbZqTdWmHpZkUfQiSnJrXaZtKpLwGrOzWyLsUmQjNvGvFbXuLqPsNrMnXtViQkVpOySqMuKtPcNrRzLhJxWoVfQrX"
                + "lGrQyKdXnJiMtPsBuLoGfWzTdVjIrCoZlHsXpNmVpKrOhRfXwGpWvBsKjOyTvDbMiVjTuYpQrKnYuLbPzFhUpQsMlJtWsNrVoPjXnKzRdIkNxSfOlRpKrUhPq"));
  }

  public @Test void calculateDistanceWithEmptyOriginTest() {
    assertEquals(9, levenshteinDistance.calculateDistance("", "Algorithm"));
  }

  @Test
  public void calculateDistanceWithEmptyDestinyTest() {
    assertEquals(9, levenshteinDistance.calculateDistance("Algorithm", ""));
  }

  @Test
  public void calculateDistanceWithTwoEmptyStringsTest() {
    assertEquals(0, levenshteinDistance.calculateDistance("", ""));
  }
}
