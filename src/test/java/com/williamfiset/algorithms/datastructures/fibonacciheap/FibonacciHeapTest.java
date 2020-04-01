package com.williamfiset.algorithms.datastructures.fibonacciheap;

import static com.google.common.truth.Truth.assertThat;
import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Disclaimer: Based by help of
// "http://langrsoft.com/jeff/2011/11/test-driving-a-heap-based-priority-queue/">Test-Driving a
// Heap-Based Priority Queue</a>
// Credits to the respecti owner for code

public final class FibonacciHeapTest {

  private Queue<Integer> queue;

  @Before
  public void setUp() {
    queue = new FibonacciHeap<Integer>();
  }

  @After
  public void tearDown() {
    queue = null;
  }

  @Test
  public void emptyWhenCreated() {
    assertThat(queue.isEmpty()).isEqualTo(true);
    assertThat(queue.poll()).isEqualTo(null);
  }

  @Test
  public void noLongerEmptyAfterAdd() {
    queue.add(50);

    assertThat(queue.isEmpty()).isFalse();
  }

  @Test
  public void singletonQueueReturnsSoleItemOnPoll() {
    queue.add(50);

    assertThat(queue.poll()).isEqualTo(50);
  }

  @Test
  public void isEmptyAfterSoleElementRemoved() {
    queue.add(50);
    queue.poll();

    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void returnsOrderedItems() {
    queue.add(100);
    queue.add(50);

    assertThat(queue.poll()).isEqualTo(50);
    assertThat(queue.poll()).isEqualTo(100);
    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void insertSingleItem() {
    queue.add(50);

    assertThat(queue.poll()).isEqualTo(50);
    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void insertSameValuesAndReturnsOrderedItems() {
    queue.add(50);
    queue.add(100);
    queue.add(50);

    assertThat(queue.poll()).isEqualTo(50);
    assertThat(queue.poll()).isEqualTo(50);
    assertThat(queue.poll()).isEqualTo(100);
    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void returnsOrderedItemsFromRandomInsert() {
    final Random r = new Random(System.currentTimeMillis());
    final List<Integer> expected = new ArrayList<Integer>();

    for (int i = 0; i < 1000; i++) {
      Integer number = r.nextInt(10000);
      expected.add(number);
      queue.add(number);
    }
    sort(expected);

    for (Integer integer : expected) {
      Integer i = queue.poll();
      assertThat(i).isEqualTo(integer);
    }

    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void addAllAndContinsItem() {
    Collection<Integer> c = new ArrayList<Integer>();

    c.add(50);
    c.add(100);
    c.add(20);
    c.add(21);

    queue.addAll(c);

    assertThat(queue.isEmpty()).isEqualTo(false);
    assertThat(queue.containsAll(c)).isEqualTo(true);

    assertThat(queue.contains(100)).isEqualTo(true);
    assertThat(queue.contains(21)).isEqualTo(true);
    assertThat(queue.contains(50)).isEqualTo(true);
    assertThat(queue.contains(20)).isEqualTo(true);
  }

  @Test
  public void clearQueue() {
    final Random r = new Random(System.currentTimeMillis());
    for (int i = 0; i < 1000; i++) {
      Integer number = r.nextInt(10000);
      queue.add(number);
    }

    assertThat(queue.isEmpty()).isEqualTo(false);
    queue.clear();
    assertThat(queue.isEmpty()).isEqualTo(true);
  }

  @Test
  public void offerPeekAndElement() {
    queue.offer(50);
    queue.offer(100);
    queue.offer(20);
    queue.offer(21);

    assertThat(queue.isEmpty()).isFalse();
    ;
    assertThat(queue.peek()).isEqualTo(20);
    assertThat(queue.element()).isEqualTo(20);
    assertThat(queue.size()).isEqualTo(4);
  }

  @Test(expected = NoSuchElementException.class)
  public void elementThrowsException() {
    queue.element();
  }
}
