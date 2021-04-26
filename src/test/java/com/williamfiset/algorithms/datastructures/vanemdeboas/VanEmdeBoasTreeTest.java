package com.williamfiset.algorithms.datastructures.vanemdeboas;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.Test;

public class VanEmdeBoasTreeTest {

  @Test
  public void testInsertSearch() {
    for (int i = 0; i < 100; i++) {
      VanEmdeBoasTree vanEmdeBoasTree = new VanEmdeBoasTree(16);
      Set<Integer> values = new HashSet<>();

      for (int j = 0; j < 16; j++) {
        if (Math.random() > 0.5) {
          values.add(j);
          vanEmdeBoasTree.add(j);
        }
      }

      List<Integer> list = IntStream.range(0, 15).boxed().collect(Collectors.toList());
      Collections.shuffle(list);

      for (int j : list) {
        if (values.contains(j)) {
          assertThat(vanEmdeBoasTree.contains(j)).isTrue();
        } else {
          assertThat(vanEmdeBoasTree.contains(j)).isFalse();
        }
      }
    }
  }

  @Test
  public void testDeleteSearch() {
    for (int i = 0; i < 100; i++) {
      VanEmdeBoasTree vanEmdeBoasTree = new VanEmdeBoasTree(16);
      Set<Integer> values = new HashSet<>();

      for (int j = 0; j < 16; j++) {
        if (Math.random() > 0.5) {
          values.add(j);
          vanEmdeBoasTree.add(j);
        }
      }

      for (Integer value : values) {
        vanEmdeBoasTree.delete(value);
        assertThat(vanEmdeBoasTree.contains(value)).isFalse();
      }
    }
  }

  @Test
  public void testPredecessor() {
    VanEmdeBoasTree vanEmdeBoasTree = new VanEmdeBoasTree(16);

    vanEmdeBoasTree.add(3);
    vanEmdeBoasTree.add(5);
    vanEmdeBoasTree.add(8);
    vanEmdeBoasTree.add(10);
    vanEmdeBoasTree.add(12);
    vanEmdeBoasTree.add(13);
    vanEmdeBoasTree.add(14);
    vanEmdeBoasTree.add(15);

    assertThat(-1).isEqualTo(vanEmdeBoasTree.predecessor(3));
    assertThat(3).isEqualTo(vanEmdeBoasTree.predecessor(5));
    assertThat(5).isEqualTo(vanEmdeBoasTree.predecessor(8));
    assertThat(8).isEqualTo(vanEmdeBoasTree.predecessor(10));
    assertThat(14).isEqualTo(vanEmdeBoasTree.predecessor(15));
  }

  @Test
  public void testMin() {
    for (int i = 0; i < 100; i++) {
      VanEmdeBoasTree vanEmdeBoasTree = new VanEmdeBoasTree(16);
      List<Integer> values = new ArrayList<>();

      for (int j = 0; j < 16; j++) {
        if (Math.random() > 0.5) {
          values.add(j);
          vanEmdeBoasTree.add(j);
        }
      }

      if (values.size() == 0) {
        continue;
      }

      assertThat(values.get(0)).isEqualTo(vanEmdeBoasTree.min());
    }
  }

  @Test
  public void testMax() {
    for (int i = 0; i < 100; i++) {
      VanEmdeBoasTree vanEmdeBoasTree = new VanEmdeBoasTree(16);
      List<Integer> values = new ArrayList<>();

      for (int j = 0; j < 16; j++) {
        if (Math.random() > 0.5) {
          values.add(j);
          vanEmdeBoasTree.add(j);
        }
      }

      if (values.size() == 0) {
        continue;
      }

      assertThat(values.get(values.size() - 1)).isEqualTo(vanEmdeBoasTree.max());
    }
  }
}
