import static com.google.common.truth.Truth.assertThat;

import java.util.*;
import org.junit.*;

public class TravelingSalesmanProblemTest {

  private static final double EPS = 1e-5;
  
  @Test(expected=IllegalArgumentException.class)
  public void testNonSquareMatrix() {
    double[][] dist = {
      {1, 2, 3},
      {4, 5, 6}
    };
    TravelingSalesmanProblemDynamicProgramming.tsp(dist);
  }
  
  @Test(expected=IllegalStateException.class)
  public void testSmallGraph() {
    double[][] dist = {
      {0, 1},
      {1, 0}
    };
    TravelingSalesmanProblemDynamicProgramming.tsp(dist);
  }
  
  @Test
  public void testTsp_small1() {
    int n = 5;
    double[][] dist = new double[n][n];
    for(double[] row : dist) java.util.Arrays.fill(row, 100);
    
    dist[1][3] = 1;
    dist[3][0] = 2;
    dist[0][2] = 3;
    dist[2][4] = 4;
    dist[4][1] = 5;
    
    double tourCost = TravelingSalesmanProblemDynamicProgramming.tsp(dist);
    assertThat(tourCost).isWithin(EPS).of(1+2+3+4+5);
  }
  
  @Test
  public void testDpVsBf() {
    for(int n = 3; n <= 8; n++) {
      for (int i = 0; i < 10; i++) {
        
        double[][] dist = new double[n][n];
        randomFillDistMatrix(dist);
        
        double dp = TravelingSalesmanProblemDynamicProgramming.tsp(dist);
        double bf = TravelingSalesmanProblemBruteForce.computeTourCost(TravelingSalesmanProblemBruteForce.tsp(dist), dist);
        
        assertThat(dp).isWithin(EPS).of(bf);
      }
    }
  }
  
  public void randomFillDistMatrix(double[][] dist) {
    for (int i = 0; i < dist.length; i++)
      for (int j = i + 1; j < dist.length; j++)
        dist[i][j] = dist[j][i] = (int)(Math.random() * 1000);
  }
  
}