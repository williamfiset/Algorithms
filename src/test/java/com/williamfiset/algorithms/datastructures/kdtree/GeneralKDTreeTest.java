package com.williamfiset.algorithms.datastructures.kdtree;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class GeneralKDTreeTest {

  /* TREE CREATION TESTS */
  @Test
  public void testDimensionsZero() {
    assertThrows(IllegalArgumentException.class, () -> new GeneralKDTree<>(0));
  }

  @Test
  public void testDimensionsNegative() {
    assertThrows(IllegalArgumentException.class, () -> new GeneralKDTree<>(-5));
  }

  /* INSERT METHOD TESTS */
  @Test
  public void testInsert() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertThat(kdTree.getRootPoint() == null).isTrue();
    Integer[] pointRoot = {3, 4, 3};
    Integer[] pointLeft = {1, 7, 6};
    Integer[] pointRight = {3, 0, 2};
    kdTree.insert(pointRoot);
    kdTree.insert(pointLeft);
    kdTree.insert(pointRight);
    assertThat(kdTree.getRootPoint() != null).isTrue();
    assertThat(kdTree.getRootPoint() == pointRoot).isTrue();
  }

  @Test
  public void testInsertNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.insert(null));
  }

  @Test
  public void testInsertMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.insert(new Integer[] {1, 2, 3}));
  }

  /* SEARCH METHOD TESTS */
  @Test
  public void testSearch() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(4);
    assertThat(kdTree.search(new Integer[] {7, 5, 4, 9})).isFalse();
    Integer[] point1 = {3, 4, 3, 9};
    Integer[] point2 = {2, 1, 5, 9};
    Integer[] point3 = {5, 6, 9, 9};
    Integer[] point4 = {4, 4, 0, 9};
    kdTree.insert(point1);
    kdTree.insert(point2);
    kdTree.insert(point3);
    kdTree.insert(point4);
    assertThat(kdTree.search(point1)).isTrue();
    assertThat(kdTree.search(point2)).isTrue();
    assertThat(kdTree.search(point3)).isTrue();
    assertThat(kdTree.search(point4)).isTrue();
    assertThat(kdTree.search(new Integer[] {7, 5, 4, 9})).isFalse();
  }

  @Test
  public void testSearchNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.search(null));
  }

  @Test
  public void testSearchMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.search(new Integer[] {1, 2, 3}));
  }

  /* FINDMIN METHOD TESTS */
  @Test
  public void testFindMin() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertThat(kdTree.findMin(0) == null).isTrue();
    Integer[] min1 = {0, 5, 4};
    Integer[] min2 = {3, 0, 7};
    Integer[] min3 = {6, 6, 0};
    kdTree.insert(new Integer[] {3, 7, 9});
    kdTree.insert(min1);
    kdTree.insert(min3);
    kdTree.insert(min2);
    kdTree.insert(new Integer[] {4, 7, 5});
    kdTree.insert(new Integer[] {3, 4, 8});
    kdTree.insert(new Integer[] {7, 7, 2});
    kdTree.insert(new Integer[] {8, 9, 8});
    assertThat(kdTree.findMin(0) == min1).isTrue();
    assertThat(kdTree.findMin(1) == min2).isTrue();
    assertThat(kdTree.findMin(2) == min3).isTrue();
  }

  @Test
  public void testFindMinOutOfBounds() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.findMin(2));
  }

  @Test
  public void testFindMinNegative() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.findMin(-1));
  }

  /* DELETE METHOD TESTS */
  @Test
  public void testDeleteEmpty() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThat(kdTree.delete(new Integer[] {1, 2}) == null).isTrue();
  }

  @Test
  public void testDeleteRoot() {
    // General Setup
    GeneralKDTree<Integer> kdTreeBarren = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeLeft = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeRight = new GeneralKDTree<Integer>(3);
    GeneralKDTree<Integer> kdTreeTwo = new GeneralKDTree<Integer>(3);
    Integer[] rootPoint = {2, 2, 2};
    Integer[] leftPoint = {1, 1, 1};
    Integer[] rightPoint = {3, 3, 3};
    // No child test
    kdTreeBarren.insert(rootPoint);
    assertThat(kdTreeBarren.delete(rootPoint) == rootPoint).isTrue();
    // Left child test
    kdTreeLeft.insert(rootPoint);
    kdTreeLeft.insert(leftPoint);
    assertThat(kdTreeLeft.delete(rootPoint) == rootPoint).isTrue();
    // Right child test
    kdTreeRight.insert(rootPoint);
    kdTreeRight.insert(rightPoint);
    assertThat(kdTreeRight.delete(rootPoint) == rootPoint).isTrue();
    // Both children test
    kdTreeTwo.insert(rootPoint);
    kdTreeTwo.insert(leftPoint);
    kdTreeTwo.insert(rightPoint);
    assertThat(kdTreeTwo.delete(rootPoint) == rootPoint).isTrue();
  }

  @Test
  public void testDelete() {
    // Tree Setup
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(3);
    assertThat(kdTree.findMin(0) == null).isTrue();
    Integer[] point1 = {3, 7, 9};
    Integer[] point2 = {0, 5, 4};
    Integer[] point3 = {6, 6, 0};
    Integer[] point4 = {3, 0, 7};
    Integer[] point5 = {4, 7, 5};
    Integer[] point6 = {3, 4, 8};
    Integer[] point7 = {7, 7, 2};
    Integer[] point8 = {8, 9, 8};
    kdTree.insert(point1);
    kdTree.insert(point2);
    kdTree.insert(point3);
    kdTree.insert(point4);
    kdTree.insert(point5);
    kdTree.insert(point6);
    kdTree.insert(point7);
    kdTree.insert(point8);
    // Delete Action Assertions
    assertThat(kdTree.delete(point8) == point8).isTrue();
    assertThat(kdTree.delete(point5) == point5).isTrue();
    assertThat(kdTree.delete(point3) == point3).isTrue();
    assertThat(kdTree.delete(point8) == null).isTrue();
  }

  @Test
  public void testDeleteNull() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.delete(null));
  }

  @Test
  public void testDeleteMismatchDimensions() {
    GeneralKDTree<Integer> kdTree = new GeneralKDTree<Integer>(2);
    assertThrows(IllegalArgumentException.class, () -> kdTree.delete(new Integer[] {1, 2, 3}));
  }
}
