package com.williamfiset.algorithms.datastructures.unionfind;

// import static org.junit.Assert.*;
import static com.google.common.truth.Truth.assertThat;

import org.junit.*;

public class UnionFindTest {

  @Test
  public void testNumComponents() {

    UnionFind uf = new UnionFind(5);
    assertThat(uf.components()).isEqualTo(5);

    uf.unify(0, 1);
    assertThat(uf.components()).isEqualTo(4);

    uf.unify(1, 0);
    assertThat(uf.components()).isEqualTo(4);

    uf.unify(1, 2);
    assertThat(uf.components()).isEqualTo(3);

    uf.unify(0, 2);
    assertThat(uf.components()).isEqualTo(3);

    uf.unify(2, 1);
    assertThat(uf.components()).isEqualTo(3);

    uf.unify(3, 4);
    assertThat(uf.components()).isEqualTo(2);

    uf.unify(4, 3);
    assertThat(uf.components()).isEqualTo(2);

    uf.unify(1, 3);
    assertThat(uf.components()).isEqualTo(1);

    uf.unify(4, 0);
    assertThat(uf.components()).isEqualTo(1);
  }

  @Test
  public void testComponentSize() {

    UnionFind uf = new UnionFind(5);
    assertThat(uf.componentSize(0)).isEqualTo(1);
    assertThat(uf.componentSize(1)).isEqualTo(1);
    assertThat(uf.componentSize(2)).isEqualTo(1);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(0, 1);
    assertThat(uf.componentSize(0)).isEqualTo(2);
    assertThat(uf.componentSize(1)).isEqualTo(2);
    assertThat(uf.componentSize(2)).isEqualTo(1);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(1, 0);
    assertThat(uf.componentSize(0)).isEqualTo(2);
    assertThat(uf.componentSize(1)).isEqualTo(2);
    assertThat(uf.componentSize(2)).isEqualTo(1);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(1, 2);
    assertThat(uf.componentSize(0)).isEqualTo(3);
    assertThat(uf.componentSize(1)).isEqualTo(3);
    assertThat(uf.componentSize(2)).isEqualTo(3);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(0, 2);
    assertThat(uf.componentSize(0)).isEqualTo(3);
    assertThat(uf.componentSize(1)).isEqualTo(3);
    assertThat(uf.componentSize(2)).isEqualTo(3);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(2, 1);
    assertThat(uf.componentSize(0)).isEqualTo(3);
    assertThat(uf.componentSize(1)).isEqualTo(3);
    assertThat(uf.componentSize(2)).isEqualTo(3);
    assertThat(uf.componentSize(3)).isEqualTo(1);
    assertThat(uf.componentSize(4)).isEqualTo(1);

    uf.unify(3, 4);
    assertThat(uf.componentSize(0)).isEqualTo(3);
    assertThat(uf.componentSize(1)).isEqualTo(3);
    assertThat(uf.componentSize(2)).isEqualTo(3);
    assertThat(uf.componentSize(3)).isEqualTo(2);
    assertThat(uf.componentSize(4)).isEqualTo(2);

    uf.unify(4, 3);
    assertThat(uf.componentSize(0)).isEqualTo(3);
    assertThat(uf.componentSize(1)).isEqualTo(3);
    assertThat(uf.componentSize(2)).isEqualTo(3);
    assertThat(uf.componentSize(3)).isEqualTo(2);
    assertThat(uf.componentSize(4)).isEqualTo(2);

    uf.unify(1, 3);
    assertThat(uf.componentSize(0)).isEqualTo(5);
    assertThat(uf.componentSize(1)).isEqualTo(5);
    assertThat(uf.componentSize(2)).isEqualTo(5);
    assertThat(uf.componentSize(3)).isEqualTo(5);
    assertThat(uf.componentSize(4)).isEqualTo(5);

    uf.unify(4, 0);
    assertThat(uf.componentSize(0)).isEqualTo(5);
    assertThat(uf.componentSize(1)).isEqualTo(5);
    assertThat(uf.componentSize(2)).isEqualTo(5);
    assertThat(uf.componentSize(3)).isEqualTo(5);
    assertThat(uf.componentSize(4)).isEqualTo(5);
  }

