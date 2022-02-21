package com.williamfiset.algorithms.linearalgebra;

import static com.google.common.truth.Truth.assertThat;
import org.junit.*;

public class GaussianEliminationTest {

    @Test
    public void test_Gauss_Elimination_solve_1(){
        double[][] augmentedMatrix = {
                {2, -3, 5, 10},
                {1, 2, -1, 18},
                {6, -1, 0, 12}
        };

        GaussianElimination.solve(augmentedMatrix);

        double x = augmentedMatrix[0][3];
        double y = augmentedMatrix[1][3];
        double z = augmentedMatrix[2][3];

        assertThat(x).isEqualTo(3.7551020408163263);
        assertThat(y).isEqualTo(10.53061224489796);
        assertThat(z).isEqualTo(6.816326530612245);
    }

    @Test
    public void test_Gauss_Elimination_solve_2(){
        double[][] augmentedMatrix = {
                {0, 0, 1, 10},
                {0, 1, 0, 18},
                {1, 0, 0, 12}
        };

        GaussianElimination.solve(augmentedMatrix);

        double x = augmentedMatrix[0][3];
        double y = augmentedMatrix[1][3];
        double z = augmentedMatrix[2][3];

        assertThat(x).isEqualTo(12);
        assertThat(y).isEqualTo(18);
        assertThat(z).isEqualTo(10);
    }
}
