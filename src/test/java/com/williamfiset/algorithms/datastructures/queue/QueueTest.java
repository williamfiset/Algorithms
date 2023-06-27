package com.williamfiset.algorithms.datastructures.queue;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class QueueTest {

  private static List<Queue<Integer>> inputs() {
    List<Queue<Integer>> queues = new ArrayList<>();
    queues.add(new ArrayQueue<Integer>(2));
    queues.add(new LinkedQueue<Integer>());
    queues.add(new IntQueue(2));
    return queues;
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testEmptyQueue(Queue<Integer> queue) {
    assertThat(queue.isEmpty()).isTrue();
    assertThat(queue.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPollOnEmpty(Queue<Integer> queue) {
    assertThrows(Exception.class, () -> queue.poll());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeekOnEmpty(Queue<Integer> queue) {
    assertThrows(Exception.class, () -> queue.peek());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testOffer(Queue<Integer> queue) {
    queue.offer(2);
    assertThat(queue.size()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeek(Queue<Integer> queue) {
    queue.offer(2);
    assertThat((int) queue.peek()).isEqualTo(2);
    assertThat(queue.size()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPoll(Queue<Integer> queue) {
    queue.offer(2);
    assertThat((int) queue.poll()).isEqualTo(2);
    assertThat(queue.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testExhaustively(Queue<Integer> queue) {
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
