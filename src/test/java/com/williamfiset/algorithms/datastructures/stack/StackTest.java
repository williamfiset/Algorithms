package com.williamfiset.algorithms.datastructures.stack;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class StackTest {

  private static List<Stack<Integer>> inputs() {
    List<Stack<Integer>> stacks = new ArrayList<>();
    stacks.add(new ListStack<Integer>());
    stacks.add(new ArrayStack<Integer>());
    stacks.add(new IntStack(2));
    return stacks;
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testEmptyStack(Stack<Integer> stack) {
    assertThat(stack.isEmpty()).isTrue();
    assertThat(stack.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPopOnEmpty(Stack<Integer> stack) {
    assertThrows(Exception.class, () -> stack.pop());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeekOnEmpty(Stack<Integer> stack) {
    assertThrows(Exception.class, () -> stack.peek());
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPush(Stack<Integer> stack) {
    stack.push(2);
    assertThat(stack.size()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPeek(Stack<Integer> stack) {
    stack.push(2);
    assertThat((int) (Integer) stack.peek()).isEqualTo(2);
    assertThat(stack.size()).isEqualTo(1);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testPop(Stack<Integer> stack) {
    stack.push(2);
    assertThat((int) stack.pop()).isEqualTo(2);
    assertThat(stack.size()).isEqualTo(0);
  }

  @ParameterizedTest
  @MethodSource("inputs")
  public void testExhaustively(Stack<Integer> stack) {
    assertThat(stack.isEmpty()).isTrue();
    stack.push(1);
    assertThat(stack.isEmpty()).isFalse();
    stack.push(2);
    assertThat(stack.size()).isEqualTo(2);
    assertThat((int) stack.peek()).isEqualTo(2);
    assertThat(stack.size()).isEqualTo(2);
    assertThat((int) stack.pop()).isEqualTo(2);
    assertThat(stack.size()).isEqualTo(1);
    assertThat((int) stack.peek()).isEqualTo(1);
    assertThat(stack.size()).isEqualTo(1);
    assertThat((int) stack.pop()).isEqualTo(1);
    assertThat(stack.size()).isEqualTo(0);
    assertThat(stack.isEmpty()).isTrue();
  }

  @Test
  public void testIteratorLifoOrder() {
    ListStack<Integer> stack = new ListStack<>();
    stack.push(1);
    stack.push(2);
    stack.push(3);

    List<Integer> iterated = new ArrayList<>();
    for (Integer v : stack) iterated.add(v);

    // Expect iteration from top (3) to bottom (1)
    assertThat(iterated).containsExactly(3, 2, 1).inOrder();

    // Iterator should not have removed elements
    assertThat(stack.size()).isEqualTo(3);
    assertThat(stack.peek()).isEqualTo(3);
  }

  @Test
  public void testIteratorOnEmptyStack() {
    ListStack<Integer> stack = new ListStack<>();

    List<Integer> iterated = new ArrayList<>();
    for (Integer v : stack) iterated.add(v);

    assertThat(iterated).isEmpty();
    assertThat(stack.isEmpty()).isTrue();
  }
}
