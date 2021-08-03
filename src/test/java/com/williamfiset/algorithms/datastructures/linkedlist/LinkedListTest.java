package com.williamfiset.algorithms.datastructures.linkedlist;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.*;

public class LinkedListTest {
  private static final int LOOPS = 10000;
  private static final int TEST_SZ = 40;
  private static final int NUM_NULLS = TEST_SZ / 5;
  private static final int MAX_RAND_NUM = 250;

  DoublyLinkedList<Integer> list;

  @Before
  public void setup() {
    list = new DoublyLinkedList<>();
  }

  @Test
  public void testEmptyList() {
    assertThat(list.isEmpty()).isTrue();
    assertThat(list.size()).isEqualTo(0);
  }

  @Test(expected = Exception.class)
  public void testRemoveFirstOfEmpty() {
    list.removeFirst();
  }

  @Test(expected = Exception.class)
  public void testRemoveLastOfEmpty() {
    list.removeLast();
  }

  @Test(expected = Exception.class)
  public void testPeekFirstOfEmpty() {
    list.peekFirst();
  }

  @Test(expected = Exception.class)
  public void testPeekLastOfEmpty() {
    list.peekLast();
  }

  @Test
  public void testAddFirst() {
    list.addFirst(3);
    assertThat(list.size()).isEqualTo(1);
    list.addFirst(5);
    assertThat(list.size()).isEqualTo(2);
  }

  @Test
  public void testAddLast() {
    list.addLast(3);
    assertThat(list.size()).isEqualTo(1);
    list.addLast(5);
    assertThat(list.size()).isEqualTo(2);
  }

  @Test
  public void testAddAt() throws Exception {
    list.addAt(0, 1);
    assertThat(list.size()).isEqualTo(1);
    list.addAt(1, 2);
    assertThat(list.size()).isEqualTo(2);
    list.addAt(1, 3);
    assertThat(list.size()).isEqualTo(3);
    list.addAt(2, 4);
    assertThat(list.size()).isEqualTo(4);
    list.addAt(1, 8);
    assertThat(list.size()).isEqualTo(5);
  }

  @Test
  public void testRemoveFirst() {
    list.addFirst(3);
    assertThat(list.removeFirst()).isEqualTo(3);
    assertThat(list.isEmpty());
  }

  @Test
  public void testRemoveLast() {
    list.addLast(4);
    assertThat(list.removeLast()).isEqualTo(4);
    assertThat(list.isEmpty()).isTrue();
  }

  @Test
  public void testPeekFirst() {
    list.addFirst(4);
    assertThat(list.peekFirst()).isEqualTo(4);
    assertThat(list.size()).isEqualTo(1);
  }

  @Test
  public void testPeekLast() {
    list.addLast(4);
    assertThat(list.peekLast()).isEqualTo(4);
    assertThat(list.size()).isEqualTo(1);
  }

  @Test
  public void testPeeking() {
    // 5
    list.addFirst(5);
    assertThat(list.peekFirst()).isEqualTo(5);
    assertThat(list.peekLast()).isEqualTo(5);

    // 6 - 5
    list.addFirst(6);
    assertThat(list.peekFirst()).isEqualTo(6);
    assertThat(list.peekLast()).isEqualTo(5);

    // 7 - 6 - 5
    list.addFirst(7);
    assertThat(list.peekFirst()).isEqualTo(7);
    assertThat(list.peekLast()).isEqualTo(5);

    // 7 - 6 - 5 - 8
    list.addLast(8);
    assertThat(list.peekFirst()).isEqualTo(7);
    assertThat(list.peekLast()).isEqualTo(8);

    // 7 - 6 - 5
    list.removeLast();
    assertThat(list.peekFirst()).isEqualTo(7);
    assertThat(list.peekLast()).isEqualTo(5);

    // 7 - 6
    list.removeLast();
    assertThat(list.peekFirst()).isEqualTo(7);
    assertThat(list.peekLast()).isEqualTo(6);

    // 6
    list.removeFirst();
    assertThat(list.peekFirst()).isEqualTo(6);
    assertThat(list.peekLast()).isEqualTo(6);
  }

  @Test
  public void testRemoving() {
    DoublyLinkedList<String> strs = new DoublyLinkedList<>();
    strs.add("a");
    strs.add("b");
    strs.add("c");
    strs.add("d");
    strs.add("e");
    strs.add("f");
    strs.remove("b");
    strs.remove("a");
    strs.remove("d");
    strs.remove("e");
    strs.remove("c");
    strs.remove("f");
    assertThat(strs.size()).isEqualTo(0);
  }

