package com.williamfiset.algorithms.datastructures.stack;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class StackTest {

  List<Stack> stacks = new ArrayList<>();

  @Before
  public void setup() {
    stacks.add(new ListStack<Integer>());
    stacks.add(new ArrayStack<>());
  }

  @Test
  public void testEmptyStack() {
    for (Stack stack : stacks) {
      assertTrue(stack.isEmpty());
      assertEquals(stack.size(), 0);
    }
  }

  @Test(expected = Exception.class)
  public void testPopOnEmpty() {
    for (Stack stack : stacks) {
      assertThrows(Exception.class, stack::pop);
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
    for (Stack stack : stacks) {
      stack.push(2);
      assertEquals(stack.size(), 1);
    }
  }

  @Test
  public void testPeek() {
    for (Stack stack : stacks) {
      stack.push(2);
      assertEquals(2, (int) (Integer) stack.peek());
      assertEquals(stack.size(), 1);
    }
  }

  @Test
  public void testPop() {
    for (Stack stack : stacks) {
      stack.push(2);
      assertEquals(2, (int) stack.pop());
      assertEquals(stack.size(), 0);
    }
  }

  @Test
  public void testExhaustively() {
    for (Stack stack : stacks) {
      assertTrue(stack.isEmpty());
      stack.push(1);
      assertTrue(!stack.isEmpty());
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
