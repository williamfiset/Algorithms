package com.williamfiset.algorithms.datastructures.queue;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class QueueTest {

  private List<Queue<Integer>> queues = new ArrayList<>();

  @Before
  public void setup() {
    queues.add(new ArrayQueue<Integer>(2));
    queues.add(new LinkedQueue<Integer>());
  }

  @Test
  public void testEmptyQueue() {
    for (Queue queue : queues) {
      assertTrue(queue.isEmpty());
      assertEquals(queue.size(), 0);
    }
  }

  @Test
  public void testPollOnEmpty() {
    for (Queue queue : queues) {
      assertThrows(Exception.class, queue::poll);
    }
  }

  @Test
  public void testPeekOnEmpty() {
    for (Queue queue : queues) {
      assertThrows(Exception.class, queue::peek);
    }
  }

  @Test
  public void testOffer() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertEquals(queue.size(), 1);
    }
  }

  @Test
  public void testPeek() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertEquals(2, (int) queue.peek());
      assertEquals(queue.size(), 1);
    }
  }

  @Test
  public void testPoll() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertEquals(2, (int) queue.poll());
      assertEquals(queue.size(), 0);
    }
  }

  @Test
  public void testExhaustively() {
    for (Queue<Integer> queue : queues) {
      assertTrue(queue.isEmpty());
      queue.offer(1);
      assertFalse(queue.isEmpty());
      queue.offer(2);
      assertEquals(queue.size(), 2);
      assertEquals(1, (int) queue.peek());
      assertEquals(queue.size(), 2);
      assertEquals(1, (int) queue.poll());
      assertEquals(queue.size(), 1);
      assertEquals(2, (int) queue.peek());
      assertEquals(queue.size(), 1);
      assertEquals(2, (int) queue.poll());
      assertEquals(queue.size(), 0);
      assertTrue(queue.isEmpty());
    }
  }
}
