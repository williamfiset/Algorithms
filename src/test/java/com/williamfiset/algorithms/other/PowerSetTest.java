package com.williamfiset.algorithms.other;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import java.util.stream.Collectors;
import org.junit.jupiter.api.*;

public class PowerSetTest {

  /** Converts a power set to a set of sorted string representations for easy comparison. */
  private static <T extends Comparable<T>> Set<String> normalize(List<List<T>> powerSet) {
    return powerSet.stream()
        .map(subset -> {
          List<T> sorted = new ArrayList<>(subset);
          Collections.sort(sorted);
          return sorted.toString();
        })
        .collect(Collectors.toSet());
  }

  @Test
  public void emptySet_binary() {
    List<List<Integer>> result = PowerSet.powerSetBinary(List.of());
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEmpty();
  }

  @Test
  public void emptySet_recursive() {
    List<List<Integer>> result = PowerSet.powerSetRecursive(List.of());
    assertThat(result).hasSize(1);
    assertThat(result.get(0)).isEmpty();
  }

  @Test
  public void singleElement_binary() {
    List<List<Integer>> result = PowerSet.powerSetBinary(List.of(42));
    assertThat(normalize(result)).containsExactly("[]", "[42]");
  }

  @Test
  public void singleElement_recursive() {
    List<List<Integer>> result = PowerSet.powerSetRecursive(List.of(42));
    assertThat(normalize(result)).containsExactly("[]", "[42]");
  }

  @Test
  public void threeElements_binary() {
    List<List<Integer>> result = PowerSet.powerSetBinary(List.of(1, 2, 3));
    assertThat(result).hasSize(8);
    assertThat(normalize(result))
        .containsExactly("[]", "[1]", "[2]", "[3]", "[1, 2]", "[1, 3]", "[2, 3]", "[1, 2, 3]");
  }

  @Test
  public void threeElements_recursive() {
    List<List<Integer>> result = PowerSet.powerSetRecursive(List.of(1, 2, 3));
    assertThat(result).hasSize(8);
    assertThat(normalize(result))
        .containsExactly("[]", "[1]", "[2]", "[3]", "[1, 2]", "[1, 3]", "[2, 3]", "[1, 2, 3]");
  }

  @Test
  public void bothMethodsProduceSameSubsets() {
    List<Integer> set = List.of(5, 10, 15, 20);
    Set<String> binary = normalize(PowerSet.powerSetBinary(set));
    Set<String> recursive = normalize(PowerSet.powerSetRecursive(set));
    assertThat(binary).isEqualTo(recursive);
  }

  @Test
  public void correctSize() {
    for (int n = 0; n <= 5; n++) {
      List<Integer> set = new ArrayList<>();
      for (int i = 0; i < n; i++)
        set.add(i);
      assertThat(PowerSet.powerSetBinary(set)).hasSize(1 << n);
      assertThat(PowerSet.powerSetRecursive(set)).hasSize(1 << n);
    }
  }

  @Test
  public void worksWithStrings() {
    List<List<String>> result = PowerSet.powerSetBinary(List.of("a", "b"));
    assertThat(normalize(result)).containsExactly("[]", "[a]", "[b]", "[a, b]");
  }
}
