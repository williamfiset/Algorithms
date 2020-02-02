package com.williamfiset.algorithms.datastructures.stack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class StackTest {

  Stack<Integer> stack;

  @Before
  public void setup() {
    stack = new Stack<Integer>();
  }

  @Test
  public void testEmptyStack() {
    assertTrue(stack.isEmpty());
    assertEquals(stack.size(), 0);
  }

  @Test(expected = Exception.class)
  public void testPopOnEmpty() {
    stack.pop();
  }

  @Test(expected = Exception.class)
  public void testPeekOnEmpty() {
    stack.peek();
  }

  @Test
  public void testPush() {
    stack.push(2);
    assertEquals(stack.size(), 1);
  }

  @Test
  public void testPeek() {
    stack.push(2);
    assertTrue(stack.peek() == 2);
    assertEquals(stack.size(), 1);
  }

  @Test
  public void testPop() {
    stack.push(2);
    assertTrue(stack.pop() == 2);
    assertEquals(stack.size(), 0);
  }

  @Test
  public void testExhaustively() {
    assertTrue(stack.isEmpty());
    stack.push(1);
    assertTrue(!stack.isEmpty());
    stack.push(2);
    assertEquals(stack.size(), 2);
    assertTrue(stack.peek() == 2);
    assertEquals(stack.size(), 2);
    assertTrue(stack.pop() == 2);
    assertEquals(stack.size(), 1);
    assertTrue(stack.peek() == 1);
    assertEquals(stack.size(), 1);
    assertTrue(stack.pop() == 1);
    assertEquals(stack.size(), 0);
    assertTrue(stack.isEmpty());
  }
}
