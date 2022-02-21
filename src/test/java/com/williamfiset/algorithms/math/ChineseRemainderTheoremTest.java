package com.williamfiset.algorithms.math;

import org.junit.Assert;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class ChineseRemainderTheoremTest {
    /*
        Tests that each moduli are coprrime upon returning from reduce(),
        as specified in the requirements of the source code.
        */
    @Test
    public void reduceCoprimeTest(){

        long[] l1 = new long[]{1,2,3,4,5};
        long[] l2 = new long[]{6,7,8,9,10};

        long [][] output = ChineseRemainderTheorem.reduce(l1,l2);

        for(int i=0; i<output[1].length; i++){
            for(int j = i+1; j< output[1].length; j++) {
                long firstMod = output[1][i];
                long secondMod = output[1][j];
                long gcd = GCD.gcd(firstMod, secondMod);
                assertThat(gcd).isEqualTo(1);
            }
        }
    }

    /**
     * The following inputs to the equations should lack a solution.
     * Tests that the function should return null.
     */
    @Test
    public void testNoSolution(){
        long[] l1 = new long[]{3,0};
        long[] l2 = new long[]{4,6};

        Assert.assertNull(ChineseRemainderTheorem.reduce(l1,l2));
    }

    /**
     * The following inputs are a variant of the test above.
     * It tests that the other return null is used.
     */
    @Test
    public void testNoSolution2(){
        long[] l1 = new long[]{0,3};
        long[] l2 = new long[]{6,4};

        Assert.assertNull(ChineseRemainderTheorem.reduce(l1,l2));
    }

    /**
     * Tests reduntant input equations.
     * Reduntant equations should be eliminated according to the requirements.
     */
    @Test
    public void testEliminateRedundance(){

        long[] l1 = new long[]{2,2,2,2,2};
        long[] l2 = new long[]{9,9,9,9,9};

        long [][] output = ChineseRemainderTheorem.reduce(l1,l2);
        Assert.assertEquals(1, output[0].length);
        Assert.assertEquals(1, output[1].length);

    }

}
