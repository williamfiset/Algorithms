package com.williamfiset.algorithms.datastructures.queue;

import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.Before;
import org.junit.Test;

public class IntQueueTest {

  @Before
  public void setup() {}

  @Test
  public void testEmptyQueue() {
    IntQueue queue = new IntQueue(0);
    assertThat(queue.isEmpty()).isTrue();
    assertThat(queue.size()).isEqualTo(0);
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
    assertThat(queue.size()).isEqualTo(1);
  }

  @Test
  public void testAll() {
    int n = 5;
    IntQueue queue = new IntQueue(10);
    assertThat(queue.isEmpty()).isTrue();
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertThat(queue.isEmpty()).isFalse();
    }
    for (int i = 1; i <= n; i++) {
      assertThat((int) queue.peek()).isEqualTo(i);
      assertThat((int) queue.poll()).isEqualTo(i);
      assertThat(queue.size()).isEqualTo(n - i);
    }
    assertThat(queue.isEmpty()).isTrue();
    n = 8;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertThat(queue.isEmpty()).isFalse();
    }
    for (int i = 1; i <= n; i++) {
      assertThat((int) queue.peek()).isEqualTo(i);
      assertThat((int) queue.poll()).isEqualTo(i);
      assertThat(queue.size()).isEqualTo(n - i);
    }
    assertThat(queue.isEmpty()).isTrue();
    n = 9;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertThat(queue.isEmpty()).isFalse();
    }
    for (int i = 1; i <= n; i++) {
      assertThat((int) queue.peek()).isEqualTo(i);
      assertThat((int) queue.poll()).isEqualTo(i);
      assertThat(queue.size()).isEqualTo(n - i);
    }
    assertThat(queue.isEmpty()).isTrue();
    n = 10;
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
      assertThat(queue.isEmpty()).isFalse();
    }
    for (int i = 1; i <= n; i++) {
      assertThat((int) queue.peek()).isEqualTo(i);
      assertThat((int) queue.poll()).isEqualTo(i);
      assertThat(queue.size()).isEqualTo(n - i);
    }
    assertThat(queue.isEmpty()).isTrue();
  }

  @Test
  public void testPeekOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertThat(queue.peek()).isEqualTo(77);
    assertThat(queue.size()).isEqualTo(1);
  }

  @Test
  public void testpollOneElement() {
    IntQueue queue = new IntQueue(1);
    queue.offer(77);
    assertThat(queue.poll()).isEqualTo(77);
    assertThat(queue.size()).isEqualTo(0);
  }

  @Test
  public void testRandom() {

    for (int qSize = 1; qSize <= 50; qSize++) {

      IntQueue intQ = new IntQueue(qSize);
      ArrayDeque<Integer> javaQ = new ArrayDeque<>(qSize);

      assertThat(javaQ.isEmpty()).isEqualTo(intQ.isEmpty());
      assertThat(javaQ.size()).isEqualTo(intQ.size());

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
            assertThat((int) javaQ.poll()).isEqualTo((int) intQ.poll());
          }
        }

        assertThat(javaQ.isEmpty()).isEqualTo(intQ.isEmpty());
        assertThat(javaQ.size()).isEqualTo(intQ.size());
      }
    }
  }
}
