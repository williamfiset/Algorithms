package com.williamfiset.algorithms.datastructures.stack;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class StackTest {

  private List<Stack<Integer>> stacks = new ArrayList<>();

  @Before
  public void setup() {
    stacks.add(new ListStack<Integer>());
    stacks.add(new ArrayStack<Integer>());
    stacks.add(new IntStack(2));
  }

  @Test
  public void testEmptyStack() {
    for (Stack stack : stacks) {
      assertThat(stack.isEmpty()).isTrue();
      assertThat(stack.size()).isEqualTo(0);
    }
  }

  @Test(expected = Exception.class)
  public void testPopOnEmpty() {
    for (Stack stack : stacks) {
      stack.pop();
    }
  }

  @Test(expected = Exception.class)
  public void testPeekOnEmpty() {
    for (Stack stack : stacks) {
      stack.peek();
    }
  }

  @Test
  public void testPush() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertThat(stack.size()).isEqualTo(1);
    }
  }

  @Test
  public void testPeek() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertThat((int) (Integer) stack.peek()).isEqualTo(2);
      assertThat(stack.size()).isEqualTo(1);
    }
  }

  @Test
  public void testPop() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertThat((int) stack.pop()).isEqualTo(2);
      assertThat(stack.size()).isEqualTo(0);
    }
  }

  @Test
  public void testExhaustively() {
    for (Stack<Integer> stack : stacks) {
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
  }
}