  @Test
  public void testConnectivity() {

    int sz = 7;
    UnionFind uf = new UnionFind(sz);

    for (int i = 0; i < sz; i++) assertThat(uf.connected(i, i)).isTrue();

    uf.unify(0, 2);

    assertThat(uf.connected(0, 2)).isTrue();
    assertThat(uf.connected(2, 0)).isTrue();

    assertThat(uf.connected(0, 1)).isFalse();
    assertThat(uf.connected(3, 1)).isFalse();
    assertThat(uf.connected(6, 4)).isFalse();
    assertThat(uf.connected(5, 0)).isFalse();

    for (int i = 0; i < sz; i++) assertThat(uf.connected(i, i)).isTrue();

    uf.unify(3, 1);

    assertThat(uf.connected(0, 2)).isTrue();
    assertThat(uf.connected(2, 0)).isTrue();
    assertThat(uf.connected(1, 3)).isTrue();
    assertThat(uf.connected(3, 1)).isTrue();

    assertThat(uf.connected(0, 1)).isFalse();
    assertThat(uf.connected(1, 2)).isFalse();
    assertThat(uf.connected(2, 3)).isFalse();
    assertThat(uf.connected(1, 0)).isFalse();
    assertThat(uf.connected(2, 1)).isFalse();
    assertThat(uf.connected(3, 2)).isFalse();

    assertThat(uf.connected(1, 4)).isFalse();
    assertThat(uf.connected(2, 5)).isFalse();
    assertThat(uf.connected(3, 6)).isFalse();

    for (int i = 0; i < sz; i++) assertThat(uf.connected(i, i)).isTrue();

    uf.unify(2, 5);
    assertThat(uf.connected(0, 2)).isTrue();
    assertThat(uf.connected(2, 0)).isTrue();
    assertThat(uf.connected(1, 3)).isTrue();
    assertThat(uf.connected(3, 1)).isTrue();
    assertThat(uf.connected(0, 5)).isTrue();
    assertThat(uf.connected(5, 0)).isTrue();
    assertThat(uf.connected(5, 2)).isTrue();
    assertThat(uf.connected(2, 5)).isTrue();

    assertThat(uf.connected(0, 1)).isFalse();
    assertThat(uf.connected(1, 2)).isFalse();
    assertThat(uf.connected(2, 3)).isFalse();
    assertThat(uf.connected(1, 0)).isFalse();
    assertThat(uf.connected(2, 1)).isFalse();
    assertThat(uf.connected(3, 2)).isFalse();

    assertThat(uf.connected(4, 6)).isFalse();
    assertThat(uf.connected(4, 5)).isFalse();
    assertThat(uf.connected(1, 6)).isFalse();

    for (int i = 0; i < sz; i++) assertThat(uf.connected(i, i)).isTrue();

    // Connect everything
    uf.unify(1, 2);
    uf.unify(3, 4);
    uf.unify(4, 6);

    for (int i = 0; i < sz; i++) {
      for (int j = 0; j < sz; j++) {
        assertThat(uf.connected(i, j)).isTrue();
      }
    }
  }

  @Test
  public void testSize() {

    UnionFind uf = new UnionFind(5);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(0, 1);
    uf.find(3);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(1, 2);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(0, 2);
    uf.find(1);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(2, 1);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(3, 4);
    uf.find(0);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(4, 3);
    uf.find(3);
    assertThat(uf.size()).isEqualTo(5);
    uf.unify(1, 3);
    assertThat(uf.size()).isEqualTo(5);
    uf.find(2);
    uf.unify(4, 0);
    assertThat(uf.size()).isEqualTo(5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation1() {
    new UnionFind(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation2() {
    new UnionFind(-3463);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBadUnionFindCreation3() {
    new UnionFind(0);
  }
}
