package com.williamfiset.algorithms.datastructures.queue;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntQueueTest {

  @BeforeEach
  public void setup() {}

  @Test
  public void testEmptyQueue() {
    IntQueue queue = new IntQueue(0);
    assertThat(queue.isEmpty()).isTrue();
    assertThat(queue.size()).isEqualTo(0);
  }

  @Test
  public void testPollOnEmpty() {
    IntQueue queue = new IntQueue(1);
    assertThrows(RuntimeException.class, queue::poll);
  }

  @Test
  public void testPeekOnEmpty() {
    IntQueue queue = new IntQueue(1);
    assertThrows(RuntimeException.class, queue::peek);
  }

  @Test
  public void testOfferOnFull() {
    IntQueue queue = new IntQueue(1);
    queue.offer(1);
    assertThrows(RuntimeException.class, () -> queue.offer(2));
  }

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

  @Test
  public void testPeekDoesNotMutateState() {
    IntQueue queue = new IntQueue(4);
    queue.offer(10);
    queue.offer(20);
    assertThat((int) queue.peek()).isEqualTo(10);
    assertThat((int) queue.peek()).isEqualTo(10); // second call must return same value
    assertThat(queue.size()).isEqualTo(2);
  }

  @Test
  public void testIsFullAndWraparound() {
    // Fill to capacity, drain partially, refill to exercise circular wrap.
    IntQueue queue = new IntQueue(4);
    for (int i = 0; i < 4; i++) queue.offer(i);
    assertThat(queue.isFull()).isTrue();

    // Drain half, then refill to confirm wrap-around works correctly.
    assertThat((int) queue.poll()).isEqualTo(0);
    assertThat((int) queue.poll()).isEqualTo(1);
    queue.offer(4);
    queue.offer(5);
    assertThat(queue.isFull()).isTrue();

    assertThat((int) queue.poll()).isEqualTo(2);
    assertThat((int) queue.poll()).isEqualTo(3);
    assertThat((int) queue.poll()).isEqualTo(4);
    assertThat((int) queue.poll()).isEqualTo(5);
    assertThat(queue.isEmpty()).isTrue();
  }

  @Test
  public void testNegativeValues() {
    IntQueue queue = new IntQueue(3);
    queue.offer(-1);
    queue.offer(-100);
    queue.offer(Integer.MIN_VALUE);
    assertThat((int) queue.poll()).isEqualTo(-1);
    assertThat((int) queue.poll()).isEqualTo(-100);
    assertThat((int) queue.poll()).isEqualTo(Integer.MIN_VALUE);
  }

  @Test
  public void testNonPowerOfTwoCapacityRounding() {
    // maxSize=5 should round up to capacity 8; all 5 slots must be usable.
    IntQueue queue = new IntQueue(5);
    for (int i = 0; i < 5; i++) queue.offer(i);
    for (int i = 0; i < 5; i++) assertThat((int) queue.poll()).isEqualTo(i);
    assertThat(queue.isEmpty()).isTrue();
  }

  @Test
  public void testRepeatedFillAndDrain() {
    IntQueue queue = new IntQueue(4);
    for (int round = 0; round < 3; round++) {
      for (int i = 0; i < 4; i++) queue.offer(i);
      for (int i = 0; i < 4; i++) assertThat((int) queue.poll()).isEqualTo(i);
    }
    assertThat(queue.isEmpty()).isTrue();
  }
}
