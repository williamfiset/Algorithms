package com.williamfiset.algorithms.datastructures.queue;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {

  @Before
  public void setup() {}

  @Test
  public void testEmptyQueue() {
    IntQueue queue = new IntQueue(0);
    assertTrue(queue.isEmpty());
    assertEquals(queue.size(), 0);
  }

  // Doesn't apply to this implementation because of wrap
  // @Test(expected=Exception.class)
  // public void testPollOnEmpty() {
  //   IntQueue queue = new IntQueue(0);
  //   queue.poll();
  // }

  // Doesn't apply to this implementation because of wrap
  // @Test(expected=Exception.class)
  // public void testPeekOnEmpty() {
  //   IntQueue queue = new IntQueue(0);
  //   queue.peek();
  // }

  @Test
  public void testofferOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertEquals(queue.size(), 1);
  }

  @Test
  public void testAll() {
    int n = 5;
    IntQueue queue = new IntQueue(10);
    assertTrue(queue.isEmpty());
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, (int) queue.peek());
      assertEquals(i, (int) queue.poll());
      assertEquals(queue.size(), n - i);
    }
    assertTrue(queue.isEmpty());
    n = 8;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, (int) queue.peek());
      assertEquals(i, (int) queue.poll());
      assertEquals(queue.size(), n - i);
    }
    assertTrue(queue.isEmpty());
    n = 9;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, (int) queue.peek());
      assertEquals(i, (int) queue.poll());
      assertEquals(queue.size(), n - i);
    }
    assertTrue(queue.isEmpty());
    n = 10;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertFalse(queue.isEmpty());
    }
    for (int i = 1; i <= n; i++) {
      assertEquals(i, (int) queue.peek());
      assertEquals(i, (int) queue.poll());
      assertEquals(queue.size(), n - i);
    }
    assertTrue(queue.isEmpty());
  }

  @Test
  public void testPeekOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertTrue(queue.peek() == 77);
    assertEquals(queue.size(), 1);
  }

  @Test
  public void testpollOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertTrue(queue.poll() == 77);
    assertEquals(queue.size(), 0);
  }

  @Test
  public void testRandom() {

    for (int qSize = 1; qSize <= 50; qSize++) {

      IntQueue intQ = new IntQueue(qSize);
      ArrayDeque<Integer> javaQ = new ArrayDeque<>(qSize);

      assertEquals(javaQ.isEmpty(), intQ.isEmpty());
      assertEquals(javaQ.size(), intQ.size());

      for (int operations = 0; operations < 5000; operations++) {

        double r = Math.random();

        if (r < 0.60) {
          int elem = (int) (1000 * Math.random());
          if (javaQ.size() < qSize) {
            javaQ.offer(elem);
            intQ.offer(elem);
          }
        } else {
          if (!javaQ.isEmpty()) {
            assertEquals((int) javaQ.poll(), (int) intQ.poll());
          }
        }

        assertEquals(javaQ.isEmpty(), intQ.isEmpty());
        assertEquals(javaQ.size(), intQ.size());
      }
    }
  }
}
