
import java.util.*;

public class TravelingSalesmanProblemDynamicProgramming {

  public static double tsp(double[][] matrix) {

    int n = matrix.length;
    int numSubsets = (1 << n);

    double[][] DP = new double[numSubsets][n];
    for (double[] row : DP) Arrays.fill(row, Double.POSITIVE_INFINITY);

    for (int i = 0; i < n; i++)
      DP[i][i] = matrix[0][i];

    for (int s = 2; s < n; s++) {
      for (int subset = 1; subset < numSubsets; subset++) {
        if ( (subset & 1) == 1 ) continue;
        for (int j = 1; j < n; j++) {
          int mask = 1 << j;
          int subsetWithoutJ = (subset ^ mask);
          if ((subset & mask) != mask) continue;
          for (int i = 0; i < n; i++) {
            int mask2 = (1 << i);
            if ((subset & mask2) != mask2) continue;
            DP[subset][j] = Math.min(DP[subset][j], DP[subsetWithoutJ][i] + matrix[i][j]);
          }
        }
      }
    }

    double minTour = Double.POSITIVE_INFINITY;
    for(int j = 1; j < n; j++) {
      double tripBackCost = matrix[j][0];
      if ( DP[numSubsets-1][j] + tripBackCost < minTour ) {
        minTour = DP[numSubsets-1][j] + tripBackCost;
      }
    }

    return minTour;

  }

  public static void main(String[] args) {

    int n = 10;
    
    double[][] m = new double[n][n];
    for(double[] row : m) Arrays.fill(row, 10.0);

    // Construct an optimal path
    List <Integer> path = new ArrayList<>(n);
    for(int i = 0; i < n; i++) path.add(i);

    for (int i = 1; i < n; i++) {
      int from = path.get(i-1);
      int to = path.get(i);
      m[from][to] = 1.0;
    }
    int last = path.get(n-1);
    int first = path.get(0);
    m[last][first] = 1.0;

    System.out.println(tsp(m));

  }

}







