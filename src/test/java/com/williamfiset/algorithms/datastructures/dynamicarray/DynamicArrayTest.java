package com.williamfiset.algorithms.datastructures.dynamicarray;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class DynamicArrayTest {

  @Test
  public void testEmptyList() {
    DynamicArray<Integer> list = new DynamicArray<>();
    assertThat(list.isEmpty()).isTrue();
  }

  @Test(expected = Exception.class)
  public void testRemovingEmpty() {
    DynamicArray<Integer> list = new DynamicArray<>();
    list.removeAt(0);
  }

  @Test(expected = Exception.class)
  public void testIndexOutOfBounds() {
    DynamicArray<Integer> list = new DynamicArray<>();
    list.add(-56);
    list.add(-53);
    list.add(-55);
    list.removeAt(3);
  }

  @Test(expected = Exception.class)
  public void testIndexOutOfBounds2() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 1000; i++) list.add(789);
    list.removeAt(1000);
  }

  @Test(expected = Exception.class)
  public void testIndexOutOfBounds3() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 1000; i++) list.add(789);
    list.removeAt(-1);
  }

  @Test(expected = Exception.class)
  public void testIndexOutOfBounds4() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 15; i++) list.add(123);
    list.removeAt(-66);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds5() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.set(-1, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds6() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.set(10, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds7() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.set(15, 3);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds8() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.get(-2);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds9() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.get(10);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testIndexOutOfBounds10() {
    DynamicArray<Integer> list = new DynamicArray<>();
    for (int i = 0; i < 10; i++) list.add(12);
    list.get(15);
  }

  @Test
  public void testRemoving() {

    DynamicArray<String> list = new DynamicArray<>();
    String[] strs = {"a", "b", "c", "d", "e", null, "g", "h"};
    for (String s : strs) list.add(s);

    boolean ret = list.remove("c");
    assertThat(ret).isTrue();

    ret = list.remove("c");
    assertThat(ret).isFalse();

    ret = list.remove("h");
    assertThat(ret).isTrue();

    ret = list.remove(null);
    assertThat(ret).isTrue();

    ret = list.remove("a");
    assertThat(ret).isTrue();

    ret = list.remove("a");
    assertThat(ret).isFalse();

    ret = list.remove("h");
    assertThat(ret).isFalse();

    ret = list.remove(null);
    assertThat(ret).isFalse();
  }

  @Test
  public void testRemoving2() {

    DynamicArray<String> list = new DynamicArray<>();
    String[] strs = {"a", "b", "c", "d"};
    for (String s : strs) list.add(s);

    assertThat(list.remove("a")).isTrue();
    assertThat(list.remove("b")).isTrue();
    assertThat(list.remove("c")).isTrue();
    assertThat(list.remove("d")).isTrue();

    assertThat(list.remove("a")).isFalse();
    assertThat(list.remove("b")).isFalse();
    assertThat(list.remove("c")).isFalse();
    assertThat(list.remove("d")).isFalse();
  }

  @Test
  public void testIndexOfNullElement() {
    DynamicArray<String> list = new DynamicArray<>();
    String[] strs = {"a", "b", null, "d"};
    for (String s : strs) list.add(s);
    assertThat(list.indexOf(null)).isEqualTo(2);
  }

  @Test
  public void testAddingElements() {

    DynamicArray<Integer> list = new DynamicArray<>();

    int[] elems = {1, 2, 3, 4, 5, 6, 7};

    for (int i = 0; i < elems.length; i++) list.add(elems[i]);

    for (int i = 0; i < elems.length; i++) assertThat(list.get(i).intValue()).isEqualTo(elems[i]);
  }

  @Test
  public void testAddAndRemove() {

    DynamicArray<Long> list = new DynamicArray<>(0);

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.remove(44L);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.removeAt(0);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.remove(44L);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.removeAt(0);
    assertThat(list.isEmpty()).isTrue();
  }

  @Test
  public void testAddSetRemove() {

    DynamicArray<Long> list = new DynamicArray<>(0);

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.set(i, 33L);
    for (int i = 0; i < 55; i++) list.remove(33L);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.set(i, 33L);
    for (int i = 0; i < 55; i++) list.removeAt(0);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.set(i, 33L);
    for (int i = 0; i < 155; i++) list.remove(33L);
    assertThat(list.isEmpty()).isTrue();

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.removeAt(0);
    assertThat(list.isEmpty()).isTrue();
  }

  @Test
  public void testSize() {

    DynamicArray<Integer> list = new DynamicArray<>();

    Integer[] elems = {-76, 45, 66, 3, null, 54, 33};
    for (int i = 0, sz = 1; i < elems.length; i++, sz++) {
      list.add(elems[i]);
      assertThat(list.size()).isEqualTo(sz);
    }
  }
}
