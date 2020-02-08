package com.williamfiset.algorithms.datastructures.dynamicarray;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DynamicArrayTest {

  @Test
  public void testEmptyList() {
    DynamicArray<Integer> list = new DynamicArray<>();
    assertTrue(list.isEmpty());
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
    assertTrue(ret);

    ret = list.remove("c");
    assertFalse(ret);

    ret = list.remove("h");
    assertTrue(ret);

    ret = list.remove(null);
    assertTrue(ret);

    ret = list.remove("a");
    assertTrue(ret);

    ret = list.remove("a");
    assertFalse(ret);

    ret = list.remove("h");
    assertFalse(ret);

    ret = list.remove(null);
    assertFalse(ret);
  }

  @Test
  public void testRemoving2() {

    DynamicArray<String> list = new DynamicArray<>();
    String[] strs = {"a", "b", "c", "d"};
    for (String s : strs) list.add(s);

    assertTrue(list.remove("a"));
    assertTrue(list.remove("b"));
    assertTrue(list.remove("c"));
    assertTrue(list.remove("d"));

    assertFalse(list.remove("a"));
    assertFalse(list.remove("b"));
    assertFalse(list.remove("c"));
    assertFalse(list.remove("d"));
  }

  @Test
  public void testIndexOfNullElement() {
    DynamicArray<String> list = new DynamicArray<>();
    String[] strs = {"a", "b", null, "d"};
    for (String s : strs) list.add(s);
    assertTrue(list.indexOf(null) == 2);
  }

  @Test
  public void testAddingElements() {

    DynamicArray<Integer> list = new DynamicArray<>();

    int[] elems = {1, 2, 3, 4, 5, 6, 7};

    for (int i = 0; i < elems.length; i++) list.add(elems[i]);

    for (int i = 0; i < elems.length; i++) assertEquals(list.get(i).intValue(), elems[i]);
  }

  @Test
  public void testAddAndRemove() {

    DynamicArray<Long> list = new DynamicArray<>(0);

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.remove(44L);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.removeAt(0);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.remove(44L);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.removeAt(0);
    assertTrue(list.isEmpty());
  }

  @Test
  public void testAddSetRemove() {

    DynamicArray<Long> list = new DynamicArray<>(0);

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.set(i, 33L);
    for (int i = 0; i < 55; i++) list.remove(33L);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 55; i++) list.add(44L);
    for (int i = 0; i < 55; i++) list.set(i, 33L);
    for (int i = 0; i < 55; i++) list.removeAt(0);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.set(i, 33L);
    for (int i = 0; i < 155; i++) list.remove(33L);
    assertTrue(list.isEmpty());

    for (int i = 0; i < 155; i++) list.add(44L);
    for (int i = 0; i < 155; i++) list.removeAt(0);
    assertTrue(list.isEmpty());
  }

  @Test
  public void testSize() {

    DynamicArray<Integer> list = new DynamicArray<>();

    Integer[] elems = {-76, 45, 66, 3, null, 54, 33};
    for (int i = 0, sz = 1; i < elems.length; i++, sz++) {
      list.add(elems[i]);
      assertEquals(list.size(), sz);
    }
  }
}
