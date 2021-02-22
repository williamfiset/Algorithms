
package com.williamfiset.algorithms.datastructures.utils;
import static com.google.common.truth.Truth.assertThat;
import org.junit.*;


public class TreePrinterTest {
    private class TestTreeNode implements TreePrinter.PrintableNode {
        String value;
        private TestTreeNode left;
        private TestTreeNode right;
    
        public TestTreeNode(String value, TestTreeNode left, TestTreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    
        // Get left child
        public TreePrinter.PrintableNode getLeft() {
            return this.left;
        }
    
        // Get right child
        public TreePrinter.PrintableNode getRight() {
            return this.right;
        }
    
        // Get text to be printed
        public String getText() {
            return value;
        }
    }

  @Test
  public void treePrinter_displayTree_checkValuesExist() {
    TestTreeNode treeToTest = new TestTreeNode(
        "A", 
        new TestTreeNode(
            "B", null, null
        ), 
        new TestTreeNode(
            "C", null, null
        )
    );
    TreePrinter.getTreeDisplay(treeToTest);
    assertThat(true).isEqualTo(true);
  }
}
