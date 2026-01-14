package com.williamfiset.algorithms.graphtheory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FindAllCliquesTest {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  private String outputBuilder(String[] output) {
    String lineSeparator = System.getProperty("line.separator");
    StringBuilder result = new StringBuilder();
    for (String element : output) {
      result.append(element).append(lineSeparator);
    }

    return result.toString();
  }

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @Test
  public void testFindAllCliquesEmptyGraph() {
    int[][] edges = {};
    String[] cliquesList = {};

    FindAllCliques.findAllCliques(edges, 0);
    Assert.assertEquals(outputBuilder(cliquesList), outContent.toString());
  }

  @Test
  public void testFindAllCliquesSingleNode() {
    int[][] edges = {};
    String[] cliquesList = {"1"};

    FindAllCliques.findAllCliques(edges, 1);
    Assert.assertEquals(outputBuilder(cliquesList), outContent.toString());
  }

  @Test
  public void testFindAllCliquesMultipleNodes() {
    int[][] edges = {
      {1, 2},
      {2, 3},
      {3, 1},
      {4, 3},
      {4, 5},
      {5, 3},
    };

    String[] cliquesList = {
      "1", "2", "3", "4", "5", "1, 2", "1, 3", "2, 3", "3, 4", "3, 5", "4, 5", "1, 2, 3", "3, 4, 5",
    };

    FindAllCliques.findAllCliques(edges, 5);

    Assert.assertEquals(outputBuilder(cliquesList), outContent.toString());
  }

  @Test(expected = NullPointerException.class)
  public void testFindAllCliquesNullInput() {
    FindAllCliques.findAllCliques(null, 0);
  }

  @Test(expected = IndexOutOfBoundsException.class)
  public void testFindAllCliquesTooManyNodes() {
    int[][] edges = {
      {1, 2},
      {2, 3},
      {3, 1},
      {4, 3},
      {4, 5},
      {5, 3},
    };
    FindAllCliques.findAllCliques(edges, 10001);
  }

  @Test
  public void testFindAllCliquesNegativeNumberOfNodes() {
    int[][] edges = {
      {1, 2},
      {2, 3},
      {3, 1},
      {4, 3},
      {4, 5},
      {5, 3},
    };
    FindAllCliques.findAllCliques(edges, -1);

    Assert.assertEquals("", outContent.toString());
  }
}