  @Test
  public void testRemoveAt() {
    list.add(1);
    list.add(2);
    list.add(3);
    list.add(4);
    list.removeAt(0);
    list.removeAt(2);
    assertThat(list.peekFirst()).isEqualTo(2);
    assertThat(list.peekLast()).isEqualTo(3);
    list.removeAt(1);
    list.removeAt(0);
    assertThat(list.size()).isEqualTo(0);
  }

  @Test
  public void testClear() {
    list.add(22);
    list.add(33);
    list.add(44);
    assertThat(list.size()).isEqualTo(3);
    list.clear();
    assertThat(list.size()).isEqualTo(0);
    list.add(22);
    list.add(33);
    list.add(44);
    assertThat(list.size()).isEqualTo(3);
    list.clear();
    assertThat(list.size()).isEqualTo(0);
  }

  @Test
  public void testRandomizedRemoving() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();
    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      javaLinkedList.clear();

      List<Integer> randNums = genRandList(TEST_SZ);
      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (int i = 0; i < randNums.size(); i++) {

        Integer rm_val = randNums.get(i);
        assertThat(javaLinkedList.remove(rm_val)).isEqualTo(list.remove(rm_val));
        assertThat(javaLinkedList.size()).isEqualTo(list.size());

        java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertThat(iter1.next()).isEqualTo(iter2.next());

        iter1 = javaLinkedList.iterator();
        iter2 = list.iterator();
        while (iter1.hasNext()) assertThat(iter1.next()).isEqualTo(iter2.next());
      }

      list.clear();
      javaLinkedList.clear();

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      // Try removing elements whether or not they exist
      for (int i = 0; i < randNums.size(); i++) {

        Integer rm_val = (int) (MAX_RAND_NUM * Math.random());
        assertThat(javaLinkedList.remove(rm_val)).isEqualTo(list.remove(rm_val));
        assertThat(javaLinkedList.size()).isEqualTo(list.size());

        java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertThat(iter1.next()).isEqualTo(iter2.next());
      }
    }
  }

  @Test
  public void testRandomizedRemoveAt() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      list.clear();
      javaLinkedList.clear();

      List<Integer> randNums = genRandList(TEST_SZ);

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      for (int i = 0; i < randNums.size(); i++) {

        int rm_index = (int) (list.size() * Math.random());

        Integer num1 = javaLinkedList.remove(rm_index);
        Integer num2 = list.removeAt(rm_index);
        assertThat(num1).isEqualTo(num2);
        assertThat(javaLinkedList.size()).isEqualTo(list.size());

        java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertThat(iter1.next()).isEqualTo(iter2.next());
      }
    }
  }

  @Test
  public void testRandomizedIndexOf() {
    java.util.LinkedList<Integer> javaLinkedList = new java.util.LinkedList<>();

    for (int loops = 0; loops < LOOPS; loops++) {

      javaLinkedList.clear();
      list.clear();

      List<Integer> randNums = genUniqueRandList(TEST_SZ);

      for (Integer value : randNums) {
        javaLinkedList.add(value);
        list.add(value);
      }

      Collections.shuffle(randNums);

      for (int i = 0; i < randNums.size(); i++) {
        Integer elem = randNums.get(i);
        Integer index1 = javaLinkedList.indexOf(elem);
        Integer index2 = list.indexOf(elem);

        assertThat(index1).isEqualTo(index2);
        assertThat(javaLinkedList.size()).isEqualTo(list.size());

        java.util.Iterator<Integer> iter1 = javaLinkedList.iterator();
        java.util.Iterator<Integer> iter2 = list.iterator();
        while (iter1.hasNext()) assertThat(iter1.next()).isEqualTo(iter2.next());
      }
    }
  }

  @Test
  public void testToString() {
    DoublyLinkedList<String> strs = new DoublyLinkedList<>();
    assertThat(strs.toString()).isEqualTo("[  ]");
    strs.add("a");
    assertThat(strs.toString()).isEqualTo("[ a ]");
    strs.add("b");
    assertThat(strs.toString()).isEqualTo("[ a, b ]");
    strs.add("c");
    strs.add("d");
    strs.add("e");
    strs.add("f");
    assertThat(strs.toString()).isEqualTo("[ a, b, c, d, e, f ]");
  }

  // Generate a list of random numbers
  static List<Integer> genRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add((int) (Math.random() * MAX_RAND_NUM));
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }

  // Generate a list of unique random numbers
  static List<Integer> genUniqueRandList(int sz) {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(i);
    for (int i = 0; i < NUM_NULLS; i++) lst.add(null);
    Collections.shuffle(lst);
    return lst;
  }
}
