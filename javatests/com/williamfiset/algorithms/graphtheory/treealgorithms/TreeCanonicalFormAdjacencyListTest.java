package javatests.com.williamfiset.algorithms.graphtheory.treealgorithms;

import static com.google.common.truth.Truth.assertThat;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCanonicalFormAdjacencyList.createEmptyTree;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCanonicalFormAdjacencyList.addUndirectedEdge;
import static com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCanonicalFormAdjacencyList.encodeTree;

import com.williamfiset.algorithms.graphtheory.treealgorithms.TreeCanonicalFormAdjacencyList;
import java.util.*; import org.junit.*;

public class TreeCanonicalFormAdjacencyListTest {

  @Test
  public void testSingleton() {
    List<List<Integer>> tree1 = createEmptyTree(1);
    List<List<Integer>> tree2 = createEmptyTree(1);
    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);
    assertThat(encoding1).isEqualTo(encoding2);
    assertThat(encoding1).isEqualTo("()");
  }

  @Test
  public void testTwoNodeTree() {
    List<List<Integer>> tree1 = createEmptyTree(2);
    List<List<Integer>> tree2 = createEmptyTree(2);
    addUndirectedEdge(tree1, 0, 1);
    addUndirectedEdge(tree2, 1, 0);
    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);
    assertThat(encoding1).isEqualTo(encoding2);
    assertThat(encoding1).isEqualTo("()()");
  }

  @Test
  public void testSmall() {
    List<List<Integer>> tree1 = createEmptyTree(5);
    List<List<Integer>> tree2 = createEmptyTree(5);

    addUndirectedEdge(tree1,2,0);
    addUndirectedEdge(tree1,2,1);
    addUndirectedEdge(tree1,2,3);
    addUndirectedEdge(tree1,3,4);

    addUndirectedEdge(tree2,1,3);
    addUndirectedEdge(tree2,1,0);
    addUndirectedEdge(tree2,1,2);
    addUndirectedEdge(tree2,2,4);

    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);
    assertThat(encoding1).isEqualTo(encoding2);
    assertThat(encoding1).isEqualTo("(()())(())");
  }

  @Test
  public void testSimilarChains() {
    // Trees 1 and 3 are equal
    int n = 10;
    List<List<Integer>> tree1 = createEmptyTree(n);
    List<List<Integer>> tree2 = createEmptyTree(n);
    List<List<Integer>> tree3 = createEmptyTree(n);

    addUndirectedEdge(tree1,0,1);
    addUndirectedEdge(tree1,1,3);
    addUndirectedEdge(tree1,3,5);
    addUndirectedEdge(tree1,5,7);
    addUndirectedEdge(tree1,7,8);
    addUndirectedEdge(tree1,8,9);
    addUndirectedEdge(tree1,2,1);
    addUndirectedEdge(tree1,4,3);
    addUndirectedEdge(tree1,6,5);

    addUndirectedEdge(tree2,0,1);
    addUndirectedEdge(tree2,1,3);
    addUndirectedEdge(tree2,3,5);
    addUndirectedEdge(tree2,5,6);
    addUndirectedEdge(tree2,6,8);
    addUndirectedEdge(tree2,8,9);
    addUndirectedEdge(tree2,6,7);
    addUndirectedEdge(tree2,4,3);
    addUndirectedEdge(tree2,2,1);

    addUndirectedEdge(tree3, 0, 1);
    addUndirectedEdge(tree3, 1, 8);
    addUndirectedEdge(tree3, 1, 6);
    addUndirectedEdge(tree3, 6, 4);
    addUndirectedEdge(tree3, 6, 5);
    addUndirectedEdge(tree3, 5, 3);
    addUndirectedEdge(tree3, 5, 7);
    addUndirectedEdge(tree3, 7, 2);
    addUndirectedEdge(tree3, 2, 9);

    String encoding1 = encodeTree(tree1);
    String encoding2 = encodeTree(tree2);
    String encoding3 = encodeTree(tree3);
    assertThat(encoding1).isNotEqualTo(encoding2);
    assertThat(encoding1).isEqualTo(encoding3);
    assertThat(encoding2).isNotEqualTo(encoding3);
  }

  @Test
  public void testSlidesExample() {
    // Setup tree structure from:
    // http://webhome.cs.uvic.ca/~wendym/courses/582/16/notes/582_12_tree_can_form.pdf
    List<List<Integer>> tree = createEmptyTree(19);

    addUndirectedEdge(tree,6,2);
    addUndirectedEdge(tree,6,7);
    addUndirectedEdge(tree,6,11);
    addUndirectedEdge(tree,7,8);
    addUndirectedEdge(tree,7,9);
    addUndirectedEdge(tree,7,10);
    addUndirectedEdge(tree,11,12);
    addUndirectedEdge(tree,11,13);
    addUndirectedEdge(tree,11,16);
    addUndirectedEdge(tree,13,14);
    addUndirectedEdge(tree,13,15);
    addUndirectedEdge(tree,16,17);
    addUndirectedEdge(tree,16,18);
    addUndirectedEdge(tree,2,0);
    addUndirectedEdge(tree,2,1);
    addUndirectedEdge(tree,2,3);
    addUndirectedEdge(tree,2,4);
    addUndirectedEdge(tree,4,5);

    String treeEncoding = encodeTree(tree);
    String expectedEncoding = "(((()())(()())())((())()()())(()()()))";
    assertThat(treeEncoding).isEqualTo(expectedEncoding);
  }
  
}
