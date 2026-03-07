package com.williamfiset.algorithms.datastructures.suffixarray;
import static com.google.common.truth.Truth.assertThat;

import java.security.SecureRandom;
import java.util.Random;
import org.junit.*;
public class SuffixArrayNewTest {
    @Test
    public void suffixArrayLength(){
        String str = "banana";

        SuffixArrayNew saTest1=new SuffixArrayNew();
        int[] result1= saTest1.generateSA(str);
        assertThat(result1.length).isEqualTo(str.length());
    }

    @Test
    public void suffixArrayTest1(){
        String str = "banana";
        int[] rightResult = {5,3,1,0,4,2};
        SuffixArrayNew saTest2=new SuffixArrayNew();
        int[] result2= saTest2.generateSA(str);
        for(int i=0; i<rightResult.length;i++){
            assertThat(result2[i]).isEqualTo(result2[i]);
        }

    }

    @Test
    public void suffixArrayTest2(){
        String str = "WorldIsBigAndWeAreSmall";
        int[] rightResult = {10, 15, 7, 5, 18, 13, 0, 20, 4, 12, 14, 17, 9, 8, 22, 3, 21, 19, 11, 1, 16, 2, 6};
        SuffixArrayNew saTest2=new SuffixArrayNew();
        int[] result2= saTest2.generateSA(str);
        for(int i=0; i<rightResult.length;i++){
            assertThat(result2[i]).isEqualTo(result2[i]);
        }

    }
}
