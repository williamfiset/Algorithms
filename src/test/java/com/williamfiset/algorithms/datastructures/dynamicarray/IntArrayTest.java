package com.williamfiset.algorithms.datastructures.dynamicarray;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.*;

public class IntArrayTest {

  @Test
  public void testEmptyArray() {
    IntArray ar = new IntArray();
    assertThat(ar.size()).isEqualTo(0);
    assertThat(ar.isEmpty()).isTrue();
  }

  @Test
  public void testIllegalCapacity() {
    assertThrows(IllegalArgumentException.class, () -> new IntArray(-1));
  }

  @Test
  public void testNullArrayConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new IntArray(null));
  }

  @Test
  public void testZeroCapacity() {
    IntArray ar = new IntArray(0);
    assertThat(ar.isEmpty()).isTrue();
    ar.add(5);
    assertThat(ar.size()).isEqualTo(1);
    assertThat(ar.get(0)).isEqualTo(5);
  }

  @Test
  public void testConstructFromArray() {
    IntArray ar = new IntArray(new int[] {1, 2, 3, 4});
    assertThat(ar.size()).isEqualTo(4);
    assertThat(ar.get(0)).isEqualTo(1);
    assertThat(ar.get(3)).isEqualTo(4);
  }

  @Test
  public void testConstructFromArrayIsCopy() {
    int[] original = {1, 2, 3};
    IntArray ar = new IntArray(original);
    original[0] = 99;
    assertThat(ar.get(0)).isEqualTo(1);
  }

  @Test
  public void testAdd() {
    IntArray ar = new IntArray();
    ar.add(1);
    ar.add(2);
    ar.add(3);
    assertThat(ar.size()).isEqualTo(3);
    assertThat(ar.get(0)).isEqualTo(1);
    assertThat(ar.get(1)).isEqualTo(2);
    assertThat(ar.get(2)).isEqualTo(3);
  }

  @Test
  public void testAddBeyondCapacity() {
    IntArray ar = new IntArray(2);
    for (int i = 0; i < 100; i++) {
      ar.add(i);
    }
    assertThat(ar.size()).isEqualTo(100);
    for (int i = 0; i < 100; i++) {
      assertThat(ar.get(i)).isEqualTo(i);
    }
  }

  @Test
  public void testGetOutOfBounds() {
    IntArray ar = new IntArray();
    ar.add(1);
    assertThrows(IndexOutOfBoundsException.class, () -> ar.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> ar.get(1));
  }

  @Test
  public void testGetOnEmpty() {
    IntArray ar = new IntArray();
    assertThrows(IndexOutOfBoundsException.class, () -> ar.get(0));
  }

  @Test
  public void testSet() {
    IntArray ar = new IntArray(new int[] {1, 2, 3});
    ar.set(1, 99);
    assertThat(ar.get(1)).isEqualTo(99);
  }

  @Test
  public void testSetOutOfBounds() {
    IntArray ar = new IntArray();
    ar.add(1);
    assertThrows(IndexOutOfBoundsException.class, () -> ar.set(-1, 5));
    assertThrows(IndexOutOfBoundsException.class, () -> ar.set(1, 5));
  }

  @Test
  public void testRemoveAt() {
    IntArray ar = new IntArray(new int[] {10, 20, 30, 40});
    ar.removeAt(1);
    assertThat(ar.size()).isEqualTo(3);
    assertThat(ar.get(0)).isEqualTo(10);
    assertThat(ar.get(1)).isEqualTo(30);
    assertThat(ar.get(2)).isEqualTo(40);
  }

  @Test
  public void testRemoveAtFirst() {
    IntArray ar = new IntArray(new int[] {10, 20, 30});
    ar.removeAt(0);
    assertThat(ar.size()).isEqualTo(2);
    assertThat(ar.get(0)).isEqualTo(20);
  }

  @Test
  public void testRemoveAtLast() {
    IntArray ar = new IntArray(new int[] {10, 20, 30});
    ar.removeAt(2);
    assertThat(ar.size()).isEqualTo(2);
    assertThat(ar.get(1)).isEqualTo(20);
  }

  @Test
  public void testRemoveAtOutOfBounds() {
    IntArray ar = new IntArray(new int[] {1, 2, 3});
    assertThrows(IndexOutOfBoundsException.class, () -> ar.removeAt(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> ar.removeAt(3));
  }

  @Test
  public void testRemoveAtThenAdd() {
    IntArray ar = new IntArray(new int[] {1, 2, 3});
    ar.removeAt(1);
    ar.add(4);
    assertThat(ar.size()).isEqualTo(3);
    assertThat(ar.get(0)).isEqualTo(1);
    assertThat(ar.get(1)).isEqualTo(3);
    assertThat(ar.get(2)).isEqualTo(4);
  }

  @Test
  public void testRemoveElement() {
    IntArray ar = new IntArray(new int[] {10, 20, 30, 20});
    assertThat(ar.remove(20)).isTrue();
    assertThat(ar.size()).isEqualTo(3);
    assertThat(ar.get(0)).isEqualTo(10);
    assertThat(ar.get(1)).isEqualTo(30);
    assertThat(ar.get(2)).isEqualTo(20);
  }

  @Test
  public void testRemoveNonExistent() {
    IntArray ar = new IntArray(new int[] {1, 2, 3});
    assertThat(ar.remove(99)).isFalse();
    assertThat(ar.size()).isEqualTo(3);
  }

  @Test
  public void testReverse() {
    IntArray ar = new IntArray(new int[] {1, 2, 3, 4, 5});
    ar.reverse();
    assertThat(ar.get(0)).isEqualTo(5);
    assertThat(ar.get(1)).isEqualTo(4);
    assertThat(ar.get(2)).isEqualTo(3);
    assertThat(ar.get(3)).isEqualTo(2);
    assertThat(ar.get(4)).isEqualTo(1);
  }

  @Test
  public void testReverseEvenLength() {
    IntArray ar = new IntArray(new int[] {1, 2, 3, 4});
    ar.reverse();
    assertThat(ar.get(0)).isEqualTo(4);
    assertThat(ar.get(1)).isEqualTo(3);
    assertThat(ar.get(2)).isEqualTo(2);
    assertThat(ar.get(3)).isEqualTo(1);
  }

  @Test
  public void testReverseSingleElement() {
    IntArray ar = new IntArray(new int[] {42});
    ar.reverse();
    assertThat(ar.get(0)).isEqualTo(42);
  }

  @Test
  public void testReverseEmpty() {
    IntArray ar = new IntArray();
    ar.reverse(); // should not throw
    assertThat(ar.isEmpty()).isTrue();
  }

  @Test
  public void testSort() {
    IntArray ar = new IntArray(new int[] {3, 1, 4, 1, 5, 9, 2});
    ar.sort();
    assertThat(ar.get(0)).isEqualTo(1);
    assertThat(ar.get(1)).isEqualTo(1);
    assertThat(ar.get(2)).isEqualTo(2);
    assertThat(ar.get(3)).isEqualTo(3);
    assertThat(ar.get(4)).isEqualTo(4);
    assertThat(ar.get(5)).isEqualTo(5);
    assertThat(ar.get(6)).isEqualTo(9);
  }

  @Test
  public void testBinarySearch() {
    IntArray ar = new IntArray(new int[] {1, 3, 5, 7, 9});
    assertThat(ar.binarySearch(5)).isEqualTo(2);
    assertThat(ar.binarySearch(1)).isEqualTo(0);
    assertThat(ar.binarySearch(9)).isEqualTo(4);
    assertThat(ar.binarySearch(4)).isLessThan(0);
  }

  @Test
  public void testIterator() {
    IntArray ar = new IntArray(new int[] {10, 20, 30});
    List<Integer> result = new ArrayList<>();
    for (int val : ar) {
      result.add(val);
    }
    assertThat(result).containsExactly(10, 20, 30).inOrder();
  }

  @Test
  public void testIteratorEmpty() {
    IntArray ar = new IntArray();
    Iterator<Integer> it = ar.iterator();
    assertThat(it.hasNext()).isFalse();
  }

  @Test
  public void testIteratorRemoveUnsupported() {
    IntArray ar = new IntArray(new int[] {1});
    Iterator<Integer> it = ar.iterator();
    assertThrows(UnsupportedOperationException.class, it::remove);
  }

  @Test
  public void testToString() {
    IntArray ar = new IntArray();
    assertThat(ar.toString()).isEqualTo("[]");

    ar.add(1);
    assertThat(ar.toString()).isEqualTo("[1]");

    ar.add(2);
    ar.add(3);
    assertThat(ar.toString()).isEqualTo("[1, 2, 3]");
  }

  @Test
  public void testToStringNegativeValues() {
    IntArray ar = new IntArray(new int[] {-5, 0, 5});
    assertThat(ar.toString()).isEqualTo("[-5, 0, 5]");
  }

  @Test
  public void testAddRemoveAddSequence() {
    IntArray ar = new IntArray(2);
    // Fill, empty, refill to exercise capacity management
    for (int i = 0; i < 10; i++) ar.add(i);
    for (int i = 0; i < 10; i++) ar.removeAt(0);
    assertThat(ar.isEmpty()).isTrue();
    for (int i = 0; i < 10; i++) ar.add(i * 10);
    assertThat(ar.size()).isEqualTo(10);
    for (int i = 0; i < 10; i++) {
      assertThat(ar.get(i)).isEqualTo(i * 10);
    }
  }
}
