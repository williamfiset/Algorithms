import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.*;

public class FloydWarshallTest {

  static final double INF = Double.POSITIVE_INFINITY;
  static final double NEG_INF = Double.NEGATIVE_INFINITY;

  static double[][] matrix1, matrix2;

  @Before
  public void setup() {
    matrix1 = new double[][] {
      {  0, INF, INF, INF, INF},
      {  1,   0,   7, INF, INF},
      {INF,   3,   0, INF, INF},
      { 13, INF,   4,   0, INF},
      {INF, INF,   3,   0,   0}
    };
    matrix2 = new double[][] {
      {  0,   3,   1,   8, INF},
      {  2,   0,   9,   4, INF},
      {INF, INF,   0, INF,  -2},
      {INF, INF,   1,   0, INF},
      {INF, INF, INF,   0,   0}
    };
  }

  @Test
  public void testDirectedGraph() {

    FloydWarshall.floydWarshall(matrix1);

    assertThat(matrix1[0][0]).isEqualTo(0.0);
    assertThat(matrix1[1][0]).isEqualTo(1.0);
    assertThat(matrix1[1][1]).isEqualTo(0.0);
    assertThat(matrix1[1][2]).isEqualTo(7.0);
    assertThat(matrix1[2][0]).isEqualTo(4.0);
    assertThat(matrix1[2][1]).isEqualTo(3.0);
    assertThat(matrix1[2][2]).isEqualTo(0.0);
    assertThat(matrix1[3][0]).isEqualTo(8.0);
    assertThat(matrix1[3][1]).isEqualTo(7.0);
    assertThat(matrix1[3][2]).isEqualTo(4.0);
    assertThat(matrix1[3][3]).isEqualTo(0.0);
    assertThat(matrix1[4][0]).isEqualTo(7.0);
    assertThat(matrix1[4][1]).isEqualTo(6.0);
    assertThat(matrix1[4][2]).isEqualTo(3.0);
    assertThat(matrix1[4][3]).isEqualTo(0.0);
    assertThat(matrix1[4][4]).isEqualTo(0.0);

  }

  @Test
  public void testNegativeCycleGraph() {

    FloydWarshall.floydWarshall(matrix2);

    assertThat(matrix2[0][0]).isEqualTo(0.0);
    assertThat(matrix2[0][1]).isEqualTo(3.0);
    assertThat(matrix2[0][2]).isEqualTo(NEG_INF);
    assertThat(matrix2[0][3]).isEqualTo(NEG_INF);
    assertThat(matrix2[0][4]).isEqualTo(NEG_INF);
    assertThat(matrix2[1][0]).isEqualTo(2.0);
    assertThat(matrix2[1][1]).isEqualTo(0.0);
    assertThat(matrix2[1][2]).isEqualTo(NEG_INF);
    assertThat(matrix2[1][3]).isEqualTo(NEG_INF);
    assertThat(matrix2[1][4]).isEqualTo(NEG_INF);
    assertThat(matrix2[2][2]).isEqualTo(NEG_INF);
    assertThat(matrix2[2][3]).isEqualTo(NEG_INF);
    assertThat(matrix2[2][4]).isEqualTo(NEG_INF);
    assertThat(matrix2[3][2]).isEqualTo(NEG_INF);
    assertThat(matrix2[3][3]).isEqualTo(NEG_INF);
    assertThat(matrix2[3][4]).isEqualTo(NEG_INF);
    assertThat(matrix2[4][2]).isEqualTo(NEG_INF);
    assertThat(matrix2[4][3]).isEqualTo(NEG_INF);
    assertThat(matrix2[4][4]).isEqualTo(NEG_INF);

  }
  
}