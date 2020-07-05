package com.williamfiset.algorithms.datastructures.queue;

import static com.google.common.truth.Truth.assertThat;

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
    queues.add(new IntQueue(2));
  }

  @Test
  public void testEmptyQueue() {
    for (Queue queue : queues) {
      assertThat(queue.isEmpty()).isTrue();
      assertThat(queue.size()).isEqualTo(0);
    }
  }

  @Test(expected = Exception.class)
  public void testPollOnEmpty() {
    for (Queue queue : queues) {
      queue.poll();
    }
  }

  @Test(expected = Exception.class)
  public void testPeekOnEmpty() {
    for (Queue queue : queues) {
      queue.peek();
    }
  }

  @Test
  public void testOffer() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertThat(queue.size()).isEqualTo(1);
    }
  }

  @Test
  public void testPeek() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertThat((int) queue.peek()).isEqualTo(2);
      assertThat(queue.size()).isEqualTo(1);
    }
  }

  @Test
  public void testPoll() {
    for (Queue<Integer> queue : queues) {
      queue.offer(2);
      assertThat((int) queue.poll()).isEqualTo(2);
      assertThat(queue.size()).isEqualTo(0);
    }
  }

  @Test
  public void testExhaustively() {
    for (Queue<Integer> queue : queues) {
      assertThat(queue.isEmpty()).isTrue();
      queue.offer(1);
      assertThat(queue.isEmpty()).isFalse();
      queue.offer(2);
      assertThat(queue.size()).isEqualTo(2);
      assertThat((int) queue.peek()).isEqualTo(1);
      assertThat(queue.size()).isEqualTo(2);
      assertThat((int) queue.poll()).isEqualTo(1);
      assertThat(queue.size()).isEqualTo(1);
      assertThat((int) queue.peek()).isEqualTo(2);
      assertThat(queue.size()).isEqualTo(1);
      assertThat((int) queue.poll()).isEqualTo(2);
      assertThat(queue.size()).isEqualTo(0);
      assertThat(queue.isEmpty()).isTrue();
    }
  }
}
