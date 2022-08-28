package test.java.com.williamfiset.algorithms.math;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinToMaxTest {
    //Min
    @Test
    public void testeMin1(){
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        assertEquals(1, MinToMax.MinResult(array));
    }
    @Test
    public void testeMin2(){
        int[] array = {5,6,2,8,15};
        assertEquals(2, MinToMax.MinResult(array));
    }
    @Test
    public void testeMin3(){
        int[] array = {0,0,0,0,0,0};
        assertEquals(0, MinToMax.MinResult(array));
    }
    @Test
    public void testeMin4(){
        int[] array = {-10,-9,-2,-3,-7,-111};
        assertEquals(-111, MinToMax.MinResult(array));
    }
    //Median
    @Test
    public void testeMedian1(){
        int[] array1 = {1,2,3,4,8};
        assertEquals(3.6, MinToMax.MedianResult(array));
    }
    @Test
    public void testeMedian2(){
        int[] array3 = {4,3,5,7};
        assertEquals(4.75, MinToMax.MedianResult(array));
    }
    @Test
    public void testeMedian3(){
        int[] array = {0,0,0,0,0,0};
        assertEquals(0, MinToMax.MedianResult(array));
    }
    @Test
    public void testeMedian4(){
        int[] array4 = {-1,0,-3,-9};
        assertEquals(-3.25, MinToMax.MedianResult(array));
    }
    //Max
    @Test
    public void testeMax1(){
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        assertEquals(10, MinToMax.MaxResult(array));
    }
    @Test
    public void testeMax2(){
        int[] array = {5,6,2,8,15};
        assertEquals(15, MinToMax.MaxResult(array));
    }
    @Test
    public void testeMax3(){
        int[] array = {0,0,0,0,0,0};
        assertEquals(0, MinToMax.MaxResult(array));
    }
    @Test
    public void testeMax4(){
        int[] array = {-10,-9,-2,-3,-7,-111};
        assertEquals(-2, MinToMax.MaxResult(array));
    }
}