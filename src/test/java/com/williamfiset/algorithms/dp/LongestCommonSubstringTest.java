package com.williamfiset.algorithms.dp;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.primitives.Ints;
import com.williamfiset.algorithms.utils.TestUtils;
import java.util.*;
import org.junit.*;



public class LongestCommonSubstringTest {


    /*
    Tests that two inputs with no overlapping substring returns empty string.
    */
    @Test
    public void testNullSubstring(){
        char[] s1 = {'a','b','c'};
        char[] s2 = {'e','e','f'};
        String result = LongestCommonSubstring.lcs(s1,s2);
        assertThat(result).isEqualTo("");
    }


    /*
     Tests that two inputs with common substring embedded in word returns
     that substring
    */
    @Test
    public void testEmbeddedWord(){
        char[] s1 = {'a','b','c'};
        char[] s2 = {'b','c','d'};
        String result = LongestCommonSubstring.lcs(s1,s2);
        assertThat(result).isEqualTo("bc");
    }

    /*
    Tests inputs with varying length to ensure common substring is found,
    where the common substring in the longer string is located at indices greater
    than the length of the shorter string
    */
    @Test
    public void testVaryingLength(){
        char[] s1 = {'a','b','c'};
        char[] s2 = {'d','e','f','d','e','f','d','e','f','a','b','c'};
        String result = LongestCommonSubstring.lcs(s1,s2);
        assertThat(result).isEqualTo("abc");
    }


    /*
    Inputs with more than one common substring, tests that the correct
    (longest one), is returned
     */
    @Test
    public void testCorrectSubstring(){
        char[] s1 = {'a','b','c','d','e','f','h','u','s'};
        char[] s2 = {'b','i','l','h','u','s','a','b','c','d','e','f'};
        String result = LongestCommonSubstring.lcs(s1,s2);
        assertThat(result).isEqualTo("abcdef");
    }
}