package com.williamfiset.algorithms.datastructures.stack;

import static org.junit.Assert.*;

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
  }

  @Test
  public void testEmptyStack() {
    for (Stack stack : stacks) {
      assertTrue(stack.isEmpty());
      assertEquals(stack.size(), 0);
    }
  }

  @Test
  public void testPopOnEmpty() {
    for (Stack stack : stacks) {
      assertThrows(Exception.class, stack::pop);
    }
  }

  @Test
  public void testPeekOnEmpty() {
    for (Stack stack : stacks) {
      assertThrows(Exception.class, stack::peek);
    }
  }

  @Test
  public void testPush() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertEquals(stack.size(), 1);
    }
  }

  @Test
  public void testPeek() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertEquals(2, (int) (Integer) stack.peek());
      assertEquals(stack.size(), 1);
    }
  }

  @Test
  public void testPop() {
    for (Stack<Integer> stack : stacks) {
      stack.push(2);
      assertEquals(2, (int) stack.pop());
      assertEquals(stack.size(), 0);
    }
  }

  @Test
  public void testExhaustively() {
    for (Stack<Integer> stack : stacks) {
      assertTrue(stack.isEmpty());
      stack.push(1);
      assertFalse(stack.isEmpty());
      stack.push(2);
      assertEquals(stack.size(), 2);
      assertEquals(2, (int) stack.peek());
      assertEquals(stack.size(), 2);
      assertEquals(2, (int) stack.pop());
      assertEquals(stack.size(), 1);
      assertEquals(1, (int) stack.peek());
      assertEquals(stack.size(), 1);
      assertEquals(1, (int) stack.pop());
      assertEquals(stack.size(), 0);
      assertTrue(stack.isEmpty());
    }
  }
}
